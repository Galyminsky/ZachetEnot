# Журнал проекта ZachetEnot

## 1. О проекте
Бесплатное офлайн Android-приложение-тренажёр для школьников Казахстана: закрепление знаний, полученных в школе (не учебник, не дневник). MVP: 3 класс, русский язык; источник содержания — PDF учебника, который предоставит владелец. Тест: 15 вопросов из банка, порог 90% (14/15), попытки не ограничены, таймер 20 минут без паузы, правильные ответы не показываются, при ошибке показывается короткое объяснение правила. Прогресс локально, история попыток не хранится. Без регистрации, сервера, рекламы и платных функций. Стек: Kotlin, Jetpack Compose, minSdk 29, портретная ориентация, смартфоны и планшеты; интерфейс на русском (затем казахский); макеты в Figma — источник визуальных решений, самостоятельно экраны не придумывать. Пакет: com.galyaminsky.zachetenot. Исходные документы «Концепция» и «Правила для разработки» хранятся у владельца.

## 2. Роли и процесс
Владелец (Юрик): принимает решения, делает макеты в Figma, передаёт задания, запускает сборку и тесты в Android Studio, делает commit/push. Claude: анализ, проверка необходимости изменений, формулирует короткие задания для Codex, принимает отчёты. Codex: выполняет точечные задания. Цикл: анализ > короткое задание > отчёт > проверка (сборка и тесты у владельца) > следующий шаг. Codex в своей среде не имеет сети и не может надёжно запускать Gradle.

## 3. Этапы
Этап 1 — логика без UI. Этап 2A — дизайн-основа: токены из Figma, ресурсы, компоненты UI-Kit, строки в strings.xml. Этап 2B — экраны и интеграция: ViewModel, навигация, DI, настройки (звук, сброс прогресса). Этап 3 — полировка и тестирование.

## 4. Текущее состояние
Этап 1 (логика) закрыт. Этап 2A (токены и компоненты по UI Kit) закрыт. Реализованы статические экраны по макетам Figma с превью: выбор класса, выбор предмета и темы пока представлены только компонентами; подготовка к тесту; тест в 12 состояниях; результат; разбор ошибок. Экраны не подключены к данным и логике. По решению владельца макеты реализованы как есть, концепция и логика не менялись. Следующий этап — адаптация под концепцию и логику, затем сборка экранов с данными.

## 5. Карта проекта
- `MainActivity.kt` — точка входа и текущий шаблонный Compose-экран.
- `data/AssetContentRepository.kt` — чтение JSON-контента из assets и передача его парсеру.
- `data/ContentParser.kt` — разбор JSON через сериализуемые DTO и создание доменной модели контента.
- `data/DataStoreProgressRepository.kt` — локальное чтение, отметка и сброс пройденных тем.
- `data/ProgressDataStore.kt` — единый Context-delegate Preferences DataStore прогресса.
- `data/SystemTimeSource.kt` — монотонное системное время через `SystemClock.elapsedRealtime()`.
- `domain/content/ContentRepository.kt` — контракт загрузки контента.
- `domain/model/ContentBundle.kt` — темы, правила, вопросы и вспомогательные фильтры.
- `domain/model/Option.kt` — вариант ответа.
- `domain/model/Question.kt` — вопрос, варианты и проверка корректности модели.
- `domain/model/Rule.kt` — правило с объяснением.
- `domain/model/TestConfig.kt` — константы теста, таймера и предупреждений.
- `domain/model/TestResult.kt` — итог попытки и вычисляемые показатели.
- `domain/model/Topic.kt` — тема с идентификатором, названием, порядком и описанием.
- `domain/catalog` — каталог доступных классов и предметов без пользовательских текстов.
- `domain/progress/ProgressCalculator.kt` — вычисление статусов тем и прогресса предмета.
- `domain/progress/ProgressRepository.kt` — контракт сохранения множества пройденных тем.
- `domain/progress/TopicProgress.kt` — статусы темы и агрегированный прогресс.
- `domain/test/BackgroundGuard.kt` — определение слишком долгого отсутствия приложения.
- `domain/test/TestEngine.kt` — случайный выбор вопросов и перемешивание вариантов.
- `domain/test/TestSession.kt` — состояние и управление одной попыткой.
- `domain/test/TestTimer.kt` — отсчёт времени и выдача пороговых предупреждений.
- `domain/test/TimeSource.kt` — контракт монотонного источника времени.
- `ui/theme/Color.kt` — цвета шаблонной Compose-темы.
- `ui/theme/Theme.kt` — Compose Material theme.
- `ui/theme/Type.kt` — типографика Compose-темы.
- `assets/content/russian_grade3.json` — временный набор тем, правил и вопросов.
- `AndroidManifest.xml` — объявление приложения и Activity.
- `keepRules/rules.keep` — правила сохранения классов при оптимизации.
- `res/drawable` — XML-ресурсы фона и переднего плана иконки.
- `res/mipmap-*` — варианты иконки приложения для плотностей экрана.
- `res/values` — цвета, строки и XML-тема Android.
- `res/xml` — правила резервного копирования и извлечения данных.

### `app/src/test`
- `ExampleUnitTest.kt` — исходный шаблонный unit-тест.
- `data/ContentParserTest.kt` — разбор JSON-контента и ссылки вопросов на правила и темы.
- `data/DataStoreProgressRepositoryTest.kt` — тесты локального хранения прогресса.
- `domain/model/ContentBundleTest.kt` — фильтрация вопросов и поиск правила в наборе контента.
- `domain/model/QuestionTest.kt` — проверки инвариантов вопроса.
- `domain/progress/ProgressCalculatorTest.kt` — статусы тем и проценты прогресса.
- `domain/test/BackgroundGuardTest.kt` — порог времени отсутствия приложения.
- `domain/test/TestEngineTest.kt` — выбор вопросов и перемешивание вариантов.
- `domain/test/TestSessionTest.kt` — ответы, переходы и результат попытки.
- `domain/test/TestTimerTest.kt` — отсчёт времени и выдача предупреждений.

## 6. Принятые решения
(1) Одна модель Question для всех форматов (выбор из вариантов); ответы сопоставляются по id варианта, не по индексу. (2) Объяснение привязано к Rule; ошибки по правилам — TestResult.mistakesByRule. (3) Порог целочисленный: correct*100 >= 90*total. (4) TestSession (состояние попытки) отделён от TestEngine (выбор и перемешивание); ответ и переход разделены (submitAnswer, moveToNext), чтобы показывать обратную связь; finish() можно вызвать в любой момент, неотвеченные вопросы считаются ошибками. (5) Таймер хранит дедлайн на абсолютном времени (TimeSource, SystemClock.elapsedRealtime); предупреждения за 5 и 1 минуту выдаются по одному разу, при прыжке времени выдаётся только наименьшее достигнутое. (6) Сворачивание: таймер идёт; если вне приложения строго больше 60 секунд, попытка сбрасывается (BackgroundGuard); закрытие приложения = сброс попытки (реализация в 2B). Значение 60 секунд — рабочее допущение, проверить на устройстве. (7) Прогресс: хранится только множество id пройденных тем (DataStore Preferences); статусы вычисляются (ProgressCalculator); первая тема всегда доступна; id тем глобально уникальны. (8) Room и DI-фреймворки не используются, зависимости собираются вручную. (9) Контент: JSON в assets (content/russian_grade3.json), разбор через DTO на kotlinx.serialization в data-слое; domain-модели без аннотаций; ContentBundle в domain.model. (10) Версии: Kotlin 2.2.10, AGP 9.5.0, Gradle 9.6.0, Compose BOM 2026.02.01, kotlinx.serialization 1.9.0, DataStore 1.2.1, coroutines 1.11.0. (11) Отложено в 2B: TestViewModel, создание экземпляров репозиториев, навигация, настройка звука (в том же DataStore), экран настроек со сбросом прогресса. (12) Текущий JSON-контент временный (1 тема, 3 правила, 24 вопроса), будет заменён контентом по PDF учебника. (13) Цветовые токены взяты из Figma UI-Kit «Шаг» (fileKey wRxJe6CeQZotDO4FrvYq2w; кит node 34:2494; экраны: выбор класса 34:2853, выбор предмета 34:2964, темы по предмету 34:3030). Используется одна светлая тема, dynamic color отключён; цвет ошибки в ките отсутствует. (14) Шкала отступов — 4/8/12/16/24/32/48; радиусы — 8/12/16/24/капсула; контуры — 1 px обычный, 2 px выбранный (карточка темы в ките 1.5 px приводится к 2); тени не используются. Экран 402, поля 24, высота кнопки 56 (радиус 16), зона нажатия не меньше 48. Иллюстрации будут векторными ресурсами, не токенами. (18) Контур карточек рисуется поверх без изменения раскладки; иллюстрация класса прижата к BottomEnd с отступами 8 и 6 dp; состояние Default карточки предмета выведено по аналогии с карточкой класса. В Type.kt заданы displayLarge 46/48 sp с letterSpacing -2 sp, titleSmall 14/17 sp Medium и bodySmall 12/15 sp. (19) Состояния PASSED и LOCKED карточки темы выведены по аналогии (в ките есть только AVAILABLE); контур выбранной темы — 2 px вместо 1.5. Навигационная панель без разделителя и тени учитывает системные insets через navigationBarsPadding; NavItem универсальный, набор разделов задаётся на экране. (20) Экраны выбора класса и предмета показываются только при первом запуске; далее открываются «Темы», класс и предмет меняются через настройки. Нижняя навигация в MVP не используется, настройки доступны кнопкой в шапке. Пройденные темы можно открывать повторно со стрелкой; тексты карточки прогресса и подписей для состояний вне макета готовит Claude и согласует владелец. (21) id тем стабильны (`topic_speech_text` … `topic_adjective`), порядок соответствует макету. Временный банк содержит 24 одинаковых по смыслу вопроса для каждой темы (копии с id `<topicId>__<id>`) и подлежит замене контентом по PDF учебника. У `Topic.description` значение по умолчанию `""`. Каталог классов и предметов расположен в domain без текстов; тексты хранятся в strings.xml.

(22) Макеты передаются экспортом PNG @2x, поскольку доступ к Figma MCP ограничен тарифом Starter; точные размеры компонентов берутся из метаданных Figma. (23) Предложенная ранее команда D1 (типы заданий в домене) не выполнялась и отложена до этапа адаптации. (24) Тексты интерфейса вынесены в strings.xml; UI-модели экранов содержат готовые строки, маппинг из домена выполняется в адаптации.

