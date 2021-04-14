package ar.edu.utn.mdp.FutbolManager.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FutbolManagerError {
    private List<String> errors;
}
