package resources;

/**
 * The TestDataBuild class is responsible for dynamically generating JSON payloads
 * for the API requests. This class allows for easy creation of request payloads,
 * making it useful for testing different API endpoints with varying data.
 *
 * Currently, it is generating payloads as JSON strings directly, but in the future,
 * you can expand this class to use POJO objects for better maintainability and flexibility.
 */
public class TestDataBuild {

	/**
	 * Generates a JSON payload for creating a site.
	 * This method is used to dynamically create the body of a PUT/POST request to
	 * create or update a site.
	 *
	 * Example usage:
	 * In your step definitions or test cases, you can call this method to generate
	 * a payload for the "create site" API.
	 *
	 * Example:
	 * String payload = testDataBuild.createSitePayload("Site 5000", "Test Site", false);
	 *
	 * This would generate a JSON string like:
	 * {
	 *   "site_id": "Site 5000",
	 *   "site_name": "Test Site",
	 *   "isHardwareDevice": false
	 * }
	 *
	 * @param siteId          The unique ID for the site
	 * @param siteName        The name of the site
	 * @param isHardwareDevice Whether the site has a hardware device (true/false)
	 * @return A JSON string to be used as the request payload.
	 */
	public String createSitePayload(String siteId, String siteName, boolean isHardwareDevice) {
		return "{\n" +
				"  \"site_id\": \"" + siteId + "\",\n" +
				"  \"site_name\": \"" + siteName + "\",\n" +
				"  \"isHardwareDevice\": " + isHardwareDevice + "\n" +
				"}";
	}

	/**
	 * Generates a JSON payload for deleting a site.
	 * This method can be used to create the body for a DELETE request when you need
	 * to delete a site by its unique ID.
	 *
	 * Example usage:
	 * In your step definitions or test cases, you can call this method to generate
	 * a payload for the "delete site" API.
	 *
	 * Example:
	 * String payload = testDataBuild.deleteSitePayload("Site 5000");
	 *
	 * This would generate a JSON string like:
	 * {
	 *   "site_id": "Site 5000"
	 * }
	 *
	 * @param siteId The unique ID of the site to be deleted
	 * @return A JSON string to be used as the request payload for deletion.
	 */
	public String deleteSitePayload(String siteId) {
		return "{\n" +
				"    \"site_id\": \"" + siteId + "\"\n" +
				"}";
	}
}
