package repository;

import business.Account;

public interface AccountRepository {
    void save(Account acc);
    void update(Account acc);
}
