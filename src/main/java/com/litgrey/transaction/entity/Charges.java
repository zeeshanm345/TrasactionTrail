package com.litgrey.transaction.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "lg_pr_charges")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Charges {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "por_orgacode", length = 10, nullable = false)
    private String porOrgacode;

    @Column(name = "chargecode", length = 10, nullable = false)
    private String chargecode;

    @Column(name = "description", length = 200)
    private String description;
}
