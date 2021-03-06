<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/header.jsp"%>
<link href="resources/admin_css/pagination.css" rel="stylesheet" type="text/css">
<link href="resources/admin_css/styles.css" rel="stylesheet" type="text/css">
<title>배송현황</title>

<script type="text/javascript" src="resources/js/myPage.js" ></script>

<div id="contentWrapper">
	<div id="contentWrap">
	<%@include file="/WEB-INF/views/mypage/myPageCommon.jsp"%>
		<hr>
		<div id="content">
			<div id="mypage">
				<div class="page-body">
					<!-- 회원 정보 -->
					<div class="info">
						<div class="user">
							<div class="user-img"></div>
							<div class="user-info">
								<p>
									<label>${userVO.user_name }[${userVO.user_id}]님</label>
									<a href="memberInfoUpdate.do" class="CSSbuttonWhite CSSbuttonMin">수정</a>
									
								</p>
								<div class="box">
									<dl>
										<dt>전 &nbsp;&nbsp;&nbsp; 화</dt>
										<dd>${userVO.user_phone }</dd>
									</dl>
									<dl>
										<!-- 이메일 --> 
										<dt>이 메 일</dt>
										<dd>${userVO.user_email }</dd>
									</dl>
									<dl>
										<dt>주 &nbsp;&nbsp;&nbsp; 소</dt>
										<dd>${userVO.user_zipcode } ${userVO.user_address1 } ${userVO.user_address2 }</dd>
									</dl>
								</div>
							</div>
						</div>
						<dl class="order">
							<dt class="tot">회원 등급</dt>
								<c:if test="${userVO.user_level eq 'Dia'}">
									<dd style="color: #6e0ccf">${userVO.user_level}<span style="color:black"> [다이아]</span></dd>
								</c:if>
								<c:if test="${userVO.user_level eq 'Platinum'}">
									<dd style="color: #00e3ae">${userVO.user_level}<span style="color:black"> [플래티넘]</span></dd>
								</c:if>
								<c:if test="${userVO.user_level eq 'Gold'}">
									<dd style="color: #ffd900">${userVO.user_level}<span style="color:black"> [골 드]</span></dd>
								</c:if>
								<c:if test="${userVO.user_level eq 'Silver'}">
									<dd style="color: #e3e3e3">${userVO.user_level}<span style="color:black"> [실 버]</span></dd>
								</c:if>
								<c:if test="${userVO.user_level eq 'Bronze'}">
									<dd style="color: #bd911a">${userVO.user_level}<span style="color:black"> [브론즈]</span></dd>
								</c:if>
							<dt class="tot">총 주문금액</dt>
								<dd><fmt:formatNumber value="${totalOrderMoney}" pattern="#,###"/>원</dd>
							<dt class="tot">적 립 금</dt>
								<dd><fmt:formatNumber value="${userVO.user_point}" pattern="#,###"/>원</dd>
						</dl>
					</div>
					<!-- //회원 정보 -->
					<div id="content">
                	<div id="myHistory">
                    <div class="page-body">
                        <!-- 내가 쓴 게시판 목록 -->
                        <div class="tit-page-2">
                            <h2>배송현황</h2>
                            <p class="dsc"><span class="fc-blue">${userVO.user_name}</span>님께서 주문하신 주문 내역입니다.</p>
                        </div>
                       
                        <div class="table-d2-list">
                            <table summary="번호, 게시판, 제목, 날짜, hit수">
                                <caption>배송 현황</caption>
                                <colgroup>
                                    <col width="125">
                                    <col width="75">
                                    <col width="115">
                                    <col width="*">
                                    <col width="105">
                                </colgroup>
                                <thead>
                                    <tr>
                                        <th scope="row"><div class="tb-center">DATE</div></th>
                                        <th scope="row"><div class="tb-center">배송번호</div></th>
                                        <th scope="row"><div class="tb-center">주문번호</div></th>
                                        <th scope="row"><div class="tb-center">상품명</div></th>
                                        <th scope="row"><div class="tb-center">배송상태</div></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="d" items="${deliveryState}">
										<tr>
											<td scope="col"><div class="tb-center"><fmt:formatDate value="${d.state_date}"
														pattern="yyyy-MM-dd HH:mm" /></div></td>
											<td scope="col"><div class="tb-center">${d.delivery_seq}</div></td>
											<td scope="col"><div class="tb-center"> ${d.order_seq} </div></td>
											<td scope="col"><div class="tb-center"> ${d.prod_title} </div></td>
											<td scope="col"><div class="tb-center">${d.delivery_state}</div></td>
										</tr>
									</c:forEach>
                                </tbody>
                            </table>
                        </div>
                        </div>
                		</div>
           			 </div><!-- #myHistory -->
				</div>
				<!-- .page-body -->
			</div>
			<!-- #mypage -->
		</div>
		<!-- #content -->
	</div>
	<!-- #contentWrap -->
</div>

<%@include file="/WEB-INF/views/include/footer.jsp"%>