package business;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TransactionPK implements Serializable {
    private Integer sourceAccount;
    private Integer targetAccount;
    private Date transactionTimestamp;

    public TransactionPK() {}

    public TransactionPK(int sourceAccount, int targetAccount, Date transactionTimestamp) {
        this.sourceAccount = sourceAccount;
        this.targetAccount = targetAccount;
        this.transactionTimestamp = transactionTimestamp;
    }
}
