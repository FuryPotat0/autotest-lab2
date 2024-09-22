package com.ssau.autotest_lab2.converter;

import com.ssau.autotest_lab2.dto.CalculationDTO;
import com.ssau.autotest_lab2.entity.Calculation;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CalculationConverter {
    public CalculationDTO toDTO(Calculation entity) {
        CalculationDTO dto = new CalculationDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    public Calculation toEntity(CalculationDTO dto) {
        Calculation entity = new Calculation();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
}

