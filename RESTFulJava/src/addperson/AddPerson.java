/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package addperson;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

/**
 * Jersey REST client generated for REST resource:AddPersonToServiceResource
 * [addPersonToService]<br>
 * USAGE:
 * <pre>
 *        addPerson client = new addPerson();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Niki
 */
public class AddPerson {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/RESTFulServ/webresources";

    public AddPerson() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("persons/add");
    }

    public String getResult(Integer id, String authkey, String name, String telephone, String email) throws ClientErrorException {
        WebTarget resource = webTarget;
        if(id != null){
            resource = resource.queryParam("id", id);
        }
        if(authkey != null){
            resource = resource.queryParam("authkey", authkey);
        }
        if (name != null) {
            resource = resource.queryParam("name", name);
        }
        if (telephone != null) {
            resource = resource.queryParam("telephone", telephone);
        }
        if (email != null) {
            resource = resource.queryParam("email", email);
        }
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    public void close() {
        client.close();
    }
    
}
