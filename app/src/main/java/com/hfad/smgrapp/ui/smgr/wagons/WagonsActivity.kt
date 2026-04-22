package com.hfad.smgrapp.ui.smgr.wagons

// REDESIGN v4 — WagonsActivity.kt
//
// История изменений:
//   v2:
//     • Чипы строились по полю `rod` и показывали числа "10", "11"…
//       Заменено на человекочитаемые категории (Крытые, Полувагоны, …)
//       с маппингом "префикс модели → категория".
//     • Категории вынесены в один справочник (CATEGORIES).
//     • Показываются только категории, реально присутствующие в данных.
//     • ChipGroup: single-select + selection required.
//     • Выбранный фильтр сохраняется через onSaveInstanceState.
//     • После смены фильтра список скроллится наверх.
//     • TextWatcher → doAfterTextChanged.
//
//   v3:
//     • Отключён бесполезный счётчик символов "0/12" (isCounterEnabled=false).
//     • helperText теперь показывает число найденных моделей с правильным
//       русским склонением: "Найдено: 1 модель / 2 модели / 5 моделей",
//       "Ничего не найдено" при пустом результате.
//
//   v4 (текущая):
//     • Убран счётчик в подписи чипа: "Крытые · 12" → "Крытые"
//       (количество и так видно по helperText после выбора).
//     • Чипы инфлейтятся из шаблона R.layout.chip_filter —
//       это надёжно применяет стиль SMGR.Chip.Filter и все ColorStateList.
//     • Удалено программное chipCornerRadius — теперь в стиле.

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
        WagonCategory("Крытые",        listOf("10", "11", "17Т", "8Т", "Р-")),
        WagonCategory("Полувагоны",    listOf("12", "17")),
        WagonCategory("Платформы",     listOf("13", "23", "903", "ПМ")),
        WagonCategory("Транспортёры",  listOf("14", "ТМ")),
        WagonCategory("Цистерны",      listOf("15", "8Г", "90", "Ж", "91")),
        WagonCategory("Рефрижераторы", listOf("15Т", "16", "МК", "ТН", "ЦБ")),
        WagonCategory("Бункерные",     listOf("17")),
        WagonCategory("Хопперы",       listOf("19", "20", "55", "ВПМ")),
        WagonCategory("Самосвалы",     listOf("31", "4ВС"))
    )

    // ── Состояние ────────────────────────────────────────────────────────────
    private lateinit var binding: ActivityWagonsBinding
    private lateinit var presenter: WagonsPresenterImpl
    private lateinit var adapterWagons: AdapterWagons

    /** Префиксы выбранной категории; пустой список = чип "Все". */
    private var selectedPrefixes: List<String> = emptyList()

    companion object {
        private const val STATE_SELECTED_PREFIXES = "state_selected_prefixes"
    }

    // ── Жизненный цикл ───────────────────────────────────────────────────────
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWagonsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Восстанавливаем фильтр после поворота экрана
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
            startActivity(
                Intent(this@WagonsActivity, FavouriteWagonsActivity::class.java)
            )
        }
    }

    // ── Поиск + счётчик результатов ──────────────────────────────────────────
    private fun setupSearch() {
        with(binding.txtInputLayout) {
            // Убиваем бесполезный счётчик символов "0/12"
//            isCounterEnabled = false
            // Резервируем место под helperText, чтобы layout не "прыгал"
            isHelperTextEnabled = true
        }
        binding.searchView.doAfterTextChanged { applyFilters() }
    }

    /**
     * Обновляет helperText поля поиска числом найденных моделей.
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
     * Русское склонение числительных.
     *   pluralize(1,  "модель", "модели", "моделей") → "модель"
     *   pluralize(3,  …) → "модели"
     *   pluralize(12, …) → "моделей"
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

        // Какой чип должен быть выбран при открытии (учёт восстановленного стейта)
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

        // Строим чипы только для категорий, реально представленных в данных
        val presentPrefixes = wagons.mapTo(HashSet()) { extractPrefix(it.model) }
        categories
            .filter { cat -> cat.prefixes.any { it in presentPrefixes } }
            .forEach { category ->
                // v4: без счётчика "· count" — только имя категории
                chipGroup.addView(
                    makeChip(
                        label = category.displayName,
                        selected = category == restoredCategory
                    ) {
                        selectedPrefixes = category.prefixes
                        applyFilters()
                    }
                )
            }
    }

    /**
     * v4: инфлейтим чип из шаблона R.layout.chip_filter, а не создаём
     * конструктором Chip(context). Конструктор не всегда корректно
     * подхватывает атрибуты Material3-стиля (особенно ColorStateList
     * для chipBackgroundColor / chipStrokeColor).
     */
    private fun makeChip(
        label: String,
        selected: Boolean = false,
        onClick: () -> Unit
    ): Chip {
        val chip = layoutInflater.inflate(
            R.layout.chip_filter,
            binding.chipGroup,
            false
        ) as Chip
        chip.text = label
        chip.isChecked = selected
        chip.setOnClickListener { onClick() }
        return chip
    }

    /** Первые 2 символа до "-": "11-066-04" → "11". */
    private fun extractPrefix(model: String): String =
        model.substringBefore("-").take(2)

    // ── Применение фильтров ──────────────────────────────────────────────────
    private fun applyFilters() {
        val query = binding.searchView.text?.toString().orEmpty()
        adapterWagons.applyFilters(query = query, prefixes = selectedPrefixes)
        // Возвращаем пользователя наверх списка, чтобы он видел свежие результаты
        binding.recyclerView.scrollToPosition(0)
        updateResultsCounter()
    }

    // ── WagonsContract.View ──────────────────────────────────────────────────
    override fun onSuccessList(wagons: ArrayList<Wagons>) {
        adapterWagons = AdapterWagons(wagons, this)
        binding.recyclerView.adapter = adapterWagons
        buildChips(wagons)

        // Применяем восстановленный фильтр, либо сразу показываем общий счётчик
        if (selectedPrefixes.isNotEmpty() ||
            !binding.searchView.text.isNullOrEmpty()
        ) {
            applyFilters()
        } else {
            updateResultsCounter()
        }
    }

    override fun error(errMessage: String) {
        binding.layoutNotConnection.visibility = View.VISIBLE
        binding.txtInputLayout.visibility = View.GONE
        binding.chipScrollView.visibility = View.GONE
        // Скрываем счётчик, пока нет данных
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