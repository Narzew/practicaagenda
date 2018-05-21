/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restService;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
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
@Path("users/add")
public class UsersAddResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AddPersonToServiceResource
     */
    public UsersAddResource() {
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getResult(@DefaultValue("") @QueryParam("email") String email, @DefaultValue("") @QueryParam("password") String password) {
        //TODO return proper representation object
        return DBHelper.add_user(email, password);
    }
}
