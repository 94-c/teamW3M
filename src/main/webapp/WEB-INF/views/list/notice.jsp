<%@page import="java.util.List"%>
<%@page import="com.spring.w3m.notice.admin.dao.NoticeDAO"%>
<%@page import="com.spring.w3m.notice.admin.vo.NoticeVO"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/WEB-INF/views/include/header.jsp"%>

<link href="resources/admin_css/pagination.css" rel="stylesheet" type="text/css">
<link href="resources/admin_css/styles.css" rel="stylesheet"
	type="text/css">
<style type="text/css">

	.page-item{list-style-type: none; display:inline; margin-left:20px;}
</style>
<script type="text/javascript">
        	//이전 버튼 이벤트
        	function fn_prev(page, range, rangSize, searchKeyword){
        		var page = ((range - 2) * rangeSize) + 1;
        		var range = range - 1;
        		var url = "${pagContext.request.contextPath}/getNoticeList.do";
        		url = url + "?page=" + page;
        		url = url + "&range=" + range;
        		url = url + "&searchKeyword" + searchKeyword;
        		location.href = url;
        	}
        	
        
        	//페이지 번호 클릭
        	function fn_pagination(page, range, rangSize, searchKeyword){
        		var url = "${pagContext.request.contextPath}/getNoticeList.do";
        		url = url + "?page=" + page;
        		url = url + "&range=" + range;
        		url = url + "&searchKeyword" + searchKeyword;
        		location.href = url;
        	}
        	
        	//다음 버튼 이벤트
        	function fn_next(page, range, rangSize, searchKeyword){
        		var page = parseInt((range  * rangeSize)) + 1;
        		var range = parseInt(range) + 1;
        		var url = "${pagContext.request.contextPath}/getNoticeList.do";
        		url = url + "?page=" + page;
        		url = url + "&range=" + range;
        		url = url + "&searchKeyword" + searchKeyword;
        		location.href = url;
        	}
        	
        	$(document).on('click', '#btnSearch', function(e){
        		e.preventDefault();
        		var url = "${pageContext.request.contextPath}/getNoticeList.do";
        		url = url + "?searchType=" + $('#searchType').val();
        		url = url + "&keyword=" + $('#keyword').val();
        		location.href = url;
        		console.log(url);
        	});
        </script>

<link rel="shortcut icon" href="resources/images/icons/favicon.ico" type="image/x-icon">
<title>공지사항</title>

<div id="contentWrapper">
	<div id="contentWrap">
		<link href="resources/css/notification.css" rel="stylesheet" type="text/css">
		<div id="content">
			<div id="bbsData">
				<div class="page-body">
					<div class="bbs-tit">
						<h3>공지사항</h3>
						<div class="bbs-sch">
							<form action="getNoticeList.do" name="form1">
								<input type="hidden" name="searchCondition" value="">  
								<input type="hidden" name="seachKeyword" value=""> 
								
								<!-- .검색 폼시작 -->
								<fieldset>
									<legend>게시판 검색 폼</legend>
									<label> 
										<input type="radio" name="searchCondition" value="nt_title" onclick="checkOnlyOne(this)" 
										checked="checked" class="MS_input_checkbox"> 제목
									</label> 
									<label> 
										<input type="radio" name="searchCondition" value="nt_content" onclick="checkOnlyOne(this)" 
										class="MS_input_checkbox"> 내용
									</label> 
									<span class="key-wrap"> 
										<input type="text" name="searchKeyword" value="" class="MS_input_txt"> 
										<a href="javascript:document.form1.submit();"> 
											<img src="//image.makeshop.co.kr/makeshop/d3/basic_simple/bbs/btn_bbs_sch.gif"
											alt="검색" title="검색">
										</a>
									</span>
								</fieldset>
							</form>
							<!-- .검색 폼 끝 -->
						</div>
						<!-- .bbs-sch -->
					</div>

					<!-- 게시판 목록 -->
					<div class="bbs-table-list">
						<table border="1" summary="No, content,Name,Date,Hits">
							<caption>일반게시판 게시글</caption>
							<colgroup>
								<col width="70">
								<col width="35">
								<col width="*">
								<col width="110">
								<col width="110">
								<col width="75">
							</colgroup>
							<thead>
								<tr>
									<th scope="col"><div class="tb-center">NO.</div></th>
									<th scope="col"><div class="tb-center">&nbsp;</div></th>
									<th scope="col"><div class="tb-center">공지</div></th>
									<th scope="col"><div class="tb-center">작성자</div></th>
									<th scope="col"><div class="tb-center">날짜</div></th>
									<th scope="col"><div class="tb-center">조회수</div></th>
								</tr>
			
							</thead>
							<tbody>
								<c:forEach var="notice" items="${getNoticeList}">
									<tr>
										<td scope="col"><div class="tb-center">${notice.nt_seq }</td>
										<td scope="col"><div class="tb-center">&nbsp;</td>
										<td scope="col"><div class="tb-center"><a href='<c:url value='/getNotice.do?nt_seq=${notice.nt_seq}'/>' class="text-dark">${notice.nt_title }</a></div></td>
										<td scope="col"><div class="tb-center"><img src="resources/images/icons/neo_admin.gif"><!--${notice.nt_writer }--></td>
										<td scope="col"><div class="tb-center"><fmt:formatDate value="${notice.nt_date}" pattern="yyyy-MM-dd"/></td>
										<td scope="col"><div class="tb-center">${notice.nt_count }</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
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
										<select class="form-control form-control-sm" name="searchType"
											id="searchType">
											<option value="nt_title">제목</option>
											<option value="nt_content">내용</option>
										</select>

									</div>
									<div class="w300" style="padding-right: 10px">
										<input type="text" class="form-control form-control-sm"
											name="keyword" id="keyword">
									</div>

									<div>
										<button class="btn btn-sm btn-primary" name="btnSearch"
											id="btnSearch">검색</button>
									</div>
								</div>
					</div>
					<!-- //게시판 목록 -->
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function checkOnlyOne(element) {
	  
	  const checkboxes 
	      = document.getElementsByName("SearchCondition");
	  
	  checkboxes.forEach((cb){
	    cb.checked = false;
	  })
	  
	  element.checked = true;
	}
</script>



<%@include file="/WEB-INF/views/include/footer.jsp"%>