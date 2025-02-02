package praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import praktikum.EnvConfig;
import praktikum.helpers.Date;

import java.time.Duration;

public class ArendaInfoPage {
    private final WebDriver driver;

    public By getPackageDateInput() {

        return packageDateInput;
    }

    public ArendaInfoPage(WebDriver driver) {
        this.driver = driver;
    }

    // Поле ввода даты доставки
    private final By packageDateInput = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    // Область вызова контекстного меню продолжительности
    private final By durationDropdownRoot = By.xpath(".//div[@class='Dropdown-placeholder']" +
            "/ancestor::div[@class='Dropdown-root']");
    // Выпадающее меню с опциями длительности аренды
    private final By durationOptionsMenu = By.className("Dropdown-menu");
    // Локатор для календаря
    private final By calendarLocator = By.cssSelector(".react-datepicker");
    // Чекбокс с цветом "Черная жемчужина"
    private final By blackColorCheckbox = By.id("black");
    // Чекбокс с цветом "Серая безысходность"
    private final By greyColorCheckbox = By.id("grey");
    // Поле ввода комментария к заказу
    private final By commentInput = By.xpath(".//input[@placeholder='Комментарий для курьера']");
    // Кнопка заказа
    private final By orderButton = By.xpath(".//div[@class='Order_Buttons__1xGrp']" +
            "/button[text()='Заказать']");

    // Локатор опции длительности заказа по тексту
    private By durationOptionByText(String duration) {
        return By.xpath
                (String.format
                        (".//div[@class = 'Dropdown-option' and text() = '%s']",
                                duration));
    }

    public void selectDeliveryDay(String dateString) {
        // Заполняем поле ввода даты доставки
        driver.findElement(packageDateInput).sendKeys(dateString);
        // Вызываем календарь
        driver.findElement(packageDateInput).click();
        // Ожидание появления календаря
        new WebDriverWait(driver,  Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT)).
                until(ExpectedConditions.visibilityOfElementLocated(calendarLocator));
        // Извлекаем день из переданной даты
        int day = Date.parseStringToDate("dd.MM.yyyy", dateString).getDayOfMonth();

        // Формирование локатора для дня
        String dayLocator = String.format(".react-datepicker__day--%03d" +
                ":not(.react-datepicker__day--outside-month)", day);

        // Поиск и клик по дню
        WebElement dayElement = driver.findElement(By.cssSelector(dayLocator));
        dayElement.click();
    }

    public void chooseDuration(String duration) {
        driver.findElement(durationDropdownRoot).click();
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

    public void chooseColorVariants (String[] selectedOptions) throws Exception {
        for (String option : selectedOptions) {
            switch (option) {
                case EnvConfig.BLACK_PEARL:
                    driver.findElement(blackColorCheckbox).click();
                    break;
                case EnvConfig.GREY_MELANCHOLY:
                    driver.findElement(greyColorCheckbox).click();
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported color option: " + option);
            }
        }

    }

    public void fillCommentSection(String comment) {
        driver.findElement(commentInput).sendKeys(comment);
    }

    public void clickOrderButton() {
        driver.findElement(orderButton).click();
    }

    public void fillOrderFormAndSubmit(String deliveryDate, String[] colors) throws Exception {
        selectDeliveryDay(deliveryDate);
        chooseDuration(EnvConfig.ORDER_DURATION);
        chooseColorVariants(colors);
        fillCommentSection(EnvConfig.ORDER_COMMENT);
        clickOrderButton();
    }

}
