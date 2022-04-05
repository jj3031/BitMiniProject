package com.model2.mvc.view.product;

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


public class ListProductAction extends Action {

	@Override
	public String execute(	HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Search search=new Search();
		
		int currentPage=1;
		
		if(request.getParameter("currentPage") != null) {
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
		}
		
		search.setCurrentPage(currentPage);
		
		String orderCondition = "prodNoAsc";
		
		if(request.getParameter("orderCondition")!= null) {
			orderCondition = request.getParameter("orderCondition");
		}
		
		search.setOrderCondition(orderCondition);
		
		
		search.setSearchCondition(request.getParameter("searchCondition"));
		search.setSearchKeyword(request.getParameter("searchKeyword"));
		
		// web.xml  meta-data �� ���� ��� ����
		int pageSize = Integer.parseInt( getServletContext().getInitParameter("pageSize"));
		int pageUnit  =  Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		search.setPageSize(pageSize);
		

		// Business logic ����
		ProductService service=new ProductServiceImpl();
		Map<String,Object> map=service.getProductList(search);

		Page resultPage	= 
				new Page( currentPage, ((Integer)map.get("prodTotalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListProductAction ::"+resultPage);
	
		// Model �� View ����
		request.setAttribute("prodList", map.get("prodList"));
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("search", search);
		
		
		HttpSession session=request.getSession();
		User user = (User)session.getAttribute("user");
		System.out.println(user.getRole());
		
		return "forward:/product/listProduct.jsp";
	}
	
}