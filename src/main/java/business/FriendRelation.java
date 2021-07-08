package business;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "FRIEND_RELATION")
@IdClass(FriendRelationPK.class)
public class FriendRelation implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name="friend_request_sender_id")
    private User friendRequestSender;

    @Id
    @ManyToOne
    @JoinColumn(name="friend_request_receiver_id")
    private User friendRequestReceiver;

    @Column(name="friend_request_status")
    private String status;

    public FriendRelation() {}

    public FriendRelation(User friendRequestSender, User friendRequestReceiver, String status) {
        this.friendRequestSender = friendRequestSender;
        this.friendRequestReceiver = friendRequestReceiver;
        this.status = status;
    }
}
