package com.udacity.jwdnd.course1.cloudstorage;

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

import java.io.File;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/

	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();


		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success-msg")));
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}



	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");

		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() throws InterruptedException {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Thread.sleep(5000);
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}

	@Test
	@Order(1)
	public void unauthorizedUserTest() throws InterruptedException {
		getLoginPage();
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("http://localhost:" + this.port + "/signup", driver.getCurrentUrl());
	}

	@Test
	@Order(2)
	public void normalFlowSignup()  throws InterruptedException {
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		doMockSignUp("abdullah","almilfy","abdu","myPass");
		doLogIn("abdu","myPass");
		Assertions.assertEquals("http://localhost:" + this.port + "/home",driver.getCurrentUrl());
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logoutButton")));
		WebElement logoutButton = driver.findElement(By.id("logoutButton"));
		logoutButton.click();
		unauthorizedUserTest();
	}



	@Test
	@Order(3)
	public void createNote(){
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		doLogIn("abdu","myPass");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement selectNote = driver.findElement(By.id("nav-notes-tab"));
		selectNote.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addNote")));
		WebElement addNote = driver.findElement(By.id("addNote"));
		addNote.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement noteTitle = driver.findElement(By.id("note-title"));
		WebElement noteDescription = driver.findElement(By.id("note-description"));
		noteTitle.sendKeys("First Note");
		noteDescription.sendKeys("Here is the description");
		WebElement submitNote = driver.findElement(By.id("submitNote"));
		submitNote.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		driver.findElement(By.id("nav-notes-tab")).click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteTitleText")));
		WebElement noteTitleText = driver.findElement(By.id("noteTitleText"));
		WebElement noteDescriptionText = driver.findElement(By.id("noteDescriptionText"));
		Assertions.assertEquals("First Note",noteTitleText.getText());
		Assertions.assertEquals("Here is the description",noteDescriptionText.getText());
	}

    @Test
	@Order(4)
	public void editNote() throws InterruptedException {
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		doLogIn("abdu","myPass");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement selectNote = driver.findElement(By.id("nav-notes-tab"));
		selectNote.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("editNoteButton")));
		WebElement editNote = driver.findElement(By.id("editNoteButton"));
		editNote.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement noteTitle = driver.findElement(By.id("note-title"));
		WebElement noteDescription = driver.findElement(By.id("note-description"));
		noteTitle.clear();
		noteDescription.clear();
		noteTitle.sendKeys("Edit Note");
		noteDescription.sendKeys("I feel like editing");
		WebElement submitNote = driver.findElement(By.id("submitNote"));
		submitNote.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		driver.findElement(By.id("nav-notes-tab")).click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteTitleText")));
		WebElement noteTitleText = driver.findElement(By.id("noteTitleText"));
		WebElement noteDescriptionText = driver.findElement(By.id("noteDescriptionText"));
		Assertions.assertEquals("Edit Note", noteTitleText.getText());
		Assertions.assertEquals("I feel like editing", noteDescriptionText.getText());
	}

	@Test
	@Order(5)
	public void deleteNote(){
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		doLogIn("abdu","myPass");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement selectNote = driver.findElement(By.id("nav-notes-tab"));
		selectNote.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("deleteNoteButton")));
		WebElement deleteNote = driver.findElement(By.id("deleteNoteButton"));
		deleteNote.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		driver.findElement(By.id("nav-notes-tab")).click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userTable")));
		WebElement table = driver.findElement(By.id("userTable"));
		int numOfElements = table.findElements(By.tagName("tbody")).size();
		Assertions.assertEquals(0,numOfElements);
	}


	@Test
	@Order(6)
	public void createCredentials(){
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		doMockSignUp("ali","sam","Ali","PASS");
		doLogIn("Ali","PASS");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement selectCredentials = driver.findElement(By.id("nav-credentials-tab"));
		selectCredentials.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addCredentialButton")));
		WebElement addCredentialButton = driver.findElement(By.id("addCredentialButton"));
		addCredentialButton.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement credentialUrl = driver.findElement(By.id("credential-url"));
		WebElement credentialUsername = driver.findElement(By.id("credential-username"));
		WebElement credentialPassword = driver.findElement(By.id("credential-password"));
		WebElement submitCredentials = driver.findElement(By.id("submitCredentials"));
		credentialUrl.sendKeys("localhost:8080/home");
		credentialPassword.sendKeys("StrongPassword");
		credentialUsername.sendKeys("ABDULLAH");
		submitCredentials.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		driver.findElement(By.id("nav-credentials-tab")).click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
		WebElement credentialsTable = driver.findElement(By.id("credentialTable"));
		Assertions.assertTrue(credentialsTable.isDisplayed(),"Credentials is displayed");
		WebElement credentialsPassDisplay = driver.findElement(By.id("credentialPasswordDisplay"));
		Assertions.assertNotEquals("StrongPassword",credentialsPassDisplay.getText());

	}


	@Test
	@Order(7)
	public void editCredentials(){
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		doLogIn("Ali","PASS");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement selectCredentials = driver.findElement(By.id("nav-credentials-tab"));
		selectCredentials.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("editCredentialsButton")));
		WebElement credentialsPassDisplay = driver.findElement(By.id("credentialPasswordDisplay"));
		WebElement editCredentialsButton = driver.findElement(By.id("editCredentialsButton"));
		editCredentialsButton.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement credentialPassword = driver.findElement(By.id("credential-password"));
		Assertions.assertNotEquals(credentialPassword,credentialsPassDisplay);
		WebElement credentialUrl = driver.findElement(By.id("credential-url"));
		WebElement credentialUsername = driver.findElement(By.id("credential-username"));
		credentialUsername.clear();
		credentialUrl.clear();
		credentialPassword.clear();
		credentialUrl.sendKeys("localhost:9032/something");
		credentialUsername.sendKeys("abdullah");
		credentialPassword.sendKeys("NewPASS");
		WebElement submitCredentials = driver.findElement(By.id("submitCredentials"));
        submitCredentials.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		driver.findElement(By.id("nav-credentials-tab")).click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
		WebElement credentialsTable = driver.findElement(By.id("credentialTable"));
		Assertions.assertTrue(credentialsTable.isDisplayed(),"Credentials is displayed");
		WebElement credentialsUrlDisplay = driver.findElement(By.id("credentialUrlDisplay"));
		WebElement credentialsUsernameDisplay = driver.findElement(By.id("credentialUsernameDisplay"));
		credentialsPassDisplay = driver.findElement(By.id("credentialPasswordDisplay"));

		Assertions.assertEquals("localhost:9032/something", credentialsUrlDisplay.getText());
		Assertions.assertEquals("abdullah",credentialsUsernameDisplay.getText());
		Assertions.assertNotEquals("NewPASS",credentialsPassDisplay.getText());
	}


	@Test
	@Order(8)
	public void deleteCredentials(){
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		doLogIn("Ali","PASS");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement selectCredentials = driver.findElement(By.id("nav-credentials-tab"));
		selectCredentials.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("deleteCredentialsButton")));
		WebElement deleteButton = driver.findElement(By.id("deleteCredentialsButton"));
		deleteButton.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		driver.findElement(By.id("nav-credentials-tab")).click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
		WebElement credTable = driver.findElement(By.id("credentialTable"));
		int numOfElements = credTable.findElements(By.tagName("tbody")).size();
	   Assertions.assertEquals(0,numOfElements);
	}



}
