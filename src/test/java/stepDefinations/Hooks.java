package stepDefinations;

import io.cucumber.java.Before;
import java.io.IOException;
import java.util.Map;
import resources.Utils;

public class Hooks {

	@Before("@DeleteSite")
	public void beforeDeleteSiteScenario() throws IOException {
		StepDefinitions stepDef = new StepDefinitions();

		// Check if siteId is null, if so, create a site first
		if (StepDefinitions.siteId == null) {
			// Use default headers from global.properties via Utils class
			Map<String, String> headers = new Utils().getDefaultHeaders();

			// Provide headers and call createSiteAPI
			stepDef.custom_headers_for_site_are_provided(headers);
			stepDef.user_calls_with_http_request_for_site("createSiteAPI", "PUT");

			// Fetch the siteId from the response and store it
			String fetchedSiteId = stepDef.response.jsonPath().getString("site_id");
			if (fetchedSiteId != null) {
				StepDefinitions.siteId = fetchedSiteId;
			} else {
				System.out.println("Failed to retrieve site_id from response.");
			}
		}
	}
}