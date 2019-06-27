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
import inkoop.budget.BudgetDaoImpl;


@Path("/budget")
public class BudgetResource {
	private BudgetDaoImpl budgetDao = new BudgetDaoImpl();

	
    @GET
    @Produces("application/json")
    public String getProducts() {
        JsonArrayBuilder jab = Json.createArrayBuilder();
        
        for (Budget budget : budgetDao.findAll()) {
            JsonObjectBuilder job = Json.createObjectBuilder();
            
            job.add("id", budget.getId());
            job.add("budget", budget.getBudget());
            job.add("afdeling", budget.getAfdeling());

            jab.add(job);
        }

        JsonArray array = jab.build();
        return array.toString();
    }
    
    @PUT
    @Path("/update/aankoop_voorstel")
    @Produces("application/json")
    public boolean updateBudgetAankoop(String response) throws ParseException{    	
    	JSONParser parser = new JSONParser();
    	JSONObject json = (JSONObject) parser.parse(response);

    	int budgetAfdeling = Integer.parseInt(json.get("budgetAfdeling").toString());
    	double budgetPrijs = Double.parseDouble(json.get("budgetPrijs").toString());
    	String budgetType = json.get("type").toString();


        boolean updateBudget = budgetDao.update(budgetAfdeling, budgetPrijs, budgetType);
        return updateBudget;
    }
    
    @PUT
    @Path("/update/budget_voorstel")
    @Produces("application/json")
    public boolean updateBudgetVoorstel(String response) throws ParseException{    	
    	JSONParser parser = new JSONParser();
    	JSONObject json = (JSONObject) parser.parse(response);

    	int budgetId = Integer.parseInt(json.get("budgetId").toString());
    	double budgetPrijs = Double.parseDouble(json.get("budgetPrijs").toString());
    	String budgetType = json.get("type").toString();


        boolean updateBudget = budgetDao.update(budgetId, budgetPrijs, budgetType);
        return updateBudget;
    }
    
    
}
