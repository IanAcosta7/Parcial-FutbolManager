package ar.edu.utn.mdp.FutbolManager.repository;

import ar.edu.utn.mdp.FutbolManager.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
}
