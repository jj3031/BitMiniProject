package com.model2.mvc.service.product.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.user.UserService;


/*
 *	FileName :  UserServiceTest.java
 * �� JUnit4 (Test Framework) �� Spring Framework ���� Test( Unit Test)
 * �� Spring �� JUnit 4�� ���� ���� Ŭ������ ���� ������ ��� ���� �׽�Ʈ �ڵ带 �ۼ� �� �� �ִ�.
 * �� @RunWith : Meta-data �� ���� wiring(����,DI) �� ��ü ����ü ����
 * �� @ContextConfiguration : Meta-data location ����
 * �� @Test : �׽�Ʈ ���� �ҽ� ����
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/commonservice.xml" })
public class ProductServiceTest {

	//==>@RunWith,@ContextConfiguration �̿� Wiring, Test �� instance DI
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

		
	@Test
	public void testAddProduct() throws Exception {
		
		Product product = new Product();
		
		product.setFileName("�׽�Ʈ");
		product.setProdName("������20");
		product.setQuantity(20);
		product.setProdDetail("�������Ƕ�");
		product.setPrice(1000000);
		
		
		productService.addProduct(product);
		
		//product = productService.getProduct(10030);

		//==> console Ȯ��
		//System.out.println(user);
		
		//==> API Ȯ��
		//Assert.assertEquals("�׽�Ʈ", product.getFileName());
		
	}
	
	@Test
	public void testGetUser() throws Exception {
		
		Product product = new Product();
		//==> �ʿ��ϴٸ�...
//		user.setUserId("testUserId");
//		user.setUserName("testUserName");
//		user.setPassword("testPasswd");
//		user.setSsn("1111112222222");
//		user.setPhone("111-2222-3333");
//		user.setAddr("��⵵");
//		user.setEmail("test@test.com");
		
		product = productService.getProduct(10030);

		//==> console Ȯ��
		//System.out.println(user);
		
		//==> API Ȯ��
		System.out.println(product.getRemain());
		Assert.assertEquals(14, product.getRemain());

	}

	//@Test
	 public void testGetProductListAll() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
		//==> console Ȯ��
	 	//System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("");
	 	map = userService.getUserList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
	 	//==> console Ȯ��
	 	System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
//	 
//	 //@Test
//	 public void testGetUserListByUserId() throws Exception{
//		 
//	 	Search search = new Search();
//	 	search.setCurrentPage(1);
//	 	search.setPageSize(3);
//	 	search.setSearchCondition("0");
//	 	search.setSearchKeyword("admin");
//	 	Map<String,Object> map = userService.getUserList(search);
//	 	
//	 	List<Object> list = (List<Object>)map.get("list");
//	 	Assert.assertEquals(1, list.size());
//	 	
//		//==> console Ȯ��
//	 	//System.out.println(list);
//	 	
//	 	Integer totalCount = (Integer)map.get("totalCount");
//	 	System.out.println(totalCount);
//	 	
//	 	System.out.println("=======================================");
//	 	
//	 	search.setSearchCondition("0");
//	 	search.setSearchKeyword(""+System.currentTimeMillis());
//	 	map = userService.getUserList(search);
//	 	
//	 	list = (List<Object>)map.get("list");
//	 	Assert.assertEquals(0, list.size());
//	 	
//		//==> console Ȯ��
//	 	//System.out.println(list);
//	 	
//	 	totalCount = (Integer)map.get("totalCount");
//	 	System.out.println(totalCount);
//	 }
//	 
//	 //@Test
//	 public void testGetUserListByUserName() throws Exception{
//		 
//	 	Search search = new Search();
//	 	search.setCurrentPage(1);
//	 	search.setPageSize(3);
//	 	search.setSearchCondition("1");
//	 	search.setSearchKeyword("SCOTT");
//	 	Map<String,Object> map = userService.getUserList(search);
//	 	
//	 	List<Object> list = (List<Object>)map.get("list");
//	 	Assert.assertEquals(3, list.size());
//	 	
//		//==> console Ȯ��
//	 	System.out.println(list);
//	 	
//	 	Integer totalCount = (Integer)map.get("totalCount");
//	 	System.out.println(totalCount);
//	 	
//	 	System.out.println("=======================================");
//	 	
//	 	search.setSearchCondition("1");
//	 	search.setSearchKeyword(""+System.currentTimeMillis());
//	 	map = userService.getUserList(search);
//	 	
//	 	list = (List<Object>)map.get("list");
//	 	Assert.assertEquals(0, list.size());
//	 	
//		//==> console Ȯ��
//	 	System.out.println(list);
//	 	
//	 	totalCount = (Integer)map.get("totalCount");
//	 	System.out.println(totalCount);
//	 }	 
}