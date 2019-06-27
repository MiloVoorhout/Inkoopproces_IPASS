package webservices.resources;

import java.security.Key;
import java.util.AbstractMap;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import inkoop.gebruiker.Gebruiker;
import inkoop.gebruiker.GebruikerPostgresDaoImpl;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

@Path("/authenticatie")
public class Authenticatie {
    static public Key key = MacProvider.generateKey();
	private GebruikerPostgresDaoImpl gebruikerDao = new GebruikerPostgresDaoImpl();


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response authenticateUser(@FormParam("username") String username,
                                     @FormParam("password") String password) {
        try {
            Gebruiker gebruiker = gebruikerDao.findRoleForUser(username, password);
            String role = gebruiker.getRol();

            if (role == null) { throw new IllegalArgumentException("No user found!");  }

            String token = createToken(username, role);

            Map<String, String> responseMessages = new HashMap<>();
									            responseMessages.put("JWT", token);
									            responseMessages.put("role", role);
									            responseMessages.put("id", Integer.toString(gebruiker.getId()));
            return Response.ok(responseMessages).build();


        } catch (JwtException | IllegalArgumentException e)
        { return Response.status(Response.Status.UNAUTHORIZED).build(); }
    }

    private String createToken(String username, String role) throws JwtException {
        Calendar expiration = Calendar.getInstance();
        expiration.add(Calendar.MINUTE, 30);

        return Jwts.builder()
                .setSubject(username)
                .setExpiration(expiration.getTime())
                .claim("role", role)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }
}
