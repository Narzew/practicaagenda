/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validarXSD;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

/**
 * Jersey REST client generated for REST resource:ValidarXSDResource
 * [validarXSD]<br>
 * USAGE:
 * <pre>
 *        validarXSD client = new validarXSD();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Niki
 */
public class ValidarXSD {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/RESTFulServ/webresources";

    public ValidarXSD() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("agenda/validarXSD");
    }

    public String getText() throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    public void putText(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.TEXT_PLAIN));
    }

    public void close() {
        client.close();
    }
    
}
