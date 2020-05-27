package clients;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import coupon_beans.Company;
import coupon_beans.Coupon;
import coupon_beans.CouponType;
import coupon_beans.Customer;
import coupon_dao.CustomerDAO;
import coupon_dbdao.CustomerDBDAO;
import coupon_facade.AdminFacade;
import coupon_facade.CompanyFacade;
import coupon_facade.CouponClientFacade;
import coupon_facade.CustomerFacade;

public class Populate_DB {

	public static void main(String[] args) {
		// create coupon system
		CouponSystem couponSystem = CouponSystem.INSTANCE;
		CouponClientFacade clientFacade = null;
		
		//THIS MAIN TESTS ALL THE DIFFERENT METHODS IN THE DIFFERENT FACADES TO SHOW THEY ARE WORKING
		//PLEASE RUN "AS IS" TO TEST THE ADMIN FACADE
		//REQUESTS TO HIDE/REVEAL CERTEIN LINES TO TEST THE REMAINING FACED WILL BE PRINTED TO THE CONSOLE

		// Admin login
		try{
				//create a administrator facade entry to the coupon system
				System.out.println("Login into the coupon system");
				System.out.println();
				
				clientFacade = couponSystem.login("admin", "1234", ClientType.ADMIN);
				AdminFacade adminFacade = (AdminFacade)clientFacade;
				
				System.out.println("Adding 6 companies to the DB who holds up tp now (calling on .getALlCompanies)" 
				 					+ adminFacade.getAllCompanies().size() + "");
				
				System.out.println();
				Company apple = 	new Company("Apfle",	"Apple_1",	     "apple@apple.com");
				Company google = 	new Company("Doodle", 	"google_2",  	 "google@gmail.com");
				Company microsoft = new Company("MicroSweeft", "microsoft3", "microsoft@microsoft.com");
				Company cocacola =  new Company("Coca-Polka", "cocacola6",	 "coca-cola@cocacola.com");
				Company toyota =    new Company("Doyota", 	 "toyota7",		 "toyota@toyota.com");
				Company disney = 	new Company("Visney", 	 "disney8",		 "disney@disney.com");
				
				//create all 6 companies in the DB
				System.out.println("Adding the following companies to the DB:");
				System.out.println("-------------------------------------------");
				adminFacade.createCompany(apple);
				System.out.println(apple.getCompName());
				adminFacade.createCompany(google);
				System.out.println(google.getCompName());
				adminFacade.createCompany(microsoft);
				System.out.println(microsoft.getCompName());
				adminFacade.createCompany(cocacola);
				System.out.println(cocacola.getCompName());
				adminFacade.createCompany(toyota);
				System.out.println(toyota.getCompName());
				adminFacade.createCompany(disney);
				System.out.println(disney.getCompName());
				System.out.println();
				
				//prints the number of companies in the db if 6 were created 
				Collection <Company> allCompanies =  adminFacade.getAllCompanies();
				if (allCompanies.size()==6){
					System.out.println();
					System.out.println("All 6 companies were added to the DB succesfully!");
					System.out.println("*************************************************");
				}
				
				//remove one company
				System.out.println("Removing " + microsoft.getCompName() + "from the DB");
				System.out.println();
				adminFacade.removeCompany(microsoft);
				allCompanies =  adminFacade.getAllCompanies();
				System.out.println("The number of companies in the DB after removing one is = " + allCompanies.size());
				System.out.println();
				
				//adminFacade.updateCompany(company);
				System.out.println("Updating " + cocacola.getCompName() + " Email from " + cocacola.getEmail());
				System.out.println();
				Company cocaColaUpdate =  new Company("Coca-Polka", "cocacola6", "Pepsi@Pepsi.com");
				adminFacade.updateCompany(cocaColaUpdate);
				System.out.println("The new email after the update is " 
								+ adminFacade.getComapny(cocacola.getCompName()).getEmail());
				System.out.println();
				
				System.out.println("This are all the companies details in the DB");
				System.out.println("--------------------------------------------");
				allCompanies = adminFacade.getAllCompanies();
				for (Company i:allCompanies){
					System.out.println(i);
				}
				
				System.out.println();
				System.out.println("moving on to the customers methods in the admin facade!");
				System.out.println();


				Customer tarazan = new Customer("Tarazan Jungle", "CaSa2");
				Customer jane = new Customer("Jane Distress", "8bLaAcA");
				System.out.println("Adding two customers to the DB");
				System.out.println("------------------------------");
				System.out.println(tarazan.getCustName() + " & " + jane.getCustName());
				System.out.println();
				
				//getAllCustomers
				Collection<Customer> allCustomers = adminFacade.getAllCustomers();
				System.out.println("So the customers DB will go from (calling on .getAllCustomers here) " + adminFacade.getAllCustomers().size() + " to 2");
				
				//create Customer
				adminFacade.createCustomer(tarazan);
				adminFacade.createCustomer(jane);
				
				System.out.println();
				System.out.println("And to proof it here are the new customers details from the DB:");
				System.out.println("-------------------------------------------------------------");
				allCustomers = adminFacade.getAllCustomers();
				for (Customer customer:allCustomers){
					System.out.println(customer);
				}
				System.out.println();
				
				allCustomers = adminFacade.getAllCustomers();
				for (Customer customer:allCustomers){
					System.out.println(customer);
				}
				System.out.println();
				System.out.println("Updating this customer password to a new one");
				System.out.println();
				Customer janeUpdate = new Customer("Jane Distress", "123Pass321");
				//update customer
				adminFacade.updateCustomer(janeUpdate);
				allCustomers = adminFacade.getAllCustomers();
				for (Customer customer:allCustomers){
					System.out.println(customer);
				}
				
				System.out.println();
				System.out.println("Moving on to the Company Facade! (***pls hide the Admin facade tests - lines 35 - 165)");
				System.out.println();
				
		}catch(Exception e){
		//	e.printStackTrace();
			System.out.println(e.getMessage());
		}
		


	
		

	
	

		// company login
		try {
			clientFacade = couponSystem.login("Coca-Polka", "cocacola6", ClientType.COMPANY);
			CompanyFacade companyFacade = (CompanyFacade)clientFacade;
			
			
			// this company details
			System.out.println("This company details are:");
			System.out.println(companyFacade.showCompany());
			
			long timeMilli = Calendar.getInstance().getTimeInMillis();	
			Date endDate = new Date(timeMilli);
			endDate.setYear(2020-1900);
			//create Coupon
			Coupon coupon = new Coupon("Fanta Box", 10 ,"HEALTH", "Its the orange stuff",9.99, "http://www.coca-colacompany.com", endDate);
//			
				System.out.println("Adding this Coupon to the DB (cupon.getTitle)" + coupon.getTitle() );
				System.out.println();
				
				//create coupon
				companyFacade.createCoupon(coupon);
				
				//getAllCoupons
				System.out.println("printing the coupons from the DB:");
				Set <Coupon> allCoupons = (Set<Coupon>) companyFacade.getAllCoupons();
				for (Coupon i:allCoupons){
					System.out.println(i);
				}
				
				System.out.println("updating this coupon price and end_date");
				Coupon updateCoupon = new Coupon("Fanta Box", 10 ,"HEALTH", "Its the orange stuff",5.99, "http://www.coca-colacompany.com", endDate);
				//update coupons price and end_date
				companyFacade.updateCoupon(updateCoupon);
				System.out.println("the Coupon details after the update are:");
				allCoupons = (Set<Coupon>) companyFacade.getAllCoupons();
				for (Coupon i:allCoupons){
					System.out.println(i);
				}

				System.out.println("moving on to removing this coupon...");
				System.out.println();
				//remove coupon
				companyFacade.removeCoupon(coupon);
				
				System.out.println("printing the numbers of coupons from the DB:");
				allCoupons = (Set<Coupon>) companyFacade.getAllCoupons();
				System.out.println("The number of coupons in the DB is now = " + allCoupons.size());
				System.out.println();
				
				System.out.println("Adding a bunch of new coupons");
				
				Coupon coupon1 = new Coupon("Coke", 10 ,"FOOD", "For the addicts of you",10, "coke.com", endDate);
				Coupon coupon2 = new Coupon("Sprite", 20 ,"CAMPING", "The arificial lemo",9, "sprite.com", endDate);
				Coupon coupon3 = new Coupon("Pepto", 5 ,"HEALTH", "Not sure here",8, "pepto.com", endDate);
				Coupon coupon4 = new Coupon("Water", 8 ,"SPORTS", "From your tap!",7, "water.com", endDate);
				Coupon coupon5 = new Coupon("FizzyBubly", 90 ,"ELECTRICITY", "Cheers mate",6, "fizzy.uk", endDate);
				
				companyFacade.createCoupon(coupon1); companyFacade.createCoupon(coupon2); companyFacade.createCoupon(coupon3);
				companyFacade.createCoupon(coupon4); companyFacade.createCoupon(coupon5);
				
				allCoupons = (Set<Coupon>) companyFacade.getAllCoupons();
				for (Coupon i:allCoupons){
					System.out.println(i);
				}
				
				System.out.println();
				System.out.println("get all coupons up to price of 8$");
				System.out.println();

				allCoupons = (Set<Coupon>) companyFacade.getCouponsToPrice(8.00);
				for (Coupon i:allCoupons){
					System.out.println(i);
				}

				System.out.println();
				System.out.println("get all coupons of type health");

				allCoupons = (Set<Coupon>)companyFacade.getCouponByType(CouponType.HEALTH);
				for (Coupon i:allCoupons){
					System.out.println(i);
				}
				
				
				System.out.println();
				System.out.println("get all coupons until end date 2018-11-10");

				allCoupons = (Set<Coupon>)companyFacade.getCouponsUntillEndDate(2018, 12, 06);
				for (Coupon i:allCoupons){
					System.out.println(i);
				}
				
				
				System.out.println();
				System.out.println("Moving on to the Customer Facade! (***pls hide the company facade tests - lines 170 - 270)");
				System.out.println();
			
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}
	
	
	
	

	
	
	
		// customer login
		try {
			clientFacade = couponSystem.login("Jane Distress", "123Pass321", ClientType.CUSTOMER);
			CustomerFacade customerFacade = (CustomerFacade)clientFacade;
			
			long timeMilli = Calendar.getInstance().getTimeInMillis();	
			Date endDate = new Date(timeMilli);
			endDate.setYear(2023-1900);
			
			System.out.println("Welcome to the customer facade!");
			System.out.println();
			System.out.println("Buying a FizzyBubly and Sprite coupons !");
			Coupon customerCoupon1 = new Coupon("FizzyBubly", 90 ,"ELECTRICITY", "Cheers mate",6, "fizzy.uk", endDate);
			Coupon customerCoupon2 = new Coupon("Sprite", 20 ,"CAMPING", "The artificial lemon",9, "sprite.com", endDate);
			//buying a coupon
			customerFacade.buyCoupon(customerCoupon1);
			customerFacade.buyCoupon(customerCoupon2);
			System.out.println();
			System.out.println("Here are all the customers coupons:");
			System.out.println("-----------------------------------");
			//getAllCoupons
			Set<Coupon>customerCoupons = (Set<Coupon>) customerFacade.getCustomersCoupons();
			for (Coupon i:customerCoupons){
				System.out.println(i);
			}
			System.out.println();
			System.out.println("Get coupons by type RESTURANTS");
			System.out.println("-------------------------------");
			customerCoupons = (Set<Coupon>) customerFacade.getCouponByType(CouponType.CAMPING);
			for (Coupon i:customerCoupons){
				System.out.println(i);
			}
			System.out.println();
			System.out.println("Get coupons up to price of 7$");
			System.out.println("-------------------------------");
			customerCoupons = (Set<Coupon>) customerFacade.getCouponsToPrice(7.00);
			for (Coupon i:customerCoupons){
				System.out.println(i);
			}
			System.out.println();
			System.out.println("Moving on to the cross facade Tests! (***please hide the customer facade tests - lines 275 - 320");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		

		// Cross Facade Tests
				try {
					clientFacade = couponSystem.login("Coca-Polka", "cocacola6", ClientType.COMPANY);
					CompanyFacade companyFacade = (CompanyFacade)clientFacade;
					
					// this company details
					System.out.println("Welcome to the Company facade!");
					System.out.println(companyFacade.showCompany());
					
					System.out.println("Removing Sprite Coupon from this company after it was bought by a customer");
					System.out.println("The coupon details are:");
					System.out.println("-----------------------");
					
					
					
					long timeMilli = Calendar.getInstance().getTimeInMillis();	
					Date endDate = new Date(timeMilli);
					endDate.setYear(2024-1900);
					
					System.out.println(companyFacade.getCouponByType(CouponType.CAMPING));
					HashSet<Coupon> allCoupons = (HashSet<Coupon>) companyFacade.getCouponByType(CouponType.CAMPING);
					Coupon couponToDelete = new Coupon("Sprite", 20 ,"CAMPING", "The artificial lemon",9, "sprite.com", endDate);
					companyFacade.removeCoupon(couponToDelete);

										
					clientFacade = couponSystem.login("Jane Distress", "123Pass321", ClientType.CUSTOMER);
					CustomerFacade customerFacade = (CustomerFacade)clientFacade;
					System.out.println();
					System.out.println("Welcome to the customer facade!");
					System.out.println("Hello:" + customerFacade.showCustomer().getCustName());
					System.out.println();
					System.out.println();
					System.out.println("Here are all the customers coupons - note that Sprite has gone");
					System.out.println("--------------------------------------------------------------");
					//getAllCoupons
					Set<Coupon>customerCoupons = (Set<Coupon>) customerFacade.getCustomersCoupons();
					for (Coupon i:customerCoupons){
						System.out.println("There were  " + i.getAmount()  + " " + i.getTitle() + " coupons. The amount of coupons left is: " + i.getAmountLeft());
					}
					
					
					clientFacade = couponSystem.login("admin", "1234", ClientType.ADMIN);					
					AdminFacade adminFacade = (AdminFacade)clientFacade;
					System.out.println();
					System.out.println("Welcome to the Admin facade!");
					System.out.println();
					
					//removing customer with coupons;
					System.out.println("After Deleting a customer -");
					System.out.println("the amount left of a coupon bought by the customer should go up by one");
					
					
					Set<Customer> allCustomers = (Set<Customer>) adminFacade.getAllCustomers();
					Iterator<Customer> it = allCustomers.iterator();
					Customer customerToRemove = it.next();
					
					System.out.println("the Customer is: " + customerToRemove.getCustName());
					System.out.println();
					System.out.println("Removing the customer...");
					System.out.println();
					
					clientFacade = couponSystem.login("Coca-Polka", "cocacola6", ClientType.COMPANY);
					companyFacade = (CompanyFacade)clientFacade;
					
					
					// this company details
					System.out.println("Welcome to the company facade!");
					System.out.println(companyFacade.showCompany());
					
					System.out.println();
					System.out.println("After deleting the only customer who bought a coupon"
										+ " the amount_left should by the same as the amount");
					
					System.out.println();
					System.out.println("Here is a list of all the company's coupons:");
					
					Collection<Coupon> allCompaniesCoupons = (Set<Coupon>) companyFacade.getAllCoupons();
					for (Coupon i:allCompaniesCoupons){
						System.out.println("Copon title: " + i.getTitle() + ", amount: " 
											+ i.getAmount() + ", amount left: " + i.getAmountLeft());
					}
					
					System.out.println();
					System.out.println("Done! continue to web page.../LoginSystem.html");
					
				} catch (Exception e) {
					System.out.println(e.getMessage());
				
}
	}
}