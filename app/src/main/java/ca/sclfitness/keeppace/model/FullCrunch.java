package ca.sclfitness.keeppace.model;

/**
 * @author Jason, Tzu Hsiang Chen
 * @since November 21, 2017
 */

public class FullCrunch extends Race {
    private static final double MARKER_1 = 0.10;
    private static final double MARKER_2 = 0.23;
    private static final double MARKER_3 = 0.27;
    private static final double MARKER_4 = 0.52;
    private static final double MARKER_5 = 0.63;
    private static final double MARKER_6 = 0.70;
    private static final double MARKER_7 = 0.87;

    public static final String FULL_CRUNCH = "Full Crunch";

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
            default:
                return this.getDistance() / (double) currentTime;
        }
    }

    @Override
    public String getMarkerName(int count) {
        switch (count) {
            case 1:
                return "Beg steps";
            case 2:
                return "End Steps";
            case 3:
                return "Lansdowne";
            case 4:
                return "Bramble Park";
            case 5:
                return "223m Elv.";
            case 6:
                return "Panorama";
            case 7:
                return "2.0 km";
            default:
                return super.getMarkerName(count);
        }
    }
}
