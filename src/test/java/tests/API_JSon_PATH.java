package tests;

import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.*;

public class API_JSon_PATH {

	/*
	 * Given Accept type is JSon
	 * When I send a GET request to REST API Url http://34.223.219.142:1212/ords/hr/regions
	 * Then status code is 200
	 * And response content should be JSon
	 * And for regions should be returned
	 * And Americas is one of the region name
	 */
	
	@Test
	public void verifyRegions() {
		given().accept(ContentType.JSON)
		.when().get("http://34.223.219.142:1212/ords/hr/regions")
		.then().assertThat().statusCode(200)
		.and().contentType(ContentType.JSON)
		.and().assertThat().body("items.region_name", hasItems("Ahmet's region", "NOT Murodil's Region"));
	}
	
	/*
	 * Given Accept type is 200
	 * And Params are limit 100
	 * WHen I send get request to URL "http://34.223.219.142:1212/ords/hr/employee
	 * Then status code is 200
	 * And response content should be JSon
	 * And employee_id data shoudl be in json response body
	 */
	@Test
	public void verifyWithQueryParameter() {
		given().accept(ContentType.JSON)
		.and().params("limit", 100)
		.when().get("http://34.223.219.142:1212/ords/hr/employees")
		.then().contentType(ContentType.JSON)
		.and().assertThat().statusCode(200)
		.and().assertThat().body("items.employee_id", hasSize(100));
		
	}

	/*
	 * Given Accept type is Json
	 * And Params are limit 100
	 * And path param id 110
	 * WHen I send get request to URL "http://34.223.219.142:1212/ords/hr/employee/110"
	 * Then status code is 200
	 * And response content should be JSon
	 * And folowing data should be returned:
	 * "employee_id": 110,
    	"first_name": "Ahmet",
    	"last_name": "Turkkahraman",
    	"email": "EM110",
	 */
	@Test
	public void verifyWithPathParameter() {
		given().accept(ContentType.JSON)
		.and().param("limit", 100)
		.and().pathParam("employee_id", 110)
		.when().get("http://34.223.219.142:1212/ords/hr/employees/{employee_id}")
		.then().statusCode(200)
		.and().contentType(ContentType.JSON)
		.and().assertThat().body("employee_id", equalTo(110), 
								"first_name", equalTo("Ahmet"),
								"last_name", equalTo("Turkkahraman"),
								"email", equalTo("EM110"));
	}
	
	
	
	/*
	 * Given Accept type Json
	 * And Params are limit 100
	 * And path param id 110
	 * WHen I send get request to URL "http://34.223.219.142:1212/ords/hr/employee"
	 * Then status code is 200
	 * And response content should be JSon
	 * And all employee_id should be returned
	 */
	@Test
	public void testWithJsonPath() {
		
		Map<String, Integer> rParamMap = new HashMap<>();
		rParamMap.put("limit", 100);
		
		Response response = given().accept(ContentType.JSON) //header
							.and().params(rParamMap) //query param/request param
							.and().pathParam("employee_id", 7222)
							.when().get(ConfigurationReader.getProperty("url")+"/employees/{employee_id}");
		JsonPath json = response.jsonPath(); //get Json body and assign to jsonPath object
		
		System.out.println(json.getInt("employee_id"));
		System.out.println(json.getString("first_name"));
		System.out.println(json.getString("last_name"));
		System.out.println(json.getString("job_id"));
		System.out.println(json.getString("salary"));
		System.out.println(json.getString("links[1].href")); // get specific element from array
		//assign all href into  a list of strings
		List<String>hrefs = json.getList("links.href");
		
		
	}
	/*
	 * Given Accept type Json
	 * And Params are limit 100
	 * WHen I send get request to URL "http://34.223.219.142:1212/ords/hr/employee"
	 * Then status code is 200
	 * And response content should be JSon
	 * And all employee data should be returned
	 */
	
	@Test
	public void testJsonPathWithLists() {
		
		Map<String, Integer> rParamMap = new HashMap<>();
		rParamMap.put("limit", 100);
		
		Response response = given().accept(ContentType.JSON)
							.and().params(rParamMap)
							.when().get(ConfigurationReader.getProperty("url")+"/employees");
		
		assertEquals(response.statusCode(), 200); //check status code
		JsonPath json = response.jsonPath(); //1st way
		//JsonPath json = new JsonPath(response.asString()); //2nd way
		
		//get all empl_ids into an arrayList
		List<Integer> empIds = json.getList("items.employee_id");
		System.out.println("Employee id count: "+empIds.size());
		assertEquals(empIds.size(), 100);
		
		//get all email and assign to arraylist
		List<String> empEmail = json.getList("items.email");
		System.out.println("Employee emails count: "+empEmail.size());
		assertEquals(empEmail.size(), 100);
		
		
		//get all empls ids that are greater than 150
		

		List<String> empIDList = json.getList("items.findAll{it.employee_id>150}.employee_id");
		System.out.println(empIDList);
				
		
		
		//get all employee last_names whose salary is mote than 7000
		List<String> empSalary =  json.getList("items.findAll{it.salary>7000}.last_name");
		System.out.println(empSalary);
		
		//get all empls ids storing json result in postman into file and reading from that file
		JsonPath jsonFromFile = new JsonPath(new File("/Users/admin/Downloads/employees.json"));
		List<String> empIDList2 = jsonFromFile.getList("items.email");
		System.out.println(empIDList2);
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
}
