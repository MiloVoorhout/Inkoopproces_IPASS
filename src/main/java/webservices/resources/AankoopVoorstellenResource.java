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
import inkoop.aankoopvoorstellen.AankoopVoorstellenDaoImpl;
import inkoop.productvoorstel.ProductVoorstel;


@Path("/aankoop_voorstellen")
public class AankoopVoorstellenResource {
	private AankoopVoorstellenDaoImpl aankoopVoorstelDao = new AankoopVoorstellenDaoImpl();
	
    @GET
    @Path("/{userId}")
    @Produces("application/json")
    public String getProducts(@PathParam("userId") int id) {
        JsonArrayBuilder jab = Json.createArrayBuilder();
        
        for (AankoopVoorstellen aankoopVoorstel : aankoopVoorstelDao.findAll(id)) {
            JsonObjectBuilder job = Json.createObjectBuilder();
            
            job.add("id", aankoopVoorstel.getId());
            job.add("aantal", aankoopVoorstel.getAantal());
            job.add("reden", aankoopVoorstel.getReden());
            job.add("product_id", aankoopVoorstel.getProduct_id());
            job.add("gebruikers_id", aankoopVoorstel.getGebruikers_id());
            job.add("gk_id", aankoopVoorstel.getGk_voorstel_id());
            job.add("naam", aankoopVoorstel.getNaam());
            job.add("totaalPrijs", (aankoopVoorstel.getAantal() * aankoopVoorstel.getPrijs()));
            job.add("afdeling", aankoopVoorstel.getAfdeling());

            jab.add(job);
        }

        JsonArray array = jab.build();
        return array.toString();
    }
    
    @POST
    @Path("/save")
    @Produces("application/json")
    public Response addAankoopVoorstel(String response) throws ParseException{    	
    	JSONParser parser = new JSONParser();
    	JSONObject json = (JSONObject) parser.parse(response);

    	AankoopVoorstellen aankoopVoorstel = new AankoopVoorstellen(Integer.parseInt(json.get("productAantal").toString()), json.get("aankoopReden").toString(), Integer.parseInt(json.get("productId").toString()), Integer.parseInt(json.get("gebruikerId").toString()),  Integer.parseInt(json.get("response").toString()));

        boolean saveProductVoorstel = aankoopVoorstelDao.save(aankoopVoorstel);
        return Response.ok(saveProductVoorstel).build();
    }
    
    @DELETE
    @Path("delete/{aankoopVoorstelId}")
    @Produces("application/json")
    public Response deleteAankoopVoorstel(@PathParam("aankoopVoorstelId") int id) {
        boolean deleteStatus = aankoopVoorstelDao.delete(id);

        return Response.ok(deleteStatus).build();
    }
}
