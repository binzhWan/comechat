package binzh.wan.comechat.chat;

public class ContentVo  {
    private String toName;
    private String msg;
    private Integer type;
    private String toId;

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    @Override
    public String toString() {
        return "ContentVo{" +
                "toName='" + toName + '\'' +
                ", msg='" + msg + '\'' +
                ", type=" + type +
                ", toId='" + toId + '\'' +
                '}';
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
