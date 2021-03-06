package binzh.wan.comechat.chat;

public class ContentVo  {
    private String toName;
    private String msg;
    private Integer type;


    @Override
    public String toString() {
        return "ContentVo{" +
                "toName='" + toName + '\'' +
                ", msg='" + msg + '\'' +
                ", type=" + type +
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
