public class Time {
    private static Time instance = new Time();

    private int iteration = 0;

    public static Time getInstance() {
        return instance;
    }

    private Time() {
    }

    public static void increment() {
        instance.iteration++;
    }

    public static int getTime() {
        return instance.iteration;
    }
}
