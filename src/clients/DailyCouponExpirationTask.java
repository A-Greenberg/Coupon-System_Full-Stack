package clients;

import java.sql.Date;
import java.util.Calendar;
import java.util.Collection;

import ExceptionsCoupons.CompanyException;
import ExceptionsCoupons.CouponException;
import ExceptionsCoupons.CustomerException;
import ExceptionsCoupons.DbConnectionException;
import coupon_dao.CompanyDAO;
import coupon_dao.CouponDAO;
import coupon_dao.CustomerDAO;

public class DailyCouponExpirationTask extends Thread {
	private final CustomerDAO customerDAO;
	private final CompanyDAO companyDAO;
	private final CouponDAO couponDAO;
	private boolean shouldStop;
	
	public DailyCouponExpirationTask(CustomerDAO customerDAO, CompanyDAO companyDAO, CouponDAO couponDAO) {
		this.customerDAO = customerDAO;
		this.companyDAO = companyDAO;
		this.couponDAO = couponDAO;
		shouldStop = false;
	}
	
	public void stopDailyCouponTask() {
		shouldStop = true;
	}
	
	@Override
	public void run() {
		while (!shouldStop) {
			// get current date
			Date current = new Date(Calendar.getInstance().getTime().getTime());
			
			try {
				// delete coupons from CouponDB
				Collection<Integer> couponIds;
				couponIds = couponDAO.getAllCouponsIdsUntillDate(current);
				for (Integer id : couponIds) {
					couponDAO.removeCoupon(id);
				}
				// delete coupons from CustomerDB
				for (Integer id : couponIds) {
					customerDAO.removeCustomersCouponsId(id);
				}
				// delete coupons from CompanyDB
				for (Integer id : couponIds) {
					companyDAO.removeCoupon(id);
				}
			} catch (DbConnectionException e) {
				System.out.println("Problem deleting expired coupons from system - DB connection related");
			} catch (CouponException e) {
				System.out.println("Problem deleting expired coupons from system - coupon DB related");
			} catch (CustomerException e) {
				System.out.println("Problem deleting expired coupons from system - customer DB related");
			} catch (CompanyException e) {
				System.out.println("Problem deleting expired coupons from system - company DB related");
			}
			
			
			try {
				Thread.sleep(86400000);
			} catch (InterruptedException e) {
				System.out.println("Problem deleting expired coupons from system");
			}
		}
	}
}
