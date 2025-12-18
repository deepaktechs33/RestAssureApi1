package Day11;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;

/**
 * GraphQL Testing with REST Assured
 * JSON Converter: https://datafetcher.com/graphql-json-body-converter
 * GraphQL queries must be wrapped in JSON format.
 */
//eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Ik9FWTJSVGM1UlVOR05qSXhSRUV5TURJNFFUWXdNekZETWtReU1EQXdSVUV4UVVRM05EazFNQSJ9.eyJodHRwczovL2hhc3VyYS5pby9qd3QvY2xhaW1zIjp7IngtaGFzdXJhLWRlZmF1bHQtcm9sZSI6InVzZXIiLCJ4LWhhc3VyYS1hbGxvd2VkLXJvbGVzIjpbInVzZXIiXSwieC1oYXN1cmEtdXNlci1pZCI6ImF1dGgwfDY5MzI4NWIxY2QzYTJhOTg4MGI4YjAyNSJ9LCJuaWNrbmFtZSI6ImRlZXBha3RlY2g2IiwibmFtZSI6ImRlZXBha3RlY2g2QGdtYWlsLmNvbSIsInBpY3R1cmUiOiJodHRwczovL3MuZ3JhdmF0YXIuY29tL2F2YXRhci80MzZmMmEyODM4YjkzM2U5NDYzY2RjZWFkNDRiZDg3Mj9zPTQ4MCZyPXBnJmQ9aHR0cHMlM0ElMkYlMkZjZG4uYXV0aDAuY29tJTJGYXZhdGFycyUyRmRlLnBuZyIsInVwZGF0ZWRfYXQiOiIyMDI1LTEyLTE2VDE5OjUyOjUzLjUxMFoiLCJpc3MiOiJodHRwczovL2dyYXBocWwtdHV0b3JpYWxzLmF1dGgwLmNvbS8iLCJhdWQiOiJQMzhxbkZvMWxGQVFKcnprdW4tLXdFenFsalZOR2NXVyIsInN1YiI6ImF1dGgwfDY5MzI4NWIxY2QzYTJhOTg4MGI4YjAyNSIsImlhdCI6MTc2NTk0ODExNCwiZXhwIjoxNzY1OTg0MTE0LCJzaWQiOiJqTjUwX2hhU0pFeFB6TE1zN3V6cHZlV20ySWFscGdDOSIsImF0X2hhc2giOiJ2R2FrSmJqc2l3cWtSalJTWnNKUWF3Iiwibm9uY2UiOiJOeHpxdDZZTE01LThhRzA3UEJhS1NoOXhvb0pYd2FzRiJ9.e9b9G4O97B2bpf3Q9ifs2CLrn6rbTbawRuG2DZZjaFzhy0Tn1JbBv85T9xCFXCBeZi190zHvz0JVQ2-zMuiN1sInkS93qx0YdTygaBMsdopSt5J26Mi29PvQikDMejwpm1G_X5JtDyj13hvVDk4JYh2AY1W3C0He9NXErOrY4SVazU9psq4B9inSgtvQS1zKWVxjqBcVBaLpLGcGknaIjAZ-Thpq1AAUF7_A4selP0lEhjDkvUYgSUYkC74GZKCRMp5h9oWy7FYlLR8G_l3II23Bi6yQ9XSFfyFm5kv0TRhRzI2CZKwzD_QM5Ulsk9MoGWzm0XFcWIMpVOqfY6OcFw

public class GraphQL_QueryTests {

	private static final String BASE_URL =
	        "https://hasura.io/learn/graphql";

	   private static final String AUTH_TOKEN = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Ik9FWTJSVGM1UlVOR05qSXhSRUV5TURJNFFUWXdNekZETWtReU1EQXdSVUV4UVVRM05EazFNQSJ9.eyJodHRwczovL2hhc3VyYS5pby9qd3QvY2xhaW1zIjp7IngtaGFzdXJhLWRlZmF1bHQtcm9sZSI6InVzZXIiLCJ4LWhhc3VyYS1hbGxvd2VkLXJvbGVzIjpbInVzZXIiXSwieC1oYXN1cmEtdXNlci1pZCI6ImF1dGgwfDY5MzI4NWIxY2QzYTJhOTg4MGI4YjAyNSJ9LCJuaWNrbmFtZSI6ImRlZXBha3RlY2g2IiwibmFtZSI6ImRlZXBha3RlY2g2QGdtYWlsLmNvbSIsInBpY3R1cmUiOiJodHRwczovL3MuZ3JhdmF0YXIuY29tL2F2YXRhci80MzZmMmEyODM4YjkzM2U5NDYzY2RjZWFkNDRiZDg3Mj9zPTQ4MCZyPXBnJmQ9aHR0cHMlM0ElMkYlMkZjZG4uYXV0aDAuY29tJTJGYXZhdGFycyUyRmRlLnBuZyIsInVwZGF0ZWRfYXQiOiIyMDI1LTEyLTE2VDE5OjUyOjUzLjUxMFoiLCJpc3MiOiJodHRwczovL2dyYXBocWwtdHV0b3JpYWxzLmF1dGgwLmNvbS8iLCJhdWQiOiJQMzhxbkZvMWxGQVFKcnprdW4tLXdFenFsalZOR2NXVyIsInN1YiI6ImF1dGgwfDY5MzI4NWIxY2QzYTJhOTg4MGI4YjAyNSIsImlhdCI6MTc2NTk0ODExNCwiZXhwIjoxNzY1OTg0MTE0LCJzaWQiOiJqTjUwX2hhU0pFeFB6TE1zN3V6cHZlV20ySWFscGdDOSIsImF0X2hhc2giOiJ2R2FrSmJqc2l3cWtSalJTWnNKUWF3Iiwibm9uY2UiOiJOeHpxdDZZTE01LThhRzA3UEJhS1NoOXhvb0pYd2FzRiJ9.e9b9G4O97B2bpf3Q9ifs2CLrn6rbTbawRuG2DZZjaFzhy0Tn1JbBv85T9xCFXCBeZi190zHvz0JVQ2-zMuiN1sInkS93qx0YdTygaBMsdopSt5J26Mi29PvQikDMejwpm1G_X5JtDyj13hvVDk4JYh2AY1W3C0He9NXErOrY4SVazU9psq4B9inSgtvQS1zKWVxjqBcVBaLpLGcGknaIjAZ-Thpq1AAUF7_A4selP0lEhjDkvUYgSUYkC74GZKCRMp5h9oWy7FYlLR8G_l3II23Bi6yQ9XSFfyFm5kv0TRhRzI2CZKwzD_QM5Ulsk9MoGWzm0XFcWIMpVOqfY6OcFw";
	
