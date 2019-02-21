package restclient.mshah;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;


/**
 * Author:  Monisha Shah
 * Make sure the file exists at location specified in the FILENAME variable.
 *
 */
public class ApacheCXFWebServiceClient 
{
	public static final String FILENAME = "/Users/mshah/Documents/examples-workspace/orders.txt";
	public static final String API_KEY = "d289a6fc4ca60c1a94ee69b0bd7946d3";
	private static List<String> list = new ArrayList<String>();
    public static void main( String[] args )
    {
    	readFile();
    	list.forEach(x -> { System.out.println(makeWebServiceCall(x)); });
    }

	private static String makeWebServiceCall(String zip) {
		StringBuilder ENDPOINT = new StringBuilder("http://api.openweathermap.org/data/2.5/weather?zip=")
				.append(zip)
				.append(",us&")
				.append("appid=")
				.append(API_KEY);
    	Client client = ClientBuilder.newClient();
    	WebTarget resourceTarget = client.target(ENDPOINT.toString());
    	String response = resourceTarget.request("text/plain").get(String.class);
		return response;
	}
    
    private static void readFile() {
		try (Stream<String> stream = Files.lines(Paths.get(FILENAME))) {

			//1. filter line 3
			//2. convert all content to upper case
			//3. convert it into a List
			list = stream
					.filter(line -> !line.startsWith("line3"))
					.map(String::toUpperCase)
					.collect(Collectors.toList());

		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
