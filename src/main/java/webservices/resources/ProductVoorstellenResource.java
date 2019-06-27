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

import inkoop.productvoorstel.ProductVoorstel;
import inkoop.productvoorstel.ProductVoorstelDaoImpl;


@Path("/product_voorstel")
public class ProductVoorstellenResource {
	private ProductVoorstelDaoImpl productVoorstelDao = new ProductVoorstelDaoImpl();
	
    @GET
    @Path("/{userId}")
    @Produces("application/json")
    public String getProducts(@PathParam("userId") int id) {
        JsonArrayBuilder jab = Json.createArrayBuilder();
        
        for (ProductVoorstel productVoorstel : productVoorstelDao.findAll(id)) {
            JsonObjectBuilder job = Json.createObjectBuilder();
            
            job.add("id", productVoorstel.getId());
            job.add("naam", productVoorstel.getNaam());
            job.add("prijs", productVoorstel.getPrijs());
            job.add("categorie", productVoorstel.getCategorie());
            job.add("gebruikers_id", productVoorstel.getGebruikers_id());
            job.add("gk_id", productVoorstel.getGk_voorstel_id());

            jab.add(job);
        }

        JsonArray array = jab.build();
        return array.toString();
    }
    
    @POST
    @Path("/save")
    @Produces("application/json")
    public Response addProductVoorstel(String response) throws ParseException{    	
    	JSONParser parser = new JSONParser();
    	JSONObject json = (JSONObject) parser.parse(response);

    	ProductVoorstel productVoorstel = new ProductVoorstel(json.get("productNaam").toString(), Double.parseDouble(json.get("productPrijs").toString()), json.get("productCategorie").toString(), Integer.parseInt(json.get("gebruikerId").toString()),  Integer.parseInt(json.get("response").toString()));

        boolean saveProductVoorstel = productVoorstelDao.save(productVoorstel);
        return Response.ok(saveProductVoorstel).build();
    }
    
    @DELETE
    @Path("delete/{productVoorstelId}")
    @Produces("application/json")
    public Response deleteProductVoorstel(@PathParam("productVoorstelId") int id) {
        boolean deleteStatus = productVoorstelDao.delete(id);

        return Response.ok(deleteStatus).build();
    }
    
    
}