## 7. Открытые вопросы и риски
- Банк вопросов реальной темы не меньше 30–45 заданий (при 16–20 тесты почти не различаются между попытками).
- Правильный ответ нигде не показывается: педагогическую эффективность проверить на 3–5 детях.
- Google Play: требования для детских приложений (Families), политика конфиденциальности.
- Права на учебник как источник содержания.
- Auto Backup: решить, должен ли прогресс восстанавливаться на новом устройстве (этап 3).
- Модель Question проверить на 3–5 реальных вопросах из учебника.
- Нет макетов для 2B: выбор класса и предмета (если остаётся в MVP), итоги теста (два состояния и ошибки по правилам), диалог выхода, обратная связь после ответа, предупреждения таймера, настройки.
- Не подтверждён запуск unit-тестов после заданий 1.5–1.6 (включая тест DataStore).
- Нет компонентов теста: вариант ответа, таймер, диалог, панель объяснения и итоги.
- Цвет ошибки в UI-Kit отсутствует.
- Контраст #FFB66C и #74788F требует проверки.
- Пятый стиль текста не заведён.
- Требуют решения название приложения и радиусы.
- Размеры текста вне шкалы: 11, 13, 14, 15, 23, 46 и 16/19 SemiBold; 11 px мелко для детей.
- Контраст #989DAF и #74788F требует проверки.
- Вкладка «Прогресс» отсутствует в концепции.
- Радиус 11 у логотипа вне шкалы.
- Название приложения (в UI Kit — «Шаг») и поддержка планшетов требуют решения.
- Размеры текста вне шкалы в UI Kit: 11, 13, 14, 15, 23, 46 и 16/19 SemiBold; 11 px мелко для детей 7–11 лет.
- Контраст #989DAF (около 2.7:1 на белом) и #74788F (около 4.3:1) требует проверки.
- Планшеты: колонка до 600 dp или отдельные макеты.
- Лицензия Inter (SIL OFL) требует приложить текст лицензии.
- Имя «Шаг / Основной текст» в Figma дублируется (цвет и текстовый стиль).
- Нет цвета и компонентов для неверного ответа, варианта ответа, таймера, диалога, панели объяснения и итогов.
- Нужно приложить тексты лицензий Lucide (ISC) и Inter (SIL OFL) в приложении.
- Для иконок 20–21 dp штрих рассчитан пропорционально (около 0.85 dp вместо 1); требуется проверка на устройстве.
- 11 px мелко для детей 7–11 лет.
- Соответствие иллюстраций классам (1, 2, 4) определить по экрану выбора класса; плитка предмета Default; текст «Aa» плитки предмета захардкожен в вызове, затем брать из контента.
- Нарисовать PASSED и LOCKED карточки темы в Figma; вкладка «Прогресс» вне концепции; UI Kit закрыт, кроме Switch (экран настроек, этап 2B).

### План адаптации и сборки экранов
A1. Домен: добавить типы заданий (выбор, ввод текста, последовательность, сопоставление), поле объяснения у вопроса и подробный результат (ответы ученика, правильные ответы, затраченное время).
A2. Контент: определить формат JSON для четырёх типов заданий, добавить примеры каждого типа и подготовить банк не менее 45 вопросов на тему.
A3. Интеграция: определить зависимости и навигацию, хранить выбор класса и предмета; показывать экраны выбора только при первом запуске.
A4. Сборка экранов с данными и ViewModel по цепочке: выбор класса, выбор предмета, темы, подготовка, тест, результат, разбор.
A5. Реализовать отсутствующие элементы: диалог выхода, экран «Время вышло», предупреждения таймера, действие последнего вопроса «Завершить тест», настройки звука и сброса прогресса, а также отдельные Compose-экраны выбора класса, предмета и тем (компоненты есть, экранов нет).

Расхождения макетов с концепцией и логикой для разрешения на этапе адаптации: правильный ответ в разборе ошибок; кнопка «Проверить»; «Затрачено времени»; объяснение у вопроса; вкладка «Прогресс» (не используется).

- Название приложения: в макетах «шаг».
- Планшеты (колонка до 600 dp или отдельные макеты).

## 8. Формат записи
### З-<номер> — <короткое название> (<дата>)
- **Команда:** дословный текст задания в блоке кода.
- **Сделано:** 3–10 строк по сути (решения, нетривиальные детали); код не копировать.
- **Файлы:** созданные / изменённые / перемещённые.
- **Проверка агента:** что запускалось; по умолчанию «сборка и тесты не запускались».
- **Приёмка:** ожидает | принято (замечания).

## 9. Журнал

### З-0.1 — проверка и отчёт по проекту (2026-11-01)
- **Команда:**
```text
Задача: только проверка и отчёт. Никакие файлы не изменять, ничего не устанавливать, не выполнять commit/push, эмулятор не запускать.

1. Проверь, что проект открывается и синхронизируется. Выполни сборку debug (./gradlew assembleDebug) и сообщи результат.
2. Сообщи версии: Android Studio (если определимо), Gradle, AGP, Kotlin, Compose BOM / Compose compiler, JDK.
3. Сообщи: applicationId, minSdk, targetSdk, compileSdk.
4. Покажи дерево папок и файлов в app/src/main (до 3 уровней глубины) и перечень файлов в app/src/test и app/src/androidTest.
5. Перечисли все подключённые зависимости из build.gradle.kts (app и project) и файла libs.versions.toml, если он есть.
6. Укажи, используется ли Jetpack Compose, есть ли Theme.kt, есть ли какой-либо код помимо шаблона по умолчанию.
7. Отдельным списком сообщи проблемы или предупреждения (устаревшие зависимости, warnings сборки), но не исправляй их.

Формат ответа: краткий отчёт по пунктам 1–7. Без предложений по рефакторингу.
```
- **Сделано:** Изучены Gradle-файлы, версии, SDK-параметры, зависимости, исходное дерево и шаблонные исходники.
  Сборка в агентской среде сначала не смогла создать Gradle lock-файл, затем остановилась при скачивании Gradle из-за отсутствия сетевого доступа.
  Владелец позже подтвердил успешную сборку проекта.
- **Файлы:** не изменялись.
- **Проверка агента:** `assembleDebug` не завершилась из-за ограничений среды; владелец позже подтвердил сборку.
- **Приёмка:** принято.

### З-1.1 — модели предметной области (2026-11-01)
- **Команда:**
```text
Задача: создать модели предметной области. Только чистый Kotlin, без Android-импортов и без новых зависимостей. Других файлов не изменять, commit/push не выполнять.

Пакет: com.galyaminsky.zachetenot.domain.model

Создай файлы:

1. Topic.kt
data class Topic(val id: String, val title: String, val order: Int)

2. Rule.kt
data class Rule(val id: String, val title: String, val explanation: String)
explanation — короткое объяснение правила простыми словами. Оно не должно содержать правильный ответ конкретного вопроса.

3. Option.kt
data class Option(val id: String, val text: String)

4. Question.kt
data class Question(
    val id: String,
    val topicId: String,
    val ruleId: String,
    val text: String,
    val options: List<Option>,
    val correctOptionId: String
)
В блоке init проверь через require: options содержит минимум 2 элемента; id вариантов уникальны; correctOptionId присутствует среди вариантов; text не пустой.

5. TestConfig.kt — object с константами:
QUESTIONS_PER_TEST = 15
PASS_THRESHOLD_PERCENT = 90
TIME_LIMIT_MINUTES = 20
WARNING_MINUTES = listOf(5, 1)

Не создавать: TestSession, TestResult, репозитории, интерфейсы, JSON-загрузку, ViewModel.

Добавь один unit-тест QuestionTest.kt в app/src/test/java/com/galyaminsky/zachetenot/domain/model/: корректный вопрос создаётся; вопрос с одним вариантом, с дублирующимися id вариантов и с несуществующим correctOptionId выбрасывает IllegalArgumentException.

Формат отчёта: список созданных файлов и текст итоговых классов.
```
- **Сделано:** Созданы пять чистых Kotlin-моделей в `domain.model`.
  В `Question.init` добавлены проверки минимального числа вариантов, уникальности их ID, наличия правильного варианта и непустого текста.
  Добавлен unit-тест с четырьмя сценариями.
- **Файлы:** созданы `Topic.kt`, `Rule.kt`, `Option.kt`, `Question.kt`, `TestConfig.kt`, `QuestionTest.kt`.
- **Проверка агента:** сборка и тесты не запускались.
- **Приёмка:** принято по коду; замечание: проверка `text.isNotEmpty()` заменена на `isNotBlank()` в З-1.1а.

### З-1.1а — точечная правка и вывод файлов (2026-11-01)
- **Команда:**
```text
Задача: одна точечная правка и вывод содержимого файлов. Больше ничего не менять, commit/push не выполнять, сборку и тесты не запускать.

1. В файле Question.kt замени проверку text.isNotEmpty() на text.isNotBlank() (сообщение об ошибке оставить прежним).

2. Убедись, что в каждом из шести файлов (Topic.kt, Rule.kt, Option.kt, Question.kt, TestConfig.kt и QuestionTest.kt) первой строкой указан корректный package:
   - пять моделей: com.galyaminsky.zachetenot.domain.model
   - QuestionTest.kt: com.galyaminsky.zachetenot.domain.model
   Если package отсутствует или неверный, исправь только его.

3. Проверь, что в QuestionTest.kt есть ровно 4 теста: корректный вопрос; один вариант; дублирующиеся id вариантов; несуществующий correctOptionId. Если чего-то не хватает, добавь недостающий тест. Лишние тесты не добавлять.

4. Проверь, что в тесте с пробельным текстом нет нужды: новый тест на пустой или пробельный text не добавлять.

Формат отчёта: полный текст файлов Question.kt и QuestionTest.kt (с package и import), и список файлов, которые были изменены.
```
- **Сделано:** Изменена проверка текста на `isNotBlank()` с прежним сообщением ошибки.
  Проверено, что во всех шести файлах корректный package и в `QuestionTest.kt` ровно четыре теста.
  Тест на пустой или пробельный текст не добавлялся.
- **Файлы:** изменён `Question.kt`.
- **Проверка агента:** сборка и тесты не запускались.
- **Приёмка:** принято.

### З-1.2 — временный контент и его разбор (2026-11-01)
- **Команда:**
```text
Задача: временный контент и его разбор. Без изменений вне указанного. Commit/push не выполнять, сборку и тесты не запускать.

1. Зависимость. Добавь kotlinx-serialization-json и плагин org.jetbrains.kotlin.plugin.serialization (версия плагина равна версии Kotlin, 2.2.10) через libs.versions.toml. Для JSON-библиотеки выбери актуальную стабильную версию, совместимую с Kotlin 2.2.10. Других зависимостей не добавлять.

2. Файл app/src/main/assets/content/russian_grade3.json. Структура:
{
  "topics": [{"id","title","order"}],
  "rules": [{"id","title","explanation"}],
  "questions": [{"id","topicId","ruleId","text","options":[{"id","text"}],"correctOptionId"}]
}
Содержимое: 1 тема «Безударные гласные в корне слова» (русский язык, 3 класс), 3 правила, 24 вопроса (по 8 на правило). Это временный контент для проверки логики. Требования: у каждого вопроса 3-4 варианта, ровно один правильный, формулировка однозначная и понятная ребёнку 8-9 лет; explanation правила не раскрывает ответ конкретного вопроса; правильные варианты распределены по разным позициям; все id уникальны. Используй только слова и правила, в корректности которых уверен на 100%.

3. Пакет com.galyaminsky.zachetenot.data:
   - ContentBundle.kt: data class ContentBundle(val topics: List<Topic>, val rules: List<Rule>, val questions: List<Question>)
   - ContentParser.kt: object ContentParser с функцией parse(json: String): ContentBundle. Для разбора используй приватные @Serializable DTO внутри этого файла, чтобы domain-модели остались без аннотаций сериализации. Результат собирается в domain-модели, их проверки в init должны срабатывать.
   - Парсер дополнительно проверяет через require: ruleId и topicId каждого вопроса существуют в соответствующих списках; id вопросов уникальны.

4. Unit-тесты ContentParserTest.kt в app/src/test/java/com/galyaminsky/zachetenot/data/:
   - разбор небольшого JSON-литерала внутри теста;
   - вопрос с несуществующим ruleId выбрасывает IllegalArgumentException;
   - реальный файл (File("src/main/assets/content/russian_grade3.json")): 1 тема, 3 правила, 24 вопроса, у каждого правила не менее 8 вопросов.

Не создавать: QuestionSource, загрузку через Context, ViewModel, репозитории.

Формат отчёта: список созданных и изменённых файлов, текст ContentParser.kt и ContentBundle.kt, содержимое изменений в libs.versions.toml и build.gradle.kts, полный JSON-файл.
```
- **Сделано:** Добавлены kotlinx.serialization, DTO-парсер, тесты парсера и временный JSON с 24 вопросами.
  В первой версии JSON обнаружены ошибки: дублированный ключ `id`, вопрос со словом «снеж_к», подсказки-проверочные слова в тексте и нерелевантные варианты-ловушки.
