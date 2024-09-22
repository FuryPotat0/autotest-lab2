package com.ssau.autotest_lab2.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalculationCreateRequest {
    private String firstNumber;
    private String firstNumberRadix;
    private String secondNumber;
    private String secondNumberRadix;
}

