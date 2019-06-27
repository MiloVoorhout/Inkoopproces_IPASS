package inkoop.persistence;

import java.util.List;

import inkoop.webservices.Country;

public interface CountryDao {
	//Save a new country in the database
    public Boolean save(Country country);
    
    //Find functies
    //Find all the country's
    public List<Country> findAll();
    
    //Find by land code
    public Country findByCode(String code);
    
    //Find 10 countries with the largest populations
    public List<Country> find10LargestPopulations();
    
    //Find 10 countries with the largest surfaces
    public List<Country> find10LargestSurfaces();
    
    //Update a country
    public Boolean update(Country country);
    
    //Delete a country
    public Boolean delete(Country country);
}
