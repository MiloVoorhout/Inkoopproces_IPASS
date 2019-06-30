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


@Path("/gekeurde_voorstellen")
public class GekeurdeVoorstellenResource {
	
    @GET
    @Path("/{userId}")
    @Produces("application/json")
    public String getGekeurdeVoorstellen(@PathParam("userId") int id) {
    	InkoopService inkoopService = ServiceProvider.getInkoopService();
        JsonArrayBuilder jab = Json.createArrayBuilder();
        
        for (GekeurdeVoorstellen approvedProposal : inkoopService.getGekeurdeVoorstellen(id)) {
            JsonObjectBuilder job = Json.createObjectBuilder();
            
            job.add("id", approvedProposal.getId());
            job.add("product", approvedProposal.getProduct());
            job.add("status", approvedProposal.getStatus());
            job.add("gebruikers_id ", approvedProposal.getUser_id());

            jab.add(job);
        }

        JsonArray array = jab.build();
        return array.toString();
    }
    
    @POST
    @Path("/save")
    @Produces("application/json")
    public int addGekeurdeVoorstel(String response) throws ParseException{   
    	InkoopService inkoopService = ServiceProvider.getInkoopService();
    	JSONParser parser = new JSONParser();
    	JSONObject json = (JSONObject) parser.parse(response);

    	GekeurdeVoorstellen approvedProposal = new GekeurdeVoorstellen(json.get("productNaam").toString(), "In afwachting", Integer.parseInt(json.get("gebruikerId").toString()));

        int saveApprovedProposal = inkoopService.addGekeurdeVoorstel(approvedProposal);
        return saveApprovedProposal;
    }
    
    @PUT
    @Path("/update")
    @Produces("application/json")
    @RolesAllowed({"Voorstel manager", "Budget manager"})
    public boolean updateGekeurdeVoorstel(String response) throws ParseException{  
    	InkoopService inkoopService = ServiceProvider.getInkoopService();
    	JSONParser parser = new JSONParser();
    	JSONObject json = (JSONObject) parser.parse(response);

    	int approvedProposalId = Integer.parseInt(json.get("gkVoorstelId").toString());
    	String approvedProposalStatus = json.get("updateStatus").toString();

        boolean updateApprovedProposal = inkoopService.updateGekeurdeVoorstel(approvedProposalId, approvedProposalStatus);
        return updateApprovedProposal;
    }
    
    @PUT
    @Path("/update_product")
    @Produces("application/json")
    @RolesAllowed({"Voorstel manager", "Budget manager"})
    public boolean updateNameGekeurdeVoorstel(String response) throws ParseException{    
    	InkoopService inkoopService = ServiceProvider.getInkoopService();
    	JSONParser parser = new JSONParser();
    	JSONObject json = (JSONObject) parser.parse(response);

    	int approvedProposalId = Integer.parseInt(json.get("gkVoorstelId").toString());
    	String approvedProposalName = json.get("updateName").toString();

        boolean updateName = inkoopService.updateNameGekeurdeVoorstel(approvedProposalId, approvedProposalName);
        return updateName;
    }
    
    @DELETE
    @Path("delete/{statusId}")
    @Produces("application/json")
    @RolesAllowed({"Voorstel manager", "Budget manager"})
    public Response deleteStatus(@PathParam("statusId") int id) {
    	InkoopService inkoopService = ServiceProvider.getInkoopService();
        boolean deleteStatus = inkoopService.deleteStatus(id);
        return Response.ok(deleteStatus).build();
    }
}
