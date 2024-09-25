package stepDefinations;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import resources.APIResources;
import resources.Utils;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Map;

import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;

public class StepDefinitions extends Utils {

	public static String siteId; // This will store the site ID between steps
	RequestSpecification res;
	Response response;

	// Use default headers from global.properties
	@Given("default headers for site are provided")
	public void default_headers_for_site_are_provided() throws IOException {
		// Get the default headers from Utils
		res = given().spec(requestSpecification(getDefaultHeaders()));
	}

	// For custom headers passed from the feature file
	@Given("custom headers for site are provided")
	public void custom_headers_for_site_are_provided(Map<String, String> headers) throws IOException {
		// Pass the custom headers from the feature file to the request specification
		res = given().spec(requestSpecification(headers));
	}

	@When("user calls {string} with {string} http request for site")
	public void user_calls_with_http_request_for_site(String resource, String method) {
		APIResources resourceAPI = APIResources.valueOf(resource);

		if (method.equalsIgnoreCase("PUT")) {
			response = res.when().put(resourceAPI.getResource());
		} else if (method.equalsIgnoreCase("GET")) {
			response = res.when().get(resourceAPI.getResource());
		} else if (method.equalsIgnoreCase("DELETE")) {
			response = res.when().delete(resourceAPI.getResource());
		}
	}

	@Then("the API call got success with status code 200")
	public void the_API_call_got_success_with_status_code_200() {
		assertEquals(200, response.getStatusCode());
	}

	@Then("{string} in response body is {string}")
	public void in_response_body_is(String key, String expectedValue) {
		System.out.println("Actual response: " + response.getBody().asString()); // Debugging
		assertEquals(expectedValue, response.jsonPath().getString(key));
	}

	@Then("store the site_id from response")
	public void store_the_site_id_from_response() {
		siteId = response.jsonPath().getString("site_id");
		if (siteId == null) {
			System.out.println("Failed to retrieve site_id from response.");
		}
	}

	@Given("Fetch Site Payload with stored site_id")
	public void fetch_Site_Payload_with_stored_site_id() throws IOException {
		res = given().spec(requestSpecification(getDefaultHeaders()));
	}

	@Given("Delete Site Payload with stored site_id")
	public void delete_Site_Payload_with_stored_site_id() throws IOException {
		res = given().spec(requestSpecification(getDefaultHeaders()));
	}
}