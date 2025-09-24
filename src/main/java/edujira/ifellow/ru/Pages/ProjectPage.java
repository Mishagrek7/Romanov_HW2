package edujira.ifellow.ru.Pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;
import static org.junit.Assert.assertTrue;

public class ProjectPage {

    // XPath локаторы
    private final SelenideElement projectsMenu = $(By.xpath("//a[contains(text(),'Проекты') or contains(text(),'Projects')]"));
    private final SelenideElement testProjectLink = $(By.xpath("//a[@id='admin_main_proj_link_lnk']"));
    private final SelenideElement issueCountText = $(By.xpath("//div[@class='pager']//div[@class='showing']/span"));
    private final SelenideElement createIssueButton = $(By.xpath("//a[@id='create_link']"));

    public ProjectPage navigateToTestProject() {
        projectsMenu.shouldBe(Condition.visible).click();
        testProjectLink.shouldBe(Condition.visible).click();

        // Проверка перехода в проект Test
        $(By.xpath("//a[@title='Test']")).shouldBe(Condition.visible);
        assertTrue("Не удалось перейти в проект Test", $(By.xpath("//a[@title='Test']")).exists());
        return this;
    }

    public int getIssuesCount() {
        String countText = issueCountText.shouldBe(Condition.visible).getText();

        // Берём последнюю встречающуюся группу цифр (обычно это общее количество)
        String digits = countText.replaceAll("(?s).*?(\\d+)\\D*$", "$1");
        // если заменой не получилось, попробуем найти все числа и взять последнее
        if (!digits.matches("\\d+")) {
            String[] nums = countText.replaceAll("[^\\d\\s]", " ").trim().split("\\s+");
            assertTrue("Не найдены числа в счетчике: " + countText, nums.length > 0);
            digits = nums[nums.length - 1];
        }
        assertTrue("Не удалось извлечь количество задач из: " + countText, digits.matches("\\d+"));
        return Integer.parseInt(digits);
    }

    public CreateIssuePage clickCreateIssue() {
        createIssueButton.shouldBe(Condition.visible).click();
        $(By.xpath("//h2[contains(., 'Создание задачи') or contains(., 'Create Issue')]")).shouldBe(Condition.visible);
        assertTrue("Не удалось открыть форму создания задачи", $(By.xpath("//h2[contains(., 'Создание задачи') or contains(., 'Create Issue')]")).exists());
        return page(CreateIssuePage.class);
    }
}
