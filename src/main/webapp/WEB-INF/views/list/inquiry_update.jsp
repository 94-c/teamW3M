<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<title>문의 게시판</title>
<%@include file="/WEB-INF/views/include/header.jsp"%>
<div id="contentWrapper">
	<div id="contentWrap">
		<div id="content">
			<div id="bbsData">
				<div class="page-body">
					<div class="bbs-tit">
						<h3>문의게시판</h3>
					</div>
					<div class="bbs-table-write">
						<form role="form" method="post" action="updateInquiry.do">
						<input name="inq_seq" type="hidden" value="${getInquiry.inq_seq}" />
							<fieldset>
								<legend>일반게시판 쓰기</legend>
								<table summary="">
									<caption>게시판 글쓰기</caption>
									<colgroup>
										<col width="135">
										<col width="395">
										<col width="155">
										<col width="*">
									</colgroup>
									<tbody>
										<tr>
											<th><div>이름</div></th>
											<td><div>
													<input id="bw_input_writer" type="text" name="inq_writer" class="MS_input_txt input_style1" value="${getInquiry.inq_writer}" readonly="readonly">
												</div></td>
										</tr>
										<tr>
											<th><div>이메일</div></th>
											<td colspan="3"><div>
													<input id="bw_input_email" type="text" class="MS_input_txt input_style1" name="inq_email" value="${getInquiry.inq_email}">
												</div></td>
										</tr>
										<tr>
											<th><div>제목</div></th>
											<td colspan="3"><div>
												<input id="bw_input_title" type="text" class="MS_input_txt input_style1" name="inq_title" value="${getInquiry.inq_title}">
												</div></td>
										</tr>
										<tr>
											<th><div>내용</div></th>
											<td colspan="3" class="text_content">
												<div>
												<pre><textarea id="MS_text_content" name="inq_content" wrap="soft" onfocus="clear_content()" class="MS_input_txt" style="font-family: 굴림체;">${getInquiry.inq_content}</textarea></pre>
													<input type="hidden" name="mobile_content_type" value="">
												</div>
											</td>
										</tr>
										<tr>
											<th><div>파일</div></th>
											<td colspan="3">
												<div>
													<input id="bw_input_file" type="text" class="MS_input_txt input_style2" name="file_name1" value="" onfocus="this.blur();upalert()"> 
													<a href="javascript:upload('file_name1');" class="btn_file">첨부</a>
												</div>
											</td>
										</tr>
									</tbody>
								</table>
							</fieldset>
							<!-- //게시판 글쓰기 -->

							<!-- 하단 버튼 -->
							<dl class="bbs-link bbs-link-btm">
								<dt></dt>
								<dd>
									<button type="submit" class="CSSbuttonBlack" id="contentbutton">수정하기</button>
								<!-- 	<a href= "#" class="CSSbuttonWhite">목록보기</a> -->
								</dd>
							</dl>
							<!-- //하단 버튼 -->
						</form>
					</div>
				</div>
				<!-- .page-body -->
			</div>
			<!-- #bbsData -->
		</div>
		<!-- #content -->
	</div>
	<!-- #contentWrap -->
</div>
<%@include file="/WEB-INF/views/include/footer.jsp"%>