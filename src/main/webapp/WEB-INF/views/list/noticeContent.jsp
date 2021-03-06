<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link type="text/css" rel="stylesheet" href="resources/css/menu.css?t=201912021906">

<title>공지사항</title>

<%@include file="/WEB-INF/views/include/header.jsp"%>

<div id="contentWrapper">
	<div id="contentWrap">
		<div id="content">
			<div id="bbsData">
				<div class="page-body">
					<div class="bbs-tit">
						<h3>공지사항</h3>
					</div>
					<div class="bbs-table-view">
						<table summary="게시글 보기">
							<caption>게시글 보기</caption>
							<thead>
								<tr>
									<th><div class="tb-center">
											<strong><font size=2><font
													color="${notice.nt_color }">${notice.nt_title }</font></font></strong>
										</div></th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td class="line">
										<div class="cont-sub-des">
											<div align="right">
												<span><em>Date :</em>
												<fmt:formatDate value="${notice.nt_date}"
														pattern="yyyy-MM-dd HH:mm:ss" /></span>
											</div>
											<div align="right">
												<span><em>Name :</em> ${notice.nt_writer }</span> <span><em>Hits
														:</em> ${notice.nt_count }</span>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="data-bd-cont">
											<div align="left">
												<pre>
													<p style="font-size: 8px; Line-height: 200%; align: left; color: #5f5f5f;">${notice.nt_content }</p>
												</pre>
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
									<input type="button" class="CSSbuttonBlack" id="submitbutton"
										value="글 목록" onclick="location.href='./getUserNoticeList.do'" />
								</thead>
							</table>
						</div>
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