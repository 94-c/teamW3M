package com.spring.w3m.delivery.common.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.w3m.delivery.common.vo.DeliveryVO;
import com.spring.w3m.order.user.vo.OrderVO;
import com.spring.w3m.paging.common.Pagination;
import com.spring.w3m.paging.common.Search;
import com.spring.w3m.product.admin.vo.OrderProductInfoVO;

@Repository
public class DeliveryDAO {
	@Autowired
	private SqlSessionTemplate template;

	// 페이징 및 검색
	public int getDeliveryListCnt(Search search) {
		return template.selectOne("delivery.getDeliveryListCnt");
	}

	public List<DeliveryVO> getPageList(Search search) {
		return template.selectList("delivery.getPageList", search);
	}

	public int getSearchCnt(String searchKeyword) {
		return template.selectOne("delivery.getSearchKeyword");
	}

	public List<DeliveryVO> getSearchPagingList(Pagination pagination) {
		return template.selectList("delivery.getSearchPagingList");
	}
	// end 페이징 및 검색

	public List<DeliveryVO> getDeliveryList(DeliveryVO vo) {
		return template.selectList("delivery.getDeliveryList", vo);
	}

	public void updateDeliveryState(DeliveryVO vo) {
		template.update("delivery.updateDeliveryState", vo);
	}

	public void insertDelivery_state(DeliveryVO vo) {
		template.insert("delivery.insertDelivery_state", vo);
	}

	public DeliveryVO getDelivery(OrderVO vo) {
		return template.selectOne("delivery.getDelivery", vo);
	}

	public DeliveryVO getDeliverycont(DeliveryVO vo) {
		return template.selectOne("delivery.getDeliverycont", vo);
	}

	public int order_state_change(int order_seq) {

		return template.update("delivery.order_state_change", order_seq);
	}

	public int Prod_state_change(int order_seq) {

		return template.update("delivery.Prod_state_change", order_seq);
	}

	public int pay_state_change(int order_seq) {

		return template.update("delivery.pay_state_change", order_seq);
	}

	public List<OrderProductInfoVO> getOrderInfo(int order_seq) {
		return template.selectList("delivery.getOrderInfo", order_seq);
	}

	public int addSalesRate(OrderProductInfoVO opiVO) {
		return template.update("delivery.addSalesRate", opiVO);
	}

}
