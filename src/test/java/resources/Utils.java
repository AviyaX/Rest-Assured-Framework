package resources;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Utils {
	public static RequestSpecification req;
	public static PrintStream log;

	// Method to dynamically add headers from the feature file
	public RequestSpecification requestSpecification(Map<String, String> headers) throws IOException {
		if (log == null) {
			// Open the log file in overwrite mode at the beginning of a test run
			log = new PrintStream(Files.newOutputStream(Paths.get("logging.txt"), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING));
		}

		// Add timestamp for better log readability
		log.println("\n\n===================== API Request =====================");
		log.println("Timestamp: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
		log.println("======================================================");

		RequestSpecBuilder specBuilder = new RequestSpecBuilder()
				.setBaseUri(getGlobalValue("baseUrl"))
				.addFilter(new RequestLoggingFilter(log))
				.addFilter(new ResponseLoggingFilter(log))
				.setContentType(ContentType.JSON);

		// Add headers from the feature file and log them in a clean format
		log.println("Headers:");
		headers.forEach((key, value) -> log.println("  " + key + ": " + value));

		headers.forEach(specBuilder::addHeader);

		return specBuilder.build();
	}

	// Fetch value from global properties file
	public String getGlobalValue(String key) throws IOException {
		Properties prop = new Properties();
		prop.load(Files.newInputStream(Paths.get("src/test/java/resources/global.properties")));
		return prop.getProperty(key);
	}

	// Method to get default headers from global properties file
	public Map<String, String> getDefaultHeaders() throws IOException {
		return Map.of(
				"promiseq-subscription-key", getGlobalValue("promiseq-subscription-key"),
				"site-id", getGlobalValue("site-id"),
				"site-name", getGlobalValue("site-name"),
				"is-hardware-device", getGlobalValue("is-hardware-device")
		);
	}
}
