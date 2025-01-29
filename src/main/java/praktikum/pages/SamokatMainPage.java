package praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import praktikum.EnvConfig;

import java.time.Duration;

import static org.junit.Assert.assertEquals;

public class SamokatMainPage {

    private final WebDriver driver;

    public QuestionPage questionPage;

    //Кнопка "Заказать" в хедере
    private final By orderButtonHeader = By.xpath(".//div[@class='Header_Nav__AGCXC']/button[text()='Заказать']");

    //Кнопка "Заказать" на основном контенте страницы
    private final By orderButtonMain = By.xpath(".//div[@class='Home_FinishButton__1_cWm']/button[text()='Заказать']");
    //Кнопка согласия с Cookie
    private final By acceptCookieButton = By.id("rcc-confirm-button");

    public SamokatMainPage(WebDriver driver){
        this.driver = driver;
        questionPage = new QuestionPage(driver);
    }

    public void acceptCookie() {
        driver.findElement(acceptCookieButton).click();
    }

    public void openMainPage() {
        driver.get(EnvConfig.BASE_URL);
    }

    public void clickOrderButton(boolean headerButton) {
        WebElement orderButton;
        if(headerButton) {
            orderButton = driver.findElement(orderButtonHeader);
        }
        else {
            orderButton = driver.findElement(orderButtonMain);
        }
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", orderButton);
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT)).
                until(ExpectedConditions.elementToBeClickable(
                        orderButton));
        orderButton.click();
    }
}
