package com.example.reactiveweb.controller;

import com.example.reactiveweb.domain.Cart;
import com.example.reactiveweb.domain.Item;
import com.example.reactiveweb.repository.CartRepository;
import com.example.reactiveweb.repository.ItemRepository;
import com.example.reactiveweb.service.CartService;
import com.example.reactiveweb.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Controller
public class HomeController {
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;
    private final CartService cartService;
    private final InventoryService inventoryService;

    @GetMapping("/")
    Mono<Rendering> home() {
        return Mono.just(Rendering.view("home.html")
                .modelAttribute("items",
                        itemRepository.findAll().doOnNext(System.out::println))
                .modelAttribute("cart",
                        cartRepository.findById("My Cart")
                                .defaultIfEmpty(new Cart("My Cart")))
                .build());
    }

    @PostMapping("/add/{id}")
    Mono<String> addToCart(@PathVariable String id){
        return cartService.addItemToCart("My Cart", id)
                .thenReturn("redirect:/");
    }

    @PostMapping()
    Mono<String> createItem(@ModelAttribute Item newItem) {
        return itemRepository.save(newItem)
                .thenReturn("redirect:/");
    }

    @DeleteMapping("/delete/{id}")
    Mono<String> deleteItem(@PathVariable String id) {
        return itemRepository.deleteById(id)
                .thenReturn("redirect:/");
    }

    @GetMapping("/search")
    Mono<Rendering> search(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Boolean useAnd
    ){
        return Mono.just(
            Rendering.view("home.html")
              .modelAttribute("results", inventoryService.searchByExample(name,description,useAnd))
              .build()
        );
    }
}
