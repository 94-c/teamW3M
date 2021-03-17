package com.spring.w3m.notice.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.w3m.notice.admin.service.NoticeService;
import com.spring.w3m.notice.admin.vo.NoticeVO;
import com.spring.w3m.paging.common.Pagination;

@Controller
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	
	@RequestMapping("/insertNotice.do")
	public String insertNotice(NoticeVO vo, Model model) {
		noticeService.insertNotice(vo);
		model.addAttribute("noticeList", noticeService.getNoticeList(vo));
		return "list/notice";
	}
	
	@RequestMapping("/updateNotice.do")
	public String updateNotice(NoticeVO vo, Model model) {
		noticeService.updateNotice(vo);
		model.addAttribute("noticeList",noticeService.getNoticeList(vo));
		return "list/notice";
	}
	
	@RequestMapping("/updateNotice_view.do")
	public String inquiryUpdateView(NoticeVO vo, Model model) {
		model.addAttribute("getNotice", noticeService.getNotice(vo));
		return "admin/page/updateNotice";
	}
	
	@RequestMapping("/deleteNotice.do")
	public String deleteNotice(NoticeVO vo, Model model) {
		noticeService.deleteNotice(vo);
		model.addAttribute("noticeList",noticeService.getNoticeList(vo));
		return "list/notice";
	}
	
	@RequestMapping("/getNotice.do")
	public String getNotice(NoticeVO vo, Model model) {
		model.addAttribute("notice", noticeService.getNotice(vo));
		return "list/noticeContent"; 
	}
	
	@RequestMapping("/getNoticeList.do")
	public String getBoardList(
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "1") int range,
			NoticeVO vo, Model model) {
		if(vo.getSearchCondition() == null) vo.setSearchCondition("nt_title");
		if(vo.getSearchKeyword() == null) vo.setSearchKeyword("");
		int cnt = noticeService.getNoticeListCnt();
		
		Pagination pagination = new Pagination();
		pagination.pageInfo(page, range, cnt);
		
		List<NoticeVO> pageList = noticeService.getPageList(pagination);
		
		model.addAttribute("pagination", pagination);
		model.addAttribute("list", pageList);
		model.addAttribute("cnt", cnt);
		
		System.out.println("검색 조건 : " + vo.getSearchCondition());
		System.out.println("검색 단어 : " + vo.getSearchKeyword());
		model.addAttribute("noticeList", noticeService.getNoticeList(vo));
		return "list/notice";
	}
}
