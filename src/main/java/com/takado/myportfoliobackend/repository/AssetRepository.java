package com.takado.myportfoliobackend.repository;

import com.takado.myportfoliobackend.domain.Asset;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AssetRepository extends CrudRepository<Asset, Long> {
    List<Asset> findAll();

    List<Asset> findAllByUserId(Long userId);
}
