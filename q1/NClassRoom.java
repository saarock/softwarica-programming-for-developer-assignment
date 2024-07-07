package q1;

import java.util.PriorityQueue;
import java.util.Random;

public class NClassRoom {

    public static int roomId = 0;
    public static int n = 3;
    public static int[] roomUsage = new int[n];


    // Comparable For the classes by there start time
    static class Cls implements Comparable<Cls> {
        int startTime;
        int endTime;
        int size;

        public Cls(int startTime, int endTime, int size) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.size = size;
        }

        // Classes with earlier start times have priority
        @Override
        public int compareTo(Cls cls) {
            if (this.startTime != cls.startTime) {
                return this.startTime - cls.startTime;
            } else {
                // If start times are the same, prioritize the larger class size
                return cls.size - this.size;
            }
        }
    }


    // Comparable the running classes with there ending time;
    static class RunningClass implements Comparable<RunningClass> {
        int endTime;
        int roomNumber;

        public RunningClass(int endTime, int roomNumber) {
            this.endTime = endTime;
            this.roomNumber = roomNumber;
        }


        // Classes with earlier end times have priority
        @Override
        public int compareTo(RunningClass rc) {
            return this.endTime - rc.endTime;
        }
    }

    public static int scheduling(int[][] classes) {
        PriorityQueue<Cls> scheduleClass = new PriorityQueue<>();
        PriorityQueue<RunningClass> runningClasses = new PriorityQueue<>();

        for (int i = 0; i < classes.length; i++) {
            int start = classes[i][0];
            int end = classes[i][1];
            // Assuming a random size for demonstration
            int size = new Random().nextInt(100) + 1;
            scheduleClass.add(new Cls(start, end, size));
        }

        while (!scheduleClass.isEmpty()) {
            Cls cls = scheduleClass.poll();

            while (!runningClasses.isEmpty() && runningClasses.peek().endTime <= cls.startTime) {
                runningClasses.poll();
            }

            if (runningClasses.size() < n) {
                int assignedRoom = runningClasses.size();
                runningClasses.add(new RunningClass(cls.endTime, assignedRoom));
                roomUsage[assignedRoom]++;
            } else {
                RunningClass finishedClass = runningClasses.poll();
                int assignedRoom = finishedClass.roomNumber;
                runningClasses.add(new RunningClass(finishedClass.endTime + (cls.endTime - cls.startTime), assignedRoom));
                roomUsage[assignedRoom]++;
            }
        }

  
        int maxUsage = roomUsage[0];
        int roomWithMaxUsage = 0;

        for (int i = 1; i < n; i++) {
            if (roomUsage[i] > maxUsage) {
                maxUsage = roomUsage[i];
                roomWithMaxUsage = i;
            }
        }
        System.out.println("  Max Room use by the class is " + maxUsage);
        return roomWithMaxUsage;
        
    }

    public static void main(String[] args) {
        int[][] classes =  {{1, 20},{2,10},{3,5},{4,9},{6,8}};
        int result = scheduling(classes);
        System.out.println(result);
    }
}
