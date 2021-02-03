package bot.detection;

import com.panforge.robotstxt.RobotsTxt;
import java.time.Duration;
import java.util.AbstractMap;
import java.util.ArrayList;

public class Detection {

    private final int maxNumberRequests=1500;
    private final int minAverageRequestTime=2;
    ArrayList<Host> hosts = new ArrayList<>();

    public void runDetection(Log log, RobotsTxt robotsTxt, long i){

        Host host = hosts.stream()
                .filter(randomHost -> log.getIp().equals(randomHost.getIp()))
                .findAny()
                .orElse(new Host(log.getIp()));

        int index= hosts.indexOf(host);
        host.incrementNumberRequests();

        String message="";
        String tmp="";
        boolean botDetected = false;

        if(hasRobotTxtRequest(log, host)) {
            message += "hasRobotTxtRequest ";
            botDetected = true;
        }
        if(hasBotInUserAgent(log, host)) {
            message += "hasBotInUserAgent ";
            botDetected = true;
        }
        if(hasHighNumberRequests(log, host)) {
            message += "hasHighNumberRequests ";
            botDetected = true;
        }
        if(hasMoreThanTenTimesNullReferrer(log, host)) {
            message += "hasMoreThanTenTimesReferrerNulll ";
            botDetected = true;
        }

        AbstractMap.SimpleEntry<Double,Boolean> averageRequestTime = hasLowAverageRequestTime(log, host);

        if(averageRequestTime.getValue()) {
            message += "hasLowAverageRequestTime = " + String.format("%.2f", averageRequestTime.getKey()) + " ";
            botDetected = true;
        }
        else
            tmp=" AverageRequestTime = "+averageRequestTime.getKey();


        if(botDetected)
            if(followRules(log, robotsTxt))
                message = "BOT DETECTED [ REASONS: " + message + " ] -> " + log;
            else
                message = "BOT DETECTED [ REASONS: " + message + " || INFO : Doesn't follow the rules of robots.txt ] -> " + log;

        else
            message = "NOT A BOT [" + tmp + " ] -> " + log;
        System.out.println(message);
        System.out.println(host);

        if(index==-1)
            hosts.add(host);
        else
            hosts.add(index, host);
    }

    public boolean hasRobotTxtRequest(Log log, Host host){
        if (log.getEndpoint().contains("/robots.txt")) {
            return true;
        }
        return false;
    }

    public boolean hasBotInUserAgent(Log log, Host host){
        if (log.getAgent().contains("bot")) {
            return true;
        }
        return false;
    }

    public boolean hasHighNumberRequests(Log log, Host host){
        if(host.getNumberRequests() > maxNumberRequests) {
            return true;
        }
        return false;
    }

    public boolean hasMoreThanTenTimesNullReferrer(Log log, Host host){
        if (log.getReferer().compareTo("-")==0) {
            host.incrementTimesReferrerNull();
        }
        if(host.getTimesReferrerNull()>10)
            return true;
        return false;
    }

    public AbstractMap.SimpleEntry<Double,Boolean> hasLowAverageRequestTime(Log log, Host host){

        //We assume that we consider the average if the number of requests exceeds 4
        if(host.getNumberRequests()>=5) {
            double average = calculateAverageRequestTime(log, host);
            host.setAverageRequestTime(average);
            host.setLastRequestDateTime(log.getDateTime());
            return new AbstractMap.SimpleEntry<>(average, average <= minAverageRequestTime);
        }
        else if(host.getNumberRequests()<5 && host.getNumberRequests()>1) {
            double average = calculateAverageRequestTime(log, host);
            host.setAverageRequestTime(average);
            host.setLastRequestDateTime(log.getDateTime());
            return new AbstractMap.SimpleEntry<>(average, false);
        }
        //First request : Average=0
        else {
            host.setLastRequestDateTime(log.getDateTime());
            host.setAverageRequestTime(0);
            return new AbstractMap.SimpleEntry<>((double) 0, false);
        }

    }

    //Rules of robots.txt
    public boolean followRules(Log log, RobotsTxt robotsTxt){
        return robotsTxt.ask(log.getAgent(), log.getEndpoint()).hasAccess();
    }

    public double calculateAverageRequestTime(Log log, Host host){
        double lastDuration = Math.abs(Duration.between(host.getLastRequestDateTime(), log.getDateTime()).toSeconds());
        if(host.getNumberRequests()>2)
            return (host.getAverageRequestTime() * (host.getNumberRequests()-2) + lastDuration) / (host.getNumberRequests()-1);
        return lastDuration;
    }

}
