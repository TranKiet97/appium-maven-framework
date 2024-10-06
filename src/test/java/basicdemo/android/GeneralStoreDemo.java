package basicdemo.android;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import ui.common.BaseTest;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Set;

public class GeneralStoreDemo extends BaseTest {
    private AndroidDriver driver;

    @BeforeClass
    public void beforeClass() {
        ConfigureAppium();
    }

    @BeforeMethod
    public void beforeTest() throws MalformedURLException, URISyntaxException {
        driver = LaunchGeneralStoreApplication();
    }

    @Test
    public void FillingTheFormDetailForShoppingSuccessTest() throws InterruptedException {
        driver.findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys("Kane Tran");
        driver.hideKeyboard();

        driver.findElement(By.id("com.androidsample.generalstore:id/radioMale")).click();
        Assert.assertEquals(driver.findElement(By.id("com.androidsample.generalstore:id/radioMale")).getAttribute("checked"), "true");

        driver.findElement(By.id("com.androidsample.generalstore:id/spinnerCountry")).click();

        // https://developer.android.com/reference/androidx/test/uiautomator/UiScrollable
        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).setMaxSearchSwipes(50).scrollIntoView(text(\"Vietnam\"));"));
        driver.findElement(By.xpath("//android.widget.TextView[@resource-id='android:id/text1' and @text='Vietnam']")).click();

        driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();

        Thread.sleep(3000);
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        explicitWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("com.androidsample.generalstore:id/toolbar_title"))));
    }

    @Test
    public void FillingTheFormDetailWithMissingFieldTest() {
        driver.findElement(By.id("com.androidsample.generalstore:id/radioFemale")).click();
        Assert.assertEquals(driver.findElement(By.id("com.androidsample.generalstore:id/radioFemale")).getAttribute("checked"), "true");

        driver.findElement(By.id("com.androidsample.generalstore:id/spinnerCountry")).click();

        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).setMaxSearchSwipes(50).scrollIntoView(text(\"Australia\"));"));
        driver.findElement(By.xpath("//android.widget.TextView[@resource-id='android:id/text1' and @text='Australia']")).click();

        driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();

        // Handle toast message
        String errorMessage = driver.findElement(By.xpath("(//android.widget.Toast)[1]")).getAttribute("name");
        Assert.assertEquals(errorMessage, "Please enter your name");
    }

    @Test
    public void ScrollingInProductListTest() {
        driver.findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys("Kane Tran");
        driver.hideKeyboard();
        driver.findElement(By.id("com.androidsample.generalstore:id/radioMale")).click();
        Assert.assertEquals(driver.findElement(By.id("com.androidsample.generalstore:id/radioMale")).getAttribute("checked"), "true");
        driver.findElement(By.id("com.androidsample.generalstore:id/spinnerCountry")).click();
        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).setMaxSearchSwipes(50).scrollIntoView(text(\"Australia\"));"));
        driver.findElement(By.xpath("//android.widget.TextView[@resource-id='android:id/text1' and @text='Australia']")).click();
        driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();

        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).setMaxSearchSwipes(50).scrollIntoView(text(\"Jordan 6 Rings\"));"));
        driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.androidsample.generalstore:id/productName' and @text='Jordan 6 Rings']/following-sibling::android.widget.LinearLayout//android.widget.TextView[@text='ADD TO CART']")).click();

        driver.findElement(By.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();
        driver.findElement(By.xpath("//android.widget.TextView[@text='Jordan 6 Rings']")).isDisplayed();
    }

    @Test
    public void LongPressAndSwitchFromAppToWebBrowserTest() throws InterruptedException {
        driver.findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys("Kane Tran");
        driver.hideKeyboard();
        driver.findElement(By.id("com.androidsample.generalstore:id/radioMale")).click();
        Assert.assertEquals(driver.findElement(By.id("com.androidsample.generalstore:id/radioMale")).getAttribute("checked"), "true");
        driver.findElement(By.id("com.androidsample.generalstore:id/spinnerCountry")).click();
        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).setMaxSearchSwipes(50).scrollIntoView(text(\"Australia\"));"));
        driver.findElement(By.xpath("//android.widget.TextView[@resource-id='android:id/text1' and @text='Australia']")).click();
        driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();

        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).setMaxSearchSwipes(50).scrollIntoView(text(\"Jordan 6 Rings\"));"));
        driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.androidsample.generalstore:id/productName' and @text='Jordan 6 Rings']/following-sibling::android.widget.LinearLayout//android.widget.TextView[@text='ADD TO CART']")).click();

        driver.findElement(By.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();
        driver.findElement(By.xpath("//android.widget.TextView[@text='Jordan 6 Rings']")).isDisplayed();

        // Long Press Action
        WebElement termOfCondition = driver.findElement(By.id("com.androidsample.generalstore:id/termsButton"));
        ((JavascriptExecutor) driver).executeScript("mobile: longClickGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) termOfCondition).getId(),
                "duration", 1000
        ));

        driver.findElement(By.id("com.androidsample.generalstore:id/parentPanel")).isDisplayed();
        driver.findElement(By.id("android:id/button1")).click();
        driver.findElement(By.className("android.widget.CheckBox")).click();
        driver.findElement(By.id("com.androidsample.generalstore:id/btnProceed")).click();
        Thread.sleep(6000);

        Set<String> contextNames = driver.getContextHandles();
        for (String contextName : contextNames) {
            System.out.println(contextName);
        }
        driver.context("WEBVIEW_com.androidsample.generalstore");
        driver.findElement(By.name("q")).sendKeys("Kane vs UT");
        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
        driver.pressKey(new KeyEvent(AndroidKey.BACK));
        driver.context("NATIVE_APP");
    }

    @Test
    public void MobileBrowserTest() throws MalformedURLException, URISyntaxException {
        driver = LaunchBrowser();
        driver.get("https://google.com");
        driver.findElement(By.name("q")).sendKeys("Kane vs UT");
        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
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
