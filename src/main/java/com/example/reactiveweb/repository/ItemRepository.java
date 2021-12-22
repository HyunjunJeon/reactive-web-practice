package com.example.reactiveweb.repository;

import com.example.reactiveweb.domain.Item;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ItemRepository extends ReactiveCrudRepository<Item, String>, ReactiveQueryByExampleExecutor<Item> {
    // Name Containing 때문에 사용자의 검색어가 포함된 이름으로 찾아서 보내줌
    Flux<Item> findByNameContaining(String partialName);

//    @Query("{'name': ?0, 'age': ?1}")
//    Flux<Item> findItemsForCustomerMonthlyReport(String name, int age);

//    @Query(sort = "{'age': -1}")
//    Flux<Item> findSortedStuffForWeeklyReport();
}
