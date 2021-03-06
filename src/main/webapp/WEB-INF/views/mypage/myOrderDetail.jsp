<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/header.jsp"%>

<title>주문 상세</title>

<link href="resources/css/orderDetail.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="resources/js/myPage.js" ></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#orderCommit").click(function(e){
		e.preventDefault();
		if(${deliveryInfo.delivery_state eq '구매확정'}){
			alert("이미 구매확정된 주문 건입니다.");
			return;
		}else if(${deliveryInfo.delivery_state eq '주문취소'}){
			alert("주문취소된 건이므로 구매확정하실 수 없습니다.");
			return;
		}else if(${deliveryInfo.delivery_state ne '배송완료'}){
			alert("배송완료후 구매확정이 가능합니다.");
			return;
		}else{
			if(confirm("구매를 확정하시겠습니까?")){
				alert("구매확정 처리되셨습니다.");
				location.href = "orderCommit.do?delivery_state=commit&delivery_seq=${deliveryInfo.delivery_seq}&order_seq=${payInfo.order_seq}&user_id=${userVO.user_id}";
			}else{
				return;
			}
		}
	});
	
	$("#orderCancel").click(function(e){
		e.preventDefault();
		if(${payInfo.pay_status eq '주문취소'}){
			alert("이미 주문취소 상태입니다.");
			return;
		}else if(${deliveryInfo.delivery_state eq '배송중'}){
			alert("배송중에는 주문취소가 되지 않습니다. 배송완료 후 다시 시도해주세요.");
			return;
		}else if(${payInfo.pay_status eq '구매확정'}){
			alert("이미 구매확정된 주문건은 취소하실 수 없습니다. 고객센터로 문의바랍니다.");
			return;
		}else {
			if(confirm("주문취소 하시겠습니까?")){
				alert("주문취소 처리되셨습니다.");
				location.href = "orderCancel.do?order_seq=${payInfo.order_seq}&delivery_seq=${deliveryInfo.delivery_seq}&pay_use_point=${payInfo.pay_use_point}";
			}else{
				return;
			}
		}
	});
	
	$(".writeReview").click(function(e){
		if(${deliveryInfo.delivery_state ne '구매확정'}){
			alert("구매확정 후 상품후기를 작성하실 수 있습니다.");
			e.preventDefault();
			return;
		}
	});
	
});
</script>
<div id="contentWrapper">
	<div id="contentWrap">
		<%@include file="/WEB-INF/views/mypage/myPageCommon.jsp"%>
		<hr>
		<div id="content">
			<div id="orderSt">
				<div class="tit-pop">
					<h1>주문정보</h1>
					<span class="txt"><em class="order_name">${userVO.user_name}</em>님께서 
					<em class="order_date"><fmt:formatDate value="${receiverInfo.order_date}" pattern="yyyy년 MM월 dd일 a h시 mm분"/></em>에 주문하신 내역입니다.</span>
				</div>
				<ul class="tab">
					<li><a href="#">주문상세</a></li>
				</ul>
				<div id="orderInfo">
					<h2>주문자정보</h2>
					<div class="table-w table-orderinfo">
						<table summary="">
							<caption>주문자정보</caption>
							<colgroup>
								<col width="120">
								<col width="*">
								<col width="120">
								<col width="*">
							</colgroup>
							<tbody>
								<tr>
									<th scope="row"><div class="tb-left">주문번호</div></th>
									<td><div class="tb-left">${receiverInfo.order_seq}</div></td>
									<th scope="row"><div class="tb-left">주문일자</div></th>
									<td><div class="tb-left"><fmt:formatDate value="${receiverInfo.order_date}" pattern="yyyy년 MM월 dd일"/></div></td>
								</tr>
								<tr>
									<th scope="row"><div class="tb-left">주문자</div></th>
									<td><div class="tb-left">${receiverInfo.user_name}</div></td>
									<th scope="row"><div class="tb-left">주문서 입금현황</div></th>
									<td><div class="tb-left">${payInfo.pay_status}</div></td>
								</tr>
								
							</tbody>
						</table>
					</div>
					<h2>배송지정보</h2>
					<div class="table-w table-region">
						<table summary="">
							<caption>배송지정보</caption>
							<colgroup>
								<col width="120">
								<col width="*">
								<col width="120">
								<col width="*">
							</colgroup>
							<tbody>
								<tr>
									<th scope="row"><div class="tb-left">배송번호</div></th>
									<td>
										<div class="tb-left">${deliveryInfo.delivery_seq}</div>
									</td>

									<th scope="row"><div class="tb-left">송장번호</div></th>
									<td>
										<div class="tb-left">롯데택배 ${lotteRandomNum}</div>
									</td>
								</tr>
								<tr>
									<th scope="row"><div class="tb-left">수취인</div></th>
									<td><div class="tb-left">${deliveryInfo.receiver_name}</div></td>

									<th scope="row"><div class="tb-left">연락처</div></th>
									<td><div class="tb-left">${deliveryInfo.receiver_phone1}
											/ ${deliveryInfo.receiver_phone2}</div></td>
								</tr>
								<tr>
									<th scope="row"><div class="tb-left">주소</div></th>
									<td colspan="3"><div class="tb-left">(${deliveryInfo.receiver_zipcode})
											${deliveryInfo.receiver_address1}
											${deliveryInfo.receiver_address2}</div></td>
								</tr>
								<tr>
									<th scope="row"><div class="tb-left">배송메세지</div></th>
									<td colspan="3"><div class="tb-left">${deliveryInfo.receiver_memo}</div></td>
								</tr>
							</tbody>
						</table>
					</div>
					<h2>주문상품</h2>
					<div class="table-w table-prdinfo">
						<table summary="">
							<caption>주문상품정보</caption>
							<colgroup>
								<col width="84">
								<col width="*">
								<col width="150">
								<col width="70">
								<col width="100">
								<col width="80">
								<col width="100">
								<col width="100">
								<col width="100">
							</colgroup>
							<thead>
								<tr>
									<th scope="row" colspan="2"><div class="tb-center">주문상품정보</div></th>
									<th scope="row"><div class="tb-center">상품별주문번호</div></th>
									<th scope="row"><div class="tb-center">수량</div></th>
									<th scope="row"><div class="tb-center">가격</div></th>
									<th scope="row"><div class="tb-center">적립금</div></th>
									<th scope="row"><div class="tb-center">처리상태</div></th>
									<th scope="row"><div class="tb-center">배송번호</div></th>
									<th scope="row"><div class="tb-center">후기작성</div></th>
								</tr>
							</thead>
							<tfoot>
								<tr>
									<td colspan="9">
										<div class="tb-right">(총 상품구매금액 + 배송비) - (사용한 적립금(${payInfo.pay_use_point} point) + 멤버십 추가할인(${salePercent}%)) = <fmt:formatNumber value="${payInfo.pay_total_money}" pattern="#,###"/>원
										</div>
									</td>
								</tr>
							</tfoot>
							<tbody>
								<c:forEach items="${orderProductInfo}" var="opi" varStatus="status">
								<tr>
									<td>
										<div class="tb-center">
											<img src="${opi.prod_title_image}" style="width: 82px; height: 82px">
										</div>
									</td>
									<td>
										<div class="tb-center">
											<a href="/getProduct?prod_code=${opi.prod_code}">${opi.prod_title}</a>
										</div>
									</td>
									<td><div class="tb-center">${opi.order_seq} [${status.count}]</div></td>
									<td><div class="tb-center">${opi.prod_amount}</div></td>
									<td><div class="tb-center tb-price">
											<strong><fmt:formatNumber value="${opi.prod_price_sale * opi.prod_amount}" pattern="#,###"/></strong>
										</div></td>
									<td><div class="tb-center">
											<span class="style4"><fmt:formatNumber value="${opi.prod_point * opi.prod_amount}" pattern="#,###"/></span>
										</div></td>
									<td><div class="tb-center">${opi.delivery_state}</div></td>
									<td><div class="tb-center">${opi.delivery_seq}</div></td>
									<td><div class="tb-center"><a href="review_write_view.do?prod_title=${opi.prod_title}" style="color:#ff08a0" class="writeReview">후기작성</a></div></td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<h2>결제정보</h2>
					<div class="table-w table-payinfo">
						<table summary="">
							<caption>결제정보</caption>
							<colgroup>
								<col width="180">
								<col width="250">
								<col width="*">
							</colgroup>
							<thead>
								<tr>
									<th scope="row"><div class="tb-center">결제수단</div></th>
									<th scope="row"><div class="tb-center">결제금액</div></th>
								</tr>
							</thead>
							<tfoot>
								<tr>
									<td><div class="tb-center tb-nbold">${payInfo.pay_tool}</div></td>
									<td><div class="tb-center"><fmt:formatNumber value="${payInfo.pay_total_money}" pattern="#,###"/>원 (${payInfo.pay_status})</div></td>
								</tr>
							</tfoot>
							<tbody>
								<tr>
									<td><div class="tb-center">사용한 적립금</div></td>
									<td><div class="tb-center"><fmt:formatNumber value="${payInfo.pay_use_point}" pattern="#,###"/> point</div></td>
								</tr>
								<tr>
									<td><div class="tb-center">회원 등급별 할인(%)</div></td>
									<td><div class="tb-center">${salePercent}%</div></td>
								</tr>
							</tbody>
						</table>
					</div>
					
					<div id="pop_order_btn_group">
						<a href="#" class="CSSbuttonWhite" id="orderCancel" style="color:red">주문취소</a>
						<a href="#" class="CSSbuttonWhite" id="orderCommit" style="color:blue">구매확정</a>
					</div>
					<div class="pop_order_btn_close">
						<a href="myOrderList.do" class="CSSbuttonWhite">목록보기</a>
					</div>
				</div>
				<!-- #orderInfo-->
			</div>
			<!-- #orderSt -->
		</div>
		<!-- #content -->
	</div>
	<!-- #contentWrap -->
</div>

<%@include file="/WEB-INF/views/include/footer.jsp"%>
