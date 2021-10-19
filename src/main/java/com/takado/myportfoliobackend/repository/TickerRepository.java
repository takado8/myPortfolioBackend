package com.takado.myportfoliobackend.repository;

import com.takado.myportfoliobackend.domain.Ticker;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TickerRepository extends CrudRepository<Ticker, Long> {
    List<Ticker> findAll();
}
