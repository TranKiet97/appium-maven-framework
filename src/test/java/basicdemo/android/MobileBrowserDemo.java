package basicdemo.android;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.testng.annotations.*;
import ui.common.BaseTest;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class MobileBrowserDemo extends BaseTest {
    private AndroidDriver driver;

    @BeforeClass
    public void beforeClass() {
        ConfigureAppium();
    }

    @BeforeMethod
    public void beforeTest() throws MalformedURLException, URISyntaxException {
        driver = LaunchBrowser();
    }

    @Test
    public void GoogleSearchingTest() throws MalformedURLException, URISyntaxException {
        driver.get("https://google.com");
        driver.findElement(By.name("q")).sendKeys("Kane vs UT");
        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
    }

    @Test
    public void ActionOnBrowserTest() throws InterruptedException {
        driver.get("https://rahulshettyacademy.com/angularAppdemo/");
        driver.findElement(By.xpath("//button[contains(@class, 'navbar-toggler')]")).click();
        driver.findElement(By.xpath("//a[text()='Products ']")).click();
        JavascriptExecutor jsExecutor = driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//a[text()='Devops']")));
        Thread.sleep(5000);
    }

    @AfterMethod
    public void afterTest() {
        driver.quit();
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        TearDown();
    }
}
