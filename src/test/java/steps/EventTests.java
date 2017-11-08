package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import steps.driver.WebDriverFactory;
import steps.driver.WebDriverWrapper;

public class EventTests {

    private WebDriverWrapper driver = WebDriverFactory.getDefaultDriver();
    private String BASE_URL = "http://localhost:8070/massive_mailer/add_course.jsp";
    private String EVENTLIST_BASE_URL = "http://localhost:8070/massive_mailer/courselist.jsp";

    @Given("^I am on Add Event page$")
    public void visitAddEventPage() throws Throwable {
        driver.visit(BASE_URL);
    }

    @When("^Create course \"([^\"]*)\" in \"([^\"]*)\"$")
    public void addEventAndSelectLocationFromDropdown(String courseName, String location, String date) throws Throwable {
        driver.setTextField("coursename", courseName);
        driver.setTextField("coursedetails", "nothing important");
        driver.setTextField("startdate", date);
        driver.setDropdownValue("location", location);
    }

    public void addEventAndSelectLocationFromDropdownAndTextBox(String courseName, String country, String city, String date) throws Throwable {
        driver.setTextField("coursename", courseName);
        driver.setTextField("coursedetails", "nothing important");
        driver.setTextField("startdate", date);
        driver.setDropdownValue("country", country);
        driver.setTextField("city", city);
    }

    @When("^I click the save button$")
    public void clickRegisterEvent() throws Throwable {
        driver.clickButton("save_button");
    }

    @Then("^Course save page should display \"([^\"]*)\"$")
    public void eventListPageShouldContain(String message) throws Throwable {
        driver.pageShouldContain(message);
    }

    void addCourse(String courseName, String location, String date) throws Throwable {
        visitAddEventPage();
        addEventAndSelectLocationFromDropdown(courseName, location, date);
        clickRegisterEvent();
    }

    void addCourseWithCountryAndCity(String courseName, String country, String city, String date) throws Throwable {
        visitAddEventPage();
        addEventAndSelectLocationFromDropdownAndTextBox(courseName, country, city, date);
        clickRegisterEvent();
    }
}
