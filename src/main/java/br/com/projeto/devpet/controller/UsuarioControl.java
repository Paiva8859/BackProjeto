package br.com.projeto.devpet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsuarioControl {
    
    @GetMapping("/login")
    public String login(){
        return "usuario/login";
    }

}
