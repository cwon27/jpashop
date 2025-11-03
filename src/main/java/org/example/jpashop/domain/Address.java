package org.example.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable // 내장 타입이라는 뜻
@Getter
public class Address {
	private String city;
	private String street;
	private String zipcode;

	// JPA 스펙상 만들어둠
	protected Address() {}

	public Address(String city, String street, String zipcode) {
		// 값 타입은 변경 불가능하게 설계해야 함
		// 생성자에서 값을 모두 초기화해서 변경 불가능한 클래스를 설계
		this.city = city;
		this.street = street;
		this.zipcode = zipcode;
	}
}
