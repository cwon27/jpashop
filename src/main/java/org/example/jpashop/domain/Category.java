package org.example.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.jpashop.domain.item.Item;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class Category {
	@Id
	@GeneratedValue
	@Column(name = "CATEGORY_ID")
	private Long id;

	private String name;

	@ManyToMany
	@JoinTable(name = "CATEGORY_ITEM",
			joinColumns = @JoinColumn(name = "CATEGORY_ID"),
			inverseJoinColumns = @JoinColumn(name = "ITEM_ID")) // 다대다 관계는 중간 테이블이 있어야 가능
	private List<Item> items = new ArrayList<>();

	// 부모 자식 관계 연관 관계 설정 가능
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "PARENT_ID")
	private Category parent;

	@OneToMany(mappedBy ="parent")
	private List<Category> child = new ArrayList<>();

	// 연관관계 메서드
	public void addChildCategory(Category child) {
		this.child.add(child);
		child.setParent(this);
	}
}
