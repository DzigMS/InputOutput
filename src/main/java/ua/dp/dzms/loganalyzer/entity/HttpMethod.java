package ua.dp.dzms.loganalyzer.entity;

public enum HttpMethod {
    GET("GET"), POST("POST");

    private String method;

    HttpMethod(String method) {
        this.method = method;
    }

    public static HttpMethod getHttpMethod(String method){
        try {
            return HttpMethod.valueOf(method);
        }catch (Exception e){
            throw new IllegalArgumentException("String is not an enum");
        }
    }
}