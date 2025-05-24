import java.util.concurrent.TimeUnit;
import junit.framework.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.testng.Assert.*;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

/****************************************/
//Historia de Usuario: Como usuario nuevo quiero registrar mis datos en Dzone.com
//
//Prueba de Aceptacion: Verificar que se muestren alertas para los campos obligatorios
//
//1. Ingresar a la pagina de DZone  www.dzone.com
//2. Hacer en el link Join
//3. Presionar el boton Join
//
//Resultado Esperado: Se deben mostrar mensajes de alerta para los campos obligatorios que no fueron llenados
/****************************************/

//Para ejecutar en la linea de comando: mvn clean compile test -Dtest=RegistroDZoneTest

public class RegistroDZoneTest {
    
    private WebDriver driver;
    
    @BeforeTest
    public void setDriver() throws Exception{
        
    	 String path = "/Users/gustavo/apps/chromedriver-mac-x64_119/chromedriver119";
         
         System.setProperty("webdriver.chrome.driver", path);
         
         WebDriverManager.chromedriver().setup();
         driver = new ChromeDriver();
        
    }
    
    @Test
    public void verificarMensajeErrorAlRegistrar(){
        
        /********** Preparacion de la prueba **********/
    	
    	//1. Ingresar a la pagina de DZone
        String dzoneUrl = "https://dzone.com";
        driver.get(dzoneUrl);
        
        /*********** Logica de la prueba***********/
        
        //2. Hacer en el link Join
        
        WebElement joinLink = driver.findElement(By.xpath("//*[@id=\"unauthenticated-block\"]/div[2]/a[2]"));
        
        joinLink.click();
        
        //Esperamos 3 segundos para que se renderice la siguiente pagina
        try{
            TimeUnit.SECONDS.sleep(3);
        }
        catch(InterruptedException e){
            e.printStackTrace();
     
        }
        
        //3. Presionar el boton Join
        WebElement joinButton = driver.findElement(By.xpath("//*[@id=\"login\"]/div/div[2]/div[3]/button"));
        
        joinButton.click();

        try{
            TimeUnit.SECONDS.sleep(5);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        
       
        
        /************Verificacion de la situacion esperada - Assert***************/
        
        
        WebElement iconAlert = driver.findElement(By.xpath("//*[@id=\"login\"]/div/div[2]/div[1]/div[2]/form/div[1]/span[2]/i"));
        Assert.assertEquals(true, iconAlert.isDisplayed());
        
        System.out.println("Se muestra el icono? "+iconAlert.isDisplayed());
        
        //Validar el mensaje del correo
        WebElement 	emailErrorMessage = driver.findElement(
        		By.xpath("//div[@data-validate=\"Please enter a valid email address\"]"));
        
        String attribute = emailErrorMessage.getAttribute("data-validate");
        System.out.println("Valor del attribute::: "+attribute);
        
        Assert.assertEquals("Please enter a valid email address", attribute);
        
        
    }
    
    
    
    @AfterTest
    public void closeDriver() throws Exception{
        driver.quit();
    }
    
    
    
    /*
     
     
        
        
        
        
        
        WebElement iconAlert = driver.findElement(By.xpath("//*[@id=\"login\"]/div/div[2]/div[1]/div[2]/form/div[1]/span[2]/i"));
        Assert.assertEquals(true, iconAlert.isDisplayed());
        
        System.out.println("Se muestra el icono? "+iconAlert.isDisplayed());
        
        //Validar el mensaje del correo
        WebElement 	emailErrorMessage = driver.findElement(
        		By.xpath("//div[@data-validate=\"Please enter a valid email address\"]"));
        String attribute = emailErrorMessage.getAttribute("data-validate");
        System.out.println("Valor del attribute::: "+attribute);
        
        Assert.assertEquals("Please enter a valid email address", attribute);
        
        
        
        
        
        
        
        
        
        //Validar el mensaje del username
        
        WebElement usernameErrorMessage = driver.findElement(By.xpath("//div[@data-validate=\"Please enter a valid username\"]"));
        attribute = usernameErrorMessage.getAttribute("data-validate");
        System.out.println("Valor del attribute::: "+attribute);
        
        Assert.assertEquals("Please enter a valid username", attribute);
        
         
        
        //Validate el check del agreement
        WebElement checkAgreement = driver.findElement(By.xpath("//*[@id=\"accept-agreement\"]"));
        
        checkAgreement.click();
        
        try{
            TimeUnit.SECONDS.sleep(3);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        
        
        //Adicionar codigo para llamar al API de la herramienta de administracion de casos de prueba

     
     * */
    
    
}
