package com.example.reactiveweb.service;

import com.example.reactiveweb.domain.Item;
import com.example.reactiveweb.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.core.ReactiveFluentMongoOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import static org.springframework.data.mongodb.core.query.Criteria.byExample;
import static org.springframework.data.mongodb.core.query.Query.query;

@RequiredArgsConstructor
@Service
public class InventoryService {
    private final ItemRepository itemRepository;
    private final ReactiveFluentMongoOperations reactiveFluentMongoOperations;

    // Example 쿼리를 사용해서 여러 조건을 조립해서 스프링 데이터에 전달하면, 필요한 쿼리문을 만들어줄 것
    public Flux<Item> searchByExample(String name, String description, boolean useAnd){
        Item item = new Item(name, description, 0.0);

        ExampleMatcher matcher = (
                useAnd ? // 유저가 선택한 useAnd 값에 따라 분기
                ExampleMatcher.matchingAll() :
                ExampleMatcher.matchingAny()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) // 부분 일치 검색 수행
                        .withIgnoreCase() // 대-소문자 구분 X
                        .withIgnorePaths("price") // null 필드는 기본적으로 무시하지만, double 타입은 null일 수 없으므로 무시 옵션
        );

        Example<Item> probe = Example.of(item, matcher); // Example 객체를 생성함

        return itemRepository.findAll(probe);
    }

    // Reactive Fluent Data API 를 이용해 위의 검색 쿼리를 구현함
    Flux<Item> searchByFluentExample(String name, String description, boolean useAnd){
        Item item = new Item(name, description, 0.0);

        ExampleMatcher matcher = (
                useAnd ? // 유저가 선택한 useAnd 값에 따라 분기
                        ExampleMatcher.matchingAll() :
                        ExampleMatcher.matchingAny()
                                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) // 부분 일치 검색 수행
                                .withIgnoreCase() // 대-소문자 구분 X
                                .withIgnorePaths("price") // null 필드는 기본적으로 무시하지만, double 타입은 null일 수 없으므로 무시 옵션
        );

        return reactiveFluentMongoOperations.query(Item.class)
                .matching(query(byExample(Example.of(item, matcher))))
                .all();
    }
}
