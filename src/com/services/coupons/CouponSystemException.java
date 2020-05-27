package com.services.coupons;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class CouponSystemException extends WebApplicationException {
	
	public CouponSystemException(String message, Response.Status stat) {
		super(Response.status(stat).entity(message).type(MediaType.TEXT_PLAIN).build());
	}
	
	public CouponSystemException(String message) {
		super(Response.status(Response.Status.CONFLICT).entity(message).type(MediaType.TEXT_PLAIN).build());
	}
}