- **Файлы:** созданы JSON, `ContentBundle.kt`, `ContentParser.kt`, `ContentParserTest.kt`; изменены каталоги версий и корневой/модульный `build.gradle.kts`.
- **Проверка агента:** сборка и тесты не запускались.
- **Приёмка:** не принято (замечания исправлены в З-1.2а).

### З-1.2а — исправление JSON-контента (2026-11-01)
- **Команда:**
```text
Задача: исправить ошибки в русском JSON-контенте и мелкие замечания в ContentParser. Другие файлы не менять. Commit/push не выполнять, сборку и тесты не запускать.

Файл 1: app/src/main/assets/content/russian_grade3.json

1. Исправь синтаксис: в вопросе q_dictionary_01 ключ "id":"id": должен быть "id":. После правки проверь, что весь файл является валидным JSON (например, python -m json.tool или другой доступный способ) и сообщи результат проверки.

2. Из поля text всех вопросов правил rule_change_form и rule_related_word убери проверочное слово. Новый формат text: «Вставь пропущенную букву: <слово>». Слова, варианты ответов и правильные ответы остаются прежними, кроме q_related_01 (см. п. 3).
   rule_change_form: в_да, л_са, г_ра, р_ка, н_га, к_за, ст_на, тр_ва (в порядке q_form_01 … q_form_08).
   rule_related_word: сн_жок, л_сной, м_рской, цв_ток, з_лотой, гр_бной, с_довый, к_рмушка (в порядке q_related_01 … q_related_08).

3. q_related_01: слово «сн_жок», варианты и правильный ответ без изменений (и, е, я; правильный ответ е).

4. Замени варианты-ловушки только на ошибки в гласных:
   q_dictionary_05, вариант option_3: «биреза» > «бераза»
   q_dictionary_06, вариант option_1: «йаблоко» > «иблоко»
   q_dictionary_08, вариант option_1: «карандащ» > «карондаш»
   Остальные варианты и correctOptionId не менять.

5. Состав файла после правок: 1 тема, 3 правила, 24 вопроса, id не менять, у каждого правила по 8 вопросов. В тексте вопросов не должно остаться слов «Проверочное слово».

Файл 2: app/src/main/java/com/galyaminsky/zachetenot/data/ContentParser.kt

6. Удали импорт kotlinx.serialization.decodeFromString.
7. Переименуй приватное поле json в jsonFormat, чтобы оно не затенялось параметром функции parse(json: String). Сигнатуру parse(json: String) не менять. Больше ничего в файле не менять.

Формат отчёта: список изменённых файлов, результат проверки валидности JSON, полный текст ContentParserTest.kt, полный текст итогового ContentParser.kt (только блок кода функции parse и поля jsonFormat достаточно).
```
- **Сделано:** Исправлен синтаксис JSON, убраны проверочные слова из текстов вопросов и заменены три указанных варианта-ловушки.
  Проверено, что файл разбирается через `ConvertFrom-Json`, содержит 1 тему, 3 правила и 24 вопроса, по 8 вопросов на правило.
  В `ContentParser.kt` удалён указанный импорт и поле переименовано в `jsonFormat`.
- **Файлы:** изменены `russian_grade3.json` и `ContentParser.kt`.
- **Проверка агента:** JSON проверен PowerShell `ConvertFrom-Json`; сборка и тесты не запускались.
- **Приёмка:** принято; владелец подтвердил сборку.

### З-1.3 — логика одной попытки теста (2026-11-01)
- **Команда:**
```text
Задача: логика одной попытки теста. Только чистый Kotlin, без Android-импортов, корутин и новых зависимостей. Существующие файлы не менять (кроме создания новых). Commit/push не выполнять, сборку и тесты не запускать.

1. Файл domain/model/TestResult.kt (пакет com.galyaminsky.zachetenot.domain.model):
   data class TestResult(
       val totalCount: Int,
       val correctCount: Int,
       val mistakesByRule: Map<String, Int>
   )
   Свойства (вычисляемые, не в конструкторе):
   - incorrectCount = totalCount - correctCount
   - percent = correctCount * 100 / totalCount (целочисленное)
   - passed = correctCount * 100 >= TestConfig.PASS_THRESHOLD_PERCENT * totalCount
   mistakesByRule: ключ ruleId, значение число ошибок по правилу. Порядок по первому появлению ошибки.
   В init: require(totalCount > 0), require(correctCount in 0..totalCount).

2. Файл domain/test/TestSession.kt (пакет com.galyaminsky.zachetenot.domain.test):
   class TestSession(val questions: List<Question>)
   require(questions.isNotEmpty()).
   Состояние: индекс текущего вопроса, map questionId -> isCorrect для отвеченных, флаг isFinished.
   Публичное API:
   - val currentQuestion: Question — текущий вопрос.
   - val currentIndex: Int
   - val isCurrentAnswered: Boolean
   - val isLastQuestion: Boolean
   - val isFinished: Boolean
   - fun submitAnswer(optionId: String): Boolean — фиксирует ответ на текущий вопрос, возвращает true если верно. Окончательный ответ: повторный ответ на тот же вопрос выбрасывает IllegalStateException. Ответ после finish() выбрасывает IllegalStateException. optionId, которого нет среди вариантов текущего вопроса, выбрасывает IllegalArgumentException.
   - fun moveToNext() — переход к следующему вопросу. Разрешён только если текущий отвечен и он не последний; иначе IllegalStateException. Пропуск вопроса невозможен.
   - fun finish(): TestResult — завершает попытку (isFinished = true) и возвращает результат. Неотвеченные вопросы считаются ошибками и попадают в mistakesByRule по своему ruleId. Повторный вызов возвращает тот же результат.
   Правильный ответ наружу не отдавать никаким методом, кроме самого Question.

3. Файл domain/test/TestEngine.kt (тот же пакет):
   class TestEngine(private val random: Random = Random.Default)  // kotlin.random.Random
   fun startSession(bank: List<Question>): TestSession
   - require(bank.size >= TestConfig.QUESTIONS_PER_TEST)
   - выбрать TestConfig.QUESTIONS_PER_TEST случайных различных вопросов из банка: bank.shuffled(random).take(...)
   - у каждого выбранного вопроса перемешать варианты: question.copy(options = question.options.shuffled(random))
   - вернуть TestSession с этим списком.

4. Unit-тесты в app/src/test/java/com/galyaminsky/zachetenot/domain/test/TestEngineTest.kt и TestSessionTest.kt. В тестах собирай банк из вопросов программно (не читай JSON). Покрыть:
   - startSession: возвращает ровно 15 различных вопросов из банка в 20 вопросов; банк из 14 вопросов выбрасывает IllegalArgumentException; одинаковый Random(seed) даёт одинаковый порядок вопросов; множество вариантов каждого вопроса после перемешивания не изменилось.
   - submitAnswer: верный возвращает true, неверный false; повторный ответ на тот же вопрос выбрасывает IllegalStateException; неизвестный optionId выбрасывает IllegalArgumentException; ответ после finish() выбрасывает IllegalStateException.
   - moveToNext: без ответа выбрасывает IllegalStateException; на последнем вопросе выбрасывает IllegalStateException.
   - finish: 15 верных = 100%, passed; 14 верных = passed; 13 верных = не passed; тест, в котором отвечено 10 вопросов верно, остальные пропущены: correctCount 10, остальные 5 считаются ошибками и их ruleId есть в mistakesByRule; повторный вызов finish() возвращает равный результат.

Не создавать: таймер, ViewModel, репозитории, интерфейсы, enum-классы состояний, любые Android-классы.

Формат отчёта: список созданных файлов, полный текст TestSession.kt, TestEngine.kt и TestResult.kt, перечень названий тестов. Если в ходе работы обнаружишь проблему вне задания, сообщи о ней и не исправляй.
```
- **Сделано:** Добавлены модель результата, управление одной попыткой и движок выбора/перемешивания.
  Неотвеченные вопросы учитываются как ошибки; повторный вызов завершения возвращает сохранённый результат.
  Добавлены unit-тесты движка и сессии с программно созданным банком.
- **Файлы:** созданы `TestResult.kt`, `TestSession.kt`, `TestEngine.kt`, `TestEngineTest.kt`, `TestSessionTest.kt`.
- **Проверка агента:** сборка и тесты не запускались.
- **Приёмка:** принято по коду.

### З-1.4 — таймер и защита от ухода в фон (2026-11-01)
- **Команда:**
```text
Задача: таймер теста и защита от длительного ухода в фон. Без корутин, ViewModel и UI. Commit/push не выполнять, сборку и тесты не запускать.

1. Изменить TestConfig.kt (domain/model): добавить одну константу
   const val BACKGROUND_RESET_SECONDS = 60
   Остальное в файле не менять.

2. Файл domain/test/TimeSource.kt (пакет com.galyaminsky.zachetenot.domain.test):
   interface TimeSource { fun nowMs(): Long }
   Комментарий: монотонное время в миллисекундах.

3. Файл data/SystemTimeSource.kt (пакет com.galyaminsky.zachetenot.data):
   class SystemTimeSource : TimeSource { override fun nowMs(): Long = SystemClock.elapsedRealtime() }
   (android.os.SystemClock; не nanoTime, чтобы время считалось и при выключенном экране.)

4. Файл domain/test/TestTimer.kt:
   class TestTimer(
       private val timeSource: TimeSource,
       private val limitMs: Long = TestConfig.TIME_LIMIT_MINUTES * 60_000L
   )
   - fun start(): запоминает время старта; повторный вызов выбрасывает IllegalStateException.
   - fun remainingMs(): Long = max(0, limitMs - (now - startedAt)); до start() выбрасывает IllegalStateException.
   - fun isExpired(): Boolean = remainingMs() == 0L
   - fun consumeWarning(): Int? — возвращает число минут (из TestConfig.WARNING_MINUTES, значения 5 и 1) для ещё не выданного порога, который уже достигнут (оставшееся время <= порог минут). Каждый порог выдаётся не более одного раза. Если достигнуто несколько невыданных порогов одновременно, вернуть наименьший, а все достигнутые пометить выданными. Иначе null. До start() выбрасывает IllegalStateException.

5. Файл domain/test/BackgroundGuard.kt:
   class BackgroundGuard(
       private val timeSource: TimeSource,
       private val maxAbsenceMs: Long = TestConfig.BACKGROUND_RESET_SECONDS * 1000L
   )
   - fun onBackground(): запоминает время ухода в фон.
   - fun onForeground(): Boolean — возвращает true, если отсутствие было строго больше maxAbsenceMs (попытку нужно сбросить), иначе false; запись об уходе очищается. Если onBackground() не вызывали, возвращает false.

6. Unit-тесты в app/src/test/java/com/galyaminsky/zachetenot/domain/test/: TestTimerTest.kt и BackgroundGuardTest.kt. В тестах используй FakeTimeSource (класс внутри тестового файла или отдельный тестовый файл) с изменяемым временем.
   TestTimer: сразу после start() осталось 20 минут; через 5 минут осталось 15; ровно через 20 минут isExpired = true, remaining = 0; через 25 минут remaining = 0 (не отрицательное); за секунду до 5 оставшихся минут consumeWarning() = null; при 5 оставшихся минутах = 5, повторный вызов = null; при 1 оставшейся минуте = 1, повторный = null; прыжок сразу с 10 минут прошедшего времени до 19 минут 30 секунд: consumeWarning() = 1, затем null (предупреждение «5» уже не придёт); consumeWarning() и remainingMs() до start() выбрасывают IllegalStateException; повторный start() выбрасывает IllegalStateException.
   BackgroundGuard: отсутствие 30 секунд = false; 61 секунда = true; ровно 60 секунд = false; onForeground() без onBackground() = false; повторный onForeground() после первого = false.

Не создавать: корутины, Handler, Flow, ViewModel, lifecycle-наблюдателей.

Формат отчёта: список созданных и изменённых файлов, полный текст TestTimer.kt, BackgroundGuard.kt и TimeSource.kt, названия тестов.
```
- **Сделано:** В `TestConfig` добавлен порог сброса 60 секунд.
  Созданы контракт времени, адаптер `SystemClock.elapsedRealtime`, таймер с предупреждениями и защита от длительного фона.
  Добавлены тесты на граничные интервалы и поведение предупреждений.
