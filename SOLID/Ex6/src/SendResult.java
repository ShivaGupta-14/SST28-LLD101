public class SendResult {
    private final boolean success;
    private final String errorMessage;

    private SendResult(boolean success, String errorMessage) {
        this.success = success;
        this.errorMessage = errorMessage;
    }

    public static SendResult success() {
        return new SendResult(true, null);
    }

    public static SendResult failure(String errorMessage) {
        return new SendResult(false, errorMessage);
    }

    public boolean isSuccess() { return success; }
    public String getErrorMessage() { return errorMessage; }
}
