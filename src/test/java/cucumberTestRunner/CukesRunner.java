package cucumberTestRunner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)//This is used to make this file run the feature files. Comes from Junit.
@CucumberOptions(
		//plugin = {"html:target/cucumber"}, //Reporting
		features = {"src/test/resources/Features"}, //Folder where feature files store
		glue = {"RESTfulAPITesting.StepDefs"}, //Folder where step definition files store
		tags = "@ApiPost" //Tags in feature files which will be run
		//,dryRun = true //Checking features, glue and tags are set properly without running actual test	
		)





public class CukesRunner {

}
