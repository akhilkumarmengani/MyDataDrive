package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> getAllNotesForUser(int userId){
        return noteMapper.getAllNotes(userId);
    }

    public int insert(Note note){
        return noteMapper.insert(note);
    }

    public Note getNote(int noteId){
        return noteMapper.getNote(noteId);
    }

    public void deleteNote(int noteId){
        noteMapper.deleteNote(noteId);
    }

    public void updateNote(NoteForm noteForm){
        noteMapper.updateNote(noteForm);
    }

}