- **Файлы:** изменён `TestConfig.kt`; созданы `TimeSource.kt`, `SystemTimeSource.kt`, `TestTimer.kt`, `BackgroundGuard.kt`, `TestTimerTest.kt`, `BackgroundGuardTest.kt`.
- **Проверка агента:** сборка и тесты не запускались; владелец подтвердил запуск проекта, запуск unit-тестов явно не подтверждён.
- **Приёмка:** принято по коду.

### З-1.5 — хранение прогресса и статусы тем (2026-11-01)
- **Команда:**
```text
Задача: локальное хранение прогресса и вычисление статусов тем. Без ViewModel и UI. Commit/push не выполнять, сборку и тесты не запускать.

1. Зависимости (через libs.versions.toml и app/build.gradle.kts). Подбери актуальные стабильные версии по официальным релизам, совместимые с Kotlin 2.2.10, AGP 9.5.0, compileSdk 37; в отчёте укажи выбранные версии и источник:
   - androidx.datastore:datastore-preferences (implementation)
   - org.jetbrains.kotlinx:kotlinx-coroutines-core (implementation)
   - org.jetbrains.kotlinx:kotlinx-coroutines-test (testImplementation); версию coroutines-core и coroutines-test сделай одной (общая запись версии в каталоге).
   Других зависимостей не добавлять.

2. Пакет com.galyaminsky.zachetenot.domain.progress:
   - TopicProgress.kt:
     enum class TopicStatus { LOCKED, AVAILABLE, PASSED }
     data class TopicProgress(val topic: Topic, val status: TopicStatus)
     data class SubjectProgress(val topics: List<TopicProgress>) с вычисляемыми свойствами:
       totalCount = topics.size
       passedCount = число тем со статусом PASSED
       percent = если totalCount == 0, то 0, иначе passedCount * 100 / totalCount
   - ProgressCalculator.kt: object ProgressCalculator { fun calculate(topics: List<Topic>, passedTopicIds: Set<String>): SubjectProgress }
     Правила: темы сортируются по Topic.order; тема из passedTopicIds имеет статус PASSED; иначе первая тема (наименьший order) имеет AVAILABLE; иначе тема имеет AVAILABLE, если предыдущая по порядку тема PASSED, и LOCKED в остальных случаях. Id из passedTopicIds, которых нет среди topics, игнорируются.
   - ProgressRepository.kt:
     interface ProgressRepository {
         val passedTopicIds: Flow<Set<String>>
         suspend fun markPassed(topicId: String)   // идемпотентно, повторный вызов ничего не меняет
         suspend fun reset()                       // полный сброс прогресса
     }

3. Пакет com.galyaminsky.zachetenot.data:
   - DataStoreProgressRepository.kt: class DataStoreProgressRepository(private val dataStore: DataStore<Preferences>) : ProgressRepository. Ключ stringSetPreferencesKey("passed_topic_ids"). passedTopicIds = dataStore.data.map { it[key] ?: emptySet() }. markPassed добавляет id в множество через dataStore.edit. reset очищает ключ (удаляет его).
   - ProgressDataStore.kt: val Context.progressDataStore: DataStore<Preferences> by preferencesDataStore(name = "progress")  // единственный экземпляр, подключение к приложению будет позже.

4. Unit-тесты в app/src/test/java/...:
   - domain/progress/ProgressCalculatorTest.kt: нет пройденных = первая AVAILABLE, остальные LOCKED; пройдена первая = первая PASSED, вторая AVAILABLE, третья LOCKED; пройдены первая и третья, вторая нет = вторая AVAILABLE, третья PASSED (статус PASSED не зависит от соседей); все пройдены = percent 100; пройдена 1 из 3 = percent 33, passedCount 1, totalCount 3; неизвестные id игнорируются; темы, переданные не по порядку, сортируются по order; пустой список тем = percent 0.
   - data/DataStoreProgressRepositoryTest.kt: используй PreferenceDataStoreFactory.create с временным файлом (JUnit TemporaryFolder; имя файла с расширением .preferences_pb, файл заранее не создавать) и scope из runTest (backgroundScope), чтобы scope не утекал. Проверки: изначально пустое множество; markPassed добавляет id; повторный markPassed того же id оставляет одно значение; два разных id сохраняются оба; reset очищает множество.

Не создавать: ViewModel, DI-фреймворки, Room, хранение истории попыток, хранение результатов тестов, другие ключи.

5. Дополнительно (только отчёт, без изменений): приведи полный текст файла data/SystemTimeSource.kt.

Формат отчёта: выбранные версии зависимостей с источником; список созданных и изменённых файлов; полный текст ProgressCalculator.kt, ProgressRepository.kt, DataStoreProgressRepository.kt и SystemTimeSource.kt; названия тестов.
```
- **Сделано:** Добавлены вычисление статусов тем и контракт локального репозитория прогресса.
  Реализация хранит множество пройденных ID в Preferences DataStore; для core/test coroutines используется единая версия.
  Добавлены тесты калькулятора и репозитория на временном файле и `backgroundScope`.
- **Файлы:** изменены `libs.versions.toml`, `app/build.gradle.kts`; созданы три файла `domain/progress`, два файла DataStore и два тестовых файла.
- **Проверка агента:** сборка и тесты не запускались; ожидается подтверждение владельца.
- **Приёмка:** принято по коду.

### З-1.6 — загрузка контента и методы ContentBundle (2026-11-01)
- **Команда:**
```text
Задача: загрузка контента из assets и два метода-помощника. Без ViewModel и UI. Commit/push не выполнять, сборку и тесты не запускать.

1. Перенос. Перенеси ContentBundle.kt из пакета com.galyaminsky.zachetenot.data в com.galyaminsky.zachetenot.domain.model (файл app/src/main/java/com/galyaminsky/zachetenot/domain/model/ContentBundle.kt). Обнови package и импорты во всех файлах, где он используется (ContentParser.kt и тесты). Логику не менять.

2. В ContentBundle добавь два метода (в теле data class):
   fun questionsOf(topicId: String): List<Question> = questions.filter { it.topicId == topicId }
   fun ruleOf(ruleId: String): Rule = rules.first { it.id == ruleId }   // неизвестный id: NoSuchElementException

3. Файл domain/content/ContentRepository.kt (пакет com.galyaminsky.zachetenot.domain.content):
   interface ContentRepository { suspend fun loadContent(): ContentBundle }

4. Файл data/AssetContentRepository.kt (пакет com.galyaminsky.zachetenot.data):
   class AssetContentRepository(private val context: Context) : ContentRepository
   loadContent() выполняется в withContext(Dispatchers.IO): читает файл "content/russian_grade3.json" через context.assets.open(...) в UTF-8 строку и возвращает ContentParser.parse(текст). Имя файла вынеси в приватную константу внутри класса. Ошибки не перехватывать (исключение пробрасывается наверх).

5. Unit-тесты в app/src/test/java/com/galyaminsky/zachetenot/domain/model/ContentBundleTest.kt:
   - questionsOf возвращает только вопросы указанной темы (собери ContentBundle из двух тем и вопросов программно);
   - questionsOf для неизвестной темы возвращает пустой список;
   - ruleOf возвращает правило по id;
   - ruleOf для неизвестного id выбрасывает NoSuchElementException.

Не создавать: кэш, ViewModel, DI, интерфейсы кроме ContentRepository, инструментальные тесты, загрузку из сети.

Формат отчёта: список созданных, перемещённых и изменённых файлов; полный текст ContentBundle.kt, ContentRepository.kt и AssetContentRepository.kt; названия тестов.
```
- **Сделано:** `ContentBundle` перемещён в `domain.model` и получил методы выборки вопросов по теме и правила по ID.
  Добавлен контракт загрузки контента и чтение UTF-8 JSON из assets в `Dispatchers.IO`; ошибки не перехватываются.
  Добавлены четыре unit-теста для методов-помощников.
- **Файлы:** перемещён и изменён `ContentBundle.kt`; изменён `ContentParser.kt`; созданы `ContentRepository.kt`, `AssetContentRepository.kt`, `ContentBundleTest.kt`.
- **Проверка агента:** сборка и тесты не запускались.
- **Приёмка:** принято по коду.

