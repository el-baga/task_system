package dev.kazimiruk.tasksystem.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonUtil {

    private static HttpServletRequest httpRequest;

    @Autowired
    public void initHttpRequest(HttpServletRequest httpRequest) {
        CommonUtil.httpRequest = httpRequest;
    }

    public static Long getCurrentUserId() {
        return Long.valueOf(httpRequest.getUserPrincipal().getName());
    }
}
