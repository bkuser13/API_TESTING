package tests;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertThat;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import utilities.ConfigurationReader;

public class API_GSON {

	@Test
	public void testWithBasicHsonToHashmap() {

		Response response = given().accept(ContentType.JSON).when()
				.get(ConfigurationReader.getProperty("url") + "/employees/9413");

		// take body and convert to HashMap using as method
		Map<String, String> map = response.as(HashMap.class); // converting from json to Java (de-serialization)
		System.out.println(map); // all response was converted to hashmap
		System.out.println(map.values());
		assertEquals(map.get("employee_id"), 9413.0);
		assertEquals(map.get("last_name"), "Abduwayit");
	}

	@Test
	public void convertJsonTOLIstOfMaps() {
		Response response = given().accept(ContentType.JSON).when()
				.get(ConfigurationReader.getProperty("url") + "/departments");

		// convert the response that contains department information into list of maps
		// List<Map<String, String>>
		List<Map> listOfMaps = response.jsonPath().getList("items", Map.class);
		// below we converted response to jsonPath and then get the lsit of all items
		// and put it map
		System.out.println(listOfMaps.get(0));

		// assert that first department name is Administration
		assertEquals(listOfMaps.get(0).get("department_name"), "IT department");

	}

	/*
	 * EX: Given content type is Json and limit is 10 when i send request to restAPI
	 * URL http://34.223.219.142:1212/ords/hr/regions then i should see all
	 * countries
	 */

	@Test
	public void testRegions() {
		Response response = given().accept(ContentType.JSON).and().params("limit", 10).when()
				.get(ConfigurationReader.getProperty("url") + "/regions");

		assertEquals(response.statusCode(), 200);
		JsonPath json = response.jsonPath();

		// get first country name and its id number and assert
		assertEquals(json.getInt("items[0].region_id"), 11111);
		assertEquals(json.getString("items[0].region_name"), "Ahmet's region");

		// get first country name and its id number and assert
		assertEquals(json.getInt("items[1].region_id"), 111111);
		assertEquals(json.getString("items[1].region_name"), "Ahmet's region");

	}

	@Test
	public void testRegionsV2() {
		Response response = given().accept(ContentType.JSON).and().params("limit", 10).when()
				.get(ConfigurationReader.getProperty("url") + "/regions");

		assertEquals(response.statusCode(), 200);

		// Store into jsonPath -> list<map>
		JsonPath json = response.jsonPath();

		// deserialize json to list<map>
		List<Map> regions = json.getList("items", Map.class);

		// 1st) we can create map and get from regions by index and store it inside map
		Map r = regions.get(0);
		System.out.println(r.get("region_name"));

		// 2nd way
		Map<Integer, String> expectedRegions = new HashMap<>();
		expectedRegions.put(1, "Ahmet's region");
		expectedRegions.put(2, "Ahmet's Region");

		for (Integer regionID : expectedRegions.keySet()) {
			System.out.println("Looking for region: " + regionID);

			for (Map map : regions) {
				if (map.get("region_id") == regionID) {
					assertEquals(map.get("region_name"), expectedRegions.get(regionID));
				}
			}
		}

	}

	@Test
	public void testRegionsV3() {
		Response response = given().accept(ContentType.JSON).and().params("limit", 10).when()
				.get(ConfigurationReader.getProperty("url") + "/regions");
		assertEquals(response.statusCode(), 200);
		JsonPath json = response.jsonPath();
		
		List<String> regionNames = new ArrayList<>();
		
		for(Object item:json.getList("items")) {
			regionNames.add(((HashMap) item).get("region_id").toString()+" "+((HashMap) item).get("region_name").toString());
			
		}
		
		
		
	}

}
