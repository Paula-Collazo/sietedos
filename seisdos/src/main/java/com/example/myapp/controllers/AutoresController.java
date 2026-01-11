package com.example.myapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.myapp.domain.Autor;
import com.example.myapp.services.AutorService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/autor")
public class AutoresController {
    @Autowired
    public AutorService autorService;


    @GetMapping({ "/", "/list" })
    public String showList(Model model) {
        model.addAttribute("listaAutores", autorService.obtenerTodos());
       
        return "curso/autor/listView";
    }


    @GetMapping("/new")
    public String showNew(Model model) {
        // el commandobject del formulario es una instancia de autor vacia
        model.addAttribute("autorForm", new Autor());
        return "curso/autor/newFormView";
    }

    @PostMapping("/new/submit")
    public String showNewSubmit(@Valid Autor autorForm,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/autor/new";
        }
        autorService.añadir(autorForm);
        return "redirect:/autor/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable long id, Model model) {
        Autor autor = autorService.obtenerPorId(id);
        if(autor != null) {
            model.addAttribute("autorForm", autor);
            return "curso/autor/editFormView";
        } else {
            return "redirect:/autor/list";
        }
        
    }

    @PostMapping("/editar/submit")
    public String showEditSubmit(@Valid Autor autorForm,
            BindingResult bindingResult) {

        if (!bindingResult.hasErrors()) {
            autorService.editar(autorForm);
        }
        return "redirect:/autor/list";
       
    }

    @GetMapping("/delete/{id}")
    public String showDelete(@PathVariable long id) {
        autorService.borrar(id);
        return "redirect:/autor/list";
    }

    
}
