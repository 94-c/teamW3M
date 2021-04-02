<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<title>상품후기</title>
<%@include file="/WEB-INF/views/include/header.jsp"%>
<link href="resources/admin_css/pagination.css" rel="stylesheet" type="text/css">
<link href="resources/admin_css/styles.css" rel="stylesheet" type="text/css">
<link href="resources/css/notification.css" rel="stylesheet" type="text/css">
<style type="text/css">
#submitbutton {
	margin: 10px;
	float: right;
	margin-left: 3px;
	width: 108px;
	height: 38px;
	font-size: 13px;
	line-height: 38px
}
</style>
<script type="text/javascript">
	//이전 버튼 이벤트
	function fn_prev(page, range, rangSize, searchKeyword) {
		var page = ((range - 2) * rangeSize) + 1;
		var range = range - 1;
		var url = "${pagContext.request.contextPath}/review.do";
		url = url + "?page=" + page;
		url = url + "&range=" + range;
		url = url + "&searchKeyword" + searchKeyword;
		location.href = url;
	}

	//페이지 번호 클릭
	function fn_pagination(page, range, rangSize, searchKeyword) {
		var url = "${pagContext.request.contextPath}/review.do";
		url = url + "?page=" + page;
		url = url + "&range=" + range;
		url = url + "&searchKeyword" + searchKeyword;
		location.href = url;
	}

	//다음 버튼 이벤트
	function fn_next(page, range, rangSize, searchKeyword) {
		var page = parseInt((range * rangeSize)) + 1;
		var range = parseInt(range) + 1;
		var url = "${pagContext.request.contextPath}/review.do";
		url = url + "?page=" + page;
		url = url + "&range=" + range;
		url = url + "&searchKeyword" + searchKeyword;
		location.href = url;
	}

	$(document).on('click', '#btnSearch', function(e) {
		e.preventDefault();
		var url = "${pageContext.request.contextPath}/review.do";
		url = url + "?searchType=" + $('#searchType').val();
		url = url + "&keyword=" + $('#keyword').val();
		location.href = url;
		console.log(url);
	});
</script>


<c:url var="getReviewList" value="review.do">
	<c:param name="page" value="${pagination.page }" />
	<c:param name="range" value="${pagination.range }" />
	<c:param name="rangeSize" value="${pagination.rangeSize}" />
	<c:param name="searchKeyword" value="${pagination.searchKeyword}" />
</c:url>
<div id="contentWrapper">
	<div id="contentWrap">
		<div id="content">
			<div id="bbsData">
				<div class="page-body">
						<div class="bbs-tit">
							<h3>후기게시판</h3>
						</div>
					<!-- 게시판 목록 -->
					<div class="bbs-table-list">
						<a href="review.do"> 
						<img src="resources/images/title/review_title.jpg"></a>
							<form role="form" method="post" action="review_write_view.do">
						<div class="bbs-tit">
							<h3>상품후기</h3>
							<td colspan="6" align="right">
								<button type="submit" class="CSSbuttonBlack" id="submitbutton">글쓰기</button>
							</td>
					</div>
					
					<!-- 게시판 목록 -->
					<div class="bbs-table-list">
							<table border="1" summary="No, content,Name,Date,Hits">
								<caption>후기게시판 게시글</caption>
								<colgroup>
                                    <col width="70">
                                    <col width="35">
                                    <col width="85">
                                    <col width="*">
                                    <col width="110">
                                    <col width="110">
                                    <col width="75">
                                </colgroup>
								<thead>
									<tr>
                                        <th scope="col"><div class="tb-center">NO.</div></th>
                                        <th scope="col"><div class="tb-center">&nbsp;</div></th>
                                        <th scope="col"><div class="tb-center">제품</div></th>
                                        <th scope="col"><div class="tb-center">후기</div></th>
                                        <th scope="col"><div class="tb-center">작성자</div></th>
                                        <th scope="col"><div class="tb-center">날짜</div></th>
                                        <th scope="col"><div class="tb-center">조회수</div></th>
                                    </tr>
								</thead>
									
								<tbody>
									<c:forEach var="reviewVO" items="${reviewList }">
										<tr>
                                        <td><div class="tb-center">${reviewVO.review_seq}</div></td>
                                        <td><div class="tb-left"><img src="resources/images/icons/neo_lock.gif"></div></td>
                                       	<td><div class="tb-left"><img src="${product.prod_title_image}" width="60" height="60" align="middle"></div></td> 
                                        <td><div class="tb-left"><a href='<c:url value='/reviewContent.do?review_seq=${reviewVO.review_seq}'/>'class="text-dark">[${product.prod_title}]${reviewVO.review_title}[]</a></div>
                                        </td>
                                        <td>
                                          <div class="tb-center"> <c:if test="${reviewVO.review_writer ne null && reviewVO.review_writer!=''}">${fn:substring(reviewVO.review_writer,0,fn:length(reviewVO.review_writer)-1)}*</c:if></div>
	                            		</td>
	                                        <td><div class="tb-center"><fmt:formatDate value="${reviewVO.review_date}" pattern="yyyy-MM-dd" /></div></td>
	                                        <td scope="col"><div class="tb-center">${reviewVO.review_cnt}</div></td>
                                    </tr>
									</c:forEach>
								</tbody>
							</table>

							<br>
							<!-- pagination -->
							<div id="paginationBox">
								<ul class="pagination">
									<c:if test="${pagination.prev}">
										<li class="page-item"><a class="page-link" href="#"
											onClick="fn_prev('${pagination.page}', '${pagination.range}', '${pagination.rangeSize}')">Previous</a></li>
									</c:if>

									<c:forEach begin="${pagination.startPage}"
										end="${pagination.endPage}" var="idx">
										<li
											class="page-item <c:out value="${pagination.page == idx ? 'active' : ''}"/> "><a
											class="page-link" href="#"
											onClick="fn_pagination('${idx}', '${pagination.range}', '${pagination.rangeSize}')">
												${idx} </a></li>
									</c:forEach>

									<c:if test="${pagination.next}">
										<li class="page-item"><a class="page-link" href="#"
											onClick="fn_next('${pagination.range}', '${pagination.range}', '${pagination.rangeSize}')">Next</a></li>
									</c:if>
								</ul>
							</div>
							<!-- search{s} -->
							<div class="searchText">
								<div class="w100" style="padding-right: 10px">
									<select class="form-control form-control-sm" name="searchType" id="searchType">
										<option value="review_title">제목</option>
										<option value="review_content">내용</option>
									</select>

								</div>
								<div class="w300" style="padding-right: 10px">
									<input type="text" class="form-control form-control-sm" name="keyword" id="keyword">
								</div>

								<div>
									<button class="btn btn-sm btn-primary" name="btnSearch" id="btnSearch">검색</button>
								</div>

							</div>
							<!-- search{e} -->
						</div>
					</form>
				</div>
				<!-- //게시판 목록 -->
			</div>
		</div>
	</div>
</div>
</div>
<%@include file="/WEB-INF/views/include/footer.jsp"%>