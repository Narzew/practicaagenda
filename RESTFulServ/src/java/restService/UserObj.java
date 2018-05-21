/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restService;

/**
 *
 * @author Niki
 */
public class UserObj {
    
    public Integer id = 0;
    public String email = "";
    public String authkey = "";

    public UserObj() {
    }
    
    public UserObj(Integer id, String email, String authkey){
        this.id = id;
        this.email = email;
        this.authkey = authkey;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthkey() {
        return authkey;
    }

    public void setAuthkey(String authkey) {
        this.authkey = authkey;
    }
    
    public String toString(){
        return ";;;"+this.id+";;;"+this.authkey+";;;"+this.email;
    }   
    
}
