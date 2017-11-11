package sample.util;

public class MsgContent {
    private String kind;
    private String content;

    public MsgContent(String kind,String content){
        this.kind = kind;
        this.content = content;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
