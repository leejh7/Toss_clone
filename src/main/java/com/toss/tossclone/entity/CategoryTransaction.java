package com.toss.tossclone.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Category_Transaction")
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryTransaction {
    @Id @GeneratedValue
    @Column(name = "category_transaction_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    @Builder
    public CategoryTransaction(Category category, Transaction transaction) {
        this.category = category;
        this.transaction = transaction;
    }
}
