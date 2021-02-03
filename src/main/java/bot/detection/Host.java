package bot.detection;

import java.time.LocalDateTime;

public class Host {

    private String ip;
    private long numberRequests=0;
    private  LocalDateTime lastRequestDateTime;
    private double averageRequestTime=0;
    private long timesReferrerNull=0;

    public Host(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "bot.detection.User{" +
                "ip='" + ip + '\'' +
                ", numberRequests=" + numberRequests +
                ", lastRequestDateTime=" + lastRequestDateTime +
                ", averageRequestTime=" + averageRequestTime +
                ", timesReferrerNull=" + timesReferrerNull +
                '}';
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public long getNumberRequests() {
        return numberRequests;
    }

    public void setNumberRequests(long numberRequests) {
        this.numberRequests = numberRequests;
    }

    public void incrementNumberRequests() {
        this.numberRequests++;
    }

    public LocalDateTime getLastRequestDateTime() {
        return lastRequestDateTime;
    }

    public void setLastRequestDateTime(LocalDateTime lastRequestDateTime) {
        this.lastRequestDateTime = lastRequestDateTime;
    }

    public double getAverageRequestTime() {
        return averageRequestTime;
    }

    public void setAverageRequestTime(double averageRequestTime) {
        this.averageRequestTime = averageRequestTime;
    }

    public long getTimesReferrerNull() {
        return timesReferrerNull;
    }

    public void setTimesReferrerNull(long timesReferrerNull) {
        this.timesReferrerNull = timesReferrerNull;
    }

    public void incrementTimesReferrerNull() {
        this.timesReferrerNull++;
    }

}
