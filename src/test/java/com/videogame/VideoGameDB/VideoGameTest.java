package com.videogame.VideoGameDB;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.util.HashMap;
import java.util.Map;

/*  https://www.youtube.com/watch?v=dl2y2Gtjemg
   Pre-Requisite: 
   1.VideoGameDB API Download Link: https://github.com/james-willett/Vide...
   2. How to run application using mvn 
   3. Swagger URL:  http://localhost:8080/swagger-ui/inde...
 */

public class VideoGameTest {
	/**
	 * RestApi Test :-) request url -http://localhost:8080/app/videogames
	 */
	@Test
	public void verifyReturnsAlltheVideosGamesInTheDB() {
		Map<String, String> header = new HashMap<String, String>();
		header.put("Accept", "application/json");
		header.put("Content-Type", "application/json");
		String response = given().headers(header)
				.when().get("http://localhost:8080/app/videogames")
				.then().assertThat()
				.statusCode(200).extract().response().asString();
		System.out.println(response);
		JsonPath js = new JsonPath(response);
		int count = js.getInt("$.size()");
		System.out.println(count);
		
		for (int i = 0; i < count; i++) {
			System.out.println(js.getString("[" + i + "].name"));
		}

	}

	@Test
	public void verifyAddANewVideosGameInTheDB() {
		Map<String, String> header = new HashMap<String, String>();
		header.put("Accept", "application/json");
		header.put("Content-Type", "application/json");
		String VideoGameId = "60";

		String input = "{\r\n" + "  \"id\": " + VideoGameId + ",\r\n" + "  \"name\": \"Java\",\r\n"
				+ "  \"releaseDate\": \"2022-02-23T17:19:09.337Z\",\r\n" + "  \"reviewScore\": 0,\r\n"
				+ "  \"category\": \"Language\",\r\n" + "  \"rating\": \"Good\"\r\n" + "}";

		String response = given().headers(header).body(input)
				.when().post("http://localhost:8080/app/videogames")
				.then()
				.assertThat().statusCode(200).extract().response().asString();
		System.out.println(response);

		response = given().headers(header).pathParam("VideoGameId", VideoGameId)
				.when().get("http://localhost:8080/app/videogames/{VideoGameId}")
				.then().assertThat().statusCode(200).extract()
				.response().asString();
		System.out.println(response);
    }

	@Test(dataProvider="videogameid")
	public void verifyReturnsSingleVideoGameById(String VideoGameId) {
		Map<String, String> header = new HashMap<String, String>();
		header.put("Accept", "application/json");
		header.put("Content-Type", "application/json");
		//String VideoGameId = "1";
		String response = given().headers(header).pathParam("VideoGameId", VideoGameId).when()
				.get("http://localhost:8080/app/videogames/{VideoGameId}").then().assertThat().statusCode(200).extract()
				.response().asString();
		System.out.println(response);
    }
	@DataProvider(name="videogameid")
	public String[] VideoGameIdProvider() {
		String idList[] = {"1","3","10"};
		return idList;
	}

	@Test
	public void verifyDeleteVideoGameById() {
		Map<String, String> header = new HashMap<String, String>();
		header.put("Accept", "application/json");
		header.put("Content-Type", "application/json");
		String VideoGameId = "61";

		String input = "{\r\n" + "  \"id\": " + VideoGameId + ",\r\n" + "  \"name\": \"JavaScript\",\r\n"
				+ "  \"releaseDate\": \"2022-02-23T17:19:09.337Z\",\r\n" + "  \"reviewScore\": 0,\r\n"
				+ "  \"category\": \"Language\",\r\n" + "  \"rating\": \"Good\"\r\n" + "}";

		//record creation
		String response = given().headers(header).body(input).when().post("http://localhost:8080/app/videogames").then()
				.assertThat().statusCode(200).extract().response().asString();
		System.out.println(response);

		//view the record created
		response = given().headers(header).pathParam("VideoGameId", VideoGameId).when()
				.get("http://localhost:8080/app/videogames/{VideoGameId}").then().assertThat().statusCode(200).extract()
				.response().asString();
		System.out.println(response);
		
		
		//delete the record created
		response = given().headers(header).pathParam("VideoGameId", VideoGameId).when()
				.delete("http://localhost:8080/app/videogames/{VideoGameId}").then().assertThat().statusCode(200).extract()
				.response().asString();
		System.out.println(response);
		
		

		//view the record deleted
		response = given().headers(header).pathParam("VideoGameId", VideoGameId).when()
				.get("http://localhost:8080/app/videogames/{VideoGameId}").then().assertThat().statusCode(500).extract()
				.response().asString();
		System.out.println(response);

	}
	
	
	
	@Test
	public void verifyUpdateVideoGameById() {
		Map<String, String> header = new HashMap<String, String>();
		header.put("Accept", "application/json");
		header.put("Content-Type", "application/json");
		String VideoGameId = "62";

		String input1 = "{\r\n" + "  \"id\": " + VideoGameId + ",\r\n" + "  \"name\": \"JavaScript\",\r\n"
				+ "  \"releaseDate\": \"2022-02-23T17:19:09.337Z\",\r\n" + "  \"reviewScore\": 0,\r\n"
				+ "  \"category\": \"Language\",\r\n" + "  \"rating\": \"Good\"\r\n" + "}";

		//record creation
		String response = given().headers(header).body(input1).when().post("http://localhost:8080/app/videogames").then()
				.assertThat().statusCode(200).extract().response().asString();
		System.out.println("record creation*********"+response);

		//view the record created
		response = given().headers(header).pathParam("VideoGameId", VideoGameId).when()
				.get("http://localhost:8080/app/videogames/{VideoGameId}").then().assertThat().statusCode(200).extract()
				.response().asString();
		System.out.println("record viewing*********"+response);
		
		
		String input2 = "{\r\n" + "  \"id\": " + VideoGameId + ",\r\n" + "  \"name\": \"ES6\",\r\n"
				+ "  \"releaseDate\": \"2022-02-23T17:19:09.337Z\",\r\n" + "  \"reviewScore\": 0,\r\n"
				+ "  \"category\": \"Language\",\r\n" + "  \"rating\": \"Good\"\r\n" + "}"; 
		
		//update the record created
		response = given().headers(header).body(input2).pathParam("VideoGameId", VideoGameId).when()
				.put("http://localhost:8080/app/videogames/{VideoGameId}").then().assertThat().statusCode(200).extract()
				.response().asString();
		System.out.println("record updation*********"+response);
		
		

		//view the record updated
		response = given().headers(header).pathParam("VideoGameId", VideoGameId).when()
				.get("http://localhost:8080/app/videogames/{VideoGameId}").then().assertThat().statusCode(200).extract()
				.response().asString();
		System.out.println("Updated record viewing*********"+response);

	}
	
	

}
