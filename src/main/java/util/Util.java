package util;

import data.AccountDB;
import data.TransactionDB;
import javafx.util.Pair;

import java.util.ArrayList;

public class Util {
    public static int indexOf(ArrayList<Pair<String, String>> list, String key) {
        int i = 0;
        for (Pair<String, String> p : list) {
            if (p.getKey().equals(key))
                return i;
            i++;
        }
        return -1;
    }

    public static void updateAccountsSold() {
        AccountDB.refundMoney();
        TransactionDB.deleteDeclinedTransactions();
    }
}
