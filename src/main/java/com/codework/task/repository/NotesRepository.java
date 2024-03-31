package com.codework.task.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codework.task.entity.Notes;
import com.codework.task.entity.User;

@Repository
public interface NotesRepository extends JpaRepository<Notes, Integer>{

    // public List<Notes> findByUser(User user);

    public Page<Notes> findByUser(User user, Pageable pageable);
    
}
