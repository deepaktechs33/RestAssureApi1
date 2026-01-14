package Day6;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;
@Test
public class ParsingJsonArrary {
public void  testJsonResponseBody()

{

	ResponseBody responseBody =  given()

.when()
.get("http://localhost:3000/employees")
.then()
.statusCode(200)
.extract().response().body();


JsonPath jsonPath=	new JsonPath(responseBody.asString());
int employeeCount=jsonPath.getInt("size()");
	
for (int i=0;i<employeeCount;i++) 
{
	String first_name=jsonPath.getString("["+i+"].first_name")	;
	String last_name=jsonPath.getString("["+i+"].last_name")	;

	String email=jsonPath.getString("["+i+"].email")	;
	String gender=jsonPath.getString("["+i+"].gender")	;
 int id=jsonPath.getInt("["+i+"].id")	;
System.out.println(first_name +" "+last_name+" "+email+" "+" "+gender+" "+id);
}	

boolean status=false;
// search of an employee "Steve" present in the list or not 
 for(int i= 0;i<employeeCount;i++) {
	 String fristName=jsonPath.getString("["+i+"].first_name");
	 if(fristName.equals("Steve"))
	 {
		 status=true;
		 System.out.println(fristName);
		 break;
	
	 }
	 
 }
	assertThat(status, is(true));
 
	
}	

}
