package com.ssau.autotest_lab2.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalculationDTO {
    private Integer id;
    private String firstNumber;
    private String firstNumberRadix;
    private String secondNumber;
    private String secondNumberRadix;
    private LocalDateTime processedAt;
    private String operationType;
}

