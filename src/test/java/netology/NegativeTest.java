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

public class NegativeTest {
    private WebDriver driver;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        //Загрузить страницу
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        options.addArguments("window-size=1800x900");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");

    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void submitAnEmptyForm() throws InterruptedException { //отправка пустой формы
        driver.findElement(By.cssSelector("button.button")).click();
        var actualElement = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"));
        var actualText = actualElement.getText().trim();
        assertEquals("Поле обязательно для заполнения", actualText);
        assertTrue(actualElement.isDisplayed());

    }

    @Test
    void submittingFormWithoutPhoneNumber() throws InterruptedException { //отправка формы без номера телефона
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов-Иванович Иван");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        var actualElement = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"));
        var actualText = actualElement.getText().trim();
        assertEquals("Поле обязательно для заполнения", actualText);
        assertTrue(actualElement.isDisplayed());

    }

    @Test
    void submittingFormWithoutСheckbox() throws InterruptedException {//отправка формы без чек бокса
        { //отправка формы без чек-бокса
            driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Дмитрий Лютиков");
            driver.findElement(By.cssSelector("[data-test-id='phone'] .input__control")).sendKeys("+79200025741");
            driver.findElement(By.cssSelector("button.button")).click();
            var actualElement = driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid .checkbox__text"));
            var actualText = actualElement.getText().trim();
            assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и " +
                    "разрешаю сделать запрос в бюро кредитных историй", actualText);
            assertTrue(actualElement.isDisplayed());

        }
    }

    @Test
    void submittingFormWithoutinvalidLastName() throws InterruptedException { //отправка формы с невалидной фамилией
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Gfffkk");
        driver.findElement(By.cssSelector("[data-test-id='phone'] .input__control")).sendKeys("+79200025741");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        var actualElement = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"));
        var actualText = actualElement.getText().trim();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actualText);
        assertTrue(actualElement.isDisplayed());

    }

    @Test
    void submittingFormWithoutLongNumber() throws InterruptedException { //отправка формы с длинным номером
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов-Иванович Иван");
        driver.findElement(By.cssSelector("[data-test-id='phone'] .input__control")).sendKeys("+792000257411");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        var actualElement = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"));
        var actualText = actualElement.getText().trim();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actualText);
        assertTrue(actualElement.isDisplayed());
    }

    @Test
    void submittingFormWithoutInvalideNumber() throws InterruptedException { //отправка формы с невалидным номером
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов-Иванович Иван");
        driver.findElement(By.cssSelector("[data-test-id='phone'] .input__control")).sendKeys("DDff");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        var actualElement = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"));
        var actualText = actualElement.getText().trim();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actualText);
        assertTrue(actualElement.isDisplayed());
    }

}
