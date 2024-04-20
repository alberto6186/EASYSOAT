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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
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
        //Grabar la imagen
        MultipartFile img = usuarioDto.getNombreImagen();
        Date fechaCreacion = new Date(System.currentTimeMillis());
        String storageFileName = fechaCreacion.getTime() +  " - " + img.getOriginalFilename();
        try{
            String unloadDir = "public/imagenes/";
            Path unloadPath = Paths.get(unloadDir);
            if(!Files.exists(unloadPath)){
                Files.createDirectories(unloadPath);
            }
            try(InputStream inputStream = img.getInputStream()){
                Files.copy(inputStream, Paths.get(unloadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);

            }


        }catch(Exception e){
            System.out.println("Exception"+e.getMessage());

        }


        //Registrar en la base de datos
        Usuario usuario = new Usuario();
        usuario.setNombres(usuarioDto.getNombres());
        usuario.setApellidos(usuarioDto.getApellidos());
        usuario.setTipoDocumento(usuarioDto.getTipoDocumento());
        usuario.setNumeroDocumento(usuarioDto.getNumeroDocumento());
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setPassword(usuarioDto.getPassword());
        usuario.setNombreImagen(storageFileName);
        usuario.setFechaCreacion((java.sql.Date)fechaCreacion);

        usuarioRepository.save(usuario);

        return "redirect:/vistas";
    }
    @GetMapping("/editar")
    public String swohUpdatePages(Model model, @RequestParam int id){
        try{
            Usuario usuario = usuarioRepository.findById(id).orElseThrow();
            model.addAttribute("usuario", usuario);
            UsuarioDto usuarioDto = new UsuarioDto();
            usuarioDto.setNombres(usuario.getNombres());
            usuarioDto.setApellidos(usuario.getApellidos());
            usuarioDto.setTipoDocumento(usuario.getTipoDocumento());
            usuarioDto.setNumeroDocumento(usuario.getNumeroDocumento());
            usuarioDto.setEmail(usuario.getEmail());
            usuarioDto.setPassword(usuario.getPassword());

            model.addAttribute("usuarioDto", usuarioDto);


        }catch(Exception e){
            System.out.println("Exception al editar"+e.getMessage());
        }
        return "/vistas/editar";
    }

    @PostMapping("/editar")
    public String updateUsuario(Model model, @RequestParam int id, @Valid@ ModelAttribute UsuarioDto usuarioDto, BindingResult resultado){
        try{
            Usuario usuario = usuarioRepository.findById(id).orElseThrow();
            model.addAttribute("usuario", usuario);
            if(resultado.hasErrors()){
                return "/vistas/editar";
            }
            if(!usuarioDto.getNombreImagen().isEmpty()){
                //Eliminamos la imagen antigua
                String dirImagen = "public/imagenes/";
                Path rutaAntiguaImagen = Paths.get(dirImagen + usuario.getNombreImagen());
                try {
                    Files.delete(rutaAntiguaImagen);

                }catch (Exception e){
                    System.out.println("Error al eliminar la imagen"+e.getMessage());
                }
                //Grabar el archivo de la nueva imagen
                MultipartFile imagen = usuarioDto.getNombreImagen();
                Date fechaCreacion = new Date(System.currentTimeMillis());
                String stringFileName = fechaCreacion.getTime() + imagen.getOriginalFilename();
                try(InputStream inputStream = imagen.getInputStream()){
                    Files.copy(inputStream, Paths.get(dirImagen + stringFileName), StandardCopyOption.REPLACE_EXISTING);
                }
                usuario.setNombreImagen(stringFileName);

            }
            usuario.setNombres(usuarioDto.getNombres());
            usuario.setApellidos(usuarioDto.getApellidos());
            usuario.setTipoDocumento(usuarioDto.getTipoDocumento());
            usuario.setNumeroDocumento(usuarioDto.getNumeroDocumento());
            usuario.setEmail(usuarioDto.getEmail());
            usuario.setPassword(usuarioDto.getPassword());


            usuarioRepository.save(usuario);


        }catch(Exception ex){
            System.out.println("Exception de editar" +ex.getMessage());
        }
        return "redirect:/vistas";
    }
    @GetMapping("/eliminar")
    public String deleteUsuario(@RequestParam int id){
        try{
            Usuario usuario = usuarioRepository.findById(id).orElseThrow();
            //Eliminamos al usuario a la base de datos
            Path rutaImagen = Paths.get("public/imagenes/" +usuario.getNombreImagen());
            try{
                Files.delete(rutaImagen);
            }catch (Exception e){
                System.out.println("Exception al eliminar la imagen"+e.getMessage());
            }
            usuarioRepository.delete(usuario);
        }catch (Exception e){
            System.out.println("Exception de eliminar"+e.getMessage());
        }
        return "redirect:/vistas";
    }
}
