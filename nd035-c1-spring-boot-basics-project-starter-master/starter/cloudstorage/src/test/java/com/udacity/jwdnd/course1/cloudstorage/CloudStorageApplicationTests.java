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
class CloudStorageApplicationTests {

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
	public void testUserSignUpLoginLogout() throws InterruptedException {

		signUp();

		Login();

		Assertions.assertEquals("Home", driver.getTitle());

		HomePage homePage = new HomePage(driver);
		homePage.logout();

		driver.get(baseURL + "/Login");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Assertions.assertEquals("Login", driver.getTitle());

	}

	@Test
	public void testUnauthorizedUser(){

		driver.get(baseURL+"/login");
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get(baseURL+"/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());

		driver.get(baseURL+"/home");
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get(baseURL+"/credentials");
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get(baseURL+"/notes");
		Assertions.assertEquals("Login", driver.getTitle());

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
