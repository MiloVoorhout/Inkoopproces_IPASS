package webservices.resources;

import javax.annotation.security.RolesAllowed;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import inkoop.budgetvoorstellen.BudgetVoorstellen;

@Path("/budget_voorstellen")
public class BudgetVoorstellenResource {
	
    @GET
    @Produces("application/json")
    @RolesAllowed("Budget manager")
    public String getBudgetsProposals() {
    	/*Make a connection and create a Json array*/
    	InkoopService inkoopService = ServiceProvider.getInkoopService();
        JsonArrayBuilder jab = Json.createArrayBuilder();
        
        for (BudgetVoorstellen budgetProposal : inkoopService.getBudgetsProposals()) {
        	/*Make for every budget proposal a Json and add it to the arraylist*/
            JsonObjectBuilder job = Json.createObjectBuilder();
            
            job.add("id", budgetProposal.getId());
            job.add("increasement", budgetProposal.getIncrease());
            job.add("department", budgetProposal.getDepartment());
            job.add("user_id", budgetProposal.getUser_id());
            job.add("budget_id", budgetProposal.getBudget_id());
            job.add("gebruikers_naam", budgetProposal.getUserName());
            job.add("gk_id", budgetProposal.getGk_proposal_id());

            jab.add(job);
        }

        JsonArray array = jab.build();
        return array.toString();
    }
    
    @POST
    @Path("/save")
    @Produces("application/json")
    @RolesAllowed("Voorstel manager")
    public Response addBudgetProposals(String response) throws ParseException{  
    	/*Make a connection and get information given to the function*/
    	InkoopService inkoopService = ServiceProvider.getInkoopService();
    	JSONParser parser = new JSONParser();
    	JSONObject json = (JSONObject) parser.parse(response);

    	/*Make a budget proposal*/
    	BudgetVoorstellen budgetProposal = new BudgetVoorstellen(Double.parseDouble(json.get("budgetVergroting").toString()), Integer.parseInt(json.get("budgetAfdeling").toString()), Integer.parseInt(json.get("gebruikerId").toString()),  Integer.parseInt(json.get("budgetId").toString()), Integer.parseInt(json.get("response").toString()));

    	/*Get boolean back from adding the proposal*/
        boolean saveBudgetProposal = inkoopService.addBudgetProposals(budgetProposal);
        return Response.ok(saveBudgetProposal).build();
    }
    
    @DELETE
    @Path("delete/{budgetProposalId}")
    @Produces("application/json")
    @RolesAllowed("Budget manager")
    public Response deleteBudgetProposals(@PathParam("budgetProposalId") int id) {
    	/*Make a connection to service provider*/
    	InkoopService inkoopService = ServiceProvider.getInkoopService();
    	/*Get boolean back from deleting the proposal*/
        boolean deleteStatus = inkoopService.deleteBudgetProposals(id);

        return Response.ok(deleteStatus).build();
    }
    
}
