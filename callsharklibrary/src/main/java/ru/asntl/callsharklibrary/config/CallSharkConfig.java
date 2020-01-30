package ru.asntl.callsharklibrary.config;

public class CallSharkConfig {

    private static String callSharkUrl;
    private static int yandexVisitorId;
    private static int clientId = 1;
    private static int siteId = 1;
    private static String lang = "RU";
    private static Class activitiesClass = null;
    private static String OAuthYandexToken = "";

    private static int videoDurationLimitMs = 120000;
    private static int videoDurationLimitMsMax = 120000;
    private static int audioDurationLimitMs = 300000;
    private static int audioDurationLimitMsMax = 300000;


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
        if (videoDurationLimitMs > videoDurationLimitMsMax)
            return getVideoDurationLimitMsMax();

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

    public static String getLang() {
        return lang;
    }

    public static void setLang(String lang) {
        CallSharkConfig.lang = lang;
    }

    public static Class getActivitiesClass() {
        return activitiesClass;
    }

    public static void setActivitiesClass(Class activitiesClass) {
        CallSharkConfig.activitiesClass = activitiesClass;
    }

    public static String getOAuthYandexToken() {
        return OAuthYandexToken;
    }

    public static void setOAuthYandexToken(String OAuthYandexToken) {
        CallSharkConfig.OAuthYandexToken = OAuthYandexToken;
    }

    public static int getAudioDurationLimitMs() {
        if (audioDurationLimitMs > audioDurationLimitMsMax)
            return getAudioDurationLimitMsMax();

        return audioDurationLimitMs;
    }

    public static void setAudioDurationLimitMs(int audioDurationLimitMs) {
        CallSharkConfig.audioDurationLimitMs = audioDurationLimitMs;
    }

    public static int getVideoDurationLimitMsMax() {
        return videoDurationLimitMsMax;
    }

    public static int getAudioDurationLimitMsMax() {
        return audioDurationLimitMsMax;
    }
}
