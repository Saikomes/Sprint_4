package praktikum;

import org.junit.Rule;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.junit.Test;
import praktikum.pages.SamokatMainPage;

// Класс с автотестом
@RunWith(Parameterized.class)
public class QuestionMenuTest {
    @Rule
    public DriverRule factory = new DriverRule();

    private final String questionMenuOptionText;

    private final String responseText;

    public QuestionMenuTest(String questionMenuOptionText, String responseText) {
        this.questionMenuOptionText = questionMenuOptionText;
        this.responseText = responseText;

    }

    @Parameterized.Parameters
    public static Object[][] getQuestionsAndAnswers() {
        return new Object[][] {
                { "Сколько это стоит? И как оплатить?",
                        "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                { "Можно ли отменить заказ?",
                        "Да, пока самокат не привезли. Штрафа не будет, " +
                                "объяснительной записки тоже не попросим. Все же свои."},
        };
    }

    @Test
    public void checkQuestionsHaveCorrectAnswers() throws Exception {
        WebDriver driver = factory.getDriver();

        // создай объект класса главной страницы
        SamokatMainPage objSamokatMainPage = new SamokatMainPage(driver);
        // переход на страницу тестового приложения
        objSamokatMainPage.openMainPage();
        // соглашаемся принять Cookie
        objSamokatMainPage.acceptCookie();
        // Раскрываем вопрос и проверяем на соответствие ожидаемому ответу
        objSamokatMainPage.questionPage.assertQuestionOptionHasCorrectAnswer(questionMenuOptionText, responseText);

    }

}