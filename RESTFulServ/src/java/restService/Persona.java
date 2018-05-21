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
@XmlRootElement(name="Persona")

public class Persona implements Serializable {
    
    @XmlElement(name="name")
    private String name;
    @XmlElement(name="email")
    private String email;
    @XmlElement(name="telephone")
    private String telephone;
    
    public Persona(){
        this.name = "";
        this.email = "";
        this.telephone = "";
    }

    public Persona(String name, String email, String telephone) {
        this.name = name;
        this.email = email;
        this.telephone = telephone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    
    public String toString(){
        String s = ""+ this.name + " - " + this.email + ", tlf: " + this.telephone + "\n";
        return s;
    }
    
}
