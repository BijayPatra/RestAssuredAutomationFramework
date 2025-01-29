package api.testcases;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.userEndPoints;
import api.payload.user;
import io.restassured.response.Response;

public class UserTest {

	Faker faker;
	user userPayload;
	public static Logger logger;

	@BeforeClass
	public void generateTestData()
	{
		faker = new Faker();
		userPayload = new user();

		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());


		//obtain logger

		logger = LogManager.getLogger("RestAssuredAutomationFramework_test");
	}

	@Test(priority=1)
	public void testCreateUser()
	{
		logger.info("****************Creating User***********");
		Response response = userEndPoints.createUser(userPayload);

		//log response
		response.then().log().all();


		//validation
		Assert.assertEquals(response.getStatusCode(),200);

		//log
		logger.info("******************User Created***************");
	}


	@Test(priority=2)
	public void testGetUserData()
	{
		logger.info("**************Getting userdata********************");
		Response response = userEndPoints.GetUser(this.userPayload.getUsername());

		System.out.println("Read User Data.");
		//log response
		response.then().log().all();


		//validation
		Assert.assertEquals(response.getStatusCode(),200);

		//log
		logger.info("**************Getting userdata completed********************");
	}

	@Test(priority=3)
	public void testUpdateUser()
	{
		//update data using payload
		//going to update below data using the existing payload getUsername.
		
		logger.info("***********************Updating User****************");
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		Response response = userEndPoints.UpdateUser(this.userPayload.getUsername(),userPayload);


		//log response
		response.then().log().all();

		//validation
		Assert.assertEquals(response.getStatusCode(),200);

		//Read User data to CHECK if firstName,lastName and EmailAddress is updated or not 

		Response responsePostUpdate = userEndPoints.GetUser(this.userPayload.getUsername());

		System.out.println("After Update User Data.");

		responsePostUpdate.then().log().all();

		//log
		logger.info("***********************Updating User completed****************");

	}

	@Test(priority=4)
	public void testDeleteUser()
	{

		logger.info("***********************Deleting user****************");
		Response response = userEndPoints.DeleteUser(this.userPayload.getUsername());

		System.out.println("Delete User Data.");

		//log response
		response.then().log().all();


		//validation
		Assert.assertEquals(response.getStatusCode(),200);

		
		//log
		logger.info("***********************Deleting User Completed****************");


	}
}
