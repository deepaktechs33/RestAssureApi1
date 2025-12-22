package payloads;

import java.util.Random;

import com.github.javafaker.Faker;

import pojo.Address;
import pojo.GeoLocation;
import pojo.Name;
import pojo.Product;
import pojo.User;

public class Payload {
	
	private static final Faker faker= new Faker();
	private static final String catagories []= {"electronics", "furnitire" ,"clothing" ,"books" , "beauty"};
	private static final Random random  =new Random(); 

	public static Product productpayload() {
		String name = faker.commerce().productName();
		
		double price =Double.parseDouble(faker.commerce().price());
		
		String description=faker.lorem().sentence();
		String imageurl="";
		String catagory= catagories[random.nextInt(catagories.length)];
		
		return new Product(price, name, description, imageurl, catagory);
	} 
	
	public static User userPayload()
	{
		//name
		
		String firstname=faker.name().firstName();
		String lastname=faker.name().lastName();
		
		Name name=new Name(firstname,lastname);
		
		//location
		String lat=faker.address().latitude();
		String lng=faker.address().longitude();
		
		GeoLocation geoLocation=new GeoLocation(lat,lng);
		String city=faker.address().city();
		String street=faker.address().streetName();
		int number=random.nextInt(100);
		String zipcode=faker.address().zipCode();
		Address address=new Address(city,street,number,zipcode);
		
		
		//User
		String email=faker.internet().emailAddress();
		String username=faker.name().username();
		String password=faker.internet().password();
		String phonenumber=faker.phoneNumber().cellPhone();
		
		User user=new User(email,username,password,name,address,geoLocation);
		
		return user;
	
	//User user=new User(email,username,password,name,address,phonenumber);
	
}
}
