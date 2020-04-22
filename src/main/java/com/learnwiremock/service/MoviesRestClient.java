package com.learnwiremock.service;

import com.learnwiremock.constants.MoviesAppConstants;
import com.learnwiremock.dto.Movie;
import com.learnwiremock.exception.MovieErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Slf4j
public class MoviesRestClient {
    private WebClient webClient;

    public MoviesRestClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<Movie> retrieveAllMovies() {
        //http://localhost:8081/movieservice/v1/allMovies
        return webClient.get().uri(MoviesAppConstants.GET_ALL_MOVIES_V1)
                .retrieve()
                .bodyToFlux(Movie.class)
                .collectList()
                .block();
    }

    public Movie retrieveMovieById(Integer movieId) {
        //http://localhost:8081/movieservice/v1/movie/10
        try {
            return webClient.get().uri(MoviesAppConstants.RETRIEVE_MOVIE_BY_ID, movieId)
                    .retrieve()
                    .bodyToMono(Movie.class)
                    .block();
        } catch (WebClientResponseException ex) {
            log.error("WebClientResponseException in retrieveMovieById. Status code is {} and the message is {}", ex.getRawStatusCode(), ex.getResponseBodyAsString());
            throw new MovieErrorResponse(ex.getStatusText(), ex);
        } catch (Exception ex) {
            log.error("Exception in retrieveMovieById and the message is {}", ex);
            throw new MovieErrorResponse(ex);
        }
    }

    public List<Movie> retrieveMovieByName(String name) {
        //http://localhost:8081/movieservice/v1/movieName?movie_name=Avengers
        String retrieveByNameUri = UriComponentsBuilder.fromUriString(MoviesAppConstants.RETRIEVE_MOVIE_BY_NAME)
                .queryParam("movie_name", name)
                .buildAndExpand()
                .toUriString();
        try {
            return webClient.get().uri(retrieveByNameUri)
                    .retrieve()
                    .bodyToFlux(Movie.class)
                    .collectList()
                    .block();
        } catch (WebClientResponseException ex) {
            log.error("WebClientResponseException in retrieveMovieByName. Status code is {} and the message is {}", ex.getRawStatusCode(), ex.getResponseBodyAsString());
            throw new MovieErrorResponse(ex.getStatusText(), ex);
        } catch (Exception ex) {
            log.error("Exception in retrieveMovieById and the message is {}", ex);
            throw new MovieErrorResponse(ex);
        }
    }

    public List<Movie> retrieveMovieByYear(Integer year) {
        // http://localhost:8081/movieservice/v1/movieYear?year=2012
        String retrieveByNameUri = UriComponentsBuilder.fromUriString(MoviesAppConstants.RETRIEVE_MOVIE_BY_YEAR)
                .queryParam("year", year)
                .buildAndExpand()
                .toUriString();
        try {
            return webClient.get().uri(retrieveByNameUri)
                    .retrieve()
                    .bodyToFlux(Movie.class)
                    .collectList()
                    .block();
        } catch (WebClientResponseException ex) {
            log.error("WebClientResponseException in retrieveMovieByYear. Status code is {} and the message is {}", ex.getRawStatusCode(), ex.getResponseBodyAsString());
            throw new MovieErrorResponse(ex.getStatusText(), ex);
        } catch (Exception ex) {
            log.error("Exception in retrieveMovieById and the message is {}", ex);
            throw new MovieErrorResponse(ex);
        }
    }
}