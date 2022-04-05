package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;


public class ProductDAO {
	
	public ProductDAO(){
	}

	public void insertProduct(Product product) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "insert into PRODUCT values (seq_product_prod_no.nextval,?,?,?,?,?,sysdate,?)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, product.getProdName());
		stmt.setString(2, product.getProdDetail());
		stmt.setString(3, product.getManuDate());
		stmt.setInt(4, product.getPrice());
		stmt.setString(5, product.getFileName());
		stmt.setInt(6, product.getQuantity());
		stmt.executeUpdate();
		
		con.close();
	}

	public Product findProduct(int prodNo) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "select PRODUCT.* ,  A.SUM FROM PRODUCT LEFT OUTER JOIN (SELECT PROD_NO, SUM(purchaseQt) SUM FROM TRANSACTION GROUP BY prod_no) A ON PRODUCT.PROD_NO = A.PROD_NO where PRODUCT.PROD_NO=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);

		ResultSet rs = stmt.executeQuery();
		Product product = null;
		while (rs.next()) {
			int remain =0;
			product = new Product();
			product.setProdNo(rs.getInt("PROD_NO"));
			product.setProdName(rs.getString("PROD_NAME"));
			product.setProdDetail(rs.getString("PROD_DETAIL"));
			product.setManuDate(rs.getString("MANUFACTURE_DAY"));
			product.setPrice(rs.getInt("PRICE"));
			product.setFileName(rs.getString("IMAGE_FILE"));
			product.setRegDate(rs.getDate("REG_DATE"));
			if((rs.getString("SUM"))==null) {
				product.setQuantity(Integer.parseInt(rs.getString("QUANTITY")));
			}else {
				remain = Integer.parseInt(rs.getString("QUANTITY"))- Integer.parseInt(rs.getString("SUM"));
				product.setQuantity(remain);
			}
			if(product.getQuantity()==0) {
				product.setProTranCode("0");
			}else {
				product.setProTranCode("1");
			}
				
		}
		
		con.close();

		return product;
	}

	public Map<String,Object> getProductList(Search search) throws Exception {
		
		Connection con = DBUtil.getConnection();
			System.out.println(search.getSearchCondition());
		String sql = "SELECT PRODUCT.* , A.SUM FROM PRODUCT LEFT OUTER JOIN (SELECT PROD_NO, SUM(purchaseQt) SUM FROM TRANSACTION GROUP BY prod_no) A ON PRODUCT.PROD_NO = A.PROD_NO";
		if (search.getSearchCondition() != null) {
			if (search.getSearchCondition().equals("1")) {
				sql += " where PROD_NO LIKE '%"+ search.getSearchKeyword()
						+ "%'";
			} else if (search.getSearchCondition().equals("2")) {
				sql += " where PROD_NAME LIKE '%" + search.getSearchKeyword()
						+ "%'";
			}else if (search.getSearchCondition().equals("3")) {
				sql += " where PRICE ='" + search.getSearchKeyword()
				+ "'";
			}
		
		}
		if(search.getOrderCondition() != null) {
			if(search.getOrderCondition().equals("priceAsc")) {
				sql += " order by PRODUCT.PRICE ASC";
			} else if(search.getOrderCondition().equals("priceDesc")) {
				sql += " order by PRODUCT.PRICE DESC";
			} else if(search.getOrderCondition().equals("prodNoAsc")) {
				sql += " order by PRODUCT.PROD_NO";
			}
		}
		

		System.out.println("ProductDAO::Original SQL :: " + sql);	
		
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("ProductDAO :: totalCount  :: " + totalCount);
		
		//==> CurrentPage 게시물만 받도록 Query 다시구성
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		

		System.out.println(search);

		Map<String,Object> map = new HashMap<String,Object>();

		List<Product> list = new ArrayList<Product>();
		
		while(rs.next()) {
			int remain =0;
			Product product = new Product();
			product.setProdNo(rs.getInt("PROD_NO"));
			product.setProdName(rs.getString("PROD_NAME"));
			product.setProdDetail(rs.getString("PROD_DETAIL"));
			product.setManuDate(rs.getString("MANUFACTURE_DAY"));
			product.setPrice(rs.getInt("PRICE"));
			product.setFileName(rs.getString("IMAGE_FILE"));
			product.setRegDate(rs.getDate("REG_DATE"));
			if((rs.getString("SUM"))==null) {
				product.setQuantity(Integer.parseInt(rs.getString("QUANTITY")));
			}else {
				remain = Integer.parseInt(rs.getString("QUANTITY"))- Integer.parseInt(rs.getString("SUM"));
				product.setQuantity(remain);
				System.out.println(Integer.parseInt(rs.getString("QUANTITY"))- Integer.parseInt(rs.getString("SUM")));
			}
			
			if(product.getQuantity()==0) {
				product.setProTranCode("0");
			}else {
				product.setProTranCode("1");
			}
				list.add(product);
			}

		//==> totalCount 정보 저장
		map.put("prodTotalCount", new Integer(totalCount));
		//==> currentPage 의 게시물 정보 갖는 List 저장
		map.put("prodList", list);

		rs.close();
		pStmt.close();
		con.close();
			
		return map;
		}

	public void updateProduct(Product productVO) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "update PRODUCT set PROD_NAME=?,PROD_DETAIL=?,MANUFACTURE_DAY=?,PRICE=?,IMAGE_FILE=? where PROD_NO=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate());
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		stmt.setInt(6, productVO.getProdNo());
		stmt.executeUpdate();
		
		con.close();
	}
	
	
	
	//이 이하는 페이지 관리를 위한 부분
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
		
		System.out.println("ProductDAO :: make SQL :: "+ sql);	
		
		return sql;
	}	
}