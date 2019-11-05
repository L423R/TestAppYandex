package ru.asntl.callsharklibrary.config;

public class CallSharkConfig {

    private static String callSharkUrl;
    private static int yandexVisitorId;
    private static int clientId;
    private static int siteId;

    private static int videoDurationLimitMs = 10000;

    private CallSharkConfig() {
    }

    public static String getCallSharkUrl() {
        return callSharkUrl;
    }

    public static void setCallSharkUrl(String callSharkUrl) {
        CallSharkConfig.callSharkUrl = callSharkUrl;
    }

    public static int getYandexVisitorId() {
        return yandexVisitorId;
    }

    public static void setYandexVisitorId(int yandexVisitorId) {
        CallSharkConfig.yandexVisitorId = yandexVisitorId;
    }

    public static int getClientId() {
        return clientId;
    }

    public static void setClientId(int clientId) {
        CallSharkConfig.clientId = clientId;
    }

    public static int getSiteId() {
        return siteId;
    }

    public static void setSiteId(int siteId) {
        CallSharkConfig.siteId = siteId;
    }

    public static int getVideoDurationLimitMs() {
        return videoDurationLimitMs;
    }

    public static void setVideoDurationLimitMs(int videoDurationLimitMs) {
        CallSharkConfig.videoDurationLimitMs = videoDurationLimitMs;
    }

    public static String getURLForSendFileToServer(){
        return callSharkUrl+"/client/yandexVideo";
    }

    public static String getURLForStarter(){
        return getCallSharkUrl()+"/client/checkOnlineOperators?siteId="+ getSiteId()+"&clientId="+ getClientId();
    }
}
