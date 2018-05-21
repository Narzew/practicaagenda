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
@Path("agenda/validarDTD")
public class ValidarDTDResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ValidarDTDResource
     */
    public ValidarDTDResource() {
    }

    /**
     * Retrieves representation of an instance of restService.ValidarDTDResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getText() {
        //TODO return proper representation object
        Engine.createAgendaDTDFile();
        Engine.export_agenda_from_mysql();
        return Engine.validar_xml_with_dtd("Agenda.xml");
    }

    /**
     * PUT method for updating or creating an instance of ValidarDTDResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    public void putText(String content) {
    }
}
