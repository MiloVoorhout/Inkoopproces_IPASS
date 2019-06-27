package inkoop.webservices;

import javax.json.*;
import javax.ws.rs.*;

@Path("/calculator")
public class CalculatorResource {

@POST

@Path("/telop")

@Produces("application/json")

public String telOp(@FormParam("getal1")int x1, @FormParam("getal2")int x2){

JsonObjectBuilder job = Json.createObjectBuilder();

job.add("resultaat", x1+x2);

return job.build().toString();

}

} 