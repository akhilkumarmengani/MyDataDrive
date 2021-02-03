package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
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
import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NoteTests {

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
    public void testCreateAndDisplay() throws InterruptedException {
        signUp();
        Login();

        String noteTitle = "My Notes 1";
        String noteDescription = "My Notes 1 Description";

        HomePage homePage = new HomePage(driver);
        homePage.createNoteAndSave(noteTitle,noteDescription);

        ResultPage resultPage = new ResultPage(driver);
        resultPage.goToHome();
        Thread.sleep(2000);

        homePage.goToNotesTab();

        NoteForm noteForm = homePage.getDisplayNote();

        Assertions.assertEquals(noteTitle, noteForm.getNoteTitle());
        Assertions.assertEquals(noteDescription, noteForm.getNoteDescription());

        //homePage.deleteDisplayNote();

        //resultPage.goToHome();

        //homePage.logout();

        //driver.get(baseURL+"/login");
    }

    @Test
    public void modifyNoteAndSave() throws InterruptedException {
        String newTitle = "My New Title 1";
        String newDescription = "My New Description 1";

        //Thread.sleep(2000);
        //signUp();
        Login();

        HomePage homePage = new HomePage(driver);
        homePage.goToNotesTab();

        NoteForm noteForm = homePage.modifyDisplayNote(newTitle,newDescription);
        System.out.println(noteForm.getNoteTitle());

        ResultPage resultPage = new ResultPage(driver);
        resultPage.goToHome();

        homePage.goToNotesTab();

        WebElement notesTable = driver.findElement(By.id("userTable"));
        wait.until(ExpectedConditions.elementToBeClickable(notesTable));

        List<WebElement> notesList = notesTable.findElements(By.tagName("tr"));
        String deleteTitle = null;
        WebElement deleteElement = null;
        boolean isEdited = false;
        for (int i = 0; i < notesList.size(); i++) {
            WebElement row = notesList.get(i);
            List<WebElement> cols = row.findElements(By.tagName("th"));
            for (WebElement col : cols) {
                if(newTitle.equals(col.getText())){
                    Assertions.assertEquals(1, 1);
                    isEdited = true;
                    break;
                }
                System.out.print(col.getText() + "\t");
            }
        }
        if(!isEdited)
            Assertions.assertEquals(1, 2);

    }


    @Test
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
