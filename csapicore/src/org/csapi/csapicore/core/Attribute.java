/*
 * Created on 29 mai 2005
 */
package org.csapi.csapicore.core;

/**
 * <p>This class holds information about an attribute; mainly
 * its name and value, but also every bit of information we
 * can retrieve from the SOAP exchange.</p>
 * 
 * <p>Attributes should be used with Records and Reports classes, because they
 * ensure some consistency together. But this class can safely be used alone,
 * to specify a record attribute.</p>
 * 
 * @author Boris Baldassari
 */
public class Attribute {

	private String attributeName;
	private String attributeValue;
	private String attributeType;
	
	/**
	 * Constructor.
	 */
	public Attribute() {
		super();
	}
	
	/** 
	 * Reimplements the hashCode method for HashTable implementation in the
	 * Record class. May not be needed if implementation changes, but is
	 * kept anyway (as long as it is harmless).
	 * 
	 * @see java.lang.Object#hashCode()
	 * @return An int representation (hash) of the object.
	 */
	public int hashCode() {
		return attributeName.hashCode();
	}

	/**
	 * Get the attributeType private attribute.
	 * 
	 * @param name The name of the attribute.
	 */
	public void setName(String name) {
		attributeName = name;
	}

	/**
	 * Get the attributeValue private attribute.
	 * 
	 * @param value The new value of the attribute.
	 */
	public void setValue(String value) {
		attributeValue = value;
	}
	
	/**
	 * Get the attributeType private attribute.
	 * 
	 * @return Returns the attributeType.
	 */
	public String getType() {
		return attributeType;
	}
	
	/**
	 * Set the attributeType private attribute.
	 * 
	 * @param type The type to set.
	 */
	public void setType(String type) {
		this.attributeType = type;
	}
	
	/**
	 * Get the attributeName private attribute.
	 * 
	 * @return Returns the attributeName.
	 */
	public String getName() {
		return attributeName;
	}
	
	/**
	 * Get the attributeValue private attribute.
	 * 
	 * @return Returns the attributeValue.
	 */
	public String getValue() {
		return attributeValue;
	}
	
	
	/**
	 * Returns a convenient String representation of the object, which form 
	 * is: "attributeName=attributeValue".
	 * 
	 * @return A String representing the attribute main characteristics.
	 */
	public String toString() {
		return attributeValue;
	}
}
