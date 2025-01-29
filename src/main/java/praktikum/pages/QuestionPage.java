package praktikum.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import praktikum.EnvConfig;

import java.time.Duration;

public class QuestionPage {

    private final WebDriver driver;// Кнопка разворачивания опции выпадающего меню
    By questionsOptionButton = By.xpath(".//div[contains(@class, 'accordion__button')]");
    By answerPanelText = By.xpath(".//div[contains(@class, 'accordion__panel')]//p");

    public QuestionPage(WebDriver driver) {

        this.driver = driver;
    }

    // Опция выпадающего меню по ее тексту
    By questionsOptionByText(String optionText) {
        return By.xpath
                (String.format
                        (".//div[contains(@class, 'accordion__button') and text()='%s']" +
                                        "/ancestor::div[contains(@class, 'accordion__item')]",
                                optionText));
    }

    // Функция разворачивания опции выпадающего меню
    public void expandQuestionsDropDownOption(String optionText) {
        WebElement questionOption = driver.findElement(questionsOptionByText(optionText));
        WebElement button = questionOption.findElement(questionsOptionButton);
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT)).until(ExpectedConditions.elementToBeClickable(button));
        button.click();
    }// Проверка что опция выпадающего меню вопросов содержит корректный ответ при раскрытии

    public void assertQuestionOptionHasCorrectAnswer(String optionText, String expectedAnswer) {
        expandQuestionsDropDownOption(optionText);
        WebElement questionOption = driver.findElement(questionsOptionByText(optionText));
        WebElement answerPanel = questionOption.findElement(answerPanelText);
        // Ожидаем, пока текст ответа станет видимым
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT))
                .until(ExpectedConditions.not(ExpectedConditions.attributeContains(answerPanel, "style", "display: none")));

        // Прокручиваем страницу до текста ответа
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", answerPanel);
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT)).until(ExpectedConditions.visibilityOf(answerPanel));
        Assert.assertEquals("При раскрытии опции меню вопросов пользователь должен видеть ожидаемый ответ",
                expectedAnswer, answerPanel.getText());
    }


}