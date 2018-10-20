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
import RESTfulAPITesting.Utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RESTfulApiPostThenGet {
	User user = new User();
	Response response;
	RequestSpecification request;

	
//	@Given("Content type is json")
//	public void content_type_is_json() {
//		request = given().contentType(ContentType.JSON);	
//	}
//
//	@Given("Accept type is json")
//	public void accept_type_is_json() {
//		request.accept(ContentType.JSON);
//	}
//
//	@When("I post a new user with username {string}")
//	public void i_post_a_new_user_with_username(String userName) {
//		HashMap geo = new HashMap<>();
//		geo.put("lat", "-37.3159");
//		geo.put("lng", "81.1496");
//		
//		user.setName("Leanne Graham");
//		user.setUsername(userName);
//		user.setEmail("Sincere@april.biz");
//		user.setPhone("1-770-736-8031 x56442");
//		user.setWebsite("hildegard.org");
//		user.setAddress("Kulas Light", "Apt. 556", "Gwenborough", "92998-3874", geo );
//		user.setCompany("Romaguera-Crona", "Multi-layered client-server neural-net", "harness real-time e-markets");
//		
//		response=request.body(user)
//			    .when().post("https://jsonplaceholder.typicode.com/users");
//		
//		
//	}
//
//	@Then("Status code should be {int}")
//	public void status_code_should_be(Integer statusCode) {
//		response.then().assertThat().statusCode(statusCode);
//	}
//
//	@Then("Response Json should contain user info")
//	public void response_Json_should_contain_user_info() {
//		response.then().assertThat().contentType(ContentType.JSON);
//		
//		//De-serialization (json to User.class)
//		User postResponseUser = response.body().as(User.class); 
//		
//		assertEquals("Name is not expected",postResponseUser.getName(),user.getName());
//		assertEquals("userName is not expected",postResponseUser.getUsername(),user.getUsername());
//		assertEquals("Email is not expected",postResponseUser.getEmail(),user.getEmail());
//		assertEquals("Phone is not expected",postResponseUser.getPhone(),user.getPhone());
//		assertEquals("Website is not expected",postResponseUser.getWebsite(),user.getWebsite());
//		assertEquals("Address is not expected",postResponseUser.getAddress(),user.getAddress());
//		assertEquals("Company is not expected",postResponseUser.getCompany(),user.getCompany());
//		
//	}
//
//	@When("I send a GET request with username {string}")
//	public void i_send_a_GET_request_with_username(String userName) {
//		Map<String,String> rParamMap=new HashMap<>();
//		rParamMap.put("username",userName);
//		
//		response = given().params(rParamMap)
//					.get("https://jsonplaceholder.typicode.com/users");
//	}
//
//	
//	@Then("user Json response data should match the posted Json data")
//	public void user_Json_response_data_should_match_the_posted_Json_data() {
//	
//	//De-serialization (json to List<Map>)
//	List<Map> getResponseUser = response.jsonPath().getList("",Map.class);
//	
//	assertEquals(getResponseUser.get(0).get("name"),user.getName());
//	assertEquals(getResponseUser.get(0).get("username"),user.getUsername());
//	assertEquals(getResponseUser.get(0).get("email"),user.getEmail());
//	assertEquals(getResponseUser.get(0).get("phone"),user.getPhone());
//	assertEquals(getResponseUser.get(0).get("website"),user.getWebsite());
//	assertEquals(getResponseUser.get(0).get("address"),user.getAddress());
//	assertEquals(getResponseUser.get(0).get("company"),user.getCompany());
//
//}
}
