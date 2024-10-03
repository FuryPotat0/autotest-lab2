package com.ssau.autotest_lab2;

import com.ssau.autotest_lab2.dto.CalculationDTO;
import com.ssau.autotest_lab2.request.CalculationCreateRequest;
import com.ssau.autotest_lab2.service.CalculationService;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(classes = AutotestLab2Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AutotestLab2ApplicationTests extends AuditVizualizationBaseTest {

	@Autowired
	CalculationService calculationService;

	@FlywayTest
	@Test
	public void whenEmptySearch_thenAllRowsReturns() {
		TestRestTemplate testRestTemplate = new TestRestTemplate();
		ResponseEntity<List> response = testRestTemplate.
				getForEntity("http://localhost:8080/search", List.class);

		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Assertions.assertEquals(response.getBody().size(), 21);
	}

	@FlywayTest
	@Test
	public void whenDivideSearch_thenDivideRowsReturns() {
		TestRestTemplate testRestTemplate = new TestRestTemplate();
		ResponseEntity<List> response = testRestTemplate.
				getForEntity("http://localhost:8080/search?operationType=/", List.class);

		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Assertions.assertEquals(response.getBody().size(), 6);
	}

	@FlywayTest
	@Test
	public void whenDateSearch_thenDateRowsReturns() {
		TestRestTemplate testRestTemplate = new TestRestTemplate();
		LocalDateTime processedFrom = LocalDateTime.of(2024, 10, 2, 0 ,0, 0);
		LocalDateTime processedTo = LocalDateTime.of(2024, 10, 2, 23 ,59, 59);
		ResponseEntity<List> response = testRestTemplate.
				getForEntity(String.format("http://localhost:8080/search?processedFrom=%s&processedTo=%s",
						processedFrom,
						processedTo
				), List.class);

		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Assertions.assertEquals(response.getBody().size(), 4);
	}

	@FlywayTest
	@Test
	public void whenAdd_thenRecordCreated() {
		CalculationCreateRequest createRequest = new CalculationCreateRequest("11", "10", "12", "10");

		TestRestTemplate testRestTemplate = new TestRestTemplate();
		ResponseEntity<String> response = testRestTemplate.
				getForEntity(String.format("http://localhost:8080/add?firstNumber=%s&firstNumberRadix=%s&secondNumber=%s&secondNumberRadix=%s",
						createRequest.getFirstNumber(),
						createRequest.getFirstNumberRadix(),
						createRequest.getSecondNumber(),
						createRequest.getSecondNumberRadix()
				), String.class);
		CalculationDTO calculationDTO = calculationService.getLastCalculation();

		Assertions.assertEquals(calculationDTO.getFirstNumber(), createRequest.getFirstNumber());
		Assertions.assertEquals(calculationDTO.getFirstNumberRadix(), createRequest.getFirstNumberRadix());
		Assertions.assertEquals(calculationDTO.getSecondNumber(), createRequest.getSecondNumber());
		Assertions.assertEquals(calculationDTO.getSecondNumberRadix(), createRequest.getSecondNumberRadix());
		Assertions.assertEquals(calculationDTO.getOperationType(), "+");
	}

	@FlywayTest
	@Test
	public void whenMultiply_thenRecordCreated() {
		CalculationCreateRequest createRequest = new CalculationCreateRequest("15", "10", "777", "16");

		TestRestTemplate testRestTemplate = new TestRestTemplate();
		ResponseEntity<String> response = testRestTemplate.
				getForEntity(String.format("http://localhost:8080/multiply?firstNumber=%s&firstNumberRadix=%s&secondNumber=%s&secondNumberRadix=%s",
						createRequest.getFirstNumber(),
						createRequest.getFirstNumberRadix(),
						createRequest.getSecondNumber(),
						createRequest.getSecondNumberRadix()
				), String.class);
		CalculationDTO calculationDTO = calculationService.getLastCalculation();

		Assertions.assertEquals(calculationDTO.getFirstNumber(), createRequest.getFirstNumber());
		Assertions.assertEquals(calculationDTO.getFirstNumberRadix(), createRequest.getFirstNumberRadix());
		Assertions.assertEquals(calculationDTO.getSecondNumber(), createRequest.getSecondNumber());
		Assertions.assertEquals(calculationDTO.getSecondNumberRadix(), createRequest.getSecondNumberRadix());
		Assertions.assertEquals(calculationDTO.getOperationType(), "*");
	}

	@FlywayTest
	@Test
	public void whenSubtract_thenRecordCreated() {
		CalculationCreateRequest createRequest = new CalculationCreateRequest("345", "16", "13", "8");

		TestRestTemplate testRestTemplate = new TestRestTemplate();
		ResponseEntity<String> response = testRestTemplate.
				getForEntity(String.format("http://localhost:8080/subtract?firstNumber=%s&firstNumberRadix=%s&secondNumber=%s&secondNumberRadix=%s",
						createRequest.getFirstNumber(),
						createRequest.getFirstNumberRadix(),
						createRequest.getSecondNumber(),
						createRequest.getSecondNumberRadix()
				), String.class);
		CalculationDTO calculationDTO = calculationService.getLastCalculation();

		Assertions.assertEquals(calculationDTO.getFirstNumber(), createRequest.getFirstNumber());
		Assertions.assertEquals(calculationDTO.getFirstNumberRadix(), createRequest.getFirstNumberRadix());
		Assertions.assertEquals(calculationDTO.getSecondNumber(), createRequest.getSecondNumber());
		Assertions.assertEquals(calculationDTO.getSecondNumberRadix(), createRequest.getSecondNumberRadix());
		Assertions.assertEquals(calculationDTO.getOperationType(), "-");
	}

	@FlywayTest
	@Test
	public void whenDivide_thenRecordCreated() {
		CalculationCreateRequest createRequest = new CalculationCreateRequest("10000", "10", "25", "10");

		TestRestTemplate testRestTemplate = new TestRestTemplate();
		ResponseEntity<String> response = testRestTemplate.
				getForEntity(String.format("http://localhost:8080/divide?firstNumber=%s&firstNumberRadix=%s&secondNumber=%s&secondNumberRadix=%s",
						createRequest.getFirstNumber(),
						createRequest.getFirstNumberRadix(),
						createRequest.getSecondNumber(),
						createRequest.getSecondNumberRadix()
				), String.class);
		CalculationDTO calculationDTO = calculationService.getLastCalculation();

		Assertions.assertEquals(calculationDTO.getFirstNumber(), createRequest.getFirstNumber());
		Assertions.assertEquals(calculationDTO.getFirstNumberRadix(), createRequest.getFirstNumberRadix());
		Assertions.assertEquals(calculationDTO.getSecondNumber(), createRequest.getSecondNumber());
		Assertions.assertEquals(calculationDTO.getSecondNumberRadix(), createRequest.getSecondNumberRadix());
		Assertions.assertEquals(calculationDTO.getOperationType(), "/");
	}
}
