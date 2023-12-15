package br.com.projeto.devpet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.projeto.devpet.entidades.Funcionario;
import br.com.projeto.devpet.enums.UF;
import br.com.projeto.devpet.repositorios.CargoRepositorio;
import br.com.projeto.devpet.repositorios.FuncionarioRepositorio;
import br.com.projeto.devpet.utils.SenhaUtils;

@Controller
@RequestMapping("/funcionarios")
public class FuncionarioControl {

    @Autowired
    private FuncionarioRepositorio funcionarioRepositorio;

    @Autowired
    private CargoRepositorio cargoRepositorio;

    @GetMapping
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("funcionario/home");

        modelAndView.addObject("funcionarios", funcionarioRepositorio.findAll());

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView detalhes(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("funcionario/detalhes");

        modelAndView.addObject("funcionario", funcionarioRepositorio.getOne(id));

        return modelAndView;
    }

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {
        ModelAndView modelAndView = new ModelAndView("funcionario/formulario");

        modelAndView.addObject("funcionario", new Funcionario());
        modelAndView.addObject("cargos", cargoRepositorio.findAll());
        modelAndView.addObject("ufs", UF.values());

        return modelAndView;
    }

    @GetMapping("/{id}/editar")
    public ModelAndView editar(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("funcionario/formulario");

        modelAndView.addObject("funcionario", funcionarioRepositorio.getOne(id));
        modelAndView.addObject("cargos", cargoRepositorio.findAll());
        modelAndView.addObject("ufs", UF.values());

        return modelAndView;
    }

    @PostMapping({"/cadastrar"})
    public String cadastrar(Funcionario funcionario) {
        String senhaEncriptada = SenhaUtils.encode(funcionario.getSenha());

        funcionario.setSenha(senhaEncriptada);
        funcionarioRepositorio.save(funcionario); 

        return "redirect:/funcionarios";
    }

    @PostMapping("/{id}/editar")
        public String editar(Funcionario funcionario, @PathVariable long id){
            String senhaAtual = funcionarioRepositorio.getOne(id).getSenha();
            funcionario.setSenha(senhaAtual);

            funcionarioRepositorio.save(funcionario);

            return "redirect:/funcionarios";
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        funcionarioRepositorio.deleteById(id);

        return "redirect:/funcionarios";
    }
    
}
