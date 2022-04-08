package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;


public class PurchaseDAO {
	
	public PurchaseDAO(){
	}

	public Purchase findPurchase(int tranNo) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "SELECT * FROM TRANSACTION Where TRAN_NO = ?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, tranNo);
		
		ResultSet rs = stmt.executeQuery();
		UserService userService = new UserServiceImpl();
		ProductService productService = new ProductServiceImpl();
		SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		Purchase purchase = null;
		while (rs.next()) {
			purchase = new Purchase();
			purchase.setTranNo(rs.getInt("TRAN_NO"));
			purchase.setPurchaseProd(productService.getProduct(rs.getInt("PROD_NO")));
			purchase.setBuyer(userService.getUser(rs.getString("BUYER_ID")));
			purchase.setPaymentOption(rs.getString("PAYMENT_OPTION"));
			purchase.setReceiverName(rs.getString("RECEIVER_NAME"));
			purchase.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
			purchase.setDivyAddr(rs.getString("DEMAILADDR"));
			purchase.setDivyRequest(rs.getString("DLVY_REQUEST"));
			purchase.setTranCode(rs.getString("TRAN_STATUS_CODE"));
			purchase.setOrderDate(rs.getDate("ORDER_DATA"));
			purchase.setDivyDate(fmt.format(rs.getDate("DLVY_DATE")));
			purchase.setPurchaseQt(Integer.parseInt(rs.getString("PURCHASEQT")));
		}
		
		con.close();
		
