package Day8;

import static io.restassured.RestAssured.given;

import org.testng.ITestContext;
import org.testng.annotations.Test;

public class DeleteUserTest {

	static final String BASE_URL = "https://gorest.co.in/public/v2/users";
    static final String BEARER_TOKEN = "c35e10e748c6f113775527bcef204e9929b4c9f4b995a8ee253eec46aed57b06";

    
	@Test(dependsOnMethods= {"day8.UpdateUserTest.updateUser"})
	void deleteUser(ITestContext context)
	{
		given()
			.headers("Authorization", "Bearer "+BEARER_TOKEN)
			.pathParam("id", (Integer)context.getAttribute("userId"))
		.when()
			.delete(BASE_URL+"/{id}")
		.then()
		.statusCode(204);
	}
	
	
}
