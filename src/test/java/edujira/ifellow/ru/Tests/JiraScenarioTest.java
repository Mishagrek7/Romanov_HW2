package edujira.ifellow.ru.Tests;

import edujira.ifellow.ru.Pages.CreateIssuePage;
import edujira.ifellow.ru.Pages.IssuePage;
import edujira.ifellow.ru.Pages.LoginPage;
import edujira.ifellow.ru.Pages.ProjectPage;
import org.junit.Test;

import static com.codeborne.selenide.Selenide.page;

public class JiraScenarioTest extends BaseTest {

    private final String USERNAME = "AT3"; // Заменить на реальные
    private final String PASSWORD = "Qwerty123"; // Заменить на реальные

    @Test
    public void test1_login() {
        System.out.println("=== Тест 1: Авторизация ===");

        LoginPage loginPage = page(LoginPage.class);
        loginPage.openLoginPage();

        ProjectPage projectPage = loginPage.login(USERNAME, PASSWORD);

        assert projectPage != null : "Не удалось авторизоваться!";
        System.out.println("✓ Авторизация успешна");
    }

    @Test
    public void test2_navigateToProject() {
        System.out.println("=== Тест 2: Переход в проект ===");

        // Включает тест 1
        test1_login();

        ProjectPage projectPage = page(ProjectPage.class);
        projectPage.navigateToTestProject();

        System.out.println("✓ Переход в проект TEST выполнен");
    }

    @Test
    public void test3_verifyIssuesCount() {
        System.out.println("=== Тест 3: Проверка счетчика задач ===");

        test2_navigateToProject();

        ProjectPage projectPage = new ProjectPage();

        // Получаем начальное количество
        int initialCount = projectPage.getIssuesCount();
        System.out.println("Задач в проекте ДО создания: " + initialCount);

        // Создаем задачу
        CreateIssuePage createIssuePage = projectPage.clickCreateIssue();
        String issueKey = createIssuePage.createBug(
                "Тестовая задача от ifellow",
                "Романов",
                "Романов",
                "2.0"
        );

        System.out.println("Создана задача: " + issueKey);

        // ПРАВИЛЬНЫЙ СПОСОБ: Просто обновляем страницу проекта
        // Не нужно снова логиниться и переходить в проект!
        projectPage = new ProjectPage();
        projectPage.navigateToTestProject(); // Просто обновляем страницу проекта

        int newCount = projectPage.getIssuesCount();
        System.out.println("Задач в проекте ПОСЛЕ создания: " + newCount);

        // Проверяем что счетчик увеличился на 1
        assert newCount == initialCount + 1 :
                "Счетчик задач не увеличился! Было: " + initialCount + ", Стало: " + newCount;

        System.out.println("✓ Счетчик задач увеличился на 1");

    }

    @Test
    public void test4_verifyExistingIssue() {
        System.out.println("Тест 4: Проверка существующей задачи");

        test3_verifyIssuesCount();

        IssuePage issuePage = new IssuePage();
        issuePage.searchAndOpenIssue("TestSeleniumATHomework");
        issuePage.verifyIssueStatus("Сделать");
        issuePage.verifyIssueVersion("Version 2.0");

        System.out.println("Существующая задача проверена");
    }

    @Test
    public void test5_createAndCloseBug() {
        System.out.println("Тест 5: Создание и закрытие бага");

        test4_verifyExistingIssue();

        IssuePage issuePage = new IssuePage();
        CreateIssuePage createIssuePage = issuePage.navigateToCreateIssue();

        String issueKey = createIssuePage.createBug(
                "Автотест баг - Selenium ДЗ",
                "Описание бага",
                "Окружение",
                "2.0"
        );

        System.out.println("Создана задача: " + issueKey);

        issuePage = createIssuePage.closeIssue();

        System.out.println("✓ Задача создана и закрыта");
        System.out.println("Все тесты пройдены успешно!");
    }
}