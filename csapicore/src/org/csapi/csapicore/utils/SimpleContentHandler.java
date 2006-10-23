/**
 * Created on 11 Aout 2005.
 */

package org.csapi.csapicore.utils;

import java.util.regex.Pattern;

import org.csapi.csapicore.core.Attribute;
import org.csapi.csapicore.core.Record;
import org.csapi.csapicore.core.Report;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.LocatorImpl;

/**
 * <p>
 * An implementation for the XML SAX ContentHandler. Attributes and Records are
 * built and put together on-the-fly, then associated to the report. This class
 * is also used for login, thus the csapiToken item.
 * </p>
 * 
 * <p>
 * The mechanism used to parse SOAP is defined thereafter:
 * </p>
 * <ul>
 * <li>When an opening tag is found, we set the currentNode private integer to
 * one of the type defined in the <a href="#listoftypes">list of types below
 * </a>. Some actions are taken from there, such as object creations.</li>
 * <li>When non-null values are encountered, they are considered as values for
 * the object currently treated, either an attribute name, value, type, or any
 * data found in the XML steam.</li>
 * <li>When a closing tar is found, we reset some values which may be
 * un-intentionally re-used. Fulfilled attributes are added to the current
 * record, and records are added to the current report.</li>
 * </ul>
 * 
 * <p>
 * <a name="listoftypes" />The list of types found in the SOAP stream are:
 * </p>
 * <ul>
 * <li>FAULT = 5</li>
 * <li>FAULTCODE = 2</li>
 * <li>FAULTSTRING = 3</li>
 * <li>CSAPI_RESPONSE = 1</li>
 * <li>CSAPI_CQUERY_DATA = 4</li>
 * <li>CSAPI_COBJECT_DATA = 10</li>
 * <li>CSAPI_COBJECT_VECTOR_SIZE = 11</li>
 * <li>CSAPI_COBJECT_VECTOR_TYPE = 12</li>
 * <li>CSAPI_COBJECT_VECTOR_POSITION = 13</li>
 * <li>CSAPI_COBJECT_VECTOR_0 = 14</li>
 * <li>CSAPI_COBJECT_DATA_SIZE = 141</li>
 * <li>CSAPI_COBJECT_VECTOR_TRANSITIONS = 142</li>
 * <li>CSAPI_COBJECT_DATA_NAME = 101</li>
 * <li>CSAPI_COBJECT_DATA_VALUE = 102</li>
 * <li>CSAPI_COBJECT_DATA_DATE = 106</li>
 * <li>CSAPI_COBJECT_DATA_TYPE = 103</li>
 * <li>CSAPI_COBJECT_DATA_READONLY = 104</li>
 * <li>CSAPI_COBJECT_DATA_REQUIRED = 105</li>
 * </ul>
 * 
 * <p>
 * Acknowledgements go to smeric for his excellent (and useful) models for XML
 * processing.
 * </p>
 * 
 * @author Boris Baldassari
 */
public class SimpleContentHandler implements ContentHandler {

    /** The token to retrieve for login. */
    private String csapiToken;

    /** Objects built while parsing is done. */
    private Report currentReport;

    /**
     * Record and Attribute objects are temporary, since they are added to the
     * record set as soon as they are finished.
     */
    private Record currentRecord;

    /**
     * Record and Attribute objects are temporary, since they are added to the
     * record set as soon as they are finished.
     */
    private Attribute currentAttribute;

    /** Identification of the Node we are currently visiting. */
    private int currentNode;

    /**
     * faultCode is used for error handling.
     */
    private Integer faultCode;

    /**
     * faultString: the message string for fault.
     */
    private String faultString;

    /**
     * 
     */
    private Locator locator;

    /*
     * Definition of the different XML tags we will meet when parsing the
     * document.
     */
    
    /**
     * Empty value to reset attributes value.
     */
    private static final int CSAPI_NULL = 0;

    /**
     * CSAPI_FAULT.
     */
    private static final int CSAPI_FAULT = 5;

    /**
     * CSAPI_FAULTCODE is fault code (number).
     */
    private static final int CSAPI_FAULTCODE = 2;

    /**
     * CSAPI_FAULTSTRING is the fault message.
     */
    private static final int CSAPI_FAULTSTRING = 3;

    /**
     * CSAPI_RESPONSE.
     */
    private static final int CSAPI_RESPONSE = 1;

    /**
     * CSAPI_CQUERY_DATA.
     */
    private static final int CSAPI_CQUERY_DATA = 4;

