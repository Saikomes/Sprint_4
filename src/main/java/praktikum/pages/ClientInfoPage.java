package praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import praktikum.EnvConfig;

import java.time.Duration;

public class ClientInfoPage {
    private final WebDriver driver;

    public By getNameInput() {
        return nameInput;
    }

    //Кнопка "Заказать"
    private final By nameInput = By.xpath(".//input[@placeholder='* Имя']");
    private final By surnameInput = By.xpath(".//input[@placeholder='* Фамилия']");
    private final By addressInput = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By metroInput = By.xpath(".//input[@placeholder='* Станция метро']");
    private final By telephoneInput = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By metroOptions = By.xpath(".//ul[@class='select-search__options']");
    private final By forwardButton = By.xpath(".//button[text()='Далее']");
    public ClientInfoPage(WebDriver driver) {
        this.driver = driver;
    }

    private By metroOptionByText(String metroName) {
        return By.xpath
                (String.format
                        (".//div[text() = '%s']/parent::button",
                                metroName));
    }

    public void setUsername(String username) {
        driver.findElement(nameInput).sendKeys(username);
    }

    public void setSubname(String subname) {
        driver.findElement(surnameInput).sendKeys(subname);
    }

    public void setAddress(String address) {
        driver.findElement(addressInput).sendKeys(address);
    }

    public void setMetro(String metroName) {
        driver.findElement(metroInput).click();

        // Ожидание появления списка станций метро
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT)).
                until(ExpectedConditions.visibilityOfElementLocated(metroOptions));

        // Поиск нужной станции метро
        WebElement metroElement = driver.findElement(metroOptionByText(metroName));

        // Прокрутка до нужного элемента
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", metroElement);

        // Клик по элементу
        metroElement.click();
    }

    public void setTelephone(String telephone) {

        driver.findElement(telephoneInput).sendKeys(telephone);
    }

    public void clickForwardButton() {

        driver.findElement(forwardButton).click();
    }

    public void submitUserInfo(String metroStation) {
        setUsername(EnvConfig.USERNAME);
        setSubname(EnvConfig.SUBNAME);
        setAddress(EnvConfig.ADDRESS);
        setMetro(metroStation);
        setTelephone(EnvConfig.TELEPHONE);
        clickForwardButton();
    }
}