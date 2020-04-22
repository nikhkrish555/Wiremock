package grabaseat.wiremock.service;

import com.github.jenspiegsa.wiremockextension.ConfigureWireMock;
import com.github.jenspiegsa.wiremockextension.InjectServer;
import com.github.jenspiegsa.wiremockextension.WireMockExtension;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.core.Options;
import com.grabaseatmock.dto.GreenlightDeals;
import com.grabaseatmock.service.GreenlightDealsRestClient;
import com.learnwiremock.service.MoviesRestClient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@ExtendWith(WireMockExtension.class)
public class GreenlightDealsRestClientTest {

    GreenlightDealsRestClient greenlightDealsRestClient;
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
//      String baseUrl = String.format("https://www.grabaseat.co.nz");
        String baseUrl = String.format("http://localhost:%s", port);
        webClient = WebClient.create(baseUrl);
        greenlightDealsRestClient = new GreenlightDealsRestClient(webClient);
    }

    @Test
    void retrieveGreenlightDeals() {
        stubFor(get(urlMatching("/api/v3/greenlight-deals"))
                .willReturn(WireMock.aResponse()
//                       .proxiedFrom("https://www.grabaseat.co.nz")
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("allDeals.json")));
        GreenlightDeals greenlightDeals = greenlightDealsRestClient.retrieveDeals();
        System.out.println(greenlightDeals);
        System.out.println(greenlightDeals.getDomesticDealsList().get(0).getBookUrl());
    }

    @AfterEach
    void tearDown() {
        wireMockServer.shutdown();
    }

}