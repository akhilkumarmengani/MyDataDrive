package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notes")
public class NoteController {
    NoteService noteService;
    UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping
    public String getNotePage(Authentication authentication,
                              @ModelAttribute("newFile") FileUploadForm noteForm,
                              @ModelAttribute("newNote") NoteForm newForm,
                              @ModelAttribute("newCredential") CredentialForm credentialForm,
                              Model model){
        model.addAttribute("allNotes",noteService.getAllNotesForUser(getLoginUserId(authentication)));
        return "home";
    }

    public int getLoginUserId(Authentication authentication){
        String username = authentication.getName();
        User user = userService.getUser(username);
        Integer userId = user.getUserId();
        return userId;
    }

    @PostMapping
    public String saveNote(Authentication authentication,
                           @ModelAttribute("newFile") FileUploadForm newFile,
                           @ModelAttribute("newNote") NoteForm newNote,
                           @ModelAttribute("newCredential") CredentialForm credentialForm,
                           Model model){
        Integer userId = getLoginUserId(authentication);
        if(newNote.getNoteId()!=null){
            noteService.updateNote(newNote);
        }
        else{
            noteService.insert(
                    new Note(null,
                            newNote.getNoteTitle(),
                            newNote.getNoteDescription(),
                            userId));
        }
        model.addAttribute("notes",noteService.getAllNotesForUser(userId));
        model.addAttribute("result","SUCCESS");
        return "result";
    }

//    @GetMapping(value = "delete-note")
//    pubic String
}
