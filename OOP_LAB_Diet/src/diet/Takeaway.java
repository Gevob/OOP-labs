package diet;

import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Represents the main class in the
 * take-away system.
 * 
 * It allows adding restaurant, users, and creating orders.
 *
 */
public class Takeaway {

	private Collection<Restaurant> ristoranti=new TreeSet<Restaurant>((a,b)->(a.getName().compareTo(b.getName())));
	private Collection <String> nomiR=new TreeSet<String>((a,b)->a.compareTo(b));
	private Collection <User> users=new TreeSet<User>((a,b)->{int e=a.getLastName().compareTo(b.getLastName());
															  if(e!=0)
																  return e;
															  else
																  return a.getFirstName().compareTo(b.getFirstName());
	});

	/**
	 * Adds a new restaurant to the take-away system
	 * 
	 * @param r the restaurant to be added
	 */
	public void addRestaurant(Restaurant r) {
		ristoranti.add(r);
		nomiR.add(r.getName());
	}
	
	/**
	 * Returns the collections of restaurants
	 * 
	 * @return collection of added restaurants
	 */
	public Collection<String> restaurants() {
		return nomiR;
	}
	
	/**
	 * Define a new user
	 * 
	 * @param firstName first name of the user
	 * @param lastName  last name of the user
	 * @param email     email
	 * @param phoneNumber telephone number
	 * @return
	 */
	public User registerUser(String firstName, String lastName, String email, String phoneNumber) {
		User tmp=new User(firstName,lastName,email,phoneNumber);
		users.add(tmp);
		return tmp;
	}
	
	/**
	 * Gets the collection of registered users
	 * 
	 * @return the collection of users
	 */
	public Collection<User> users(){
		return users;
	}
	
	/**
	 * Create a new order by a user to a given restaurant.
	 * 
	 * The order is initially empty and is characterized
	 * by a desired delivery time. 
	 * 
	 * @param user				user object
	 * @param restaurantName	restaurant name
	 * @param h					delivery time hour
	 * @param m					delivery time minutes
	 * @return
	 */
	public Order createOrder(User user, String restaurantName, int h, int m) {
		String time=StriBuld(h,m);
		Restaurant tm=null;
		Iterator<Restaurant> res=ristoranti.iterator();
		while(res.hasNext()) {
			tm=res.next();
			if(tm.getName().equals(restaurantName)) {
				time=setTimeDel(time,tm.getHours());
				break;
			}
		}
	    Order tmp=new Order(user,tm,time);
	    tm.addOrder(tmp);
		return tmp;
	}
	
	private String setTimeDel(String time, String[] hours) {
		for(int i=0;i<hours.length;i=i+2) {
			 if(time.compareTo(hours[i])>=0) {
				if(time.compareTo(hours[i+1])<=0)
					return time;
			 }
			 else 
				 return hours[i];
		}
		return time;
	}

	private String StriBuld(int h, int m) {
		String s="";
		if(h<10)
			s="0"+h;
		else
			s=s+h;
		s+=":";
		if(m<10)
			s+="0"+m;
		s+=m;
		return s;
	}

	/**
	 * Retrieves the collection of restaurant that are open
	 * at the given time.
	 * 
	 * @param time time to check open
	 * 
	 * @return collection of restaurants
	 */
	public Collection<Restaurant> openedRestaurants(String time){
		Collection <Restaurant> opened=new TreeSet<Restaurant>((a,b)->(a.getName().compareTo(b.getName())));
		Iterator<Restaurant> open=ristoranti.iterator();
		Restaurant tmp;
		while(open.hasNext()) {
			tmp=open.next();
			if(CheckTime(time,tmp.getHours()))
				opened.add(tmp);
		}
		return opened;
	}

	private boolean CheckTime(String time, String[] hours) {
		for(int i=0;i<hours.length;i=i+2) {
			if(time.compareTo(hours[i])>=0)
				if(time.compareTo(hours[i+1])<0)
					return true;
		}
		return false;
	}

	
	
}
