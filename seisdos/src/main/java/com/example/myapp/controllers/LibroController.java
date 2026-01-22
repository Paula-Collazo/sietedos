package com.example.myapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public String showNewSubmit(@Valid @ModelAttribute("libroForm") Libro libroForm,
                            BindingResult bindingResult,
                            Model model) {

    // 🔹 1️⃣ Resolver el curso REAL a partir del id
    if (libroForm.getCurso() != null && libroForm.getCurso().getId() != null) {

        Curso cursoSeleccionado =
                cursoService.obtenerPorId(libroForm.getCurso().getId());

        // 🔴 Validación de negocio @OneToOne
        if (libroService.obtenerPorCurso(cursoSeleccionado) != null) {
            bindingResult.rejectValue(
                "curso",
                "error.libro",
                "No puede haber más de un libro por curso"
            );
        }

        libroForm.setCurso(cursoSeleccionado);
    } else {
    libroForm.setCurso(null);
    }

    // 🔹 2️⃣ AHORA sí comprobamos errores
    if (bindingResult.hasErrors()) {
        model.addAttribute("listaCursos", cursoService.obtenerTodos());
        return "curso/libro/newFormView";
    }

    // 🔹 3️⃣ Guardar
    try {
    libroService.anadir(libroForm);
    } catch (IllegalStateException e) {
        bindingResult.rejectValue(
            "titulo",
            "error.libro",
            "Ese título ya existe"
        );
        model.addAttribute("listaCursos", cursoService.obtenerTodos());
        return "curso/libro/newFormView";
    }
    return "redirect:/libro/list";
}


    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable long id, Model model ) {
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
    public String showEditSubmit(
        @Valid @ModelAttribute("libroForm") Libro libroForm,
        BindingResult bindingResult,
        Model model) {

    // 🔹 Resolver curso real
    if (libroForm.getCurso() != null && libroForm.getCurso().getId() != null) {

        Curso cursoSeleccionado =
                cursoService.obtenerPorId(libroForm.getCurso().getId());

        Libro libroExistente =
                libroService.obtenerPorCurso(cursoSeleccionado);

        // 🔴 Validación OneToOne SOLO si es otro libro
        if (libroExistente != null
                && libroExistente.getId() != libroForm.getId()) {

            bindingResult.rejectValue(
                "curso",
                "error.libro",
                "Este curso ya tiene otro libro asignado"
            );
        }

        libroForm.setCurso(cursoSeleccionado);
    }else {
    libroForm.setCurso(null);
    }

    if (bindingResult.hasErrors()) {
        model.addAttribute("listaCursos", cursoService.obtenerTodos());
        return "curso/libro/editFormView";
    }

    try {
        libroService.editar(libroForm);
    } catch (IllegalStateException e) {
        bindingResult.rejectValue(
            "titulo",
            "error.libro",
            "Ese título ya existe"
        );
        model.addAttribute("listaCursos", cursoService.obtenerTodos());
        return "curso/libro/editFormView";
    }

    return "redirect:/libro/list";
}


    @GetMapping("/delete/{id}")
    public String showDelete(@PathVariable long id, Model model) {
        libroService.borrar(id);
        return "redirect:/libro/list";
       
    }
}
