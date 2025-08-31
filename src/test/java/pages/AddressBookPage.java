package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class AddressBookPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By addressBookLink = By.linkText("Address Book");
    private By addNewButton = By.xpath("//a[text()='New Address']");
    private By deleteButtons = By.xpath("//a[contains(@data-original-title, 'Delete')]");
    private By successAlert = By.cssSelector(".alert-success");
    private By firstNameInput = By.id("input-firstname");
    private By lastNameInput = By.id("input-lastname");
    private By address1Input = By.id("input-address-1");
    private By cityInput = By.id("input-city");
    private By postcodeInput = By.id("input-postcode");
    private By countrySelect = By.id("input-country");
    private By zoneSelect = By.id("input-zone");
    private By continueButton = By.xpath("//input[@value='Continue']");
    private By errorMessages = By.cssSelector(".text-danger");

    public AddressBookPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void goToAddressBook() {
        wait.until(ExpectedConditions.elementToBeClickable(addressBookLink)).click();
    }

    public void clickAddNew() {
        wait.until(ExpectedConditions.elementToBeClickable(addNewButton)).click();
    }

    public void addNewAddress(String firstName, String lastName, String address1, String city, String postcode, String country, String zone) {
        clickAddNew();
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput)).sendKeys(firstName);
        driver.findElement(lastNameInput).sendKeys(lastName);
        driver.findElement(address1Input).sendKeys(address1);
        driver.findElement(cityInput).sendKeys(city);
        driver.findElement(postcodeInput).sendKeys(postcode);
        selectCountry(country);
        selectZone(zone);
        driver.findElement(continueButton).click();
    }

    public void selectCountry(String country) {
        Select select = new Select(driver.findElement(countrySelect));
        select.selectByVisibleText(country);
    }

    public void selectZone(String zone) {
        Select select = new Select(driver.findElement(zoneSelect));
        select.selectByVisibleText(zone);
    }

    public void deleteFirstAddress() {
        List<WebElement> deletes = driver.findElements(deleteButtons);
        if (!deletes.isEmpty()) {
            deletes.get(0).click();
        }
    }

    public String getSuccessMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successAlert)).getText();
    }

    public boolean hasAddresses() {
        return !driver.findElements(deleteButtons).isEmpty();
    }

    public boolean isErrorMessagePresent() {
        return !driver.findElements(errorMessages).isEmpty();
    }
}
