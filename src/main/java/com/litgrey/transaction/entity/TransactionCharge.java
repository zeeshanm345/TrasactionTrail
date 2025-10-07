package com.litgrey.transaction.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "lg_gp_transactioncharge")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionCharge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chrgcode", length = 10, nullable = false)
    private String chrgcode;

    @Column(name = "por_orgacode", length = 10, nullable = false)
    private String porOrgacode;

    @Column(name = "trancode", length = 10, nullable = false)
    private String trancode;

    @Column(name = "glcode", length = 50)
    private String glcode;

    @Column(name = "chargedesc", length = 200, nullable = false)
    private String chargedesc;
}