    /**
     * CSAPI_COBJECT_DATA.
     */
    private static final int CSAPI_COBJECT_DATA = 10;

    /**
     * CSAPI_COBJECT_VECTOR_SIZE. 
     */
    private static final int CSAPI_COBJECT_VECTOR_SIZE = 11;

    /** 
     * CSAPI_COBJECT_VECTOR_TYPE.
      */
    private static final int CSAPI_COBJECT_VECTOR_TYPE = 12;

    /**
     * CSAPI_COBJECT_VECTOR_POSITION.
     */
    private static final int CSAPI_COBJECT_VECTOR_POSITION = 13;

    /**
     * CSAPI_COBJECT_VECTOR_0.
     */
    private static final int CSAPI_COBJECT_VECTOR_0 = 14;

    /**
     * CSAPI_COBJECT_DATA_SIZE.
     */
    private static final int CSAPI_COBJECT_DATA_SIZE = 141;

    /**
     *  CSAPI_COBJECT_VECTOR_TRANSITIONS.
     */
    private static final int CSAPI_COBJECT_VECTOR_TRANSITIONS = 142;

    /**
     * 
     */
    private static final int CSAPI_COBJECT_DATA_NAME = 101;

    /**
     * The attribute value.
     */
    private static final int CSAPI_COBJECT_DATA_VALUE = 102;

    /**
     * CSAPI_COBJECT_DATA_DATE.
     */
    private static final int CSAPI_COBJECT_DATA_DATE = 106;

    /**
     * The type of data sent (CCM_TYPE).
     */
    private static final int CSAPI_COBJECT_DATA_TYPE = 103;

    /**
     * Is the date readonly?
     */
    private static final int CSAPI_COBJECT_DATA_READONLY = 104;

    /**
     * CSAPI_COBJECT_DATA_REQUIRED.
     */
    private static final int CSAPI_COBJECT_DATA_REQUIRED = 105;

    /**
     * Constructor.
     */
    public SimpleContentHandler() {
        super();
        locator = new LocatorImpl();
    }

    /**
     * Set the locator for this document. Locator handles processing progression 
     * in the file, with e.g. number of line and number of character on the line.
     * @param value The locator.
     * @see org.xml.sax.ContentHandler#setDocumentLocator(org.xml.sax.Locator)
     */
    public final void setDocumentLocator(final Locator value) {
        locator = value;
    }

    /**
     * Event sent on XML parsing beginning.
     * 
     * @throws SAXException
     *             If a problem occurs, preventing parsing of the document.
     * @see org.xml.sax.ContentHandler#startDocument()
     */
    public void startDocument() throws SAXException {
        /* Not needed. */
    }

    /**
     * Event sent on XML parsing ending.
     * 
     * @throws SAXException
     *             If a problem occured, which invalidates analyze completion.
     * @see org.xml.sax.ContentHandler#endDocument()
     */
    public void endDocument() throws SAXException {
        /* Not needed. */
    }

    /**
     * Event sent on beginning of prefix mapping.
     * 
     * @param prefix
     *            The prefix used for this namespace.
     * @param URI
     *            URI of this namespace.
     * @see org.xml.sax.ContentHandler#startPrefixMapping(java.lang.String,
     *      java.lang.String)
     */
    public void startPrefixMapping(final String prefix, final String URI)
            throws SAXException {
        /* Not needed. */
    }

    /**
     * Event sent on ending of prefix mapping.
     * 
     * @param prefix
     *            The prefix used for this namespace
     * @see org.xml.sax.ContentHandler#endPrefixMapping(java.lang.String)
     */
    public void endPrefixMapping(String prefix) throws SAXException {
        /* Not needed. */
    }

    /**
     * <p>
     * Event sent on every XML tag opening. Used to mark the currentNode
     * attribute with the property treated on next CDATA value.
     * </p>
     * 
     * <p>
     * In the context of the SOAP protocol, we use this method to set the type
     * of node to be treated next. When the parser hits the values of XML tag
     * via the characters() method call, the node type is used to take actions.
     * Node types are defined <a href="#listoftypes"> list of types above</a>.
     * </p>
     * 
     * @param nameSpaceURI
     *            URI of the current namespace.
     * @param localName
     *            Local name of the opening tag.
     * @param rawName
     *            Name of the tag for version 1.0
     *            <code>nameSpaceURI + ":" + localName</code>
     * @throws SAXException
     *             If the tag is not correct (e.g. for no conformance to a
     *             Document Type Declaration).
     * @see org.xml.sax.ContentHandler#startElement(java.lang.String,
     *      java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    public void startElement(String nameSpaceURI, String localName,
            String rawName, Attributes attributs) throws SAXException {

        /* localName is always empty if we do not treat namespace. */
        localName = rawName;

