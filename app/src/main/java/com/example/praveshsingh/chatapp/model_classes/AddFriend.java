package com.example.praveshsingh.chatapp.model_classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddFriend {
    @SerializedName("MemberId")
    @Expose
    private String memberId;

    public AddFriend(String memberId, String friendId, String requestBy, String createdBy, String modifiedBy) {
        this.memberId = memberId;
        this.friendId = friendId;
        this.requestBy = requestBy;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
    }

    @SerializedName("FriendId")
    @Expose
    private String friendId;
    @SerializedName("RequestBy")
    @Expose
    private String requestBy;
    @SerializedName("CreatedBy")
    @Expose
    private String createdBy;
    @SerializedName("ModifiedBy")
    @Expose
    private String modifiedBy;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getRequestBy() {
        return requestBy;
    }

    public void setRequestBy(String requestBy) {
        this.requestBy = requestBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

}
