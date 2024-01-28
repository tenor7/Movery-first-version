package com.example.mediatec.Models;

import com.rengwuxian.materialedittext.MaterialEditText;

public class Film {
    private String name, type, year, genre, time, country, description;

    public Film(String name, String type, String year, String genre, String time, String country, String description) {
        this.name = name;
        this.type = type;
        this.year = year;
        this.genre = genre;
        this.time = time;
        this.country = country;
        this.description = description;
    }

    public Film(MaterialEditText name, MaterialEditText type, MaterialEditText year, MaterialEditText genre, MaterialEditText time, MaterialEditText country, MaterialEditText description) {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
