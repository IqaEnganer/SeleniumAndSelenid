import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CallBackTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        System.setProperty("webdriver.chrome.driver", "./driver/win/chromedriver.exe");
    }

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestSomething() {
        driver.get("http://localhost:9999");
        List<WebElement> inputField = driver.findElements(By.className("input__control"));
        inputField.get(0).sendKeys("Васиоий");
        inputField.get(1).sendKeys("+79270000000");
        driver.findElement(By.className("checkbox__control")).click();
        driver.findElement(By.tagName("button")).click();
        String actualMessage = driver.findElement(By.className("alert-success")).getText();
        String expectedMessage = "Ваша заявка успешно отправлена";
        assertEquals(expectedMessage, actualMessage.strip());
    }
    @Test
    void shouldTestSomething1() {
        driver.get("http://localhost:9999");
        /*List<WebElement> inputField = driver.findElements(By.className("input__control"));
        inputField.get(0).sendKeys("Васиоий");
        inputField.get(1).sendKeys("+79270000000");*/
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("Василий");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("+79384356464");
        driver.findElement(By.cssSelector(".checkbox__control")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualMessage = driver.findElement(By.cssSelector(".alert-success")).getText();
        String expectedMessage = "Ваша заявка успешно отправлена";
        assertEquals(expectedMessage, actualMessage.strip());
    }

}
