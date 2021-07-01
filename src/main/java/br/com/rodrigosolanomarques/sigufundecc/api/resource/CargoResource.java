package br.com.rodrigosolanomarques.sigufundecc.api.resource;

import br.com.rodrigosolanomarques.sigufundecc.api.model.Cargo;
import br.com.rodrigosolanomarques.sigufundecc.api.repository.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cargos")
public class CargoResource {

    @Autowired
    private CargoRepository cargoRepository;

    @GetMapping
    public List<Cargo> listar() {
        return cargoRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Cargo> criar(@Valid @RequestBody Cargo cargo, HttpServletResponse response) {

        Cargo cargoCriado = cargoRepository.save(cargo);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(cargoCriado.getId())
                .toUri();

        return ResponseEntity.created(uri).body(cargoCriado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cargo> buscarPeloId(@PathVariable Long id) {
        return cargoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
