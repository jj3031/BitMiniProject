package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;


public class AddPurchaseViewAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute("user");
		
		Purchase purchase=new Purchase();
		ProductService prodService = new ProductServiceImpl();
				
		
		purchase.setPurchaseProd(prodService.getProduct(Integer.parseInt(request.getParameter("prod_no"))));
		purchase.setBuyer(user);
		System.out.println(prodService.getProduct(Integer.parseInt(request.getParameter("prod_no"))).getProdNo());
		
		request.setAttribute("purchase", purchase);
		
		return "forward:/purchase/addPurchaseView.jsp";
	}
}