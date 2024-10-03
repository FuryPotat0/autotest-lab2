package com.ssau.autotest_lab2.repository;

import com.ssau.autotest_lab2.entity.Calculation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CalculationRepository extends JpaRepository<Calculation, Integer> {
    @Query(nativeQuery = true, value = """
        SELECT c.*
        FROM lab2.calculation AS c 
        WHERE c.calculation_id = (SELECT MAX(calculation_id) FROM lab2.calculation)
    """)
    Calculation findLastCalculation();
}
