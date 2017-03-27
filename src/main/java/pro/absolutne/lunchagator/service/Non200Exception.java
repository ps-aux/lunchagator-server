package pro.absolutne.lunchagator.service;

public class Non200Exception extends RuntimeException {

    private final int httpStatus;
    private final String msg;

    public Non200Exception(int httpStatus, String msg) {
        super("Status: " + httpStatus + ", message: " + msg);
        this.httpStatus = httpStatus;
        this.msg = msg;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public String getMsg() {
        return msg;
    }
}
