package diet;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Represents an order in the take-away system
 */
public class Order {
	/**
	 * Defines the possible order status
	 */
	public enum OrderStatus {
		ORDERED, READY, DELIVERED;
	}
	/**
	 * Defines the possible valid payment methods
	 */
	public enum PaymentMethod {
		PAID, CASH, CARD;
	}
	private OrderStatus status=OrderStatus.ORDERED;
	private PaymentMethod method=PaymentMethod.CASH;
	private String time;
	private User client;
	private Restaurant restaurant;
	private Map<String,Integer> menu=new TreeMap<String,Integer>();
	public Order(User user, Restaurant restaurantName, String time2) {
		time=time2;
		client=user;
		restaurant=restaurantName;
		
	}

	/**
	 * Total order price
	 * @return order price
	 */
	public double Price() {
		return -1.0;
	}
	
	/**
	 * define payment method
	 * 
	 * @param method payment method
	 */
	public void setPaymentMethod(PaymentMethod method) {
		this.method=method;
	}
	
	/**
	 * get payment method
	 * 
	 * @return payment method
	 */
	public PaymentMethod getPaymentMethod() {
		return method;
	}
	
	/**
	 * change order status
	 * @param newStatus order status
	 */
	public void setStatus(OrderStatus newStatus) {
		status=newStatus;
	}
	
	/**
	 * get current order status
	 * @return order status
	 */
	public OrderStatus getStatus(){
		return status;
	}
	
	/**
	 * Add a new menu with the relative order to the order.
	 * The menu must be defined in the {@link Food} object
	 * associated the restaurant that created the order.
	 * 
	 * @param menu     name of the menu
	 * @param quantity quantity of the menu
	 * @return this order to enable method chaining
	 */
	public Order addMenus(String menu, int quantity) {
        this.menu.put(menu,quantity);
		return this;
	}
	
	public String getTime() {
		return time;
	}
	
	public String getFName() {
		return client.getFirstName();
	}
	
	public String getLName() {
		return client.getLastName();
	}
	
	/**
	 * Converts to a string as:
	 * <pre>
	 * RESTAURANT_NAME, USER_FIRST_NAME USER_LAST_NAME : DELIVERY(HH:MM):
	 * 	MENU_NAME_1->MENU_QUANTITY_1
	 * 	...
	 * 	MENU_NAME_k->MENU_QUANTITY_k
	 * </pre>
	 */
	@Override
	public String toString() {
		String s="";
		s+=restaurant.getName()+", "+client.getFirstName()+" "+client.getLastName()+" : "+"("+time+")"+":\n";
		for(String key : menu.keySet()) {
			s+="\t"+key+"->"+menu.get(key)+"\n";
		}
		return s;
	}
	
}
