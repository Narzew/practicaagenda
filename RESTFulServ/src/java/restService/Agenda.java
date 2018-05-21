/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restService;

import java.io.Serializable;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Nikodem Solarz
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Agenda")

public class Agenda implements Serializable {
    
    @XmlElement(name="Persona")
    private ArrayList<Persona> persons;
    
    public Agenda(){
        this.persons = new ArrayList<Persona>();
    }
    
    public Agenda(ArrayList<Persona> personas){
        this.persons = personas;
    }

    public ArrayList<Persona> getPersons() {
        return persons;
    }
    
    // Add person
    public void addPerson(Persona person){
        persons.add(person);
    }

    public void setPersons(ArrayList<Persona> persons) {
        this.persons = persons;
    }
    
    public String toString(){
        if(persons.size() == 0){
            return "Nothing to display!\n";
        } else {
            String s = "";
            for(Persona p : persons){
               s = s+p.toString()+"<br/>";       
            }
            return s;
        }
    }
    
}
