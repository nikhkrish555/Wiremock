package learn.wiremock.service;

import com.github.jenspiegsa.wiremockextension.ConfigureWireMock;
import com.github.jenspiegsa.wiremockextension.InjectServer;
import com.github.jenspiegsa.wiremockextension.WireMockExtension;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.core.Options;
import com.learnwiremock.dto.Movie;
import com.learnwiremock.exception.MovieErrorResponse;
import com.learnwiremock.service.MoviesRestClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import org.springframework.http.HttpStatus;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(WireMockExtension.class)
public class MoviesRestClientTest {
    MoviesRestClient moviesRestClient;
    WebClient webClient;

    @InjectServer
    WireMockServer wireMockServer;

    @ConfigureWireMock
    Options option = wireMockConfig()
            .port(8088)
            .notifier(new ConsoleNotifier(true));

    @BeforeEach
    void setUp() {
        int port = wireMockServer.port();
//        String baseUrl = String.format("http://localhost:8081");
        String baseUrl = String.format("http://localhost:%d/", port);
        webClient = WebClient.create(baseUrl);
        moviesRestClient = new MoviesRestClient(webClient);
    }

    @Test
    void retrieveAllMovies() {
        stubFor(get(anyUrl())
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("all-movies.json")));
        //when
        List<Movie> movieList = moviesRestClient.retrieveAllMovies();
        System.out.println(movieList);
        //then
        assertTrue(movieList.size() > 0);
    }

    @Test
    void retrieveMovieById() {
        //given
        Integer movieId = 1;

        //when
        Movie movie = moviesRestClient.retrieveMovieById(movieId);

        //then
        assertEquals("Batman Begins", movie.getName());
    }

    @Test
    void retrieveMovieByIdNotFound() {
        //given
        Integer movieId = 100;

        //when
        Assertions.assertThrows(MovieErrorResponse.class, () -> moviesRestClient.retrieveMovieById(movieId));
    }

    @Test
    void retrieveMovieByName() {
        //given
        String movieName = "Avengers";

        //when
        List<Movie> movies = moviesRestClient.retrieveMovieByName(movieName);

        //then
        String castExpected = "Robert Downey Jr, Chris Evans , Chris HemsWorth";
        assertEquals(4, movies.size());
        assertEquals(castExpected, movies.get(0).getCast());
    }

    @Test
    void retrieveMovieByNameNotFound() {
        //given
        String movieName = "ABC";

        //when
        Assertions.assertThrows(MovieErrorResponse.class, () -> moviesRestClient.retrieveMovieByName(movieName));
    }

    @Test
    void retrieveMovieByYear() {
        //given
        Integer year = 2012;

        //when
        List<Movie> movies = moviesRestClient.retrieveMovieByYear(year);

        //then
        String castExpected = "Christian Bale, Heath Ledger , Michael Caine";
        assertEquals(2, movies.size());
        assertEquals(castExpected, movies.get(0).getCast());
    }

    @Test
    void retrieveMovieByYearNotFound() {
        //given
        Integer year = 2020;

        //when
        Assertions.assertThrows(MovieErrorResponse.class, () -> moviesRestClient.retrieveMovieByYear(year));
    }
}
