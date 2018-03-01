package com.cdkj.myxb.models;

/**评论列表
 * Created by cdkj on 2018/2/26.
 */

public class CommentListMode {


    /**
     * code : C201802242149238508593
     * type : P
     * orderCode : PO201802241635214937378
     * entityCode : P201802241111118419027
     * score : 5
     * content : 454545
     * status : A
     * commenter : U201802221556357869675
     * commentDatetime : Feb 24, 2018 9:49:40 PM
     * entityName : 463
     * nickname : 57869675
     * photo :
     */

    private String code;
    private String type;
    private String orderCode;
    private String entityCode;
    private int score;
    private String content;
    private String status;
    private String commenter;
    private String commentDatetime;
    private String entityName;
    private String nickname;
    private String photo;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getEntityCode() {
        return entityCode;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCommenter() {
        return commenter;
    }

    public void setCommenter(String commenter) {
        this.commenter = commenter;
    }

    public String getCommentDatetime() {
        return commentDatetime;
    }

    public void setCommentDatetime(String commentDatetime) {
        this.commentDatetime = commentDatetime;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