	   //Fetch Users and Their Todos
	
	  @Test
	   public void testFetchUsers() {

	      String body= "{\r\n"
	      		+ "  \"query\": \"{ todos { id title user { id name } }}\"\r\n"
	      		+ "}";
	      

	       given()
	           .contentType(ContentType.JSON)
	           .accept(ContentType.JSON)
	           .header("Authorization", AUTH_TOKEN)
	           .redirects().follow(false)
	           .body(body)
	       .when()
	           .post(BASE_URL)
	       .then()
	           .log().all()
	           .statusCode(200);
	   }
	   
	 //Fetch Limited Todos
	   @Test(priority=2)
	   public void testFetchLimitedTodos()
	   {
		   String graphqlQuery="{\r\n"
		   		+ "  \"query\": \"query { todos(limit: 5) { id title } } \"\r\n"
		   		+ "}";
		   		   
		   given()
		   		.contentType(ContentType.JSON) //.contentType("application/json")
		   		.header("Authorization",AUTH_TOKEN)
		   		.body(graphqlQuery)
		   	.when()	
		   		.post(BASE_URL)
		   
		   .then()
		   		.statusCode(200)
		   		.body("data.todos", hasSize(lessThanOrEqualTo(5)))
		   		.body("data.todos[0].id", notNullValue())
		   		.body("data.todos[0].title", notNullValue())
		   		.log().body();
		   		
	   }
	   //Fetch Users with Recent Todos
	  @Test(priority=3)
	   public void testFetchUsersWithRecentTodos()
	   {
		   String graphqlQuery="{\r\n"
		   		+ "  \"query\": \"query { users(limit: 2) { id name todos(order_by: {created_at: desc}, limit: 5) { id title } } } \"\r\n"
		   		+ "}";
		   		   
		   given()
		   		.contentType(ContentType.JSON) //.contentType("application/json")
		   		.header("Authorization",AUTH_TOKEN)
		   		.body(graphqlQuery)
		   	.when()	
		   		.post(BASE_URL)
		   
		   .then()
		   		.statusCode(200)
		   		.body("data.users", hasSize(2))
		   		.body("data.users[0].todos", hasSize(lessThanOrEqualTo(1)))
		   		.body("data.users[0].name",notNullValue())
		   		.log().body();
		   		
	   }
	   
	   
	 //Fetch Todos Using Variables
	  @Test(priority=4)
	   public void testFetchTodosWithVariables()
	   {
		   String graphqlQuery="{\r\n"
		   		+ "  \"query\": \"query ($limit: Int!) { todos(limit: $limit) { id title } } \",\r\n"
		   		+ "  \"variables\": {\r\n"
		   		+ "    \"limit\": 5\r\n"
		   		+ "  }\r\n"
		   		+ "}";
		   		   
		   given()
		   		.contentType(ContentType.JSON) //.contentType("application/json")
		   		.header("Authorization",AUTH_TOKEN)
		   		.body(graphqlQuery)
		   	.when()	
		   		.post(BASE_URL)
		   
		   .then()
		   		.statusCode(200)
		   		.body("data.todos", hasSize(5))
	            .body("data.todos[0].id", notNullValue())
	            .body("data.todos[0].title", notNullValue())
		   		.log().body();
		   		
	   }
	   
	   
	   @Test(priority=5)
	   public void testFetchPublicTodos()
	   {
		   String graphqlQuery="{\r\n"
		   		+ "  \"query\": \"query { todos(where: {is_public: {_eq: true}}) { title is_public is_completed } } \"\r\n"
		   		+ "}";
		   		   
		   given()
		   		.contentType(ContentType.JSON) //.contentType("application/json")
		   		.header("Authorization",AUTH_TOKEN)
		   		.body(graphqlQuery)
		   	.when()	
		   		.post(BASE_URL)
		   
		   .then()
		   		.statusCode(200)
		   		.body("data.todos", hasSize(greaterThan(0)))
	            .body("data.todos[0].is_public",equalTo(true))
	            .body("data.todos[0].is_completed", notNullValue())
		   		.log().body();
		   		
	   }

	   
	   
	   
}





