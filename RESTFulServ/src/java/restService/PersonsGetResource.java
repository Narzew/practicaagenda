/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restService;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Niki
 */
@Path("persons/get")
public class PersonsGetResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AddPersonToServiceResource
     */
    public PersonsGetResource() {
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getResult(@DefaultValue("") @QueryParam("id") Integer id,@DefaultValue("") @QueryParam("authkey") String authkey, @DefaultValue("") @QueryParam("person_id") Integer person_id) {
        //TODO return proper representation object
        return DBHelper.get_person_by_id(id, authkey, person_id);
    }
}
