package uk.co.solong.steam4j.tf2.data;

public enum Status {
    SUCCESS(1), STEAMID_INVALID_MISSING(8), PRIVATE(15), NO_SUCH_STEAMID(18), UNKNOWN_STATUS(-1);

    private int status;

    Status(int status) {
        this.status = status;
    }

    public int getState() {
        return status;
    }

    public static Status fromState(int state) {
        switch (state) {
        case 1:
            return SUCCESS;
        case 8:
            return STEAMID_INVALID_MISSING;
        case 15:
            return PRIVATE;
        case 18:
            return NO_SUCH_STEAMID;
        default:
            return UNKNOWN_STATUS;
        }
    }

}
