/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package users;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

/**
 * Jersey REST client generated for REST resource:UsersAddResource
 * [users/add]<br>
 * USAGE:
 * <pre>
 *        UsersAdd client = new UsersAdd();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Niki
 */
public class UsersAdd {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/RESTFulServ/webresources";

    public UsersAdd() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("users/add");
    }

    public String getResult(String email, String password) throws ClientErrorException {
        WebTarget resource = webTarget;
        if (email != null) {
            resource = resource.queryParam("email", email);
        }
        if (password != null) {
            resource = resource.queryParam("password", password);
        }
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    public void close() {
        client.close();
    }
    
}
