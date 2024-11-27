package com.ssau.autotest_lab2;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.ManualRestDocumentation;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(classes = AutotestLab2Application.class)
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources",
        glue = "com.ssau.autotest_lab2",
        tags = "@all",
        dryRun = false,
        snippets = CucumberOptions.SnippetType.CAMELCASE
)
@ContextConfiguration(classes = AutotestLab2Application.class)
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
public class CucumberIntegrationTests {
    private ManualRestDocumentation restDocumentation;

    public void setUp() {
        restDocumentation = new ManualRestDocumentation("target/generated-snippets");

        restDocumentation.beforeTest(CucumberTestsDefinition.class, "setUp");
    }

    public void tearDown() {
        restDocumentation.afterTest();
    }
}

