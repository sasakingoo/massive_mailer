package steps;

import com.odde.massivemailer.model.OnlineTest;
import com.odde.massivemailer.model.Question;
import com.odde.massivemailer.service.OnlineTestService;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.ArrayList;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class OnlineTestCreationSteps {
    private OnlineTestService onlineTestService;

    @Given("^There are \"([^\"]*)\" questions in the system$")
    public void there_are_questions_in_the_system(int n) throws Throwable {
        Question.deleteAll();
        IntStream.rangeClosed(1, n)
                .forEach(i -> new Question(String.valueOf(i), new ArrayList<>(), "advice").save());
    }

    @When("^I start an online test$")
    public void i_start_an_online_test() throws Throwable {
        onlineTestService = new OnlineTestService();
    }

    @Then("^An online test with \"([^\"]*)\" questions is generated$")
    public void an_online_test_with_questions_is_generated(int m) throws Throwable {
        OnlineTest onlineTest = onlineTestService.generate();
        System.out.println(onlineTest.getQuestions());
        assertEquals(m, onlineTest.getQuestions().size());
    }

}