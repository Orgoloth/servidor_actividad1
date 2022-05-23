package edu.sanvalero.manuel.servidor_actividad1.contexts.notes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NoteService {
    @Autowired
    NoteRepository noteRepository;

    public void save(Note note) {
        noteRepository.save(note);
    }

    public Iterable<Note> findAll() {
        return noteRepository.findAll();
    }

    public Optional<Note> findById(Long id) {
        return noteRepository.findById(id);
    }

    public void delete(Note note){
        noteRepository.delete(note);
    }
}
