package com.example.myapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.myapp.domain.Video;
import com.example.myapp.services.CursoService;
import com.example.myapp.services.VideoService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/video")
public class VideosController {
    
    @Autowired
    public VideoService videoService;
    
    @Autowired
    public CursoService cursoService;

    @GetMapping({ "/", "/list" })
    public String showList(Model model) {
        model.addAttribute("listaVideos", videoService.obtenerTodos());
       
        return "curso/video/listView";
    }


    @GetMapping("/new")
    public String showNew(Model model) {
        // el commandobject del formulario es una instancia de video vacia
        model.addAttribute("videoForm", new Video());
        model.addAttribute("listaCursos", cursoService.obtenerTodos());
        return "curso/video/newFormView";
    }

    @PostMapping("/new/submit")
    public String showNewSubmit(@Valid Video videoForm,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/video/new";
        }
        videoService.añadir(videoForm);
        return "redirect:/video/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable long id, Model model) {
        Video video = videoService.obtenerPorId(id);
        if(video != null) {
            model.addAttribute("videoForm", video);
            model.addAttribute("listaCursos", cursoService.obtenerTodos());
            return "curso/video/editFormView";
        } else {
            return "redirect:/video/list";
        }
        
    }
    // @GetMapping("/{id}")
    // public String shoForm(@PathVariable long id, Model model) {
    //     Video video = videoService.obtenerPorCursoId(id);
    //     if(video != null) {
    //         model.addAttribute("videoForm", video);
    //         return "curso/video/editFormView";
    //     } else {
    //         return "redirect:/video/list";
    //     }
        
    // }

    @PostMapping("/editar/submit")
    public String showEditSubmit(@Valid Video videoForm,
            BindingResult bindingResult) {

        if (!bindingResult.hasErrors()) {
            videoService.editar(videoForm);
        }
        return "redirect:/video/list";
       
    }

    @GetMapping("/delete/{id}")
    public String showDelete(@PathVariable long id) {
        videoService.borrar(id);
        return "redirect:/video/list";
    }
}
