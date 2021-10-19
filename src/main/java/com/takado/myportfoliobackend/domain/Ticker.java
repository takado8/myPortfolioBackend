package com.takado.myportfoliobackend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tickers")
public class Ticker {
    @Id
    @NotNull
    @GeneratedValue
    @Column(unique = true)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String ticker;

    @NotNull
    @Column(unique = true)
    private String coinId;

}
