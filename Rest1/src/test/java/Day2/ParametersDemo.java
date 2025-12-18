package Day2;


import static io.restassured.RestAssured.*;

import org.testng.annotations.Test;


public class ParametersDemo {
//
//	@Test
//	void pathParams()
//	{
//		given()
//			.pathParam("country","India")   // path parameter
//		.when()
//				.get("https://restcountries.com/v2/name/{country}")   //URL: https://restcountries.com/v2/name/India
//		.then()
//			.statusCode(200)
//			.log().body();
//	}
	
	
	@Test
	void queryParams()
	{	
		given()
			.queryParam("page", 2)
			.queryParam("id",5)
		
		.when()
				.get("https://reqres.in/api/users")  //URL: https://reqres.in/api/users?page=2&id=5
		.then()
			.statusCode(200)
			.log().body();
		
	}
	
	
	
}
