package com.dialog.blend.logX;

/**
 * The Logger interface provides methods to log messages with different log
 * levels.
 */
public interface Logger {

    // Declared application name
    String APP_NAME = "DialogBlend";

    // Log format for each log entry
    String LOG_FORMAT = "[%s] [%-5s] [%s:%d] - %s %n";

    // 10 MB (adjust as needed)
    long MAX_FILE_SIZE = 10 * 1024 * 1024;

    // Banner encoded in Base64 - change the encoded string for custom header
    String BANNER = "ICAgX19fIF9fX19fXyAgIF9fX19fIF9fXyBfX18gX19fIF9fXyAgX19fIF9fX19fIAogIC8gX198XyAgL1wgXCAvIC8gXyBcXyBfLyBfX3wgX198IF8gKS8gXyBcXyAgIF98CiB8IChfXyAvIC8gIFwgVi"
            + "AvIChfKSB8IHwgKF9ffCBffHwgXyBcIChfKSB8fCB8ICAKICBcX19fL19fX3wgIFxfLyBcX19fL19fX1xfX198X19ffF9fXy9cX19fLyB8X3wgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgKHYyL"
            + "jAuMjMpCgogKEMtWmVudHJpeCkgVG93YXJkcyBWaXNpb24gVGVjaG5vbG9naWVzIFB2dC4gTHRkLgoKIAogICAgICAgIFRpbWVTdGFtcCAgICAgICAgICB8ICAgICBMZXZlbCAvIFN0YWNrVHJhY2UgICAgfCAgICAgTWVzc2FnZXMK";

    /*
     * Sets the log level for the logger. Messages with a log level below the
     * specified level will not be logged.
     */
    public void setLogLevel(LogLevel level);

    // Logs all level message.
    public void trace(String trace);

    public void debug(String debug);

    public void info(String info);

    public void warn(String warn);

    public void error(String error);

    public void error(Exception e);

    public void fatal(String fatal);

}
