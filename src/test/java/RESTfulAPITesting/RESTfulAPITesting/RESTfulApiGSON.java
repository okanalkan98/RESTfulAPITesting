package RESTfulAPITesting.RESTfulAPITesting;

import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static io.restassured.RestAssured.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


public class RESTfulApiGSON {
	
	public static Properties ConfigurationReader;
	
	public RESTfulApiGSON() throws IOException {
		ConfigurationReader=new Properties();
		FileInputStream ip=new FileInputStream("C:\\Users\\Qkan\\Desktop\\JAVA\\Java_Codes\\RESTfulAPITesting\\config.properties");
		ConfigurationReader.load(ip);
	}
	

	@Test
	public void testWithJsonToHashMap() {
		Response response = given().accept(ContentType.JSON)
							.when().get(ConfigurationReader.getProperty("restbaseurl")+"/users/1");
		
		HashMap<String,String> map = response.as(HashMap.class); //De-serialization
		
		System.out.println(map.keySet());
		System.out.println(map.values());

		assertEquals(map.get("id"),1.0);
		assertEquals(map.get("username"),"Bret");
	}
	
	@Test
	public void convertJsonToListOfMaps() {
		Response response = given().accept(ContentType.JSON)
							.when().get(ConfigurationReader.getProperty("restbaseurl")+"/users");
		
		//Convert the response that contains user information into list of maps:
		// List<Map<String,String>>
		List<Map<String,String>> listOfMaps = response.as(ArrayList.class);
		//List<Map> listOfMaps = response.jsonPath().getList("",Map.class); //second way
		System.out.println(listOfMaps.get(0));
		
		//assert that first username is "Bret"
		assertEquals(listOfMaps.get(0).get("username"),"Bret");
		
	}
	
	/*
	 * Given content type is Json
	 * And user id is 1
	 * When I send a GET request to REST url https://jsonplaceholder.typicode.com/todos?userId=1
	 * Then I should see the first todo data of user id 1 like below:
	 *     {
		        "userId": 1,
		        "id": 1,
		        "title": "delectus aut autem",
		        "completed": false
		   }
	 */
	@Test
	public void jsonValidationOfTodo() {
		
		Map<String,Integer> rParamMap=new HashMap<>();
		rParamMap.put("userId", 1);
		
		Response response = given().accept(ContentType.JSON)
							.and().params(rParamMap)
							.when().get(ConfigurationReader.getProperty("restbaseurl")+"/todos");
							
		response.then().assertThat().statusCode(200)
				.and().assertThat().contentType(ContentType.JSON);
		
		JsonPath json = response.jsonPath();
		
		//Validate userId
		int userId=json.getInt("find{it.id=1}.userId");
		assertEquals(userId,1);
		
		//Validate title
		String title=json.getString("find{it.id=1}.title");
		assertEquals(title,"delectus aut autem");
		
		//Validate completed
		Boolean completed=json.getBoolean("find{it.id=1}.completed");
		assertTrue(completed==false);

	}

	/*
	 * Given content type is Json
	 * And post id is 1
	 * When I send a GET request to REST url https://jsonplaceholder.typicode.com/comments?postId=1
	 * Then I should see all comments of post id 1
	 */
	@Test
	public void jsonValidationOfComments() {
		
		Map<String,Integer> rParamMap=new HashMap<>();
		rParamMap.put("postId", 1);
		
		Response response = given().accept(ContentType.JSON)
							.and().params(rParamMap)
							.when().get(ConfigurationReader.getProperty("restbaseurl")+"/comments");
							
		response.then().assertThat().statusCode(200)
				.and().assertThat().contentType(ContentType.JSON);
		
		//Deseriliaze json to List<Map>
		List<Map> comments = response.jsonPath().getList("",Map.class);
		
		Map<Integer,String> expectedComments = new HashMap<>();
		
		expectedComments.put(1, "id labore ex et quam laborum");
		expectedComments.put(2, "quo vero reiciendis velit similique earum");
		expectedComments.put(3, "odio adipisci rerum aut animi");
		expectedComments.put(4, "alias odio sit");
		expectedComments.put(5, "vero eaque aliquid doloribus et culpa");
		
		for(Integer commentId : expectedComments.keySet()) {
			for(Map map : comments) {
				if(map.get("id")==commentId) {
					assertEquals(map.get("name"),expectedComments.get(commentId));
				}
			}
		}
		//2.way of assertion with using JsonPath
//		JsonPath json=response.jsonPath(); 
//		for(Integer id : expectedComments.keySet()) {
//			assertEquals(json.getString("name["+(id-1)+"]"),expectedComments.get(id));
//		}
	}
}
