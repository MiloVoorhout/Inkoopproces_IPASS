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

import inkoop.aankoopvoorstellen.AankoopVoorstellen;


@Path("/aankoop_voorstellen")
public class AankoopVoorstellenResource {	
	
    @GET
    @Path("/{userId}")
    @Produces("application/json")
    @RolesAllowed("Voorstel manager")
    public String getPurchaseProposal(@PathParam("userId") int id) {
    	/*Make a connection and create a Json array*/
    	InkoopService inkoopService= ServiceProvider.getInkoopService();
        JsonArrayBuilder jab = Json.createArrayBuilder();
        
        for (AankoopVoorstellen purchaseProposals : inkoopService.getPurchaseProposal(id)) {
        	/*Make for every purchase proposal a Json and add it to the arraylist*/
            JsonObjectBuilder job = Json.createObjectBuilder();
            
            job.add("id", purchaseProposals.getId());
            job.add("aantal", purchaseProposals.getAmount());
            job.add("reden", purchaseProposals.getReason());
            job.add("product_id", purchaseProposals.getProduct_id());
            job.add("gebruikers_id", purchaseProposals.getUser_id());
            job.add("gk_id", purchaseProposals.getGk_proposal_id());
            job.add("naam", purchaseProposals.getName()); 	
            job.add("totaalPrijs", (purchaseProposals.getAmount() * purchaseProposals.getPrice()));
            job.add("afdeling", purchaseProposals.getDepartment());

            jab.add(job);
        }

        JsonArray array = jab.build();
    	/*Return the arraylist*/
        return array.toString();
    }
    
    @GET
    @Path("/products/{productId}")
    @Produces("application/json")
    @RolesAllowed("Voorstel manager")
    public String getPurchaseProposalId(@PathParam("productId") int id) {
    	/*Make a connection and create a Json array*/
    	InkoopService inkoopService= ServiceProvider.getInkoopService();
        JsonArrayBuilder jab = Json.createArrayBuilder();
        
        for (AankoopVoorstellen purchaseProposal : inkoopService.getPurchaseProposalId(id)) {
        	/*Make for every purchase proposal a Json and add it to the arraylist*/
            JsonObjectBuilder job = Json.createObjectBuilder();
            
            job.add("id", purchaseProposal.getId());
            job.add("aantal", purchaseProposal.getAmount());
            job.add("gk_id", purchaseProposal.getGk_proposal_id());
            job.add("naam", purchaseProposal.getName());

            jab.add(job);
        }

        JsonArray array = jab.build();
    	/*Return the arraylist*/
        return array.toString();
    }
    
    @POST
    @Path("/save")
    @Produces("application/json")
    public Response addPurchaseProposal(String response) throws ParseException{  
    	/*Make a connection and get information given to the function*/
    	InkoopService inkoopService= ServiceProvider.getInkoopService();
    	JSONParser parser = new JSONParser();
    	JSONObject json = (JSONObject) parser.parse(response);

    	/*Make a purchase proposal*/
    	AankoopVoorstellen purchaseProposal = new AankoopVoorstellen(Integer.parseInt(json.get("productAantal").toString()), json.get("aankoopReden").toString(), Integer.parseInt(json.get("productId").toString()), Integer.parseInt(json.get("gebruikerId").toString()),  Integer.parseInt(json.get("response").toString()));

    	/*Get boolean back from adding the proposal*/
        boolean savePurchaseProposal = inkoopService.addPurchaseProposal(purchaseProposal);
        return Response.ok(savePurchaseProposal).build();
    }
    
    @DELETE
    @Path("delete/{PurchaseProposalId}")
    @Produces("application/json")
    @RolesAllowed("Voorstel manager")
    public Response deletePurchaseProposal(@PathParam("PurchaseProposalId") int id) {
    	/*Make a connection and get information given to the function*/
    	InkoopService inkoopService= ServiceProvider.getInkoopService();
    	/*Get boolean back from deleting the proposal*/
        boolean deleteStatus = inkoopService.deletePurchaseProposal(id);

        return Response.ok(deleteStatus).build();
    }
}
