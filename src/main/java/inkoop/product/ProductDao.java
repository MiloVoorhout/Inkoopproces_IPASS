package inkoop.product;

import java.util.List;

public interface ProductDao {
    //Find functies
    //Find all the products
    public List<Product> findAll();

	//Save a new product in the database
    public Boolean save(Product product);

    //Update a product
    public Boolean update(int id, String name, double price, String category);
    
    //Delete a product
    public Boolean delete(int id);
}
