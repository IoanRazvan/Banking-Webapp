package statistics;


public class TransactionFrame {
    String currency;
    TransactionData transactionData;

    public TransactionFrame(String currency, TransactionData transactionData) {
        this.currency = currency;
        this.transactionData = transactionData;
    }
}
