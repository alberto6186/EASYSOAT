package com.easysoat.crud.easysoatcrud.controllers;

import com.easysoat.crud.easysoatcrud.entities.Usuario;
import com.easysoat.crud.easysoatcrud.repositories.UsuarioRepository;
import com.easysoat.crud.easysoatcrud.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("usuarios")
public class UsuarioControllers {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @GetMapping({"","/"})
    public String mostrarListaUsuarios(Model model){
        List<Usuario> usuarios =(List<Usuario>) usuarioRepository.findAll();
        model.addAttribute("usuarios", usuarios);
       return "usuarios/lista-usuarios";
    }
}
