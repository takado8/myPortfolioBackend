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
@Entity(name = "users")
public class User {
    @Id
    @NotNull
    @GeneratedValue
    @Column(unique = true)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String email;

    @Column
    private String nameHash;

    @Column
    private String displayedName;

    @OneToMany(
            targetEntity = Asset.class,
            mappedBy = "user",
            cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER)
    private List<Asset> assets;

    @OneToMany(
            targetEntity = Trade.class,
            mappedBy = "user",
            cascade = CascadeType.MERGE,
            fetch = FetchType.LAZY)
    private List<Trade> trades;
}
