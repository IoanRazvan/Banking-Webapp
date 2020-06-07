package business;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class TransactionPK implements Serializable {
    private int sourceAccount;
    private int targetAccount;
    private Date transactionTimestamp;

    public TransactionPK(){}

    public TransactionPK(int sourceAccount, int targetAccount, Date transactionTimestamp) {
        this.sourceAccount = sourceAccount;
        this.targetAccount = targetAccount;
        this.transactionTimestamp = transactionTimestamp;
    }

    public int getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(int sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public int getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(int targetAccount) {
        this.targetAccount = targetAccount;
    }

    public Date getTransactionTimestamp() {
        return transactionTimestamp;
    }

    public void setTransactionTimestamp(Date transactionTimestamp) {
        this.transactionTimestamp = transactionTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionPK that = (TransactionPK) o;
        return sourceAccount == that.sourceAccount &&
                targetAccount == that.targetAccount &&
                transactionTimestamp.equals(that.transactionTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceAccount, targetAccount, transactionTimestamp);
    }
}
