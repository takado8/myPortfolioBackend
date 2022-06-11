package com.takado.myportfoliobackend.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "trades")
public class Trade {
    public enum Type {
        ASK,
        BID
    }

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "amount")
    private String amount;

    @Column(name = "valueIn")
    private String valueIn;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "dateTime")
    private LocalDateTime dateTime;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "ticker_id")
    private Ticker ticker;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return "Trade{" +
                "id=" + id +
                ", amount='" + amount + '\'' +
                ", valueIn='" + valueIn + '\'' +
                ", type=" + type +
                ", dateTime=" + dateTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trade trade = (Trade) o;

        return id.equals(trade.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
