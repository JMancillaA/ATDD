import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HackerTyperTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        driver = new EdgeDriver(options);
        // Usar el constructor compatible con Selenium 3.x
        wait = new WebDriverWait(driver, 10);
        driver.manage().window().maximize();
    }

    @Test
    public void escribirComoHacker() throws Exception {
        driver.get("https://hackertyper.net/");

        // Hacer clic en la página para activarla
        WebElement body = driver.findElement(By.tagName("body"));
        body.click();

        // Enviar múltiples teclas simulando que el usuario teclea
        for (int i = 0; i < 20; i++) {
            body.sendKeys("HACK ");
            Thread.sleep(100); // Espera para ver el efecto
        }

        // Captura de pantalla
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Files.copy(screenshot.toPath(), Paths.get("hackertyper_result.png"));
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
