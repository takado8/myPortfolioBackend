package com.takado.myportfoliobackend.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Entity(name = "assets")
public class Asset {
    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "id", unique = true)
    private Long id;

    @NotNull
    @Column(name = "ticker")
    private String ticker;

    @Column(name = "amount")
    private String amount;

    @Column(name = "valueIn")
    private String valueIn;

    public Asset(String ticker, String amount, String valueIn) {
        this.ticker = ticker;
        this.amount = amount;
        this.valueIn = valueIn;
    }
}
