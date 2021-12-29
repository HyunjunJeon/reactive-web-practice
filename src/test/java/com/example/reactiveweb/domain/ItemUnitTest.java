package com.example.reactiveweb.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ItemUnitTest {
    @Test
    void itemBasicShouldWork(){
        Item sampleItem = new Item("item1", "TV tray", 19.99);

        assertThat(sampleItem.getId()).isEqualTo("item1");
        assertThat(sampleItem.getName()).isEqualTo("item1");
        assertThat(sampleItem.getDescription()).isEqualTo("TV tray");
        assertThat(sampleItem.getPrice()).isEqualTo(19.99);

        Item sampleItem2 = new Item("item2","Smurf TV tray", 24.99);

        assertThat(sampleItem).isEqualTo(sampleItem2);
    }
}
