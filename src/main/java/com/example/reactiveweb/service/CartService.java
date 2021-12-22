package com.example.reactiveweb.service;

import com.example.reactiveweb.domain.Cart;
import com.example.reactiveweb.domain.CartItem;
import com.example.reactiveweb.repository.CartRepository;
import com.example.reactiveweb.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class CartService {
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;

    public Mono<Cart> addToCart(String cartId, String id) { // <3>
        return this.cartRepository.findById(cartId) //
                .log("Found cart")
                .defaultIfEmpty(new Cart(cartId)) //
                .log("Empty cart")
                .flatMap(cart -> cart.getCartItems().stream() //
                        .filter(cartItem -> cartItem.getItem() //
                                .getId().equals(id)) //
                        .findAny() //
                        .map(cartItem -> {
                            cartItem.increment();
                            return Mono.just(cart);
                        }) //
                        .orElseGet(() -> //
                                this.itemRepository.findById(id) //
                                        .map(CartItem::new) // <4>
                                        .doOnNext(cartItem -> //
                                                cart.getCartItems().add(cartItem)) //
                                        .map(cartItem -> cart)))
                .flatMap(this.cartRepository::save); // <5>
    }
}
