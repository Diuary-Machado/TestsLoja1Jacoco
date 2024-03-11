package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entity.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long> {

}
