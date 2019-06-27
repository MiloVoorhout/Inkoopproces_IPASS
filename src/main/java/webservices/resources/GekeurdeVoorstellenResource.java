package webservices.resources;

import javax.annotation.security.RolesAllowed;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import inkoop.gekeurdevoorstellen.GekeurdeVoorstellen;
import inkoop.gekeurdevoorstellen.GekeurdeVoorstellenDaoImpl;
import inkoop.productvoorstel.ProductVoorstel;


@Path("/gekeurde_voorstellen")
public class GekeurdeVoorstellenResource {
	private GekeurdeVoorstellenDaoImpl gekeurdeVoorstelleDao = new GekeurdeVoorstellenDaoImpl();
	
    @GET
    @Path("/{userId}")
    @Produces("application/json")
    public String getGekeurdeVoorstellen(@PathParam("userId") int id) {
        JsonArrayBuilder jab = Json.createArrayBuilder();
        
        for (GekeurdeVoorstellen gekeurdeVoorstel : gekeurdeVoorstelleDao.findAll(id)) {
            JsonObjectBuilder job = Json.createObjectBuilder();
            
            job.add("id", gekeurdeVoorstel.getId());
            job.add("product", gekeurdeVoorstel.getProduct());
            job.add("status", gekeurdeVoorstel.getStatus());
            job.add("gebruikers_id ", gekeurdeVoorstel.getGebruikers_id());

            jab.add(job);
        }

        JsonArray array = jab.build();
        return array.toString();
    }
    
    @POST
    @Path("/save")
    @Produces("application/json")
    public int addGekeurdeVoorstel(String response) throws ParseException{    	
    	JSONParser parser = new JSONParser();
    	JSONObject json = (JSONObject) parser.parse(response);

    	GekeurdeVoorstellen gekeurdeVoorstel = new GekeurdeVoorstellen(json.get("productNaam").toString(), "In afwachting", Integer.parseInt(json.get("gebruikerId").toString()));

        int saveProductVoorstel = gekeurdeVoorstelleDao.save(gekeurdeVoorstel);
        return saveProductVoorstel;
    }
    
    @PUT
    @Path("/update")
    @Produces("application/json")
    public boolean updateGekeurdeVoorstel(String response) throws ParseException{    	
    	JSONParser parser = new JSONParser();
    	JSONObject json = (JSONObject) parser.parse(response);

    	int gekeurdeVoorstelId = Integer.parseInt(json.get("gkVoorstelId").toString());
    	String gekeurdeVoorstelStatus = json.get("updateStatus").toString();

        boolean updateGekeurdeVoorstel = gekeurdeVoorstelleDao.update(gekeurdeVoorstelId, gekeurdeVoorstelStatus);
        return updateGekeurdeVoorstel;
    }
    
    @DELETE
    @Path("delete/{statusId}")
    @Produces("application/json")
    public Response deleteStatus(@PathParam("statusId") int id) {
        boolean deleted = false;

        JsonObjectBuilder job = Json.createObjectBuilder();
        boolean deleteStatus = gekeurdeVoorstelleDao.delete(id);

        return Response.ok(deleteStatus).build();
    }
}
