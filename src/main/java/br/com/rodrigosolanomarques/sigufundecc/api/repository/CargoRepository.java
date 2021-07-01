package br.com.rodrigosolanomarques.sigufundecc.api.repository;

import br.com.rodrigosolanomarques.sigufundecc.api.model.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CargoRepository extends JpaRepository<Cargo, Long> {
}
