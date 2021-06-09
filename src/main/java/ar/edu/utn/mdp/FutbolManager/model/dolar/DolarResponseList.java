package ar.edu.utn.mdp.FutbolManager.model.dolar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DolarResponseList {
    private List<DolarResponse> dolarList;
}
