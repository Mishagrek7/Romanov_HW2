package edujira.ifellow.ru.Pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;
import static org.junit.Assert.assertTrue;

public class LoginPage {

    // XPath локаторы
    private final SelenideElement usernameInput = $(By.xpath("//input[@id='login-form-username']"));
    private final SelenideElement passwordInput = $(By.xpath("//input[@id='login-form-password']"));
    private final SelenideElement loginButton = $(By.xpath("//input[@id='login']"));
    private final SelenideElement userProfile = $(By.xpath("//a[@id='header-details-user-fullname']"));

    public LoginPage openLoginPage() {
        // Ждём загрузки формы логина
        usernameInput.shouldBe(Condition.visible);
        assertTrue("Поле логина не отображается", usernameInput.exists());
        return this;
    }

    public ProjectPage login(String username, String password) {
        usernameInput.setValue(username);
        passwordInput.setValue(password);
        loginButton.click();

        // Проверка успешной авторизации (ожидаем профиль)
        userProfile.shouldBe(Condition.visible);
        assertTrue("Авторизация не удалась - профиль пользователя не отображается", userProfile.exists());
        return page(ProjectPage.class);
    }
}
