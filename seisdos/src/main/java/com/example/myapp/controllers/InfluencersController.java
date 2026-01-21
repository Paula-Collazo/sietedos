package com.example.myapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.myapp.domain.Influencer;
import com.example.myapp.services.InfluencerService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/influencer")
public class InfluencersController {
    
    @Autowired
    public InfluencerService influencerService;

    @GetMapping({ "/", "/list" })
    public String showList(Model model) {
        model.addAttribute("listaInfluencers", influencerService.obtenerTodos());
       
        return "curso/influencer/listView";
    }


    @GetMapping("/new")
    public String showNew(Model model) {
        // el commandobject del formulario es una instancia de influencer vacia
        model.addAttribute("influencerForm", new Influencer());
        model.addAttribute("listaCursos", influencerService.obtenerTodos());
        return "curso/influencer/newFormView";
    }

    @PostMapping("/new/submit")
    public String showNewSubmit(@Valid Influencer influencerForm,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/influencer/new";
        }
        influencerService.añadir(influencerForm);
        return "redirect:/influencer/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable long id, Model model) {
        Influencer influencer = influencerService.obtenerPorId(id);
        if(influencer != null) {
            model.addAttribute("influencerForm", influencer);
            model.addAttribute("listaCursos", influencerService.obtenerTodos());
            return "curso/influencer/editFormView";
        } else {
            return "redirect:/influencer/list";
        }
        
    }
    // @GetMapping("/{id}")
    // public String shoForm(@PathVariable long id, Model model) {
    //     Influencer influencer = influencerService.obtenerPorCursoId(id);
    //     if(influencer != null) {
    //         model.addAttribute("influencerForm", influencer);
    //         return "curso/influencer/editFormView";
    //     } else {
    //         return "redirect:/influencer/list";
    //     }
        
    // }

    @PostMapping("/editar/submit")
    public String showEditSubmit(@Valid Influencer influencerForm,
            BindingResult bindingResult) {

        if (!bindingResult.hasErrors()) {
            influencerService.editar(influencerForm);
        }
        return "redirect:/influencer/list";
       
    }

    @GetMapping("/delete/{id}")
    public String showDelete(@PathVariable long id) {
        influencerService.borrar(id);
        return "redirect:/influencer/list";
    }
}

    

