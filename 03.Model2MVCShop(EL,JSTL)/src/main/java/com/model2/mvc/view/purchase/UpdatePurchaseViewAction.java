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


public class UpdatePurchaseViewAction extends Action{

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		
		System.out.println("업데이트뷰 시작");
		request.getAttribute("tranNo");
		
		Purchase purchaseVO = new Purchase();
		System.out.println(request.getParameter("tranNo"));
		
		PurchaseService purchaseService = new PurchaseServiceImpl();
		purchaseVO = purchaseService.getPurchase(Integer.parseInt(request.getParameter("tranNo")));

		
		request.setAttribute("PurchaseVO", purchaseVO);
		
		return "forward:/purchase/updatePurchaseView.jsp";
	}
}
