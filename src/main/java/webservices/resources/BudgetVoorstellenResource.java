package webservices.resources;

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

import inkoop.aankoopvoorstellen.AankoopVoorstellen;
import inkoop.budgetvoorstellen.BudgetVoorstellen;
import inkoop.budgetvoorstellen.BudgetVoorstellenDaoImpl;

@Path("/budget_voorstellen")
public class BudgetVoorstellenResource {
	private BudgetVoorstellenDaoImpl budgetProposalDao = new BudgetVoorstellenDaoImpl();
	
    @GET
    @Produces("application/json")
    public String getProducts() {
        JsonArrayBuilder jab = Json.createArrayBuilder();
        
        for (BudgetVoorstellen budgetProposal : budgetProposalDao.findAll()) {
            JsonObjectBuilder job = Json.createObjectBuilder();
            
            job.add("id", budgetProposal.getId());
            job.add("vergroting", budgetProposal.getIncrease());
            job.add("afdeling", budgetProposal.getDepartment());
            job.add("gebruikers_id", budgetProposal.getUser_id());
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
    public Response addBudgetVoorstel(String response) throws ParseException{    	
    	JSONParser parser = new JSONParser();
    	JSONObject json = (JSONObject) parser.parse(response);

    	BudgetVoorstellen budgetProposal = new BudgetVoorstellen(Double.parseDouble(json.get("budgetVergroting").toString()), Integer.parseInt(json.get("budgetAfdeling").toString()), Integer.parseInt(json.get("gebruikerId").toString()),  Integer.parseInt(json.get("budgetId").toString()), Integer.parseInt(json.get("response").toString()));

        boolean saveBudgetProposal = budgetProposalDao.save(budgetProposal);
        return Response.ok(saveBudgetProposal).build();
    }
    
    @DELETE
    @Path("delete/{budgetProposalId}")
    @Produces("application/json")
    public Response deleteBudgetVoorstel(@PathParam("budgetProposalId") int id) {
        boolean deleteStatus = budgetProposalDao.delete(id);

        return Response.ok(deleteStatus).build();
    }
    
}
