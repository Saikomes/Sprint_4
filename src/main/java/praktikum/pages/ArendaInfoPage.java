package praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import praktikum.EnvConfig;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class ArendaInfoPage {
    private WebDriver driver;

    public By getPackageDateInput() {
        return packageDateInput;
    }

    private final By packageDateInput = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    private final By duratonDropdownRoot = By.xpath(".//div[@class='Dropdown-placeholder']/ancestor::div[@class='Dropdown-root']");
    private final By durationOptionsMenu = By.className("Dropdown-menu");
    // Локатор для календаря
    private final By calendarLocator = By.cssSelector(".react-datepicker");
    private final By blackColorCheckbox = By.id("black");
    private final By greyColorCheckbox = By.id("grey");
    private final By commentInput = By.xpath(".//input[@placeholder='Комментарий для курьера']");
    private final By orderButton = By.xpath(".//div[@class='Order_Buttons__1xGrp']/button[text()='Заказать']");
    private By durationOptionByText(String duration) {
        return By.xpath
                (String.format
                        (".//div[@class = 'Dropdown-option' and text() = '%s']",
                                duration));
    }

    public ArendaInfoPage(WebDriver driver) {
        this.driver = driver;
    }

    public void selectDeliveryDay(String dateString) {

        driver.findElement(packageDateInput).click();
        // Ожидание появления календаря
        new WebDriverWait(driver,  Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT)).
                until(ExpectedConditions.visibilityOfElementLocated(calendarLocator));
        // Парсинг даты
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.parse(dateString, formatter);
        int day = date.getDayOfMonth();

        // Формирование локатора для дня
        String dayLocator = String.format(".react-datepicker__day--%03d" +
                ":not(.react-datepicker__day--outside-month)", day);

        // Поиск и клик по дню
        WebElement dayElement = driver.findElement(By.cssSelector(dayLocator));
        dayElement.click();
    }

    public void chooseDuration(String duration) {
        driver.findElement(duratonDropdownRoot).click();
        // Ожидание появления меню
        new WebDriverWait(driver,  Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT)).
                until(ExpectedConditions.visibilityOfElementLocated(durationOptionsMenu));
        // Поиск нужного срока аренды
        WebElement durationElement = driver.findElement(durationOptionByText(duration));

        // Прокрутка до нужного элемента
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", durationElement);

        // Клик по элементу
        durationElement.click();
    }

    public void chooseColorVariants(String[] selectedOptions) {
        if(Arrays.asList(selectedOptions).contains(EnvConfig.BLACK_PEARL)) {
            driver.findElement(blackColorCheckbox).click();
        }
        if(Arrays.asList(selectedOptions).contains(EnvConfig.GREY_MELANCHOLY)) {
            driver.findElement(greyColorCheckbox).click();
        }

    }

    public void fillCommentSection(String comment) {
        driver.findElement(commentInput).sendKeys(comment);
    }

    public void clickOrderButton() {
        driver.findElement(orderButton).click();
    }

    public void fillOrderFormAndSubmit(String deliveryDate, String[] colors) {
        selectDeliveryDay(deliveryDate);
        chooseDuration(EnvConfig.ORDER_DURATION);
        chooseColorVariants(colors);
        fillCommentSection(EnvConfig.ORDER_COMMENT);
        clickOrderButton();
    }

}
