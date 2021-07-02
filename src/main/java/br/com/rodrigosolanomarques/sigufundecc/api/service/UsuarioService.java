package br.com.rodrigosolanomarques.sigufundecc.api.service;

import br.com.rodrigosolanomarques.sigufundecc.api.model.Usuario;
import br.com.rodrigosolanomarques.sigufundecc.api.repository.CargoRepository;
import br.com.rodrigosolanomarques.sigufundecc.api.repository.UsuarioRepository;
import br.com.rodrigosolanomarques.sigufundecc.api.service.exception.CpfJaCadastradoException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CargoRepository cargoRepository;

    public Usuario salvar(Usuario usuario) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findByCpf(usuario.getCpf());

        if (usuarioExistente.isPresent()) {
            throw new CpfJaCadastradoException("CPF já cadastrado");
        }
        Usuario usuarioCriado = usuarioRepository.save(usuario);

        return usuarioRepository.findById(usuarioCriado.getId()).get();
    }

    public Usuario atualizar(Long id, Usuario usuario) {
        Usuario usuarioSalvo = usuarioRepository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException(1));

        BeanUtils.copyProperties(usuario, usuarioSalvo, "id");
        return usuarioRepository.save(usuarioSalvo);
    }
}
