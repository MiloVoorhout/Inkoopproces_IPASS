package inkoop.productvoorstel;

import java.util.List;


public interface ProductVoorstelDao {
	//Find functies
	//Find all the product voorstellen
	public List<ProductVoorstel> findAll(int id);
	
    public Boolean save(ProductVoorstel productVoorstel);
    
    public Boolean delete(int id);
}
