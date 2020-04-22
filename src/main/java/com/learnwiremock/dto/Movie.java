package com.learnwiremock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    public String cast;
    public long movie_id;
    public String name;
    public LocalDate release_date;
    public Integer year;

    public String getCast() {
        return cast;
    }

    public long getMovie_id() {
        return movie_id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getRelease_date() {
        return release_date;
    }

    public Integer getYear() {
        return year;
    }
}
