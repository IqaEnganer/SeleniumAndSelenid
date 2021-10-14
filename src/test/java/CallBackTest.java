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

public class CallBackTest {
    private WebDriver driver;


    @BeforeAll
    static void setUpAll() {
        System.setProperty("webdriver.chrome.driver", "./driver/win/chromedriver.exe");
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    //Все поля заполнены
    @Test
    void shouldTestSomething1() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79384356464");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Вектор Мадара");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualMessage = driver.findElement(By.className("Success_successBlock__2L3Cw")).getText();
        String expectedMessage = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expectedMessage, actualMessage.strip());
    }

    //Все поля пустые
    @Test
    void shouldTestSomething12() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("button")).click();
        String actualMessage = driver.findElement(By.className("input__sub")).getText();
        String expectedMessage = "Поле обязательно для заполнения";
        assertEquals(expectedMessage, actualMessage);
    }

    // В поле (Фамилия и имя) заполнено только имя
    @Test
    void shouldTestSomething13() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79384356464");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Вектор");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualMessage = driver.findElement(By.className("Success_successBlock__2L3Cw")).getText();
        String expectedMessage = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expectedMessage, actualMessage.strip());
    }

    //Все поля заполнены кроме флажка
    @Test
    void shouldCheckTheCheckbox() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79384356464");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Вектор Гарбушев");
        driver.findElement(By.cssSelector("button")).click();
        String actualMessage = driver.findElement(By.cssSelector(".input_invalid")).getText();
        String expectedMessage = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        assertEquals(expectedMessage, actualMessage.strip());
    }

    // Все поля заполнены кроме телефона
    @Test
    void shouldTestSomething1354() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Вектор Гарбушев");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualMessage = driver.findElement(By.cssSelector(".input_theme_alfa-on-white.input_invalid .input__sub")).getText();
        String expectedMessage = "Поле обязательно для заполнения";
        assertEquals(expectedMessage, actualMessage.strip());
    }

    // Поле имя с английскими символами
    @Test
    void shouldTestSomething1354w() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Gi Mi");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79384356464");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualMessage = driver.findElement(By.cssSelector(".input_size_m .input__sub")).getText();
        String expectedMessage = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expectedMessage, actualMessage.strip());
    }

}
