package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.user.vo.UserVO;


public class AddPurchaseAction extends Action{

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		
		
		Purchase purchase = new Purchase();
		
		PurchaseService service=new PurchaseServiceImpl();
		ProductService prodService = new ProductServiceImpl();
		UserService userService = new UserServiceImpl();
		
		
		
		purchase.setPurchaseProd(prodService.getProduct(Integer.parseInt(request.getParameter("prodNo")))); 
		purchase.setBuyer(userService.getUser(request.getParameter("buyerId"))); 
		
	
		if(Integer.parseInt(request.getParameter("paymentOption"))==1) {
			purchase.setPaymentOption("1");
		}else {
			purchase.setPaymentOption("2");
		}
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));		
		purchase.setDivyRequest(request.getParameter("receiverRequest"));		
		purchase.setDivyDate(request.getParameter("receiverDate"));
		purchase.setPurchaseQt(Integer.parseInt(request.getParameter("quantity")));
		purchase.setTranCode("1");
				
		service.addPurchase(purchase);
		
		request.setAttribute("purchase", purchase);
		
		
		return "forward:/purchase/readPurchase.jsp";
	}
}
