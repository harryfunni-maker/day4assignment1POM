package tests;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.AddressBookPage;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AddressBookTests {
    private WebDriver driver;
    private AddressBookPage addressBookPage;

    @BeforeAll
    public void setUpClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://ecommerce-playground.lambdatest.io/index.php?route=account/login");
        login();
        addressBookPage = new AddressBookPage(driver);
        driver.get("https://ecommerce-playground.lambdatest.io/index.php?route=account/address");
        // Debug: check login and address book page
        boolean loggedIn = driver.findElements(By.linkText("My Account")).size() > 0;
        Assertions.assertTrue(loggedIn, "Login failed: 'My Account' link not found. Check credentials.");
        boolean onAddressBook = driver.findElements(By.xpath("//a[text()='New Address']")).size() > 0;
        if (!onAddressBook) {
            System.out.println("DEBUG: 'New Address' button not found. Saving page source to debug_addressbook.html");
            try {
                java.nio.file.Files.write(java.nio.file.Paths.get("debug_addressbook.html"), driver.getPageSource().getBytes());
            } catch (Exception e) {
                System.out.println("DEBUG: Failed to save page source: " + e.getMessage());
            }
        }
        Assertions.assertTrue(onAddressBook, "Address Book page did not load or 'New Address' button not found. See debug_addressbook.html for details.");
    }

    private void login() {
    driver.findElement(By.id("input-email")).sendKeys("harreyelumarthi@gmail.com"); // Change to valid user
    driver.findElement(By.id("input-password")).sendKeys("admin123"); // Change to valid password
    driver.findElement(By.xpath("//input[@value='Login']")).click();
    // Assert login success by checking for My Account link
    boolean loggedIn = driver.findElements(By.linkText("My Account")).size() > 0;
    Assertions.assertTrue(loggedIn, "Login failed: 'My Account' link not found. Check credentials.");
    }

    @Test
    public void testAddNewAddressPositive() {
        addressBookPage.addNewAddress(
                "John", "Doe", "123 Main St", "New York", "10001", "United States", "New York"
        );
        Assertions.assertTrue(addressBookPage.getSuccessMessage().contains("Your address has been successfully added"));
    }

    @Test
    public void testAddNewAddressNegative() {
        addressBookPage.clickAddNew();
        driver.findElement(By.id("input-firstname")).clear(); // leave required fields blank
        driver.findElement(By.xpath("//input[@value='Continue']")).click();
        Assertions.assertTrue(addressBookPage.isErrorMessagePresent());
    }

    @Test
    public void testDeleteAddress() {
        if (addressBookPage.hasAddresses()) {
            addressBookPage.deleteFirstAddress();
            Assertions.assertTrue(addressBookPage.getSuccessMessage().contains("Your address has been successfully deleted"));
        } else {
            Assumptions.assumeTrue(false, "No address to delete.");
        }
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
