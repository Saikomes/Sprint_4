package praktikum;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.rules.ExternalResource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class DriverRule extends ExternalResource {
    private WebDriver driver;

    @Override
    protected void before() throws Throwable {
        initDriver();
    }

    @Override
    protected void after() {
        driver.quit();
    }

    public void initDriver() throws Exception {
        if("firefox".equalsIgnoreCase((System.getProperty("browser")))) {
            startUpFirefox();
        }
        else {
            startUpChrome();
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(EnvConfig.IMPLICIT_WAIT));
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void startUpChrome() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    public void startUpFirefox() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
    }
}
