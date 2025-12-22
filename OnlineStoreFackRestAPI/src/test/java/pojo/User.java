package pojo;

/*
 fields:
{
    id:20,
    email:String,
    username:String,
    password:String,
    name:{
        firstname:String,
        lastname:String
        },
    address:{
    city:String,
    street:String,
    number:Number,
    zipcode:String,
    geolocation:{
        lat:String,
        long:String
        }
    },
    phone:String
}
*/
public class User {
private String email;
private String username ;
private String password ;
private String phone ;
private Name name;
private Address address;
private GeoLocation  geoLocation;
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public Name getName() {
	return name;
}
public void setName(Name name) {
	this.name = name;
}
public Address getAddress() {
	return address;
}
public void setAddress(Address address) {
	this.address = address;
}
public GeoLocation getGeoLocation() {
	return geoLocation;
}
public void setGeoLocation(GeoLocation geoLocation) {
	this.geoLocation = geoLocation;
}
public User(String email, String username, String password, Name name, Address address,
		GeoLocation geoLocation) {

	this.email = email;
	this.username = username;
	this.password = password;
	this.phone = phone;
	this.name = name;
	this.address = address;
	this.geoLocation = geoLocation;
}
@Override
public String toString() {
	return "User [email=" + email + ", username=" + username + ", password=" + password + ", phone=" + phone + ", name="
			+ name + ", address=" + address + ", geoLocation=" + geoLocation + "]";
}



	

}
