package business;

import lombok.Data;
import org.eclipse.persistence.annotations.Direction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

    private boolean enabled;

    public Account() {}

    public Account(User owner, Date creationDate, String currency, Float sold) {
        this.owner = owner;
        this.creationDate = creationDate;
        this.currency = currency;
        this.sold = sold;
    }
}
