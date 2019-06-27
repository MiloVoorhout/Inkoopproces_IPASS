package inkoop.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import inkoop.connection.PostgresBaseDao;
import inkoop.webservices.Country;

public class CountryPostgresDaoImpl extends PostgresBaseDao implements CountryDao {
	
	//Make a var of the connection
    private Connection conn = super.getConnection();

    public CountryPostgresDaoImpl() {
    }

    //Save a new country in the database
    //Using prepared statements for safety
    public Boolean save(Country country) {
        try {
            String saveQuery = "INSERT INTO country (code, iso3, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, latitude, longitude, capital) VALUES (?, ?, ?, ?, ?, ?, null, ?, null, null, null, ?, ?, null, ?, ?, ?)";
            PreparedStatement save = conn.prepareStatement(saveQuery);
            save.setString(1, country.getCode());
            save.setString(2, country.getIso3());
            save.setString(3, country.getName());
            save.setString(4, country.getContinent());
            save.setString(5, country.getRegion());
            save.setDouble(6, country.getSurface());
            save.setInt(7, country.getPopulation());
            save.setString(8, country.getName());
            save.setString(9, country.getGovernment());
            save.setDouble(10, country.getLatitude());
            save.setDouble(11, country.getLongitude());
            save.setString(12, country.getCapital());
            save.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Find all the countries in the database
    public List<Country> findAll() {
        List<Country> countries = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("SELECT * FROM country ORDER BY name");

            while (results.next()) {

                countries.add(new Country(
                        results.getString("code"),
                        results.getString("iso3"),
                        results.getString("name"),
                        results.getString("capital"),
                        results.getString("continent"),
                        results.getString("region"),
                        results.getDouble("surfacearea"),
                        results.getInt("population"),
                        results.getString("governmentform"),
                        results.getDouble("latitude"),
                        results.getDouble("longitude")
                ));
            }

            results.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countries;
    }
    
    //Find by land code
    public Country findByCode(String code) {
        Country country = null;

        try {
            String selectQuery = "SELECT * FROM country WHERE code = ?";
            PreparedStatement select = conn.prepareStatement(selectQuery);
            select.setString(1, code);
            ResultSet results = select.executeQuery();

            while (results.next()) {

                country = new Country(
                        results.getString("code"),
                        results.getString("iso3"),
                        results.getString("name"),
                        results.getString("capital"),
                        results.getString("continent"),
                        results.getString("region"),
                        results.getDouble("surfacearea"),
                        results.getInt("population"),
                        results.getString("governmentform"),
                        results.getDouble("latitude"),
                        results.getDouble("longitude")
                );
            }

            results.close();
            select.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return country;
    }
    
    //Find 10 countries with the largest populations
    public List<Country> find10LargestPopulations() {
        List<Country> countries = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("SELECT * FROM country ORDER BY population LIMIT 10");

            while (results.next()) {

                countries.add(new Country(
                        results.getString("code"),
                        results.getString("iso3"),
                        results.getString("name"),
                        results.getString("capital"),
                        results.getString("continent"),
                        results.getString("region"),
                        results.getDouble("surfacearea"),
                        results.getInt("population"),
                        results.getString("governmentform"),
                        results.getDouble("latitude"),
                        results.getDouble("longitude")
                ));
            }

            results.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countries;
    }
    
    //Find 10 countries with the largest surfaces
    public List<Country> find10LargestSurfaces() {
        List<Country> countries = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("SELECT * FROM country ORDER BY surfacearea LIMIT 10");

            while (results.next()) {

                countries.add(new Country(
                        results.getString("code"),
                        results.getString("iso3"),
                        results.getString("name"),
                        results.getString("capital"),
                        results.getString("continent"),
                        results.getString("region"),
                        results.getDouble("surfacearea"),
                        results.getInt("population"),
                        results.getString("governmentform"),
                        results.getDouble("latitude"),
                        results.getDouble("longitude")
                ));
            }

            results.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countries;
    }

    //Update a country
    public Boolean update(Country country) {
        try {
            String updateQuery = "UPDATE country SET name = ?, region = ?, surfacearea = ?, population = ?, capital = ? WHERE code = ?";
            PreparedStatement update = conn.prepareStatement(updateQuery);
            update.setString(1, country.getName());
            update.setString(2, country.getRegion());
            update.setDouble(3, country.getSurface());
            update.setInt(4, country.getPopulation());
            update.setString(5, country.getCapital());
            update.setString(6, country.getCode());
            update.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    //Delete a country
    public Boolean delete(Country country) {
    	try {
            String deleteQuery = "DELETE FROM country WHERE code = ?";
            PreparedStatement delete = conn.prepareStatement(deleteQuery);
            delete.setString(1, country.getCode());
            delete.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
