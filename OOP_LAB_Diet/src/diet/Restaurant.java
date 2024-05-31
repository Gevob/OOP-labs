package diet;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

import com.sun.tools.javac.code.Attribute.Array;

import diet.Order.OrderStatus;

/**
 * Represents a restaurant in the take-away system
 *
 */
public class Restaurant {
	private String name;
	private String[] hours=new String[0];
	private Food food;
	private Collection <Order> orders=new TreeSet<Order>((a,b)->{int e=a.getLName().compareTo(b.getLName());
	  if(e!=0)
		  return e;
	  else {
		   e=a.getFName().compareTo(b.getFName());
		   if(e!=0)
			   return e;
		   else
			   return a.getTime().compareTo(b.getTime());
	  }
});
	/**
	 * 
	 * Constructor for a new restaurant.
	 * 
	 * Materials and recipes are taken from
	 * the food object provided as argument.
	 * 
	 * @param name	unique name for the restaurant
	 * @param food	reference food object
	 */
	public Restaurant(String name, Food food) {
		this.food=food;
		this.name=name;
	}
	
	/**
	 * gets the name of the restaurant
	 * 
	 * @return name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Define opening hours.
	 * 
	 * The opening hours are considered in pairs.
	 * Each pair has the initial time and the final time
	 * of opening intervals.
	 * 
	 * for a restaurant opened from 8:15 until 14:00 and from 19:00 until 00:00, 
	 * is thoud be called as {@code setHours("08:15", "14:00", "19:00", "00:00")}.
	 * 
	 * @param hm a list of opening hours
	 */
	public void setHours(String ... hm) {
		hours=new String[hm.length];
		if(hm.length%2==0) {
			for(int i=0;i<hm.length;i++) {
				hours[i]=hm[i];
			}
		}
	}
	
	public String[] getHours() {
		return hours;
	}
	
	public Menu getMenu(String name) {
		return null;
	}
	
	/**
	 * Creates a new menu
	 * 
	 * @param name name of the menu
	 * 
	 * @return the newly created menu
	 */
	public Menu createMenu(String name) {
		return food.createMenu(name);
	}

	/**
	 * Find all orders for this restaurant with 
	 * the given status.
	 * 
	 * The output is a string formatted as:
	 * <pre>
	 * Napoli, Judi Dench : (19:00):
	 * 	M6->1
	 * Napoli, Ralph Fiennes : (19:00):
	 * 	M1->2
	 * 	M6->1
	 * </pre>
	 * 
	 * The orders are sorted by name of restaurant, name of the user, and delivery time.
	 * 
	 * @param status the status of the searched orders
	 * 
	 * @return the description of orders satisfying the criterion
	 */
	public void addOrder(Order o) {
		orders.add(o);
	}
	
	public String ordersWithStatus(OrderStatus status) {
		String s="";
		Order tmp;
		Iterator<Order> ord=orders.iterator();
		while(ord.hasNext()) {
			tmp=ord.next();
			if(tmp.getStatus()==status) {
				s+=tmp.toString();
			}
		}
		return s;
	}
}