### З-S1 — создание журнала проекта (2026-11-01)
- **Команда:**
```text
Задача: создать журнал проекта PROJECT_LOG.md, постоянные правила AGENTS.md и добавить журнал в .gitignore. Код приложения не менять. Commit/push не выполнять, сборку и тесты не запускать.

1. .gitignore в корне проекта: добавить в конец строку PROJECT_LOG.md (если файла нет, создать; существующие строки не менять; дубликат не добавлять).

2. AGENTS.md в корне проекта, на русском, кратко:
   - Перед началом любой задачи прочитай PROJECT_LOG.md целиком.
   - После завершения каждой задачи (в том числе «только отчёт») добавь запись в раздел «Журнал» по формату из раздела «Формат записи» и при необходимости обнови разделы «Текущее состояние», «Карта проекта», «Принятые решения», «Открытые вопросы». Запись делается до финального отчёта.
   - PROJECT_LOG.md не добавлять в git, commit/push не выполнять.
   - Постоянные ограничения: выполнять только поставленную задачу; проблемы вне задания только сообщать, не исправлять; сборку, тесты и эмулятор не запускать, если задание этого прямо не требует (сборку и тесты запускает владелец в Android Studio); не добавлять зависимости и абстракции без обоснования в задании.
   - Поле «Приёмка» в новой записи всегда «ожидает»; статус меняется только по прямому указанию в задании.

3. PROJECT_LOG.md в корне проекта, на русском, со следующими разделами (разделы 1–3 и 8 перенеси как есть, разделы 5–7 составь по тексту ниже и фактическому коду):

# Журнал проекта ZachetEnot

## 1. О проекте
Бесплатное офлайн Android-приложение-тренажёр для школьников Казахстана: закрепление знаний, полученных в школе (не учебник, не дневник). MVP: 3 класс, русский язык; источник содержания — PDF учебника, который предоставит владелец. Тест: 15 вопросов из банка, порог 90% (14/15), попытки не ограничены, таймер 20 минут без паузы, правильные ответы не показываются, при ошибке показывается короткое объяснение правила. Прогресс локально, история попыток не хранится. Без регистрации, сервера, рекламы и платных функций. Стек: Kotlin, Jetpack Compose, minSdk 29, портретная ориентация, смартфоны и планшеты; интерфейс на русском (затем казахский); макеты в Figma — источник визуальных решений, самостоятельно экраны не придумывать. Пакет: com.galyaminsky.zachetenot. Исходные документы «Концепция» и «Правила для разработки» хранятся у владельца.

## 2. Роли и процесс
Владелец (Юрик): принимает решения, делает макеты в Figma, передаёт задания, запускает сборку и тесты в Android Studio, делает commit/push. Claude: анализ, проверка необходимости изменений, формулирует короткие задания для Codex, принимает отчёты. Codex: выполняет точечные задания. Цикл: анализ > короткое задание > отчёт > проверка (сборка и тесты у владельца) > следующий шаг. Codex в своей среде не имеет сети и не может надёжно запускать Gradle.

## 3. Этапы
Этап 1 — логика без UI. Этап 2A — дизайн-основа: токены из Figma, ресурсы, компоненты UI-Kit, строки в strings.xml. Этап 2B — экраны и интеграция: ViewModel, навигация, DI, настройки (звук, сброс прогресса). Этап 3 — полировка и тестирование.

## 4. Текущее состояние
Этап 1 закрыт (подтверждено владельцем); идёт Этап 2A (токены сделаны, далее компоненты).

## 5. Карта проекта
Составь по фактическому дереву app/src/main и app/src/test: пакет или файл — одна строка о назначении.

## 6. Принятые решения
(1) Одна модель Question для всех форматов (выбор из вариантов); ответы сопоставляются по id варианта, не по индексу. (2) Объяснение привязано к Rule; ошибки по правилам — TestResult.mistakesByRule. (3) Порог целочисленный: correct*100 >= 90*total. (4) TestSession (состояние попытки) отделён от TestEngine (выбор и перемешивание); ответ и переход разделены (submitAnswer, moveToNext), чтобы показывать обратную связь; finish() можно вызвать в любой момент, неотвеченные вопросы считаются ошибками. (5) Таймер хранит дедлайн на абсолютном времени (TimeSource, SystemClock.elapsedRealtime); предупреждения за 5 и 1 минуту выдаются по одному разу, при прыжке времени выдаётся только наименьшее достигнутое. (6) Сворачивание: таймер идёт; если вне приложения строго больше 60 секунд, попытка сбрасывается (BackgroundGuard); закрытие приложения = сброс попытки (реализация в 2B). Значение 60 секунд — рабочее допущение, проверить на устройстве. (7) Прогресс: хранится только множество id пройденных тем (DataStore Preferences); статусы вычисляются (ProgressCalculator); первая тема всегда доступна; id тем глобально уникальны. (8) Room и DI-фреймворки не используются, зависимости собираются вручную. (9) Контент: JSON в assets (content/russian_grade3.json), разбор через DTO на kotlinx.serialization в data-слое; domain-модели без аннотаций; ContentBundle в domain.model. (10) Версии: Kotlin 2.2.10, AGP 9.5.0, Gradle 9.6.0, Compose BOM 2026.02.01, kotlinx.serialization 1.9.0, DataStore 1.2.1, coroutines 1.11.0. (11) Отложено в 2B: TestViewModel, создание экземпляров репозиториев, навигация, настройка звука (в том же DataStore), экран настроек со сбросом прогресса. (12) Текущий JSON-контент временный (1 тема, 3 правила, 24 вопроса), будет заменён контентом по PDF учебника. (13) Цветовые токены взяты из Figma UI-Kit «Шаг» (fileKey wRxJe6CeQZotDO4FrvYq2w; кит node 34:2494; экраны: выбор класса 34:2853, выбор предмета 34:2964, темы по предмету 34:3030). Используется одна светлая тема, dynamic color отключён; цвет ошибки в ките отсутствует. (14) Шкала отступов — 4/8/12/16/24/32/48; радиусы — 8/12/16/24/капсула; контуры — 1 px обычный, 2 px выбранный (карточка темы в ките 1.5 px приводится к 2); тени не используются. Экран 402, поля 24, высота кнопки 56 (радиус 16), зона нажатия не меньше 48. Иллюстрации будут векторными ресурсами, не токенами. (18) Контур карточек рисуется поверх без изменения раскладки; иллюстрация класса прижата к BottomEnd с отступами 8 и 6 dp; состояние Default карточки предмета выведено по аналогии с карточкой класса. В Type.kt заданы displayLarge 46/48 sp с letterSpacing -2 sp, titleSmall 14/17 sp Medium и bodySmall 12/15 sp. (19) Состояния PASSED и LOCKED карточки темы выведены по аналогии (в ките есть только AVAILABLE); контур выбранной темы — 2 px вместо 1.5. Навигационная панель без разделителя и тени учитывает системные insets через navigationBarsPadding; NavItem универсальный, набор разделов задаётся на экране. (20) Экраны выбора класса и предмета показываются только при первом запуске; далее открываются «Темы», класс и предмет меняются через настройки. Нижняя навигация в MVP не используется, настройки доступны кнопкой в шапке. Пройденные темы можно открывать повторно со стрелкой; тексты карточки прогресса и подписей для состояний вне макета готовит Claude и согласует владелец. (21) id тем стабильны (`topic_speech_text` … `topic_adjective`), порядок соответствует макету. Временный банк содержит 24 одинаковых по смыслу вопроса для каждой темы (копии с id `<topicId>__<id>`) и подлежит замене контентом по PDF учебника. У `Topic.description` значение по умолчанию `""`. Каталог классов и предметов расположен в domain без текстов; тексты хранятся в strings.xml.

## 7. Открытые вопросы и риски
- Банк вопросов реальной темы не меньше 30–45 заданий (при 16–20 тесты почти не различаются между попытками).
- Правильный ответ нигде не показывается: педагогическую эффективность проверить на 3–5 детях.
- Google Play: требования для детских приложений (Families), политика конфиденциальности.
- Права на учебник как источник содержания.
- Auto Backup: решить, должен ли прогресс восстанавливаться на новом устройстве (этап 3).
- Модель Question проверить на 3–5 реальных вопросах из учебника.
- Нет макетов для 2B: выбор класса и предмета (если остаётся в MVP), итоги теста (два состояния и ошибки по правилам), диалог выхода, обратная связь после ответа, предупреждения таймера, настройки.
- Не подтверждён запуск unit-тестов после заданий 1.5–1.6 (включая тест DataStore).
- Нет компонентов теста: вариант ответа, таймер, диалог, панель объяснения и итоги.
- Цвет ошибки в UI-Kit отсутствует.
- Контраст #FFB66C и #74788F требует проверки.
- Пятый стиль текста не заведён.
- Требуют решения название приложения и радиусы.
- Размеры текста вне шкалы: 11, 13, 14, 15, 23, 46 и 16/19 SemiBold; 11 px мелко для детей.
- Контраст #989DAF и #74788F требует проверки.
- Вкладка «Прогресс» отсутствует в концепции.
- Радиус 11 у логотипа вне шкалы.
- Название приложения (в UI Kit — «Шаг») и поддержка планшетов требуют решения.
- Размеры текста вне шкалы в UI Kit: 11, 13, 14, 15, 23, 46 и 16/19 SemiBold; 11 px мелко для детей 7–11 лет.
- Контраст #989DAF (около 2.7:1 на белом) и #74788F (около 4.3:1) требует проверки.
- Планшеты: колонка до 600 dp или отдельные макеты.
- Лицензия Inter (SIL OFL) требует приложить текст лицензии.
- Имя «Шаг / Основной текст» в Figma дублируется (цвет и текстовый стиль).
- Нет цвета и компонентов для неверного ответа, варианта ответа, таймера, диалога, панели объяснения и итогов.
- Нужно приложить тексты лицензий Lucide (ISC) и Inter (SIL OFL) в приложении.
- Для иконок 20–21 dp штрих рассчитан пропорционально (около 0.85 dp вместо 1); требуется проверка на устройстве.
- 11 px мелко для детей 7–11 лет.
- Соответствие иллюстраций классам (1, 2, 4) определить по экрану выбора класса; плитка предмета Default; текст «Aa» плитки предмета захардкожен в вызове, затем брать из контента.
- Нарисовать PASSED и LOCKED карточки темы в Figma; вкладка «Прогресс» вне концепции; UI Kit закрыт, кроме Switch (экран настроек, этап 2B).

- Название приложения: в макетах «шаг».
- Планшеты (колонка до 600 dp или отдельные макеты).

## 8. Формат записи
- **Команда:** дословный текст задания в блоке кода.
- **Сделано:** 3–10 строк по сути (решения, нетривиальные детали); код не копировать.
- **Файлы:** созданные / изменённые / перемещённые.
- **Проверка агента:** что запускалось; по умолчанию «сборка и тесты не запускались».
- **Приёмка:** ожидает | принято (замечания).

## 9. Журнал
Восстанови записи для уже выполненных заданий по своей переписке. Команду бери дословно из чата (если не нашёл точный текст, пиши «восстановлено по отчёту»). Дата у всех 2026-11-01. Соответствие номеров заданиям (по первой строке команды) и статусы приёмки:
- З-0.1 «только проверка и отчёт» (версии, дерево, зависимости): приёмка принято; в среде агента сборка не прошла из-за отсутствия сети, владелец позже подтвердил сборку проекта.
- З-1.1 «создать модели предметной области»: принято по коду; замечание: text.isNotEmpty() заменён на isNotBlank() в З-1.1а.
- З-1.1а «одна точечная правка и вывод содержимого файлов»: принято.
- З-1.2 «временный контент и его разбор»: не принято; в JSON найдены ошибки (дублированный ключ "id", слово «снеж_к» с пропущенной гласной не в корне, проверочные слова в тексте вопросов, нерелевантные ловушки в вариантах).
- З-1.2а «исправить ошибки в русском JSON-контенте и мелкие замечания в ContentParser»: принято; владелец подтвердил сборку.
- З-1.3 «логика одной попытки теста»: принято по коду.
- З-1.4 «таймер теста и защита от длительного ухода в фон»: принято по коду; владелец подтвердил запуск проекта, запуск unit-тестов явно не подтверждён.
- З-1.5 «локальное хранение прогресса и вычисление статусов тем»: принято по коду; сборка и тесты ожидают подтверждения владельца.
- З-1.6 «загрузка контента из assets и два метода-помощника»: принято по коду; сборка и тесты ожидают подтверждения владельца.
- З-S1 «создать журнал проекта PROJECT_LOG.md» (эта задача): добавь запись как для остальных, приёмка ожидает.

Формат отчёта: список созданных и изменённых файлов, результат проверки git (PROJECT_LOG.md должен игнорироваться: git check-ignore -v PROJECT_LOG.md, если git доступен), количество записей в разделе «Журнал».
```
- **Сделано:** Созданы постоянные правила проекта и журнал с фактической картой `app/src/main` и `app/src/test`.
  Восстановлены записи для заданий З-0.1 и З-1.1–З-1.6 с командами, файлами, проверками и статусами приёмки.
  Добавлена запись о создании журнала; по правилам статус новой записи — ожидает.
  `PROJECT_LOG.md` добавлен в корневой `.gitignore`.
- **Файлы:** созданы `AGENTS.md`, `PROJECT_LOG.md`; изменён `.gitignore`.
- **Проверка агента:** `git check-ignore -v PROJECT_LOG.md`; сборка и тесты не запускались.
- **Приёмка:** ожидает.

