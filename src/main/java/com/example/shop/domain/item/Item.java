package com.example.shop.domain.item;

import com.example.shop.domain.Category;
import com.example.shop.exception.NotEnoughStockException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    /* 재고 증가 */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /* 재고 감소 */
    public void removeStock(int quantity) {
        int remain = this.stockQuantity - quantity;
        if(remain < 0)
            throw new NotEnoughStockException("need more stock");

        this.stockQuantity = remain;
    }

}
