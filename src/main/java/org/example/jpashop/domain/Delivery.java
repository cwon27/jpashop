package org.example.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class Delivery {
	@Id
	@GeneratedValue
	@Column(name = "DELIVERY_ID")
	private Long id;

	@OneToOne(mappedBy = "delivery", fetch = LAZY)
	private Order order;

	@Embedded
	private Address address;

	@Enumerated(EnumType.STRING) // enum 사용시 꼭 String으로!
	private DeliveryStatus status; // 배송 상태 [READY, COMP]
}
