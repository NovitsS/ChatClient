package sample.util;

import java.io.Serializable;

public class Msg implements Serializable{
    private static final long serialId=2L;
    public String kind;
    public String content = null;
    public String msgContent=null;
    public String ip=null;
    public String isOnLine=null;
    public String id=null;
    public String icon;
    public int port;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Msg(String kind) {
        this.kind = kind;
    }

    public String getKind(){
        return this.kind;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIsOnLine() {
        return isOnLine;
    }

    public void setIsOnLine(String isOnLine) {
        this.isOnLine = isOnLine;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}