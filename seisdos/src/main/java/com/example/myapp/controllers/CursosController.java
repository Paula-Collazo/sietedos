package com.example.myapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.myapp.domain.Curso;
import com.example.myapp.domain.Tematica;
import com.example.myapp.services.AutorService;
import com.example.myapp.services.CursoService;

import jakarta.validation.Valid;



@Controller
public class CursosController {

    @Autowired
    public CursoService cursoService;

    @Autowired
    public AutorService autorService;


    private String txtMsg;

    @GetMapping({ "/", "/list" })
    public String showList(Model model) {
        model.addAttribute("listaCursos", cursoService.obtenerTodos());
        if (txtMsg != null) {
            model.addAttribute("msg", txtMsg);
            txtMsg = null;
        }
        return "curso/listView";
    }

    @PostMapping("/buscar")
    public String searchCursos(String nombre, String tematica, Model model) {
        Tematica t = null;
        if (tematica != null && !tematica.isEmpty()) {
            try {
                t = Tematica.valueOf(tematica);
            } catch (IllegalArgumentException e) {
                t = null;
            }
        }
        List<Curso> entity = cursoService.buscarPorNombreYTematica(nombre, t);
        model.addAttribute("listaCursos", entity);
        model.addAttribute("msg", "Resultados de la búsqueda");
        model.addAttribute("nombreBusqueda", nombre);
        model.addAttribute("tematicaSeleccionada", tematica);
        return "curso/listView";
    }
    

    @GetMapping("/{id}")
    public String showElement(@PathVariable Long id, Model model) {
        try {
            Curso curso = cursoService.obtenerPorId(id);
            model.addAttribute("curso", curso);
            return "curso/listOneView";

        } catch (RuntimeException e) {
           txtMsg = e.getMessage();
           return "redirect:/";
        }
        
    }

    @GetMapping("/nuevo")
    public String showNew(Model model) {
        // el commandobject del formulario es una instancia de curso vacia
        model.addAttribute("cursoForm", new Curso());
        model.addAttribute("listaAutores", autorService.obtenerTodos());
        return "curso/newFormView";
    }

    @PostMapping("/nuevo/submit")
    public String showNewSubmit(@Valid Curso cursoForm,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            txtMsg = "Error en formulario";
            return "redirect:/";
        }

        try {
            cursoService.añadir(cursoForm);
            txtMsg = "Operación realizada con éxito";
            return "redirect:/";
            
        } catch (RuntimeException e) {
           txtMsg = e.getMessage();
           return "redirect:/";
        }
       
    }

    @GetMapping("/editar/{id}")
    public String showEditForm(@PathVariable long id, Model model) {
        try {
            Curso curso = cursoService.obtenerPorId(id);
            model.addAttribute("cursoForm", curso);
            model.addAttribute("listaAutores", autorService.obtenerTodos());
            return "curso/editFormView";
        } catch (RuntimeException e) {
           txtMsg = e.getMessage();
           return "redirect:/";
        }
        
    }

    @PostMapping("/editar/{id}/submit")
    public String showEditSubmit(@PathVariable Long id, @Valid Curso cursoForm,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            txtMsg = "Error en formulario";
            return "redirect:/";
        }
        try {
            cursoService.editar(cursoForm);
            txtMsg = "Operación exitosa";
            return "redirect:/";
        } catch (RuntimeException e) {
           txtMsg = e.getMessage();
           return "redirect:/";
        }
        
       
    }

    @GetMapping("/borrar/{id}")
    public String showDelete(@PathVariable long id) {
        cursoService.borrar(id);
        txtMsg = "Operación realizada con éxito";
        return "redirect:/";
    }

    @GetMapping("/listado1/{precio}")
    public String showListado1(@PathVariable Double precio, Model model) {
        List<Curso> cursos = cursoService.obtenerCursoPrecioMenor(precio);
        model.addAttribute("tituloListado", "Curso con precio menor al introducido");
        model.addAttribute("listaCursos", cursos);
        return "curso/listadosView";
    }
    
}
