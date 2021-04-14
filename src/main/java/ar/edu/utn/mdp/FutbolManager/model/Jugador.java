package ar.edu.utn.mdp.FutbolManager.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.Date;

@Data
@NoArgsConstructor
public class Jugador extends Persona {
    private Double peso;
    private Double altura;
    private Integer goles;
    private Integer minutosJugados;
    private Currency currency;
    private Date fechaDeNacimiento;
}
