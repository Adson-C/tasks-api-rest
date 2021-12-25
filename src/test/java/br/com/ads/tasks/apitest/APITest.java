package br.com.ads.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
	}
	
	@Test
	public void deveRetornarTarefas() {
		
		RestAssured.given() // dados , pré condições
		.when() // quando faz uma ação
		.get("/todo")
		.then() // então  faz uma acertiva
			.statusCode(200)
		;
	}
	
	@Test
	public void deveAdicionarTarefasComSucesso() {
		
		RestAssured.given()
			.body("{ \"task\": \"Teste via API\", \"dueDate\": \"2021-12-25\" }")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.statusCode(201)
		;
		
	}
	
	@Test
	public void naoDeveAdiconarTrefasInvalida() {
		
		RestAssured.given()
			.body("{ \"task\": \"Teste via API\", \"dueDate\": \"2010-12-24\" }")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.log().all()
			.statusCode(400)
			.body("message", CoreMatchers.is("Due date must not be in past"))
		;
		
	}

}