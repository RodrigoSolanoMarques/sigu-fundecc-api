package br.com.rodrigosolanomarques.sigufundecc.api.service;

import br.com.rodrigosolanomarques.sigufundecc.api.model.Cargo;
import br.com.rodrigosolanomarques.sigufundecc.api.repository.CargoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CargoService {

    @Autowired
    private CargoRepository cargoRepository;

    public Cargo atualziar(Long id, Cargo cargo) {
        Cargo cargoSalvo = cargoRepository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException(1));

        BeanUtils.copyProperties(cargo, cargoSalvo, "id");
        return cargoRepository.save(cargoSalvo);
    }
}
