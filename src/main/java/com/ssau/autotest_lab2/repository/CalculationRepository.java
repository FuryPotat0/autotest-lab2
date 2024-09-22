package com.ssau.autotest_lab2.repository;

import com.ssau.autotest_lab2.entity.Calculation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalculationRepository extends JpaRepository<Calculation, Integer> {
}
