package ca.sclfitness.keeppace.model;

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
    private String mBestTime;

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
        this.mAveragePace = 0.0;
        this.mBestTime = "0";
    }

    public double getCurrentPace(int currentMarker, long currentTime) {
        double currentDistance = (double) currentMarker;
        return currentDistance / (double) currentTime;
    }

    public long getEstimateTime(double pace) {
        return (long) (mDistance / pace);
    }

    public String getMarkerName(int count) {
        if (count == getMarkers()) {
            return "finish";
        }
        return String.valueOf(count) + "K";
    }

    /**
     * Get race ID
     * @return mId - race ID as int
     */
    public int getId() {
        return mId;
    }

    /**
     * Set race ID
     * @param id - race ID
     */
    public void setId(int id) {
        this.mId = id;
    }

    /**
     * Get name of the race
     * @return mName - name of the race as String
     */
    public String getName() {
        return mName;
    }

    /**
     * Set name of the race
     * @param name - name of the race
     */
    public void setName(String name) {
        this.mName = name;
    }

    /**
     * Get total distance
     * @return mDistance - total distance as double
     */
    public double getDistance() {
        return mDistance;
    }

    /**
     * Set total distance
     * @param distance - total distance
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
    public String getBestTime() {
        return mBestTime;
    }

    /**
     * Set best time of the race
     * @param bestTime - best time
     */
    public void setBestTime(String bestTime) {
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
