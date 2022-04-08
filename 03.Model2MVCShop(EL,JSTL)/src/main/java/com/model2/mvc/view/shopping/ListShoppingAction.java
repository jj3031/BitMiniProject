package com.model2.mvc.view.shopping;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;


public class ListShoppingAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		System.out.println("listPurchase 시작");
		
		User dbUser = new User();
		
		HttpSession session=request.getSession();
		dbUser = (User)session.getAttribute("user");
		
		Search search=new Search();
		String userId =dbUser.getUserId();
		
		int currentPage=1;
		
		if(request.getParameter("currentPage") != null) {
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
		}
		
		search.setCurrentPage(currentPage);
		search.setSearchCondition(request.getParameter("searchCondition"));
		search.setSearchKeyword(request.getParameter("searchKeyword"));
		
		// web.xml  meta-data 로 부터 상수 추출
		int pageSize = Integer.parseInt( getServletContext().getInitParameter("pageSize"));
		int pageUnit  =  Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		search.setPageSize(pageSize);

		// Business logic 수행
		PurchaseService service=new PurchaseServiceImpl();
		Map<String,Object> map=service.getPurchaseList(search, dbUser.getUserId());

		Page resultPage	= 
				new Page( currentPage, ((Integer)map.get("purchaseTotalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListPurchaseAction ::"+resultPage);
	
		// Model 과 View 연결
		request.setAttribute("purchaseList", map.get("purchaseList"));
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("search", search);
		
		
		
		
		System.out.println("listPurchase 끝");
		return "forward:/purchase/listPurchase.jsp";
	}
	
}