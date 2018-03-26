package br.com.testAPI.maxMilhas.TesteAPI;

import static org.junit.Assert.*;

import java.awt.event.ItemEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import static org.hamcrest.Matchers.*;


public class suiteTestAPI {

	public suiteTestAPI() {
		baseURI = "https://flight-pricing-hmg.maxmilhas.com.br";
	}

	private String getDateTime() { 
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		Date date = new Date(); 
		return dateFormat.format(date); 
	}
	
	@Test
	/*Test to validate a Search with Infant Greater than Adult*/
	public void testAdultsGreaterInfantsValidation() {
		String myJson = "{\"tripType\":\"RT\",\"from\":\"CNF\",\"to\":\"GRU\",\"adults\":1,\"children\":0,\"infants\":2,\"outboundDate\":\"2018-05-01\",\"inboundDate\":\"2018-05-21\",\"cabin\":\"EC\"}";
		
		given()
			.contentType("application/json")
			.headers("Authorization","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJtYXhtaWxoYXMuY29tLmJyIiwiaWF0IjoxNTIxNTc4MTg3LCJleHAiOjE1MjQyNTY1ODgsImF1ZCI6InRlc3RlLXFhIiwic3ViIjoidGVzdGUtcWEiLCJlbnYiOiJobWcifQ.-Q04QmHo_Hoy3rq0y3V3hhyIeqdzqTlo27rLkhAOI7s")
			.body(myJson)
		.when()
			.post("/search")
		.then()
			.statusCode(422)
			.body("type", containsString("AdultsGreaterInfantsValidationError"))
			.body("message", containsString("The number of adults must be greater than that of infants"));
	}

	@Test
	/*Test to validate a Search with Adult quantity Greater than nine (9)*/
	public void testPassengersQuantityValidation() {
		String myJson = "{\"tripType\":\"RT\",\"from\":\"CNF\",\"to\":\"GRU\",\"adults\":10,\"children\":0,\"infants\":0,\"outboundDate\":\"2018-05-01\",\"inboundDate\":\"2018-05-21\",\"cabin\":\"EC\"}";
		
		given()
			.contentType("application/json")
			.headers("Authorization","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJtYXhtaWxoYXMuY29tLmJyIiwiaWF0IjoxNTIxNTc4MTg3LCJleHAiOjE1MjQyNTY1ODgsImF1ZCI6InRlc3RlLXFhIiwic3ViIjoidGVzdGUtcWEiLCJlbnYiOiJobWcifQ.-Q04QmHo_Hoy3rq0y3V3hhyIeqdzqTlo27rLkhAOI7s")
			.body(myJson)
		.when()
			.post("/search")
		.then()
			.statusCode(422)
			.body("type", containsString("PassengersQuantityValidationError"))
			.body("message", containsString("The total number of passengers exceeds the maximum 9 allowed"));
	}
	
	@Test
	/*Test to validate a Search with Airport(from) code invalid*/
	public void testResourceNotFoundFrom() {
		String myJson = "{\"tripType\":\"RT\",\"from\":\"AAA\",\"to\":\"GRU\",\"adults\":1,\"children\":0,\"infants\":0,\"outboundDate\":\"2018-05-01\",\"inboundDate\":\"2018-05-21\",\"cabin\":\"EC\"}";
		
		given()
			.contentType("application/json")
			.headers("Authorization","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJtYXhtaWxoYXMuY29tLmJyIiwiaWF0IjoxNTIxNTc4MTg3LCJleHAiOjE1MjQyNTY1ODgsImF1ZCI6InRlc3RlLXFhIiwic3ViIjoidGVzdGUtcWEiLCJlbnYiOiJobWcifQ.-Q04QmHo_Hoy3rq0y3V3hhyIeqdzqTlo27rLkhAOI7s")
			.body(myJson)
		.when()
			.post("/search")
		.then()
			.statusCode(404)
			.body("type", containsString("ResourceNotFoundError"))
			.body("message", containsString("Could not create search resource due to some incorrect parameter."));
	}
	
	@Test
	/*
	Test to validate a Search with Airport(To) code invalid*/
	public void testResourceNotFoundTo() {
		String myJson = "{\"tripType\":\"RT\",\"from\":\"GRU\",\"to\":\"AAA\",\"adults\":1,\"children\":0,\"infants\":0,\"outboundDate\":\"2018-05-01\",\"inboundDate\":\"2018-05-21\",\"cabin\":\"EC\"}";
		
		given()
			.contentType("application/json")
			.headers("Authorization","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJtYXhtaWxoYXMuY29tLmJyIiwiaWF0IjoxNTIxNTc4MTg3LCJleHAiOjE1MjQyNTY1ODgsImF1ZCI6InRlc3RlLXFhIiwic3ViIjoidGVzdGUtcWEiLCJlbnYiOiJobWcifQ.-Q04QmHo_Hoy3rq0y3V3hhyIeqdzqTlo27rLkhAOI7s")
			.body(myJson)
		.when()
			.post("/search")
		.then()
			.statusCode(404)
			.body("type", containsString("ResourceNotFoundError"))
			.body("message", containsString("Could not create search resource due to some incorrect parameter."));
	}
	
	
	@Test
	/*
	Test to validate a Search with unavailability of Business Class in Avianca*/
	public void testUnavailableAviacaBusinessClass() {
		String myJson = "{\"tripType\":\"RT\",\"from\":\"CNF\",\"to\":\"GRU\",\"adults\":1,\"children\":0,\"infants\":0,\"outboundDate\":\"2018-05-01\",\"inboundDate\":\"2018-05-21\",\"cabin\":\"EX\"}";
		
		given()
			.contentType("application/json")
			.headers("Authorization","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJtYXhtaWxoYXMuY29tLmJyIiwiaWF0IjoxNTIxNTc4MTg3LCJleHAiOjE1MjQyNTY1ODgsImF1ZCI6InRlc3RlLXFhIiwic3ViIjoidGVzdGUtcWEiLCJlbnYiOiJobWcifQ.-Q04QmHo_Hoy3rq0y3V3hhyIeqdzqTlo27rLkhAOI7s")
			.body(myJson)
		.when()
			.post("/search")
		.then()
			.statusCode(200)
			.body("airlines.label", hasItems("avianca"))
			.body("airlines.status.message", hasItems("Indisponível para classe executiva"));
	}
	
	@Test
	/*
	Test to validate a Search with current date*/
	public void testSearchCurrentDate() {
		String currentDate = getDateTime();
		String myJson = "{\"tripType\":\"RT\",\"from\":\"CNF\",\"to\":\"GRU\",\"adults\":1,\"children\":0,\"infants\":0,\"outboundDate\":\"" + currentDate +"\",\"inboundDate\":\""+ currentDate +"\",\"cabin\":\"EC\"}";
		
		
		given()
			.contentType("application/json")
			.headers("Authorization","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJtYXhtaWxoYXMuY29tLmJyIiwiaWF0IjoxNTIxNTc4MTg3LCJleHAiOjE1MjQyNTY1ODgsImF1ZCI6InRlc3RlLXFhIiwic3ViIjoidGVzdGUtcWEiLCJlbnYiOiJobWcifQ.-Q04QmHo_Hoy3rq0y3V3hhyIeqdzqTlo27rLkhAOI7s")
			.body(myJson)
		.when()
			.post("/search")
		.then()
			.statusCode(200)
			.body("airlines.label", hasItems("gol"))
			.body("airlines.status.message", hasItems("Somente partidas superiores a 24 horas"));
	}
}