### З-2A.1 — цвета дизайн-системы и светлая тема (2026-11-01)
- **Команда:**
```text
Задача: цвета дизайн-системы и светлая тема.

1. ui/theme/Color.kt: заменить шаблонные цвета на 10 токенов (top-level val Color, пакет com.galyaminsky.zachetenot.ui.theme):
   Primary = 0xFF5752D9, Accent = 0xFFFFB66C, Surface = 0xFFFFFFFF, Background = 0xFFF8F9FD, Selected = 0xFFEEEDFF, Border = 0xFFE5E7F0, Success = 0xFF27846B, Disabled = 0xFFECEEF4, TextPrimary = 0xFF22243D, TextSecondary = 0xFF74788F.
   Над каждым значением комментарий-источник: роль в Figma (Основной, Акцентный, Поверхность, Фон, Выбранное, Границы, Успех, Недоступное, Основной текст, Вторичный текст).

2. ui/theme/Theme.kt: оставить только одну светлую тему. Удалить тёмную схему и dynamic color (Android 12+). Имя функции темы и сигнатуру, которые использует MainActivity, не менять (если у функции есть параметры darkTheme и dynamicColor, удали их и поправь вызовы). lightColorScheme:
   primary = Primary, onPrimary = Surface, primaryContainer = Selected, onPrimaryContainer = TextPrimary, secondary = Accent, onSecondary = TextPrimary, tertiary = Success, onTertiary = Surface, background = Background, onBackground = TextPrimary, surface = Surface, onSurface = TextPrimary, surfaceVariant = Disabled, onSurfaceVariant = TextSecondary, outline = Border, outlineVariant = Border.
   Цвет error не задавать (значение по умолчанию остаётся). Type.kt не менять.

3. Не менять другие файлы, кроме строк, которые перестали компилироваться из-за п. 1–2. Не создавать компоненты, градиенты, Shape, Dimens.

4. PROJECT_LOG.md: в «Текущее состояние» запиши, что Этап 1 закрыт (подтверждено владельцем), идёт Этап 2A; в «Принятые решения» добавь: токены берутся из Figma UI-Kit «Шаг» (fileKey wRxJe6CeQZotDO4FrvYq2w; кит node 34:2494; экраны: выбор класса 34:2853, выбор предмета 34:2964, темы по предмету 34:3030); светлая тема одна, dynamic color отключён; цвет ошибки в киту отсутствует; в «Открытые вопросы» добавь: нет компонентов теста (вариант ответа, таймер, диалог, панель объяснения, итоги), нет цвета ошибки, контраст #FFB66C и #74788F требует проверки, пятый стиль текста не заведён, название приложения, радиусы. Добавь запись З-2A.1.

Формат отчёта: полный текст Color.kt и Theme.kt, список изменённых файлов.
```
- **Сделано:** Добавлены десять цветовых токенов с комментариями источников из ролей Figma. Настроена единственная светлая Material 3 схема с заданными соответствиями и цветом ошибки по умолчанию. В журнале отмечены закрытие Этапа 1 и переход к Этапу 2A, решения и открытые вопросы по UI-Kit.
- **Файлы:** изменены `app/src/main/java/com/galyaminsky/zachetenot/ui/theme/Color.kt`, `app/src/main/java/com/galyaminsky/zachetenot/ui/theme/Theme.kt`, `PROJECT_LOG.md`.
- **Проверка агента:** сборка и тесты не запускались; проверены ссылки темы и отсутствие обращений к удалённым шаблонным цветам.
- **Приёмка:** ожидает.
### З-2A.2 — токены формы и размеров и четыре дополнительных цвета (2026-11-01)
- **Команда:**
```text
Задача: токены формы и размеров и четыре дополнительных цвета. Commit/push не выполнять, сборку и тесты не запускать.

1. ui/theme/Color.kt: добавить 4 токена (комментарий-источник у каждого):
   TextDisabled = 0xFF989DAF       // UI Kit: текст недоступного
   SuccessContainer = 0xFFE5F4ED   // UI Kit: фон успеха
   SelectedStrong = 0xFFE1DEFF     // UI Kit: подложка статуса «Доступна»
   SwitchTrackOff = 0xFFD9DDE8     // UI Kit: дорожка выключенного переключателя
   Существующие токены не менять.

2. ui/theme/Shape.kt (пакет com.galyaminsky.zachetenot.ui.theme):
   val AppShapes = Shapes(extraSmall = RoundedCornerShape(8.dp), small = RoundedCornerShape(12.dp), medium = RoundedCornerShape(16.dp), large = RoundedCornerShape(24.dp), extraLarge = RoundedCornerShape(24.dp))
   val CapsuleShape = RoundedCornerShape(percent = 50)
   Комментарий: шкала UI Kit 8/16/24/капсула; 12 используется в плитках иконок и бейдже номера темы (решение владельца: оставить в шкале).

3. ui/theme/Dimens.kt: object Dimens со значениями Dp: Space4 = 4.dp, Space8 = 8.dp, Space12 = 12.dp, Space16 = 16.dp, Space24 = 24.dp, Space32 = 32.dp, Space48 = 48.dp; ScreenHorizontalPadding = 24.dp; MinTouchTarget = 48.dp. Размеры отдельных компонентов не добавлять.

4. Theme.kt: передать shapes = AppShapes в MaterialTheme. Других изменений нет.

5. PROJECT_LOG.md: запись З-2A.2 и дополнения:
   «Принятые решения»: шкала отступов 4/8/12/16/24/32/48; радиусы 8/12/16/24/капсула; контуры 1 px обычный, 2 px выбранный (карточка темы в ките 1.5, приводится к 2); тени не используются; экран 402, поля 24, высота кнопки 56 (радиус 16), зона нажатия не меньше 48; иллюстрации и иконки будут векторными ресурсами, не токенами. (15) Inter включён в res/font (4 начертания, offline, без Downloadable Fonts); высота строки и межбуквенный интервал задаются в sp; пять стилей UI Kit сопоставлены слотам M3; дополнительные стили текста вводятся по мере необходимости компонентов. (16) Иконки Lucide (лицензия ISC) конвертированы в vector drawable, размер 24 dp, штрих 1 по измерению UI Kit; малые варианты со штрихом 2 для 12–15 dp (check, lock, play). Цвет иконок задаётся tint, в файлах — чёрный. Иллюстрации и логотип из экспорта SVG Figma имеют цвета в файлах, tint не применяется. Имена исходных SVG перепутаны по смыслу: школьная иллюстрация — тетрадь и карандаш, закрытая — линейка и ластик. (17) Кнопки реализуются на M3 Button; для всех стилей единое состояние Disabled. StatusChip использует TopicStatus из domain. В Type.kt добавлены labelLarge (16/19 SemiBold) и labelSmall (11/13 SemiBold) по CSS кита. SelectionState общий для карточек.
   «Открытые вопросы»: размеры текста вне шкалы (11, 13, 14, 15, 23, 46 и 16/19 SemiBold), 11 px мелко для детей, контраст #989DAF и #74788F требует проверки, вкладка «Прогресс» отсутствует в концепции, радиус 11 у логотипа вне шкалы, название приложения (в ките «Шаг»), планшеты.

Формат отчёта: полный текст Shape.kt и Dimens.kt, изменения в Color.kt и Theme.kt, список файлов.
```
- **Сделано:** Добавлены четыре цветовых токена с источниками UI Kit, шкала форм и капсульная форма, а также базовые отступы и минимальная зона нажатия. `AppShapes` передана в `MaterialTheme`. В журнал внесены решения и открытые вопросы по дизайну.
- **Файлы:** изменены `app/src/main/java/com/galyaminsky/zachetenot/ui/theme/Color.kt`, `app/src/main/java/com/galyaminsky/zachetenot/ui/theme/Theme.kt`, `PROJECT_LOG.md`; созданы `app/src/main/java/com/galyaminsky/zachetenot/ui/theme/Shape.kt`, `app/src/main/java/com/galyaminsky/zachetenot/ui/theme/Dimens.kt`.
- **Проверка агента:** сборка и тесты не запускались.
- **Приёмка:** ожидает.
### З-2A — токены дизайн-системы из UI Kit «Шаг» (2026-11-01)
- **Команда:**
```text
Задача: токены дизайн-системы из UI Kit «Шаг»: цвета, форма, размеры, типографика, светлая тема. Только файлы в ui/theme, плюс запись в журнале. Commit/push не выполнять, сборку и тесты не запускать. Предусловие: все 4 файла Inter в res/font.
Выполнить токены цветов, форм, размеров и типографики по UI Kit «Шаг», оставить одну светлую тему, добавить TypographyPreview и обновить состояние, решения и открытые вопросы проекта. Подробности — в задании пользователя.
```
- **Сделано:** Проверено наличие всех четырёх файлов Inter до изменений. Цветовые токены, формы и размеры приведены к описанию UI Kit; Type.kt настроен на Inter и пять заданных стилей M3, остальные слоты сохраняют значения по умолчанию с Inter. Светлая тема использует AppShapes и AppTypography; добавлен TypographyPreview с пятью строками. Журнал обновлён: токены Этапа 2A готовы, далее компоненты.
- **Файлы:** изменены `app/src/main/java/com/galyaminsky/zachetenot/ui/theme/Type.kt`, `Theme.kt`; создан `TypographyPreview.kt`; изменён `PROJECT_LOG.md`. `Color.kt`, `Shape.kt` и `Dimens.kt` уже соответствовали заданию.
- **Проверка агента:** четыре файла Inter присутствуют; сборка и тесты не запускались. `LineHeightStyle` не использован: API требует opt-in, поэтому в стилях заданы только значения `lineHeight`.
- **Приёмка:** принято (владелец сверил превью с Figma).
### З-R1 — отчёт о состоянии проекта (2026-11-01)
- **Команда:**
```text
Задача: только отчёт о текущем состоянии проекта. Ничего не изменять, не создавать, не запускать сборку и тесты. Commit/push не выполнять.

1. git: вывод git status --short и последнего коммита (git log -1 --oneline).
2. Журнал: существуют ли PROJECT_LOG.md и AGENTS.md; результат git check-ignore -v PROJECT_LOG.md; строки .gitignore; список заголовков записей раздела «Журнал» (только заголовки «### З-...»).
3. Тема: список файлов в ui/theme и в res/font; полный текст Color.kt, Theme.kt, Type.kt (если файла нет, так и напиши); есть ли Shape.kt, Dimens.kt, TypographyPreview.kt.
4. Тесты: для каждого файла в app/src/test перечисли имя файла и число аннотаций @Test.
5. Контент: файл russian_grade3.json одной строкой на вопрос в формате «id | text | варианты через / | correctOptionId».
6. Если что-то из пунктов 1-5 выполнить не удалось, укажи что и почему.

После отчёта добавь запись «З-R1 — отчёт о состоянии проекта» в журнал по правилам AGENTS.md. Если AGENTS.md не найден, не выдумывай: сообщи об этом.
```
- **Сделано:** Проверено состояние Git, журнал и тема; собраны списки файлов шрифтов и тестов с количеством `@Test`; перечислены вопросы и варианты из `russian_grade3.json`. Обнаружено, что `PROJECT_LOG.md` присутствует в `.gitignore`, но уже отслеживается Git, поэтому `git check-ignore -v` не выводит его правило.
- **Файлы:** изменён `PROJECT_LOG.md` (добавлена запись отчёта).
- **Проверка агента:** только чтение файлов и команды Git; сборка и тесты не запускались.
- **Приёмка:** ожидает.
### З-C1 — исправление пяти неоднозначных вопросов (2026-11-01)
- **Команда:**
```text
Задача: заменить 5 неоднозначных вопросов во временном контенте и привести журнал в порядок. Commit/push не выполнять, сборку и тесты не запускать.

1. app/src/main/assets/content/russian_grade3.json. Заменить text и text вариантов у пяти вопросов. id вопроса, topicId, ruleId и id вариантов не менять; у каждого вопроса остаётся 3 варианта option_1, option_2, option_3; correctOptionId указывает на правильный:
   q_form_02: text «Вставь пропущенную букву: в_сна», варианты option_1 «и», option_2 «а», option_3 «е»; correctOptionId q_form_02_option_3.
   q_form_05: text «Вставь пропущенную букву: с_стра», варианты «а», «е», «и»; correctOptionId q_form_05_option_2.
   q_form_07: text «Вставь пропущенную букву: гн_здо», варианты «а», «и», «е»; correctOptionId q_form_07_option_3.
   q_related_06: text «Вставь пропущенную букву: в_дяной», варианты «о», «а», «у»; correctOptionId q_related_06_option_1.
   q_related_07: text «Вставь пропущенную букву: д_мовой», варианты «а», «о», «у»; correctOptionId q_related_07_option_2.
   Остальные вопросы не менять. После правок проверь валидность JSON (ConvertFrom-Json) и сообщи, что по-прежнему 1 тема, 3 правила, 24 вопроса.

2. PROJECT_LOG.md: удалить из раздела «Журнал» лишний заголовок-шаблон «### З-<номер> — <короткое название> (<дата>)» (шаблон остаётся только в разделе «Формат записи»). Добавить запись З-C1.

Формат отчёта: список изменённых файлов, результат проверки JSON, пять изменённых вопросов в формате «id | text | варианты | correctOptionId».
```
- **Сделано:** Заменены формулировки и варианты у пяти заданных вопросов; идентификаторы вопросов, правил, тем и вариантов сохранены. Шаблонный заголовок удалён из раздела «Журнал»; запись З-C1 добавлена.
- **Файлы:** изменены `app/src/main/assets/content/russian_grade3.json`, `PROJECT_LOG.md`.
- **Проверка агента:** JSON успешно разобран через `ConvertFrom-Json`; количество тем/правил/вопросов — 1/3/24. Сборка и тесты не запускались.
- **Приёмка:** ожидает.
### З-2A.4 — векторные ресурсы UI Kit (выполнено владельцем вручную) (2026-11-01)
- **Команда:**
```text
Выполнено владельцем вручную, без Codex: добавить 16 файлов в res/drawable — 10 основных иконок Lucide (ISC), малые ic_check_small / ic_lock_small / ic_play_small, illustration_school, illustration_locked и ic_logo.
```
- **Сделано:** Владелец вручную добавил 16 drawable-ресурсов без Codex: 10 основных иконок Lucide со штрихом 1, малые check/lock/play со штрихом 2 для размеров 12–15 dp, две иллюстрации и логотип. Цвет иконок назначается через tint; иллюстрации используют цвета, встроенные в файлы.
- **Файлы:** добавлены владельцем вручную 16 XML-файлов в `app/src/main/res/drawable`.
- **Проверка агента:** при последующей проверке все 16 XML корректны и имеют корень `vector`; сборка и тесты не запускались.
- **Открытый вопрос:** приложить лицензии Lucide (ISC) и Inter (SIL OFL) в приложении.
- **Приёмка:** принято (владелец сверил превью с Figma).
### З-2A.5 — четыре базовых компонента и галерея (2026-11-01)
- **Команда:**
```text
Задача: четыре базовых компонента и их галерея. Commit/push, сборку и тесты не запускать. Использовать только существующие токены и ресурсы. Тексты показывать через stringResource, кроме текстов внутри @Preview.

1. Type.kt: заменить labelLarge и labelSmall на заданные CSS стили Inter SemiBold (16/19 sp и 11/13 sp).
2. strings.xml: добавить topic_status_available, topic_status_passed, topic_status_locked.
3. Создать AppButton, StatusChip, StepProgressBar, SelectionIndicator и BasicComponentsPreview в ui/components согласно переданным спецификациям.
4. AGENTS.md: читать разделы 1–8 и три последние записи журнала, весь журнал целиком не читать.
5. PROJECT_LOG.md: уточнить ручное добавление ресурсов в З-2A.4, добавить запись З-2A.5 и указанные решения/открытый вопрос.

Формат отчёта: только список созданных и изменённых файлов, без кода.
```
- **Сделано:** Добавлены четыре базовых Compose-компонента и Preview-галерея; статусные подписи вынесены в string resources. Обновлены два слота типографики. В AGENTS.md изменена инструкция чтения журнала. Уточнена запись о ручном добавлении drawable-ресурсов владельцем и внесена запись З-2A.5.
- **Файлы:** созданы `AppButton.kt`, `StatusChip.kt`, `StepProgressBar.kt`, `SelectionIndicator.kt`, `BasicComponentsPreview.kt`; изменены `Type.kt`, `strings.xml`, `AGENTS.md`, `PROJECT_LOG.md`.
- **Проверка агента:** сборка и тесты не запускались.
- **Приёмка:** принято (владелец сверил превью с Figma).
### З-2A.5а — третья закрытая иллюстрация (выполнено владельцем) (2026-11-02)
- **Команда:**
```text
Выполнено владельцем: добавить illustration_locked_2.xml (третья закрытая иллюстрация; файл «Иллюстрация2.svg» дублировал уже имеющуюся illustration_locked).
```
- **Сделано:** Добавлена третья закрытая иллюстрация. Файл «Иллюстрация2.svg» оказался дублем существующей `illustration_locked`.
- **Файлы:** добавлен владельцем `app/src/main/res/drawable/illustration_locked_2.xml`.
- **Проверка агента:** файл проверен по наличию; сборка и тесты не запускались.
- **Приёмка:** ожидает.

