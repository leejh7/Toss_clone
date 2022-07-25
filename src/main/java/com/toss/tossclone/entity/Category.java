package com.toss.tossclone.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "category_key")
    private Long id;

    @Column(name = "category_name", nullable = false)
    private String name;

    @Builder
    public Category(String name) {
        this.name = name;
    }
}
