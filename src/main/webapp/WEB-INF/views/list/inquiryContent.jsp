<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/views/include/header.jsp"%>
<script type="text/javascript">
function deleteCheck(){
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
		location.href='./deleteInquiry.do?inq_seq=${inquiryVO.inq_seq}'
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
		location.href='./inquiry_update_view.do?inq_seq=${inquiryVO.inq_seq}'
	}
}
</script>

<div id="contentWrapper">
	<div id="contentWrap">
		<div id="content">
			<div id="bbsData">
				<div class="page-body">
					<div class="bbs-tit">
						<h3>문의사항</h3>
					</div>
					<div class="bbs-table-view">
						<table summary="게시글 보기">
							<caption>게시글 보기</caption>
							<thead>
								<tr>
									<th><div class="tb-center">
											<font size="2"> ${inquiryVO.inq_title } </font>
										</div></th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td class="line">
										<div class="cont-sub-des">
											<div>
												<span><em>Date :</em> <fmt:formatDate
														value="${inquiryVO.inq_date }"
														pattern="yyyy-MM-dd HH:mm:ss" /></span>
											</div>
											<div>
												<span><em>Name :</em> <c:if
														test="${inquiryVO.inq_writer ne null && inquiryVO.inq_writer!=''}">${fn:substring(inquiryVO.inq_writer,0,fn:length(inquiryVO.inq_writer)-1)}*</c:if>
												</span> <span><em>Hits :</em> ${inquiryVO.inq_cnt }</span>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="data-bd-cont">
											<pre>${inquiryVO.inq_content } <br></pre>
											<c:if test="${inquiryVO.inq_image eq '파일없음'}"></c:if>
											<c:if test="${inquiryVO.inq_image ne '파일없음'}">
												<img src="${inquiryVO.inq_image }" />
											</c:if>
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
											id="pass1" /> <input type="hidden" name="inq_pass"
											id="pass2" value="${inquiryVO.inq_pass}" />
										</td>
									</tr>
								</thead>
							</table>
							<!-- 댓글 -->
							<br>
						</div>
					</div>
					<input type="button" value="글 수정" class="CSSbuttonBlack"
						id="contentbutton" onclick="updateCheck();" /> <input
						type="button" value="글 삭제" class="CSSbuttonBlack"
						id="contentbutton" onclick="deleteCheck();" /> <input
						type="button" value="글 목록" class="CSSbuttonBlack"
						id="contentbutton" onclick="location.href='./inquiry.do'" />
					<div class="page-body">
						<div class="bbs-tit">
							<h3>댓글</h3>
							<br>
						</div>
						<div class="bbs-table-view"></div>
					</div>
					<hr size="">
					<div id="reply">
						<c:forEach items="${replyList}" var="replyList">
							<div
								style="height: auto; width: auto; border-bottom: 1px solid gray;">${replyList.re_writer}
								<font id="commentDate"><fmt:formatDate
										value="${inquiryVO.inq_date }" pattern="yyyy-MM-dd HH:mm:ss" /></font><br />
								<br>
								<pre>
									<font id="commentContent">${replyList.re_content}</font>
								</pre>
								<br />
							</div>

						</c:forEach>
					</div>
					<!-- .page-body -->
				</div>
				<!-- #bbsData -->
			</div>
			<!-- #content -->
		</div>
		<!-- #contentWrap -->
	</div>
</div>

<%@include file="/WEB-INF/views/include/footer.jsp"%>