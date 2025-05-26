import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.*;
import org.testng.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

public class ReclamosTest {

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
    public void verificarFormularioReclamosCompleto() {
        // Paso 1: Abrir archivo local HTML
        String rutaLocal = "file:///C:/Users/Joaquin/Documents/GitHub/Residencial-Arcangel-Miguel/index.html";
        driver.get(rutaLocal);

        // Paso 2: Navegar al tab de reclamos
        WebElement linkReclamos = driver.findElement(By.xpath("//a[contains(text(),'Reclamos')]"));
        linkReclamos.click();

        // Paso 3: Esperar que se muestre el contenido del tab de reclamos
        WebElement seccionReclamos = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reclamos")));
        Assert.assertTrue(seccionReclamos.isDisplayed(), "La sección de reclamos debe estar visible");

        // Paso 4: Verificar que el formulario esté presente
        WebElement formularioReclamos = driver.findElement(By.id("reclamos-form"));
        Assert.assertTrue(formularioReclamos.isDisplayed(), "El formulario de reclamos debe estar visible");

        // Paso 5: Llenar el formulario
        WebElement nombreField = driver.findElement(By.id("nombre_reclamo"));
        nombreField.sendKeys("Juan Pérez");

        WebElement emailField = driver.findElement(By.id("email_reclamo"));
        emailField.sendKeys("juan.perez@email.com");

        WebElement telefonoField = driver.findElement(By.id("telefono_reclamo"));
        telefonoField.sendKeys("72123456");

        WebElement habitacionField = driver.findElement(By.id("habitacion_reclamo"));
        habitacionField.sendKeys("205");

        // Paso 6: Seleccionar tipo de reclamo
        WebElement tipoReclamoSelect = driver.findElement(By.id("tipo_reclamo"));
        Select tipoReclamo = new Select(tipoReclamoSelect);
        tipoReclamo.selectByValue("reclamo");

        // Paso 7: Seleccionar departamento
        WebElement departamentoSelect = driver.findElement(By.id("departamento_reclamo"));
        Select departamento = new Select(departamentoSelect);
        departamento.selectByValue("limpieza");

        // Paso 8: Llenar el mensaje
        WebElement mensajeField = driver.findElement(By.id("mensaje_reclamo"));
        mensajeField.sendKeys("La habitación no fue limpiada adecuadamente durante mi estadía. Había polvo en los muebles y el baño necesitaba mejor mantenimiento.");

        // Paso 9: Verificar que todos los campos están llenos
        Assert.assertEquals(nombreField.getAttribute("value"), "Juan Pérez", "El campo nombre debe estar lleno");
        Assert.assertEquals(emailField.getAttribute("value"), "juan.perez@email.com", "El campo email debe estar lleno");
        Assert.assertEquals(telefonoField.getAttribute("value"), "72123456", "El campo teléfono debe estar lleno");
        Assert.assertEquals(habitacionField.getAttribute("value"), "205", "El campo habitación debe estar lleno");
        Assert.assertEquals(tipoReclamo.getFirstSelectedOption().getAttribute("value"), "reclamo", "Debe estar seleccionado 'reclamo'");
        Assert.assertEquals(departamento.getFirstSelectedOption().getAttribute("value"), "limpieza", "Debe estar seleccionado 'limpieza'");
        Assert.assertTrue(mensajeField.getAttribute("value").contains("limpiada adecuadamente"), "El mensaje debe contener el texto ingresado");

        // Paso 10: Enviar el formulario
        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit' and contains(@class, 'submit-btn')]"));
        Assert.assertTrue(submitButton.isDisplayed(), "El botón de envío debe estar visible");
        submitButton.click();

        // Paso 11: Verificar que aparece la alerta de confirmación (simulada por JavaScript)
        // Nota: En una implementación real, aquí verificaríamos la respuesta del servidor
        // Por ahora, verificamos que el formulario se puede enviar sin errores
    }

