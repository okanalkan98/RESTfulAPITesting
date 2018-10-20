package RESTfulAPITesting.RESTfulAPITesting;

import org.testng.annotations.Test;
import java.net.URI;
import java.net.URISyntaxException;

import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class RESTfulApiGetRequest {

	/* TEST CASE-1:
	 * When I send a GET request to REST url https://jsonplaceholder.typicode.com/posts?userId=9&id=81
	 * Then response status code should be 200
	 */
  @Test
  public void simpleGet() {
	  when().get("https://jsonplaceholder.typicode.com/posts?userId=9&id=81")
	  .then().statusCode(200);
  }
  
	/* TEST CASE-2:
	 * When I send a GET request to REST url https://jsonplaceholder.typicode.com/posts?userId=9
	 * Then I should see JSON response
	 */
  @Test
  public void printResponse() {
	  when().get("https://jsonplaceholder.typicode.com/posts?userId=9")
	  .body().prettyPrint();
  }
  
	/* TEST CASE-3:
	 * With accept type is "application/json"
	 * When I send a GET request to REST url https://jsonplaceholder.typicode.com/posts?userId=9
	 * Then response status should be 200
	 */
  @Test
  public void getWithHeaders() {
	  with().accept(ContentType.JSON) //accept - application/json 
	  .when().get("https://jsonplaceholder.typicode.com/posts?userId=9")
	  .then().statusCode(200);
  }
  
	/* TEST CASE-4:
	 * When I send a GET request to REST url https://jsonplaceholder.typicode.com/users/okan
	 * Then response status should be 404
	 * And response body should contains "}"
	 */
  @Test
  public void negativeGet() {
//	  when().get("https://jsonplaceholder.typicode.com/users/okan")
//	  .then().statusCode(404);
	  Response response=when().get("https://jsonplaceholder.typicode.com/users/okan");
	  assertEquals(response.statusCode(),404);
	  assertTrue(response.asString().contains("}"));
	  response.prettyPrint();
  }
  
	/* TEST CASE-5:
	 * Given accept type is json
	 * When I send a GET request to REST url https://jsonplaceholder.typicode.com/users/1
	 * Then response status should be 200
	 * And response body should be Json
	 * 
	 * GIVEN,ACCEPT,WHEN,GET,ASSERTTHAT,CONTENTTYPE
	 */
  @Test
  public void VerifyContentTypeWithAssertThat() {
	  given().accept(ContentType.JSON)
	  .when().get("https://jsonplaceholder.typicode.com/users/1")
	  .then().assertThat().statusCode(200)
	  .and().contentType(ContentType.JSON);
  }
  
	/* TEST CASE-6:
	 * Given accept type is json
	 * When I send a GET request to REST url https://jsonplaceholder.typicode.com/users/1
	 * Then response status should be 200
	 * And response body should be Json
	 * And name should be "Leanne Graham"
	 * And id should be 1
	 */
  @Test
  public void verifyName() throws URISyntaxException {
	  
	  URI uri=new URI("https://jsonplaceholder.typicode.com/users/1");
	  
	  given().accept(ContentType.JSON)
	  .when().get(uri)
	  .then().assertThat().statusCode(200)
	  .and().contentType(ContentType.JSON)
	  .and().assertThat().body("name", equalTo("Leanne Graham"))
	  .and().assertThat().body("id", Matchers.equalTo(1));
  }
}
