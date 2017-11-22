package ca.sclfitness.keeppace.model;

/**
 * @author Jason, Tzu Hsiang Chen
 * @since November 21, 2017
 */

public class StairCrunch extends Race {
    private static final double MARKER_1 = 11.66;
    private static final double MARKER_2 = 22.37;
    private static final double MARKER_3 = 34.09;
    private static final double MARKER_4 = 45.45;
    private static final double MARKER_5 = 56.81;
    private static final double MARKER_6 = 68.18;
    private static final double MARKER_7 = 79.54;
    private static final double MARKER_8 = 90.90;

    public static final String STAIR_CRUNCH = "437 Steps";

    @Override
    public double getCurrentPace(int currentMarker, long currentTime) {
        switch (currentMarker) {
            case 1:
                return this.getDistance() * MARKER_1 / (double) currentTime;
            case 2:
                return this.getDistance() * MARKER_2 / (double) currentTime;
            case 3:
                return this.getDistance() * MARKER_3 / (double) currentTime;
            case 4:
                return this.getDistance() * MARKER_4 / (double) currentTime;
            case 5:
                return this.getDistance() * MARKER_5 / (double) currentTime;
            case 6:
                return this.getDistance() * MARKER_6 / (double) currentTime;
            case 7:
                return this.getDistance() * MARKER_7 / (double) currentTime;
            case 8:
                return this.getDistance() * MARKER_8 / (double) currentTime;
            default:
                return this.getDistance() / (double) currentTime;
        }
    }

    @Override
    public String getMarkerName(int count) {
        switch (count) {
            case 1:
                return "50";
            case 2:
                return "100";
            case 3:
                return "150";
            case 4:
                return "200";
            case 5:
                return "250";
            case 6:
                return "300";
            case 7:
                return "350";
            case 8:
                return "400";
            default:
                return super.getMarkerName(count);
        }
    }
}