### З-2A.6 — карточки класса и предмета (2026-11-02)
- **Команда:**
```text
Задача: карточки класса и предмета и недостающие токены. Commit/push, сборку и тесты не запускать. Использовать только существующие токены и ресурсы; тексты через stringResource, кроме текстов внутри @Preview.

1. Type.kt: заменить три слота (остальное не трогать), fontFamily = InterFamily: displayLarge = Bold, 46 / 48 sp, letterSpacing -2.sp; titleSmall = Medium, 14 / 17 sp, letterSpacing 0; bodySmall = Normal, 12 / 15 sp, letterSpacing 0.
2. Dimens.kt: добавить BorderWidth = 1.dp и BorderWidthSelected = 2.dp.
3. res/values/strings.xml: добавить grade_label «класс».
4. Создать ui/components/ClassCard.kt согласно спецификации карточки класса, SelectionState из SelectionIndicator.kt.
5. Создать ui/components/SubjectCard.kt согласно спецификации карточки предмета; состояние Default выведено по аналогии с карточкой класса и отражено в журнале.
6. Создать ui/components/CardsPreview.kt: один Preview 402×900 в ZachetEnotTheme с карточками класса и предмета по указанным состояниям и ресурсам.
7. Не создавать карточку темы, навигацию, ViewModel, данные. Другие файлы не менять.
8. В журнал внести решения о контуре поверх без изменения раскладки, расположении иллюстрации и типографике; записать З-2A.5а (выполнено владельцем) про illustration_locked_2. Обновить открытые вопросы: соответствие иллюстраций классам, Default карточки предмета и текст Aa из контента.

Формат отчёта: только список созданных и изменённых файлов.
```
- **Сделано:** Добавлены карточки класса и предмета с доступными состояниями и общий preview; для подписи класса добавлен string resource. Добавлены токены ширины контура и настроены три заданных стиля Inter. В принятые решения и открытые вопросы внесены уточнения по карточкам; зафиксировано выполненное владельцем добавление третьей закрытой иллюстрации.
- **Файлы:** созданы `ClassCard.kt`, `SubjectCard.kt`, `CardsPreview.kt`; изменены `Type.kt`, `Dimens.kt`, `res/values/strings.xml`, `PROJECT_LOG.md`.
- **Проверка агента:** проверено наличие используемых drawable-ресурсов; сборка и тесты не запускались.
- **Приёмка:** принято (владелец сверил превью с Figma).

### З-2A.7 — карточка темы и нижняя навигация (2026-11-02)
- **Команда:**
```text
Задача: карточка темы и нижняя навигация. Commit/push, сборку и тесты не запускать. Только существующие токены и ресурсы; тексты через stringResource, кроме текстов внутри @Preview.

1. strings.xml: добавить nav_topics «Темы», nav_progress «Прогресс», nav_settings «Настройки».
2. Создать ui/components/TopicCard.kt по спецификации: бейдж номера, описание, StatusChip, состояния AVAILABLE/PASSED/LOCKED с заданными цветами и контуром.
3. Создать ui/components/AppNavigationBar.kt: NavItem и универсальная нижняя панель с тремя состояниями разделов, без разделителя и тени, с navigationBarsPadding.
4. Создать ui/components/TopicAndNavPreview.kt: один Preview 402×620 с карточками тем и панелью разделов из строковых ресурсов.
5. Не создавать Switch, ViewModel, экраны, навигацию Compose. Другие файлы не менять.
6. Внести решения о PASSED/LOCKED, контуре выбранной темы, панели и универсальном NavItem; обновить открытые вопросы по макетам и Switch.

Формат отчёта: только список созданных и изменённых файлов.
```
- **Сделано:** Созданы карточка темы с тремя статусами, универсальная нижняя навигация и preview. Названия разделов вынесены в string resources; использованы имеющиеся иконки и цветовые токены. Зафиксированы решения по отсутствующим состояниям в ките и открытые вопросы по макетам.
- **Файлы:** созданы `TopicCard.kt`, `AppNavigationBar.kt`, `TopicAndNavPreview.kt`; изменены `res/values/strings.xml`, `PROJECT_LOG.md`.
- **Проверка агента:** сборка и тесты не запускались.
- **Приёмка:** принято (владелец сверил превью с Figma).

### З-S2 — спецификация экранов MVP (2026-11-02)
- **Команда:**
```text
Задача: зафиксировать спецификацию трёх экранов в документации. Код не менять. Commit/push, сборку и тесты не запускать.

1. Создать в корне проекта SCREENS.md с предоставленным содержимым на русском.
2. AGENTS.md: добавить строку «Для задач по экранам и компонентам читать SCREENS.md».
3. PROJECT_LOG.md: добавить запись З-S2 с источником Figma «Шаг» (fileKey wRxJe6CeQZotDO4FrvYq2w; экраны: выбор класса 34:2853, выбор предмета 34:2964, темы 34:3030) и перенести в «Открытые вопросы» раздел SCREENS.md.

Формат отчёта: только список созданных и изменённых файлов.
```
- **Сделано:** Добавлена спецификация маршрута, трёх экранов, тем курса, состояний карточки, компонентов, данных и ассетов. В AGENTS.md добавлена ссылка на документ для задач по экранам и компонентам. Открытые вопросы из спецификации перенесены в раздел журнала.
- **Файлы:** создан `SCREENS.md`; изменены `AGENTS.md`, `PROJECT_LOG.md`.
- **Проверка агента:** только чтение и сверка документации; сборка и тесты не запускались.
- **Приёмка:** ожидает.

### З-2A.8а — ассеты экранных компонентов (выполнено владельцем) (2026-11-02)
- **Команда:**
```text
Выполнено владельцем: добавить illustration_school_locked.xml и ic_sparkles.xml.
```
- **Сделано:** Владелец добавил иллюстрацию закрытого четвёртого класса и иконку sparkles для экранных компонентов.
- **Файлы:** добавлены владельцем `app/src/main/res/drawable/illustration_school_locked.xml`, `app/src/main/res/drawable/ic_sparkles.xml`.
- **Проверка агента:** файлы проверены по наличию; сборка и тесты не запускались.
- **Приёмка:** ожидает.

