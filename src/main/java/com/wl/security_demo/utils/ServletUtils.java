package com.wl.security_demo.utils;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ServletUtils {
    public static void renderString(HttpServletResponse response, String string) {
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
