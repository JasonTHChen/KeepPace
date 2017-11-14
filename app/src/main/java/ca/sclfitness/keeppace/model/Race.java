package ca.sclfitness.keeppace.model;

import java.util.Locale;

/**
 * Basic model for a race
 *
 * @version kp 1.0
 * @author Jason, Tzu Hsiang Chen
 * @since November 12, 2017
 */

public class Race {
    private int mId;
    private String mName;
    private double mDistance;
    private int mMarkers;
    private double mAveragePace;
    private long mBestTime;

    /**
     * Default constructor
     */
    public Race() {
        this.mName = "";
    }

    /**
     * Construct a race
     * @param name - name of the race
     * @param distance - total distance of the race
     * @param markers - total markers of the race
     */
    public Race(String name, double distance, int markers) {
        this.mName = name;
        this.mDistance = distance;
        this.mMarkers = markers;
        this.mAveragePace = 0.00;
        this.mBestTime = 0;
    }

    /**
     * Get current pace
     *
     * @param currentMarker - current marker
     * @param currentTime - current time
     * @return current pace
     */
    public double getCurrentPace(int currentMarker, long currentTime) {
        double currentDistance = (double) currentMarker;
        return currentDistance / (double) currentTime;
    }

    /**
     * Get estimated finish time
     * @param pace - current speed
     * @return estimated finish time
     */
    public long getEstimateTime(double pace) {
        return (long) (mDistance / pace);
    }

    /**
     * Get marker names.
     *
     * @param count - current marker
     * @return marker name
     */
    public String getMarkerName(int count) {
        if (count == getMarkers()) {
            return "finish";
        }
        return String.valueOf(count) + "K";
    }

    /**
     * Converts milliseconds to hh:mm:ss or mm:ss.ss
     *
     * @param ms - milliseconds
     * @return time in String format
     */
    public String timeTextFormat(long ms) {
        int msec = (int) (ms / 10) % 100;
        int sec = (int) (ms / 1000);
        int min = sec / 60;
        int hour = min / 60;
        sec = sec % 60;
        if (hour > 0) {
            return String.format(Locale.getDefault(), "%02d:%02d:%02d", hour, min, sec);
        }
        return String.format(Locale.getDefault(), "%02d:%02d.%02d", min, sec, msec);
    }

    /**
     * Converts estimate time to String
     *
     * @param pace - current speed
     * @return String format hh:mm:ss or mm:ss.ss
     */
    public String estimateTimeText(double pace) {
        return timeTextFormat(getEstimateTime(pace));
    }

    /**
     * Converts best time to String
     * @return String format hh:mm:ss or mm:ss.ss
     */
    public String bestTimeText() {
        return timeTextFormat(mBestTime);
    }

    /**
     * Get race ID.
     *
     * @return mId - race ID as int.
     */
    public int getId() {
        return mId;
    }

    /**
     * Set race ID.
     *
     * @param id - race ID.
     */
    public void setId(int id) {
        this.mId = id;
    }

    /**
     * Get name of the race.
     * \
     * @return mName - name of the race as String.
     */
    public String getName() {
        return mName;
    }

    /**
     * Set name of the race.
     *
     * @param name - name of the race.
     */
    public void setName(String name) {
        this.mName = name;
    }

    /**
     * Get total distance.
     *
     * @return mDistance - total distance as double.
     */
    public double getDistance() {
        return mDistance;
    }

    /**
     * Set total distance.
     *
     * @param distance - total distance.
     */
    public void setDistance(double distance) {
        this.mDistance = distance;
    }

    /**
     * Get total markers
     * @return mMarkers - total markers as int
     */
    public int getMarkers() {
        return mMarkers;
    }

    /**
     * Set total markers
     * @param markers - total markers
     */
    public void setMarkers(int markers) {
        this.mMarkers = markers;
    }

    /**
     * Get average pace of the race
     * @return mAveragePace - average pace as double
     */
    public double getAveragePace() {
        return mAveragePace;
    }

    /**
     * Set average pace of the race
     * @param averagePace - average pace
     */
    public void setAveragePace(double averagePace) {
        this.mAveragePace = averagePace;
    }

    /**
     * Get best time of the race
     * @return mBestTime - best time of the race as String
     */
    public long getBestTime() {
        return mBestTime;
    }

    /**
     * Set best time of the race
     * @param bestTime - best time
     */
    public void setBestTime(long bestTime) {
        this.mBestTime = bestTime;
    }

    @Override
    public String toString() {
        return "Race{" +
                "mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mDistance=" + mDistance +
                ", mMarkers=" + mMarkers +
                ", mAveragePace=" + mAveragePace +
                ", mBestTime='" + mBestTime + '\'' +
                '}';
    }
}
