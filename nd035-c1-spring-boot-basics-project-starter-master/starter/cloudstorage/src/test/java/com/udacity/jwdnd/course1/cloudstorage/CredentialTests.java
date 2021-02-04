package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
    @Order(1)
    public void testZCreateCredentialAndDisplay() throws InterruptedException {
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
    @Order(2)
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

        List<WebElement> credentialList = credentialTable.findElements(By.tagName("tr"));
        String deleteTitle = null;
        WebElement deleteElement = null;
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
                List<WebElement> tdCols = row.findElements(By.tagName("td"));
                for (WebElement col : tdCols) {
                    EncryptionService eS= new EncryptionService();
                    eS.
                    if (password.equals(col.getText())) {
                        Assertions.assertEquals(1, 1);
                        isPasswordCorrect = true;
                        break;
                    }
                    if(username.equals(col.getText())){
                        Assertions.assertEquals(1, 1);
                        isUsernameCorrect = true;
                        break;
                    }
                    System.out.print(col.getText() + "\t");
                }
            }

        }
        if(!isURLCorrect || !isPasswordCorrect || !isUsernameCorrect)
            Assertions.assertEquals(1, 2);

    }


    public void testZDeleteNoteAndVerify() throws InterruptedException {
        //Thread.sleep(2000);
        //signUp();
        Login();

        HomePage homePage = new HomePage(driver);
        homePage.goToNotesTab();

        NoteForm noteForm = homePage.deleteDisplayNote();
        System.out.println(noteForm.getNoteTitle());
        String noteTitle = null;

        ResultPage resultPage = new ResultPage(driver);
        resultPage.goToHome();

        homePage.goToNotesTab();

        WebElement notesTable = driver.findElement(By.cssSelector("#userTable"));
        List<WebElement> notesList = notesTable.findElements(By.tagName("tr"));
        String deleteTitle = null;
        WebElement deleteElement = null;
        for (int i = 0; i < notesList.size(); i++) {
            if(i==0){
                continue;
            }
            WebElement row = notesList.get(i);
            List<WebElement> cols = row.findElements(By.tagName("th"));
            for (WebElement col : cols) {
                noteTitle = (String)col.getText();
                if(noteTitle.equals(noteForm.getNoteTitle())){
                    Assertions.assertEquals(1, 2);
                    break;
                }
                System.out.print(col.getText() + "\t");
            }
        }
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
