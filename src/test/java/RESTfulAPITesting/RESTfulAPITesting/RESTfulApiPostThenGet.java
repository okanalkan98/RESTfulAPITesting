package RESTfulAPITesting.RESTfulAPITesting;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;

import RESTfulAPITesting.Beans.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class RESTfulApiPostThenGet {
	
	public static Properties ConfigurationReader;
	
	public RESTfulApiPostThenGet() throws IOException {
		ConfigurationReader=new Properties();
		FileInputStream ip=new FileInputStream("C:\\Users\\Qkan\\Desktop\\JAVA\\Java_Codes\\RESTfulAPITesting\\config.properties");
		ConfigurationReader.load(ip);
	}

	/* TEST CASE-1:
	 * Given content type is JSON
	 * And accept type is JSON
	 * When I post a new user with username "Bret"
	 * Then status code should be 201
	 * And response Json should contain user info
	 * When I send a GET request with username "Bret" 
	 * Then status code should be 200
	 * And user Json response data should match the posted Json data
	 */
	@Test
	public void postUserThenGetUser() {
		
		String url = ConfigurationReader.getProperty("restbaseurl")+"/users";

		HashMap geo = new HashMap<>();
		geo.put("lat", "-37.3159");
		geo.put("lng", "81.1496");
		
		User user = new User();
		user.setName("Leanne Graham");
		user.setUsername("Bret");
		user.setEmail("Sincere@april.biz");
		user.setPhone("1-770-736-8031 x56442");
		user.setWebsite("hildegard.org");
		user.setAddress("Kulas Light", "Apt. 556", "Gwenborough", "92998-3874", geo );
		user.setCompany("Romaguera-Crona", "Multi-layered client-server neural-net", "harness real-time e-markets");
		
		//POST Testing
		Response response = given().log().all() 
							.accept(ContentType.JSON) //I am gonna only accept json from API
							.and().contentType(ContentType.JSON) //I am sending json to API
							.and().body(user) //Serialization (class to json)
							.when().post(url);
		
		response.then().assertThat().statusCode(201)
				.and().assertThat().contentType(ContentType.JSON);
		
		//De-serialization (json to User.class)
		User postResponseUser = response.body().as(User.class); 
		
		assertEquals(postResponseUser.getName(),user.getName());
		assertEquals(postResponseUser.getUsername(),user.getUsername());
		assertEquals(postResponseUser.getEmail(),user.getEmail());
		assertEquals(postResponseUser.getPhone(),user.getPhone());
		assertEquals(postResponseUser.getWebsite(),user.getWebsite());
		assertEquals(postResponseUser.getAddress(),user.getAddress());
		assertEquals(postResponseUser.getCompany(),user.getCompany());
		
		
		//GET Testing
		Map<String,String> rParamMap=new HashMap<>();
		rParamMap.put("username","Bret");
		
		response = given().log().all() 
				   .accept(ContentType.JSON) 
				   .and().params(rParamMap)
				   .when().get(url);
		
		response.then().assertThat().statusCode(200)
				.and().assertThat().contentType(ContentType.JSON);
		
		//De-serialization (json to List<Map>)
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




