package edujira.ifellow.ru.Tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.openqa.selenium.WebDriver;

import java.util.logging.Logger;

import static com.codeborne.selenide.Selenide.open;

abstract public class BaseTest {
    private static final Logger logger = Logger.getLogger(BaseTest.class.getName());
    protected final String PRODUCT_URL = "https://edujira.ifellow.ru";

    @Rule
    public TestName name = new TestName();

    @Before
    public void setUp() {
        // Set up WebDriverManager
        WebDriverManager.chromedriver().setup();

        // Set up Selenide configuration
        Configuration.browser = "chrome";
        Configuration.baseUrl = PRODUCT_URL;
        Configuration.timeout = 10000;
        Configuration.reportsFolder = "target/surefire-reports";
        Configuration.headless = Boolean.getBoolean("headless");

        // Открываем базовый URL перед получением драйвера
        open(Configuration.baseUrl);

        // Теперь можно получить драйвер
        WebDriver webDriver = WebDriverRunner.getWebDriver();
        webDriver.manage().window().maximize();

        logger.info("Starting test: " + name.getMethodName());
    }

    @After
    public void tearDown() {
        logger.info("Finished test: " + name.getMethodName());
        Selenide.closeWebDriver();
    }
}