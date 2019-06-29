package webservices.resources;

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
	private ProductDaoImpl productDao = new ProductDaoImpl();
	
    @GET
    @Produces("application/json")
    public String getProducts() {
        JsonArrayBuilder jab = Json.createArrayBuilder();
        
        for (Product product : productDao.findAll()) {
            JsonObjectBuilder job = Json.createObjectBuilder();
            
            job.add("id", product.getId());
            job.add("naam", product.getNaam());
            job.add("prijs", product.getPrijs());
            job.add("categorie", product.getCategorie());

            jab.add(job);
        }

        JsonArray array = jab.build();
        return array.toString();
    }
    
    @POST
    @Path("/save")
    @Produces("application/json")
    public Response addProduct(String response) throws ParseException{    	
    	JSONParser parser = new JSONParser();
    	JSONObject json = (JSONObject) parser.parse(response);

    	Product saveProduct = new Product(json.get("productNaam").toString(), Double.parseDouble(json.get("productPrijs").toString()), json.get("productCategorie").toString());

        boolean saveProductStatus = productDao.save(saveProduct);
        return Response.ok(saveProductStatus).build();
    }
    
    @PUT
    @Path("/update")
    @Produces("application/json")
    public boolean updateNameGekeurdeVoorstel(String response) throws ParseException{    	
    	JSONParser parser = new JSONParser();
    	JSONObject json = (JSONObject) parser.parse(response);

    	int productId = Integer.parseInt(json.get("id").toString());
    	String productName = json.get("name").toString();
    	double productPrice = Double.parseDouble(json.get("price").toString());
    	String productCategorie = json.get("categorie").toString();


        boolean updateProduct = productDao.update(productId, productName, productPrice, productCategorie);
        return updateProduct;
    }
    
    @DELETE
    @Path("delete/{productId}")
    @Produces("application/json")
    public Response deleteAankoopVoorstel(@PathParam("productId") int id) {
        boolean deleteStatus = productDao.delete(id);

        return Response.ok(deleteStatus).build();
    }
}
