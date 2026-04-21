package com.hfad.smgrapp.ui.smgr.wagons

// REDESIGN v2 — WagonsActivity.kt
// Изменения относительно предыдущей версии:
//   1. Вместо чипов по полю `rod` (где были числа "10", "11"…) —
//      человекочитаемые категории: Крытые, Полувагоны, Платформы и т.д.
//   2. Категории описаны в одном месте (CATEGORIES) — легко расширять.
//   3. В чипе показывается количество моделей в категории: "Крытые · 12".
//   4. Чипы строятся только для категорий, реально присутствующих в данных.
//   5. ChipGroup работает в режиме single-select с обязательным выбором.
//   6. Выбранный фильтр переживает поворот экрана (onSaveInstanceState).
//   7. После смены фильтра список скроллится наверх.
//   8. TextWatcher заменён на doAfterTextChanged (core-ktx) — чище и короче.

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.chip.Chip
import com.hfad.smgrapp.R
import com.hfad.smgrapp.databinding.ActivityWagonsBinding
import com.hfad.smgrapp.model.Wagons
import com.hfad.smgrapp.ui.smgr.WagonActivity
import com.hfad.smgrapp.ui.smgr.favourite.FavouriteWagonsActivity
import com.hfad.smgrapp.ui.smgr.wagons.adapter.AdapterWagons
import com.hfad.smgrapp.ui.smgr.wagons.impl.WagonsContract
import com.hfad.smgrapp.ui.smgr.wagons.impl.WagonsPresenterImpl
import java.io.Serializable

