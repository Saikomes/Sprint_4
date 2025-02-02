package praktikum;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import praktikum.pages.OrderFormPage;
import praktikum.pages.SamokatMainPage;

import java.time.Duration;

@RunWith(Parameterized.class)
public class MakeOrderTest {
    @Rule
    public DriverRule factory = new DriverRule();

    private WebDriver driver;

    private final boolean isHeaderOrderButton;

    private final String metroStation;

    private final String orderDate;

    private final String[] samokatColors;

    public MakeOrderTest(boolean isHeaderOrderButton, String metroStation, String orderDate, String[] samokatColors) {
        this.isHeaderOrderButton = isHeaderOrderButton;
        this.metroStation = metroStation;
        this.orderDate = orderDate;
        this.samokatColors = samokatColors;

    }

    @Parameterized.Parameters
    public static Object[][] getQuestionsAndAnswers() {
        return new Object[][] {
                { true, "Митино", "24.08.2012", new String[]{EnvConfig.BLACK_PEARL}},
                { false, "Сокольники", "24.08.2025", new String[]{EnvConfig.BLACK_PEARL, EnvConfig.GREY_MELANCHOLY}},
        };
    }

    @Before
    public void setUp() {
        driver = factory.getDriver();

    }
    @Test
    public void makeOrder() throws Exception {
        // создаем объект класса главной страницы
        SamokatMainPage objSamokatMainPage = new SamokatMainPage(driver);
        // создаем объект класса страницы заказа
        OrderFormPage orderFormPage = new OrderFormPage(driver);

        // переход на страницу тестового приложения
        objSamokatMainPage.openMainPage();
        // соглашаемся принять Cookie
        objSamokatMainPage.acceptCookie();

        //кликаем на кнопку заказа
        objSamokatMainPage.clickOrderButton(isHeaderOrderButton);

        //ожидаем пока не увидим элементы страницы заказа
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT)).
                until(ExpectedConditions.visibilityOfElementLocated(
                        orderFormPage.clientInfoPage.getNameInput()));

        //заполняем данные пользователя и нажимаем кнопку
        orderFormPage.clientInfoPage.submitUserInfo(metroStation);

        //ждем пока не появятся элементы данных об аренде
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT)).
                until(ExpectedConditions.visibilityOfElementLocated(
                        orderFormPage.arendaInfoPage.getPackageDateInput()));

        //заполняем данные об аренде и нажимаем кнопку
        orderFormPage.arendaInfoPage.fillOrderFormAndSubmit(orderDate,
                samokatColors);

        //ждем диалога подтверждения
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT)).
                until(ExpectedConditions.visibilityOfElementLocated(
                        orderFormPage.confirmDialogPage.getDialogWindow()));

        //Выбираем вариант Да в диалоге подтверждения
        orderFormPage.confirmDialogPage.clickDialogButton(EnvConfig.YES_DIALOG_OPTION);

        //Ждем пока не сменится текст в диалоге
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT)).until(ExpectedConditions.not(ExpectedConditions.
                textToBePresentInElementLocated(orderFormPage.confirmDialogPage.getDialogHeaderText(), EnvConfig.ORDER_CONFIRM_QUESTION)));

        //Проверяем что содержимое диалога ожидаемо
        orderFormPage.confirmDialogPage.checkHeaderContainsTextExpected(EnvConfig.ORDER_ACCEPTED);

    }

}
