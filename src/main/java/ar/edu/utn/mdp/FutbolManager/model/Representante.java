package ar.edu.utn.mdp.FutbolManager.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@NoArgsConstructor
public class Representante extends Persona {
    @OneToMany
    @JoinColumn(name = "jugador_id", referencedColumnName = "id")
    List<Jugador> jugadores;
    private Double pesoDeLaBoveda;
    private Double montoTotal;
}
