package Day10;

import static io.restassured.RestAssured.given;

import java.util.Map;

import org.json.JSONObject;
import org.testng.annotations.Test;

public class DataDrivenTesting {

	 private static final String AUTH_TOKEN = "Bearer 248297e38fe3cb47116349a5d14693ff168d125257373d93cacbb483e9636359";
	 private static final String BASE_URL =
		        "https://simple-books-api.click/orders";
	 
	 @Test(dataProvider="excelDataProvider", dataProviderClass=DataProviders.class)
	 public void testWithExcelData(String bookId, String customerName)
	 {
		 testSubmitAndDeleteOrder(bookId,customerName);
	 }
	  
	 	 
	 @Test(dataProvider="jsonDataProvider", dataProviderClass=DataProviders.class)
	 public void testWithJsonData(Map<String,String> data)
	 {
		 testSubmitAndDeleteOrder(data.get("BookID"), data.get("CustomerName"));
	 }
	  
	 
	 @Test(dataProvider="csvDataProvider", dataProviderClass=DataProviders.class)
	 public void testWithCSVData(String bookId, String customerName)
	 {
		 testSubmitAndDeleteOrder(bookId,customerName);
	 }
	  
	 
	 
	 void testSubmitAndDeleteOrder(String bookId, String customerName)
	 {
		//Submitting order
		 JSONObject requestBody=new JSONObject();
		 requestBody.put("bookId", Integer.parseInt(bookId));
		 requestBody.put("customerName", customerName);
		 
		  String orderId=given()
		  	.contentType("application/json")
		  	.header("Authorization",AUTH_TOKEN)
		  	.body(requestBody.toString())
		  	
		 .when()
		 	.post(BASE_URL)
		 .then()
		 	.statusCode(201)
		 	.log().body()
		 	.extract().jsonPath().getString("orderId");
		  
		  //Deleting order
		  given()
		  	.header("Authorization",AUTH_TOKEN)
		  	.pathParam("orderId", orderId)
		  	
		  .when()
		  		.delete(BASE_URL+"/{orderId}")
		  .then()
		  	.statusCode(204);
	 }
	
		 
}
