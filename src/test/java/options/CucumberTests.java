package options;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = {"pretty"},
		glue = {"stepdefs"},
		tags = {"~@TitleTest"},
		features = {"src/test/features"})
public class CucumberTests {}
