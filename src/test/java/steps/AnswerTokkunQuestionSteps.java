package steps;

import com.odde.massivemailer.factory.QuestionBuilder;
import com.odde.massivemailer.model.onlinetest.Category;
import com.odde.massivemailer.model.onlinetest.Question;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import static org.junit.Assert.assertEquals;

public class AnswerTokkunQuestionSteps {
    private final MassiveMailerSite site = new MassiveMailerSite();
    private final WebDriverWrapper driver = site.getDriver();
    private Question questionA;

    @Given("^問題 \"([^\"]*)\" が追加されている$")
    public void 問題_が追加されている(String arg1) throws Throwable {
        questionA = new QuestionBuilder()
//                .aQuestion(arg1, "advice", String.valueOf(Category.SCRUM.getId()))
                .aQuestion(1)
                .withCorrectOption("visible")
                .withCorrectOption("valuable")
                .withCorrectOption("vertical")
                .withWrongOption("virtual")
                .please();

    }

    @When("^特訓回答ページに遷移する$")
    public void 特訓回答ページに遷移する() throws Throwable {
        site.visit("tokkun/question");
    }

    @When("^ユーザが \"([^\"]*)\" と \"([^\"]*)\" と \"([^\"]*)\" と回答する$")
    public void ユーザが_と_と_と回答する(String arg1, String arg2, String arg3) throws Throwable {
        driver.clickCheckBox(arg1);
        driver.clickCheckBox(arg2);
        driver.clickCheckBox(arg3);
    }

    @When("^ユーザーがanswerボタンを押す$")
    public void ユーザーがanswerボタンを押す() throws Throwable {
        driver.clickButton("answer");
    }

    @Then("^特訓のEndOfTheTestが表示される$")
    public void 特訓のendofthetestが表示される() throws Throwable {
        assertEquals(driver.getCurrentTitle(), "End Of Test");
    }

}