package business;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@IdClass(TransactionPK.class)
@Table(name="TRANSACTION")
@Data
public class Transaction implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name="source_account")
    private Account sourceAccount;

    @Id
    @ManyToOne
    @JoinColumn(name="target_account")
    private Account targetAccount;

    @Id
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="transaction_timestamp")
    private Date transactionTimestamp;

    @Column(name="amount")
    private Float amount;

    @Column(name="transaction_status")
    private String status;

    public Transaction() {}

    public Transaction(Account sourceAccount, Account targetAccount, Date transactionTimestamp, Float amount, String status) {
        this.amount = amount;
        this.sourceAccount = sourceAccount;
        this.targetAccount = targetAccount;
        this.transactionTimestamp = transactionTimestamp;
        this.status = status;
    }
}
