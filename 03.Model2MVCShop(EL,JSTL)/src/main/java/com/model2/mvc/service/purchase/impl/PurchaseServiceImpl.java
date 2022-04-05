package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;


public class PurchaseServiceImpl implements PurchaseService{
	
	private PurchaseDAO purchaseDAO;

	
	
	public PurchaseServiceImpl() {
		purchaseDAO=new PurchaseDAO();
		System.out.println("구매서비스임플 생성");
	}

	@Override
	public Purchase addPurchase(Purchase purchaseVO) throws Exception {
		purchaseDAO.insertPurchase(purchaseVO);
		return purchaseVO;
	}

	@Override
	public Purchase getPurchase(int No) throws Exception {
		return purchaseDAO.findPurchase(No);
	}

	@Override
	public Map<String, Object> getPurchaseList(Search search, String userId) throws Exception {
		System.out.println("구매리스트임플 생성");
		return purchaseDAO.getPurchaseList(search, userId);
	}

	@Override
	public Map<String, Object> getSaleList(Search search) throws Exception {
		return purchaseDAO.getSaleList(search);
	}

	@Override
	public Purchase updatePurchase(Purchase purchaseVO) throws Exception {
		purchaseDAO.updatePurchase(purchaseVO);
		return purchaseVO;
	}

	@Override
	public void updateTranCode(Purchase purchaseVO) throws Exception {
		
		purchaseDAO.updateTranCode(purchaseVO);
		
	}
	


}