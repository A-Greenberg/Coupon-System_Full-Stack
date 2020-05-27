package com.services.coupons;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import clients.ClientType;
import clients.CouponSystem;
import coupon_beans.Coupon;
import coupon_beans.CouponType;
import coupon_facade.CompanyFacade;
import coupon_facade.CouponClientFacade;
import coupon_facade.CustomerFacade;

@Path("/customer")
public class CustomerService {

	CustomerFacade facade;
	
	@Context private HttpServletRequest request;

	@GET
	@Path("/login")
	@Produces(MediaType.TEXT_PLAIN)
	public String login(@QueryParam("username") String username, @QueryParam("password") String password) {
		HttpSession session = request.getSession(true);
		CouponSystem couponSystem = CouponSystem.INSTANCE;
		try {
			CouponClientFacade clientFacade = null;
			clientFacade = couponSystem.login(username, password, ClientType.CUSTOMER);
			if (clientFacade != null) {
				facade = (CustomerFacade) clientFacade;
				session.setAttribute("facade", facade);
				return "SuccessCustomer";
			} else
				return "Invalid entry";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Invalid entry exception";
		}
	}



	@GET
	@Path("/getCustomerCoupons")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getcustomerCoupons() {
		HttpSession session = request.getSession(false);
		// get session
		CustomerFacade facade = (CustomerFacade) session.getAttribute("facade");
		// check if session exist
		Collection coupons;
		try {
			coupons = facade.getCustomersCoupons();
			return coupons;
		} catch (Exception e) {
			throw new CouponSystemException("Error retrieving coupons", Response.Status.SERVICE_UNAVAILABLE);
		}
	}
	
	
	@GET
	@Path("/getCouponsForSell")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getCouponsForSell() {
		HttpSession session = request.getSession(false);
		// get session
		CustomerFacade facade = (CustomerFacade) session.getAttribute("facade");
		// check if session exist
		Collection coupons;
		try {
			coupons = facade.getCouponsForSell();
			return coupons;
		} catch (Exception e) {
//			System.out.println(e.getMessage());
			throw new CouponSystemException("Error retrieving coupons", Response.Status.SERVICE_UNAVAILABLE);
		}
	}
	
	
	@GET
	@Path("/getCouponsByType")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getCouponsByType(@QueryParam("type") String type) {
		HttpSession session = request.getSession(false);
		// get session
		CustomerFacade facade = (CustomerFacade) session.getAttribute("facade");
		// check if session exist
		Collection coupons;
		try {
			coupons = facade.getCouponByType(CouponType.valueOf(type));
			return coupons;
		} catch (Exception e) {
//			System.out.println(e.getMessage());
			throw new CouponSystemException("Error retrieving coupons", Response.Status.SERVICE_UNAVAILABLE);
		}
	}
	
	@GET
	@Path("/getCouponsByPrice")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getCouponsByType(@QueryParam("price") Double price) {
		HttpSession session = request.getSession(false);
		// get session
		CustomerFacade facade = (CustomerFacade) session.getAttribute("facade");
		// check if session exist
		Collection coupons;
		try {
			coupons = facade.getCouponsToPrice(price);
			return coupons;
		} catch (Exception e) {
//			System.out.println(e.getMessage());
			throw new CouponSystemException("Error retrieving coupons", Response.Status.SERVICE_UNAVAILABLE);
		}
	}
	
	@POST
	@Path("/buyCoupon")
	@Consumes(MediaType.APPLICATION_JSON)
	public void buyCoupon(Coupon c) {
		HttpSession session = request.getSession(false);
		// get session
		CustomerFacade facade = (CustomerFacade) session.getAttribute("facade");
		// check if session exist
		Collection coupons;
		try {
			facade.buyCoupon(c);
		} catch (Exception e) {
//			System.out.println(e.getMessage());
			throw new CouponSystemException("Error buying coupon", Response.Status.SERVICE_UNAVAILABLE);
		}
	}
	
	/*
	@GET
	@Path("/getAllCoupons")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getCoupons() {
		HttpSession session = request.getSession(false);
		// get session
		CustomerFacade facade = (CustomerFacade) session.getAttribute("facade");
		// check if session exist
		Collection coupons;
		try {
			coupons = facade.getCustomersCoupons();
			return coupons;
		} catch (Exception e) {
			throw new CouponSystemException("Error retrieving coupons", Response.Status.SERVICE_UNAVAILABLE);
		}
	}*/

}


