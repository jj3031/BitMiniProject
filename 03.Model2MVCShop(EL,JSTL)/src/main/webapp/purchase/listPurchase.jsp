<%@ page contentType="text/html; charset=euc-kr" %>
<%@ taglib prefix ="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@ page import="java.util.List"  %>

<%@ page import="com.model2.mvc.service.domain.*" %>
<%@ page import="com.model2.mvc.common.Search" %>
<%@page import="com.model2.mvc.common.Page"%>
<%@page import="com.model2.mvc.common.util.CommonUtil"%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 




<html>
<head>
<title>구매 목록 조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
<!--
// 검색 / page 두가지 경우 모두 Form 전송을 위해 JavaScrpt 이용  
function fncGetUserList(currentPage) {
	document.getElementById("currentPage").value = currentPage;
   	document.detailForm.submit();		
}
-->
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">
<c:if test="${user.role=='admin' }">
<form name="detailForm" action="/listSale.do" method="post">
</c:if>
<c:if test="${user.role=='user' }">
<form name="detailForm" action="/listPurchase.do" method="post">
</c:if>
<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">

						<c:if test="${user.role=='admin' }">
						판매 목록 조회
						</c:if>
						<c:if test="${user.role=='user' }">
						구매 목록 조회
						</c:if>
						
					</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
		</td>
	</tr>
</table>



<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >전체 ${resultPage.totalCount} 건수,	현재 ${resultPage.currentPage} 페이지</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">회원ID</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">회원명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">전화번호</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">배송현황</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">정보수정</td>			
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>

	<c:forEach items="${purchaseList}" var="list" varStatus="status">		
	<tr class="ct_list_pop">
		<td align="center">${status.count}</td>
		<td></td>
	
		<td align="left"><a href="/getPurchase.do?tranNo=${list.tranNo }">${list.buyer.userId}</a></td>
						
		<td></td>
		<td align="left">${list.buyer.userName}</td>
		<td></td>
		<td align="left">${list.buyer.phone}</td>
		<td></td>
		<td align="left">
			
				<c:if test="${list.tranCode=='1' }">
					현재 구매완료 상태입니다.
				</c:if>
				<c:if test="${list.tranCode=='2' }">
					현재 배송 중 상태입니다.
				</c:if>		
				<c:if test="${list.tranCode=='3' }">
					현재 배송 완료 상태입니다.
				</c:if>								
			
		</td>
		<td></td>		
		<td align="left">
		
				<c:if test="${list.tranCode=='1' }">
					<c:if test="${user.role=='admin'}">
						구매완료 <a href="/updateTranCode.do?prodNo=${list.purchaseProd.prodNo}&tranCode=2">배송하기</a>	
					</c:if>
				</c:if>	
				<c:if test="${list.tranCode=='2' }">
					<c:if test="${user.userId == list.buyer.userId }">
					<a href="/updateTranCode.do?prodNo=${list.purchaseProd.prodNo}&tranCode=3">물건도착</a>
					</c:if>	
				</c:if>				
				<c:if test="${list.tranCode=='3' }">
					현재 배송 완료 상태입니다.
				</c:if>					
			

		</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>	
	</c:forEach>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
		   <input type="hidden" id="currentPage" name="currentPage" value=""/>
			 <jsp:include page="../common/pageNavigator.jsp"/>
    	</td>
	</tr>
</table>
<!--  페이지 Navigator 끝 -->

</form>

</div>
</body>
</html>
