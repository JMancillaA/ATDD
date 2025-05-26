import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import junit.framework.Assert;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class HackerTyperTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        driver = new EdgeDriver(options);
        wait = new WebDriverWait(driver, 10); // Selenium 3.x compatible
        driver.manage().window().maximize();
    }

    @Test
    public void escribirComoHacker() throws Exception {
        driver.get("https://hackertyper.net/");

        WebElement body = driver.findElement(By.tagName("body"));
        body.click();

        for (int i = 0; i < 20; i++) {
            body.sendKeys("HACK ");
            Thread.sleep(100);
        }

        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Files.copy(screenshot.toPath(), Paths.get("hackertyper_result.png"), StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    public void verificarTituloDeBrowse() {
        driver.get("https://neocities.org/browse");
        wait.until(ExpectedConditions.titleContains("Browse"));
        Assert.assertEquals("Neocities - Browse", driver.getTitle());

        WebElement header = driver.findElement(By.xpath("//h1[text()='Sites on Neocities']"));
        Assert.assertNotNull(header);

        WebElement dropdown = driver.findElement(By.id("sort_by"));
        Assert.assertNotNull(dropdown);

        WebElement tagField = driver.findElement(By.id("tag"));
        Assert.assertNotNull(tagField);
    }

    @Test
    public void clickEnPrimerSitioListado() {
        driver.get("https://neocities.org/browse");

        // Espera y selecciona el primer enlace de sitio real
        WebElement primerEnlace = wait.until(
            ExpectedConditions.elementToBeClickable(By.cssSelector("li[id^='username_'] .title a"))
        );
        primerEnlace.click();

        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("browse")));
        Assert.assertFalse(driver.getCurrentUrl().contains("browse"));
    }

    @Test
    public void buscarSitioPorNombre() {
        driver.get("https://neocities.org/browse");

        WebElement campoBusqueda = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tag")));
        campoBusqueda.clear();
        campoBusqueda.sendKeys("cool");
        campoBusqueda.submit();

        wait.until(ExpectedConditions.urlContains("tag=cool"));
        Assert.assertTrue(driver.getCurrentUrl().contains("tag=cool"));
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
