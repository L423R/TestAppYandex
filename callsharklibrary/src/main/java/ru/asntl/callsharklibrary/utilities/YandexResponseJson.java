package ru.asntl.callsharklibrary.utilities;

/**
 * @author m.krylov
 * Date: 27.01.2020
 */
class YandexResponseJson{
    String operation_id;
    String href;
    String method;
    String templated;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getTemplated() {
        return templated;
    }

    public void setTemplated(String templated) {
        this.templated = templated;
    }

    public String getOperation_id() {
        return operation_id;
    }

    public void setOperation_id(String operation_id) {
        this.operation_id = operation_id;
    }
}
