package bot.detection;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;

public class Log {

    private String ip;
    private String clientId;
    private String userID;
    private LocalDateTime dateTime;
    private String method;
    private String endpoint;
    private String protocol;
    private String responseCode;
    private String contentSize;
    private String referer;
    private String agent;

    public Log(Matcher matcher) {
        this.ip = matcher.group(1);
        this.clientId = matcher.group(2);
        this.userID = matcher.group(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MMM/yyyy:H:m:s Z", Locale.ENGLISH);
        this.dateTime = LocalDateTime.parse(matcher.group(4),formatter);
        this.method = matcher.group(5);
        this.endpoint = matcher.group(6);
        this.protocol = matcher.group(7);
        this.responseCode = matcher.group(8);
        this.contentSize = matcher.group(9);
        this.referer = matcher.group(10);
        this.agent = matcher.group(11).toLowerCase();
    }

    @Override
    public String toString() {
        return "Request{" +
                "ip='" + ip + '\'' +
                ", clientId='" + clientId + '\'' +
                ", userID='" + userID + '\'' +
                ", dateTimeString=" + dateTime +
                ", method='" + method + '\'' +
                ", endpoint='" + endpoint + '\'' +
                ", protocol='" + protocol + '\'' +
                ", responseCode='" + responseCode + '\'' +
                ", contentSize='" + contentSize + '\'' +
                ", referer='" + referer + '\'' +
                ", agent='" + agent + '\'' +
                '}';
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getContentSize() {
        return contentSize;
    }

    public void setContentSize(String contentSize) {
        this.contentSize = contentSize;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }
}
