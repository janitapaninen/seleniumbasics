package tests;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by jtapaninen on 06/06/17.
 */
public class TestDemo
{

    @Test
    public void runTests() throws InterruptedException
    {
        //Testin alustusta
        System.setProperty("webdriver.gecko.driver", "/Users/jtapaninen/projects/geckodriver");
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("marionette", false);
        WebDriver driver = new FirefoxDriver(capabilities);

        driver.get("https://www.google.com");
        testSearchResult(driver);

        driver.get("https://www.signom.com/corporate/PowerForms.signom");
        testFormFilling(driver);

        driver.quit();
    }

    public static void testFormFilling(WebDriver driver) throws InterruptedException
    {

        driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[3]/div[3]/div[2]/div/div[1]/a[7]")).click();

        driver.findElement(By.name("personName")).sendKeys("Testi Mies");
        driver.findElement(By.name("billNo")).sendKeys("123123123");
        driver.findElement(By.name("companyName")).sendKeys("Best company");
        driver.findElement(By.name("bic")).sendKeys("2220746-7");

        List <WebElement>radioButtons = driver.findElements(By.name("chosenPayment"));

        for (WebElement radio: radioButtons)
        {
            if (radio.getAttribute("value").equalsIgnoreCase("2"))
            {
                radio.click();
            }
        }

        driver.findElement(By.name("entryDate")).sendKeys("22.02.2010");
        driver.findElement(By.name("entrySum")).sendKeys("150");
        driver.findElement(By.name("forPaymentSum")).sendKeys("50");

        driver.findElement(By.id("contract_file_upload")).sendKeys("/Users/jtapaninen/projects/hello.pdf");
        driver.findElement(By.id("upload_submit")).click();

        new WebDriverWait(driver, 30000)
                .until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "Asiakirjatiedosto hello.pdf on"));

        driver.findElement(By.id("submit_power_form")).click();

        new WebDriverWait(driver, 30000)
                .until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "Lomakkeen tiedoissa oli virheitä"));

    }

    private void testSearchResult(WebDriver driver)
    {
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        driver.findElement(By.id("lst-ib")).sendKeys("Sähköinen allekirjoitus");
        driver.findElement(By.id("lst-ib")).sendKeys(Keys.ENTER);

        new WebDriverWait(driver, 10000)
                .until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "company.signom.com"));


    }

}
