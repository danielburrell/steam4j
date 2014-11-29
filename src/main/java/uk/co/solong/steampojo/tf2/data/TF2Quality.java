package uk.co.solong.steampojo.tf2.data;

public enum TF2Quality {
    NORMAL(0), GENUINE(1), RARITY2(2), VINTAGE(3), RARITY3(4), UNUSUAL(5), UNIQUE(6), COMMUNITY(7), VALVE(8), SELFMADE(9), CUSTOMIZED(10), STRANGE(11), COMPLETED(
            12), HAUNTED(13), COLLECTORS(14);
    private int quality;

    private TF2Quality(int quality) {
        this.quality = quality;
    }

    public int getQuality() {
        return quality;
    }
}
