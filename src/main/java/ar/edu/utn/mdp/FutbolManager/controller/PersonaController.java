package ar.edu.utn.mdp.FutbolManager.controller;

import ar.edu.utn.mdp.FutbolManager.model.Jugador;
import ar.edu.utn.mdp.FutbolManager.model.Persona;
import ar.edu.utn.mdp.FutbolManager.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persona")
public class PersonaController {

    private PersonaService personaService;

    @Autowired
    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @PostMapping
    public Integer add(@RequestBody Persona persona) {
        return personaService.add(persona);
    }

    @GetMapping
    public List<Persona> get() {
        return personaService.get();
    }

    @GetMapping("/{id}")
    public Persona getById(@PathVariable("id") Integer id) {
        return personaService.getById(id);
    }

    @PutMapping("/{id}/jugadores/{idJugador}")
    public Integer putJugador(@PathVariable("id") Integer id, @PathVariable("idJugador") Integer idJugador) {
        return personaService.putJugador(id, idJugador);
    }
}
