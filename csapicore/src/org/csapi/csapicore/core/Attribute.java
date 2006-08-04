/*
 * Created on 29 mai 2005
 */
package org.csapi.csapicore.core;

/**
 * <p>
 * This class holds information about an attribute; mainly its name and value,
 * but also every bit of information we can retrieve from the SOAP exchange.
 * </p>
 *
 * <p>
 * Attributes should be used with Records and Reports classes, because they
 * ensure some consistency together. But this class can also be used alone, to
 * specify a record attribute.
 * </p>
 *
 * @author Boris Baldassari
 */
public class Attribute {

    /**
     * Name of the attribute, e.g. 'crstatus'.
     */
    private String attributeName;

    /**
     * Value of the attribute, e.g. 'entered'.
     */
    private String attributeValue;

    /**
     * Type of the attribute, e.g. 'CCM_STRING'.
     */
    private String attributeType;

    /**
     * Constructor.
     */
    public Attribute() {
        super();
    }

    /**
     * Constructor for Attribute.
     *
     * @param name It is the name of the attribute (e.g. 'crstatus')
     * @param value It is the value of the attribute (e.g. 'entered')
     * @param type It is the type of the attribute (e.g. 'CCM_STRING')
     */
    public Attribute(final String name,
            final String value,
            final String type) {
        super();
        this.attributeName = name;
        this.attributeValue = value;
        this.attributeType = type;
    }

    /**
     * Reimplements the hashCode method for HashTable implementation in the
     * Record class. May not be needed if implementation changes, but is kept
     * anyway (as long as it is harmless).
     *
     * @see java.lang.Object#hashCode()
     * @return An int representation (hash) of the object.
     */
    public final int hashCode() {
        return attributeName.hashCode();
    }

    /**
     * Set the attributeType private attribute.
     * @param type  The type to set.
     */
    public final void setType(final String type) {
        this.attributeType = type;
    }

    /**
     * Set the attributeName private attribute.
     *
     * @param name
     *            The name of the attribute.
     */
    public final void setName(final String name) {
        attributeName = name;
    }

    /**
     * Set the attributeValue private attribute.
     *
     * @param value
     *            The new value of the attribute.
     */
    public final void setValue(final String value) {
        attributeValue = value;
    }

    /**
     * Get the attributeType private attribute.
     *
     * @return Returns the attributeType.
     */
    public final String getType() {
        return attributeType;
    }

    /**
     * Get the attributeName private attribute.
     *
     * @return Returns the attributeName.
     */
    public final String getName() {
        return attributeName;
    }

    /**
     * Get the attributeValue private attribute.
     *
     * @return Returns the attributeValue.
     */
    public final String getValue() {
        return attributeValue;
    }

    /**
     * Returns a convenient String representation of the object, which form
     * is: "attributeName = attributeValue".
     *
     * @return A String representing the attribute main characteristics.
     */
    public final String toString() {
        return attributeName + " = " + attributeValue;
    }
}
