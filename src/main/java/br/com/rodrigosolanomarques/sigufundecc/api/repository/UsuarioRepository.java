package br.com.rodrigosolanomarques.sigufundecc.api.repository;

import br.com.rodrigosolanomarques.sigufundecc.api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByCpf(String cpf);
}
