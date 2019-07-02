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
    	/*Make a connection and create a Json array*/
    	InkoopService inkoopService = ServiceProvider.getInkoopService();
        JsonArrayBuilder jab = Json.createArrayBuilder();
        
        for (GekeurdeVoorstellen approvedProposal : inkoopService.getGekeurdeVoorstellen(id)) {
        	/*Make for every approved proposal a Json and add it to the arraylist*/
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
    	/*Make a connection and get information given to the fucntion*/
    	InkoopService inkoopService = ServiceProvider.getInkoopService();
    	JSONParser parser = new JSONParser();
    	JSONObject json = (JSONObject) parser.parse(response);

    	/*Make a approved proposal*/
    	GekeurdeVoorstellen approvedProposal = new GekeurdeVoorstellen(json.get("productNaam").toString(), "In afwachting", Integer.parseInt(json.get("gebruikerId").toString()));

    	/*Get boolean back from adding the proposal*/
        int saveApprovedProposal = inkoopService.addGekeurdeVoorstel(approvedProposal);
        return saveApprovedProposal;
    }
    
    @PUT
    @Path("/update")
    @Produces("application/json")
    @RolesAllowed({"Voorstel manager", "Budget manager"})
    public boolean updateGekeurdeVoorstel(String response) throws ParseException{  
    	/*Make a connection and get information given to the function*/
    	InkoopService inkoopService = ServiceProvider.getInkoopService();
    	JSONParser parser = new JSONParser();
    	JSONObject json = (JSONObject) parser.parse(response);

    	/*Get the necessary information*/
    	int approvedProposalId = Integer.parseInt(json.get("gkVoorstelId").toString());
    	String approvedProposalStatus = json.get("updateStatus").toString();
    	
    	/*Get boolean back from updating the approved proposal*/
        boolean updateApprovedProposal = inkoopService.updateGekeurdeVoorstel(approvedProposalId, approvedProposalStatus);
        return updateApprovedProposal;
    }
    
    @PUT
    @Path("/update_product")
    @Produces("application/json")
    @RolesAllowed({"Voorstel manager", "Budget manager"})
    public boolean updateNameGekeurdeVoorstel(String response) throws ParseException{   
    	/*Make a connection and get information given to the function*/
    	InkoopService inkoopService = ServiceProvider.getInkoopService();
    	JSONParser parser = new JSONParser();
    	JSONObject json = (JSONObject) parser.parse(response);

    	/*Get the necessary information*/
    	int approvedProposalId = Integer.parseInt(json.get("gkVoorstelId").toString());
    	String approvedProposalName = json.get("updateName").toString();

    	/*Get boolean back from updating the approved proposal*/
        boolean updateName = inkoopService.updateNameGekeurdeVoorstel(approvedProposalId, approvedProposalName);
        return updateName;
    }
    
    @DELETE
    @Path("delete/{statusId}")
    @Produces("application/json")
    public Response deleteStatus(@PathParam("statusId") int id) {
    	/*Make a connection to service provider*/
    	InkoopService inkoopService = ServiceProvider.getInkoopService();
    	/*Get boolean back from deleting the proposal*/
        boolean deleteStatus = inkoopService.deleteStatus(id);
        
        return Response.ok(deleteStatus).build();
    }
}
