package payloads;

import java.util.Random;

import com.github.javafaker.Faker;

import pojo.Product;

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
	
	
}
