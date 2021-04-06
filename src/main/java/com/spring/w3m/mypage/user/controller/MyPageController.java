package com.spring.w3m.mypage.user.controller;

import java.io.IOException;
import java.util.List;

import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.spring.w3m.delivery.common.service.DeliveryService;
import com.spring.w3m.delivery.common.vo.DeliveryVO;
import com.spring.w3m.inquiry.user.vo.InquiryVO;
import com.spring.w3m.join.user.vo.UserVO;
import com.spring.w3m.mypage.user.service.MyPageService;
import com.spring.w3m.mypage.user.vo.MyPageVO;
import com.spring.w3m.order.user.vo.OrderVO;
import com.spring.w3m.order.user.vo.PayVO;
import com.spring.w3m.paging.common.Pagination;
import com.spring.w3m.paging.common.Search;
import com.spring.w3m.point.user.service.PointService;
import com.spring.w3m.point.user.vo.PointVO;
import com.spring.w3m.product.admin.vo.OrderProductInfoVO;
import com.spring.w3m.review.user.vo.ReviewVO;

@Controller
public class MyPageController {
	@Autowired
	private MyPageService myPageService;
	@Autowired
	private PointService pointService;
	@Autowired
	private DeliveryService deliveryService;

	@RequestMapping("/mypage.do")
	public String myPage(MyPageVO vo, Model model, @SessionAttribute("userVO") UserVO uvo) { // 마이페이지 진입
		System.out.println("마이페이지 진입");
		model.addAttribute("myRecent", myPageService.recentList(vo));
		List<OrderVO> myOrderList = myPageService.myOrderList(uvo.getUser_id());
		model.addAttribute("myOrderList", myOrderList);
		return "mypage/myPage";
	}

	@RequestMapping("/myinquryList.do")
	public String myWriteList(Model model, UserVO vo1, InquiryVO vo,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "1") int range,
			@RequestParam(required = false, defaultValue = "title") String searchType,
			@RequestParam(required = false) String keyword) throws PSQLException, IOException {
		System.out.println("문의사항 리스트");

		Search search = new Search();
		search.setSearchType(searchType);
		search.setKeyword(keyword);
		search.setUser_email(vo1.getUser_email());
		search.setProd_code(vo.getProd_code());
		int cnt = myPageService.myPageListCnt(search);

		search.pageInfo(page, range, cnt);

		// Pagination
		Pagination pagination = new Pagination();
		pagination.pageInfo(page, range, cnt);

		List<InquiryVO> pageList = myPageService.myPageList(search);

		model.addAttribute("userVO", myPageService.myUser(vo1));
		model.addAttribute("pagination", search);
		model.addAttribute("inquiryList", pageList);
		model.addAttribute("cnt", cnt);
		return "mypage/myWriteList";
	}

	@RequestMapping("/myReviewList.do")
	public String myReviewList(Model model, UserVO vo1, ReviewVO vo,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "1") int range,
			@RequestParam(required = false, defaultValue = "title") String searchType,
			@RequestParam(required = false) String keyword) throws PSQLException, IOException {
		System.out.println("후기 리스트");

		Search search = new Search();
		search.setSearchType(searchType);
		search.setKeyword(keyword);
		search.setUser_email(vo1.getUser_email());
		search.setProd_code(vo.getProd_code());
		int cnt = myPageService.myReviewListCnt(search);

		search.pageInfo(page, range, cnt);

		// Pagination
		Pagination pagination = new Pagination();
		pagination.pageInfo(page, range, cnt);

		List<ReviewVO> pageList = myPageService.myReviewList(search);

		model.addAttribute("userVO", myPageService.myUser(vo1));
		model.addAttribute("pagination", search);
		model.addAttribute("ReviewList", pageList);
		model.addAttribute("cnt", cnt);
		return "mypage/myReviewList";
	}

	@RequestMapping("/getPointList.do")
	public String getPointList(Model model, UserVO vo1, PointVO vo,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "1") int range,
			@RequestParam(required = false, defaultValue = "title") String searchType,
			@RequestParam(required = false) String keyword) throws PSQLException, IOException {
		System.out.println("후기 리스트");

		Search search = new Search();
		search.setSearchType(searchType);
		search.setKeyword(keyword);
		search.setUser_id(vo1.getUser_id());
		int cnt = pointService.getPointListCnt(search);

		search.pageInfo(page, range, cnt);

		// Pagination
		Pagination pagination = new Pagination();
		pagination.pageInfo(page, range, cnt);

		List<PointVO> pageList = pointService.getPointPageList(search);

		model.addAttribute("userVO", myPageService.myUser(vo1));
		model.addAttribute("pagination", search);
		model.addAttribute("pointList", pageList);
		model.addAttribute("cnt", cnt);
		return "mypage/myPoint";
	}

	// 주문내역
	@RequestMapping("myOrderList.do")
	public String getOrderList(Model model, @SessionAttribute("userVO") UserVO vo) {

		List<OrderVO> myOrderList = myPageService.myOrderList(vo.getUser_id());
		model.addAttribute("myOrderList", myOrderList);
		return "mypage/myOrder";
	}

	// 배송상세
	@RequestMapping("deliveryState.do")
	public String deliveryState(Model model, @SessionAttribute("userVO") UserVO vo, DeliveryVO vo1) {
		System.out.println(vo1.getOrder_seq());
		List<DeliveryVO> deliveryState = myPageService.deliveryState(vo1.getOrder_seq());
		model.addAttribute("deliveryState", deliveryState);
		return "mypage/myDeliveryDetail";
	}
	
	// 주문상세
	@RequestMapping("myOrderDetail.do")
	public String getOrderDetail(Model model, @SessionAttribute("userVO") UserVO vo, OrderVO oVO, DeliveryVO dVO, PayVO pVO) {
		oVO.setUser_id(vo.getUser_id());
		OrderVO receiverInfo = myPageService.getReceiverInfo(oVO);
		
		dVO.setUser_id(vo.getUser_id());
		DeliveryVO deliveryInfo = myPageService.getDeliveryInfo(dVO);
		String lotteRandomNum = myPageService.makeRandomNumber();
		
		String userId = vo.getUser_id();
		List<OrderProductInfoVO> opiList = myPageService.getOrderProductInfo(userId);
		
		PayVO payInfo = myPageService.getPayInfo(pVO);
		
		String salePercent = myPageService.getUserLevel(userId);
		
		model.addAttribute("receiverInfo", receiverInfo); //주문자정보
		model.addAttribute("deliveryInfo", deliveryInfo); //배송지정보
		model.addAttribute("orderProductInfo", opiList); //주문상품정보
		model.addAttribute("payInfo", payInfo); //결제정보
		model.addAttribute("lotteRandomNum", lotteRandomNum); //송장번호(12자리난수)
		model.addAttribute("salePercent", salePercent);
		return "mypage/myOrderDetail";
	}
	
	@RequestMapping("orderCancel.do")
	public String cancelOrder(Model model, DeliveryVO dVO, PointVO pVO, PayVO payVO, OrderVO oVO) {
		myPageService.deletePoint(pVO); // 포인트 회수
		myPageService.changePayState(payVO); // 결제정보 변경
		myPageService.changeOrderState(oVO); // 주문상태 변경
		myPageService.changeOrderProductState(oVO); // 주문상품상태 변경
		myPageService.changeDeliveryState(dVO); // 배송상태 변경
		dVO.setDelivery_state("주문취소");
		deliveryService.insertDelivery_state(deliveryService.getDeliveryCont(dVO)); // 배송상세내역에 넣어줌
		return "redirect:myOrderList.do";
	}

}
