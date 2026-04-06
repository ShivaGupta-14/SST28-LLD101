public class ExternalService {

    public String call(String requestData) {
        System.out.println("    [ExternalService] Called with: " + requestData);
        return "response-for-" + requestData;
    }
}
