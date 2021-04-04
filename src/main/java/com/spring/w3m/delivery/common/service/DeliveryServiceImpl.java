package com.spring.w3m.delivery.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.w3m.delivery.common.dao.DeliveryDAO;
import com.spring.w3m.delivery.common.vo.DeliveryVO;
import com.spring.w3m.order.user.vo.OrderVO;
import com.spring.w3m.paging.common.Pagination;
import com.spring.w3m.paging.common.Search;

@Service
public class DeliveryServiceImpl implements DeliveryService {
	@Autowired
	private DeliveryDAO dao;
	
	@Override
	public List<DeliveryVO> getDeliveryList(DeliveryVO vo){		
		return dao.getDeliveryList(vo);
	}
	
	@Override //배송상태 변경이벤트 발생시 처리메서드
	public void updateDeliveryState(DeliveryVO vo) {
		if(vo.getDelivery_state() == null) {
			vo.setDelivery_state("배송전");			
		}else if(vo.getDelivery_state().equals("before")) {
			vo.setDelivery_state("배송전");
		}else if(vo.getDelivery_state().equals("ing")) {
			vo.setDelivery_state("배송중");
		}else if(vo.getDelivery_state().equals("after")) {
			vo.setDelivery_state("배송완료");
		}else if(vo.getDelivery_state().equals("commit")) {
			vo.setDelivery_state("구매확정");
		}else {
			vo.setDelivery_state("주문취소");
		}
		dao.updateDeliveryState(vo);
	}

	@Override
	public int getDeliveryListCnt(Search search) {
		return dao.getDeliveryListCnt(search);
	}

	@Override
	public List<DeliveryVO> getPageList(Search search) {
		return dao.getPageList(search);
	}

	@Override
	public int getSearchCnt(String searchKeyword) {
		return dao.getSearchCnt(searchKeyword);
	}

	@Override
	public List<DeliveryVO> getSearchPagingList(Pagination pagination) {
		return dao.getSearchPagingList(pagination);
	}

	@Override
	public void insertDelivery_state(DeliveryVO vo) {
		dao.insertDelivery_state(vo);
	}

	@Override
	public DeliveryVO getDelivery(OrderVO vo) {
		return dao.getDelivery(vo);
	}

	@Override
	public DeliveryVO getDeliveryCont(DeliveryVO vo) {
		return dao.getDeliverycont(vo);
	}
}
