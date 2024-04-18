package com.easysoat.crud.easysoatcrud.services;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


public class UsuarioDto {
    @NotEmpty(message = "El nombre es obligatorio")
    private String nombres;
    @NotEmpty(message = "El apellido es obligatorio")
    private String apellidos;
    @NotEmpty(message = "El tipo de documento es obligatorio")
    private String tipoDocumento;
    @NotEmpty(message = "El numero de documento es obligatorio")
    private String numeroDocumento;

    @Email
    @NotEmpty(message = "El email no es valido")
    private String email;
    @NotEmpty(message = "El password es obligatorio")
    private String password;

    private MultipartFile nombreImagen;



    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MultipartFile getNombreImagen() {
        return nombreImagen;
    }

    public void setNombreImagen(MultipartFile nombreImagen) {
        this.nombreImagen = nombreImagen;
    }
}
