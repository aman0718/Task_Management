package com.codework.task.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.codework.task.entity.Notes;
import com.codework.task.entity.User;
import com.codework.task.service.NotesService;
import com.codework.task.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private NotesService notesService;


    @ModelAttribute
    public User getUser(Principal principal, Model model){
    
        String email = principal.getName();
        User newUser = userService.findByEmail(email);
        model.addAttribute("user", newUser);

        return newUser;
        
    }


    @GetMapping("/")
    public String userHome() {
        return "index";
    }


    @GetMapping("/dashboard")
    public String userDashboard(Model model, Principal principal, @RequestParam(defaultValue = "0") Integer pageNo) {
    
        User getUser = getUser(principal, model);
        List<Notes> allNotes = notesService.getAllNotes(getUser);
        model.addAttribute("notesList", allNotes);
        return "user_dashboard";
    }



    @GetMapping("/register")
    public String signUp() {
        return "register";
    }


    

     @GetMapping("/addNotes")
    public String addNotes(HttpSession session){

        
        return "notes";
    }

    @GetMapping("/viewNotes")
    public String viewAllNotes(Model model, Principal principal, @RequestParam(defaultValue = "0") Integer pageNo,HttpSession session){
        User get_user = getUser(principal, model);
        Page<Notes> notesList =  notesService.getNotesByUser(get_user, pageNo);
        System.out.println(session.getId());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalElements", notesList.getTotalElements());
        model.addAttribute("totalPages", notesList.getTotalPages());

        // model.addAttribute("notesList");
        model.addAttribute("notesList", notesList.getContent());

        return "view_notes";
    }


    @GetMapping("/editNotes/{id}")
    public String editNotes(@PathVariable int id, Model model){

        Notes notes = notesService.getNotesById(id);
        model.addAttribute("notes", notes);

        return "edit_notes";
    }


    @PostMapping("/saveNotes")
    public String handleSaveNotes(@ModelAttribute Notes notes, HttpSession session, Principal principal, Model model){
        
        notes.setDate(LocalDate.now());
        notes.setUser(getUser(principal, model));

        Notes note = notesService.saveNotes(notes);
        System.out.println(note);


        if(note != null)session.setAttribute("msg", "note saved successfully");
        else session.setAttribute("msg", "Something went wrong! Please check");

        return "redirect:/user/addNotes";

    }


    @PostMapping("/updateNotes")
    public String updateNotes(@ModelAttribute Notes notes, HttpSession session, Principal principal, Model model){
        
        notes.setDate(LocalDate.now());
        notes.setUser(getUser(principal, model));

        Notes note = notesService.saveNotes(notes);
        System.out.println(note);

        if(note != null)session.setAttribute("msg", "note update successfully");
        else session.setAttribute("msg", "Something went wrong! Please check");

        return "redirect:/user/viewNotes";
    }


    @GetMapping("/deleteNotes/{id}")
    public String removeNotes(@PathVariable int id, HttpSession session){

        boolean notes = notesService.deleteNotes(id);

        if(notes) session.setAttribute("msg", "note deleted");
        else session.setAttribute("msg", "something wrong!");

        return "redirect:/user/viewNotes";
    }

   
    
    
}
