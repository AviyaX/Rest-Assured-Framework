Feature: Site API Automation

	@CreateSite
	Scenario: Verify if Site is created successfully using CreateSiteAPI
		Given default headers for site are provided
		When user calls "createSiteAPI" with "PUT" http request for site
		Then the API call got success with status code 200
		And store the site_id from response

	@FetchSite
	Scenario: Verify if Site is fetched successfully using FetchSiteAPI
		Given Fetch Site Payload with stored site_id
		When user calls "fetchSiteAPI" with "GET" http request for site
		Then the API call got success with status code 200
		And "name" in response body is "Test Site test"

	@DeleteSite
	Scenario: Verify if Site is deleted successfully using DeleteSiteAPI
		Given Delete Site Payload with stored site_id
		When user calls "deleteSiteAPI" with "DELETE" http request for site
		Then the API call got success with status code 200