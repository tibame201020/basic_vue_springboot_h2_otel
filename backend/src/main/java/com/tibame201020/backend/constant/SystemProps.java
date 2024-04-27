package com.tibame201020.backend.constant;

/**
 * system props
 */
public class SystemProps {
    /**
     * constants
     */
    public static final String ROLE_PREFIX = "ROLE_";
    public static final String API_PREFIX = "/api";
    public static final String API_MAPPING = API_PREFIX + "/**";

    /**
     * cors
     */
    public static final String LOCAL_5173 = "http://localhost:5173/";
    public static final String LOCAL_5174 = "http://localhost:5174/";
    public static final String[] ALLOW_CORS_URLS = new String[]{LOCAL_5173, LOCAL_5174};

    /**
     * jwt
     */
    public static final String JWT_SECRET = "[a-zA-z0-9._]^+$ljdljlwqjmlwdqwdqmslkwqms$";
    public static final Integer ACCESS_TOKEN_DEFAULT_EXPIRE_HOURS = 10;

    /**
     * security
     */
    public static final String BEARER = "Bearer ";
}
