# Карта файлов и компонентов ZachetEnot

Инвентаризация на 2026-11-03. Сборочные каталоги `build/`, `.gradle/` и IDE-кэш не перечисляются пофайлово: это генерируемые/локальные файлы, не исходные компоненты. Пути относительно корня репозитория.

## Корень проекта

- `AGENTS.md` — локальные инструкции работы.
- `PROJECT_LOG.md` — решения, текущее состояние, журнал задач; исключён из git по инструкции проекта.
- `SCREENS.md` — спецификация экранов и решений по макетам.
- `settings.gradle.kts`, `build.gradle.kts`, `gradle.properties` — Gradle project и общие настройки.
- `gradle/libs.versions.toml` — централизованные версии зависимостей.
- `gradle/wrapper/gradle-wrapper.properties`, `gradle-wrapper.jar`, `gradlew`, `gradlew.bat` — Gradle Wrapper.
- `local.properties` — локальный путь SDK, не переносимая конфигурация.
- `.gitignore`, `app/.gitignore`, `.idea/*` — исключения и локальные настройки IDE.
- `gradle/gradle-daemon-jvm.properties` — JDK toolchain daemon.

## Android-модуль `app`

- `app/build.gradle.kts` — единственный Android application-модуль; Compose, SDK-параметры, зависимости.
- `app/src/main/AndroidManifest.xml` — одна launcher Activity; internet permission отсутствует.
- `app/src/main/keepRules/rules.keep` — правила shrinker.
- `app/src/main/assets/content/russian_grade3.json` — локальный временный JSON: 6 тем, 3 правила, 144 вопроса.

### Исходники `app/src/main/java/com/galyaminsky/zachetenot`

- `MainActivity.kt` — launcher, в текущем состоянии показывает шаблон `Greeting`.
- `data/AssetContentRepository.kt` — читает asset на IO dispatcher.
- `data/ContentParser.kt` — JSON DTO → доменный `ContentBundle`, проверка связей и уникальности ID вопросов.
- `data/DataStoreProgressRepository.kt`, `data/ProgressDataStore.kt` — Preferences DataStore для множества пройденных тем.
- `data/SystemTimeSource.kt` — Android `SystemClock.elapsedRealtime()` адаптер для доменного clock.
- `domain/content/ContentRepository.kt` — контракт чтения контента.
- `domain/catalog/AppCatalog.kt`, `Grade.kt`, `Subject.kt` — каталог доступных классов/предметов.
- `domain/model/ContentBundle.kt`, `Topic.kt`, `Rule.kt`, `Question.kt`, `Option.kt` — модель содержимого.
- `domain/model/TestConfig.kt`, `TestResult.kt` — конфигурация и агрегированный итог.
- `domain/progress/ProgressCalculator.kt`, `ProgressRepository.kt`, `TopicProgress.kt` — расчёт/контракт прогресса.
- `domain/test/TestEngine.kt`, `TestSession.kt`, `TestTimer.kt`, `TimeSource.kt`, `BackgroundGuard.kt` — выбор вопросов, попытка, таймер и контроль отсутствия.
- `ui/components/*.kt` — базовые кнопки, карточки, варианты ответов, строки, панели, статусы и Compose previews; подробная инвентаризация ниже.
- `ui/screens/test/PrepScreen.kt`, `TestScreen.kt`, `TestUiModels.kt`, `PreviewFrame.kt`, `TestScreenPreviews.kt` — статические UI-модели и экраны подготовки/теста.
- `ui/screens/result/ResultScreen.kt`, `ReviewScreen.kt`, `ResultUiModels.kt`, `ResultScreenPreviews.kt` — статические экран результата и разбора.
- `ui/theme/Color.kt`, `Dimens.kt`, `Shape.kt`, `Theme.kt`, `Type.kt`, `TypographyPreview.kt` — Compose-токены, шрифты/типографика и preview.

### UI-компоненты

`AnswerComponentsPreview.kt`, `AppButton.kt`, `AppLogo.kt`, `AppNavigationBar.kt`, `BackButton.kt`, `BasicComponentsPreview.kt`, `CardsPreview.kt`, `ChoiceOption.kt`, `ClassCard.kt`, `HintRow.kt`, `InfoChip.kt`, `MatchingRow.kt`, `MistakeCard.kt`, `ProgressCard.kt`, `ResourcesPreview.kt`, `ResultSummaryCard.kt`, `ReviewSuccessCard.kt`, `ScreenPartsPreview.kt`, `SectionHeader.kt`, `SelectionIndicator.kt`, `SequenceItem.kt`, `SettingsButton.kt`, `StageIndicator.kt`, `StatTile.kt`, `StatusChip.kt`, `StepProgressBar.kt`, `SubjectCard.kt`, `TaskCard.kt`, `TestActionBlock.kt`, `TestComponentsPreview.kt`, `TestProgressHeader.kt`, `TestTopBar.kt`, `TextAnswerField.kt`, `TopicAndNavPreview.kt`, `TopicCard.kt`.

### Ресурсы `app/src/main/res`

- `drawable/*.xml` — векторные значки (стрелки, книга, калькулятор, график, замок, настройки и др.), logo и иллюстрации заблокированных/школьных карточек.
- `font/inter_{regular,medium,semibold,bold}.ttf` — Inter локально.
- `mipmap-*/*` — launcher icons для плотностей и adaptive icon XML.
- `values/colors.xml`, `strings.xml`, `themes.xml` — XML-ресурсы Android.
- `xml/backup_rules.xml`, `data_extraction_rules.xml` — шаблонные правила резервного копирования/извлечения; не настроены явно для DataStore.

## Тесты

`app/src/test/java/com/galyaminsky/zachetenot/`: `ExampleUnitTest.kt` (шаблон), `data/ContentParserTest.kt`, `data/DataStoreProgressRepositoryTest.kt`, `domain/catalog/AppCatalogTest.kt`, `domain/model/ContentBundleTest.kt`, `domain/model/QuestionTest.kt`, `domain/progress/ProgressCalculatorTest.kt`, `domain/test/BackgroundGuardTest.kt`, `TestEngineTest.kt`, `TestSessionTest.kt`, `TestTimerTest.kt`.

`app/src/androidTest/java/com/galyaminsky/zachetenot/ExampleInstrumentedTest.kt` — шаблонный instrumented test. Android UI-сценариев тестирования не найдено.

## Не исходные артефакты и замечания о фактическом использовании

- В `app/build/` найдены APK debug и androidTest APK, manifest merger report, test-results XML и HTML отчёты от прежних запусков. Они сгенерированы и сами по себе не подтверждают состояние текущей сборки.
- В корневом `build/reports/configuration-cache/` находятся сгенерированные отчёты Gradle.
- `MainActivity.kt` пока не вызывает продуктовые UI-экраны; экраны и множество компонентов доступны как composable/preview, но не связаны маршрутизацией.
- `Greeting`, `ExampleUnitTest` и `ExampleInstrumentedTest` выглядят шаблонными. Удаление не проводилось.
- В рабочем дереве не обнаружены файлы, названные `MVP`, `audit`, `матрица`/`аудит`, кроме запрошенных сейчас отчётов и журнала. Материалы аудита матрицы 2.0 недоступны в проектной папке; проверка совместимости A–H не выполнена.
- Точный «неиспользуемый» статус ресурсов без полного анализа XML/Compose ссылок и сборки не присваивался; превью-файлы являются намеренно используемыми средствами просмотра UI.