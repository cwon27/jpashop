package org.example.jpashop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.jpashop.domain.Address;
import org.example.jpashop.domain.Member;
import org.example.jpashop.service.MemberService;
import org.example.jpashop.web.MemberForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	// 회원 가입 화면 조회
	@GetMapping(value = "/members/new")
	public String createForm(Model model) {
		model.addAttribute("memberForm", new MemberForm());

		return "members/createMemberForm";
	}

	// 회원 가입
	@PostMapping(value = "/members/new")
	public String create(@Valid MemberForm form, BindingResult result) {
		if (result.hasErrors()) {
			return "members/createMemberForm";
		}
		Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
		Member member = new Member();

		member.setName(form.getName());
		member.setAddress(address);

		memberService.join(member);

		return "redirect:/";
	}

	// 회원 목록 조회
	@GetMapping(value = "/members")
	public String list(Model model) {
		List<Member> members = memberService.findMembers();
		model.addAttribute("members", members);

		return "members/memberList";
	}
}
