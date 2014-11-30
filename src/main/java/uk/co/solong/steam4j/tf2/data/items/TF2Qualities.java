package uk.co.solong.steam4j.tf2.data.items;

public enum TF2Qualities {
    NORMAL(0), GENUINE(1), VINTAGE(3), UNUSUAL(5), UNIQUE(6), COMMUNITY(7), VALVE(8), SELFMADE(9), CUSTOMIZED(10), STRANGE(11), COMPLETED(12), HAUNTED(13), COLLECTORS(
            14);
    private int quality;

    private TF2Qualities(int quality) {
        this.quality = quality;
    }

    public int getQuality() {
        return quality;
    }
}
