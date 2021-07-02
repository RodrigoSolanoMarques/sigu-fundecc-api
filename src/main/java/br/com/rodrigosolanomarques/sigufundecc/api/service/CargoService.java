package br.com.rodrigosolanomarques.sigufundecc.api.service;

import br.com.rodrigosolanomarques.sigufundecc.api.model.Cargo;
import br.com.rodrigosolanomarques.sigufundecc.api.repository.CargoRepository;
import br.com.rodrigosolanomarques.sigufundecc.api.service.exception.RegistroJaCadastradoException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CargoService {

    @Autowired
    private CargoRepository cargoRepository;

    public Cargo savar(Cargo cargo) {

        Optional<Cargo> cargoSalvo = cargoRepository.findByNome(cargo.getNome());

        if (cargoSalvo.isPresent()) {
            throw new RegistroJaCadastradoException();
        }

        return cargoRepository.save(cargo);
    }

    public Cargo atualizar(Long id, Cargo cargo) {
        Cargo cargoSalvo = cargoRepository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException(1));

        BeanUtils.copyProperties(cargo, cargoSalvo, "id");
        return cargoRepository.save(cargoSalvo);
    }
}
