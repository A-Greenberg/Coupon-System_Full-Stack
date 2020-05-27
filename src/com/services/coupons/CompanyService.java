package com.services.coupons;

import java.sql.Date;
import java.util.Calendar;
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
import coupon_beans.CouponType;
import coupon_facade.AdminFacade;
import coupon_facade.CompanyFacade;
import coupon_facade.CouponClientFacade;

@Path("/company")
public class CompanyService {

	CompanyFacade facade;
	
	@Context private HttpServletRequest request;


	@GET
	@Path("/login")
	@Produces(MediaType.TEXT_PLAIN)
	public String login(@QueryParam("username") String username, @QueryParam("password") String password) {
		CouponSystem couponSystem = CouponSystem.INSTANCE;
		try {
			HttpSession session = request.getSession(true);
			CouponClientFacade clientFacade = null;
			clientFacade = couponSystem.login(username, password, ClientType.COMPANY);
		
		// couponSystem
		if (clientFacade != null) {
			facade = (CompanyFacade)clientFacade;
			session.setAttribute("facade", facade);
			return "SuccessCompany";
		} else 
			return "Invalid entry";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return "Invalid entry exception";
		}
	}
	
	@POST
	@Path("/createCoupon")
	@Consumes(MediaType.APPLICATION_JSON)
	public void createCoupon(Coupon c)	{
		HttpSession session = request.getSession(false);
		//check if inside createComapny Service
		//get session
		CompanyFacade facade = (CompanyFacade)session.getAttribute("facade");
		//check if session exist
		if (facade!=null){
		}
		try {
			Coupon SdateCoupon = new Coupon (c.getTitle(), c.getAmount(), c.getType().toString(), c.getMessage(), c.getPrice(), c.getImage(), c.getEndDate());
			facade.createCoupon(SdateCoupon);
		} catch (DbConnectionException | CouponException | CompanyException | InvalidInputException e) {
			// TODO Auto-generated catch block
			throw new CouponSystemException (e.getMessage(), Response.Status.SERVICE_UNAVAILABLE);
		}
	
	}
	
	@GET
	@Path("/getAllCoupons")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection <Coupon> getCoupons (){
		HttpSession session = request.getSession(false);
		// get session
		CompanyFacade facade = (CompanyFacade) session.getAttribute("facade");
		// check if session exist
		Collection coupons;
		try{
			coupons = facade.getAllCoupons();
			return coupons;
		}catch (Exception e){
			throw new CouponSystemException("Error retrieving coupons", Response.Status.SERVICE_UNAVAILABLE);
		}
	}
	
	
	@GET
	@Path("/getAllCouponsByType")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection <Coupon> getCouponsByType (@QueryParam("type") String type){
		HttpSession session = request.getSession(false);
		// get session
		CompanyFacade facade = (CompanyFacade) session.getAttribute("facade");
		// check if session exist
		Collection coupons;
		try{
			coupons = facade.getCouponByType(CouponType.valueOf(type));
			return coupons;
		}catch (Exception e){
			throw new CouponSystemException("Error retrieving coupons", Response.Status.SERVICE_UNAVAILABLE);
		}
	}
	
	@GET
	@Path("/getAllCouponsByPrice")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection <Coupon> getCouponsByPrice(@QueryParam("price") Double price){
		HttpSession session = request.getSession(false);
		// get session
		CompanyFacade facade = (CompanyFacade) session.getAttribute("facade");
		// check if session exist
		Collection coupons;
		try{
			coupons = facade.getCouponsToPrice(price);
			return coupons;
		}catch (Exception e){
			throw new CouponSystemException("Error retrieving coupons", Response.Status.SERVICE_UNAVAILABLE);
		}
	}
	
	@GET
	@Path("/getAllCouponsByDate")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection <Coupon> getCouponsByEndDate (@QueryParam("date") long date){
//		System.out.println(date);
		
		HttpSession session = request.getSession(false);
		// get session
		CompanyFacade facade = (CompanyFacade) session.getAttribute("facade");
		// check if session exist
		Collection coupons;
		try{
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(date);
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			coupons = facade.getCouponsUntillEndDate(year, month, day);
//			System.out.println(year/*+ "" +  month + "" +  day*/);
			return coupons;
		}catch (Exception e){
//			System.out.println(e.getMessage());
			throw new CouponSystemException("Error retrieving coupons", Response.Status.SERVICE_UNAVAILABLE);
		}
	}
	
	@PUT
	@Path("/updateCoupon")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateCompany(Coupon c) {
		HttpSession session = request.getSession(false);
		//check if inside createComapny Service
		//get session
		CompanyFacade facade = (CompanyFacade)session.getAttribute("facade");
		//check if session exist
		if (facade!=null){
		}
		try {
			System.out.println(c.toString());
			facade.updateCoupon(c);
		} catch (DbConnectionException e) {
			throw new CouponSystemException("Coupon System Error", Response.Status.SERVICE_UNAVAILABLE);
		} catch (CouponException e) {
			throw new CouponSystemException (e.getMessage(), Response.Status.SERVICE_UNAVAILABLE);
		}
		
	}
	
	
	@POST
	@Path("/removeCoupon")
	@Consumes(MediaType.APPLICATION_JSON)
	public void removeCoupon (Coupon c){
		HttpSession session = request.getSession(false);
		//check if inside createComapny Service
		//get session
		CompanyFacade facade = (CompanyFacade)session.getAttribute("facade");
		//check if session exist
		if (facade!=null){
		}
		try {
			facade.removeCoupon(c);
			} catch (DbConnectionException e) {
			throw new CouponSystemException("Coupon System Error", Response.Status.SERVICE_UNAVAILABLE);
		} catch (CouponException e) {
			throw new CouponSystemException (e.getMessage(), Response.Status.SERVICE_UNAVAILABLE);
		} catch (CompanyException e) {
			throw new CouponSystemException (e.getMessage(), Response.Status.SERVICE_UNAVAILABLE);
		} catch (CustomerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
