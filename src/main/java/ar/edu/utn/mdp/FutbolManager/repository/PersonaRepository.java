package ar.edu.utn.mdp.FutbolManager.repository;

import ar.edu.utn.mdp.FutbolManager.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {
    @Modifying
    @Query("update Persona set representante_id = ?2 where id = ?1")
    @Transactional
    Integer putJugador(Integer id, Integer typeId);
}
