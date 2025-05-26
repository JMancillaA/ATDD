import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.*;
import org.testng.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.ArrayList;

public class BotonWhatsappTest {

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
    public void verificarBotonWhatsappRedirecciona() {
        // Paso 1: Abrir archivo local HTML
        String rutaLocal = "file:///C:/Users/HP%20VICTUS/OneDrive/Documentos/GitHub/Residencial-Arcangel-Miguel/index.html";
        driver.get(rutaLocal);

        // Paso 2: Navegar al tab de contactos (simulando clic)
        WebElement linkContactos = driver.findElement(By.xpath("//a[contains(text(),'Contactos')]"));
        linkContactos.click();

        // Paso 3: Esperar que se muestre el contenido del tab
        WebElement seccionContactos = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("contactos")));
        Assert.assertTrue(seccionContactos.isDisplayed(), "La sección de contactos debe estar visible");

        // Paso 4: Buscar el botón flotante de WhatsApp
        WebElement botonWhatsapp = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='https://wa.me/59172207330']")));
        Assert.assertTrue(botonWhatsapp.isDisplayed(), "El botón flotante de WhatsApp debe ser visible");

        // Paso 5: Clickear el botón y cambiar a la nueva pestaña
        botonWhatsapp.click();
        ArrayList<String> ventanas = new ArrayList<>(driver.getWindowHandles());
        if (ventanas.size() > 1) {
            driver.switchTo().window(ventanas.get(1));
        }

        // Paso 6: Verificar URL de redirección
        wait.until(ExpectedConditions.urlContains("https://api.whatsapp.com/send/?phone=59172207330&text&type=phone_number&app_absent=0"));
        String urlActual = driver.getCurrentUrl();
        Assert.assertEquals(urlActual, "https://api.whatsapp.com/send/?phone=59172207330&text&type=phone_number&app_absent=0", "La redirección debe ser correcta");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
