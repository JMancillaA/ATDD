import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.*;
import org.testng.Assert;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

/****************************************/
// Historia de Usuario: Como usuario quiero realizar una búsqueda en Google y verificar los resultados
//
// Prueba de Aceptacion: Verificar que aparezcan resultados al realizar una búsqueda
//
// 1. Ingresar a la pagina de Google: https://www.google.com
// 2. Ingresar un término de búsqueda
// 3. Realizar la búsqueda
// 4. Verificar que existan resultados
//
// Resultado Esperado: Se deben mostrar resultados de búsqueda
/****************************************/

public class GoogleSearchResultTest {
    
    private WebDriver driver;
    
    @BeforeTest
    public void setDriver() throws Exception {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        driver = new EdgeDriver(options);
    }
    
    @Test
    public void verificarResultadosBusqueda() {
        // Preparación
        String googleUrl = "https://www.google.com";
        driver.get(googleUrl);
        
        // Crear espera explícita
        WebDriverWait wait = new WebDriverWait(driver, 20);
        
        // Manejar el diálogo de consentimiento de cookies si aparece
        try {
            WebElement consent = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//button//*[contains(text(), 'Accept all')]"))
            );
            consent.click();
        } catch (Exception e) {
            // El diálogo puede no aparecer, continuamos
        }
        
        // Ejecución
        WebElement searchBox = wait.until(
            ExpectedConditions.elementToBeClickable(By.name("q"))
        );
        searchBox.sendKeys("Gatos negros bebes");
        searchBox.submit();
        
        // Verificación
        try {
            // Esperar y verificar resultados usando múltiples selectores
            WebElement results = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.id("search"))
            );
            Assert.assertTrue(results.isDisplayed(), "Los resultados de búsqueda deben ser visibles");
            
            // Buscar el primer resultado por un selector más genérico
            WebElement firstResult = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("#search div[data-hveid]")
                )
            );
            Assert.assertTrue(firstResult.isDisplayed(), "Debe existir al menos un resultado de búsqueda");
            
        } catch (Exception e) {
            Assert.fail("No se pudieron encontrar los resultados de búsqueda: " + e.getMessage());
        }
    }
    
    @AfterTest
    public void closeDriver() throws Exception {
        driver.quit();
    }
}
