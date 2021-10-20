package com.takado.myportfoliobackend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "assets")
public class Asset {
    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "id", unique = true)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "ticker_id")
    private Ticker ticker;

    @Column(name = "amount")
    private String amount;

    @Column(name = "valueIn")
    private String valueIn;

    public Asset(Ticker ticker, String amount, String valueIn) {
        this.ticker = ticker;
        this.amount = amount;
        this.valueIn = valueIn;
    }
}
