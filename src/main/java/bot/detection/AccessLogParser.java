package bot.detection;

import com.panforge.robotstxt.RobotsTxt;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccessLogParser {

    public static void LogParser (String record, RobotsTxt robotsTxt)
    {
        // Creating a regular expression for the records
        final String regex = "^(\\S+) (\\S+) (\\S+) " +
                "\\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(\\S+)" +
                " (\\S+)\\s*(\\S+)?\\s*\" (\\d{3}) (\\S+) \"(.*?)\" \"(.*?)\"";

        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(record);

        long i=0;

        //We start to consume the log
        long startTime = System.nanoTime();
        Detection detection = new Detection();
        while (matcher.find()) {
            i++;
            System.out.println(i);
            Log log = new Log(matcher);
            detection.runDetection(log, robotsTxt, i);
        }

        //We finish to consume the log
        double endTime = System.nanoTime();
        double timeElapsed = endTime - startTime;
        System.out.println("Total number of requests : " + i);
        System.out.println("Execution time in milliseconds : " + timeElapsed / 1000000);
        System.out.println("Average execution time of a request in milliseconds : " + (timeElapsed / 1000000)/i);
    }
    public static void main(String[] args) {

        if(args.length != 2){
            System.out.println("Please specify access.log.txt file in your system and URL of robots.txt");
            return;
        }

        Path filePath = Paths.get(args[0]);
        String content="";
        try {
            content = Files.readString(filePath);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        RobotsTxt robotsTxt = null;
        try(InputStream robotsTxtStream = new URL(args[1]).openStream()) {
            robotsTxt = RobotsTxt.read(robotsTxtStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogParser(content, robotsTxt);
    }

}
