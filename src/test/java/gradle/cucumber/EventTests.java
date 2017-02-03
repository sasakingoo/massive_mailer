package gradle.cucumber;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import gradle.cucumber.driver.WebDriverFactory;
import gradle.cucumber.driver.WebDriverWrapper;

public class EventTests {

    private WebDriverWrapper driver = WebDriverFactory.getDefaultDriver();
    private String BASE_URL = "http://localhost:8070/massive_mailer/add_event.jsp";
    private String EVENTLIST_BASE_URL = "http://localhost:8070/massive_mailer/eventlist.jsp";

    @Given("^I am on Add Event page$")
    public void visitAddEventPage() throws Throwable {
        driver.visit(BASE_URL);
    }

    @When("^Add an event \"([^\"]*)\"$")
    public void clickRegisterEvent(String eventTitle) throws Throwable {
        driver.text_field("evtTitle", eventTitle);
        driver.click_button("register_button");
        driver.expectAlert("Add event successfully");
    }

    @Then("^Event list page should contain \"([^\"]*)\"$")
    public void eventListPageShouldContain(String eventTitle) throws Throwable {
        driver.expectRedirect(EVENTLIST_BASE_URL);
        driver.pageShouldContain(eventTitle);
    }
}
