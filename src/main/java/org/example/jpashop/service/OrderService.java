package org.example.jpashop.service;

import lombok.RequiredArgsConstructor;
import org.example.jpashop.domain.Delivery;
import org.example.jpashop.domain.Member;
import org.example.jpashop.domain.Order;
import org.example.jpashop.domain.OrderItem;
import org.example.jpashop.domain.item.Item;
import org.example.jpashop.repository.ItemRepository;
import org.example.jpashop.repository.MemberRepository;
import org.example.jpashop.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final MemberRepository memberRepository;
	private final ItemRepository itemRepository;

	// 주문
	@Transactional
	public Long order(Long memberId, Long itemId, int count) {
		// 엔티티 조회
		Member memeber = memberRepository.findOne(memberId);
		Item item = itemRepository.findOne(itemId);
		
		// 배송 정보 설정
		Delivery delivery = new Delivery();
		delivery.setAddress(memeber.getAddress());

		// 주문 상품 생성
		OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

		// 주문 생성
		Order order = Order.createOrder(memeber, delivery, orderItem);

		// 주문 저장
		// CASCADE 해줘서 order만 저장해줘도 됨
		orderRepository.save(order);

		return order.getId();
	}

	// 취소
	@Transactional
	public void cancelOrder(Long orderId) {
		// 주문 엔티티 조회
		Order order = orderRepository.findOne(orderId);
		//주문 취소
		order.cancel();
	}

	// 검색
	/*
	public List<Order> findOrders(OrderSearch orderSearch) {
	return orderRepository.findAll(orderSearch);
	}
	*/
}
