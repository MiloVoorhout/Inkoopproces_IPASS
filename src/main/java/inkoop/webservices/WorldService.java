package inkoop.webservices;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;

import inkoop.persistence.CountryPostgresDaoImpl;

public class WorldService {
	private CountryPostgresDaoImpl countryDao = new CountryPostgresDaoImpl();
	
	@GET
	@Produces("application/json")
	public List<Country> getAllCountries() {
		return countryDao.findAll();
	}
	
	@GET
	@Produces("application/json")
	public List<Country> get10LargestPopulations() {
		return countryDao.find10LargestPopulations();
	}

	@GET
	@Produces("application/json")
	public List<Country> get10LargestSurfaces() {
		return countryDao.find10LargestSurfaces();
	}
	
	@GET
	@Produces("application/json")
	public Country getCountryByCode(String code) {
		return countryDao.findByCode(code);
	}
}
