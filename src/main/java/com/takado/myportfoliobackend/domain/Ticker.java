package com.takado.myportfoliobackend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

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

    @OneToMany(
            targetEntity = Asset.class,
            mappedBy = "ticker",
            cascade = CascadeType.MERGE,
            fetch = FetchType.LAZY)
    private List<Asset> assets;

    @OneToMany(
            targetEntity = Trade.class,
            mappedBy = "ticker",
            cascade = CascadeType.MERGE,
            fetch = FetchType.LAZY)
    private List<Trade> trades;

    @Override
    public String toString() {
        return "Ticker{" +
                "id=" + id +
                ", ticker='" + ticker + '\'' +
                ", coinId='" + coinId + '\'' +
                '}';
    }
}
