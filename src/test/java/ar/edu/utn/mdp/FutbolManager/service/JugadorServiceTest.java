package ar.edu.utn.mdp.FutbolManager.service;

import ar.edu.utn.mdp.FutbolManager.model.Currency;
import ar.edu.utn.mdp.FutbolManager.model.Jugador;
import ar.edu.utn.mdp.FutbolManager.model.euro.EuroResponseList;
import ar.edu.utn.mdp.FutbolManager.repository.JugadorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
public class JugadorServiceTest {

    private JugadorRepository jugadorRepositoryMock;
    private HttpClient httpClientMock;
    private JugadorService jugadorService;

    @BeforeAll
    public void setUp() {
        jugadorRepositoryMock = mock(JugadorRepository.class);
        httpClientMock = mock(HttpClient.class);

        jugadorService = new JugadorService(jugadorRepositoryMock);
    }

    @Test
    public void getConversions_Ok() {
        // Arrange
        Page<Jugador> result = null;
        Pageable pageable = PageRequest.of(0, 50);
        Page<Jugador> jugadorPage = getJugadorPage();
        Double expectedEurValue = jugadorPage.getContent().get(0).getCurrency().getMonto() * JugadorService.FIXED_EURO_VALUE;
        Double expectedUsdValue = jugadorPage.getContent().get(1).getCurrency().getMonto() * JugadorService.FIXED_DOLAR_VALUE;
        Mockito.when(jugadorRepositoryMock.findAll(pageable)).thenReturn(jugadorPage);
        Mockito.when(httpClientMock.send(Mockito.any(HttpRequest.class), Mockito.any(HttpResponse.BodyHandler.class))).thenReturn(new HttpResponseImpl<EuroResponseList>());

        try (MockedStatic<HttpClient> entityURLBuilderStaticMock = Mockito.mockStatic(HttpClient.class)) {
            entityURLBuilderStaticMock.when(HttpClient::newHttpClient).thenReturn(httpClientMock);

            // Act
            try {
                result = jugadorService.getConversions(pageable);
            } catch (Exception e) {
                Assertions.fail(e.getMessage());
            }

            // Assert
            Assertions.assertNotNull(result);
            Assertions.assertEquals(expectedEurValue, result.getContent().get(0).getCurrency().getMonto());
            Assertions.assertEquals(expectedUsdValue, result.getContent().get(1).getCurrency().getMonto());
        }
    }

    @Test
    public void getConversions_ThrowsException() {
        // Arrange
        Page<Jugador> result = null;
        Pageable pageable = PageRequest.of(0, 50);
        Page<Jugador> jugadorPage = getJugadorPage();
        Double expectedEurValue = jugadorPage.getContent().get(0).getCurrency().getMonto() * JugadorService.FIXED_EURO_VALUE;
        Double expectedUsdValue = jugadorPage.getContent().get(1).getCurrency().getMonto() * JugadorService.FIXED_DOLAR_VALUE;
        Mockito.when(jugadorRepositoryMock.findAll(pageable)).thenReturn(jugadorPage);
        Mockito.when(httpClientMock.send(Mockito.any(HttpRequest.class), Mockito.any(HttpResponse.BodyHandler.class))).thenThrow(IOException.class);

        try (MockedStatic<HttpClient> entityURLBuilderStaticMock = Mockito.mockStatic(HttpClient.class)) {
            entityURLBuilderStaticMock.when(HttpClient::newHttpClient).thenReturn(httpClientMock);

            // Act
            Assertions.assertThrows(IOException.class, () -> {
                jugadorService.getConversions(pageable);
            });
        }
    }

    @Test
    public void getStaticConversions_Ok() {
        // Arrange
        Page<Jugador> result = null;
        Pageable pageable = PageRequest.of(0, 50);
        Page<Jugador> jugadorPage = getJugadorPage();
        Double expectedEurValue = jugadorPage.getContent().get(0).getCurrency().getMonto() * JugadorService.FIXED_EURO_VALUE;
        Double expectedUsdValue = jugadorPage.getContent().get(1).getCurrency().getMonto() * JugadorService.FIXED_DOLAR_VALUE;
        Mockito.when(jugadorRepositoryMock.findAll(pageable)).thenReturn(jugadorPage);

        // Act
        try {
            result = jugadorService.getStaticConversions(pageable);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedEurValue, result.getContent().get(0).getCurrency().getMonto());
        Assertions.assertEquals(expectedUsdValue, result.getContent().get(1).getCurrency().getMonto());
    }

    public Page<Jugador> getJugadorPage() {
        List<Jugador> list = new ArrayList<>();
        list.add(new Jugador(1d, 1d, 1, 1, new Currency(1, "EUR", 1d), Date.from(Instant.now())));
        list.add(new Jugador(1d, 1d, 1, 1, new Currency(1, "USD", 1d), Date.from(Instant.now())));
       return new PageImpl<>(list);
    }
}
