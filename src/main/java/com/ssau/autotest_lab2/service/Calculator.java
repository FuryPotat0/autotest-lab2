package com.ssau.autotest_lab2.service;

import com.ssau.autotest_lab2.request.CalculationCreateRequest;
import org.springframework.stereotype.Service;

@Service
public class Calculator {
    public String add(CalculationCreateRequest request) {
        return Integer.toString(Integer.parseInt(request.getFirstNumber(), Integer.parseInt(request.getFirstNumberRadix()))
                + Integer.parseInt(request.getSecondNumber(), Integer.parseInt(request.getSecondNumberRadix())));
    }

    public String subtract(CalculationCreateRequest request) {
        return Integer.toString(Integer.parseInt(request.getFirstNumber(), Integer.parseInt(request.getFirstNumberRadix()))
                                - Integer.parseInt(request.getSecondNumber(), Integer.parseInt(request.getSecondNumberRadix())));
    }

    public String multiply(CalculationCreateRequest request) {
        return Integer.toString(Integer.parseInt(request.getFirstNumber(), Integer.parseInt(request.getFirstNumberRadix()))
                * Integer.parseInt(request.getSecondNumber(), Integer.parseInt(request.getSecondNumberRadix())));
    }

    public String divide(CalculationCreateRequest request) {
        return Integer.toString(Integer.parseInt(request.getFirstNumber(), Integer.parseInt(request.getFirstNumberRadix()))
                                / Integer.parseInt(request.getSecondNumber(), Integer.parseInt(request.getSecondNumberRadix())));
    }
}

