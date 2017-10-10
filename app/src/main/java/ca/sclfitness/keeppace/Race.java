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

    public double getCurrentPace(int currentDistance, long time) {
        return (double) (currentDistance) / (double) time;
    }

    public long getEstimateTime(double speed) {
        return (long) (mTotalDistance / speed);
    }
}
