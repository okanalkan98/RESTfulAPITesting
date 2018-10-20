package RESTfulAPITesting.StepDefs;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import RESTfulAPITesting.Beans.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import RESTfulAPITesting.Utilities.ConfigurationReader;

public class API_PostAnUser {

	RequestSpecification request;
	Response response;
	User user;
	String url= "https://jsonplaceholder.typicode.com/users"; 
    //String url = ConfigurationReader.prop.getProperty("restbaseurl")+"/users";

    
	
	@Given("content type is JSON and accept type is JSON")
	public void content_type_is_JSON_nnd_accept_type_is_JSON() {
		request=given().log().all() 
		.accept(ContentType.JSON) //I am gonna only accept json from API
		.and().contentType(ContentType.JSON); //I am sending json to API
	}
	
	@When("I post a new user with username {string}")
	public void i_post_a_new_user_with_username(String userName) {
		user = new User();
		HashMap geo = new HashMap<>();
		geo.put("lat", "-37.3159");
		geo.put("lng", "81.1496");
		
		user.setName("Leanne Graham");
		user.setUsername(userName);
		user.setEmail("Sincere@april.biz");
		user.setPhone("1-770-736-8031 x56442");
		user.setWebsite("hildegard.org");
		user.setAddress("Kulas Light", "Apt. 556", "Gwenborough", "92998-3874", geo );
		user.setCompany("Romaguera-Crona", "Multi-layered client-server neural-net", "harness real-time e-markets");
		
		response = request.body(user) //Serialization (class to json)
						.when().post(url);
	}

	@Then("status code should be {int}")
	public void status_code_should_be(int statusCode) {
		assertEquals(statusCode, response.getStatusCode());
	}

	@Then("response Json should contain user info")
	public void response_Json_should_contain_user_info() {
		
		User postResponseUser = response.body().as(User.class); 
		
		assertEquals(postResponseUser.getName(),user.getName());
		assertEquals(postResponseUser.getUsername(),user.getUsername());
		assertEquals(postResponseUser.getEmail(),user.getEmail());
		assertEquals(postResponseUser.getPhone(),user.getPhone());
		assertEquals(postResponseUser.getWebsite(),user.getWebsite());
		assertEquals(postResponseUser.getAddress(),user.getAddress());
		assertEquals(postResponseUser.getCompany(),user.getCompany());
	}
	
	@When("I send a GET request with username {string}")
	public void i_send_a_GET_request_with_username(String userName) {
		HashMap rParamMap = new HashMap<>();
		
		rParamMap.put("username", userName);
		
		response = given().log().all() 
				   .accept(ContentType.JSON) 
				   .and().params(rParamMap)
				   .when().get(url);
	}
	
	@Then("user Json response data should match the posted Json data")
	public void user_Json_response_data_should_match_the_posted_Json_data() {
		
		List<Map> getResponseUser = response.jsonPath().getList("",Map.class);
		
		assertEquals(getResponseUser.get(0).get("name"),user.getName());
		assertEquals(getResponseUser.get(0).get("username"),user.getUsername());
		assertEquals(getResponseUser.get(0).get("email"),user.getEmail());
		assertEquals(getResponseUser.get(0).get("phone"),user.getPhone());
		assertEquals(getResponseUser.get(0).get("website"),user.getWebsite());
		assertEquals(getResponseUser.get(0).get("address"),user.getAddress());
		assertEquals(getResponseUser.get(0).get("company"),user.getCompany());
	}
}
