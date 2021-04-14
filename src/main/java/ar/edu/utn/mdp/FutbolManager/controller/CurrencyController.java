package ar.edu.utn.mdp.FutbolManager.controller;

import ar.edu.utn.mdp.FutbolManager.model.Currency;
import ar.edu.utn.mdp.FutbolManager.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/currency")
public class CurrencyController {

    private CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @PostMapping
    public Integer add(@RequestBody Currency currency) {
        return currencyService.add(currency);
    }

    @GetMapping
    public List<Currency> get() {
        return currencyService.get();
    }

    @GetMapping("/{id}")
    public Currency getById(@PathVariable("id") Integer id) {
        return currencyService.getById(id);
    }

    @DeleteMapping
    public Integer delete(@RequestBody Currency currency) {
        currencyService.delete(currency);

        return currency.getId();
    }

}
