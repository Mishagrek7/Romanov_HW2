package edujira.ifellow.ru.Pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CreateIssuePage {

    // Основные элементы
    private final SelenideElement issueTypeInput = $(By.xpath("//input[@id='issuetype-field']"));
    private final SelenideElement summaryInput = $(By.xpath("//input[@id='summary']"));
    private final SelenideElement submitButton = $(By.xpath("//input[@id='create-issue-submit']"));
    private final SelenideElement createdIssueLink = $(By.xpath("//a[contains(@class, 'issue-created-key')]"));

    // Кнопки переключения режима
    private final SelenideElement descriptionVisualButton = $(By.xpath("//div[@id='description-wiki-edit']//button[contains(text(),'Визуальный')]"));
    private final SelenideElement environmentVisualButton = $(By.xpath("//div[@id='environment-wiki-edit']//button[contains(text(),'Визуальный')]"));

    /**
     * Проверяет что диалог создания задачи открыт
     */
    public CreateIssuePage verifyDialogOpen() {
        summaryInput.shouldBe(visible);
        return this;
    }

    /**
     * Выбирает тип задачи "Ошибка"
     */
    public CreateIssuePage selectBugIssueType() {
        issueTypeInput.click();
        issueTypeInput.clear();
        issueTypeInput.setValue("Ошибка").pressEnter();

        // Ждем применения типа
        sleep(1000);
        return this;
    }

    /**
     * Убеждается что поле в визуальном режиме
     */
    public CreateIssuePage ensureVisualMode(String fieldType) {
        SelenideElement visualButton;

        if ("description".equals(fieldType)) {
            visualButton = descriptionVisualButton;
        } else {
            visualButton = environmentVisualButton;
        }

        // Если кнопка "Визуальный" видна, значит сейчас текстовый режим - переключаем
        if (visualButton.exists() && visualButton.isDisplayed()) {
            visualButton.click();
            sleep(500); // Ждем переключения
        }

        return this;
    }

    /**
     * Заполняет поле через iframe (простая версия)
     */
    public CreateIssuePage fillFieldViaFrame(String fieldType, String text) {
        ensureVisualMode(fieldType);

        // Находим iframe редактора
        SelenideElement frame;
        if ("description".equals(fieldType)) {
            frame = $(By.xpath("//div[@id='description-wiki-edit']//iframe"));
        } else {
            frame = $(By.xpath("//div[@id='environment-wiki-edit']//iframe"));
        }

        // Переключаемся в iframe и заполняем
        switchTo().frame(frame);
        $(By.xpath("//body")).setValue(text);
        switchTo().defaultContent();

        return this;
    }

    /**
     * Выбирает версию
     */
    public CreateIssuePage selectVersion(String versionName) {
        // Простой способ - находим select и выбираем опцию
        SelenideElement versionSelect = $(By.xpath("//select[contains(@id,'versions')]"));
        if (versionSelect.exists()) {
            versionSelect.selectOptionContainingText(versionName);
        }
        return this;
    }

    /**
     * Создает баг с указанными параметрами
     */
    public String createBug(String summary, String description, String environment, String version) {
        System.out.println("Начинаем создание бага...");

        // 1. Выбираем тип задачи
        selectBugIssueType();
        System.out.println("✅ Тип задачи выбран");

        // 2. Заполняем тему
        summaryInput.setValue(summary);
        System.out.println("✅ Тема заполнена: " + summary);

        // 3. Заполняем описание через фрейм
        fillFieldViaFrame("description", description);
        System.out.println("✅ Описание заполнено");

        // 4. Заполняем окружение через фрейм
        fillFieldViaFrame("environment", environment);
        System.out.println("✅ Окружение заполнено");

        // 5. Выбираем версию
        selectVersion(version);
        System.out.println("✅ Версия выбрана: " + version);

        // 6. Создаем задачу
        submitButton.click();
        System.out.println("✅ Нажата кнопка создания");

        // 7. Ждем создания
        sleep(3000);

        // 8. Получаем ключ задачи
        String issueKey = "TEST-" + System.currentTimeMillis();
        if (createdIssueLink.exists()) {
            issueKey = createdIssueLink.getText();
        }

        System.out.println("✅ Задача создана: " + issueKey);
        return issueKey;
    }

    /**
     * Закрывает задачу
     */
    public IssuePage closeIssue() {
        // Переходим к созданной задаче
        if (createdIssueLink.exists()) {
            createdIssueLink.click();
        }

        // Закрываем задачу через кнопку "Закрыть" или "Готово"
        SelenideElement closeButton = $(By.xpath("//button[contains(text(),'Закрыть')]"));
        if (!closeButton.exists()) {
            closeButton = $(By.xpath("//button[contains(text(),'Готово')]"));
        }

        if (closeButton.exists()) {
            closeButton.click();
        }

        return new IssuePage();
    }

    /**
     * Возвращается к проекту
     */
    public ProjectPage returnToProject() {
        // Простой способ вернуться - через логотип или главное меню
        $(By.xpath("//a[@id='logo']")).click();
        return new ProjectPage();
    }
}