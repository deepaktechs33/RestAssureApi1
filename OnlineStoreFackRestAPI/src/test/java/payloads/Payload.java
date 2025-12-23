package payloads;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.github.javafaker.Faker;

import pojo.Address;
import pojo.Cart;
import pojo.CartProduct;
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
		
		

	
}
	
	
	//Cart
		public static Cart cartPayload(int userId) {
	        List<CartProduct> products = new ArrayList<>();
	        
	        
	        // Adding one random product to the cart
	        int productId = random.nextInt(100);
	        int quantity = random.nextInt(10) + 1;
	               
	        CartProduct cartProduct= new CartProduct(productId, quantity);
	        products.add(cartProduct);

	        
	        //new Date()  ----> Returns date like  Wed Feb 19 13:17:45 IST 202
	        // We need to convert this to "yyyy-MM-dd" format in String 
	        
	         SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);// Define output date format
	         String date = outputFormat.format(new Date());//Converting to String
		    
	        return new Cart(userId, date, products);
	    }
		
	
	
	
	
	
	
	
	
	
}
