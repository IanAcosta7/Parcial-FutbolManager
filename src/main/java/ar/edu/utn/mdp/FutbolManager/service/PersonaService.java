package ar.edu.utn.mdp.FutbolManager.service;

import ar.edu.utn.mdp.FutbolManager.model.Persona;
import ar.edu.utn.mdp.FutbolManager.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonaService {

    private PersonaRepository personaRepository;

    @Autowired
    public PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    public Integer add(Persona persona) {
        return personaRepository.save(persona).getId();
    }

    public List<Persona> get() {
        return personaRepository.findAll();
    }

    public Persona getById(Integer id) {
        return personaRepository.findById(id).orElse(null);
    }

    public Integer putJugador(Integer id, Integer idJugador) {
        return personaRepository.putJugador(id, idJugador);
    }
}
