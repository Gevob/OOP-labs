package diet;

/**
 * Represent a take-away system user
 *  
 */
public class User {
	private String lastname;
	private String firstname;
	private String mail;
	private String cell;
	/**
	 * get user's last name
	 * @return last name
	 */
	public User(String name,String surname,String email,String num) {
		lastname=surname;
		firstname=name;
		mail=email;
		cell=num;
	}
	public String getLastName() {
		return lastname;
	}
	
	
	
	/**
	 * get user's first name
	 * @return first name
	 */
	public String getFirstName() {
		return firstname;
	}
	
	/**
	 * get user's email
	 * @return email
	 */
	public String getEmail() {
		return mail;
	}
	
	/**
	 * get user's phone number
	 * @return  phone number
	 */
	public String getPhone() {
		return cell;
	}
	
	/**
	 * change user's email
	 * @param email new email
	 */
	public void SetEmail(String email) {
		mail=email;
	}
	
	/**
	 * change user's phone number
	 * @param phone new phone number
	 */
	public void setPhone(String phone) {
		cell=phone;
	}
	@Override
	public String toString() {
		return firstname+" "+lastname;
	}
	
}
