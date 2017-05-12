package ua.dp.dzms.loganalyzer;

import ua.dp.dzms.loganalyzer.entity.HttpMethod;
import ua.dp.dzms.loganalyzer.entity.LogToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogAnalyzer {
    private final static String PatternDate = "\\[(\\d{2}\\W.+\\W\\d{4}:\\d{2}:\\d{2}:\\d{2}).+";
    private final static String PatternHttpMethod = "(GET|POST) ";
    private final static String PatternMessage = "(.+)\"";

    public Collection<LogToken> parseFile(String path, LocalDateTime timeFrom, LocalDateTime timeTo) {
        String readLine;
        List<LogToken> logTokenList = new ArrayList<>();
        Pattern pattern = Pattern.compile(PatternDate + PatternHttpMethod + PatternMessage);

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            while ((readLine = bufferedReader.readLine()) != null) {
                Matcher matcher = pattern.matcher(readLine);
                if (matcher.find()) {
                    LocalDateTime logTokenDateTime = getDateTime(matcher.group(1).replaceAll("/|-|:", " "));
                    LogToken logToken;
                    if (logTokenDateTime.isAfter(timeFrom) && logTokenDateTime.isBefore(timeTo)) {
                        logToken = new LogToken();
                        logToken.setLocalDateTime(logTokenDateTime);
                        logToken.setMethod(getMethod(matcher.group(2)));
                        logToken.setMessage(matcher.group(3));
                        logTokenList.add(logToken);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logTokenList;
    }

    private LocalDateTime getDateTime(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH mm ss", Locale.ENGLISH);
        return LocalDateTime.parse(dateTime, formatter);
    }

    private HttpMethod getMethod(String httpMethod) {
        return HttpMethod.getHttpMethod(httpMethod);
    }
}
