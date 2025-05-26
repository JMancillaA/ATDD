import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginDashboardTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeTest
    public void setUp() {        // Configurar WebDriver
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        // Espera explícita de hasta 15 segundos
        wait = new WebDriverWait(driver, 15);
    }

    @Test
    public void verificarLoginYDashboard() {
        // 1. Abrir la página de login
        driver.get("https://opensource-demo.orangehrmlive.com");

        // 2. Esperar a que se muestre el formulario de autenticación
        WebElement usernameField = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("txtUsername"))
        );

        // 3. Ingresar usuario válido
        usernameField.sendKeys("Admin");

        // 4. Ingresar contraseña válida
        WebElement passwordField = driver.findElement(By.id("txtPassword"));
        passwordField.sendKeys("admin123");

        // 5. Pulsar el botón "Login"
        WebElement loginButton = driver.findElement(By.id("btnLogin"));
        loginButton.click();

        // 6. Verificar redirección al dashboard
        // Esperar a que aparezca el título "Dashboard"
        WebElement dashboardHeader = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h1[contains(text(), 'Dashboard')]")
            )
        );
        Assert.assertTrue(
            dashboardHeader.isDisplayed(),
            "El encabezado del Dashboard debe estar visible tras el login"
        );

        // 7. Cerrar sesión
        WebElement welcomeMenu = driver.findElement(By.id("welcome"));
        welcomeMenu.click();
        WebElement logoutLink = wait.until(
            ExpectedConditions.elementToBeClickable(By.linkText("Logout"))
        );
        logoutLink.click();

        // 8. Verificar que regresó al login
        WebElement loginPanel = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("logInPanelHeading"))
        );
        Assert.assertEquals(
            loginPanel.getText(),
            "LOGIN Panel",
            "Debe mostrarse de nuevo el panel de login tras hacer logout"
        );
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}