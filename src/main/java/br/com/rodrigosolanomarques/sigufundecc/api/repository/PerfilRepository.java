package br.com.rodrigosolanomarques.sigufundecc.api.repository;

import br.com.rodrigosolanomarques.sigufundecc.api.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
}
