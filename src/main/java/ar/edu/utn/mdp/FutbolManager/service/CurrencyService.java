package ar.edu.utn.mdp.FutbolManager.service;

import ar.edu.utn.mdp.FutbolManager.model.Currency;
import ar.edu.utn.mdp.FutbolManager.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyService {

    private CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public Integer add(Currency currency) {
        return currencyRepository.save(currency).getId();
    }

    public List<Currency> get() {
        return currencyRepository.findAll();
    }

    public Currency getById(Integer id) {
        return currencyRepository.findById(id).orElse(null);
    }

    public void delete(Currency currency) {
        currencyRepository.delete(currency);
    }
}
