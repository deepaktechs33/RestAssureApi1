package Day11;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.hamcrest.MatcherAssert.assertThat;


public class GraphQL_MutationTests {

	  private static final String BASE_URL = "https://hasura.io/learn/graphql";
	   private static final String AUTH_TOKEN = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Ik9FWTJSVGM1UlVOR05qSXhSRUV5TURJNFFUWXdNekZETWtReU1EQXdSVUV4UVVRM05EazFNQSJ9.eyJodHRwczovL2hhc3VyYS5pby9qd3QvY2xhaW1zIjp7IngtaGFzdXJhLWRlZmF1bHQtcm9sZSI6InVzZXIiLCJ4LWhhc3VyYS1hbGxvd2VkLXJvbGVzIjpbInVzZXIiXSwieC1oYXN1cmEtdXNlci1pZCI6ImF1dGgwfDY5MzI4NWIxY2QzYTJhOTg4MGI4YjAyNSJ9LCJuaWNrbmFtZSI6ImRlZXBha3RlY2g2IiwibmFtZSI6ImRlZXBha3RlY2g2QGdtYWlsLmNvbSIsInBpY3R1cmUiOiJodHRwczovL3MuZ3JhdmF0YXIuY29tL2F2YXRhci80MzZmMmEyODM4YjkzM2U5NDYzY2RjZWFkNDRiZDg3Mj9zPTQ4MCZyPXBnJmQ9aHR0cHMlM0ElMkYlMkZjZG4uYXV0aDAuY29tJTJGYXZhdGFycyUyRmRlLnBuZyIsInVwZGF0ZWRfYXQiOiIyMDI1LTEyLTE2VDE5OjUyOjUzLjUxMFoiLCJpc3MiOiJodHRwczovL2dyYXBocWwtdHV0b3JpYWxzLmF1dGgwLmNvbS8iLCJhdWQiOiJQMzhxbkZvMWxGQVFKcnprdW4tLXdFenFsalZOR2NXVyIsInN1YiI6ImF1dGgwfDY5MzI4NWIxY2QzYTJhOTg4MGI4YjAyNSIsImlhdCI6MTc2NTk0ODExNCwiZXhwIjoxNzY1OTg0MTE0LCJzaWQiOiJqTjUwX2hhU0pFeFB6TE1zN3V6cHZlV20ySWFscGdDOSIsImF0X2hhc2giOiJ2R2FrSmJqc2l3cWtSalJTWnNKUWF3Iiwibm9uY2UiOiJOeHpxdDZZTE01LThhRzA3UEJhS1NoOXhvb0pYd2FzRiJ9.e9b9G4O97B2bpf3Q9ifs2CLrn6rbTbawRuG2DZZjaFzhy0Tn1JbBv85T9xCFXCBeZi190zHvz0JVQ2-zMuiN1sInkS93qx0YdTygaBMsdopSt5J26Mi29PvQikDMejwpm1G_X5JtDyj13hvVDk4JYh2AY1W3C0He9NXErOrY4SVazU9psq4B9inSgtvQS1zKWVxjqBcVBaLpLGcGknaIjAZ-Thpq1AAUF7_A4selP0lEhjDkvUYgSUYkC74GZKCRMp5h9oWy7FYlLR8G_l3II23Bi6yQ9XSFfyFm5kv0TRhRzI2CZKwzD_QM5Ulsk9MoGWzm0XFcWIMpVOqfY6OcFw";
	
	   static int insertedTodoId;
	   
	   @Test(priority=1)
	   public void testInsertToDo()
	   {
		   String insertMutation="{\r\n"
		   		+ "  \"query\": \"mutation { insert_todos(objects: [{title: \\\"sdet\\\"}]) { affected_rows returning { id created_at title } } } \"\r\n"
		   		+ "}";
			   
			   
			   Response response=given()
			   		.contentType(ContentType.JSON) //.contentType("application/json")
			   		.header("Authorization",AUTH_TOKEN)
			   		.body(insertMutation)
			   	.when()	
			   		.post(BASE_URL)
			   
			   .then()
			   		.statusCode(200)
			   		.extract().response();
			   
			   //Extract the response body
			   String responseBody=response.body().asString();
			   System.out.println("Insert mutation response:  "+responseBody);
			   			   
			   //Extract the ID of the inserted to (use it for updation and deletion)
			   insertedTodoId=response.jsonPath().getInt("data.insert_todos.returning[0].id");
			   System.out.println("Inserted to do ID: "+ insertedTodoId);
			   
			   //Validate that the title of teh inserted to do is "sdet"
			   String insertedTitle=response.jsonPath().getString("data.insert_todos.returning[0].title");
			   assertThat(insertedTitle, is("sdet"));
			   
	   }
	   
	   
	   @Test(dependsOnMethods="testInsertToDo")
	  public void testUpdateTodo()
	  {
		  String updateMutation="{\r\n"
		  		+ "  \"query\": \"mutation { update_todos(where: {id: {_eq: "+insertedTodoId+"}}, _set: {title: \\\"sdetqa\\\", is_completed: true}) { affected_rows returning { id title is_completed } } } \"\r\n"
		  		+ "}";
		  
		  Response response=given()
		  	.contentType(ContentType.JSON) //.contentType("application/json")
	   		.header("Authorization",AUTH_TOKEN)
	   		.body(updateMutation)
		  .when()
		  		.post(BASE_URL)
		  .then()
		  	.statusCode(200)
		  	.extract().response();
		  
		  //Extract and print updated body
		  String responseBody=response.body().asString();
		  System.out.println(responseBody);
		  
		  //validate title is updated or not
		  String updatedTitle=response.jsonPath().getString("data.update_todos.returning[0].title");
		  boolean isCompleted=response.jsonPath().getBoolean("data.update_todos.returning[0].is_completed");
		  
		  assertThat(updatedTitle, is("sdetqa"));
		  assertThat(isCompleted, is(true));
		  
	  }
	   
	   

	   @Test(dependsOnMethods="testUpdateTodo")
	    public void testDeleteTodo() {
	       
	    	String deleteMutation = "{\r\n"
	    			+ "  \"query\": \"mutation { delete_todos(where: {id: {_eq:"+insertedTodoId+"} }) { affected_rows returning { title } } } \"\r\n"
	    			+ "}";

	        // Send the request and validate the response
	        Response response = 
	        		
	        		given()
	                 .contentType(ContentType.JSON)
		                .header("Authorization", AUTH_TOKEN)
		                .body(deleteMutation)
		             .when()
	                	.post(BASE_URL)
	                .then()
	            		.statusCode(200)
	            		.extract().response();

	        // Extract the response body
	        String responseBody = response.body().asString();
	        System.out.println("Delete Response: " + responseBody);

	        // Validate that the todo is deleted
	        int affectedRows = response.jsonPath().getInt("data.delete_todos.affected_rows");
	        assertThat(affectedRows, is(1));
	    }
	   
	   	   
	   
}
