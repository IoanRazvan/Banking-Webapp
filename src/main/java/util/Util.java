package util;

import data.AccountDB;
import data.TransactionDB;
import java.util.AbstractMap;
import java.util.ArrayList;

public class Util {
    public static int indexOf(ArrayList<AbstractMap.SimpleEntry<String, String>> list, String key) {
        int i = 0;
        for (AbstractMap.SimpleEntry<String, String> p : list) {
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
