public class StatisticsTracker {
    private int miss;
    private int strikes;
    private int totalMiss;
    private int totalHit;

    public StatisticsTracker() {
        reset();
    }

    public void reset() {
        miss = 0;
        strikes = 0;
        totalMiss = 0;
        totalHit = 0;
    }

    public void addHit() {
        totalHit++;
        miss = 0;
    }

    public void addMiss() {
        miss++;
        totalMiss++;

        if (miss == 5) {
            strikes++;
            miss = 0;
        }
    }

    public int getMiss() { return miss; }
    public int getStrikes() { return strikes; }
    public int getTotalMiss() { return totalMiss; }
    public int getTotalHit() { return totalHit; }
}