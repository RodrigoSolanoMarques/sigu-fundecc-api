package br.com.rodrigosolanomarques.sigufundecc.api.service;

import br.com.rodrigosolanomarques.sigufundecc.api.model.Perfil;
import br.com.rodrigosolanomarques.sigufundecc.api.repository.PerfilRepository;
import br.com.rodrigosolanomarques.sigufundecc.api.service.exception.RegistroJaCadastradoException;
import br.com.rodrigosolanomarques.sigufundecc.api.service.exception.RegistroSendoUsadoException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    public Perfil savar(Perfil perfil) {

        Optional<Perfil> perfilSalvo = perfilRepository.findByNome(perfil.getNome());

        if (perfilSalvo.isPresent()) {
            throw new RegistroJaCadastradoException();
        }

        return perfilRepository.save(perfil);
    }

    public Perfil atualizar(Long id, Perfil perfil) {
        Perfil perfilSalvo = perfilRepository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException(1));

        BeanUtils.copyProperties(perfil, perfilSalvo, "id");
        return perfilRepository.save(perfilSalvo);
    }

    public void remover(Long id) {
        perfilRepository.deleteById(id);
    }
}
