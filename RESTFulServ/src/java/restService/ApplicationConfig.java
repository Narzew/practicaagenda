/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restService;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Sir_D
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(restService.AddPersonToServiceResource.class);
        resources.add(restService.AgendaClearResource.class);
        resources.add(restService.AgendaGetXMLResource.class);
        resources.add(restService.GetAgendaResource.class);
        resources.add(restService.PersonsDeleteResource.class);
        resources.add(restService.PersonsGetByNameResource.class);
        resources.add(restService.PersonsGetResource.class);
        resources.add(restService.PersonsModifyResource.class);
        resources.add(restService.UsersAddResource.class);
        resources.add(restService.UsersDeleteResource.class);
        resources.add(restService.UsersLoginResource.class);
        resources.add(restService.UsersModifyResource.class);
        resources.add(restService.ValidarDTDResource.class);
        resources.add(restService.ValidarXSDResource.class);
    }
    
}
