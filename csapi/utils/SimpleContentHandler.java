package csapi.utils;

import java.util.regex.Pattern;

import org.xml.sax.*;
import org.xml.sax.helpers.LocatorImpl;

import csapi.core.Attribute;
import csapi.core.Record;
import csapi.core.Report;

/**
 * @author boris
 * 
 * Exemple d'implementation extremement simplifiee d'un SAX XML ContentHandler.
 * Le but de cet exemple est purement pedagogique. Very simple implementation
 * sample for XML SAX ContentHandler.
 */
public class SimpleContentHandler implements ContentHandler {

	private String csapiToken;

	private Report currentReport;
	private Record currentRecord;
	private Attribute currentAttribute;

	private int currentNode;

	private Locator locator;

	final static int CSAPI_NULL = 0;
	
	final static int FAULT = 5;
	final static int FAULTCODE = 2;
	final static int FAULTSTRING = 3;
	
	final static int CSAPI_RESPONSE = 1;
	final static int CSAPI_CQUERY_DATA = 4;
	final static int CSAPI_COBJECT_DATA = 10;
	final static int CSAPI_COBJECT_VECTOR_SIZE = 11;
	final static int CSAPI_COBJECT_VECTOR_TYPE = 12;
	final static int CSAPI_COBJECT_VECTOR_POSITION = 13;
	final static int CSAPI_COBJECT_VECTOR_0 = 14;
	final static int CSAPI_COBJECT_DATA_SIZE = 141;
	final static int CSAPI_COBJECT_VECTOR_TRANSITIONS = 142;
	final static int CSAPI_COBJECT_DATA_NAME = 101;
	final static int CSAPI_COBJECT_DATA_VALUE = 102;
	final static int CSAPI_COBJECT_DATA_TYPE = 103;
	final static int CSAPI_COBJECT_DATA_READONLY = 104;
	final static int CSAPI_COBJECT_DATA_REQUIRED = 105;

	/**
	 * Constructeur par defaut.
	 */
	public SimpleContentHandler() {
		super();
		// On definit le locator par defaut.
		locator = new LocatorImpl();
	}

	/**
	 * Definition du locator qui permet a tout moment pendant l'analyse, de
	 * localiser le traitement dans le flux. Le locator par defaut indique, par
	 * exemple, le numero de ligne et le numero de caractere sur la ligne.
	 * 
	 * @author smeric
	 * @param value
	 *            le locator a utiliser.
	 * @see org.xml.sax.ContentHandler#setDocumentLocator(org.xml.sax.Locator)
	 */
	public void setDocumentLocator(Locator value) {
		locator = value;
	}

	/**
	 * Evenement envoye au demarrage du parse du flux xml.
	 * 
	 * @throws SAXException
	 *             en cas de probleme quelquonque ne permettant pas de se lancer
	 *             dans l'analyse du document.
	 * @see org.xml.sax.ContentHandler#startDocument()
	 */
	public void startDocument() throws SAXException {
//		System.out.println("Debut de l'analyse du document");
	}

	/**
	 * Evenement envoye a la fin de l'analyse du flux xml.
	 * 
	 * @throws SAXException
	 *             en cas de probleme quelquonque ne permettant pas de
	 *             considerer l'analyse du document comme etant complete.
	 * @see org.xml.sax.ContentHandler#endDocument()
	 */
	public void endDocument() throws SAXException {
//		System.out.println("Fin de l'analyse du document");
	}

	/**
	 * Debut de traitement dans un espace de nommage.
	 * 
	 * @param prefixe
	 *            utilise pour cet espace de nommage dans cette partie de
	 *            l'arborescence.
	 * @param URI
	 *            de l'espace de nommage.
	 * @see org.xml.sax.ContentHandler#startPrefixMapping(java.lang.String,
	 *      java.lang.String)
	 */
	public void startPrefixMapping(String prefix, String URI)
			throws SAXException {
		//System.out.println("Traitement de l'espace de nommage : " + URI
		//		+ ", prefixe choisi : " + prefix);
	}

	/**
	 * Fin de traitement de l'espace de nommage.
	 * 
	 * @param prefixe
	 *            le prefixe choisi a l'ouverture du traitement de l'espace
	 *            nommage.
	 * @see org.xml.sax.ContentHandler#endPrefixMapping(java.lang.String)
	 */
	public void endPrefixMapping(String prefix) throws SAXException {
		//System.out.println("Fin de traitement de l'espace de nommage : "
		//		+ prefix);
	}

