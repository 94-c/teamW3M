<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<title>상품후기 - ${reviewVO.review_title }</title>
<%@include file="/WEB-INF/views/include/header.jsp"%>
<link type="text/css" rel="stylesheet" href="resources/css/menu.css">
<link type="text/css" rel="stylesheet" href="resources/css/soo.css">
<link type="text/css" rel="stylesheet" href="resources/css/notification.css">
<script type="text/javascript">
function submitForm()
{
	var updateReply = document.comment;
	
	updateReply.submit();

	}
</script>
<div id="contentWrapper">
	<div id="contentWrap">
		<div id="content">
			<div id="bbsData">
				<div class="page-body">
					<div class="bbs-tit">
						<h3>후기게시판</h3>
					</div>
					<div class="bbs-table-view">
						<table summary="게시글 보기">
							<caption>게시글 보기</caption>
							<thead>
								<tr>
									<th><input type="hidden" name="review_seq"
										value="${reviewVO.review_seq }" />
										<div class="tb-center">
											<font size="2"> ${reviewVO.review_title } </font>
										</div></th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td class="line">
										<div class="cont-sub-des">
											<div align="right">
												<span><em>Date :</em> <fmt:formatDate
														value="${reviewVO.review_date }"
														pattern="yyyy-MM-dd HH:mm:ss" /></span>
											</div>
											<div align="right">
												<span><em>Name :</em> <c:if
														test="${reviewVO.review_writer ne null && reviewVO.review_writer!=''}">${fn:substring(reviewVO.review_writer,0,fn:length(reviewVO.review_writer)-1)}*</c:if>
												</span>
											</div>

											<div align="right">
												<span><em>Hits :</em> ${reviewVO.review_cnt }</span>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="data-bd-cont">
											<div align="left">
												<p style="font-size: 15px; Line-height: 200%; align: left; color: #5f5f5f;">${reviewVO.review_content }</p>
											<div class = "image_box">
												<p style="width: 100%">
												<c:if test="${reviewVO.review_image eq '파일없음'}"></c:if>
												<c:if test="${reviewVO.review_image ne '파일없음'}">
													<img src="${reviewVO.review_image }" />
												</c:if>
												</p>
											</div>	
											</div>
										</div>
									</td>
								</tr>
							</tbody>
						</table>

						<hr size="1" color="#E5E5E5">

						<!-- 이전글 다음글 -->
						<div class="bbs-table-list">
							<table summary="게시글 목록">
								<caption>게시글 목록</caption>
								<thead>
									<tr>
										<td>비밀번호 : <input type="password" name="content_pass"
											id="pass1" /></td>
										<td><input type="hidden" name="inq_pass" id="pass2"
											value="${reviewVO.review_pass}" /></td>
									</tr>

								</thead>
							</table>
							<!-- 댓글 -->
							<br>
						</div>
					</div>
					<div align="right">
						<input type="button" value="글 수정" class="CSSbuttonBlack" id="contentbutton" onclick="updateCheck();" />
						<input type="button" value="글 삭제" class="CSSbuttonBlack" id="contentbutton" onclick="deleteCheck();" />
						<input type="button" value="글 목록" class="CSSbuttonBlack" id="contentbutton" onclick="location.href='./review.do'" />
						<input type="button" value="글 쓰기" class="CSSbuttonBlack" id="contentbutton" onclick="location.href='./review_write_view.do'" />
					</div>
					<br> <br> <br> <br>
				</div>
				
				<div id="reply">
							<h5>댓글</h5><br>
							<table id="re_table">							
									<c:forEach items="${reviewReplyList}" var="reviewList">
											<input type="hidden" id="review_seq" name="review_seq" value="${reviewList.review_seq}" />
											<input type="hidden" id="review_re_seq" name="review_re_seq" value="${reviewList.review_re_seq}" />
											<label><i>${reviewList.review_re_writer}</i></label>
											<font id="commentDate"><fmt:formatDate value="${reviewList.review_re_date}" pattern="yyyy-MM-dd HH:mm" /></font>
											<c:set var="user_member_id" value="${userVO.user_id }" />
											<c:set var="user_review_id" value="${reviewList.review_re_writer }" />
										    <c:if test="${user_member_id eq user_review_id}">
											<a href="javascript:void(window.open('replyUpdate_view.do?review_re_seq=${reviewList.review_re_seq}', '_blank','width=430px, height=340px, status=no, menubar=no, toolbar=no, resizable=no'))"> 수정</a>
											<a href="deleteReviewReply.do?review_re_seq=${reviewList.review_re_seq}&review_seq=${reviewVO.review_seq}"> 삭제</a>
											<br>									
											</c:if>
											<pre>${reviewList.review_re_content}</pre><br>
									</c:forEach>
						  </table>
							</div>			 	
				
				
				<!-- .page-body -->
				<c:if test="${login_state eq 'login' }">
				<form id="comment_form" name="comment" action="insertReviewReply.do" method="post">
				<input type="hidden" name="review_seq" class="MS_input_txt input-style input-style2" value="${reviewVO.review_seq }">
					<fieldset>
						<legend>코멘트 쓰기</legend>
						<table summary="코멘트 쓰기" class="comment-box comment-write">
							<caption>코멘트 쓰기</caption>
							<colgroup>
								<col width="*">
							</colgroup>
							<tbody>
								<tr>
									<td class="com-wrt-box">
										<div>
											<div class="wrt">
												<label>아이디</label>
												<span>
												<input type="text" name="review_re_writer" class="MS_input_txt input-style input-style2" value="${userVO.user_id }" onclick="CheckLogin()"
													onkeypress="CheckLogin()" placeholder="아이디"></span>
											</div>
											<div class="wrt wrt_write">
												<textarea name="review_re_content" onchange="Checklength(this);" onkeyup="Checklength(this);" onclick="CheckLogin()" placeholder="내용"></textarea>
												<a href="javascript:submitForm()" class="CSSbuttonBlack">댓글쓰기</a>
											</div>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</fieldset>
				</form>
				</c:if>
			</div>
			<!-- #bbsData -->
		</div>
		<!-- #content -->
	</div>
	<!-- #contentWrap -->
</div>
<script type="text/javascript">
function deleteCheck()
{
	var p1 = document.getElementById('pass1').value;
	var p2 = document.getElementById('pass2').value;
	
	if(p1==""){
		alert("비밀번호를 입력해주세요.")
		document.getElementById('pass1').focus();
		return;
		}
	else if(p1!=p2){
		alert("비밀번호가 일치하지 않습니다.");
		return false;
	}else{
		location.href='./deleteReview.do?review_seq=${reviewVO.review_seq}'

	}
}

function updateCheck()
{
	var p1 = document.getElementById('pass1').value;
	var p2 = document.getElementById('pass2').value;
	
	if(p1==""){
		alert("비밀번호를 입력해주세요.")
		document.getElementById('pass1').focus();
		return;
		}
	else if(p1!=p2){
		alert("비밀번호가 일치하지 않습니다.");
		return false;
	}else{
		location.href='./review_update_view.do?review_seq=${reviewVO.review_seq}'

	}

}
</script>
<%@include file="/WEB-INF/views/include/footer.jsp"%>