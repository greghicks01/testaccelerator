package au.com.tava.Core;

import org.joda.time.DateTime;
import org.json.*;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

/**
 * Created by Greg on 4/06/2017.
 */
public class JSonTest extends TestBase{

    String jstring = "{\n" +
            "    \"name\": \"Alice\",\n" +
            "    \"age\": 20,\n" +
            "    \"address\": {\n" +
            "        \"streetAddress\": \"100 Wall Street\",\n" +
            "        \"city\": \"New York\"\n" +
            "    },\n" +
            "    \"phoneNumber\": [\n" +
            "        {\n" +
            "            \"type\": \"home\",\n" +
            "            \"number\": \"212-333-1111\"\n" +
            "        },{\n" +
            "            \"type\": \"fax\",\n" +
            "            \"number\": \"646-444-2222\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    @Test
    public void testJson(){
        JSONObject obj = new JSONObject(jstring);
        String pageName = obj.getJSONObject("address").getString("city");
        Reporter.log(pageName, true);

        JSONArray arr = obj.getJSONArray("phoneNumber");
        for (int i = 0; i < arr.length(); i++)
        {
            String post_id = arr.getJSONObject(i).getString("type");
            Reporter.log(post_id, true);
        }
    }

    @Test
    public static void geocoding() throws Exception
    {
        // build a URL
        String s = "http://maps.google.com/maps/api/geocode/json?" +
                "sensor=false&address=";
        s += URLEncoder.encode("711 Centaur Rd Hamilton Valley NSW 2641", "UTF-8");
        URL url = new URL(s);

        // read from the URL
        Scanner scan = new Scanner(url.openStream());
        String str = new String();
        while (scan.hasNext())
            str += scan.nextLine();
        scan.close();

        // build a JSON object
        JSONObject obj = new JSONObject(str);
        if (! obj.getString("status").equals("OK"))
            return;

        // get the first result
        JSONObject res = obj.getJSONArray("results").getJSONObject(0);
        System.out.println(res.getString("formatted_address"));
        JSONObject loc =
                res.getJSONObject("geometry").getJSONObject("location");
        System.out.println("lat: " + loc.getDouble("lat") +
                ", lng: " + loc.getDouble("lng"));
    }

    @Test
    public void testDateTIme(){
        DateTime start = new DateTime();

        Reporter.log(start.toString(),true);
        Reporter.log(start.toString(Globals.imageDateTimeFormat),true);

    }
}
