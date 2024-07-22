package SeleniumPackage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class LoginTest {

    public String baseUrl = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
    public WebDriver driver;

    @BeforeTest
    public void setup() {
        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Amarachi\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        System.out.println("Before Test Executed");
        driver = new ChromeDriver();

        // Maximize window
        driver.manage().window().maximize();
        driver.get(baseUrl);
    }

    @Test
    public void successfulLoginTest() {
        WebDriverWait wait = new WebDriverWait(driver, 15); // 15 seconds wait

        // Wait for the userName input to be present
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
        // Find userName and enter userName "Admin"
        driver.findElement(By.name("username")).sendKeys("Admin");

        // Wait for the password input to be present
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("password")));
        // Find Password and enter password "admin123"
        driver.findElement(By.name("password")).sendKeys("admin123");

        // Wait for the login button to be clickable
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
        // Find Login Button and click
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        // Wait for the page title to change and verify login
        wait.until(ExpectedConditions.titleContains("OrangeHRM"));
        String pageTitle = driver.getTitle();
        if (pageTitle.equals("OrangeHRM")) {
            System.out.println("Login successful");
            
         // Adding delay after successful login
            try {
                Thread.sleep(10000); // Wait for 10 seconds after successful login
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Now perform logout
            logout();
        } else {
            System.out.println("Login failed");
        }
    }

    @Test
    public void invalidLoginTest() {
        WebDriverWait wait = new WebDriverWait(driver, 15); // 15 seconds wait

        // Wait for the userName input to be present
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
        // Enter invalid userName
        driver.findElement(By.name("username")).sendKeys("InvalidUser");

        // Wait for the password input to be present
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("password")));
        // Enter invalid Password
        driver.findElement(By.name("password")).sendKeys("InvalidPass");

        // Wait for the login button to be clickable
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
        // Click the login button
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        // Wait for the invalid credentials error message to be visible
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Invalid credentials')]")));
            System.out.println("Invalid credentials error message displayed after trying to login with invalid credentials");
        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("Invalid credentials ");
        }
    }

    public void logout() {
        WebDriverWait wait = new WebDriverWait(driver, 15); // 10 seconds wait

        // Click on the profile dropdown to expand it
        By profileDropdown = By.xpath("//p[@class='oxd-userdropdown-name']");
        wait.until(ExpectedConditions.elementToBeClickable(profileDropdown)).click();

        // Click on the Logout link
        By logoutLink = By.xpath("//a[text()='Logout']");
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();

        // Wait for the login page to load after logout
        wait.until(ExpectedConditions.titleContains("OrangeHRM"));
        System.out.println("Logged out successfully");
    }

    @AfterTest
    public void tearDown() throws InterruptedException {
        Thread.sleep(5000); // Wait for  seconds before quitting
        driver.close();
        driver.quit();
    }
}
