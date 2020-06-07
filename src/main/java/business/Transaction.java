package business;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@IdClass(TransactionPK.class)
@Table(name="TRANSACTION")
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

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public Account getTargetAccount() {
        return targetAccount;
    }

    public Date getTransactionTimestamp() {
        return transactionTimestamp;
    }

    public Float getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public void setSourceAccount(Account sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTargetAccount(Account targetAccount) {
        this.targetAccount = targetAccount;
    }

    public void setTransactionTimestamp(Date transactionTimestamp) {
        this.transactionTimestamp = transactionTimestamp;
    }
}
