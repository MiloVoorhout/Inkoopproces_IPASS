package inkoop.gekeurdevoorstellen;

import java.util.List;


public interface GekeurdeVoorstellenDao {
	public List<GekeurdeVoorstellen> findAll(int id);
	
    public int save(GekeurdeVoorstellen approvedProposals);
    
    public Boolean update(int id, String status);
    
    public Boolean updateProduct(int id, String name);
    
    public Boolean delete(int id);
}
