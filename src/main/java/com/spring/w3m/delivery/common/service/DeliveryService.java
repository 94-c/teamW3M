package com.spring.w3m.delivery.common.service;

import java.util.List;

import com.spring.w3m.delivery.common.vo.DeliveryVO;
import com.spring.w3m.order.user.vo.OrderVO;
import com.spring.w3m.paging.common.Pagination;
import com.spring.w3m.paging.common.Search;
import com.spring.w3m.product.admin.vo.OrderProductInfoVO;

public interface DeliveryService {

	List<DeliveryVO> getDeliveryList(DeliveryVO vo);
	
	void updateDeliveryState(DeliveryVO vo);
	
	int getDeliveryListCnt(Search search);
	
	List<DeliveryVO> getPageList(Search search);
	
	int getSearchCnt(String searchKeyword);
	
	List<DeliveryVO> getSearchPagingList(Pagination pagination);
	
	void insertDelivery_state(DeliveryVO vo);
	
	DeliveryVO getDelivery(OrderVO vo);
	
	DeliveryVO getDeliveryCont(DeliveryVO vo);
	int order_state_change(int order_seq);
	int Prod_state_change(int order_seq);
	int pay_state_change(int order_seq);
	
	void getOrderInfo(int order_seq, OrderProductInfoVO opiVO);
	void addSalesRate(OrderProductInfoVO opiVO);

}
