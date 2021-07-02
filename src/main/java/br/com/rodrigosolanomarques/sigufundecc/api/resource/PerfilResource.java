package br.com.rodrigosolanomarques.sigufundecc.api.resource;

import br.com.rodrigosolanomarques.sigufundecc.api.model.Perfil;
import br.com.rodrigosolanomarques.sigufundecc.api.repository.PerfilRepository;
import br.com.rodrigosolanomarques.sigufundecc.api.service.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/perfis")
public class PerfilResource {

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private PerfilService perfilService;

    @GetMapping
    public List<Perfil> listar() {
        return perfilRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Perfil> criar(@Valid @RequestBody Perfil perfil) {

        Perfil perfilCriado = perfilService.savar(perfil);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(perfilCriado.getId())
                .toUri();

        return ResponseEntity.created(uri).body(perfilCriado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Perfil> buscarPeloId(@PathVariable Long id) {
        return perfilRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        perfilService.remover(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Perfil> atualizar(@PathVariable Long id, @Valid @RequestBody Perfil perfil) {
        Perfil perfilSalvo = perfilService.atualizar(id, perfil);
        return ResponseEntity.ok(perfilRepository.save(perfilSalvo));
    }
}
