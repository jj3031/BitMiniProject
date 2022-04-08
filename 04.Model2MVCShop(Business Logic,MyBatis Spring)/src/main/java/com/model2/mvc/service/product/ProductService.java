package com.model2.mvc.service.product;

import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;


//==> 회원관리에서 서비스할 내용 추상화/캡슐화한 Service  Interface Definition  
public interface ProductService {
	
	// 상품추가
	public void addProduct(Product product) throws Exception;
	
	// 상품 정보 확인
	public Product getProduct(int prodNo) throws Exception;
	
	// 상품정보리스트 
	public Map<String, Object> getProductList(Search search) throws Exception;
	
	// 상품정보수정
	public Product updateProduct(Product productVO) throws Exception;
	
	
}