package com.ssau.autotest_lab2.service;

import com.ssau.autotest_lab2.converter.CalculationConverter;
import com.ssau.autotest_lab2.dto.CalculationDTO;
import com.ssau.autotest_lab2.entity.Calculation;
import com.ssau.autotest_lab2.repository.CalculationRepository;
import com.ssau.autotest_lab2.request.CalculationCreateRequest;
import com.ssau.autotest_lab2.request.CalculationSearchRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CalculationService {
    private final Calculator calculator;
    private final CalculationRepository calculationRepository;
    private final CalculationConverter calculationConverter;
    private final EntityManager em;

    public String add(CalculationCreateRequest request) {
        String answer = calculator.add(request);
        this.save(request, "+");
        return answer;
    }

    public String subtract(CalculationCreateRequest request) {
        String answer = calculator.subtract(request);
        this.save(request, "-");
        return answer;
    }

    public String multiply(CalculationCreateRequest request) {
        String answer = calculator.multiply(request);
        this.save(request, "*");
        return answer;
    }

    public String divide(CalculationCreateRequest request) {
        String answer = calculator.divide(request);
        this.save(request, "/");
        return answer;
    }

    public List<CalculationDTO> search(CalculationSearchRequest searchRequest) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Calculation> cq = cb.createQuery(Calculation.class);
        Root<Calculation> calculation = cq.from(Calculation.class);
        List<Predicate> predicates = new ArrayList<>();

        if (searchRequest.getProcessedFrom() != null) {
            predicates.add(cb.greaterThanOrEqualTo(calculation.get("processedAt"), searchRequest.getProcessedFrom()));
        }
        if (searchRequest.getProcessedTo() != null) {
            predicates.add(cb.lessThanOrEqualTo(calculation.get("processedAt"), searchRequest.getProcessedTo()));
        }
        if (searchRequest.getOperationType() != null) {
            predicates.add(cb.equal(calculation.get("operationType"), searchRequest.getOperationType()));
        }
        if (searchRequest.getFirstNumberRadix() != null) {
            predicates.add(cb.equal(calculation.get("firstNumberRadix"), searchRequest.getFirstNumberRadix()));
        }
        if (searchRequest.getSecondNumberRadix() != null) {
            predicates.add(cb.equal(calculation.get("secondNumberRadix"), searchRequest.getSecondNumberRadix()));
        }
        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList().stream().map(calculationConverter::toDTO).toList();
    }

    private void save(CalculationCreateRequest request, String operationType) {
        CalculationDTO dto = this.fromRequest(request, operationType);
        calculationRepository.save(calculationConverter.toEntity(dto));
    }

    private CalculationDTO fromRequest(CalculationCreateRequest request, String operationType) {
        return CalculationDTO.builder()
                .firstNumber(request.getFirstNumber())
                .firstNumberRadix(request.getFirstNumberRadix())
                .secondNumber(request.getSecondNumber())
                .secondNumberRadix(request.getSecondNumberRadix())
                .operationType(operationType)
                .processedAt(LocalDateTime.now())
                .build();
    }
}

