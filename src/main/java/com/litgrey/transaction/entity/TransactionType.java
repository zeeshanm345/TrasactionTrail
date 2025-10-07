package com.litgrey.transaction.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "lg_tr_transactiontype")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionType {

    @EmbeddedId
    private TransactionTypeId id;

    @Column(length = 200)
    private String description;

    @Embeddable
    @Getter
    @Setter
    public static class TransactionTypeId implements java.io.Serializable {
        @Column(name = "por_orgacode", length = 10)
        private String porOrgacode;
        @Column(name = "trancode", length = 10)
        private String trancode;

        public TransactionTypeId() {}
        public TransactionTypeId(String porOrgacode, String trancode) {
            this.porOrgacode = porOrgacode;
            this.trancode = trancode;
        }
    }
}
