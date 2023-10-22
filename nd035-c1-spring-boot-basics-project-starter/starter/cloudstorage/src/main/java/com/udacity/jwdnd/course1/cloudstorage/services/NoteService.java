package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class NoteService {

    private final NotesMapper notesMapper;

    public NoteService(NotesMapper notesMapper) {
        this.notesMapper = notesMapper;
    }

    public ArrayList<Note> getNotes(Integer userId){
        return notesMapper.getNotes(userId);
    }

    public void addNote(Note note){
        Note newNote = new Note(null,note.getNoteTitle(),note.getNoteDescription(),note.getUserid());
       notesMapper.insertNote(newNote);
    }

    public void editNote(Note note){
        notesMapper.editNote(note);
    }

    public void deleteNote(Integer noteId) {
        notesMapper.deleteNote(noteId);
    }
}
