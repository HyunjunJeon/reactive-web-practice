package com.example.reactiveweb.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;

import java.time.LocalDateTime;

@Data
public class Item {
    @Id
    private String id;
    private String name;
    private String description;
    private double price;
    private String distributorRegion;
    private LocalDateTime releaseDate;
    private int availableUnits;
    private Point location;
    private boolean active;

    private Item() {}

    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public Item(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
