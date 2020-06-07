package statistics;


import java.util.List;

public class TransactionData{
    List<ExchangedMoney> sentMoneyList;
    List<ExchangedMoney> recievedMoneyList;

    public TransactionData(List<ExchangedMoney> sentMoneyList, List<ExchangedMoney> recievedMoneyList) {
        this.sentMoneyList = sentMoneyList;
        this.recievedMoneyList = recievedMoneyList;
    }
}
