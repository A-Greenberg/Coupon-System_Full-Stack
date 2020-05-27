package clients;


import ExceptionsCoupons.DbConnectionException;
import coupon_dao.CompanyDAO;
import coupon_dao.CouponDAO;
import coupon_dao.CustomerDAO;
import coupon_dbdao.*;
import coupon_facade.AdminFacade;
import coupon_facade.CompanyFacade;
import coupon_facade.CouponClientFacade;
import coupon_facade.CustomerFacade;

public class CouponSystem implements CouponClientFacade {
	private final CustomerDAO customerDAO;
	private final CompanyDAO companyDAO;
	private final CouponDAO couponDAO;
	private final DailyCouponExpirationTask dailyCouponTask;
	
	public static final CouponSystem INSTANCE = new CouponSystem();
	
	private CouponSystem() {
		customerDAO = new CustomerDBDAO();
		companyDAO = new CompanyDBDAO();
		couponDAO = new CouponDBDAO();
		dailyCouponTask = new DailyCouponExpirationTask(customerDAO, companyDAO, couponDAO);
		dailyCouponTask.start();
	}
	
	@Override
	public CouponClientFacade login(String name, String password, ClientType type) throws Exception {
		if (name == null || password == null) {
			return null;
		}
		
		CouponClientFacade loginResult = null;
		switch (type) {
			case ADMIN:				
				AdminFacade adminFacade = new AdminFacade(couponDAO, customerDAO, companyDAO);
				loginResult = adminFacade.login(name, password, type);
				break;
			case COMPANY:
				CompanyFacade companyFacade = new CompanyFacade(couponDAO, customerDAO, companyDAO);
				loginResult = companyFacade.login(name, password, type);
				break;
			case CUSTOMER:
				CustomerFacade custFacade = new CustomerFacade(customerDAO,couponDAO);
				loginResult = custFacade.login(name, password, type);
		}		
		return loginResult;
	}
	
	public void shutdown() {
		dailyCouponTask.stopDailyCouponTask();
		try {
			ConnectionPool.getMyConnectionPool().closeAllConnections();
		} catch (DbConnectionException e) {
			System.out.println(e.getMessage());
		}
	}
}
