package util;

import business.Account;

import java.util.List;

public class AccountUtil {

    public static Account getAccountFromList(int id, List<Account> userAccounts) {
        for (Account acc : userAccounts)
            if (acc.getAccountId() == id)
                return acc;
        return null;
    }
}
