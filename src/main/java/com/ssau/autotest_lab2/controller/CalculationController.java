package com.ssau.autotest_lab2.controller;

import com.ssau.autotest_lab2.dto.CalculationDTO;
import com.ssau.autotest_lab2.request.CalculationCreateRequest;
import com.ssau.autotest_lab2.request.CalculationSearchRequest;
import com.ssau.autotest_lab2.service.CalculationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class CalculationController {
    private final CalculationService calculationService;

    @GetMapping("/add")
    public String add(CalculationCreateRequest request) {
        return calculationService.add(request);
    }

    @GetMapping("/subtract")
    public String subtract(CalculationCreateRequest request) {
        return calculationService.subtract(request);
    }

    @GetMapping("/multiply")
    public String multiply(CalculationCreateRequest request) {
        return calculationService.multiply(request);
    }

    @GetMapping("/divide")
    public String divide(CalculationCreateRequest request) {
        return calculationService.divide(request);
    }

    @GetMapping("/search")
    public List<CalculationDTO> search(CalculationSearchRequest request) {
        return calculationService.search(request);
    }
}

