package com.takado.myportfoliobackend.repository;

import com.takado.myportfoliobackend.domain.Trade;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TradeRepository extends CrudRepository<Trade, Long> {
    List<Trade> findAll();
    List<Trade> findAllByUserIdAndTickerId(Long userId, Long tickerId);
}
