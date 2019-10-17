package ru.asntl.callsharklibrary.config;

public class CallSharkConfig {

    private static String callSharkUrl;
    private static int yandexVisitorId;
    private static int clientId;
    private static int siteId;

    private static int layoutResIDForVideoCaptureActivity;
    private static int videoDurationLimit = 10;

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

    public static int getLayoutResIDForVideoCaptureActivity() {
        return layoutResIDForVideoCaptureActivity;
    }

    public static void setLayoutResIDForVideoCaptureActivity(int layoutResIDForVideoCaptureActivity) {
        CallSharkConfig.layoutResIDForVideoCaptureActivity = layoutResIDForVideoCaptureActivity;
    }

    public static int getVideoDurationLimit() {
        return videoDurationLimit;
    }

    public static void setVideoDurationLimit(int videoDurationLimit) {
        CallSharkConfig.videoDurationLimit = videoDurationLimit;
    }

    public static String getURLForSendFileToServer(){
        return callSharkUrl+"/client/yandexVideo";
    }

    public static String getURLForStarter(){
        return getCallSharkUrl()+"/client/checkOnlineOperators?siteId="+ getSiteId()+"&clientId="+ getClientId();
    }
}