	/**
	 * Evenement recu a chaque fois que l'analyseur rencontre une balise xml
	 * ouvrante.
	 * 
	 * @param nameSpaceURI
	 *            l'url de l'espace de nommage.
	 * @param localName
	 *            le nom local de la balise.
	 * @param rawName
	 *            nom de la balise en version 1.0
	 *            <code>nameSpaceURI + ":" + localName</code>
	 * @throws SAXException
	 *             si la balise ne correspond pas a ce qui est attendu, comme
	 *             par exemple non respect d'une dtd.
	 * @see org.xml.sax.ContentHandler#startElement(java.lang.String,
	 *      java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement(String nameSpaceURI, String localName,
			String rawName, Attributes attributs) throws SAXException {
		
		// localName is always empty if we do not treat namespace.
		localName = rawName;
//		System.out.println("DEBUG ouv : " + localName);

		/*
		 * let's begin the string switch statement. since java does not permit
		 * to switch/case on String, we have to affect the currentNode attribute
		 * by hand (i.e. with else/if's).
		 */
		if (localName.equalsIgnoreCase("csapi_response")) {
			// the tag sometimes contains the csapi_token
			currentNode = CSAPI_RESPONSE;
		} else if (localName.equalsIgnoreCase("csapi_cquery_data")) {
			currentNode = CSAPI_CQUERY_DATA;
			
			// create a Report object
			currentReport = new Report();
			
		} else if (localName.equalsIgnoreCase("csapi_cobject_vector_size")) {
			currentNode = CSAPI_COBJECT_VECTOR_SIZE;
			// the number of records in the report
		} else if (localName.equalsIgnoreCase("csapi_cobject_vector_type")) {
			currentNode = CSAPI_COBJECT_VECTOR_TYPE;
		} else if (localName.equalsIgnoreCase("csapi_cobject_vector_0")) {
			currentNode = CSAPI_COBJECT_VECTOR_0;
			
			// create a Record Object
			currentRecord = new Record();
			
		} else if (localName.equalsIgnoreCase("csapi_cobject_data")) {
			currentNode = CSAPI_COBJECT_DATA;
			
			// create an Attribute object
			currentAttribute = new Attribute();
			
		} else if (localName.equalsIgnoreCase("csapi_cobject_data_name")) {
			currentNode = CSAPI_COBJECT_DATA_NAME;
			// the name of the attribute
		} else if (localName.equalsIgnoreCase("csapi_cobject_data_value")) {
			currentNode = CSAPI_COBJECT_DATA_VALUE;
			// the value of the attribute
		} else if (localName.equalsIgnoreCase("csapi_cobject_data_type")) {
			currentNode = CSAPI_COBJECT_DATA_TYPE;
			// the CCM_TYPE of the attribute
		} else if (localName.equalsIgnoreCase("csapi_cobject_data_size")) {
			currentNode = CSAPI_COBJECT_DATA_SIZE;
			// the number of attributes for this record
		}
	}

	/**
	 * Evenement recu a chaque fermeture de balise.
	 * 
	 * @see org.xml.sax.ContentHandler#endElement(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public void endElement(String nameSpaceURI, String localName, String rawName)
			throws SAXException {
		
		// localName is always empty if we do not treat namespace.
		localName = rawName;
		
//		System.out.println("DEBUG ferm : " + localName);
		if (localName.equalsIgnoreCase("csapi_response")) {
		} else if (localName.equalsIgnoreCase("csapi_cquery_data")) {
			// currentReport is ok
			currentNode = CSAPI_NULL;
		} else if (localName.equalsIgnoreCase("csapi_cobject_vector_size")) {
			// the number of records in the report: test it?
			currentNode = CSAPI_NULL;
		} else if (localName.equalsIgnoreCase("csapi_cobject_vector_type")) {
			currentNode = CSAPI_NULL;
		} else if (localName.equalsIgnoreCase("csapi_cobject_vector_0")) {
			// attach the currentRecord
			currentReport.addRecord(currentRecord);
			currentNode = CSAPI_NULL;
		} else if (localName.equalsIgnoreCase("csapi_cobject_data")) {
			// attach the currentAttribute
			currentRecord.addAttribute(currentAttribute);
			currentNode = CSAPI_NULL;
		} else if (localName.equalsIgnoreCase("csapi_cobject_data_name")) {
			currentNode = CSAPI_NULL;
		} else if (localName.equalsIgnoreCase("csapi_cobject_data_value")) {
			currentNode = CSAPI_NULL;
		} else if (localName.equalsIgnoreCase("csapi_cobject_data_type")) {
			currentNode = CSAPI_NULL;
		} else if (localName.equalsIgnoreCase("csapi_cobject_data_size")) {
			// the number of attributes for this record: test it ?
			currentNode = CSAPI_NULL;
		}
	}

	/**
	 * Evenement recu a chaque fois que l'analyseur rencontre des caracteres
	 * (entre deux balises).
	 * 
	 * @param ch
	 *            les caracteres proprement dits.
	 * @param start
	 *            le rang du premier caractere a traiter effectivement.
	 * @param end
	 *            le rang du dernier caractere a traiter effectivement
	 * @see org.xml.sax.ContentHandler#characters(char[], int, int)
	 */
	public void characters(char[] ch, int start, int end) throws SAXException {
		String myChars = new String(ch, start, end);
		//		if (end != 1)
		//			System.out.println("#PCDATA [" + start + "|"
		//					+ end + "] : " + myCsapiToken);
		if (Pattern.matches(".*\\S+.*", myChars)) {
			switch (currentNode) {
			case CSAPI_RESPONSE:
				csapiToken = myChars;
				break;
			case CSAPI_CQUERY_DATA:
				// create a Report object
				break;
			case CSAPI_COBJECT_VECTOR_SIZE:
				// the number of records in the report
				break;
			case CSAPI_COBJECT_VECTOR_TYPE:
				break;
			case CSAPI_COBJECT_VECTOR_0:
				// create a Record Object
				break;
			case CSAPI_COBJECT_DATA:
				// create an Attribute object
				break;
			case CSAPI_COBJECT_DATA_NAME:
				// the name of the attribute
				currentAttribute.setName(myChars);
//				System.out.println("Att.setName(" + myChars + ")");
				break;
			case CSAPI_COBJECT_DATA_VALUE:
				// the value of the attribute
				currentAttribute.setValue(myChars);
//				System.out.println("Att.setValue(" + myChars + ")");
				break;
			case CSAPI_COBJECT_DATA_TYPE:
				// the CCM_TYPE of the attribute
				currentAttribute.setType(myChars);
				break;
			case CSAPI_COBJECT_DATA_SIZE:
				// the number of attributes for this record
				break;
			default:
			}
		}
	}

	/**
	 * Recu chaque fois que des caracteres d'espacement peuvent etre ignores au
	 * sens de XML. C'est a dire que cet evenement est envoye pour plusieurs
	 * espaces se succedant, les tabulations, et les retours chariot se
	 * succedants ainsi que toute combinaison de ces trois types d'occurrence.
	 * 
	 * @param ch
	 *            les caracteres proprement dits.
	 * @param start
	 *            le rang du premier caractere a traiter effectivement.
	 * @param end
	 *            le rang du dernier caractere a traiter effectivement
	 * @see org.xml.sax.ContentHandler#ignorableWhitespace(char[], int, int)
	 */
	public void ignorableWhitespace(char[] ch, int start, int end)
			throws SAXException {
//		System.out.println("espaces inutiles rencontres : ..."
//				+ new String(ch, start, end) + "...");
	}

	/**
	 * Rencontre une instruction de fonctionnement.
	 * 
	 * @param target
	 *            la cible de l'instruction de fonctionnement.
	 * @param data
	 *            les valeurs associees a cette cible. En general, elle se
	 *            presente sous la forme d'une serie de paires nom/valeur.
	 * @see org.xml.sax.ContentHandler#processingInstruction(java.lang.String,
	 *      java.lang.String)
	 */
	public void processingInstruction(String target, String data)
			throws SAXException {
//		System.out.println("Instruction de fonctionnement : " + target);
//		System.out.println("  dont les arguments sont : " + data);
	}

	/**
	 * Recu a chaque fois qu'une balise est evitee dans le traitement a cause
	 * d'un probleme non bloque par le parser. Pour ma part je ne pense pas que
	 * vous en ayez besoin dans vos traitements.
	 * 
	 * @see org.xml.sax.ContentHandler#skippedEntity(java.lang.String)
	 */
	public void skippedEntity(String arg0) throws SAXException {
		// Je ne fais rien, ce qui se passe n'est pas franchement normal.
		// Pour eviter cet evenement, le mieux est quand meme de specifier une
		// dtd pour vos documents xml et de les faire valider par votre parser.
	}

	/**
	 * @return Returns the csapiToken.
	 */
	public String getCsapiToken() {
		return csapiToken;
	}

	/**
	 * @return Returns the currentReport object.
	 */
	public Report getReport() {
		return currentReport;
	}
	
	public Locator getLocator() {
		return locator;
	}
}