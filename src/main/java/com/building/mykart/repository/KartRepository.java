package com.building.mykart.repository;

import com.building.mykart.model.Kart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KartRepository extends JpaRepository<Kart, Long> {
    Kart findByUserId(Long userId);
}
