package inkoop.aankoopvoorstellen;

import java.util.List;

public interface AankoopVoorstellenDao {
    public List<AankoopVoorstellen> findAll(int id);
    
    public List<AankoopVoorstellen> findByProductId(int id);
    
    public Boolean save(AankoopVoorstellen purchaseProposals);
    
    public Boolean delete(int id);
    
	public Boolean deleteByProduct(int id);
}
