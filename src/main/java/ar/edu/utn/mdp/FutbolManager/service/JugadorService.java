package ar.edu.utn.mdp.FutbolManager.service;

import ar.edu.utn.mdp.FutbolManager.model.dolar.DolarResponseList;
import ar.edu.utn.mdp.FutbolManager.model.Jugador;
import ar.edu.utn.mdp.FutbolManager.model.euro.EuroResponseList;
import ar.edu.utn.mdp.FutbolManager.repository.JugadorRepository;
import com.google.gson.Gson;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@Slf4j
public class JugadorService {

    public static final Float FIXED_DOLAR_VALUE = 265f;
    public static final Float FIXED_EURO_VALUE = 231f;
    private final JugadorRepository jugadorRepository;

    @Autowired
    public JugadorService(JugadorRepository jugadorRepository) {
        this.jugadorRepository = jugadorRepository;
    }

    public Page<Jugador> getConversions(Pageable pageable)  throws IOException, InterruptedException {
        Page<Jugador> jugadorPage = jugadorRepository.findAll(pageable);

        Float dolarValue = getDolarValue();
        Float euroValue = getEuroValue();

        jugadorPage = convertJugadorPage(jugadorPage, dolarValue, euroValue);

        return jugadorPage;
    }

    public Page<Jugador> getStaticConversions(Pageable pageable) {
        Page<Jugador> jugadorPage = jugadorRepository.findAll(pageable);

        Float dolarValue = FIXED_DOLAR_VALUE;
        Float euroValue = FIXED_EURO_VALUE;

        jugadorPage = convertJugadorPage(jugadorPage, dolarValue, euroValue);

        return jugadorPage;
    }

    private Page<Jugador> convertJugadorPage(Page<Jugador> jugadorPage, Float dolarValue, Float euroValue) {
        return jugadorPage.map(jugador -> {
            if (jugador.getCurrency().getCurrency().equals("EUR"))
                jugador.getCurrency().setMonto(jugador.getCurrency().getMonto() * euroValue);
            else
                jugador.getCurrency().setMonto(jugador.getCurrency().getMonto() * dolarValue);

            return jugador;
        });
    }

    private Float getEuroValue() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.dolarsi.com/api/api.php?type=genedolar&opc=ta"))
                .header("accept", "application/json")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        EuroResponseList euroResponse = new Gson().fromJson(response.body(), EuroResponseList.class);

        return Float.parseFloat(euroResponse.getCasaList().get(0).getCompra());
    }

    private Float getDolarValue() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.dolarsi.com/api/api.php?type=dolar"))
                .header("accept", "application/json")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        DolarResponseList dolarResponseList = new Gson().fromJson(response.body(), DolarResponseList.class);

        return Float.parseFloat(dolarResponseList.getDolarList().get(0).getDolar().getCompra());
    }
}
