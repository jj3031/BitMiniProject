package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;


public class UpdatePurchaseAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {

		Purchase purchaseVO= new Purchase();
		
		
		
		PurchaseService service=new PurchaseServiceImpl();
		ProductService prodService = new ProductServiceImpl();
		UserService userService = new UserServiceImpl();
		
		
		//System.out.println("업퍼액에 있는 prodNo"+request.getParameter("prodNo"));
		prodService.getProduct(Integer.parseInt(request.getParameter("prodNo")));
		//System.out.println(prodService.getProduct(Integer.parseInt(request.getParameter("prodNo"))).getProdNo());

		purchaseVO.setPurchaseProd(prodService.getProduct(Integer.parseInt(request.getParameter("prodNo")))); 
		purchaseVO.setBuyer(userService.getUser(request.getParameter("buyerId"))); 
		
	
		if(Integer.parseInt(request.getParameter("paymentOption"))==1) {
			purchaseVO.setPaymentOption("1");
		}else {
			purchaseVO.setPaymentOption("2");
		}
		purchaseVO.setReceiverName(request.getParameter("receiverName"));
		purchaseVO.setReceiverPhone(request.getParameter("receiverPhone"));
		purchaseVO.setDivyAddr(request.getParameter("receiverAddr"));		
		purchaseVO.setDivyRequest(request.getParameter("receiverRequest"));		
		purchaseVO.setDivyDate(request.getParameter("receiverDate"));
		purchaseVO.setTranCode("1");
		purchaseVO.setTranNo(Integer.parseInt(request.getParameter("tranNo")));
		service.updatePurchase(purchaseVO);
		
		request.setAttribute("purchaseVO", purchaseVO);
		

	
		
		return "redirect:/listPurchase.do?menu=search";
	}
}