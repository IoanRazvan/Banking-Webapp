package business;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name="BANK_ACCOUNT")
@Data
public class Account implements Serializable {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer accountId;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @Temporal(TemporalType.DATE)
    @Column(name="creation_date")
    private Date creationDate;

    @Column(name="currency")
    private String currency;

    @Column(name="sold")
    private Float sold;

    private boolean enabled = true;

    public Account() {
        creationDate = Calendar.getInstance().getTime();
    }

    public Account(User owner, Date creationDate, String currency, Float sold) {
        this.owner = owner;
        this.creationDate = creationDate;
        this.currency = currency;
        this.sold = sold;
    }
}
