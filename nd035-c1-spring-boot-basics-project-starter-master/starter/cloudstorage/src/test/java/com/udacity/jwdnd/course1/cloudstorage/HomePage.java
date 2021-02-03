package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    @FindBy(id="btnLogout")
    private WebElement btnLogout;

    @FindBy(id="fileUpload")
    private WebElement fileUpload;

    @FindBy(id="btnAddNewNote")
    private WebElement btnAddNewNote;

    @FindBy(id="btnEditNote")
    private WebElement btnEditNote;

    @FindBy(id="aDeleteNote")
    private WebElement aDeleteNote;

    @FindBy(id="listNoteTitle")
    private WebElement listNoteTitle;

    @FindBy(id="listNoteDescription")
    private WebElement listNoteDescription;

    @FindBy(id="note-title")
    private WebElement noteTitle;

    @FindBy(id="note-description")
    private WebElement noteDescription;

    @FindBy(id="noteSubmit")
    private WebElement noteSubmit;

    @FindBy(id="btnNoteSaveChanges")
    private WebElement btnNoteSaveChanges;

    @FindBy(id="btnAddNewCredential")
    private WebElement btnAddNewCredential;

    @FindBy(id="btnEditCredential")
    private WebElement btnEditCredential;

    @FindBy(id="aDeleteCredential")
    private WebElement aDeleteCredential;

    @FindBy(id="listCredentialURL")
    private WebElement listCredentialURL;

    @FindBy(id="listCredentialUsername")
    private WebElement listCredentialUsername;

    @FindBy(id="listCredentialPassword")
    private WebElement listCredentialPassword;

    @FindBy(id="credential-url")
    private WebElement credentialURL;

    @FindBy(id="credential-username")
    private WebElement credentialUsername;

    @FindBy(id="credential-password")
    private WebElement credentialPassword;

    @FindBy(id="credentialSubmit")
    private WebElement credentialSubmit;

    @FindBy(id="btnCredentialSaveChanges")
    private WebElement btnCredentialSaveChanges;

    @FindBy(id="nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id="nav-credentials-tab")
    private WebElement credentialsTab;

    private final WebDriverWait wait;
    private JavascriptExecutor jse;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 50);
        this.jse =(JavascriptExecutor) driver;
    }

    public void logout() throws InterruptedException {
        Thread.sleep(1000);
        wait.until(ExpectedConditions.elementToBeClickable(this.btnLogout)).click();
    }

    public void editNote(){
        this.btnEditNote.click();
    }

    public void editNoteDetailsAndSave(String noteTitleStr,String noteDescriptionStr){
        wait.until(ExpectedConditions.elementToBeClickable(noteTitle)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(noteTitle)).sendKeys(noteTitleStr);

        wait.until(ExpectedConditions.elementToBeClickable(noteDescription)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(noteDescription)).sendKeys(noteDescriptionStr);

        this.btnNoteSaveChanges.click();
    }

    public void goToNotesTab(){
        //wait.until(ExpectedConditions.elementToBeClickable(notesTab)).click();
        jse.executeScript("arguments[0].click()", notesTab);
    }

    public void createNoteAndSave(String title, String description) throws InterruptedException {

        goToNotesTab();

        Thread.sleep(1000);
        wait.until(ExpectedConditions.elementToBeClickable(btnAddNewNote)).click();

        wait.until(ExpectedConditions.elementToBeClickable(noteTitle)).sendKeys(title);;
        wait.until(ExpectedConditions.elementToBeClickable(noteDescription)).sendKeys(description);;


        Thread.sleep(1000);

        btnNoteSaveChanges.click();
    }

    public NoteForm getDisplayNote(){

        goToNotesTab();
        String noteTitle = wait.until(ExpectedConditions.elementToBeClickable(listNoteTitle)).getText();
        String noteDescription = listNoteDescription.getText();

        return new NoteForm(noteTitle,noteDescription);
    }

    public NoteForm deleteDisplayNote(){
        goToNotesTab();

        String noteTitle = wait.until(ExpectedConditions.elementToBeClickable(listNoteTitle)).getText();
        String noteDescription = listNoteDescription.getText();

        NoteForm noteForm = new NoteForm(noteTitle,noteDescription);
        aDeleteNote.click();

        return noteForm;
    }

    public NoteForm modifyDisplayNote(String newTitle,String newDescription){
        goToNotesTab();

        String noteTitle = wait.until(ExpectedConditions.elementToBeClickable(listNoteTitle)).getText();
        String noteDescription = listNoteDescription.getText();

        NoteForm noteForm = new NoteForm(noteTitle,noteDescription);

        btnEditNote.click();

        wait.until(ExpectedConditions.elementToBeClickable(this.noteTitle)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(this.noteDescription)).clear();

        wait.until(ExpectedConditions.elementToBeClickable(this.noteTitle)).sendKeys(newTitle);;
        wait.until(ExpectedConditions.elementToBeClickable(this.noteDescription)).sendKeys(newDescription);;


        btnNoteSaveChanges.click();


        return noteForm;
    }

    public void createCredentialsAndSave(String url, String username, String password) {
        goToCredentialsTab();

        //Thread.sleep(1000);
        wait.until(ExpectedConditions.elementToBeClickable(btnAddNewCredential)).click();

        wait.until(ExpectedConditions.elementToBeClickable(this.credentialURL)).sendKeys(url);;
        wait.until(ExpectedConditions.elementToBeClickable(this.credentialUsername)).sendKeys(username);
        wait.until(ExpectedConditions.elementToBeClickable(this.credentialPassword)).sendKeys(password);
        //Thread.sleep(1000);

        btnCredentialSaveChanges.click();
    }

    public void goToCredentialsTab() {
        jse.executeScript("arguments[0].click()", credentialsTab);
    }

    public CredentialForm getDisplayCredential() {
        goToCredentialsTab();
        String URL = wait.until(ExpectedConditions.elementToBeClickable(listCredentialURL)).getText();
        String username = listCredentialUsername.getText();
        String encryptedPassword = listCredentialPassword.getText();

        return new CredentialForm(URL,username,encryptedPassword);
    }

    public CredentialForm modifyDisplayCredential(String newUrl, String newUsername, String newPassword) {
        goToCredentialsTab();

        String URL = wait.until(ExpectedConditions.elementToBeClickable(listCredentialURL)).getText();
        String username = listCredentialUsername.getText();
        String encryptedPassword = listCredentialPassword.getText();

        CredentialForm credentialForm = new CredentialForm(URL,username,encryptedPassword);

        btnEditCredential.click();

        wait.until(ExpectedConditions.elementToBeClickable(this.credentialURL)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(this.credentialUsername)).clear();

        String UnencryptedPassword = this.credentialPassword.getText();

        Assertions.assertNotEquals(encryptedPassword,UnencryptedPassword);

        wait.until(ExpectedConditions.elementToBeClickable(this.credentialPassword)).clear();

        wait.until(ExpectedConditions.elementToBeClickable(this.credentialURL)).sendKeys(newUrl);
        wait.until(ExpectedConditions.elementToBeClickable(this.credentialPassword)).sendKeys(newPassword);
        wait.until(ExpectedConditions.elementToBeClickable(this.credentialUsername)).sendKeys(newUsername);

        btnCredentialSaveChanges.click();


        return credentialForm;
    }
}
