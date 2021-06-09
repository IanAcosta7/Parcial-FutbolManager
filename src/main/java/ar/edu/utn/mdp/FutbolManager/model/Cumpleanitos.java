package ar.edu.utn.mdp.FutbolManager.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Cumpleanitos {
    @Id
    Integer id;
    LocalDate fecha;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "persona_id", referencedColumnName = "id")
    Persona cumpleaniero;
    @ManyToMany(fetch = FetchType.EAGER)
    Set<Persona> invitados;
}
