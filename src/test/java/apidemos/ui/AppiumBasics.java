package apidemos.ui;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.DeviceRotation;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import ui.common.BaseTest;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class AppiumBasics extends BaseTest {
    private AndroidDriver driver;

    @BeforeClass
    public void beforeClass() {
        ConfigureAppium();
    }

    @BeforeMethod
    public void beforeTest() throws MalformedURLException, URISyntaxException {
        driver = LaunchApplication();
    }

    @Test
    public void AppiumTest() {
        driver.findElement(AppiumBy.accessibilityId("Preference")).click();
        driver.findElement(By.xpath("//android.widget.TextView[@content-desc='3. Preference dependencies']")).click();
        driver.findElement(By.id("android:id/checkbox")).click();
        driver.findElement(By.xpath("//android.widget.ListView[@resource-id='android:id/list']/android.widget.LinearLayout[2]/android.widget.RelativeLayout")).click();

        driver.findElement(By.id("android:id/alertTitle")).isDisplayed();
        driver.findElement(By.id("android:id/edit")).sendKeys("Kane Tran WiFi");
        driver.findElement(By.id("android:id/button1")).click();
    }

    @Test
    public void LongPressGestureTest() {
        driver.findElement(AppiumBy.accessibilityId("Views")).click();
        driver.findElement(AppiumBy.accessibilityId("Expandable Lists")).click();
        driver.findElement(AppiumBy.accessibilityId("1. Custom Adapter")).click();

        // https://github.com/appium/appium-uiautomator2-driver/blob/master/docs/android-mobile-gestures.md
        ((JavascriptExecutor) driver).executeScript("mobile: longClickGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) driver.findElement(By.xpath("//android.widget.TextView[@text='People Names']"))).getId(), "duration", 2000
        ));
        driver.findElement(By.xpath("//android.widget.TextView[@resource-id='android:id/title']")).isDisplayed();
        Assert.assertEquals(driver.findElement(By.xpath("//android.widget.TextView[@resource-id='android:id/title']")).getText(), "Sample menu");
    }

    @Test
    public void ScrollTest() {
        driver.findElement(AppiumBy.accessibilityId("Views")).click();
        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"WebView\"));"));
        driver.findElement(AppiumBy.accessibilityId("WebView")).click();
        driver.navigate().back();
        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Expandable Lists\"));"));
        driver.findElement(AppiumBy.accessibilityId("Expandable Lists")).click();
    }

    @Test
    public void SwipeTest() {
        driver.findElement(AppiumBy.accessibilityId("Views")).click();
        driver.findElement(AppiumBy.accessibilityId("Gallery")).click();
        driver.findElement(AppiumBy.accessibilityId("1. Photos")).click();

        WebElement firstImage = driver.findElement(By.xpath("//android.widget.ImageView[1]"));
        Assert.assertEquals(firstImage.getAttribute("focusable"), "true");
        ((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) firstImage).getId(),
                "direction", "left",
                "percent", 0.75,
                "speed", 2000
        ));
        Assert.assertEquals(firstImage.getAttribute("focusable"), "false");
    }

    @Test
    public void DragAndDropTest() {
        driver.findElement(AppiumBy.accessibilityId("Views")).click();
        driver.findElement(AppiumBy.accessibilityId("Drag and Drop")).click();

        WebElement firstCircle = driver.findElement(By.xpath("//android.view.View[@resource-id='io.appium.android.apis:id/drag_dot_1']"));
        WebElement droppedMessage = driver.findElement(By.xpath("//android.widget.TextView[@resource-id='io.appium.android.apis:id/drag_result_text']"));

        ((JavascriptExecutor) driver).executeScript("mobile: dragGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) firstCircle).getId(),
                "endX", 619,
                "endY", 560
        ));

        Assert.assertEquals(droppedMessage.getText(), "Dropped!");
    }

    @Test
    public void MiscellaneousAppiumActionTest() {
        // Landscape
        driver.rotate(new DeviceRotation(0, 0, 90));
        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Preference\"));"));
        driver.findElement(AppiumBy.accessibilityId("Preference")).click();
        driver.findElement(By.xpath("//android.widget.TextView[@content-desc='3. Preference dependencies']")).click();
        driver.findElement(By.id("android:id/checkbox")).click();
        driver.rotate(new DeviceRotation(90, 0, 0));

        driver.findElement(By.xpath("//android.widget.ListView[@resource-id='android:id/list']/android.widget.LinearLayout[2]/android.widget.RelativeLayout")).click();
        driver.findElement(By.id("android:id/alertTitle")).isDisplayed();

        // Clipboard
        driver.setClipboardText("Kane Tran WiFi");

        driver.findElement(By.id("android:id/edit")).sendKeys(driver.getClipboardText());
        driver.findElement(By.id("android:id/button1")).click();

        // Android Key
        driver.pressKey(new KeyEvent(AndroidKey.BACK));
        driver.pressKey(new KeyEvent(AndroidKey.HOME));

        // adb devices -> to check action emulator
        // adb shell dumpsys window | find "mCurrentFocus" -> mCurrentFocus=Window{e21eed2 u0 io.appium.android.apis/io.appium.android.apis.preference.PreferenceDependencies}
        String appPackage = "io.appium.android.apis";
        String appActivity = "io.appium.android.apis.preference.PreferenceDependencies";
        ((JavascriptExecutor) driver).executeScript("mobile: startActivity", ImmutableMap.of(
                "intent", appPackage + "/" + appActivity
        ));
        driver.findElement(By.id("android:id/checkbox")).click();
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
