package org.example.jpashop.domain.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.jpashop.domain.Category;
import org.example.jpashop.exception.NotEnoughStockException;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 한 테이블에 다 넣음
@DiscriminatorColumn(name = "dtype")
@Getter
@Setter
public abstract class Item {
	@Id
	@GeneratedValue
	@Column(name = "ITEM_ID")
	private Long id;

	private String name;

	private int price;

	private int stockQuantity;

	@ManyToMany(mappedBy = "items")
	private List<Category> categories = new ArrayList<>();

	// 비즈니스 로직
	// 재고 수량(stock) 증가
	public void addStock(int quantity) {
		this.stockQuantity += quantity;
	}

	// 재고 수량(stock) 감소
	public void removeStock(int quantity) {
		int restStock = this.stockQuantity - quantity;
		if(restStock < 0) {
			throw new NotEnoughStockException("need more stock");
		}
		this.stockQuantity = restStock;
	}
}
