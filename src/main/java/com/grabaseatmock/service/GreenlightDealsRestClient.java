package com.grabaseatmock.service;

import com.grabaseatmock.constants.GrabaseatAppConstants;
import com.grabaseatmock.dto.GreenlightDeals;
import org.springframework.web.reactive.function.client.WebClient;

public class GreenlightDealsRestClient {
    private WebClient webClient;

    public GreenlightDealsRestClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public GreenlightDeals retrieveDeals() {
        return webClient.get().uri(GrabaseatAppConstants.GET_GREENLIGHT_DEALS_V3)
                .retrieve()
                .bodyToMono(GreenlightDeals.class)
                .block();
    }
}
