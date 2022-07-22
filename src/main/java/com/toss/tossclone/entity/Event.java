package com.toss.tossclone.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event {
    @Id @GeneratedValue
    @Column(name = "event_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @Column(name = "event_detail")
    private String eventDetail;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Builder
    public Event(Bank bank, String eventDetail, LocalDateTime endDate) {
        this.bank = bank;
        this.eventDetail = eventDetail;
        this.endDate = endDate;
    }
}
