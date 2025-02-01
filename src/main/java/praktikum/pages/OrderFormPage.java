package praktikum.pages;
import org.openqa.selenium.WebDriver;

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
