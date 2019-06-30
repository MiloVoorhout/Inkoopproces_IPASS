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

import inkoop.productvoorstel.ProductVoorstel;
import inkoop.productvoorstel.ProductVoorstelDaoImpl;


@Path("/product_voorstel")
public class ProductVoorstellenResource {
	
    @GET
    @Path("/{userId}")
    @Produces("application/json")
    @RolesAllowed("Voorstel manager")
    public String getProducts(@PathParam("userId") int id) {
    	InkoopService inkoopService = ServiceProvider.getInkoopService();
        JsonArrayBuilder jab = Json.createArrayBuilder();
        
        for (ProductVoorstel productProposal : inkoopService.getProductsWithId(id)) {
            JsonObjectBuilder job = Json.createObjectBuilder();
            
            job.add("id", productProposal.getId());
            job.add("naam", productProposal.getName());
            job.add("prijs", productProposal.getPrice());
            job.add("categorie", productProposal.getCategory());
            job.add("gebruikers_id", productProposal.getUser_id());
            job.add("gk_id", productProposal.getGk_proposal_id());

            jab.add(job);
        }

        JsonArray array = jab.build();
        return array.toString();
    }
    
    @POST
    @Path("/save")
    @Produces("application/json")
    public Response addProductProposal(String response) throws ParseException{    	
    	InkoopService inkoopService = ServiceProvider.getInkoopService();
    	JSONParser parser = new JSONParser();
    	JSONObject json = (JSONObject) parser.parse(response);

    	ProductVoorstel productProposal = new ProductVoorstel(json.get("productNaam").toString(), Double.parseDouble(json.get("productPrijs").toString()), json.get("productCategorie").toString(), Integer.parseInt(json.get("gebruikerId").toString()),  Integer.parseInt(json.get("response").toString()));

        boolean saveProductProposal = inkoopService.addProductProposal(productProposal);
        return Response.ok(saveProductProposal).build();
    }
    
    @DELETE
    @Path("delete/{productProposalId}")
    @Produces("application/json")
    @RolesAllowed("Voorstel manager")
    public Response deleteProductProposal(@PathParam("productProposalId") int id) {
    	InkoopService inkoopService = ServiceProvider.getInkoopService();
        boolean deleteStatus = inkoopService.deleteProductProposal(id);

        return Response.ok(deleteStatus).build();
    }
    
    
}
