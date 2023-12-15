package br.com.projeto.devpet.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.projeto.devpet.entidades.Cargo;

public interface CargoRepositorio extends JpaRepository<Cargo, Long> {
    
}
