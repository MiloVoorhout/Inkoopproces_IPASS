package webservices.resources;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;

import inkoop.aankoopvoorstellen.AankoopVoorstellen;
import inkoop.aankoopvoorstellen.AankoopVoorstellenDaoImpl;
import inkoop.budget.Budget;
import inkoop.budget.BudgetDaoImpl;
import inkoop.budgetvoorstellen.BudgetVoorstellen;
import inkoop.budgetvoorstellen.BudgetVoorstellenDaoImpl;
import inkoop.gebruiker.Gebruiker;
import inkoop.gebruiker.GebruikerPostgresDaoImpl;
import inkoop.gekeurdevoorstellen.GekeurdeVoorstellen;
import inkoop.gekeurdevoorstellen.GekeurdeVoorstellenDaoImpl;
import inkoop.product.Product;
import inkoop.product.ProductDaoImpl;
import inkoop.productvoorstel.ProductVoorstel;
import inkoop.productvoorstel.ProductVoorstelDaoImpl;

public class InkoopService {
//  All conections
	private AankoopVoorstellenDaoImpl purchaseProposalDao = new AankoopVoorstellenDaoImpl();
	private BudgetDaoImpl budgetDao = new BudgetDaoImpl();
	private BudgetVoorstellenDaoImpl budgetProposalDao = new BudgetVoorstellenDaoImpl();
	private GebruikerPostgresDaoImpl userDao = new GebruikerPostgresDaoImpl();
	private GekeurdeVoorstellenDaoImpl approvedProposalDao = new GekeurdeVoorstellenDaoImpl();
	private ProductDaoImpl productDao = new ProductDaoImpl();
	private ProductVoorstelDaoImpl productProposalDao = new ProductVoorstelDaoImpl();


	
//	============================
//  Purchase Proposals functions
//	============================

    @GET
    @Produces("application/json")
    public List<AankoopVoorstellen> getPurchaseProposal(int id) {
        return purchaseProposalDao.findAll(id);
    }
    
    @GET
    @Produces("application/json")
    public List<AankoopVoorstellen> getPurchaseProposalId(int id) {
        return purchaseProposalDao.findByProductId(id);
    }
    
    @POST
    @Produces("application/json")
    public Boolean addPurchaseProposal(AankoopVoorstellen purchaseProposal) {    	
        return purchaseProposalDao.save(purchaseProposal);
    }
    
    @DELETE
    @Produces("application/json")
    public Boolean deletePurchaseProposal(int id) {
        return purchaseProposalDao.delete(id);
    }
    
    @DELETE
    @Produces("application/json")
    public Boolean deletePurchaseProposalByProductId(int id) {
        return purchaseProposalDao.deleteByProduct(id);
    }
    
    
//	================
//  Budget functions
//	================
	
    @GET
    @Produces("application/json")
    public List<Budget> getBudgets() {
        return budgetDao.findAll();
    }
    
    @PUT
    @Produces("application/json")
    public boolean updateBudgetPurchase(int budgetDepartment, double budgetPrice, String budgetType){    	
        return budgetDao.update(budgetDepartment, budgetPrice, budgetType);
    }
    
    @PUT
    @Produces("application/json")
    public boolean updateBudgetProposal(int budgetId, double budgetPrijs, String budgetType) {    	
        return budgetDao.update(budgetId, budgetPrijs, budgetType);
    }
    

//	==========================
//  Budget Proposals functions
//	==========================
	
    @GET
    @Produces("application/json")
    public List<BudgetVoorstellen> getBudgetsProposals() {
        return budgetProposalDao.findAll();
    }
    
    @POST
    @Produces("application/json")
    public Boolean addBudgetProposals(BudgetVoorstellen budgetProposal) {    	
        return budgetProposalDao.save(budgetProposal);
    }
    
    @DELETE
    @Produces("application/json")
    public Boolean deleteBudgetProposals(int id) {
        return budgetProposalDao.delete(id);
    }
    
//	==============
//  User functions
//  ==============
	
	@GET
    @Produces("application/json")
    public List<Gebruiker> getUser(int id) {
        return userDao.findById(id);
    }
	
//	============================
//  Approved Proposals functions
//  ============================
	
    @GET
    @Produces("application/json")
    public List<GekeurdeVoorstellen> getGekeurdeVoorstellen(int id) {
        return approvedProposalDao.findAll(id);
    }
    
    @POST
    @Produces("application/json")
    public int addGekeurdeVoorstel(GekeurdeVoorstellen approvedProposal) {    	
        return approvedProposalDao.save(approvedProposal);
    }
    
    @PUT
    @Produces("application/json")
    public boolean updateGekeurdeVoorstel(int approvedProposalId, String approvedProposalStatus) {    	
        return approvedProposalDao.update(approvedProposalId, approvedProposalStatus);
    }
    
    @PUT
    @Produces("application/json")
    public boolean updateNameGekeurdeVoorstel(int approvedProposalId, String approvedProposalName) {    	
        return approvedProposalDao.updateProduct(approvedProposalId, approvedProposalName);
    }
    
    @DELETE
    @Produces("application/json")
    public Boolean deleteStatus(int id) {
        return approvedProposalDao.delete(id);
    }
    
    
//	=================	
//  Product functions
//  =================
	
    @GET
    @Produces("application/json")
    public List<Product> getProducts() {
        return productDao.findAll();
    }
    
    @POST
    @Produces("application/json")
    public Boolean addProduct(Product saveProduct) {    	
        return productDao.save(saveProduct);
    }
    
    @PUT
    @Produces("application/json")
    public boolean updateProduct(int productId, String productName, double productPrice, String productCategorie) {    	
        return productDao.update(productId, productName, productPrice, productCategorie);
    }
    
    @DELETE
    @Produces("application/json")
    public Boolean delteProduct(int id) {
        return productDao.delete(id);
    }
    
    
//	===========================
//  Product Proposals functions
//  ===========================
	
    @GET
    @Produces("application/json")
    public List<ProductVoorstel> getProductsWithId(int id) {
        return productProposalDao.findAll(id);
    }
    
    @POST
    @Produces("application/json")
    public Boolean addProductProposal(ProductVoorstel productProposal) {    	
        return productProposalDao.save(productProposal);
    }
    
    @DELETE
    @Produces("application/json")
    public Boolean deleteProductProposal(int id) {
        return productProposalDao.delete(id);
    }
}
