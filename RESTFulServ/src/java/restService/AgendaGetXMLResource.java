/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restService;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Niki
 */
@Path("agenda/getXML")
public class AgendaGetXMLResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ValidarXSDResource
     */
    public AgendaGetXMLResource() {
    }

    /**
     * Retrieves representation of an instance of restService.ValidarXSDResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getText() {
        Engine.createAgendaXSDFile();
        Engine.export_agenda_from_mysql();
        return Engine.read_agenda_file();
    }

    /**
     * PUT method for updating or creating an instance of ValidarXSDResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    public void putText(String content) {
    }
}
