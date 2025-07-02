package otee.dev.swipe.util;

public class ServiceResponse {

    public static Boolean isNullOrBlank(String str){
        return str == null || str.isBlank();
    }

    public static Boolean isNullOrBlank(Long id){
        return id == null;
    }

    public static Boolean isNullOrBlank(Double n){
        return n == null;
    }
}
