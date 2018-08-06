package tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;



public class HR_REST_API_GET_REQUEST {

	/*
	 * When I send a GET request to REST Url http://34.223.219.142:1212/ords/hr/employees
	 * Then response status should be 200
	 */
	
	
	@Test
	public void simpleGet() {
		
		when().get("http://34.223.219.142:1212/ords/hr/employees")
		.then()
		.statusCode(200);
		
	}
	
	/*
	 * When I send a GET request to REST Url http://34.223.219.142:1212/ords/hr/countries
	 * Then I should see JSon response
	 */
	@Test
	public void printResponse() {
		when().get("http://34.223.219.142:1212/ords/hr/countries/US")
		.body().prettyPrint();
		
	}

	/*
	 * When I send a GET request to REST API Url http://34.223.219.142:1212/ords/hr/countries/US
	 * And accept type is "application/json"
	 * Then response status code should be 200
	 */
	
	@Test
	public void getWithHeader() {
		with().accept(ContentType.JSON)
		.when().get("http://34.223.219.142:1212/ords/hr/countries/US")
		.then().statusCode(200);
		
	}
	
	/*
	 * When I send a GET request to REST API Url http://34.223.219.142:1212/ords/hr/employees/1234
	 * Then response status code should be 404
	 * ANd Response body error message is "Not Found"
	 */
	
	@Test
	public void negativeTest() {
//		when().get("http://34.223.219.142:1212/ords/hr/employees/1234")
//		.then().statusCode(404);
		
		Response response = when().get("http://34.223.219.142:1212/ords/hr/employees/1234");
		assertEquals(response.statusCode(), 404);
		assertTrue(response.asString().contains("Not Found"));
		response.prettyPrint();
		
		response.then().assertThat();
		
	}
	
	/*
	 * When I send a GET request to REST API Url http://34.223.219.142:1212/ords/hr/employees/101
	 * And assert type is JSon 
	 * Then response status code should be 200
	 * ANd Response body content should be JSon
	 * when, with, get, assertThat, accept, content
	 * given().accept(ContentType.JSON) - I will accept only JSon
	 */
	@Test
	public void verifyContentTypeWithAssertThat() {
		String url = "http://34.223.219.142:1212/ords/hr/employees/101";
		given().accept(ContentType.JSON) //here we asking that we need JSon
		.when().get(url)	//sending to this url
		.then().assertThat().statusCode(200) //asserting if its returning status code is 200 (assertThat - will give more options)
		.and().contentType(ContentType.JSON); //checking if response content type is JSon
		
	}
	
	/*
	 * 
	 * When I send a GET request to REST API Url http://34.223.219.142:1212/ords/hr/employees/101
	 * And accept type is JSon 
	 * Then status code is 200
	 * And response content should be JSon
	 * And first name should be "Steven"
	 */
	
	@Test
	public void verifyFirstName() {
		given().accept(ContentType.JSON)
		.when().get("http://34.223.219.142:1212/ords/hr/employees/101")
		.then().assertThat().statusCode(200)
		.and().contentType(ContentType.JSON)
		.and().assertThat().body("first_name", equalTo("Ahmet")) //edited to 'import static org.hamcrest.Matchers.*;'
		.and().assertThat().body("email", Matchers.equalTo("EM101"));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
