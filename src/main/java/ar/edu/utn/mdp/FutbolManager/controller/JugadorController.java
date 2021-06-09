package ar.edu.utn.mdp.FutbolManager.controller;

import ar.edu.utn.mdp.FutbolManager.model.Jugador;
import ar.edu.utn.mdp.FutbolManager.service.JugadorService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/jugador")
public class JugadorController {

    private final JugadorService jugadorService;

    @Autowired
    public JugadorController(JugadorService jugadorService) {
        this.jugadorService = jugadorService;
    }

    @CircuitBreaker(name = "conversion", fallbackMethod = "conversionFallback")
    @GetMapping("/conversion")
    public Page<Jugador> getConversion(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "50") Integer size
    )  throws IOException, InterruptedException {
        return jugadorService.getConversions(PageRequest.of(page, size));
    }

    // Si se rompe alguna de las api se devuelve un valor estático
    public Page<Jugador> conversionFallback(final Throwable t) {
        return jugadorService.getStaticConversions(PageRequest.of(0, 50));
    }
}
