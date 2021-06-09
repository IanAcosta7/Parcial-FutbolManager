package ar.edu.utn.mdp.FutbolManager.model.euro;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EuroResponseList {

    private List<EuroCasa> casaList;

}
