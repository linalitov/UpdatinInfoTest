package Steps;

import PageElements.UpdateForm;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;


public class MyStepdefs {
    WebDriver driver;
    UpdateForm updateForm;
    User myUser;
    Gson gson = new Gson();;
    GsonBuilder builder = new GsonBuilder();

    @Before ()
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        updateForm = new UpdateForm(driver);
    }

    @Given("^User is created$")
    public void userIsCreated() throws Throwable {
        long currentTimestamp = System.currentTimeMillis();
        User user1 = new User(
                "AUT_name_" + currentTimestamp,
                "AUT_username_" + currentTimestamp,
                "AUT_email_" + currentTimestamp,
                "AUT_address_" + currentTimestamp,
                ""+currentTimestamp
        );

        gson = builder.create();
        String jsonInString = gson.toJson(user1);

        HttpResponse<JsonNode> response
                = Unirest.post("https://seleniumclass.000webhostapp.com/api/v1/users")
                .header("Content-Type", "application/json")
                .body(jsonInString)
                .asJson();

        myUser = gson.fromJson(response.getBody().toString(), User.class);
    }

    @Given("^User navigates to Update info page$")
    public void userNavigatesToUpdateInfoPage() throws Throwable {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://seleniumclass.000webhostapp.com/web/index.php?r=users%2Fupdate&id=" + myUser.id);
    }

    @When("^User updates name with the value \"([^\"]*)\"$")
    public void userUpdatesNameWithTheValue(String name) throws Throwable {
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        updateForm.setName(name);
    }

    @When("^User updates username with the value \"([^\"]*)\"$")
    public void userUpdatesUsernameWithTheValue(String username) throws Throwable {
       updateForm.setUsername(username);
    }

    @When("^User updates email with the value \"([^\"]*)\"$")
    public void userUpdatesEmailWithTheValue(String email) throws Throwable {
      updateForm.setEmail(email);
    }

    @When("^User updates address with the value \"([^\"]*)\"$")
    public void userUpdatesAddressWithTheValue(String address) throws Throwable {
       updateForm.setAddress(address);
    }

    @When("^User updates phone with the value \"([^\"]*)\"$")
    public void userUpdatesPhoneWithTheValue(String phone) throws Throwable {
        updateForm.setPhone(phone);
    }

    @When("^User clicks Save$")
    public void userClicksSave() throws Throwable {
       updateForm.saveButtonClick();
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.urlToBe("https://seleniumclass.000webhostapp.com/web/index.php?r=users%2Fview&id=" + myUser.id));
    }

    @Then("^Data changed$")
    public void dataChanged() throws Throwable {
    driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        HttpResponse<JsonNode> response
                = Unirest.get("https://seleniumclass.000webhostapp.com/api/v1/users/" + myUser.id).asJson();

        User myUser1 = gson.fromJson(response.getBody().toString(), User.class);
        String myUserString = myUser.name+myUser.username+myUser.address+myUser.email+myUser.phone;
        String myUser1String = myUser1.name+myUser1.username+myUser1.address+myUser1.email+myUser1.phone;
        Assert.assertNotEquals(myUserString, myUser1String);
    }

    @After()
    public void closing () {
        driver.close();
        driver.quit();
    }
}
