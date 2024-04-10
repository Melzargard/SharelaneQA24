import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SignUpTest {
    WebDriver driver;

    @BeforeMethod

    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

    }


    @Test
    public void zipCode5Digits() {
        driver.get("https://www.sharelane.com/cgi-bin/register.py");
        driver.findElement(By.name("zip_code")).sendKeys("11111");
        driver.findElement(By.cssSelector("input[value=Continue]")).click();
        boolean isSignUpPageOpened = driver.findElement(By.cssSelector("input[value=Register]")).isDisplayed();
        assertTrue(isSignUpPageOpened, "Страница регистрации не открылась");

    }

    @Test
    public void zipCode4Digits() {
        driver.get("https://www.sharelane.com/cgi-bin/register.py");
        driver.findElement(By.name("zip_code")).sendKeys("1111");
        driver.findElement(By.cssSelector("input[value=Continue]")).click();
        String actualError = driver.findElement(By.cssSelector("span[class=error_message]")).getText();
        assertEquals(actualError, "Oops, error on page. ZIP code should have 5 digits",
                "Wrong error message shown");
    }

    @Test
    public void zipCode6Digits() {
        driver.get("https://www.sharelane.com/cgi-bin/register.py");
        driver.findElement(By.name("zip_code")).sendKeys("111111");
        driver.findElement(By.cssSelector("input[value=Continue]")).click();
        String actualError = driver.findElement(By.cssSelector("span[class=error_message]")).getText();
        assertEquals(actualError, "Oops, error on page. ZIP code should have 5 digits",
                "Wrong error message shown");
    }

    @Test
    public void successfulSignUp() {
        driver.get("https://www.sharelane.com/cgi-bin/register.py?page=1&zip_code=11111");
        driver.findElement(By.name("first_name")).sendKeys("Test");
        driver.findElement(By.name("last_name")).sendKeys("Test");
        driver.findElement(By.name("email")).sendKeys("Test@test.com");
        driver.findElement(By.name("password1")).sendKeys("12345678");
        driver.findElement(By.name("password2")).sendKeys("12345678");
        driver.findElement(By.cssSelector("input[value=Register]")).click();
        String actualError = driver.findElement(By.cssSelector("span[class=confirmation_message]")).getText();
        assertEquals(actualError, "Account is created!",
                "User was not registered");

    }

    @Test
    public void badSignUpWithoutDotCom() {
        driver.get("https://www.sharelane.com/cgi-bin/register.py?page=1&zip_code=11111");
        driver.findElement(By.name("first_name")).sendKeys("Test");
        driver.findElement(By.name("last_name")).sendKeys("Test");
        driver.findElement(By.name("email")).sendKeys("Test@test");
        driver.findElement(By.name("password1")).sendKeys("12345678");
        driver.findElement(By.name("password2")).sendKeys("12345678");
        driver.findElement(By.cssSelector("input[value=Register]")).click();
        String actualError = driver.findElement(By.cssSelector("span[class=error_message]")).getText();
        assertEquals(actualError,
                "Oops, error on page. Some of your fields have invalid data or email was previously used",
                "User was registered");
    }

    @Test
    public void badSignUpNumberFirstName() {
        driver.get("https://www.sharelane.com/cgi-bin/register.py?page=1&zip_code=11111");
        driver.findElement(By.name("first_name")).sendKeys("Test1");
        driver.findElement(By.name("last_name")).sendKeys("Test");
        driver.findElement(By.name("email")).sendKeys("Test@test.com");
        driver.findElement(By.name("password1")).sendKeys("12345678");
        driver.findElement(By.name("password2")).sendKeys("12345678");
        driver.findElement(By.cssSelector("input[value=Register]")).click();
        String actualError = driver.findElement(By.cssSelector("span[class=error_message]")).getText();
        assertEquals(actualError,
                "Oops, error on page. Some of your fields have invalid data or email was previously used",
                "User was registered");
    }

    @Test
    public void badSignUpNumberLastName() {
        driver.get("https://www.sharelane.com/cgi-bin/register.py?page=1&zip_code=11111");
        driver.findElement(By.name("first_name")).sendKeys("Test");
        driver.findElement(By.name("last_name")).sendKeys("Test1");
        driver.findElement(By.name("email")).sendKeys("Test@test.com");
        driver.findElement(By.name("password1")).sendKeys("12345678");
        driver.findElement(By.name("password2")).sendKeys("12345678");
        driver.findElement(By.cssSelector("input[value=Register]")).click();
        String actualError = driver.findElement(By.cssSelector("span[class=error_message]")).getText();
        assertEquals(actualError,
                "Oops, error on page. Some of your fields have invalid data or email was previously used",
                "User was registered");
    }

    @Test
    public void badSignUpWithoutCom() {
        driver.get("https://www.sharelane.com/cgi-bin/register.py?page=1&zip_code=11111");
        driver.findElement(By.name("first_name")).sendKeys("Test");
        driver.findElement(By.name("last_name")).sendKeys("Test");
        driver.findElement(By.name("email")).sendKeys("Test@test.");
        driver.findElement(By.name("password1")).sendKeys("12345678");
        driver.findElement(By.name("password2")).sendKeys("12345678");
        driver.findElement(By.cssSelector("input[value=Register]")).click();
        String actualError = driver.findElement(By.cssSelector("span[class=error_message]")).getText();
        assertEquals(actualError,
                "Oops, error on page. Some of your fields have invalid data or email was previously used",
                "User was registered");
    }

    @Test
    public void badSignUpWithDiffPass() {
        driver.get("https://www.sharelane.com/cgi-bin/register.py?page=1&zip_code=11111");
        driver.findElement(By.name("first_name")).sendKeys("Test");
        driver.findElement(By.name("last_name")).sendKeys("Test");
        driver.findElement(By.name("email")).sendKeys("Test@test.");
        driver.findElement(By.name("password1")).sendKeys("12345678");
        driver.findElement(By.name("password2")).sendKeys("87654321");
        driver.findElement(By.cssSelector("input[value=Register]")).click();
        String actualError = driver.findElement(By.cssSelector("span[class=error_message]")).getText();
        assertEquals(actualError,
                "Oops, error on page. Some of your fields have invalid data or email was previously used",
                "User was registered");
    }

    @Test
    public void passwordOneStarsTest() {
        driver.get("https://www.sharelane.com/cgi-bin/register.py?page=1&zip_code=11111");
        driver.findElement(By.name("password1")).sendKeys("12345678");
        String actualType = driver.findElement(By.name("password1")).getAttribute("type");
        assertEquals(actualType, "Password", "No stars");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }



}
