package org.example.jpashop.controller;

import lombok.RequiredArgsConstructor;
import org.example.jpashop.domain.Member;
import org.example.jpashop.domain.Order;
import org.example.jpashop.domain.OrderSearch;
import org.example.jpashop.domain.item.Item;
import org.example.jpashop.service.ItemService;
import org.example.jpashop.service.MemberService;
import org.example.jpashop.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;
	private final MemberService memberService;
	private final ItemService itemService;

	// 상품 주문 화면 조회
	@GetMapping(value = "/order")
	public String createForm(Model model) {
		List<Member> members = memberService.findMembers();
		List<Item> items = itemService.findItems();

		model.addAttribute("members", members);
		model.addAttribute("items", items);

		return "order/orderForm";
	}

	// 상품 주문
	@PostMapping(value = "/order")
	public String order(@RequestParam("memberId") Long memberId,
						@RequestParam("itemId") Long itemId,
						@RequestParam("count") int count) {
		// 핵심 비지니스 로직은 service에서! -> 영속성 상태를 가지는게 좋기 때문 + 트랜잭션 안에서 실행돼야 함
		orderService.order(memberId, itemId, count);
		
		return "redirect:/orders";
	}

	// 상품 주문 목록 조회
	@GetMapping(value = "/orders")
	public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch,
							Model model) {
		List<Order> orders = orderService.findOrders(orderSearch);

		model.addAttribute("orders", orders);

		return "order/orderList";
	}

	// 상품 주문 취소
	@PostMapping(value = "/orders/{orderId}/cancel")
	public String cancelOrder(@PathVariable("orderId") Long orderId) {
		orderService.cancelOrder(orderId);

		return "redirect:/orders";
	}
}
