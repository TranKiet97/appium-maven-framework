package ui.common;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

public class BaseTest {
    private AppiumDriverLocalService service;
    private AndroidDriver driver;
    public void ConfigureAppium() {
        // Start Appium Server
        AppiumServiceBuilder builder = new AppiumServiceBuilder()
                .usingDriverExecutable(new File("C:\\Program Files\\nodejs\\node.exe"))
                .withAppiumJS(new File("C:\\Users\\ASUS\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
                .withIPAddress("127.0.0.1")
                .usingPort(4723);
        builder.withTimeout(Duration.ofSeconds(60));
        service = AppiumDriverLocalService.buildService(builder);
        service.start();
    }

    public AndroidDriver LaunchApplication() throws URISyntaxException, MalformedURLException {
        // AndroidDriver, IOSDriver
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("KaneEmulatorPhone");
        options.setApp("D:\\Workspace\\Appium Maven Framework\\appium-maven-framework\\src\\test\\resources\\ApiDemos-debug.apk");
        driver = new AndroidDriver(new URI("http://127.0.0.1:4723/").toURL(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return driver;
    }

    public void TearDown() {
        service.stop();
    }
}