class WagonsActivity : AppCompatActivity(),
    WagonsContract.View,
    AdapterWagons.OnClickListener {

    // ── Категории вагонов ────────────────────────────────────────────────────
    // Префикс — это первые две цифры модели (до знака "-").
    // Пример: "10-4022" → "10" → категория "Крытые".
    private data class WagonCategory(
        val displayName: String,
        val prefixes: List<String>
    )

    private val categories = listOf(
        WagonCategory("Крытые",       listOf("10", "11")),
        WagonCategory("Полувагоны",   listOf("12")),
        WagonCategory("Платформы",    listOf("13", "23")),
        WagonCategory("Транспортёры", listOf("14")),
        WagonCategory("Цистерны",     listOf("15")),
        WagonCategory("Рефрижераторы", listOf("16")), // "Рефы" — разговорное; в UI лучше полное
        WagonCategory("Бункерные",    listOf("17")),
        WagonCategory("Хопперы",      listOf("19", "20", "55")),
        WagonCategory("Самосвалы",    listOf("31"))
    )

    // ── Состояние ────────────────────────────────────────────────────────────
    private lateinit var binding: ActivityWagonsBinding
    private lateinit var presenter: WagonsPresenterImpl
    private lateinit var adapterWagons: AdapterWagons

    /** Префиксы выбранной категории; пустой список = "Все". */
    private var selectedPrefixes: List<String> = emptyList()

    /** Ключ для восстановления выбранного фильтра. */
    companion object {
        private const val STATE_SELECTED_PREFIXES = "state_selected_prefixes"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWagonsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Восстанавливаем фильтр после поворота
        savedInstanceState?.getStringArrayList(STATE_SELECTED_PREFIXES)?.let {
            selectedPrefixes = it.toList()
        }

        adapterWagons = AdapterWagons(ArrayList(), this)
        binding.recyclerView.adapter = adapterWagons

        presenter = WagonsPresenterImpl()
        presenter.attachView(this)
        presenter.responseData()

        setupToolbar()
        setupSearch()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList(
            STATE_SELECTED_PREFIXES,
            ArrayList(selectedPrefixes)
        )
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    // ── Toolbar ──────────────────────────────────────────────────────────────
    private fun setupToolbar() = with(binding.toolbar) {
        textView.text = "Модели вагонов"
        clickBackBtn.setOnClickListener { onBackPressed() }
        clickHomeBtn.visibility = View.VISIBLE
        clickHomeBtn.setImageDrawable(
            resources.getDrawable(R.drawable.ic_favourite_list, theme)
        )
        clickHomeBtn.setOnClickListener {
            startActivity(Intent(this@WagonsActivity, FavouriteWagonsActivity::class.java))
        }
    }

    // ── Поиск ────────────────────────────────────────────────────────────────
    private fun setupSearch() {
        binding.searchView.doAfterTextChanged { applyFilters() }
    }

    // ── Чипы категорий ───────────────────────────────────────────────────────
    private fun buildChips(wagons: List<Wagons>) {
        val chipGroup = binding.chipGroup
        chipGroup.removeAllViews()
        // Ровно один чип всегда активен — это правильный UX для режима "фильтр-табы":
        chipGroup.isSingleSelection = true
        chipGroup.isSelectionRequired = true

        // Какой чип должен быть выбран при открытии (учитываем восстановленный стейт)
        val restoredCategory = categories.firstOrNull {
            it.prefixes == selectedPrefixes
        }

        // Чип "Все" — всегда первый
        chipGroup.addView(
            makeChip(
                label = "Все",
                selected = restoredCategory == null
            ) {
                selectedPrefixes = emptyList()
                applyFilters()
            }
        )

        // Строим чипы только для категорий, реально представленных в данных.
        val presentPrefixes = wagons.mapTo(HashSet()) { extractPrefix(it.model) }
        categories
            .filter { cat -> cat.prefixes.any { it in presentPrefixes } }
            .forEach { category ->
                val count = wagons.count { extractPrefix(it.model) in category.prefixes }
                chipGroup.addView(
                    makeChip(
                        label = "${category.displayName} · $count",
                        selected = category == restoredCategory
                    ) {
                        selectedPrefixes = category.prefixes
                        applyFilters()
                    }
                )
            }
    }

    private fun makeChip(
        label: String,
        selected: Boolean = false,
        onClick: () -> Unit
    ): Chip = Chip(this).apply {
        text = label
        isCheckable = true
        isChecked = selected
        chipCornerRadius = 20f
        setOnClickListener { onClick() }
    }

    /** Первые 2 символа до "-": "11-066-04" → "11". */
    private fun extractPrefix(model: String): String =
        model.substringBefore("-").take(2)

    // ── Применение фильтров ──────────────────────────────────────────────────
    private fun applyFilters() {
        val query = binding.searchView.text?.toString().orEmpty()
        adapterWagons.applyFilters(query = query, prefixes = selectedPrefixes)
        // Возвращаем пользователя наверх списка, чтобы он видел свежие результаты.
        binding.recyclerView.scrollToPosition(0)
    }

    // ── WagonsContract.View ──────────────────────────────────────────────────
    override fun onSuccessList(wagons: ArrayList<Wagons>) {
        adapterWagons = AdapterWagons(wagons, this)
        binding.recyclerView.adapter = adapterWagons
        buildChips(wagons)
        // Применяем восстановленный фильтр (если был)
        if (selectedPrefixes.isNotEmpty() ||
            !binding.searchView.text.isNullOrEmpty()
        ) {
            applyFilters()
        }
    }

    override fun error(errMessage: String) {
        binding.layoutNotConnection.visibility = View.VISIBLE
        binding.txtInputLayout.visibility = View.GONE
        binding.chipScrollView.visibility = View.GONE
        binding.btnClickReply.setOnClickListener {
            presenter.responseData()
            binding.txtInputLayout.visibility = View.VISIBLE
            binding.chipScrollView.visibility = View.VISIBLE
        }
    }

    override fun progress(show: Boolean) {
        binding.layoutNotConnection.visibility = View.GONE
        binding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun onClickModel(wagons: Wagons) {
        val intent = Intent(this, WagonActivity::class.java)
        intent.putExtra("WAGON", wagons as Serializable)
        startActivity(intent)
    }
}