package RESTfulAPITesting.RESTfulAPITesting;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class RESTfulApiJasonPath {
	
	public static Properties ConfigurationReader;
	
	public RESTfulApiJasonPath() throws IOException {
		ConfigurationReader=new Properties();
		FileInputStream ip=new FileInputStream("C:\\Users\\Qkan\\Desktop\\JAVA\\Java_Codes\\RESTfulAPITesting\\config.properties");
		ConfigurationReader.load(ip);
	}

	/* TEST CASE-1:
	 * Given accept type is json
	 * When I send a GET request to REST url https://jsonplaceholder.typicode.com/users
	 * Then response status should be 200
	 * And response body should be Json
	 * And 10 street names should be returned
	 * And "Rex Trail" is one of the street names
	 * "Victor Plains" and "Douglas Extension" are also included in street names
	 */
	@Test
	public void testItemsCountFromResponseBody() {
		given().accept(ContentType.JSON)
		.when().get(ConfigurationReader.getProperty("restbaseurl")+"/users")
		.then().assertThat().statusCode(200)
		.and().assertThat().contentType(ContentType.JSON)
		.and().assertThat().body("address.street", hasSize(10))
		.and().assertThat().body("address.street",hasItem("Rex Trail"))
		.and().assertThat().body("address.street",hasItems("Victor Plains","Douglas Extension"));
	}
	
	/* TEST CASE-2:
	 * Given accept type is json
	 * And user id equals to 10 (Query Parameter)
	 * When I send a GET request to REST url https://jsonplaceholder.typicode.com/posts?userId=10
	 * Then response status should be 200
	 * And response body should be Json
	 * And 10 posts data should be in json response body
	 */
	@Test
	public void testWithQueryParameterAndList() {
		given().accept(ContentType.JSON)
		.and().params("userId",10)
		.when().get(ConfigurationReader.getProperty("restbaseurl")+"/posts")
		.then().assertThat().statusCode(200)
		.and().assertThat().contentType(ContentType.JSON)
		.and().assertThat().body("id", hasSize(10));
	} 
	
	/* TEST CASE-3:
	 * Given accept type is json
	 * And id equals to 1 (Path parameter)
	 * When I send a GET request to REST url https://jsonplaceholder.typicode.com/todos/1
	 * Then response status should be 200
	 * And response body should be Json
	 * And following data should be returned:
		{
		    "userId": 1,
		    "id": 1,
		    "title": "delectus aut autem",
		    "completed": false
		}
	 */
	@Test
	public void testWithPathParameter() {
		given().accept(ContentType.JSON)
		.and().pathParams(":id", 1)
		.when().get(ConfigurationReader.getProperty("restbaseurl")+"/todos/{:id}")
		.then().assertThat().statusCode(200)
		.and().assertThat().contentType(ContentType.JSON)
		.and().assertThat().body("userId", equalTo(1),
								 "id", equalTo(1),
								 "title", equalTo("delectus aut autem"),
								 "completed", equalTo(false));	
	}
	
	/* TEST CASE-4:
	 * Given accept type is json
	 * And user id equals to 1 (Path parameter)
	 * And post id equals to 1 (Query parameter)
	 * When I send a GET request to REST url https://jsonplaceholder.typicode.com/users/1/todos?id=1
	 * Then response status should be 200
	 * And response body should be Json
	 * And todo id 1 from user id 1 should be returned
	 */
	@Test
	public void testWithJasonPath() {
		
		Map<String,Integer> rParamMap=new HashMap<>();
		rParamMap.put("id", 1);
		
		Response response = given().accept(ContentType.JSON)//header
							.and().pathParams(":userId",1)//query/request parameter
							.and().params(rParamMap)//path parameter
							.when().get(ConfigurationReader.getProperty("restbaseurl")+"/users/{:userId}/todos");
		
		assertEquals(response.statusCode(),200);//check status code
		
		JsonPath json=response.jsonPath();//get json body and assign to JsonPath object
		
		System.out.println(json.getString("userId"));
		System.out.println(json.getString("id"));
		System.out.println(json.getString("title"));
		System.out.println(json.getBoolean("completed"));
	}
	

	/* TEST CASE-5:
	 * Given accept type is json
	 * When I send a GET request to REST url https://jsonplaceholder.typicode.com/users
	 * Then response status should be 200
	 * And response body should be Json
	 * And all user records should be returned
	 */
	@Test
	public void testJasonPathWithList() {
		
		Response response = given().accept(ContentType.JSON)//header
							.when().get(ConfigurationReader.getProperty("restbaseurl")+"/users");
		
		assertEquals(response.statusCode(),200);//check status code
		
		//get json body and assign to JsonPath object
		JsonPath json=response.jsonPath(); // 1.way 
		//JsonPath json=new JsonPath(response.asString()); // 2.way
		//JsonPath json=new JsonPath(new File(FilePath.json)); // 3.way
		
		//get specific element from array
		System.out.println(json.getString("address[0].city"));
		
		//assign all cities into a list of array
		List<String> cities=json.getList("address.city");
		System.out.println(cities);
		assertEquals(cities.size(),10);//assert that there are 10 cities
		
		//get all names for user ids grater than 5
		List<Integer> names=json.getList("findAll{it.id > 5}.name");
		System.out.println(names);
		assertEquals(names.size(),5);//assert that there are 5 names
		
		//get all streets for user ids grater than 5
		List<Integer> streets=json.getList("findAll{it.id > 5}.address.street");
		System.out.println(streets);
		assertEquals(streets.size(),5);//assert that there are 5 streets
		
		//get all usernames for lng values grater than 30
		List<Float> usernames=json.getList("findAll{Float.valueOf(it.address.geo.lng) > 30}.username");
		System.out.println(usernames);
		assertEquals(usernames.size(),4);//assert that there are 4 usernames
		
		//get all lat values for lng values grater than 30
		List<Float> lats=json.getList("address.geo.findAll{Float.valueOf(it.lng) > 30}.lat");
		System.out.println(lats);
		assertEquals(lats.size(),4);//assert that there are 4 lats
		
	}
	
	/* TEST CASE-6:
	 * Import json file
	 */
	@Test
	public void importJsonFromFile() {
		JsonPath jsonFromFile = new JsonPath(new File("C:\\Users\\Qkan\\Desktop\\JAVA\\Java_Codes\\RESTfulAPITesting\\users.json"));
		List<String> emails = jsonFromFile.getList("email");
		System.out.println(emails);
	}
}	
