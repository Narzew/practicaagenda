/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restexampleclassclientjava;

import addperson.AddPerson;
import agenda.AgendaClear;
import agenda.AgendaGetXML;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import getagenda.GetAgenda;
import java.util.Scanner;
import persons.PersonsDelete;
import persons.PersonsGet;
import persons.PersonsGetByName;
import persons.PersonsModify;
import users.UsersAdd;
import users.UsersDelete;
import users.UsersLogin;
import users.UsersModify;
import validarDTD.ValidarDTD;
import validarXSD.ValidarXSD;

/**
 * @author Niki
 */

public class RESTExampleClassClientJava {

    public static Integer global_user_id=0;
    public static String global_user_authkey="";
    public static Boolean global_user_auth=false;
    
    public static void get_agenda(){
        GetAgenda getAgendaClient = new GetAgenda();
        System.out.println(getAgendaClient.getResult(global_user_id, global_user_authkey));
    };

    public static void validate_person_by_xsd(){
    }
    
    public static void validate_agenda_by_xsd(){
        ValidarXSD validarXSDClient = new ValidarXSD();
        System.out.println(validarXSDClient.getText());
    };
    public static void validate_agenda_by_dtd(){
        ValidarDTD validarDTDClient = new ValidarDTD();
        System.out.println(validarDTDClient.getText());
    };
    
    public static void get_person_by_name(){
        String person_name;
        System.out.println("Enter person name: ");
        Scanner scanner = new Scanner(System.in);
        person_name = scanner.nextLine();
        
        PersonsGetByName personsGetByNameClient = new PersonsGetByName();
        System.out.println(personsGetByNameClient.getResult(global_user_id, global_user_authkey, person_name));
    }
    
    public static void get_html_from_agenda(){
        AgendaGetXML agendaXMLClient = new AgendaGetXML();
        System.out.println(agendaXMLClient.getText());
    }
    
    public static void crud_agenda_add(){
        String name="",email="",telephone="";
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter name: ");
        name = scanner.nextLine();
        while(Engine.check_email_address(email)==false){
            System.out.println("Enter e-mail: ");
            email = scanner.nextLine();
        }
        System.out.println("Enter telephone number: ");
        telephone = scanner.nextLine();
        AddPerson addPersonClient = new AddPerson();
        System.out.println(addPersonClient.getResult(global_user_id, global_user_authkey, name, telephone, email));
    }
    
    public static void crud_agenda_get(){
        Integer person_id;
        System.out.println("Enter person ID: ");
        Scanner scanner = new Scanner(System.in);
        person_id = Integer.parseInt(scanner.nextLine());
        
        PersonsGet personsGetClient = new PersonsGet();
        System.out.println(personsGetClient.getResult(global_user_id, global_user_authkey, person_id));
    }
    
    public static void crud_agenda_modify(){
        Integer person_id;
        String name="",email="",telephone="";
        System.out.println("Enter person ID: ");
        Scanner scanner = new Scanner(System.in);
        person_id = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter name: ");
        name = scanner.nextLine();
        while(Engine.check_email_address(email)==false){
            System.out.println("Enter e-mail: ");
            email = scanner.nextLine();
        }
        System.out.println("Enter telephone number: ");
        telephone = scanner.nextLine();
        
        PersonsModify personsModifyClient = new PersonsModify();
        System.out.println(personsModifyClient.getResult(global_user_id, global_user_authkey, name, email, telephone));
    }
    
    public static void crud_agenda_delete(){
        Integer person_id;
        System.out.println("Enter person ID: ");
        Scanner scanner = new Scanner(System.in);
        person_id = Integer.parseInt(scanner.nextLine());
        PersonsDelete personsDeleteClient = new PersonsDelete();
        System.out.println(personsDeleteClient.getResult(global_user_id, global_user_authkey, person_id));
    }
    
    public static void add_user(){
        String user_email;
        String user_password;
        System.out.println("Enter e-mail: ");
        Scanner scanner = new Scanner(System.in);
        user_email = scanner.nextLine();
        System.out.println("Enter password: ");
        user_password = scanner.nextLine();
        UsersAdd usersAddClient = new UsersAdd();
        System.out.println(usersAddClient.getResult(user_email, user_password));
    }
    
