import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.testng.Assert;
import io.github.bonigarcia.wdm.WebDriverManager;

public class contactos {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        driver = new EdgeDriver(options);
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void pruebaFormularioContacto() {
        // 1. Abrir el archivo HTML local
        String rutaLocal = "file:///D:/Codigos/Github/Residencial-Arcangel-Miguel/index.html";
        driver.get(rutaLocal);

        // 2. Hacer clic en la pestaña "Contactos"
        WebElement tabContactos = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Contactos')]")));
        tabContactos.click();

        // 3. Esperar a que se active la pestaña con clase "active"
        WebElement seccionContactos = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("#contactos.tab-content.active")
        ));
        Assert.assertTrue(seccionContactos.isDisplayed(), "La sección de contactos debe estar visible");

        // 4. Llenar el formulario
        WebElement nombre = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nombre_contacto")));
        WebElement email = driver.findElement(By.id("email_contacto"));
        WebElement asunto = driver.findElement(By.id("asunto_contacto"));
        WebElement mensaje = driver.findElement(By.id("mensaje_contacto"));

        nombre.sendKeys("Juan Pérez");
        email.sendKeys("juan.perez@email.com");
        asunto.sendKeys("Consulta de disponibilidad");
        mensaje.sendKeys("Hola, quisiera saber si hay habitaciones disponibles para la próxima semana.");

        // 5. Verificar los valores ingresados
        Assert.assertEquals(nombre.getAttribute("value"), "Juan Pérez");
        Assert.assertEquals(email.getAttribute("value"), "juan.perez@email.com");
        Assert.assertEquals(asunto.getAttribute("value"), "Consulta de disponibilidad");
        Assert.assertTrue(mensaje.getAttribute("value").contains("habitaciones disponibles"));

        // 6. Enviar el formulario de forma robusta
        WebElement botonEnviar = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("form#contactForm button[type='submit']"))
        );

        // Scroll al botón para que sea visible
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", botonEnviar);

        // Si el botón de WhatsApp interfiere, ocultarlo
        ((JavascriptExecutor) driver).executeScript("let w = document.querySelector('a[href*=\"wa.me\"]'); if (w) w.style.display='none';");

        // Click con JS para evitar intercepción
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", botonEnviar);

        // 7. Manejar alerta si se muestra
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();
        } catch (Exception e) {
            // Si no hay alerta, no hacer nada
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
