package webservices.resources;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import inkoop.gebruiker.Gebruiker;

@Path("/gebruiker")
public class GebruikerResource {
	
	@GET
    @Path("{statusId}")
    @Produces("application/json")
    public String getUser(@PathParam("statusId") int id) {
    	/*Make a connection and create a Json array*/
    	InkoopService inkoopService = ServiceProvider.getInkoopService();
    	JsonArrayBuilder jab = Json.createArrayBuilder();
        
        for (Gebruiker user : inkoopService.getUser(id)) {
        	/*Make for every user a Json and add it to the arraylist*/
            JsonObjectBuilder job = Json.createObjectBuilder();
            
            job.add("id", user.getId());
            job.add("naam", (user.getFirstName() + " " + user.getLastname()));
            job.add("afdeling", user.getDepartment());

            jab.add(job);
        }

        JsonArray array = jab.build();
        return array.toString();
    }
}
