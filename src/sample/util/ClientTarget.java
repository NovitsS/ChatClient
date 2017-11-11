package sample.util;

public class ClientTarget {
    public String Id;
    public String Ip;
    public String isOnLine;
    public String beforeString;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getIp() {
        return Ip;
    }

    public void setIp(String ip) {
        Ip = ip;
    }

    public String getIsOnLine() {
        return isOnLine;
    }

    public void setIsOnLine(String isOnLine) {
        this.isOnLine = isOnLine;
    }

    public String getBeforeString() {
        return beforeString;
    }

    public void setBeforeString(String beforeString) {
        this.beforeString = beforeString;
    }
}
