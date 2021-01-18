package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    FileService fileService;
    UserService userService;
    NoteService noteService;

    public HomeController(FileService fileService, UserService userService, NoteService noteService) {
        this.fileService = fileService;
        this.userService = userService;
        this.noteService = noteService;
    }

    @GetMapping
    public String getUserFiles(Authentication authentication,
                               @ModelAttribute("newFile") FileUploadForm noteForm,
                               @ModelAttribute("newNote") NoteForm newForm,
                               @ModelAttribute("newCredential") CredentialForm credentialForm,
                               Model model) {
        String username = authentication.getName();
        User user = userService.getUser(username);
        Integer userId = user.getUserId();
        List<String> allFileNames = fileService.getAllStoredFileNames(userId);
        model.addAttribute("allFileNames",allFileNames);
        model.addAttribute("allNotes",noteService.getAllNotesForUser(userId));
        return "home";
    }

    @PostMapping
    public String uploadFiles(Authentication authentication, @ModelAttribute("newFile") FileUploadForm newFile , Model model) throws IOException {
        if(newFile==null) {
            model.addAttribute("result", "FAILURE");
            return "result";
        }
        String username = authentication.getName();
        User user = userService.getUser(username);
        Integer userId = user.getUserId();
        MultipartFile multipartFile = newFile.getMultipartFile();
        String fileName = multipartFile.getOriginalFilename();
        if(fileName==null || fileName.isEmpty()){
            model.addAttribute("result", "FAILURE");
            return "result";
        }
        List<String> allFileNames = fileService.getAllStoredFileNames(userId);
        Boolean isDuplicate = false;
        for(String name:allFileNames){
            if(name.equals(fileName)){
                isDuplicate = true;
                break;
            }
        }
        if(!isDuplicate) {
            fileService.addFile(multipartFile, userId);
            model.addAttribute("result", "SUCCESS");
        }
        else{
            model.addAttribute("result","DUPLICATE_FILE_ERROR");
            model.addAttribute("errorMessage","File with name '"+fileName+"' already exists");
        }

        model.addAttribute("allFileNames",allFileNames);
        return "result";
    }

    @GetMapping(value = {"/get-file/{fileName}"},
                produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody
    byte[] getFile(@PathVariable String fileName,Authentication authentication) {
        String username = authentication.getName();
        User user = userService.getUser(username);
        Integer userId = user.getUserId();
        return fileService.getFileByName(userId,fileName).getFileData();
    }

    @GetMapping(value = {"/delete-file/{fileName}"})
    public String deleteFile(@PathVariable String fileName,
                             Authentication authentication,
                             @ModelAttribute("newFile") FileUploadForm newFile,
                             Model model) {
        String username = authentication.getName();
        User user = userService.getUser(username);
        Integer userId = user.getUserId();
        fileService.deleteFileByName(fileName);
        /*if(rows>0){
            model.addAttribute("result","SUCCESS");
        }
        else{
            model.addAttribute("result","FAILURE");
        }*/
        List<String> allFileNames = fileService.getAllStoredFileNames(userId);
        model.addAttribute("allFileNames",allFileNames);
        model.addAttribute("result","SUCCESS");
        return "result";
    }

}
