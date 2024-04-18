package com.easysoat.crud.easysoatcrud.controllers;

import com.easysoat.crud.easysoatcrud.entities.Usuario;
import com.easysoat.crud.easysoatcrud.repositories.UsuarioRepository;
import com.easysoat.crud.easysoatcrud.services.UsuarioDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;



@Controller
@RequestMapping("vistas")
public class UsuarioControllers {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @GetMapping({"","/"})
    public String mostrarListaUsuarios(Model model){
        List<Usuario> usuarios =(List<Usuario>) usuarioRepository.findAll();
        model.addAttribute("usuarios", usuarios);
       return "vistas/index";
    }

    @GetMapping("/crear")
    public String createPageUsuario(Model model){
        UsuarioDto usuarioDto = new UsuarioDto();
        model.addAttribute("usuarioDto", usuarioDto);
        return "vistas/crear";
    }
    @PostMapping ("/crear")
    public String createUsuario(@Valid @ModelAttribute UsuarioDto usuarioDto, BindingResult resultado){
        if(usuarioDto.getNombreImagen().isEmpty()){
            resultado.addError(new FieldError("usuarioDto", "nombreImagen", "La imagen es obligatoria"));
        }
        if(resultado.hasErrors()){
            return "vistas/crear";
        }
        Usuario usuario = new Usuario();
        usuario= usuarioRepository.save(usuario);

        return "redirect:/vistas";
    }
}
