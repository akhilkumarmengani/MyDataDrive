package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/credentials")
public class CredentialController {
    UserService userService;
    CredentialService credentialService;
    EncryptionService encryptionService;

    public CredentialController(UserService userService, CredentialService credentialService, EncryptionService encryptionService) {
        this.userService = userService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }





    @GetMapping
    public String getUserFiles(Authentication authentication,
                               @ModelAttribute("newFile") FileUploadForm noteForm,
                               @ModelAttribute("newNote") NoteForm newForm,
                               @ModelAttribute("newCredential") CredentialForm credentialForm,
                               Model model) {

        int userId = getLoginUserId(authentication);
        model.addAttribute("allCredentials", credentialService.getAllCredentialsOfUser(userId));
        model.addAttribute("encryptionService",encryptionService);
        return "home";
    }

    public int getLoginUserId(Authentication authentication){
        String username = authentication.getName();
        User user = userService.getUser(username);
        Integer userId = user.getUserId();
        return userId;
    }

    @PostMapping("/add-credential")
    public String addCredential(Authentication authentication,
                                @ModelAttribute("newFile") FileUploadForm noteForm,
                                @ModelAttribute("newNote") NoteForm newForm,
                                @ModelAttribute("newCredential") CredentialForm credentialForm,
                                Model model){
        int userId = getLoginUserId(authentication);
        credentialService.insert(credentialForm,userId);

        model.addAttribute("allCredentials", credentialService.getAllCredentialsOfUser(userId));
        model.addAttribute("encryptionService",encryptionService);
        model.addAttribute("result","SUCCESS");

        return "result";
    }

    @GetMapping(value = "/get-credential/{credentialId}")
    public Credential getCredential(@PathVariable Integer credentialId) {
        return credentialService.getCredentialById(credentialId);
    }

    @GetMapping(value = "/delete-credential/{credentialId}")
    public String deleteCredential(
            Authentication authentication, @PathVariable Integer credentialId,
            @ModelAttribute("newFile") FileUploadForm noteForm,
            @ModelAttribute("newNote") NoteForm newForm,
            @ModelAttribute("newCredential") CredentialForm credentialForm,
            Model model) {
        int userId = getLoginUserId(authentication);
        credentialService.deleteCredentialById(credentialForm.getCredentialId());
        model.addAttribute("allCredentials", credentialService.getAllCredentialsOfUser(userId));
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("result", "success");
        return "result";
    }
}
