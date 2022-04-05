package com.model2.mvc.service.purchase;

import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;


public interface PurchaseService {
	
	public Purchase addPurchase (Purchase purchaseVO) throws Exception;
	
	public Purchase getPurchase(int No) throws Exception;
	
	public Map<String, Object> getPurchaseList(Search search,String name) throws Exception;
	
	public Map<String, Object> getSaleList(Search search) throws Exception;
	
	public Purchase updatePurchase(Purchase purchaseVO) throws Exception;
	
	public void updateTranCode(Purchase purchaseVO) throws Exception;
	
}