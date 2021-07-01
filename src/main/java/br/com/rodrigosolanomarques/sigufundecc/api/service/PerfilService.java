package br.com.rodrigosolanomarques.sigufundecc.api.service;

import br.com.rodrigosolanomarques.sigufundecc.api.model.Perfil;
import br.com.rodrigosolanomarques.sigufundecc.api.repository.PerfilRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class PerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    public Perfil atualizar(Long id, Perfil perfil) {
        Perfil perfilSalvo = perfilRepository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException(1));

        BeanUtils.copyProperties(perfil, perfilSalvo, "id");
        return perfilRepository.save(perfilSalvo);
    }
}
