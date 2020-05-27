package com.services.coupons;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ExceptionsCoupons.CompanyException;
import ExceptionsCoupons.CouponException;
import ExceptionsCoupons.CustomerException;
import ExceptionsCoupons.DbConnectionException;
import ExceptionsCoupons.InvalidInputException;
import clients.ClientType;
import clients.CouponSystem;
import coupon_beans.Company;
import coupon_beans.Coupon;
import coupon_beans.Customer;
import coupon_facade.AdminFacade;
import coupon_facade.CouponClientFacade;

@Path("/admin")
public class AdminService {

	AdminFacade facade;
	
	@Context private HttpServletRequest request;
	
//	@POST
//	@Path("/createTest")
//	@Consumes(MediaType.APPLICATION_JSON)
//	public void createTest(Company test)
//	{
//		System.out.println("inside AdminService create comapny - test");
//		System.out.println("json is " + test.getCompName());
//		
//		
//	}

	@POST
	@Path("/createCompany")
	@Consumes(MediaType.APPLICATION_JSON)
	public void createCompany(Company c) 
	{
//		System.out.println("inside AdminService create comapny");
		HttpSession session = request.getSession(false);
		//check if inside createComapny Service
//		System.out.println("inside AdminService - add company");
		//get session
		AdminFacade facade = (AdminFacade)session.getAttribute("facade");
		//check if session exist
		if (facade!=null){
//			System.out.println("inside AdminService - facade is not null");
		}
		//example for checking the variables before sending to the DB
		if(c.getCompName().length() < 3)
			throw new CouponSystemException("Company name not valid", Response.Status.NOT_ACCEPTABLE);
		try {
			facade.createCompany(c);
		} catch (DbConnectionException e) {
			// TODO Auto-generated catch block
			throw new CouponSystemException("Coupon System Error", Response.Status.SERVICE_UNAVAILABLE);
		} catch (CompanyException e) {
			// TODO Auto-generated catch block
			throw new CouponSystemException (e.getMessage(), Response.Status.SERVICE_UNAVAILABLE);
		}
		
	}
	
	@POST
	@Path("/createCustomer")
	@Consumes(MediaType.APPLICATION_JSON)
	public void createCustomer(Customer c) 
	{
//		System.out.println("inside AdminService create customer");
		HttpSession session = request.getSession(false);
		//check if inside createComapny Service
//		System.out.println("inside AdminService - add customer");
		//get session
		AdminFacade facade = (AdminFacade)session.getAttribute("facade");
		//check if session exist
		if (facade!=null){
//			System.out.println("inside AdminService - facade is not null");
		}
		//example for checking the variables before sending to the DB
		if(c.getCustName().length() < 3)
			throw new CouponSystemException("Customer name not valid", Response.Status.NOT_ACCEPTABLE);
		try {
			facade.createCustomer(c);
		} catch (DbConnectionException e) {
			// TODO Auto-generated catch block
			throw new CouponSystemException("Coupon System Error", Response.Status.SERVICE_UNAVAILABLE);
		} catch (CustomerException e) {
			// TODO Auto-generated catch block
//			System.out.println(e.getMessage());
			throw new CouponSystemException (e.getMessage(), Response.Status.SERVICE_UNAVAILABLE);
		}
		
	}
	
	@PUT
	@Path("/updateCompany")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateCompany(Company c) 
	{
//		System.out.println("inside AdminService update comapny");
		HttpSession session = request.getSession(false);
		//check if inside createComapny Service
//		System.out.println("inside AdminService - update company");
		//get session
		AdminFacade facade = (AdminFacade)session.getAttribute("facade");
		//check if session exist
		if (facade!=null){
//			System.out.println("inside AdminService - facade is not null");
		}
		try {
			facade.updateCompany(c);
		} catch (DbConnectionException e) {
			throw new CouponSystemException("Coupon System Error", Response.Status.SERVICE_UNAVAILABLE);
		} catch (CompanyException e) {
			throw new CouponSystemException (e.getMessage(), Response.Status.SERVICE_UNAVAILABLE);
		}
		
	}
	
	@PUT
	@Path("/updateCustomer")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateCustomer(Customer c) 
	{
//		System.out.println("inside AdminService update Customer");
		HttpSession session = request.getSession(false);
		//check if inside updateCustomer Service
//		System.out.println("inside AdminService - update customer");
		//get session
		AdminFacade facade = (AdminFacade)session.getAttribute("facade");
		//check if session exist
		if (facade!=null){
//			System.out.println("inside AdminService - facade is not null");
		}
		try {
			facade.updateCustomer(c);
		} catch (DbConnectionException e) {
			throw new CouponSystemException("Coupon System Error", Response.Status.SERVICE_UNAVAILABLE);
		} catch (CustomerException e) {
			throw new CouponSystemException (e.getMessage(), Response.Status.SERVICE_UNAVAILABLE);
		}
		
	}
	
