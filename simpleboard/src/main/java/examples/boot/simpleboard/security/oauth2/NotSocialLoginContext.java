package examples.boot.simpleboard.security.oauth2;

public class NotSocialLoginContext {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    public static void setEmail(String email){
        threadLocal.set(email);
    }
    public static String getEmail(){
        return threadLocal.get();
    }
}
