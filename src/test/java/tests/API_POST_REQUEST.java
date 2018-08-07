package tests;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import beans.Country;
import beans.Region;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utilities.ConfigurationReader;

public class API_POST_REQUEST {

	/*
	 * POST SCENARIO: Given content type is JSON And Accept type is Json When I send
	 * POST request to url With request body: { "region_id" : 5, "region_name" :
	 * "Fekir's region" } Then status code should be 201 And response body should
	 * match request body
	 */

	@Test
	public void postNewRegion() {
		String url = ConfigurationReader.getProperty("url") + "/regions/";
		// String requestJson= " {\"region_id\" : 9999, \"region_name\" : \"Bolot's
		// region\" }" ;

		Map requestMap = new HashMap<>();
		requestMap.put("region_id", 9992);
		requestMap.put("region_name", "10 region");

		Response response = given().accept(ContentType.JSON).and().contentType(ContentType.JSON).and().body(requestMap)
				.when().post(url);

		System.out.println(response.statusLine());
		response.prettyPrint();

		// assert that status code is 201
		// assert that body should match request body
		assertEquals(response.statusCode(), 201);
		Map responseMap = response.body().as(Map.class);

		assertEquals(responseMap.get("region_id"), requestMap.get("region_id"));
		assertEquals(responseMap.get("region_name"), requestMap.get("region_name"));
	}

	@Test
	public void postRegionUsingPOJO() {
		String url = ConfigurationReader.getProperty("url") + "/regions/";
		Region region = new Region();
		region.setRegionId(9990);
		region.setRegionName("9's region");

		Response response = given().accept(ContentType.JSON).and().contentType(ContentType.JSON).and().body(region)
				.when().post(url);

		assertEquals(response.statusCode(), 201);

		Region responseRegion = response.body().as(Region.class); //

		// and response body should match request body
		assertEquals(responseRegion.getRegionId(), region.getRegionId());
		assertEquals(responseRegion.getRegionName(), region.getRegionName());

	}

	/*
	 * Given content type is JSON 
	 * And Accept type is Json 
	 * When I send POST request to url(countries)
	 * With request body: 
	 * { "country_id" : 111, 
	 * "region_name" : "Obama's Country",
	 * "region_id: 9989"
	 * } 
	 * Then status code should be 201 
	 * And response body should match request body
	 */

	@Test
	public void postCountryUsingPOJO() {
		String url = ConfigurationReader.getProperty("url") + "/countries/";
		Country reqCountry = new Country();
		reqCountry.setCountryId("QQ");
		reqCountry.setCountryName("Obama's Country");
		reqCountry.setRegionId(328);
		
		Response response = given().log().all()
							.accept(ContentType.JSON)
							.and().contentType(ContentType.JSON)
							.and().body(reqCountry)
							.when().post(url);
		assertEquals(response.statusCode(), 201);
		
		Country resCountry = response.body().as(Country.class);
		
		assertEquals(resCountry.getCountryId(), reqCountry.getCountryId());
		assertEquals(resCountry.getRegionId(), reqCountry.getRegionId());
		assertEquals(resCountry.getCountryName(), reqCountry.getCountryName());
	}



















}
