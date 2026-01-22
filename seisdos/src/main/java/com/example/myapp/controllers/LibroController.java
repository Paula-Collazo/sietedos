package com.example.myapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.myapp.domain.Curso;
import com.example.myapp.domain.Libro;
import com.example.myapp.services.CursoService;
import com.example.myapp.services.LibroService;

import jakarta.validation.Valid;



@Controller
@RequestMapping("/libro")
public class LibroController {

    @Autowired
    public LibroService libroService;

    @Autowired
    public CursoService cursoService;

    @GetMapping({ "/", "/list" })
    public String showList(Model model) {
        model.addAttribute("listaLibros", libroService.obtenerTodos());
       
        return "curso/libro/listView";
    }


    @GetMapping("/new")
    public String showNew(Model model) {
        // el commandobject del formulario es una instancia de libro vacia
        model.addAttribute("libroForm", new Libro());
        model.addAttribute("listaLibros", libroService.obtenerTodos());
        model.addAttribute("listaCursos", cursoService.obtenerTodos());
        return "curso/libro/newFormView";
    }

    @PostMapping("/new/submit")
public String showNewSubmit(@Valid Libro libroForm,
                            BindingResult bindingResult,
                            Model model) {

    // 1️⃣ Validaciones básicas
    if (bindingResult.hasErrors()) {
        // Mantener libroForm y enviar lista de cursos
        model.addAttribute("listaCursos", cursoService.obtenerTodos());
        return "curso/libro/newFormView"; // ✅ NO redirect
    }

    // 2️⃣ Validar que el curso seleccionado no tenga ya un libro
    if (libroForm.getCurso() != null && libroForm.getCurso().getId() != null) {
        Curso cursoSeleccionado = cursoService.obtenerPorId(libroForm.getCurso().getId());

        if (libroService.obtenerPorCurso(cursoSeleccionado) != null) {
            bindingResult.rejectValue("curso", "error.libro", "Este curso ya tiene un libro asignado");
            model.addAttribute("listaCursos", cursoService.obtenerTodos());
            return "curso/libro/newFormView"; // ✅ NO redirect
        }

        // Asignar el curso real al libro
        libroForm.setCurso(cursoSeleccionado);
    }

    // 3️⃣ Guardar libro
    libroService.anadir(libroForm);

    // 4️⃣ Redirigir al listado
    return "redirect:/libro/list";
}


    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable long id, Model model) {
        Libro libro = libroService.obtenerPorId(id);
        if(libro != null) {
            model.addAttribute("libroForm", libro);
            model.addAttribute("listaLibros", libroService.obtenerTodos());
            model.addAttribute("listaCursos", cursoService.obtenerTodos());
            return "curso/libro/editFormView";
        } else {
            return "redirect:/libro/list";
        }
        
    }
    // @GetMapping("/{id}")
    // public String shoForm(@PathVariable long id, Model model) {
    //     Libro libro = libroService.obtenerPorCursoId(id);
    //     if(libro != null) {
    //         model.addAttribute("libroForm", libro);
    //         return "curso/libro/editFormView";
    //     } else {
    //         return "redirect:/libro/list";
    //     }
        
    // }

    @PostMapping("/editar/submit")
    public String showEditSubmit(@Valid Libro libroForm,
            BindingResult bindingResult) {

        if (!bindingResult.hasErrors()) {
            libroService.editar(libroForm);
        }
        return "redirect:/libro/list";
       
    }

    @GetMapping("/delete/{id}")
    public String showDelete(@PathVariable long id) {
        libroService.borrar(id);
        return "redirect:/libro/list";
    }
}
