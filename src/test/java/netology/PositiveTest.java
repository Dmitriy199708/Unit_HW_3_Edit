package netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PositiveTest {
        private  WebDriver driver;

        @BeforeAll
        public static void setupAll() { WebDriverManager.chromedriver().setup();}

        @BeforeEach
        void setUp() {
            //Загрузить страницу
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--no-sandbox");
            options.addArguments("--headless");
            options.addArguments("window-size=1800x900");
            driver = new ChromeDriver(options);
            driver.get("http://localhost:7777");
        }
        @AfterEach
        void tearDown() {
            driver.quit();
            driver = null;
        }
        @Test
        void fillingAllFieldsWithValidValues() throws InterruptedException {
            driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Лютиков-Цветочкин Дмитрий");
            driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79200011585");
            driver.findElement(By.cssSelector("#root > div > form > div:nth-child(3) > label > span.checkbox__box")).click();
            driver.findElement(By.cssSelector("#root > div > form > div:nth-child(4) > button")).click();
            var actualElement = driver.findElement(By.cssSelector("[data-test-id=order-success]"));
            var  actualText = actualElement.getText().trim();
            assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actualText);
            assertTrue(actualElement.isDisplayed());
//        Thread.sleep(2000);
        }

    }

//    @BeforeAll
//    public static void setupAll() {
//        System.setProperty("webdriver.chrome.driver", "D:\\IdeaProjects\\Unit_3\\driver\\win\\chromedriver.exe");
//
//    }
//    cssselector
//    button[type='button']
//    form .button
//    [class*='input__sub']

//      Xpath
//  //span[@class='input__sub']
//  //span[text()='Укажите точно как в паспорте']
//  //span[contains(text(), 'паспорте')]
//span[@class='input__sub' and text()='Укажите точно как в паспорте']