package business;

import lombok.Data;

import java.io.Serializable;

@Data
public class FriendRelationPK implements Serializable {
    Integer friendRequestSender;
    Integer friendRequestReceiver;

    public FriendRelationPK() {}

    public FriendRelationPK(Integer friendRequestSender, Integer friendRequestReceiver) {
        this.friendRequestSender = friendRequestSender;
        this.friendRequestReceiver = friendRequestReceiver;
    }
}
