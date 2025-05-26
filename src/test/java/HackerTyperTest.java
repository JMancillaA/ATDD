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

    /**
     * Prueba de aceptación #1
     * Simula escritura en HackerTyper y verifica captura
     */
    @Test
    public void escribirComoHacker() throws Exception {
        // 1. Navegar a la página
        driver.get("https://hackertyper.net/");
        Assert.assertTrue(driver.getTitle().toLowerCase().contains("hacker"));

        // 2. Seleccionar el body
        WebElement body = driver.findElement(By.tagName("body"));
        Assert.assertNotNull(body);

        // 3. Clic en body para activarlo
        body.click();

        // 4. Simular escritura
        for (int i = 0; i < 20; i++) {
            body.sendKeys("HACK ");
            Thread.sleep(100);
        }

        // 5. Tomar captura de pantalla
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        // 6. Guardar imagen
        Files.copy(screenshot.toPath(), Paths.get("hackertyper_result.png"), StandardCopyOption.REPLACE_EXISTING);
        Assert.assertTrue(Files.exists(Paths.get("hackertyper_result.png")));
    }

    /**
     * Prueba de aceptación #2
     * Verifica elementos clave en la página Browse de Neocities
     */
    @Test
    public void verificarTituloDeBrowse() {
        // 1. Cargar página Browse
        driver.get("https://neocities.org/browse");
        wait.until(ExpectedConditions.titleContains("Browse"));
        Assert.assertEquals("Neocities - Browse", driver.getTitle());

        // 2. Verificar encabezado
        WebElement header = driver.findElement(By.xpath("//h1[text()='Sites on Neocities']"));
        Assert.assertTrue(header.isDisplayed());

        // 3. Verificar campo dropdown de orden
        WebElement dropdown = driver.findElement(By.id("sort_by"));
        Assert.assertTrue(dropdown.isDisplayed());

        // 4. Verificar campo de filtro por tag
        WebElement tagField = driver.findElement(By.id("tag"));
        Assert.assertTrue(tagField.isDisplayed());

        // 5. Enviar un valor al campo
        tagField.clear();
        tagField.sendKeys("art");

        // 6. Enviar formulario
        tagField.submit();
        wait.until(ExpectedConditions.urlContains("tag=art"));
        Assert.assertTrue(driver.getCurrentUrl().contains("tag=art"));
    }

    /**
     * Prueba de aceptación #3
     * Interactúa con el primer sitio listado
     */
    @Test
    public void clickEnPrimerSitioListado() {
        // 1. Cargar página Browse
        driver.get("https://neocities.org/browse");
        Assert.assertTrue(driver.getTitle().contains("Browse"));

        // 2. Esperar al menos un sitio cargado
        WebElement primerEnlace = wait.until(
            ExpectedConditions.elementToBeClickable(By.cssSelector("li[id^='username_'] .title a"))
        );
        Assert.assertTrue(primerEnlace.isDisplayed());

        // 3. Capturar URL actual
        String urlInicial = driver.getCurrentUrl();

        // 4. Hacer clic en el primer enlace
        primerEnlace.click();

        // 5. Esperar redirección
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(urlInicial)));

        // 6. Verificar cambio de página
        Assert.assertNotSame(driver.getCurrentUrl(), urlInicial);
        Assert.assertFalse(driver.getCurrentUrl().contains("browse"));
    }

    // Prueba adicional (no cuenta como aceptación pero complementa funcionalidad)
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
