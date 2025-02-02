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
                { "Хочу сразу несколько самокатов! Так можно?",
                        "Пока что у нас так: один заказ — один самокат. " +
                                "Если хотите покататься с друзьями, " +
                                "можете просто сделать несколько заказов — один за другим."},
                { "Как рассчитывается время аренды?",
                        "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. " +
                                "Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. " +
                                "Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                { "Можно ли заказать самокат прямо на сегодня?",
                        "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                { "Можно ли продлить заказ или вернуть самокат раньше?",
                        "Пока что нет! Но если что-то срочное — " +
                                "всегда можно позвонить в поддержку по красивому номеру 1010."},
                { "Вы привозите зарядку вместе с самокатом?",
                        "Самокат приезжает к вам с полной зарядкой. " +
                                "Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. " +
                                "Зарядка не понадобится."},
                { "Можно ли отменить заказ?",
                        "Да, пока самокат не привезли. Штрафа не будет, " +
                                "объяснительной записки тоже не попросим. Все же свои."},
                { "Можно ли отменить заказ?",
                        "Да, пока самокат не привезли. Штрафа не будет, " +
                                "объяснительной записки тоже не попросим. Все же свои."},
                { "Я жизу за МКАДом, привезёте?",
                        "Да, обязательно. Всем самокатов! И Москве, и Московской области."},
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