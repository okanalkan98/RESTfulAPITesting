package RESTfulAPITesting.RESTfulAPITesting;

import org.testng.annotations.Test;

import RESTfulAPITesting.Beans.Post;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class RESTfulApiPostRequest {
	
	public static Properties ConfigurationReader;
	
	public RESTfulApiPostRequest() throws IOException {
		ConfigurationReader=new Properties();
		FileInputStream ip=new FileInputStream("C:\\Users\\Qkan\\Desktop\\JAVA\\Java_Codes\\RESTfulAPITesting\\config.properties");
		ConfigurationReader.load(ip);
	}

	/* TEST CASE-1:
	 * Given content type is JSON
	 * And accept type is JSON
	 * When send POST request to url https://jsonplaceholder.typicode.com/posts
	 * With request body:
	 * 	{
	      "title": "foo",
	      "body": "bar",
	      "userId": 1
		}
	 * Then status code should be 200
	 * and response body should match request body
	 */
	
	@Test
	public void postUsingHashMap() {
		
		String url = ConfigurationReader.getProperty("restbaseurl")+"/posts/";
		
		//String requestJson = "{\"title\":\"foo\",\"body\":\"bar\",\"userId\":1}";	
		
		Map requestMap = new HashMap<>();
		requestMap.put("title", "foo");
		requestMap.put("body", "bar");
		requestMap.put("userId", 1);
		
		Response response = given().accept(ContentType.JSON)
							.and().contentType(ContentType.JSON)
							.and().body(requestMap) //Serialization
							.when().post(url);
		
		System.out.println(response.statusLine());
		response.prettyPrint();
		
		assertEquals(response.statusCode(),201);
		
		Map responseMap = response.body().as(Map.class); //De-serilization
		
		assertEquals(responseMap.get("title"),requestMap.get("title"));
		assertEquals(responseMap.get("body"),requestMap.get("body"));
		assertEquals(responseMap.get("userId"),Double.valueOf(requestMap.get("userId").toString()));	
	}
	
	@Test
	public void postUsingPOJO() {
		
		String url = ConfigurationReader.getProperty("restbaseurl")+"/posts/";
		
		Post post=new Post();
		post.setTitle("foo");
		post.setBody("bar");
		post.setUserId(1);
		
		Response response = given().log().all() //shows what you are sending
							.accept(ContentType.JSON)
							.and().contentType(ContentType.JSON)
							.and().body(post) //Serialization
							.when().post(url);
		
//		assertEquals(response.statusCode(),201);
//		assertEquals(response.contentType(),ContentType.JSON);
		
		response.then().assertThat().statusCode(201)
				.and().assertThat().contentType(ContentType.JSON);
		
		Post responsePost = response.body().as(Post.class); //De-serilization
		
		assertEquals(responsePost.getTitle(),post.getTitle());
		assertEquals(responsePost.getBody(),post.getBody());
		assertEquals(responsePost.getUserId(),post.getUserId());

	}
}