    public static void modify_user(){
        String user_email;
        String user_password;
        System.out.println("Enter new e-mail: ");
        Scanner scanner = new Scanner(System.in);
        user_email = scanner.nextLine();
        System.out.println("Enter new password: ");
        user_password = scanner.nextLine();
        
        UsersModify usersModifyClient = new UsersModify();
        System.out.println(usersModifyClient.getResult(global_user_id, global_user_authkey, user_email, user_password));
        logout();
    }
    
    public static void delete_user(){
        UsersDelete usersDeleteClient = new UsersDelete();
        System.out.println(usersDeleteClient.getResult(global_user_id, global_user_authkey));
        logout();
    }
    
    public static void clear_user_agenda(){
        AgendaClear agendaClearClient = new AgendaClear();
        System.out.println(agendaClearClient.getResult(global_user_id, global_user_authkey));
    }
    
    /**
     * @param args the command line arguments
     */
    
    
    public static void main_menu_with_login() {
        System.out.println("RESTFul Java Client\n"+
                "1 - logout\n"+
                "2 - validate agenda by XSD\n"+
                "3 - validate agenda by DTD\n"+
                "4 - get Agenda\n"+
                "5 - get Person by name\n"+
                "6 - get HTML from Agenda\n"+
                "7 - CRUD Agenda: Add Person\n"+
                "8 - CRUD Agenda: Get Person by ID\n"+
                "9 - CRUD Agenda: Modify Person\n"+
                "10 - CRUD Agenda: Delete Person\n"+
                "11 - Modify user\n"+
                "12 - Clear user agenda\n"+
                "13 - Delete user\n"+
                "0 - exit\n");
        Scanner scanner = new Scanner(System.in);
        int numero = Integer.parseInt(scanner.nextLine());
        switch(numero){
            case 0:
                System.exit(0);
                break;
            case 1:
                logout();
                break;
            case 2:
                validate_agenda_by_xsd();
                break;
            case 3:
                validate_agenda_by_dtd();
                break;
            case 4:
                get_agenda();
                break;
            case 5:
                get_person_by_name();
                break;
            case 6:
                get_html_from_agenda();
                break;
            case 7:
                crud_agenda_add();
                break;
            case 8:
                crud_agenda_get();
                break;
            case 9:
                crud_agenda_modify();
                break;
            case 10:
                crud_agenda_delete();
                break;
            case 11:
                modify_user();
                break;
            case 12:
                clear_user_agenda();
                break;
            case 13:
                delete_user();
                break;
            default:
                System.out.println("Wrong choice!\n");
        }
    }
    
    public static void main_menu_without_login() {
        System.out.println("RESTFul Java Client\n"+
                "1 - login\n"+
                "2 - register\n"+
                "3 - validate agenda by XSD\n"+
                "4 - validate agenda by DTD\n"+
                "0 - exit\n");
        Scanner scanner = new Scanner(System.in);
        int numero = Integer.parseInt(scanner.nextLine());
        switch(numero){
            case 0:
                System.exit(0);
                break;
            case 1:
                login();
                break;
            case 2:
                add_user();
                break;
            case 3:
                validate_agenda_by_xsd();
                break;
            case 4:
                validate_agenda_by_dtd();
                break;
            default:
                System.out.println("Wrong choice!\n");
        }
    }
    
    public static void login(){
        String email, password;
        System.out.println("Enter e-mail: ");
        Scanner scanner = new Scanner(System.in);
        email = scanner.nextLine();
        System.out.println("Enter password: ");
        password = scanner.nextLine();
        
        try {
        UsersLogin usersLoginClient = new UsersLogin();
        String result = usersLoginClient.getResult(email, password);
        String[] parts = result.split(";;;");
        Integer id = Integer.parseInt(parts[1]);
        String authkey = parts[2];
        String uemail = parts[3];
        if(uemail.equals(email)){
            global_user_id = id;
            global_user_authkey = authkey;
            global_user_auth = true;
            main_menu_with_login();
        } else {
            System.out.println("Auth failed.");
        }
        } catch (Exception e){
            System.out.println("Auth failed.");
        }
        //System.out.println(String.format("ID: %d, Authkey: %s",id, authkey));
    }
    
    public static void logout(){
        global_user_id = 0;
        global_user_authkey = "";
        global_user_auth = false;
        main_menu_without_login();
    }
    
    public static void main_menu(){
        if(global_user_auth==true){
            main_menu_with_login();
        } else {
            main_menu_without_login();
        }
    }
        
    public static void main(String[] args) {
        for(;;){
            main_menu();
        }
    }
    
}
