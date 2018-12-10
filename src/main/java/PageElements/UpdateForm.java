package PageElements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UpdateForm {
    WebDriver driver;
    By name = By.id("users-name");
    By username = By.id("users-username");
    By email = By.id("users-email");
    By address = By.id("users-address");
    By phone = By.id("users-phone");
    By saveButton = By.xpath("//button[@type='submit']");

    public UpdateForm (WebDriver driver) {
       this.driver=driver;
    }

    public void setName (String name ) {
        driver.findElement(this.name).clear();
        driver.findElement(this.name).sendKeys(name);
    }

    public void setUsername (String username) {
        driver.findElement(this.username).clear();
        driver.findElement(this.username).sendKeys(username);
    }

    public void setEmail (String email) {
        driver.findElement(this.email).clear();
        driver.findElement(this.email).sendKeys(email);
    }

    public void setAddress (String address) {
        driver.findElement(this.address).clear();
        driver.findElement(this.address).sendKeys(address);
    }

    public void setPhone (String phone) {
        driver.findElement(this.phone).clear();
        driver.findElement(this.phone).sendKeys(phone);
    }

    public void saveButtonClick () {
        driver.findElement(saveButton).click();
    }
}
