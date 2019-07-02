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

import inkoop.product.Product;
import inkoop.product.ProductDaoImpl;
import inkoop.productvoorstel.ProductVoorstel;

@Path("/product")
public class ProductResource {
	
    @GET
    @Produces("application/json")
    public String getProducts() {
    	/*Make a connection and create a Json array*/
    	InkoopService inkoopService = ServiceProvider.getInkoopService();
        JsonArrayBuilder jab = Json.createArrayBuilder();
        
        for (Product product : inkoopService.getProducts()) {
        	/*Make for every approved proposal a Json and add it to the arraylist*/
            JsonObjectBuilder job = Json.createObjectBuilder();
            
            job.add("id", product.getId());
            job.add("naam", product.getName());
            job.add("prijs", product.getPrice());
            job.add("categorie", product.getCategory());

            jab.add(job);
        }

        JsonArray array = jab.build();
        return array.toString();
    }
    
    @POST
    @Path("/save")
    @Produces("application/json")
    @RolesAllowed("Voorstel manager")
    public Response addProduct(String response) throws ParseException{    
    	/*Make a connection and get information given to the fucntion*/
    	InkoopService inkoopService = ServiceProvider.getInkoopService();
    	JSONParser parser = new JSONParser();
    	JSONObject json = (JSONObject) parser.parse(response);

    	/*Make a product proposal*/
    	Product saveProduct = new Product(json.get("productNaam").toString(), Double.parseDouble(json.get("productPrijs").toString()), json.get("productCategorie").toString());

    	/*Get boolean back from adding the product proposal*/
        boolean saveProductStatus = inkoopService.addProduct(saveProduct);
        return Response.ok(saveProductStatus).build();
    }
    
    @PUT
    @Path("/update")
    @Produces("application/json")
    @RolesAllowed("Voorstel manager")
    public boolean updateProduct(String response) throws ParseException{  
    	/*Make a connection and get information given to the function*/
    	InkoopService inkoopService = ServiceProvider.getInkoopService();
    	JSONParser parser = new JSONParser();
    	JSONObject json = (JSONObject) parser.parse(response);

    	/*Get the necessary information*/
    	int productId = Integer.parseInt(json.get("id").toString());
    	String productName = json.get("name").toString();
    	double productPrice = Double.parseDouble(json.get("price").toString());
    	String productCategorie = json.get("categorie").toString();

    	/*Get boolean back from updating the product proposal*/
        boolean updateProduct = inkoopService.updateProduct(productId, productName, productPrice, productCategorie);
        return updateProduct;
    }
    
    @DELETE
    @Path("delete/{productId}")
    @Produces("application/json")
    @RolesAllowed("Voorstel manager")
    public Response deleteProduct(@PathParam("productId") int id) {
    	/*Make a connection to service provider*/
    	InkoopService inkoopService = ServiceProvider.getInkoopService();
    	/*Get boolean back from deleting the product proposal*/
        boolean deleteStatus = inkoopService.delteProduct(id);

        return Response.ok(deleteStatus).build();
    }
}
