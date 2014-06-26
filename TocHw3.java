// Name: ，丁士宸
// Student ID: F74001014
/* 
 * Description:
 * This program reads real-price housing information from the given URL as input.
 * The output is the average price of a given road according to the arguments.
 *
 * The program will first read data from the given URL and store the information
 * into JSONArray, then find the targets according to the arguments. Once a target is
 * found, its price is added to variable 'sum'. Finally, 'sum' is devided by the 
 * number of target that matches the input. If no matches is found, the program will 
 * output a 0.
 */
import java.net.*;
import java.io.*;
import org.json.*;

public class TocHw3 {
    public static void main(String[] args) {
        int jarrLen = 0, year100 = 0;
        int match = 0;
        long sum = 0;
        URL jsonUrl = null;
        InputStream input = null;
        JSONTokener jtk = null;
        JSONArray jarr = null;

        // check the number of the argument
        if(args.length != 4){
            System.out.println("Usage TocHw3 <URL> <鄉鎮市區> <road> <year>");
            System.exit(1);
        }

        try {
            year100 = Integer.parseInt(args[3]) * 100;
        } catch (NumberFormatException e) {
            System.out.println("Arguement 4 should be a number");
            System.exit(1);
        }

        try {
            // check if the URL is valid
            jsonUrl = new URL(args[0]);
            // open a stream from the url
            input = jsonUrl.openStream();
            // construct a JSONTokener
            jtk = new JSONTokener(input);
            // construct a JSONArray from JSONTokener
            jarr = new JSONArray(jtk);
        }
        catch (MalformedURLException e) {
            System.out.println("Invalid URL.");
            System.out.println(e.getMessage());
            System.exit(1);
        }
        catch (IOException e) {
            System.out.println("Fail to open stream.");
            System.out.println(e.getMessage());
            System.exit(1);
        }
        catch(JSONException e) {
            System.out.println("JSONTokener can't read from stream.");
            System.out.println(e.getMessage());
            System.exit(1);
        }

        // get the number of object in the JSONArray
        jarrLen = jarr.length();

        try {
            // iterate through the json objects
            for(int i = 0; i < jarrLen; i++) {
                JSONObject jobj = jarr.getJSONObject(i);
                if(jobj.getLong("交易年月") > year100 &&
                   jobj.getString("鄉鎮市區").equals(args[1]) &&
                   jobj.getString("土地區段位置或建物區門牌")
                        .indexOf(args[2]) != -1)
                {
                    sum += jobj.getLong("總價元");
                    match++;
                }
            }
        } 
        catch (JSONException e) {
            System.out.println("JSON error.");
            System.out.println(e.getMessage());
            System.exit(1);
        }

        if(match == 0)
            System.out.println("0");
        else
            System.out.println((int)(sum/match));
    }
}
