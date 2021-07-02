package br.com.rodrigosolanomarques.sigufundecc.api.resource;

import br.com.rodrigosolanomarques.sigufundecc.api.model.Cargo;
import br.com.rodrigosolanomarques.sigufundecc.api.repository.CargoRepository;
import br.com.rodrigosolanomarques.sigufundecc.api.service.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cargos")
public class CargoResource {

    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private CargoService cargoService;

    @GetMapping
    public List<Cargo> listar() {
        return cargoRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Cargo> criar(@Valid @RequestBody Cargo cargo) {

        Cargo cargoCriado = cargoService.savar(cargo);

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

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        cargoRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cargo> atualizar(@PathVariable Long id, @Valid @RequestBody Cargo cargo) {
        Cargo cargoSalvo = cargoService.atualizar(id, cargo);
        return ResponseEntity.ok(cargoRepository.save(cargoSalvo));
    }
}