        /*
         * Let's begin the string switch statement. since java does not permit
         * to switch/case on String, we have to affect the currentNode attribute
         * by hand (i.e. with else/if's).
         */
        if (rawName.equalsIgnoreCase("csapi_response")) {
            /* This tag sometimes contains the csapi_token. */
            currentNode = CSAPI_RESPONSE;
        } else if (rawName.equalsIgnoreCase("faultcode")) {
            /* The identifier of the fault been reported. */
            currentNode = CSAPI_FAULTCODE;
        } else if (rawName.equalsIgnoreCase("faultstring")) {
            /* The identifier of the fault been reported. */
            currentNode = CSAPI_FAULTSTRING;
        } else if (rawName.equalsIgnoreCase("csapi_cquery_data")) {
            /*
             * Beginning of a new set of records: Create a Report object.
             */
            currentNode = CSAPI_CQUERY_DATA;

            String[] attributes = new String[1];
            attributes[0] = "attributes";
            currentReport = new Report("query", attributes);

        } else if (rawName.equalsIgnoreCase("csapi_cobject_vector_size")) {
            /* The number of records in the report. */
            currentNode = CSAPI_COBJECT_VECTOR_SIZE;
        } else if (rawName.equalsIgnoreCase("csapi_cobject_vector_type")) {
            currentNode = CSAPI_COBJECT_VECTOR_TYPE;
        } else if (rawName.equalsIgnoreCase("csapi_cobject_vector_0")) {
            /* Beginning of a new record: Create a Record object. */
            currentNode = CSAPI_COBJECT_VECTOR_0;

            currentRecord = new Record("xxxx");
        } else if (rawName.equalsIgnoreCase("csapi_cobject_data")) {
            /* Beginning of a new attribute: Create an Attribute object. */
            currentNode = CSAPI_COBJECT_DATA;

            currentAttribute = new Attribute();
        } else if (rawName.equalsIgnoreCase("csapi_cobject_data_name")) {
            /* The name of the current attribute. */
            currentNode = CSAPI_COBJECT_DATA_NAME;
        } else if (rawName.equalsIgnoreCase("csapi_cobject_data_value")) {
            /* The value of the current attribute. */
            currentNode = CSAPI_COBJECT_DATA_VALUE;
        } else if (rawName.equalsIgnoreCase("csapi_cobject_data_type")) {
            /* The CCM_TYPE of the current attribute. */
            currentNode = CSAPI_COBJECT_DATA_TYPE;
        } else if (rawName.equalsIgnoreCase("csapi_cobject_data_size")) {
            /* The number of attributes for this record. */
            currentNode = CSAPI_COBJECT_DATA_SIZE;
        }
    }

    /**
     * Event sent on every tag closing. We set the currentNode attribute to null
     * to prevent overwriting of value (this happens if you remove it).
     * 
     * @see org.xml.sax.ContentHandler#endElement(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    public void endElement(final String nameSpaceURI, final String localName, final String rawName)
            throws SAXException {

        /* localName is always empty if we do not treat namespace. */
