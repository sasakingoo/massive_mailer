package steps;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

public class GDPRSteps {
    private MassiveMailerSite site = new MassiveMailerSite();
    private WebDriverWrapper driver = site.getDriver();
    @When("^I open the main page$")
    public void iOpenTheMainPage() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
       driver.visit(site.baseUrl());
    }

    @Then("^System shows GDPR menu on left panel$")
    public void systemShowsGDPRMenuOnLeftPanel() throws Throwable {
        driver.pageShouldContain("GDPR");
    }
}
