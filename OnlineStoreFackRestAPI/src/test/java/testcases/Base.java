package testcases;

import io.restassured.RestAssured;
import routs.Routs;
import utils.ConfigReader;

public class Base {
	
ConfigReader configReader;

public void setUp()
{
RestAssured.baseURI=Routs.BASE_URL;
configReader= new ConfigReader();

	}	
	

}
