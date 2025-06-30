package otee.dev.swipe.util;

import java.util.HashMap;
import java.util.Map;

public class ServiceResponse {

    public static Map<String, String> defaultResponse(Boolean isError, String message){
        Map<String, String> response = new HashMap<>();
        response.put("isError", String.valueOf(isError));
        response.put("message", message);
        return response;
    }

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
