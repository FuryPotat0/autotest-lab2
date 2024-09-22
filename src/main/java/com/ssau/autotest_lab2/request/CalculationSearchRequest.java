package com.ssau.autotest_lab2.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CalculationSearchRequest {
    private LocalDateTime processedFrom;
    private LocalDateTime processedTo;
    private String operationType;
    private String firstNumberRadix;
    private String secondNumberRadix;
}

