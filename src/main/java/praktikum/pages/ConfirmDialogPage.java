package praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConfirmDialogPage {
    private final WebDriver driver;

    public ConfirmDialogPage(WebDriver driver) {
        this.driver = driver;
    }

    public By getDialogWindow() {
        return dialogWindow;
    }

    public By getDialogHeaderText() {
        return dialogHeaderText;
    }

    private final By dialogWindow = By.className("Order_Modal__YZ-d3");

    //кнопка диалога по тексту
    private By dialogOptionButtonByText(String text) {
        return By.xpath
                (String.format
                        (".//div[@class='Order_Modal__YZ-d3']/div/button[text()='%s']",
                                text));
    }

    private final By dialogHeaderText = By.className("Order_ModalHeader__3FDaJ");

    public void clickDialogButton(String buttonText) {
        driver.findElement(dialogOptionButtonByText(buttonText)).click();
    }

    public void checkHeaderContainsTextExpected(String expectedText) {
        String headerText = driver.findElement(dialogHeaderText).getText().trim();
        assertTrue("Заголовок диалога содержит ожидаемый текст", headerText.contains(expectedText));
    }
}
