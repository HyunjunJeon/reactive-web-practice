package com.example.reactiveweb.service;

import com.example.reactiveweb.domain.Cart;
import com.example.reactiveweb.domain.CartItem;
import com.example.reactiveweb.domain.Item;
import com.example.reactiveweb.repository.CartRepository;
import com.example.reactiveweb.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class) // 테스트 핸들러를 지정해줌, 스프링에 특화된 테스트 기능을 이용할 수 있음
public class CartServiceUnitTest {
    CartService cartService; // 테스트 대상 클래스(Class Under Test)

    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private CartRepository cartRepository;

    @BeforeEach
    void setUp(){
        // 테스트 데이터 정의
        Item sampleItem = new Item("item1", "TV tray", 19.99);
        CartItem sampleCartItem = new CartItem(sampleItem);
        Cart sampleCart = new Cart("My Cart", Collections.singletonList(sampleCartItem));

        // 상호작용 정의
        when(cartRepository.findById(anyString())).thenReturn(Mono.empty());
        when(itemRepository.findById(anyString())).thenReturn(Mono.just(sampleItem));
        when(cartRepository.save(any(Cart.class))).thenReturn(Mono.just(sampleCart));

        cartService = new CartService(itemRepository, cartRepository);
    }

    @Test
    void addItemToEmptyCartShouldProduceOneCartItem(){
        cartService
                .addItemToCart("My Cart", "item1")
                .as(StepVerifier::create)
                .expectNextMatches(cart -> {
                    assertThat(cart.getCartItems()).extracting(CartItem::getQuantity).containsExactlyInAnyOrder();
                    assertThat(cart.getCartItems()).extracting(CartItem::getItem).containsExactly(new Item("item1", "TV tray", 19.99));

                    return true;
                })
                .verifyComplete();
    }

}
