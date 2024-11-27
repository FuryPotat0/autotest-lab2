package com.ssau.autotest_lab2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.dockerjava.api.exception.NotFoundException;
import com.ssau.autotest_lab2.converter.CalculationConverter;
import com.ssau.autotest_lab2.dto.CalculationDTO;
import com.ssau.autotest_lab2.entity.Calculation;
import com.ssau.autotest_lab2.repository.CalculationRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.DefaultDataTableCellTransformer;
import io.cucumber.java.DefaultDataTableEntryTransformer;
import io.cucumber.java.DefaultParameterTransformer;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = AutotestLab2Application.class)
@CucumberContextConfiguration
@RequiredArgsConstructor
public class CucumberTestsDefinition {
    private final CalculationRepository calculationRepository;
    private final CalculationConverter calculationConverter;

    @Getter
    private String status;

    @Getter
    private String getResult;

    @Getter
    private List searchResult;

    @Getter
    private Response response;

    @Getter
    private Response responseDate;

    private String calculationString;

    public ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    @DefaultParameterTransformer
    @DefaultDataTableEntryTransformer
    @DefaultDataTableCellTransformer
    public Object defaultTransformer(Object fromValue, Type toValueType) {
        JavaType javaType = mapper.constructType(toValueType);
        return mapper.convertValue(fromValue, javaType);
    }

    @Given("^calculations table$")
    public void setUp(DataTable table) throws IOException {
        calculationRepository.deleteAll();
        List<List<String>> rows = table.asLists(String.class);
        List<Calculation> calculations = new ArrayList<>();
        rows = rows.subList(1, rows.size());
        for (List<String> columns: rows) {
            calculations.add(new Calculation(
                    columns.get(0),
                    columns.get(1),
                    columns.get(2),
                    columns.get(3),
                    LocalDateTime.parse(columns.get(4)),
                    columns.get(5)
            ));
        }
        calculationRepository.saveAllAndFlush(calculations);
    }


    @When("^I perform search request \"([^\"]*)\"$")
    public void searchAllCalculations(String url) throws Exception {
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<List> response = testRestTemplate.
                getForEntity(url, List.class);
        status = String.valueOf(response.getStatusCode().value());
        searchResult = response.getBody();
    }

    @When("^I perform get request \"([^\"]*)\"$")
    public void getAllCalculations(String url) throws Exception {
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<String> response = testRestTemplate.
                getForEntity(url, String.class);
        status = String.valueOf(response.getStatusCode().value());
        getResult = response.getBody();
    }

    @Then("I should receive response status code 200")
    public void responseAllStatusCode() {
        Assertions.assertEquals("200", getStatus());
    }

    @And("Body should contain calculations that are given into the table")
    public void responseAllBody() throws JsonProcessingException {
        Assertions.assertEquals(
                mapper.writeValueAsString(calculationRepository.findAll().stream().map(calculationConverter::toDTO).toList()),
                mapper.writeValueAsString(getSearchResult().stream().toList())
        );
    }

    @And("^Body should contain (\\d+) calculations$")
    public void bodyShouldContainCalculationNumberCalculations(Integer calculationNumber) throws JsonProcessingException {
        Assertions.assertEquals(
                calculationNumber,
                searchResult.size()
        );
    }

    @When("^I perform get request \"([^\"]*)\" with parameters (.+) and (.+) and (.+) and (.+)$")
    public void getResult(String url, String firstNumber, String secondNumber, String firstNumberRadix, String secondNumberRadix) {
        calculationString = String.valueOf(RestAssured.given().get(url + "?firstNumber=" + firstNumber + "&firstNumberRadix=" + firstNumberRadix + "&secondNumber=" + secondNumber + "&secondNumberRadix="  + secondNumberRadix).getBody().prettyPrint());
    }

    @Then("^I see the result as (.+)$")
    public void checkResult(String result) {
        System.out.println("Result from get request: " + calculationString);
        System.out.println("Result from example table: " + result);
        Assertions.assertEquals(result, calculationString);
    }

    @Then("I should receive status code 200")
    public void receiveStatusAfterAdding() {
        Assertions.assertEquals(200, getResponse().getStatusCode());
    }

    @And("I should receive body that given into the table")
    public void checkBodyAfterAdding() throws JsonProcessingException {
        Calculation calculationEntity = calculationRepository.findById(Integer.valueOf(getResponse().jsonPath().getString("id"))).orElseThrow(() -> new NotFoundException("Calculation not found"));
        System.out.println(calculationEntity.getProcessedAt().getNano());
        Assertions.assertEquals(
                mapper.writeValueAsString(calculationConverter.toDTO(calculationEntity)),
                getResponse().getBody().print()
        );
    }

    @When("^I perform get request \"([^\"]*)\" and I pass this date (.+) and below body$")
    public void postWithDate(String url, LocalDateTime date, CalculationDTO createCalculationDto) throws ParseException, JsonProcessingException {
        createCalculationDto.setProcessedAt(date);
        responseDate = RestAssured.given()
                .header("Content-type", "application/json")
                .and().body(mapper.writeValueAsString(createCalculationDto))
                .when().post(url).then().extract().response();
    }

    @Then("I receive status code 200")
    public void receiveStatusAfterAddingWithDate() {
        Assertions.assertEquals(200, getResponseDate().getStatusCode());
    }
}

