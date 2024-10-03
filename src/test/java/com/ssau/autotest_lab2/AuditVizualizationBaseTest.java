package com.ssau.autotest_lab2;

import org.flywaydb.test.FlywayTestExecutionListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@ContextConfiguration
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, SqlScriptsTestExecutionListener.class, FlywayTestExecutionListener.class})
public abstract class AuditVizualizationBaseTest {
    @Container
    protected static final PostgreSQLContainer POSTGRE_SQL_CONTAINER;

    static {
        POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>("postgres:15.1")
                .withUsername("postgres")
                .withPassword("1234");
        POSTGRE_SQL_CONTAINER.start();
    }
}