//        localName = rawName;

        if (localName.equalsIgnoreCase("csapi_response")) {
            // no action here
        } else if (localName.equalsIgnoreCase("csapi_cquery_data")) {
            /* The currentReport is ok (finished). */
            currentNode = CSAPI_NULL;
        } else if (localName.equalsIgnoreCase("faultcode")) {
            /* The identifier of the fault been reported. */
            currentNode = CSAPI_NULL;
        } else if (localName.equalsIgnoreCase("faultstring")) {
            /* The identifier of the fault been reported. */
            currentNode = CSAPI_NULL;
        } else if (localName.equalsIgnoreCase("csapi_cobject_vector_size")) {
            /* The number of records in the report. */
            currentNode = CSAPI_NULL;
        } else if (localName.equalsIgnoreCase("csapi_cobject_vector_type")) {
            currentNode = CSAPI_NULL;
        } else if (localName.equalsIgnoreCase("csapi_cobject_vector_0")) {
            /*
             * The current record is ok (finished); attach it to the
             * currentRepord.
             */
            currentReport.addRecord(currentRecord);
            currentNode = CSAPI_NULL;
        } else if (localName.equalsIgnoreCase("csapi_cobject_data")) {
            /*
             * The current attribute is ok (finished); attach it to the
             * currentRecord.
             */
            currentRecord.addAttribute(currentAttribute);
            currentNode = CSAPI_NULL;
        } else if (localName.equalsIgnoreCase("csapi_cobject_data_name")) {
            currentNode = CSAPI_NULL;
        } else if (localName.equalsIgnoreCase("csapi_cobject_data_value")) {
            currentNode = CSAPI_NULL;
        } else if (localName.equalsIgnoreCase("csapi_cobject_data_type")) {
            currentNode = CSAPI_NULL;
        }
    }

    /**
     * <p>
     * Event sent when the parser encounters a character outside of a XML tag.
     * We must check that characters returned are real, and not just newline
     * characters (which are not useful).
     * </p>
     * 
     * <p>
     * Depending on the node type defined in the startElement() method, we set
     * values for the element treated. It may be attribute name, value and type,
     * or csapiToken, or any data from the SOAP exchange.
     * </p>
     * 
     * @param ch
     *            The set of characters read.
     * @param start
     *            Rank of the first character read.
     * @param end
     *            Rank of the last character read.
     * @see org.xml.sax.ContentHandler#characters(char[], int, int)
     */
    public void characters(final char[] ch, final int start, final int end) throws SAXException {
        /* Create a String from the set of characters read. */
        String myChars = new String(ch, start, end);

        /* Identify and treat only non-empty strings. */
        if (Pattern.matches(".*\\S+.*", myChars)) {
            switch (currentNode) {
            case CSAPI_RESPONSE:
                csapiToken = myChars;
                break;
            case CSAPI_FAULTCODE:
                faultCode = new Integer(myChars);
                break;
            case CSAPI_FAULTSTRING:
                faultString = myChars;
                break;
            case CSAPI_CQUERY_DATA:
                break;
            case CSAPI_COBJECT_VECTOR_SIZE:
                break;
            case CSAPI_COBJECT_VECTOR_TYPE:
                break;
            case CSAPI_COBJECT_VECTOR_0:
                break;
            case CSAPI_COBJECT_DATA:
                break;
            case CSAPI_COBJECT_DATA_NAME:
                /* The effective name of the attribute. */
                currentAttribute.setName(myChars);
                break;
            case CSAPI_COBJECT_DATA_VALUE:
                /* The effective value of the attribute. */
                currentAttribute.setValue(myChars);
                break;
            case CSAPI_COBJECT_DATA_TYPE:
                /* The effective CCM_TYPE of the attribute. */
                currentAttribute.setType(myChars);
                break;
            default:
            }
        }
    }

    /**
     * Event sent everytime space characters which can be ignored are
     * encountered.
     * 
     * @param ch
     *            The characters read.
     * @param start
     *            Rank of the first character to be read.
     * @param end
     *            Rank of the last character to be read.
     * @see org.xml.sax.ContentHandler#ignorableWhitespace(char[], int, int)
     */
    public void ignorableWhitespace(final char[] ch, final int start, final int end)
            throws SAXException {
        /* Not needed. */
    }

    /**
     * Event sent when a processing instruction is encountered.
     * 
     * @param target
     *            Target of the processing instruction.
     * @param data
     *            Values associated to the target.
     * @see org.xml.sax.ContentHandler#processingInstruction(java.lang.String,
     *      java.lang.String)
     */
    public void processingInstruction(final String target, final String data)
            throws SAXException {
        /* Not needed. */
    }

    /**
     * Event sent when a tag is not processed because of a problem not treated
     * by the parser.
     * 
     * @see org.xml.sax.ContentHandler#skippedEntity(java.lang.String)
     */
    public void skippedEntity(String arg0) throws SAXException {
        /* Not needed. */
    }

    /**
     * Get the csapiToken private attribute.
     * 
     * @return Returns the csapiToken.
     */
    public String getCsapiToken() {
        return csapiToken;
    }

    /**
     * Get the currentReport private attribute.
     * 
     * @return Returns the currentReport object, or an empty report if it is
     *         null.
     */
    public Report getReport() {
        if (currentReport != null) {
            return currentReport;
        }
        return null;
    }

    public int getFaultCode() {
        return this.faultCode.intValue();
    }

    /**
     * @return
     */
    public final String getFaultString() {
        return this.faultString;
    }
}
