/**
	Wagon Member class, contains a method for adding
	the wagon member's name whenever a new object is
	instantiated. More on health to go here later.
	@author Kaitlyn Reaser
	@version 1.0 9 April 2024
	@filename WagonMember.java
*/
package OregonTrailMVP;;

public class WagonMember {
	String memberName;
	
	/**
	 * Constructor for the Wagon Member Class
	 * @param name - Name of a wagon member
	 */
	public WagonMember (String name)
	{
		memberName = name;
	}
	
	/**
	 * Sets name of the wagon member
	 * @param name - Name of a wagon member
	 */
	public void setName(String name) 
	{
		memberName = name;
	}
}