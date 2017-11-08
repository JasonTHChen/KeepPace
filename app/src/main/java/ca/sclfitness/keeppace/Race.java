package ca.sclfitness.keeppace;

/**
 * Created by Jason on 09-Oct-2017.
 */

public class Race {
    private double mTotalDistance;
    private String mName;

    public Race(String name, double totalDistance) {
        System.out.println(totalDistance);
        this.mName = name;
        this.mTotalDistance = totalDistance;
    }

    public String getName() {
        return mName;
    }

    public int getMakers() {
        if (mTotalDistance == 853) {
            return 4;
        }

        return (int) mTotalDistance;

    }

    public double getCurrentPace(int marker, long time, int mode) {
        double currentDistance = (double) marker;
        if (mode == 1) {
            if (marker == 1) {
                currentDistance = 853 * 0.33;
            } else if (marker == 2) {
                currentDistance = 853 * 0.54;
            } else if (marker == 3) {
                currentDistance = 853 * 0.78;
            } else {
                currentDistance = 853;
            }
        }
        return currentDistance / (double) time;
    }

    public long getEstimateTime(double speed) {
        return (long) (mTotalDistance / speed);
    }

    public String getMarkerName(int count, int mode) {
        String markerName = String.valueOf(count) + "K";
        if (mode == 1) {
            if (count == 1) {
                markerName = "1/4";
            } else if (count == 2) {
                markerName = "1/2";
            } else if (count == 3) {
                markerName = "3/4";
            }
        }

        if (count == getMakers()) {
            markerName = "finish";
        }

        return markerName;
    }
}
