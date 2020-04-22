package com.grabaseatmock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class domesticDeals {
    public String currencySymbol;
    public String destinationIataCode;
    public String destinationName;
    public String originIataCode;
    public String originName;
    public String serviceClass;
    public LocalDate displayFromDate;
    public LocalDate displayToDate;
    public Integer seatCount;
    public Double price;
    public String statusText;
    public String status;
    public String journeyType;
    public String bookUrl;
    public String promoCode;
    public List<String> regions;


    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public String getDestinationIataCode() {
        return destinationIataCode;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public String getOriginIataCode() {
        return originIataCode;
    }

    public String getOriginName() {
        return originName;
    }

    public String getServiceClass() {
        return serviceClass;
    }

    public LocalDate getDisplayFromDate() {
        return displayFromDate;
    }

    public LocalDate getDisplayToDate() {
        return displayToDate;
    }

    public Integer getSeatCount() {
        return seatCount;
    }

    public Double getPrice() {
        return price;
    }

    public String getStatusText() {
        return statusText;
    }

    public String getStatus() {
        return status;
    }

    public String getJourneyType() {
        return journeyType;
    }

    public String getBookUrl() {
        return bookUrl;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public List<String> getRegions() {
        return regions;
    }
}
