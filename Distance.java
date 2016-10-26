
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class Distance {
	protected String addr;
	protected String miles;
	protected String time;
	public void findDistance(String address, String destinations) throws MalformedURLException{

		// build a URL
//		String s = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=";
		String s = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=";
		this.addr = address;
		try {
			s += URLEncoder.encode(addr, "UTF-8");
			s += URLEncoder.encode("&destinations=", "UTF-8");
			s += URLEncoder.encode(destinations, "UTF-8");
			
			URL url = new URL(s);

			// read from URL
			Scanner input = new Scanner(url.openStream());
			String str = new String();
			while (input.hasNext())
				str += input.nextLine();
			input.close();

			JSONObject obj = new JSONObject(str);
			JSONObject rows = obj.getJSONArray("rows").getJSONObject(0);
			JSONArray loc = rows.getJSONArray("elements");
			JSONObject dist = loc.getJSONObject(0);
			miles = dist.getString("text");
			JSONObject dur = loc.getJSONObject(1);
			time = dur.getString("text");

			


		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
