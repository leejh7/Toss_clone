package com.toss.tossclone.entity;

import com.toss.tossclone.entity.constant.EventStatus;
import lombok.*;
import net.bytebuddy.asm.Advice;

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

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus;

    @Builder
    public Event(Bank bank, String eventDetail, LocalDateTime startDate, LocalDateTime endDate, EventStatus eventStatus) {
        this.bank = bank;
        this.eventDetail = eventDetail;
        this.startDate = startDate;
        this.endDate = endDate;
        this.eventStatus = eventStatus;
    }

    //==비지니스 로직==//
    public void cancel() {
        // TODO: 어떻게 해야할지 잘 모르겠음

        if(this.eventStatus == EventStatus.COMP) {
            throw new IllegalStateException("이미 종료된 행사는 취소가 불가능합니다.");
        }
        this.eventStatus = EventStatus.COMP;
    }
}
