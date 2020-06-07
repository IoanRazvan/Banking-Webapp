package business;

import org.eclipse.persistence.annotations.Direction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="BANK_ACCOUNT")
public class Account implements Serializable {
    @Id
    @Column(name="account_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int accountId;

    @ManyToOne
    @JoinColumn(name ="owner_id")
    private User owner;

    @Temporal(TemporalType.DATE)
    @Column(name="creation_date")
    private Date creationDate;

    @Column(name="currency")
    private String currency;

    @Column(name="sold")
    private Float sold;

    @Column(name="main_account")
    private boolean mainAccount;

    public Account(){

    }

    public Account(User owner, Date creationDate, String currency, Float sold, boolean mainAccount) {
        this.owner = owner;
        this.creationDate = creationDate;
        this.currency = currency;
        this.sold = sold;
        this.mainAccount = mainAccount;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Float getSold() {
        return sold;
    }

    public int getAccountId() {
        return accountId;
    }

    public String getCurrency() {
        return currency;
    }

    public User getOwner() {
        return owner;
    }

    public boolean isMainAccount() {
        return mainAccount;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setMainAccount(boolean mainAccount) {
        this.mainAccount = mainAccount;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setSold(Float sold) {
        this.sold = sold;
    }
}
