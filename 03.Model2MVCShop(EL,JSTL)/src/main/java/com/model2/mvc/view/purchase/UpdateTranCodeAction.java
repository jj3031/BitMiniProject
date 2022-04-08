package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.*;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;



public class UpdateTranCodeAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		
		
		Purchase purchaseVO=new Purchase();
		purchaseVO.setTranNo(Integer.parseInt(request.getParameter("tranNo")));
		purchaseVO.setTranCode(request.getParameter("tranCode"));
		System.out.println(request.getParameter("tranCode"));
		
		
		PurchaseService service=new PurchaseServiceImpl();
		service.updateTranCode(purchaseVO);
		

		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		
		if(user.getRole().equals("admin")) {
			return "redirect:/listSale.do?menu=manage";
		}else {
			return "redirect:/listPurchase.do?menu=search";
		}
		
		
	}
}