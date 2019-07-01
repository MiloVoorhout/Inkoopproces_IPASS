package webservices.resources;

import javax.annotation.security.RolesAllowed;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import inkoop.budget.Budget;

@Path("/budget")
public class BudgetResource {
	
    @GET
    @Produces("application/json")
    @RolesAllowed({"Voorstel manager", "Budget manager"})
    public String getBudgets() {
    	InkoopService inkoopService = ServiceProvider.getInkoopService();
        JsonArrayBuilder jab = Json.createArrayBuilder();
        
        for (Budget budget : inkoopService.getBudgets()) {
            JsonObjectBuilder job = Json.createObjectBuilder();
            
            job.add("id", budget.getId());
            job.add("budget", budget.getBudget());
            job.add("afdeling", budget.getDepartment());

            jab.add(job);
        }

        JsonArray array = jab.build();
        return array.toString();
    }
    
    @PUT
    @Path("/update/aankoop_voorstel")
    @Produces("application/json")
    @RolesAllowed("Voorstel manager")
    public boolean updateBudgetPurchase(String response) throws ParseException{    
    	InkoopService inkoopService = ServiceProvider.getInkoopService();
    	JSONParser parser = new JSONParser();
    	JSONObject json = (JSONObject) parser.parse(response);

    	int budgetDepartment = Integer.parseInt(json.get("budgetAfdeling").toString());
    	double budgetPrice = Double.parseDouble(json.get("budgetPrijs").toString());
    	String budgetType = json.get("type").toString();


        boolean updateBudget = inkoopService.updateBudgetPurchase(budgetDepartment, budgetPrice, budgetType);
        return updateBudget;
    }
    
    @PUT
    @Path("/update/budget_voorstel")
    @Produces("application/json")
    @RolesAllowed("Budget manager")
    public boolean updateBudgetProposal(String response) throws ParseException{    	
    	InkoopService inkoopService = ServiceProvider.getInkoopService();
    	JSONParser parser = new JSONParser();
    	JSONObject json = (JSONObject) parser.parse(response);

    	int budgetId = Integer.parseInt(json.get("budgetId").toString());
    	double budgetPrijs = Double.parseDouble(json.get("budgetPrijs").toString());
    	String budgetType = json.get("type").toString();

        boolean updateBudget = inkoopService.updateBudgetProposal(budgetId, budgetPrijs, budgetType);
        return updateBudget;
    }
    
    
}
