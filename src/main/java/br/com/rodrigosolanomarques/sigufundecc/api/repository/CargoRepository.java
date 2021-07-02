package br.com.rodrigosolanomarques.sigufundecc.api.repository;

import br.com.rodrigosolanomarques.sigufundecc.api.model.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CargoRepository extends JpaRepository<Cargo, Long> {

    Optional<Cargo> findByNome(String nome);
}
