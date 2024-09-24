package resources;

public enum APIResources {

	createSiteAPI("/site"),      // PUT
	fetchSiteAPI("/site"),       // GET
	deleteSiteAPI("/site");      // DELETE

	private String resource;

	APIResources(String resource) {
		this.resource = resource;
	}

	public String getResource() {
		return resource;
	}
}
