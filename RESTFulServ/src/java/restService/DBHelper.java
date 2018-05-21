/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restService;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;
import org.xml.sax.SAXException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;
import java.util.logging.*;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Niki
 */
public class DBHelper {

    // Connection variable
    public static Connection connection;
    public static DataSource datasource;
    
    public static void init(){
	try {
		InitialContext ic = new InitialContext();
                datasource = (DataSource) ic.lookup("jdbc/agendaRes");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public static void connect_to_db(){
        // Insert database credentials
        try {
            init();
            connection = datasource.getConnection();
            //connection = DriverManager.getConnection ("jdbc:mysql://localhost:3306/rest", "root", "");
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public static String generate_authkey(){
        return sha1(Long.toHexString(Double.doubleToLongBits(Math.random())));
    }
    
    /*
    public static String sample_query(){
        if(connection == null){
                DBHelper.connect_to_db();
            }
        try {
            if (connection == null){
                connect_to_db();
            }
            //String query = String.format("insert into users (email, password, authkey) values ('%s','%s','%s')", "narzew@gmail.com", sha1("ziomek"), sha1("authkey"));
            String query = "insert into users (user_id, name, email, telephone) values (1,'Niki','narzew@gmail.com','123456789')";
            Statement s = connection.createStatement();
            s.executeUpdate (query);
            return query;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }
    */

    public static void do_exec(String query){
        if(connection == null){
                DBHelper.connect_to_db();
            }
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(query);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    /*
    public static String sha1(String text) {
        try {
        byte[] convertme = text.getBytes("utf-8");
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        }
        catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        } 
        return new String(md.digest(convertme));
        } catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
    */
    
    public static String sha1(String input) {
    String sha1 = null;
    try {
        MessageDigest msdDigest = MessageDigest.getInstance("SHA-1");
        msdDigest.update(input.getBytes("UTF-8"), 0, input.length());
        sha1 = DatatypeConverter.printHexBinary(msdDigest.digest());
    } catch (Exception e) {
        e.printStackTrace();
    }
    return sha1;
    }
    
    public static void no_auth_error(){
        System.out.println("No auth!");
    }
    
    public static String add_user(String email, String password){
        if(connection == null){
                DBHelper.connect_to_db();
            }
        try {
            password = sha1(password);
            String authkey = generate_authkey();
            String query = String.format("insert into users (email, password, authkey) values ('%s', '%s', '%s')", email, password, authkey);
            do_exec(query);
            return "Registration succesfull!";
        } catch (Exception e){
            e.printStackTrace();
            return "Error!";
        }
    }
    
    public static String delete_user(Integer id, String authkey){
        if(connection == null){
                DBHelper.connect_to_db();
            }
        try {
            if(authenticate(id,authkey)){
                String query = String.format("delete from users where id = %d", id);
                do_exec(query);
                return "User was deleted!";
            } else {
                no_auth_error();
                return "Access denied!";
            }
        } catch(Exception e){
            e.printStackTrace();
            return "Error!";
        }
    }
    
    public static String modify_user(Integer id, String authkey, String email, String password){
        if(connection == null){
                DBHelper.connect_to_db();
            }
        try {
            if(authenticate(id,authkey)){
                String new_authkey = generate_authkey();
                password = sha1(password);
                String query = String.format("update users set email = '%s', password = '%s', authkey = '%s' where id = %d", email, password, new_authkey, id);
                do_exec(query);
                return "User was modified!";
            } else {
                no_auth_error();
                return "Access denied!";
            }
        } catch(Exception e){
            e.printStackTrace();
            return "Error!";
        }
    }
    
    /*
    public static void get_id(String email){
    } */
    
    public static UserObj login(String email, String password){
        if(connection == null){
                DBHelper.connect_to_db();
            }
        try {
            password = sha1(password);
            String query = String.format("select id,authkey from users where email = '%s' and password = '%s'",email,password);
            Statement st = connection.createStatement();
            java.sql.ResultSet resultSet;
            resultSet = st.executeQuery(query);
            if(resultSet.next()){
                if(resultSet.getString("id")!=null){
                    Integer id = resultSet.getInt("id");
                    String authkey = resultSet.getString("authkey");
                    return new UserObj(id, email, authkey);
                } else {
                    return new UserObj();
                }
            } else {
                return new UserObj();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return new UserObj();
    }
    
    public static Boolean authenticate(Integer id, String authkey){
        if(connection == null){
                DBHelper.connect_to_db();
            }
        try {
            connect_to_db();
            String query = String.format("select email from users where id = %d and authkey = '%s'",id,authkey);
            Statement st = connection.createStatement();
            java.sql.ResultSet resultSet;
            resultSet = st.executeQuery(query);
            if(resultSet.next()){
                if(resultSet.getString("email")!=null){
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    
    public static String get_person_by_id(Integer id, String authkey, Integer person_id){
        if(connection == null){
                DBHelper.connect_to_db();
            }
        try {
            String s = "";
            if(authenticate(id,authkey)){
                String query = String.format("select id,name,email,telephone from persons where user_id = %d and id = %d", id, person_id);
                Statement st = connection.createStatement();
                java.sql.ResultSet resultSet;
                resultSet = st.executeQuery(query);

                while (resultSet.next()) {
                    s=s+"ID: " + resultSet.getString("ID") + " "
                        + "Name: " + resultSet.getString("name") + " "
                        + "E-mail: " + resultSet.getString("email") + " "
                        + "Telephone: " + resultSet.getString("telephone")+"\n";
                }
                return s;
            } else {
                no_auth_error();
                return "Access denied!";
            }
        } catch (Exception e){
            e.printStackTrace();
            return "Error!";
        }
    }
    
    public static String get_person_by_name(Integer id, String authkey, String person_name){
        if(connection == null){
                DBHelper.connect_to_db();
            }
        try {
            String s = "";
            if(authenticate(id,authkey)){
                String query = "select id,name,email,telephone from persons where user_id = "+id+" and name like '%"+person_name+"%'";
                Statement st = connection.createStatement();
                java.sql.ResultSet resultSet;
                resultSet = st.executeQuery(query);

                while (resultSet.next()) {
                    s=s+"ID: " + resultSet.getString("id")+ " "
                        + "Name: " + resultSet.getString("name") + " "
                        + "E-mail: " + resultSet.getString("email") + " "
                        + "Telephone: " + resultSet.getString("telephone")+"\n";
                }
                return s;
            } else {
                return "Access denied!";
            }
        } catch (Exception e){
            e.printStackTrace();
            return "Error!";
        }
    }
    
    public static String add_person(Integer id, String authkey, String name, String email, String telephone){
        if(connection == null){
                DBHelper.connect_to_db();
            }
        if(authenticate(id,authkey)){
            String query = String.format("insert into persons (user_id, name, email, telephone) values (%d, '%s','%s','%s')", id, name, email, telephone);
            do_exec(query);
            return "Person added.";
        } else {
            no_auth_error();
            return "Access denied!";
        }
    }
    
    public static String modify_person(Integer id, String authkey, Integer person_id, String name, String email, String telephone){
        if(connection == null){
                DBHelper.connect_to_db();
            }
        if(authenticate(id,authkey)){
            String query = String.format("update persons set name = '%s', email = '%s', telephone='%s' where id = %d and user_id = %d", name, email, telephone, person_id, id);
            do_exec(query);
            return "Person modified.";
        } else {
            no_auth_error();
            return "Access denied!";
        }
    }
    
    public static String delete_person(Integer id, String authkey, Integer person_id){
        if(connection == null){
                DBHelper.connect_to_db();
            }
        if(authenticate(id,authkey)){
            String query = String.format("delete from persons where id = %d and user_id = %d", person_id, id);
            return "Person deleted.";
        } else {
            return "Access denied!";
        }
    }
    
    public static String get_agenda(Integer id, String authkey){
        try {
            if(connection == null){
                DBHelper.connect_to_db();
            }
            String s = "";
            if(authenticate(id,authkey)){
                String query = String.format("select id,name,email,telephone from persons where user_id = %d", id);
                Statement st = connection.createStatement();
                java.sql.ResultSet resultSet;
                resultSet = st.executeQuery(query);
                while (resultSet.next()) {
                    s=s+"ID: " + resultSet.getString("id") + " "
                        + "Name: " + resultSet.getString("name") + " "
                        + "E-mail: " + resultSet.getString("email") + " "
                        + "Telephone: " + resultSet.getString("telephone")+"\n";
                }
                return s;
            } else {
                no_auth_error();
                return "Access denied!";
            }
        } catch (Exception e){
            e.printStackTrace();
            return "Error!";
        }
    }
    
    public static Agenda get_agenda_obj(){
        try {
            if(connection == null){
                DBHelper.connect_to_db();
            }
            String s = "";
                String query = String.format("select id,name,email,telephone from persons");
                Statement st = connection.createStatement();
                java.sql.ResultSet resultSet;
                resultSet = st.executeQuery(query);
                Agenda agenda_obj = new Agenda();
                Persona p;
                while (resultSet.next()) {
                    agenda_obj.addPerson(new Persona(resultSet.getString("name"),resultSet.getString("email"),resultSet.getString("telephone")));
                }
                return agenda_obj;
        } catch (Exception e){
            e.printStackTrace();
            return new Agenda();
        }
    }
    
    public static String clear_agenda(Integer id, String authkey){
        if(connection == null){
                DBHelper.connect_to_db();
            }
        if(authenticate(id,authkey)){
            String query = String.format("delete from persons where user_id = %d", id);
            do_exec(query);
            return "Agenda was cleared.";
        } else {
            no_auth_error();
            return "Access Denied!";
        }
    }
    
}