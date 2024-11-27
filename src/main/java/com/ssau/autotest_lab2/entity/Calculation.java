package com.ssau.autotest_lab2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "calculation", schema = "lab2")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Calculation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "calculationSeqGen")
    @SequenceGenerator(name = "calculationSeqGen", sequenceName = "calculation_sequence", schema = "lab2", allocationSize = 1)
    @Column(name = "calculation_id")
    private Integer id;

    @Column(name = "calculation_first_number")
    private String firstNumber;

    @Column(name = "calculation_first_number_radix")
    private String firstNumberRadix;

    @Column(name = "calculation_second_number")
    private String secondNumber;

    @Column(name = "calculation_second_number_radix")
    private String secondNumberRadix;

    @Column(name = "calculation_processed_at")
    private LocalDateTime processedAt;

    @Column(name = "calculation_operation_type")
    private String operationType;

    public Calculation(String firstNumber,
                       String firstNumberRadix,
                       String secondNumber,
                       String secondNumberRadix,
                       LocalDateTime processedAt,
                       String operationType) {
        this.firstNumber = firstNumber;
        this.firstNumberRadix = firstNumberRadix;
        this.secondNumber = secondNumber;
        this.secondNumberRadix = secondNumberRadix;
        this.processedAt = processedAt;
        this.operationType = operationType;
    }
}

