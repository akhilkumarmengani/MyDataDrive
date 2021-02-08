package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CredentialTests {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    String baseURL;

    private WebDriverWait wait;

    String username ="amengani";
    String password ="123";
    String firstName = "Akhil Kumar";
    String LastName = "Mengani";

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {

        this.driver = new ChromeDriver();
        baseURL = "http://localhost:" + this.port;
        wait = new WebDriverWait(driver, 50);
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
            driver =null;
        }
    }

    public void getLoginPage() {
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    public void getHomePage() {
        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Home", driver.getTitle());
    }

    @Test
    @AutoConfigureOrder(3)
    public void testCreateCredentialAndDisplay() throws InterruptedException {
        signUp();
        Login();

        String URL = "https://www.google.com";
        String username = "akhil";
        String password = "12345";

        HomePage homePage = new HomePage(driver);
        homePage.createCredentialsAndSave(URL,username,password);

        ResultPage resultPage = new ResultPage(driver);
        resultPage.goToHome();
        Thread.sleep(2000);

        homePage.goToCredentialsTab();

        CredentialForm credentialForm = homePage.getDisplayCredential();

        Assertions.assertEquals(URL, credentialForm.getURL());
        Assertions.assertEquals(username, credentialForm.getUsername());
        Assertions.assertNotEquals(password,credentialForm.getPassword());

    }

    @Test
    @AutoConfigureOrder(2)
    public void testModifyCredentialsAndSave() throws InterruptedException {
        String URL = "NewURL";
        String username = "NewUsername";
        String password = "NewPassword";

        //Thread.sleep(2000);
        signUp();
        Login();

        String oldURL = "https://www.google.com";
        String oldUsername = "akhil";
        String oldPassword = "12345";

        HomePage homePage = new HomePage(driver);
        homePage.createCredentialsAndSave(oldURL,oldUsername,oldPassword);

        ResultPage resultPage = new ResultPage(driver);
        resultPage.goToHome();


        CredentialForm credentialForm = homePage.modifyDisplayCredential(URL,username,password);
        System.out.println(credentialForm.getPassword());

        resultPage.goToHome();

        homePage.goToCredentialsTab();

        WebElement credentialTable = driver.findElement(By.id("credentialTable"));
        wait.until(ExpectedConditions.elementToBeClickable(credentialTable));

        List<WebElement> tBody = credentialTable.findElements(By.tagName("tbody"));
        if(tBody==null)
            Assertions.assertEquals(1, 2);
        List<WebElement> credentialList = tBody.get(0).findElements(By.tagName("tr"));


        boolean isURLCorrect = false;
        boolean isPasswordCorrect = false;
        boolean isUsernameCorrect = false;

        for (int i = 0; i < credentialList.size(); i++) {
            WebElement row = credentialList.get(i);
            List<WebElement> thCols = row.findElements(By.tagName("th"));
            for (WebElement col : thCols) {
                if(URL.equals(col.getText())){
                    Assertions.assertEquals(1, 1);
                    isURLCorrect = true;
                    break;
                }
                System.out.print(col.getText() + "\t");
            }
            if(isURLCorrect) {

                List<WebElement> tdBCols = tBody.get(0).findElements(By.tagName("td"));

                for (WebElement col : tdBCols) {
                    isPasswordCorrect = false;
                    isUsernameCorrect = false;
                    if (!password.equals(col.getText())) {
                        Assertions.assertEquals(1, 1);
                        isPasswordCorrect = true;
                    }
                    if(username.equals(col.getText())){
                        Assertions.assertEquals(1, 1);
                        isUsernameCorrect = true;
                    }
                    System.out.print(col.getText() + "\t");
                }

            }

        }
        if(!isURLCorrect || !isPasswordCorrect || !isUsernameCorrect)
            Assertions.assertEquals(1, 2);

    }

    @Test
    @AutoConfigureOrder(1)
    public void testDeleteNoteAndVerify() throws InterruptedException {

        signUp();
        Login();

        String url = "https://www.google.com";
        String username = "akhil";
        String password = "12345";

        HomePage homePage = new HomePage(driver);
        homePage.createCredentialsAndSave(url,username,password);

        ResultPage resultPage = new ResultPage(driver);
        resultPage.goToHome();

        CredentialForm credentialForm = homePage.deleteDisplayCredential();
        System.out.println(credentialForm.getURL());

        resultPage.goToHome();

        homePage.goToCredentialsTab();

        WebElement credentialTable = driver.findElement(By.id("credentialTable"));
        List<WebElement> tBody = credentialTable.findElements(By.tagName("tBody"));
        List<WebElement> credentialList = tBody.get(0).findElements(By.tagName("tr"));

        if(tBody == null || tBody.size() == 0)
            Assertions.assertNotEquals(1,2);
        boolean isURLDeleted = true;

        for (int i = 0; i < credentialList.size(); i++) {
            WebElement row = credentialList.get(i);
            List<WebElement> thCols = row.findElements(By.tagName("th"));
            for (WebElement col : thCols) {
                if(credentialForm.getURL().equals(col.getText())){
                    Assertions.assertEquals(1, 1);
                    isURLDeleted = false;
                    break;
                }
                System.out.print(col.getText() + "\t");
            }


        }
        if(!isURLDeleted)
            Assertions.assertEquals(1, 2);

    }



    public void signUp() throws InterruptedException {
        driver.get(baseURL+"/signup");

        SignupPage signupPage = new SignupPage(driver);
        signupPage.signup(firstName,LastName,username,password);
    }

    public void Login() throws InterruptedException {
        driver.get(baseURL+"/login");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username,password);
    }

}
