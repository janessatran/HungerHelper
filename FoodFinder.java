/**
  *  Program 8
  *  Takes address, preferred food type, and restaurant rating from user. Outputs a list of restaurants
  *  ranging from closest to farthest based on user inputs. Note: values based on lat/long, so distances are an approximation. 
  *  Uses Yelp Business API and Google API. 
  *  CS108-2
  *  April 29, 2016 
  *  @author  Janessa Tran
*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import realtimeweb.simplebusiness.SimpleBusiness;
import realtimeweb.simplebusiness.domain.Business;
import java.io.IOException;


public class FoodFinder {
	public static void main(String[] args) {
//		SimpleBusiness yelp = new SimpleBusiness("cache.json");  //OFFLINE
		SimpleBusiness yelp = new SimpleBusiness(); // REALTIME

		try {
			Location loc = new Location();
			System.out.println("Please enter the current address (on a single line): ");
			Scanner scan = new Scanner(System.in);
			String addr = scan.nextLine();
			loc.getLoc(addr);
			System.out.println(loc.currAddress);
			System.out.println(loc.currLocation);

			List<String> foodType = new ArrayList<>(5);
			System.out.println("Please enter a food type: ");
			String type = scan.nextLine();
			foodType.add(type);

			Location resLoc = new Location();
			double key = 0;
			String rAdd = null;
			
			System.out.println("Please enter a minimum rating for the restaurant: ");
			int rate = scan.nextInt();
			System.out.println("Processing . . . ");
			Map<Double, String> yourMap = new HashMap<Double, String>();
			for (String kind: foodType) {
				List<Business> restaurants = yelp.search(kind, loc.currLocation);
				for (Business b: restaurants) {
					if(b.getRating() > rate){
						rAdd = b.getLocation();
						resLoc.getLoc(rAdd);
						key = loc.distance(loc.currentLat, resLoc.currentLat, loc.currentLon, resLoc.currentLon);
						yourMap.put(key, (b.getName() + " at " + b.getLocation()));
			            Thread.sleep(2000);
					}
					

				}		
			}
	
			Map<Double, String> sortedMap = new TreeMap<Double, String>(yourMap);
			System.out.println("");
			System.out.println("Ordered by closest to farthest: ");
			
			for(Map.Entry<Double, String> entry: sortedMap.entrySet()){
				String v = entry.getValue();
				System.out.println(v);
			}
		}

		catch(IOException | InterruptedException e) {
			e.printStackTrace();
		}

	}

}


