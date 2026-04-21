package com.hfad.smgrapp.ui.smgr.wagons

// REDESIGN v3 — WagonsActivity.kt
// Изменения относительно v2:
//   1. Отключён счётчик символов TextInputLayout (counterEnabled = false).
//   2. Добавлен динамический счётчик результатов через helperText:
//      "Найдено: 12 моделей" / "Найдена 1 модель" / "Ничего не найдено".
//   3. Счётчик обновляется:
//        - после каждого applyFilters()
//        - после загрузки данных (onSuccessList)
//        - скрывается в состоянии ошибки (error)
//   4. Русское склонение числительных через helper pluralize().

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
    private data class WagonCategory(
        val displayName: String,
        val prefixes: List<String>
    )

    private val categories = listOf(
        WagonCategory("Крытые",        listOf("10", "11")),
        WagonCategory("Полувагоны",    listOf("12")),
        WagonCategory("Платформы",     listOf("13", "23")),
        WagonCategory("Транспортёры",  listOf("14")),
        WagonCategory("Цистерны",      listOf("15")),
        WagonCategory("Рефрижераторы", listOf("16")),
        WagonCategory("Бункерные",     listOf("17")),
        WagonCategory("Хопперы",       listOf("19", "20", "55")),
        WagonCategory("Самосвалы",     listOf("31"))
    )

    // ── Состояние ────────────────────────────────────────────────────────────
    private lateinit var binding: ActivityWagonsBinding
    private lateinit var presenter: WagonsPresenterImpl
    private lateinit var adapterWagons: AdapterWagons

    /** Префиксы выбранной категории; пустой список = "Все". */
    private var selectedPrefixes: List<String> = emptyList()

    companion object {
        private const val STATE_SELECTED_PREFIXES = "state_selected_prefixes"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWagonsBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

    // ── Поиск + счётчик результатов ──────────────────────────────────────────
    private fun setupSearch() {
        with(binding.txtInputLayout) {
            // NEW: убиваем бесполезный счётчик символов "0/12"
            isCounterEnabled = false
            // NEW: резервируем место под helperText, чтобы layout не "прыгал"
            isHelperTextEnabled = true
        }
        binding.searchView.doAfterTextChanged { applyFilters() }
    }

    /**
     * NEW: обновляет helperText поля поиска числом найденных моделей.
     * Вызывается после каждого applyFilters() и после загрузки данных.
     */
    private fun updateResultsCounter() {
        val count = adapterWagons.visibleCount
        binding.txtInputLayout.helperText = when (count) {
            0 -> "Ничего не найдено"
            else -> "Найдено: $count ${pluralize(count, "модель", "модели", "моделей")}"
        }
    }

    /**
     * NEW: русское склонение числительных.
     * pluralize(1,  "модель", "модели", "моделей") → "модель"
     * pluralize(3,  …) → "модели"
     * pluralize(12, …) → "моделей"
     */
    private fun pluralize(n: Int, one: String, few: String, many: String): String {
        val mod100 = n % 100
        val mod10 = n % 10
        return when {
            mod100 in 11..14 -> many
            mod10 == 1 -> one
            mod10 in 2..4 -> few
            else -> many
        }
    }

    // ── Чипы категорий ───────────────────────────────────────────────────────
    private fun buildChips(wagons: List<Wagons>) {
        val chipGroup = binding.chipGroup
        chipGroup.removeAllViews()
        chipGroup.isSingleSelection = true
        chipGroup.isSelectionRequired = true

        val restoredCategory = categories.firstOrNull {
            it.prefixes == selectedPrefixes
        }

        chipGroup.addView(
            makeChip(
                label = "Все",
                selected = restoredCategory == null
            ) {
                selectedPrefixes = emptyList()
                applyFilters()
            }
        )

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

    private fun extractPrefix(model: String): String =
        model.substringBefore("-").take(2)

    // ── Применение фильтров ──────────────────────────────────────────────────
    private fun applyFilters() {
        val query = binding.searchView.text?.toString().orEmpty()
        adapterWagons.applyFilters(query = query, prefixes = selectedPrefixes)
        binding.recyclerView.scrollToPosition(0)
        updateResultsCounter() // NEW
    }

    // ── WagonsContract.View ──────────────────────────────────────────────────
    override fun onSuccessList(wagons: ArrayList<Wagons>) {
        adapterWagons = AdapterWagons(wagons, this)
        binding.recyclerView.adapter = adapterWagons
        buildChips(wagons)

        if (selectedPrefixes.isNotEmpty() ||
            !binding.searchView.text.isNullOrEmpty()
        ) {
            applyFilters()
        } else {
            updateResultsCounter() // NEW: показываем счётчик сразу после загрузки
        }
    }

    override fun error(errMessage: String) {
        binding.layoutNotConnection.visibility = View.VISIBLE
        binding.txtInputLayout.visibility = View.GONE
        binding.chipScrollView.visibility = View.GONE
        // NEW: скрываем счётчик, пока нет данных
        binding.txtInputLayout.helperText = null

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