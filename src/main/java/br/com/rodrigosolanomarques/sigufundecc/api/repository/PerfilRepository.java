package br.com.rodrigosolanomarques.sigufundecc.api.repository;

import br.com.rodrigosolanomarques.sigufundecc.api.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {

    Optional<Perfil> findByNome(String nome);

    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM usuario_perfil WHERE id_perfil = :id", nativeQuery = true)
    boolean isPerfilInUse(@Param("id") Long id);


}
