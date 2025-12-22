package testcases;

import org.testng.annotations.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.Payload;
import pojo.Product;
import pojo.User;
import routs.Routes;

//@Test
public class UserTest extends BaseClass {
	
	//1) Fetch all the user 
@Test	
	public void testGetAllUser()
	{
		
 given()
 
 .when()
  .get(Routes.GET_ALL_USERS)
.then()		
		
.statusCode(200)
.contentType(ContentType.JSON)
 .log().body()
 .body("size()",greaterThan(0));
 
	}	
//2 fetch a specific user by Id 
	@Test
public void testGetUserById()
{
	int userId =configReader.getIntProperty("userId");
	System.out.println(userId);
	
given()	
  .pathParam("id", userId)
.when()
.get(Routes.GET_USER_BY_ID)

.then()
.log().body()
.statusCode(200);

}

@Test
//3 test to fetch a limited number of user 
public void testGetuserWithLimit()
{
	int limit=configReader.getIntProperty("limit");
	given()
	.pathParam("limit",limit)
	
	.when()
	.get(Routes.GET_USERS_WITH_LIMIT)
	.then()
	.statusCode(200)
	.log().all();
}
@Test
//4) Test to fetch users sorted in descending order
	//@Test
	void testGetUsersSortedDesc()
	{
		Response response=given()
			.pathParam("order", "desc")
		.when()
			.get(Routes.GET_USERS_SORTED)
		.then()
			.statusCode(200)
			.extract().response();
	
		List<Integer> userIds=response.jsonPath().getList("id", Integer.class);
		assertThat(isSortedDesceding(userIds), is(true));
	}
	
	//5) Test to fetch users sorted in ascending order
	@Test
	void testGetUsersSortedAsc()
	{
		Response response=given()
			.pathParam("order", "asc")
		.when()
			.get(Routes.GET_USERS_SORTED)
		.then()
			.statusCode(200)
			.extract().response();
	
		List<Integer> userIds=response.jsonPath().getList("id", Integer.class);
		
		assertThat(isSortedAsceding(userIds), is(true));
	}
//6. test to create a new user 
@Test

public void testCreateUser() {

    User newUser = Payload.userPayload();

    int id =
        given()
            .contentType(ContentType.JSON)
            .body(newUser)
        .when()
            .post(Routes.CREATE_USER)
        .then()
            .log().all()
            .statusCode(201)
            .extract().jsonPath().getInt("id");
   System.out.println("Created User ID: " + id);
  
}

//7) Test to update user

	@Test
	public void testUpdateUser()
	{
		int userId=configReader.getIntProperty("userId");
		
		User updateUser=Payload.userPayload();
				
		given()
			.contentType(ContentType.JSON)
			.pathParam("id", userId)
			.body(updateUser)
		.when()
			.put(Routes.UPDATE_USER)
		.then() 
			.log().body()
			.statusCode(200)
			.body("username",equalTo(updateUser.getUsername()));
				
	}
	
	//8) delete user
	
	@Test
	void testDeleteUser()
	{

		int userId=configReader.getIntProperty("userId");
		
		given()
			.pathParam("id", userId)
		.when()
			.delete(Routes.DELETE_USER)
		.then()
			.statusCode(200);
		}}
	