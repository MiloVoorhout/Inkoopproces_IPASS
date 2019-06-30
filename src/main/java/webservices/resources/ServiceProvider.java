package webservices.resources;

public class ServiceProvider {
	private static InkoopService inkoopService = new InkoopService();

	public static InkoopService getInkoopService() {
		return inkoopService;
	}
}