	@POST
	@Path("/removeCompany")
	@Consumes(MediaType.APPLICATION_JSON)
	public void removeCompany(Company comp) {
//		System.out.println("inside AdminService remove comapny");
		HttpSession session = request.getSession(false);
		AdminFacade facade = (AdminFacade) session.getAttribute("facade");
		if (facade == null) {
			throw new CouponSystemException("Not logged in", Response.Status.FORBIDDEN);
		} else {
			try {
				facade.removeCompany(comp);
			} catch (DbConnectionException | CompanyException | CouponException | CustomerException e) {
				throw new CouponSystemException("Error", Response.Status.INTERNAL_SERVER_ERROR);
			}
		}
	}
	
	@POST
	@Path("/removeCustomer")
	@Consumes(MediaType.APPLICATION_JSON)
	public void removecustomer(Customer cust) {
//		System.out.println("inside AdminService remove customer");
		HttpSession session = request.getSession(false);
		AdminFacade facade = (AdminFacade) session.getAttribute("facade");
		if (facade == null) {
			throw new CouponSystemException("Not logged in", Response.Status.FORBIDDEN);
		} else {
			try {
				facade.removeCustomer(cust);
			} catch (DbConnectionException | CouponException | CustomerException | InvalidInputException e) {
				throw new CouponSystemException("Error", Response.Status.INTERNAL_SERVER_ERROR);
			}
		}
	}
	

	
//	@GET
//	@Path("/getTest")
//	@Produces(MediaType.APPLICATION_JSON)
//	public void getTest(@QueryParam("compName") String compName){
//		System.out.println("inside AdminService get company");
//		System.out.println(compName);
//	}
	
	
	@GET
	@Path("/getCompanyByName")
	@Produces(MediaType.APPLICATION_JSON)
	public Company getCompany(@QueryParam("compName") String compName) {
//		System.out.println("inside AdminService get company");
		HttpSession session = request.getSession(false);
		// get session
		AdminFacade facade = (AdminFacade) session.getAttribute("facade");
		// check if session exist
		Company company;
		if (facade != null) {
			try {
//				System.out.println("facade not null ");
				company = facade.getComapny(compName);
				if (company != null) {
//					System.out.println("Company is not null " + company.toString());
					return company;
				} else{}
					throw new CouponSystemException("Coupon System Error", Response.Status.SERVICE_UNAVAILABLE);
			} catch (DbConnectionException | CompanyException | InvalidInputException e) {
				// TODO Auto-generated catch block
//				System.out.println(e.getMessage());
				throw new CouponSystemException("Coupon System Error", Response.Status.SERVICE_UNAVAILABLE);
				
			}
		}
		throw new CouponSystemException("Coupon System Error", Response.Status.SERVICE_UNAVAILABLE);
	}
	
	
	@GET
	@Path("/getAllCompanies")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Company> getCompanies (){
//		System.out.println("inside AdminService get company");
		HttpSession session = request.getSession(false);
		// get session
		AdminFacade facade = (AdminFacade) session.getAttribute("facade");
		// check if session exist
		Collection companies;
		try{
			companies = facade.getAllCompanies();
			return companies;
		}catch (Exception e){
			throw new CouponSystemException("Error retrieving coupons", Response.Status.SERVICE_UNAVAILABLE);
		}
	}

	@GET
	@Path("/getAllCustomers")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Customer> getCustomers (){
//		System.out.println("inside AdminService get Customer");
		HttpSession session = request.getSession(false);
		// get session
		AdminFacade facade = (AdminFacade) session.getAttribute("facade");
		// check if session exist
		Collection customers;
		try{
			customers = facade.getAllCustomers();
			return customers;
		}catch (Exception e){
			throw new CouponSystemException("Error retrieving coupons", Response.Status.SERVICE_UNAVAILABLE);
		}
	}
	
	@GET
	@Path("/getCompanyCoupons")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getCompCoupons(@QueryParam("compId") Integer compId) {
//		System.out.println("inside AdminService get company's coupons");
		HttpSession session = request.getSession(false);
		// get session
		AdminFacade facade = (AdminFacade) session.getAttribute("facade");
		// check if session exist
		Collection<Coupon> compCoupons = null;
		if (facade != null) {
			try {
//				System.out.println("facade not null ");
				compCoupons = facade.getCompaniesCoupons(compId);
				if (compCoupons != null) {
//					System.out.println("This company has " + compCoupons.size() + " Coupons");
					return compCoupons;
				} else {
					throw new CouponSystemException("No Coupons For This Company", Response.Status.NOT_FOUND);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
//				System.out.println(e.getMessage());
				throw new CouponSystemException("No Coupons For This Company", Response.Status.NOT_FOUND);
			}
		}
		throw new CouponSystemException("Coupon System Error", Response.Status.SERVICE_UNAVAILABLE);
	}
	
	
	@GET
	@Path("/login")
	@Produces(MediaType.TEXT_PLAIN)
	public String login(@QueryParam("username") String username, @QueryParam("password") String password) {
//		System.out.println("inside loginservice");
		CouponSystem couponSystem = CouponSystem.INSTANCE;
		try {
			HttpSession session = request.getSession(true);
			CouponClientFacade clientFacade = null;
			clientFacade = couponSystem.login(username, password, ClientType.ADMIN);
		
		// couponSystem
		if (clientFacade != null) {
			AdminFacade facade = (AdminFacade)clientFacade;
			session.setAttribute("facade", facade);
			return "SuccessAdmin";
		} else 
			return "Invalid entry";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Invalid entry exception";
		}
	}
}

