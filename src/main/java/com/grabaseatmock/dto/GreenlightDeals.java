package com.grabaseatmock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GreenlightDeals {
    public String storeFront;
    public String specialsStatus;
    public List<com.grabaseatmock.dto.domesticDeals> domesticDeals;

    public String getStoreFront() {
        return storeFront;
    }

    public String getSpecialsStatus() {
        return specialsStatus;
    }

    public List<com.grabaseatmock.dto.domesticDeals> getDomesticDealsList() {
        return domesticDeals;
    }
}
