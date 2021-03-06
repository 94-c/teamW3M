package com.spring.w3m.order.user.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.spring.w3m.delivery.common.service.DeliveryService;
import com.spring.w3m.delivery.common.vo.DeliveryVO;
import com.spring.w3m.join.user.service.UserService;
import com.spring.w3m.join.user.vo.UserVO;
import com.spring.w3m.order.user.service.OrderService;
import com.spring.w3m.order.user.vo.LastAddress;
import com.spring.w3m.order.user.vo.OrderVO;
import com.spring.w3m.order.user.vo.PayVO;
import com.spring.w3m.point.user.vo.PointVO;
import com.spring.w3m.product.admin.vo.ProductVO;

@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private DeliveryService deliveryservice;
	@Autowired
	private UserService userService;

	@RequestMapping("/send_order_go.do")
	public String OrderList(@SessionAttribute("userVO") UserVO vo, HttpSession session) { // 주문
		PayVO payVO = new PayVO();
		int pay_total_price = 0;
		int pay_total_point = 0;
		int pay_Shipping_cost = 0;
		List<OrderVO> OrderVO = orderService.order_List(vo.getUser_id());
		for (OrderVO orderList : OrderVO) {
			int point = orderList.getProd_point();
			int price = orderList.getProd_price_sale();
			int amount = orderList.getProd_amount();
			orderList.setProd_total_point(point * amount);
			orderList.setProd_total_price(price * amount);
			pay_total_price = pay_total_price + orderList.getProd_total_price();
			pay_total_point = pay_total_point + orderList.getProd_total_point();

		}

		double level = 0;
		// Bronze 1%, silver 3%, gold 5%, Platinum 7%, dia 9%
		switch (vo.getUser_level()) {
		case "Bronze":
			level = pay_total_price * 0.01;
			break;
		case "Silver":
			level = pay_total_price * 0.03;
			break;
		case "Gold":
			level = pay_total_price * 0.05;
			break;
		case "Platinum":
			level = pay_total_price * 0.07;
			break;
		case "Dia":
			level = pay_total_price * 0.09;
			break;

		}
		int level2 = (int) (level / 100);
		level2 = level2 * 100;
		System.out.println(level2);
		if (pay_total_price >= 20000) {
			pay_Shipping_cost = 0;

		} else {
			pay_Shipping_cost = 2500;
		}
		int pay_total_money = pay_Shipping_cost + pay_total_price - level2;
		payVO.setPay_Shipping_cost(pay_Shipping_cost);
		payVO.setPay_total_price(pay_total_price);
		payVO.setPay_total_point(pay_total_point);
		payVO.setPay_total_money(pay_total_money);
		payVO.setPay_Membership(level2);
		System.out.println(OrderVO.toString());
		session.setAttribute("OrderVO", OrderVO);
		session.setAttribute("payVO", payVO);

		return "order/orderList";
	}

	@RequestMapping("/send_order.do")
	@ResponseBody
	public int order_Pord_List(@RequestBody ProductVO productvo, @SessionAttribute("userVO") UserVO vo) { // 제품들 주문 리스트에
																											// 등록

		orderService.order_drop_List(vo.getUser_id());

		OrderVO orderVO = new OrderVO();
		orderVO.setUser_id(vo.getUser_id());
		orderVO.setProd_code(productvo.getProd_code());
		orderVO.setProd_amount(productvo.getProd_amount());

		int aa = orderService.order_inser_prod(orderVO);

		return aa;
	}

	@RequestMapping("/request_pay.do") // Bronze 1%, silver 3%, gold 5%, Platinum 7%, dia 9%
	@ResponseBody
	public void order_request_pay(@RequestBody PayVO payVO, @SessionAttribute("userVO") UserVO vo, HttpSession session,
			@SessionAttribute("OrderVO") List<OrderVO> ordervo) { // 제품들 주문 리스트에 등록

		// 제품 결제시 수량 -
		for (OrderVO Orvo : ordervo) {
			orderService.prod_decrease(Orvo);
		}

		orderService.insert_order_list(vo.getUser_id());

		int orVO = orderService.orderNum(vo.getUser_id());

		payVO.setOrder_seq(orVO);

		orderService.insert_pay(payVO);
		session.setAttribute("PayVO", payVO);
	}

	@RequestMapping("order_Success.do")
	public String order_Success(@SessionAttribute("userVO") UserVO vo, HttpSession session, PayVO payvo,
			OrderVO ordervo) {
		String receiver_name_1 = ordervo.getReceiver_name();
		String receiver_phone1_1 = ordervo.getReceiver_phone1();
		String receiver_phone2_1 = ordervo.getReceiver_phone2();
		String receiver_zipcode_1 = ordervo.getReceiver_zipcode();
		String receiver_address1_1 = ordervo.getReceiver_address1();
		String receiver_address2_1 = ordervo.getReceiver_address2();

		int orVO = orderService.orderNum(vo.getUser_id());

		ordervo.setOrder_seq(orVO);
		ordervo.setUser_id(vo.getUser_id());
		String aaaaa = ordervo.getProd_title();
		String title[] = aaaaa.split(",");
		int bbb = title.length - 1;
		String total_title;
		if (bbb == 0) {
			total_title = title[0];
		} else {
			total_title = title[0] + " 외 " + String.valueOf(bbb) + "건";
		}

		ordervo.setProd_title(total_title);

		orderService.insert_delivery(ordervo);
		DeliveryVO vvs = deliveryservice.getDelivery(ordervo);

		vvs.setOrder_seq(ordervo.getOrder_seq());
		deliveryservice.insertDelivery_state(vvs);

		// order_prod 테이블 - 주문상태를 결제완료 업데이트 + 주문번호 입력
		orderService.update_order_prod(ordervo);
		// 장바구니에서 결제완료 시 장바구니 비우기
		String location = ordervo.getLocation_before();
		String location_before[] = location.split(",");
		if (location_before[0].equals("장바구니")) {
			orderService.delete_cart(vo.getUser_id());
		}
		int use_point = payvo.getPay_use_point();
		if (use_point != 0) {

			// 사용한 적립금 적립금테이블에 누적
			PointVO pointvo_use = new PointVO();
			pointvo_use.setUser_id(vo.getUser_id());
			use_point = payvo.getPay_use_point() * -1;
			pointvo_use.setAdd_point(use_point);
			pointvo_use.setOrder_seq(ordervo.getOrder_seq());
			pointvo_use.setPoint_content(total_title);
			pointvo_use.setOrder_state("사용가능");// 결제 취소시 상태를 사용 불가로 바꿀꺼임
			orderService.insert_Use_point(pointvo_use);
		}
		// 구매할때 지급된 추가 적립급 누적 단,결제 취소시 상태를 사용 불가로 바꿈
		PointVO pointvo_add = new PointVO();
		pointvo_add.setUser_id(vo.getUser_id());
		pointvo_add.setAdd_point(payvo.getPay_total_point());
		pointvo_add.setOrder_seq(ordervo.getOrder_seq());
		pointvo_add.setPoint_content(total_title);
		pointvo_add.setOrder_state("사용불가");// 구매확정시 상태를 사용 가능로 바꿀꺼임
		orderService.insert_Use_point(pointvo_add);

		orderService.update_user_point(vo.getUser_id());// 사용한 적립금 업데이트!

		// 결제완료 시 최근 배송지에 추가
		LastAddress ad = new LastAddress();

		ad.setReceiver_name(receiver_name_1);
		ad.setUser_id(vo.getUser_id());
		ad.setReceiver_zipcode(receiver_zipcode_1);
		ad.setReceiver_address1(receiver_address1_1);
		ad.setReceiver_address2(receiver_address2_1);
		ad.setReceiver_phone1(receiver_phone1_1);
		ad.setReceiver_phone2(receiver_phone2_1);

		orderService.insert_Last_Address(ad);

		// order_list 테이블 - 주문생태를 배송전 업데이트
		orderService.update_order_list_status(vo.getUser_id());
		UserVO user = userService.getUser(vo);
		session.setAttribute("userVO", user);
		session.setAttribute("OrderVO1", ordervo);

		return "order/order_success";
	}

	@RequestMapping("last_address.do")
	public String last_address(@SessionAttribute("userVO") UserVO vo, HttpSession session) {
		List<LastAddress> la = orderService.get_Last_Address(vo.getUser_id());

		session.setAttribute("LastAddress", la);
		return "order/last_address";
	}

	@RequestMapping("/check_point.do")
	@ResponseBody
	public int order_request_pay(@SessionAttribute("userVO") UserVO vo) { // 제품들 주문 리스트에 등록
		int point = orderService.Check_Point(vo.getUser_id());

		return point;
	}

	@RequestMapping("/delete_last_address.do")
	@ResponseBody
	public String updateReviewReply(@RequestBody LastAddress vo, Model model) {
		orderService.delete_last_address(vo);
		return "1";
	}
}
