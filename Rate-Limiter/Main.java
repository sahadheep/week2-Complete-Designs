public class Main {
    public static void main(String[] args) throws InterruptedException {
        RateLimitManager manager = new RateLimitManager();
        ExternalServiceCaller service = new ExternalServiceCaller(manager);

        // Simulate 7 requests for User_T1 (Limit is 5 per minute)
        for (int i = 1; i <= 7; i++) {
            System.out.print("Request " + i + ": ");
            service.processRequest("User_T1", true);
            Thread.sleep(100); 
        }
    }
}