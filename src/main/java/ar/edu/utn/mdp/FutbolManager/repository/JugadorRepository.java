package ar.edu.utn.mdp.FutbolManager.repository;

import ar.edu.utn.mdp.FutbolManager.model.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Integer> {
}
