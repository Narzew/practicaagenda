/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restService;

import java.io.BufferedReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;
import org.xml.sax.SAXException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

/**
 *
 * @author Niki
 */
public class Engine {
    
    public static String AGENDA_FILE_NAME = "agenda.xml";
    public static Agenda agenda = new Agenda();
    
    public static Boolean exception_occured = false;
    private static SchemaFactory schemaFactory;
    static {
       schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    }
    
    /*
    public static boolean check_email_address(String email){
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }*/
    
    public static boolean check_email_address(String email){
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
    
    public static boolean file_exist(String filename){
        return new File(filename).isFile();
    }
    
    public static void validar_xml(String filename){
        try {
            File plik = new File(filename);
            if(plik.exists() && !plik.isDirectory()){
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = (Document)dBuilder.parse(plik);
                System.out.println("This .xml file is well-formed!");
            } else {
                throw new IOException();
            }
        } catch(IOException e){
            System.out.println("File don't exist!");
        } catch(Exception e){
            System.out.println("Error occured! XML is not well-formed!");
        }
        
    }
    
    public static String validar_xml_with_dtd(String filename){
        exception_occured = false;
        try {
            File plik = new File(filename);
            if(plik.exists() && !plik.isDirectory()){
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                dbFactory.setValidating(true);
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                dBuilder.setErrorHandler(new ErrorHandler() {
                @Override
                public void error(SAXParseException exception) throws SAXException {
                    // do something more useful in each of these handlers
                    exception_occured = true;
                    System.out.println(exception.getMessage());
                }
                @Override
                public void fatalError(SAXParseException exception) throws SAXException {
                    exception_occured = true;
                    System.out.println(exception.getMessage());
                }
        
                @Override
                public void warning(SAXParseException exception) throws SAXException {
                    exception_occured = true;
                    System.out.println(exception.getMessage());
                }
                });
                
                Document doc = (Document)dBuilder.parse(plik);
                if(exception_occured){
                    System.out.println("Something is wrong with this .xml!");
                    return "Something is wrong with this .xml!";
                } else {
                    System.out.println("This .xml file is correct!");
                    return "This .xml file is correct!";
                }
            } else {
                throw new IOException();
            }
        } catch(IOException e){
            System.out.println("File not found!");
            return "File not found!";
        } catch(Exception e){
            System.out.println("Something is wrong with this .xml!");
            return "Something is wrong with this .xml";
        }
        
    }
    
    /** 
     * Method used to validate XML
     **/

    public static String validar_xml_with_xsd(String xsdPath, String xmlPath){
        
        try {
            SchemaFactory factory = 
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
        } catch (IOException | SAXException e) {
            System.out.println("Exception: "+e.getMessage());
            return "Exception: "+e.getMessage();
        }
        return "Everything is OK with this XML!";
    }
    
    public static void export_agenda(String filename){
        try {
            JAXBContext jaxbC = JAXBContext.newInstance(Agenda.class);
            Marshaller jaxbM = jaxbC.createMarshaller();
            jaxbM.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
            jaxbM.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            jaxbM.setProperty("com.sun.xml.internal.bind.xmlHeaders", 
  "\n<!DOCTYPE Agenda SYSTEM  \"Agenda.dtd\">");
            File output_file = new File(filename);
            jaxbM.marshal(agenda, output_file);
            System.out.println("Write successful.");
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public static void export_agenda_from_mysql(){
        agenda = DBHelper.get_agenda_obj();
        export_agenda("Agenda2.xml");
    }
    
    public static String read_agenda_file(){
        try {
            agenda = DBHelper.get_agenda_obj();
            export_agenda("Agenda2.xml");
            File file = new File("Agenda2.xml");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            String s = "";
            while ((st = br.readLine()) != null){
                s = s+st+"\n";
            }
            return s;
        } catch (Exception e){
            e.printStackTrace();
            return "";
        }
        
    }
    
    public static Agenda import_agenda(String filename){
        if(Engine.file_exist(filename)){
            // Import data
            try {
                JAXBContext jaxbC = JAXBContext.newInstance(Agenda.class);
                Unmarshaller jaxbU = jaxbC.createUnmarshaller();
                File agendaFile = new File(filename);
                agenda = (Agenda)jaxbU.unmarshal(agendaFile);
                System.out.println("Import successfull.");
                return agenda;
            } catch(Exception e){
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("File don't exist!");
            System.out.println("Import failed.");
        }
        return new Agenda();
    }
    
    public static String add_person(String name, String email, String telephone){
        
        if(Engine.check_email_address(email)){
            Persona p = new Persona(name, email, telephone);
            agenda.addPerson(p);
            export_agenda("agenda.xml");
            return "Person was added!";
        } else {
            System.out.println("E-mail incorrect! Person wasn't added.");
            return "E-mail incorrect! Person wasn't added!";
        }
        
    }
    
    public static String list_persons(){
        return agenda.toString();
    }
    
    public static String cleanDatabase(){
        try{
            agenda = new Agenda();
            File file = new File("agenda.xml");
            if(file.delete()){
                return "Database cleaned.";
            } else {
                return "Fail to clean database.";
            }
            
        }catch(Exception e){
            return "Error: "+e.getMessage();
        }
    }
    
    public static void createAgendaDTDFile(){
        try {
            PrintWriter pw = new PrintWriter("Agenda.dtd", "UTF-8");
            pw.write("<?xml encoding=\"UTF-8\"?>\n" +
"\n" +
"<!ELEMENT Agenda (Persona)>\n" +
"<!ATTLIST Agenda\n" +
"  xmlns CDATA #FIXED ''>\n" +
"\n" +
"<!ELEMENT Persona (name,email,telephone)>\n" +
"<!ATTLIST Persona\n" +
"  xmlns CDATA #FIXED ''>\n" +
"\n" +
"<!ELEMENT name (#PCDATA)>\n" +
"<!ATTLIST name\n" +
"  xmlns CDATA #FIXED ''>\n" +
"\n" +
"<!ELEMENT email (#PCDATA)>\n" +
"<!ATTLIST email\n" +
"  xmlns CDATA #FIXED ''>\n" +
"\n" +
"<!ELEMENT telephone (#PCDATA)>\n" +
"<!ATTLIST telephone\n" +
"  xmlns CDATA #FIXED ''>");
            pw.close();
        } catch(Exception e){
            System.out.println("Exception occured: "+e.getMessage());
        }
    }
    
    public static void createAgendaXSDFile(){
        try {
            PrintWriter pw = new PrintWriter("Agenda.xsd", "UTF-8");
            pw.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
"<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n" +
"<xs:simpleType name=\"emailAddress\">\n" +
"<xs:restriction base=\"xs:token\">\n" +
"<xs:maxLength value=\"254\"/>\n" +
"<xs:pattern value=\"[_\\-a-zA-Z0-9\\.\\+]+@[a-zA-Z0-9](\\.?[\\-a-zA-Z0-9]*[a-zA-Z0-9])*\"/>\n" +
"</xs:restriction>\n" +
"</xs:simpleType>\n" +
"<xs:complexType name=\"Persona\">\n" +
"<xs:sequence>\n" +
"<xs:element name=\"name\" type=\"xs:string\"/>\n" +
"<xs:element name=\"email\" type=\"emailAddress\"/>\n" +
"<xs:element name=\"telephone\" type=\"xs:string\"/>\n" +
"<xs:any namespace=\"##any\" minOccurs=\"0\" maxOccurs=\"unbounded\" processContents=\"lax\"/>\n" +
"</xs:sequence>\n" +
"</xs:complexType>\n" +
"<xs:element name=\"Agenda\">\n" +
"<xs:complexType>\n" +
"<xs:sequence>\n" +
"<xs:element name=\"Persona\" type=\"Persona\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>\n" +
"</xs:sequence>\n" +
"</xs:complexType>\n" +
"</xs:element>\n" +
"<xs:element name=\"Persona\" type=\"Persona\"/>\n" +
"</xs:schema>");
            pw.close();
        } catch(Exception e){
            System.out.println("Exception occured: "+e.getMessage());
        }
    }
    
}