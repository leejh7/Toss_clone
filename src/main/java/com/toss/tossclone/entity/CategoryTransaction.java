package com.toss.tossclone.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Category_Transaction")
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryTransaction extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "category_transaction_key")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_key")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_key")
    private Transaction transaction;

    @Builder
    public CategoryTransaction(Category category, Transaction transaction) {
        this.category = category;
        this.transaction = transaction;
    }
}