    @Test
    public void verificarCamposObligatorios() {
        // Paso 1: Abrir archivo local HTML
        String rutaLocal = "file:///C:/Users/Joaquin/Documents/GitHub/Residencial-Arcangel-Miguel/index.html";
        driver.get(rutaLocal);

        // Paso 2: Navegar al tab de reclamos
        WebElement linkReclamos = driver.findElement(By.xpath("//a[contains(text(),'Reclamos')]"));
        linkReclamos.click();

        // Paso 3: Esperar que se muestre el contenido del tab de reclamos
        WebElement seccionReclamos = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reclamos")));
        Assert.assertTrue(seccionReclamos.isDisplayed(), "La sección de reclamos debe estar visible");

        // Paso 4: Intentar enviar formulario vacío
        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit' and contains(@class, 'submit-btn')]"));
        submitButton.click();

        // Paso 5: Verificar que los campos obligatorios tienen el atributo required
        WebElement nombreField = driver.findElement(By.id("nombre_reclamo"));
        WebElement emailField = driver.findElement(By.id("email_reclamo"));
        WebElement tipoReclamoField = driver.findElement(By.id("tipo_reclamo"));
        WebElement departamentoField = driver.findElement(By.id("departamento_reclamo"));
        WebElement mensajeField = driver.findElement(By.id("mensaje_reclamo"));

        Assert.assertEquals(nombreField.getAttribute("required"), "true", "El campo nombre debe ser obligatorio");
        Assert.assertEquals(emailField.getAttribute("required"), "true", "El campo email debe ser obligatorio");
        Assert.assertEquals(tipoReclamoField.getAttribute("required"), "true", "El campo tipo de reclamo debe ser obligatorio");
        Assert.assertEquals(departamentoField.getAttribute("required"), "true", "El campo departamento debe ser obligatorio");
        Assert.assertEquals(mensajeField.getAttribute("required"), "true", "El campo mensaje debe ser obligatorio");
    }

    @Test
    public void verificarOpcionesSelectTipoReclamo() {
        // Paso 1: Abrir archivo local HTML
        String rutaLocal = "file:///C:/Users/Joaquin/Documents/GitHub/Residencial-Arcangel-Miguel/index.html";
        driver.get(rutaLocal);

        // Paso 2: Navegar al tab de reclamos
        WebElement linkReclamos = driver.findElement(By.xpath("//a[contains(text(),'Reclamos')]"));
        linkReclamos.click();

        // Paso 3: Esperar que se muestre el contenido del tab de reclamos
        WebElement seccionReclamos = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reclamos")));
        Assert.assertTrue(seccionReclamos.isDisplayed(), "La sección de reclamos debe estar visible");

        // Paso 4: Verificar opciones del select de tipo de reclamo
        WebElement tipoReclamoSelect = driver.findElement(By.id("tipo_reclamo"));
        Select tipoReclamo = new Select(tipoReclamoSelect);

        // Verificar que existen las opciones esperadas
        Assert.assertTrue(tipoReclamo.getOptions().stream().anyMatch(option -> option.getAttribute("value").equals("reclamo")), "Debe existir la opción 'reclamo'");
        Assert.assertTrue(tipoReclamo.getOptions().stream().anyMatch(option -> option.getAttribute("value").equals("sugerencia")), "Debe existir la opción 'sugerencia'");
        Assert.assertTrue(tipoReclamo.getOptions().stream().anyMatch(option -> option.getAttribute("value").equals("felicitacion")), "Debe existir la opción 'felicitacion'");
    }

    @Test
    public void verificarOpcionesSelectDepartamento() {
        // Paso 1: Abrir archivo local HTML
        String rutaLocal = "file:///C:/Users/Joaquin/Documents/GitHub/Residencial-Arcangel-Miguel/index.html";
        driver.get(rutaLocal);

        // Paso 2: Navegar al tab de reclamos
        WebElement linkReclamos = driver.findElement(By.xpath("//a[contains(text(),'Reclamos')]"));
        linkReclamos.click();

        // Paso 3: Esperar que se muestre el contenido del tab de reclamos
        WebElement seccionReclamos = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reclamos")));
        Assert.assertTrue(seccionReclamos.isDisplayed(), "La sección de reclamos debe estar visible");

        // Paso 4: Verificar opciones del select de departamento
        WebElement departamentoSelect = driver.findElement(By.id("departamento_reclamo"));
        Select departamento = new Select(departamentoSelect);

        // Verificar que existen las opciones esperadas
        Assert.assertTrue(departamento.getOptions().stream().anyMatch(option -> option.getAttribute("value").equals("recepcion")), "Debe existir la opción 'recepcion'");
        Assert.assertTrue(departamento.getOptions().stream().anyMatch(option -> option.getAttribute("value").equals("habitaciones")), "Debe existir la opción 'habitaciones'");
        Assert.assertTrue(departamento.getOptions().stream().anyMatch(option -> option.getAttribute("value").equals("limpieza")), "Debe existir la opción 'limpieza'");
        Assert.assertTrue(departamento.getOptions().stream().anyMatch(option -> option.getAttribute("value").equals("mantenimiento")), "Debe existir la opción 'mantenimiento'");
        Assert.assertTrue(departamento.getOptions().stream().anyMatch(option -> option.getAttribute("value").equals("seguridad")), "Debe existir la opción 'seguridad'");
        Assert.assertTrue(departamento.getOptions().stream().anyMatch(option -> option.getAttribute("value").equals("wifi")), "Debe existir la opción 'wifi'");
        Assert.assertTrue(departamento.getOptions().stream().anyMatch(option -> option.getAttribute("value").equals("otro")), "Debe existir la opción 'otro'");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}