		return purchase;
	}
	
	


	public Map<String,Object> getPurchaseList(Search search, String userId) throws Exception {
		System.out.println("getPurchase 함수 시작");
		Connection con = DBUtil.getConnection();
		System.out.println(userId);
		
		String sql = "select * from TRANSACTION WHERE BUYER_ID = '"+userId+"' order by TRAN_NO";
		
		System.out.println("PurchaseDAO::Original SQL :: " + sql);	
		
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("PurchaseDAO :: totalCount  :: " + totalCount);

		//==> CurrentPage 게시물만 받도록 Query 다시구성
		sql = makeCurrentPageSql(sql, search);		
		
		PreparedStatement stmt = 
			con.prepareStatement(	sql,
														ResultSet.TYPE_SCROLL_INSENSITIVE,
														ResultSet.CONCUR_UPDATABLE);
		
		
		
		ResultSet rs = stmt.executeQuery();

		System.out.println(search);
		
		int total = rs.getRow();
		System.out.println("로우의 수:" + total);

		Map<String,Object> map = new HashMap<String,Object>();

		List<Purchase> list = new ArrayList<Purchase>();
		
		UserService userService = new UserServiceImpl();
		ProductService productService = new ProductServiceImpl();
		SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		
		
		while(rs.next()) {
				Purchase vo = new Purchase();
				vo.setTranNo(rs.getInt("TRAN_NO"));
				vo.setPurchaseProd(productService.getProduct(rs.getInt("PROD_NO")));
				vo.setBuyer(userService.getUser(rs.getString("BUYER_ID")));
				vo.setPaymentOption(rs.getString("PAYMENT_OPTION"));
				vo.setReceiverName(rs.getString("RECEIVER_NAME"));
				vo.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
				vo.setDivyAddr(rs.getString("DEMAILADDR"));
				vo.setDivyRequest(rs.getString("DLVY_REQUEST"));
				vo.setTranCode(rs.getString("TRAN_STATUS_CODE"));
				vo.setOrderDate(rs.getDate("ORDER_DATA"));
				vo.setDivyDate(fmt.format(rs.getDate("DLVY_DATE")));
				vo.setPurchaseQt(Integer.parseInt(rs.getString("PURCHASEQT")));
				list.add(vo);
			}

		//==> totalCount 정보 저장
		map.put("purchaseTotalCount", new Integer(totalCount));
		//==> currentPage 의 게시물 정보 갖는 List 저장
		map.put("purchaseList", list);

		System.out.println("리스트사이즈 :"+ list.size());
		rs.close();
		stmt.close();
		con.close();
		System.out.println("getPurchase 함수 끝");
		
		return map;

	}

	public Map<String,Object> getSaleList(Search search) throws Exception {
		

		Connection con = DBUtil.getConnection();
		
		String sql = "select * from TRANSACTION order by TRAN_NO";
		
		System.out.println("PurchaseDAO::Original SQL :: " + sql);	
		
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("PurchaseDAO :: totalCount  :: " + totalCount);

		//==> CurrentPage 게시물만 받도록 Query 다시구성
		sql = makeCurrentPageSql(sql, search);		
		
		PreparedStatement stmt = 
			con.prepareStatement(	sql,
														ResultSet.TYPE_SCROLL_INSENSITIVE,
														ResultSet.CONCUR_UPDATABLE);
		
		
		ResultSet rs = stmt.executeQuery();

		System.out.println(search);
		
		int total = this.getTotalCount(sql);
		System.out.println("로우의 수:" + total);

		Map<String,Object> map = new HashMap<String,Object>();

		List<Purchase> list = new ArrayList<Purchase>();
		
		UserService userService = new UserServiceImpl();
		ProductService productService = new ProductServiceImpl();
		SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		while(rs.next()) {
			Purchase vo = new Purchase();
			vo.setTranNo(rs.getInt("TRAN_NO"));
			vo.setPurchaseProd(productService.getProduct(rs.getInt("PROD_NO")));
			vo.setBuyer(userService.getUser(rs.getString("BUYER_ID")));
			vo.setPaymentOption(rs.getString("PAYMENT_OPTION"));
			vo.setReceiverName(rs.getString("RECEIVER_NAME"));
			vo.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
			vo.setDivyAddr(rs.getString("DEMAILADDR"));
			vo.setDivyRequest(rs.getString("DLVY_REQUEST"));
			vo.setTranCode(rs.getString("TRAN_STATUS_CODE"));
			vo.setOrderDate(rs.getDate("ORDER_DATA"));
			vo.setDivyDate(fmt.format(rs.getDate("DLVY_DATE")));
			vo.setPurchaseQt(Integer.parseInt(rs.getString("PURCHASEQT")));
			list.add(vo);
		}

		//==> totalCount 정보 저장
		map.put("purchaseTotalCount", new Integer(totalCount));
		//==> currentPage 의 게시물 정보 갖는 List 저장
		map.put("purchaseList", list);

		rs.close();
		stmt.close();
		con.close();
			
		return map;

	}
	
	public void insertPurchase(Purchase purchaseVO) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "insert into TRANSACTION values (seq_transaction_tran_no.nextval,?,?,?,?,?,?,?,?,SYSDATE,TO_DATE(?,'YYYY-MM-DD'),?)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setInt(1, purchaseVO.getPurchaseProd().getProdNo());
		stmt.setString(2, purchaseVO.getBuyer().getUserId());
		stmt.setString(3, purchaseVO.getPaymentOption());
		stmt.setString(4, purchaseVO.getReceiverName());
		stmt.setString(5, purchaseVO.getReceiverPhone());
		stmt.setString(6, purchaseVO.getDivyAddr());
		stmt.setString(7, purchaseVO.getDivyRequest());
		stmt.setString(8, purchaseVO.getTranCode());
		String form = purchaseVO.getDivyDate();
		System.out.println(form);
		
		//SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
		//Date to =transFormat.parse(form);
		stmt.setString(9, purchaseVO.getDivyDate());
		stmt.setInt(10, purchaseVO.getPurchaseQt());
		stmt.executeUpdate();
		
		
		con.close();
	}
	
	public void updatePurchase(Purchase purchase) throws Exception {
		Connection con = DBUtil.getConnection();


		String sql = "UPDATE TRANSACTION SET PROD_NO=?, BUYER_ID=?,PAYMENT_OPTION=?, RECEIVER_NAME=?, RECEIVER_PHONE=?,DEMAILADDR=?,DLVY_REQUEST=?,TRAN_STATUS_CODE=?, DLVY_DATE= TO_DATE( ?,'YYYY-MM-DD HH:mi:ss')  WHERE TRAN_NO=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setInt(1, purchase.getPurchaseProd().getProdNo());
		stmt.setString(2, purchase.getBuyer().getUserId());
		stmt.setString(3, purchase.getPaymentOption());
		stmt.setString(4, purchase.getReceiverName());
		stmt.setString(5, purchase.getReceiverPhone());
		stmt.setString(6, purchase.getDivyAddr());
		stmt.setString(7, purchase.getDivyRequest());
		stmt.setString(8, purchase.getTranCode());
		String form = purchase.getDivyDate();

		stmt.setString(9, form );
		stmt.setInt(10, purchase.getTranNo());
		stmt.executeUpdate();
		
		con.close();
	}
	
	public void updateTranCode(Purchase purchase) throws Exception {
		Connection con = DBUtil.getConnection();

		String sql = "update TRANSACTION set TRAN_STATUS_CODE=? where TRAN_NO=?";
		
		
		System.out.println(sql);
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setString(1, purchase.getTranCode());
		stmt.setInt(2, purchase.getTranNo());


		stmt.executeUpdate();
		
		con.close();
	}
	
	private int getTotalCount(String sql) throws Exception {
		
		sql = "SELECT COUNT(*) "+
		          "FROM ( " +sql+ ") countTable";
		
		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		int totalCount = 0;
		if( rs.next() ){
			totalCount = rs.getInt(1);
		}
		
		pStmt.close();
		con.close();
		rs.close();
		
		return totalCount;
	}
	private String makeCurrentPageSql(String sql , Search search){
		sql = 	"SELECT * "+ 
					"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
									" 	FROM (	"+sql+" ) inner_table "+
									"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
					"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
		
		System.out.println("PurchaseDAO :: make SQL :: "+ sql);	
		
		return sql;
	}	
}