### З-2A.8 — компоненты частей экранов (2026-11-02)
- **Команда:**
```text
Задача: новые компоненты экранов и правки существующих по SCREENS.md. Commit/push, сборку и тесты не запускать. Только существующие токены и ресурсы (включая новый res/drawable/ic_sparkles.xml); тексты через stringResource, кроме текстов внутри @Preview. Нижнюю навигацию не менять.

1. Color.kt и strings.xml: добавить OnPrimaryMuted и строковые ресурсы для логотипа, back и карточки прогресса.
2. TopicCard.kt: привести состояния PASSED и LOCKED к макету.
3. StepProgressBar.kt: добавить настройки цвета трека и заполнения.
4. Создать AppLogo, StageIndicator, BackButton, InfoChip, HintRow, SectionHeader, SettingsButton и ProgressCard.
5. Создать ScreenPartsPreview.kt с заданными компонентами и карточками.
6. Не создавать экраны, ViewModel, навигацию; другие файлы не менять.
7. В SCREENS.md добавить принятые решения о показе экранов, настройках, повторном открытии тем и текстах состояний; убрать решённые вопросы.
8. В журнал внести З-2A.8 и З-2A.8а о ресурсах, добавленных владельцем.

Формат отчёта: только список созданных и изменённых файлов.
```
- **Сделано:** Добавлены компоненты для шапки, подсказок, карточки прогресса и заголовка раздела; создана preview-галерея. StepProgressBar получил параметры цветов; TopicCard приведён к указанным цветам для состояний PASSED и LOCKED. В SCREENS.md добавлены согласованные решения, решённые вопросы удалены.
- **Файлы:** созданы `AppLogo.kt`, `StageIndicator.kt`, `BackButton.kt`, `InfoChip.kt`, `HintRow.kt`, `SectionHeader.kt`, `SettingsButton.kt`, `ProgressCard.kt`, `ScreenPartsPreview.kt`; изменены `Color.kt`, `strings.xml`, `TopicCard.kt`, `StepProgressBar.kt`, `SCREENS.md`, `PROJECT_LOG.md`.
- **Проверка агента:** проверено наличие требуемых drawable-ресурсов; сборка и тесты не запускались.
- **Приёмка:** принято (владелец сверил превью с Figma).

### З-2B.1 — модель данных экранов и временный контент (2026-11-02)
- **Команда:**
```text
Задача: модель данных под экраны и временный контент. Только domain и data, без UI. Commit/push, сборку и тесты не запускать.

1. Topic.kt: добавить description: String = "".
2. ContentParser.kt: добавить description со значением по умолчанию в TopicDto и передавать его в Topic.
3. russian_grade3.json: определить шесть тем в порядке макета; правила оставить без изменений; размножить 24 существующих вопроса на каждую тему с id вида <topicId>__<старый id>; проверить 6/3/144, уникальность ID, по 24 вопроса на тему и UTF-8 без BOM.
4. ContentParserTest.kt: обновить проверки реального файла до 6 тем, 3 правил, 144 вопросов, 24 вопроса на тему и непустого description.
5. Создать domain.catalog: Grade, Subject, AppCatalog с четырьмя классами и четырьмя предметами без текстов; создать AppCatalogTest с четырьмя проверками каталога и значений по умолчанию.
6. Не создавать хранилище выбора, ViewModel, строки, UI; другие файлы не менять.
7. Внести решения в журнал; в SCREENS.md отметить, что описание темы, шесть тем и каталог добавлены.

Формат отчёта: результаты проверки JSON и список созданных и изменённых файлов.
```
- **Сделано:** Добавлено необязательное описание темы и передача поля через парсер; временный JSON расширен до шести тем и 144 вопросов-копий. Создан каталог классов и предметов без текстов и тесты его состава и defaults. Спецификация экранов и принятые решения журнала обновлены.
- **Файлы:** созданы `Grade.kt`, `Subject.kt`, `AppCatalog.kt`, `AppCatalogTest.kt`; изменены `Topic.kt`, `ContentParser.kt`, `russian_grade3.json`, `ContentParserTest.kt`, `SCREENS.md`, `PROJECT_LOG.md`.
- **Проверка агента:** JSON успешно разобран через `ConvertFrom-Json`; 6 тем, 3 правила, 144 вопроса; ID вопросов уникальны; у каждой темы 24 вопроса; UTF-8 BOM отсутствует. Сборка и тесты не запускались.
- **Приёмка:** принято по коду; Run Tests ожидает подтверждения владельца.

### З-R2 — запуск unit-тестов app (2026-11-02)
- **Команда:**
```text
Запусти тесты app/src/test.
```
- **Сделано:** Выполнен `testDebugUnitTest`; 56 тестов завершились, 2 упали в `DataStoreProgressRepositoryTest` с IOException при переименовании временного `progress.preferences_pb.tmp` в `progress.preferences_pb`.
- **Файлы:** изменён `PROJECT_LOG.md`.
- **Проверка агента:** `testDebugUnitTest` запускался; 54 теста прошли, 2 упали. Отдельную сборку не запускали.
- **Приёмка:** ожидает.

### З-2C.1 — общие компоненты экрана теста (2026-11-02)
- **Команда:** создать только UI-компоненты теста по макетам Figma, строки интерфейса, preview и обновить спецификацию экранов; без экранов, ViewModel и навигации. Commit/push, сборку и тесты не запускать.
- **Сделано:** добавлены верхняя панель, заголовок прогресса, карточка задания, варианты ответа с зафиксированным состоянием и блок действия с уведомлением о зафиксированном ответе. Добавлен preview и строки ресурсов. В SCREENS.md внесены макеты теста, решение владельца и перечень расхождений на потом.
- **Файлы:** созданы `TestTopBar.kt`, `TestProgressHeader.kt`, `TaskCard.kt`, `ChoiceOption.kt`, `TestActionBlock.kt`, `TestComponentsPreview.kt`; изменены `res/values/strings.xml`, `SCREENS.md`, `PROJECT_LOG.md`.
- **Проверка агента:** проверено соответствие компонентов указанным параметрам и использованию существующих токенов/ресурсов; сборка и тесты не запускались.
- **Приёмка:** принято (владелец сверил превью с Figma).

### З-2C.2 — именованные аргументы Compose (2026-11-02)
- **Команда:** исправить позиционные аргументы Text и Icon в ui/components; добавить ограничение в AGENTS.md. Не менять логику, значения и размеры; сборку и тесты не запускать.
- **Сделано:** вызовы Text и Icon, где аргументы style/color/modifier/tint передавались позиционно, переведены на именованные аргументы. Ограничение добавлено в AGENTS.md.
- **Файлы:** изменены `AGENTS.md`, `ChoiceOption.kt`, `TaskCard.kt`, `TestActionBlock.kt`, `TestProgressHeader.kt`, `TestTopBar.kt`, `ResourcesPreview.kt`, `PROJECT_LOG.md`.
- **Проверка агента:** выполнен поиск вызовов Text и Icon в ui/components; сборка и тесты не запускались.
- **Приёмка:** принято (владелец сверил превью с Figma).

### З-2C.2 — компоненты ответов для типов заданий (2026-11-02)
- **Команда:** создать UI-компоненты ввода текста, последовательности и сопоставления по макетам Figma, добавить preview и подписи состояний в существующий preview. Добавить строку «Выбрать». Сборку и тесты не запускать.
- **Сделано:** добавлены TextAnswerField, SequenceItem и MatchingRow с состояниями заполненного/зафиксированного ответа; создан AnswerComponentsPreview и дополнен TestComponentsPreview. Добавлена строка matching_choose.
- **Файлы:** созданы `TextAnswerField.kt`, `SequenceItem.kt`, `MatchingRow.kt`, `AnswerComponentsPreview.kt`; изменены `strings.xml`, `TestComponentsPreview.kt`, `PROJECT_LOG.md`.
- **Проверка агента:** сверены сигнатуры, именованные аргументы Compose, токены и ресурсы по заданию; сборка и тесты не запускались.
- **Приёмка:** принято (владелец сверил превью с Figma).

### З-2C.3 — экраны подготовки и теста (2026-11-02)
- **Команда:** создать UI-модели и экраны «Подготовка к тесту» и «Тест» с previews по макетам Figma. Только UI, без ViewModel, домена, навигации и хранения. Не использовать системные insets; сборку и тесты не запускать.
- **Сделано:** добавлены UI-модели, экран подготовки, экран теста с рендерингом четырёх типов ответа и 13 превью состояний. В SCREENS.md записаны решения о системных insets, готовых строках UI-моделей и закреплённой нижней кнопке.
- **Файлы:** созданы `TestUiModels.kt`, `TestScreen.kt`, `PrepScreen.kt`, `TestScreenPreviews.kt`; изменены `SCREENS.md`, `PROJECT_LOG.md`.
- **Проверка агента:** сверены модели, состояния и preview с заданием; сборка и тесты не запускались.
- **Приёмка:** принято (владелец сверил превью с Figma).

### З-2C.4 — экраны результата и разбора ошибок (2026-11-02)
- **Команда:** создать UI карточек результата и ошибок, экраны результата и разбора по макетам Figma, четыре preview и вынести рамку preview в общую функцию. Только UI; сборку и тесты не запускать.
- **Сделано:** добавлены сводка результата, статистическая плитка, карточка ошибки и карточка успешного разбора; созданы модели и экраны результата/разбора с четырьмя preview. Общая `PreviewFrame` вынесена для экранов теста и результата. В SCREENS.md записаны решения о готовых строках, нескольких ошибках и ответственности хоста за подписи.
- **Файлы:** созданы `ResultSummaryCard.kt`, `StatTile.kt`, `MistakeCard.kt`, `ReviewSuccessCard.kt`, `ResultUiModels.kt`, `ResultScreen.kt`, `ReviewScreen.kt`, `ResultScreenPreviews.kt`, `PreviewFrame.kt`; изменены `TestScreenPreviews.kt`, `SCREENS.md`, `PROJECT_LOG.md`.
- **Проверка агента:** сверены компоненты, UI-модели, состояния и previews с заданием; сборка и тесты не запускались.
- **Приёмка:** принято (владелец сверил превью с Figma).

### З-F1 — фиксация состояния проекта (2026-11-02)
- **Команда:**
```text
Задача: зафиксировать текущее состояние проекта в документации. Код не менять. Commit/push, сборку и тесты не запускать.

Обновить текущее состояние, статусы приёмки перечисленных записей, принятые решения и открытые вопросы согласно согласованному плану этапа адаптации. Добавить запись З-F1.
```
- **Сделано:** отмечено закрытие этапов 1 и 2A и текущее состояние статических UI-экранов. Обновлены принятые решения, этапы адаптации и статусы приёмки указанных записей журнала.
- **Файлы:** изменён `PROJECT_LOG.md`.
- **Проверка агента:** документация обновлена и сверена; код не менялся, сборка и тесты не запускались.
- **Приёмка:** ожидает.

### З-R3 — запуск unit-тестов app (2026-11-02)
- **Команда:**
```text
Теперь прогони все тесты в app/src/test
```
- **Сделано:** Запущена задача `:app:testDebugUnitTest`; выполнено 56 тестов, из них 54 прошли и 2 завершились ошибкой IOException в `DataStoreProgressRepositoryTest`.
- **Файлы:** изменён `PROJECT_LOG.md`.
- **Проверка агента:** выполнен `:app:testDebugUnitTest`; сборка отдельно не запускалась.
- **Приёмка:** ожидает.
