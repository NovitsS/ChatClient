package sample.util;

/**
 * Created by HUSTy on 2017/10/20.
 */
public class Constants {
    public static String Me;
    public static final String LoginSrc = "resources/login.fxml";
    public static final String MainSrc = "resources/main.fxml";

    public static String USER_CURRENT = "离线消息";
    public static String IP_SERVER="127.0.0.1";
    public static String IP_CURRENT_CLIENT="127.0.0.1";
    public static String IP_ME = "127.0.0.1";
    public static final int PORT_TCP_CLIENT=9876;
    public static final int PORT_TCP_SERVER=9875;

    public static final String ERROR_SERVER="服务器无连接";
    public static final String ERROR_illegal="账号或密码不合法";
    public static final String ERROR_NO_MATCH="账号不存在";
    public static final String ERROR_ACCOUNT_PASSWORD="账号密码不匹配";
    public static final String ERROR_HAS = "账号被占用";

    public static final String SUCESS_LOGIN = "登陆成功";
    public static final String SUCESS_REGISTER = "注册成功";
    public static final String SUCESS_REVERSE = "改密成功";
    public static final String SUCESS_ADD = "添加好友成功";

    public static final String KIND_SERVER = "server";
    public static final String KIND_CLIENT = "client";
    public static final String KIND_IP = "ip";

    public static final String KIND_LOGIN="login";
    public static final String KIND_REGISTER="register";
    public static final String KIND_REVERSE="reverse";
    public static final String KIND_ADD = "add";
    public static final String KIND_OFFLINE_MSG = "offline_msg";
    public static final String KIND_OFFLINE = "offline";

    public static void setServerIP(String IP){
        IP_SERVER=IP;
    }

    public static void setIpCurrentClient(String IP){
        IP_CURRENT_CLIENT=IP;
    }

    public static String getIpMe() {
        return IP_ME;
    }

    public static void setIpMe(String ipMe) {
        IP_ME = ipMe;
    }
}
