package Day3;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class AuthenticationTests {


//@Test
void verifyBasicAuth()
{
	given()
		.auth().basic("postman","password")
	.when()
		.get("https://postman-echo.com/basic-auth")
	.then()
		.statusCode(200)
		.body("authenticated", equalTo(true))
		.log().body();
}


//2. Basic Preemptive Authentication.

//@Test
void verifyPreemtiveAuth()
{
	given()
		.auth().preemptive().basic("postman", "password")
	.when()
		.get("https://postman-echo.com/basic-auth")
	.then()
		.statusCode(200)
		.body("authenticated", equalTo(true))
		.log().body();
}

//3. Digest Authentication.

	//@Test
	void verifyDigestAuth()
	{
		given()
			.auth().digest("postman","password")
		.when()
			.get("https://postman-echo.com/basic-auth")
		.then()
			.statusCode(200)
			.body("authenticated", equalTo(true))
			.log().body();
	}
	
//4. Bearer token authentication
	
	//@Test
	void verifyTokenAuth()
	{
        // Generate your token from GitHub account settings -> Developer settings -> Personal access tokens
		String bearerToken="";
		
		given()
			.header("Authorization","Bearer " +bearerToken)
		.when()
			.get("https://api.github.com/user/repos")
		.then()
			.statusCode(200)
			.log().body();
		
	}

	
	//5. API Key authentication	
	
	@Test
	void verifyAPIKeyAuth()
	{
		given()
		.queryParam("lat", 44.34)
        .queryParam("lon", 10.99)
        .queryParam("appid", "6115eb9cd5ffac96bec6a0839a5c6371")
		.when()
			.get("https://api.openweathermap.org/data/2.5/forecast")
				
		.then()
			.statusCode(200)
			.log().body();
	}
	
	
}

