package com.ssau.autotest_lab2.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CalculationCreateRequest {
    private String firstNumber;
    private String firstNumberRadix;
    private String secondNumber;
    private String secondNumberRadix;
}

