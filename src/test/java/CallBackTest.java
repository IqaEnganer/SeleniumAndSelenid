import com.codeborne.selenide.selector.ByText;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CallBackTest {
    private WebDriver driver;


    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    //Все поля заполнены c валидными значениями
    @Test
    void shouldCheckValidValues() {
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79384356464");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Вектор Мадара");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualMessage = driver.findElement(By.cssSelector("[data-test-id]")).getText();
        String expectedMessage = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expectedMessage, actualMessage.strip());
    }

    //Все поля пустые
    @Test
    void shouldCheckEmptyField() {
        driver.findElement(By.cssSelector("button")).click();
        String actualMessage = driver.findElement(By.className("input__sub")).getText();
        String expectedMessage = "Поле обязательно для заполнения";
        assertEquals(expectedMessage, actualMessage);
    }

    // В поле (Фамилия и имя) заполнено только имя
    // Проверка возможности ввода только имени в поле
    @Test
    void shouldCheckAbilityEnterOnlyName() {
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79384356464");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Вектор");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualMessage = driver.findElement(By.cssSelector("[data-test-id]")).getText();
        String expectedMessage = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expectedMessage, actualMessage.strip());
    }

    //Все поля заполнены, флажок не задействован.
    @Test
    void shouldCheckTheCheckbox() {
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79384356464");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Вектор Гарбушев");
        driver.findElement(By.cssSelector("button")).click();
        driver.findElement(By.cssSelector(".input_invalid"));
    }

    // Все поля заполнены кроме телефона
    @Test
    void shouldCheckNumberPhone() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Вектор Гарбушев");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualMessage = driver.findElement(By.cssSelector(".input_theme_alfa-on-white.input_invalid .input__sub")).getText();
        String expectedMessage = "Поле обязательно для заполнения";
        assertEquals(expectedMessage, actualMessage.strip());
    }

    // В поле (Номер телефона) введено не валидное значение
    @Test
    void shouldCheckValidationPhoneNumber() {
        List<WebElement> elements = driver.findElements(By.className("input__sub"));
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+8938435664");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Вектор Мадара");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualMessage = elements.get(1).getText();
        String expectedMessage = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expectedMessage, actualMessage.strip());
    }


    // Поле имя с английскими символами
    @Test
    void shouldCheckEnglishLetters() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Gi Mi");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79384356464");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualMessage = driver.findElement(By.cssSelector(".input_size_m .input__sub")).getText();
        String expectedMessage = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expectedMessage, actualMessage.strip());
    }

    // Поле (Фамилия и имя) пустое.
    @Test
    void shouldIssueMandatoryMessageFieldName() throws InterruptedException {
        List<WebElement> elements = driver.findElements(By.className("input__sub"));
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79384356464");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.cssSelector("button")).click();
        //Thread.sleep(5000);
        String actualMessage = elements.get(0).getText();
        String expectedMessage = "Поле обязательно для заполнения";
        assertEquals(expectedMessage, actualMessage.strip());
    }
}
