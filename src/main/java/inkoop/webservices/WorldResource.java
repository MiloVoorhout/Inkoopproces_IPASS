package inkoop.webservices;

import java.io.IOException;

import javax.annotation.security.RolesAllowed;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
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

import inkoop.persistence.CountryPostgresDaoImpl;

@Path("/countries")
public class WorldResource {
     @GET
     @Produces("application/json")
     public String getCountries() {
         WorldService countries = ServiceProvider.getWorldService();
         JsonArrayBuilder jab = Json.createArrayBuilder();

         for (Country country : countries.getAllCountries()) {
             JsonObjectBuilder job = Json.createObjectBuilder();
             job.add("code", country.getCode());
             job.add("naam", country.getName());
             job.add("hoofdstad", country.getCapital());
             job.add("oppervlakte", country.getSurface());
             job.add("overheid", country.getGovernment());
             job.add("lat", country.getLatitude());
             job.add("iso3", country.getIso3());
             job.add("continent", country.getContinent());
             job.add("regio", country.getRegion());
             job.add("bevolking", country.getPopulation());
             job.add("lon", country.getLongitude());

             jab.add(job);
         }

         JsonArray array = jab.build();
         return array.toString();
     }
     
     @GET
     @Path("{countryCode}")
     @Produces("application/json")
     public String getOrderInfo(@PathParam("countryCode") String code) {
    	 WorldService countries = ServiceProvider.getWorldService();
    	 Country country = countries.getCountryByCode(code);

    	 JsonObjectBuilder job = Json.createObjectBuilder();
         job.add("code", country.getCode());
         job.add("naam", country.getName());
         job.add("hoofdstad", country.getCapital());
         job.add("oppervlakte", country.getSurface());
         job.add("overheid", country.getGovernment());
         job.add("lat", country.getLatitude());
         job.add("iso3", country.getIso3());
         job.add("continent", country.getContinent());
         job.add("regio", country.getRegion());
         job.add("bevolking", country.getPopulation());
         job.add("lon", country.getLongitude());

    	 return job.build().toString();
     }
     
     @GET
     @Path("/largestsurfaces")
     @Produces("application/json")
     public String getLargestSurfaces() {
    	 WorldService countries = ServiceProvider.getWorldService();
         JsonArrayBuilder jab = Json.createArrayBuilder();

         for (Country country : countries.get10LargestSurfaces()) {
             JsonObjectBuilder job = Json.createObjectBuilder();
             job.add("code", country.getCode());
             job.add("naam", country.getName());
             job.add("hoofdstad", country.getCapital());
             job.add("oppervlakte", country.getSurface());
             job.add("overheid", country.getGovernment());
             job.add("lat", country.getLatitude());
             job.add("iso3", country.getIso3());
             job.add("continent", country.getContinent());
             job.add("regio", country.getRegion());
             job.add("bevolking", country.getPopulation());
             job.add("lon", country.getLongitude());

             jab.add(job);
         }

         JsonArray array = jab.build();
         return array.toString();
     }
     
     @GET
     @Path("/largestpopulations")
     @Produces("application/json")
     public String getLargestPopulations() {
    	 WorldService countries = ServiceProvider.getWorldService();
         JsonArrayBuilder jab = Json.createArrayBuilder();

         for (Country country : countries.get10LargestPopulations()) {
             JsonObjectBuilder job = Json.createObjectBuilder();
             job.add("code", country.getCode());
             job.add("naam", country.getName());
             job.add("hoofdstad", country.getCapital());
             job.add("oppervlakte", country.getSurface());
             job.add("overheid", country.getGovernment());
             job.add("lat", country.getLatitude());
             job.add("iso3", country.getIso3());
             job.add("continent", country.getContinent());
             job.add("regio", country.getRegion());
             job.add("bevolking", country.getPopulation());
             job.add("lon", country.getLongitude());

             jab.add(job);
         }

         JsonArray array = jab.build();
         return array.toString();
     }
     
     @DELETE
     @Path("delete/{countryCode}")
     @Produces("application/json")
     @RolesAllowed("user")
     public String deleteCountry(@PathParam("countryCode") String code) {
    	 CountryPostgresDaoImpl countryDao = new CountryPostgresDaoImpl();
         WorldService worldService = ServiceProvider.getWorldService();
         boolean deleted = false;

         JsonObjectBuilder job = Json.createObjectBuilder();
         Country country = worldService.getCountryByCode(code);
         boolean deleteCountry = countryDao.delete(country);
         if(deleteCountry)
             deleted = true;

         job.add("deleted", deleted);

         return job.build().toString();
     }
     
     @PUT
     @Path("update")
     @Produces("application/json")
     @RolesAllowed("user")
     public String updateCountry(String response) throws ParseException {
    	 CountryPostgresDaoImpl countryDao = new CountryPostgresDaoImpl();
         WorldService worldService = ServiceProvider.getWorldService();
         
         JSONParser parser = new JSONParser();
         JSONObject json = (JSONObject) parser.parse(response);
         
         Country editCountry = new Country(json.get("code").toString(), "", json.get("land").toString(), json.get("hoofdstad").toString(), "", json.get("regio").toString(), Integer.parseInt(json.get("oppervlakte").toString()), Integer.parseInt(json.get("inwoners").toString()), "", 0, 0);
         
         JsonObjectBuilder job = Json.createObjectBuilder();
         boolean updateCountry = countryDao.update(editCountry);
         
         job.add("update", updateCountry);
         return job.build().toString();
     }

     @POST
     @Path("/save")
     @Produces("application/json")
     @RolesAllowed("user")
     public Response addCountry(@FormParam("code") String cod,
    		@FormParam("iso3") String iso, @FormParam("name") String nam,
    		@FormParam("continent") String con, @FormParam("region") String reg,
    		@FormParam("surfacearea") double sur, @FormParam("population") int pop,
    		@FormParam("governmentform") String gov, @FormParam("latitude") double lat,
    		@FormParam("longitude") double lon, @FormParam("capital") String cap) {
    	 CountryPostgresDaoImpl countryDao = new CountryPostgresDaoImpl();
         WorldService worldService = ServiceProvider.getWorldService();

         Country saveCountry = new Country(cod, iso, nam, cap, con, reg, sur, pop, gov, lat, lon);

         boolean saveCountryInfo = countryDao.save(saveCountry);
         return Response.ok(saveCountryInfo).build();
     }
}