package org.example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FreeTimeExtractor {
    List<TimeInterval>getFreeTimes(ArrayList<String>classTimes) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");


        // Define your class schedule as time intervals
        List<TimeInterval> classSchedule = new ArrayList<>();

        for(String x: classTimes){

            if(x.contains("–")){
                String[] split=x.split("–");

                String start=split[0];
                String end=split[1];

                classSchedule.add(new TimeInterval(dateFormat.parse(start),dateFormat.parse(end)));
            }else if(x.contains("-")){
                String[] split=x.split("-");

                String start=split[0];
                String end=split[1];


                classSchedule.add(new TimeInterval(dateFormat.parse(start),dateFormat.parse(end)));
            }

        }

        // Define the time range from 8:30 AM to 4:30 PM
        Date startTime = dateFormat.parse("8:30 AM");
        Date endTime = dateFormat.parse("4:30 PM");

        // Sort the class schedule by start time
        Collections.sort(classSchedule, Comparator.comparing(TimeInterval::getStartTime));

        // Initialize free time intervals
        List<TimeInterval> freeTimes = new ArrayList<>();

        // Find free time intervals
        Date previousEndTime = startTime;
        for (TimeInterval interval : classSchedule) {
            if (interval.getStartTime().after(previousEndTime)) {
                freeTimes.add(new TimeInterval(previousEndTime, interval.getStartTime()));
            }
            previousEndTime = interval.getEndTime();
        }

        // Check if there's free time after the last class
        if (previousEndTime.before(endTime)) {
            freeTimes.add(new TimeInterval(previousEndTime, endTime));
        }

        // Print the free time intervals (excluding one-minute intervals)
//        for (TimeInterval freeTime : freeTimes) {
//            long timeDifference = freeTime.getEndTime().getTime() - freeTime.getStartTime().getTime();
//
//            // Check if the time difference is greater than one minute
//            if (timeDifference > 60000) { // 60000 milliseconds = 1 minute
//                System.out.println("Free time: " + dateFormat.format(freeTime.getStartTime()) + " - " + dateFormat.format(freeTime.getEndTime()));
//            }
//        }


        return freeTimes;
    }
}

class TimeInterval {
    private Date startTime;
    private Date endTime;

    public TimeInterval(Date startTime, Date endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }
}
