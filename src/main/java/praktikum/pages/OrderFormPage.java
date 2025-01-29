package praktikum.pages;

import org.checkerframework.checker.units.qual.A;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertEquals;

public class OrderFormPage {

    public ClientInfoPage clientInfoPage;
    public ArendaInfoPage arendaInfoPage;
    public ConfirmDialogPage confirmDialogPage;
    public OrderFormPage(WebDriver driver){
        clientInfoPage = new ClientInfoPage(driver);
        arendaInfoPage = new ArendaInfoPage(driver);
        confirmDialogPage = new ConfirmDialogPage(driver);
    }
}
