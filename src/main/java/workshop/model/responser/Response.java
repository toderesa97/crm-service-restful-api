package workshop.model.responser;

public class Response {

    private int code;
    private String description;
    private String token;

    public Response(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public Response(int code, String description, String token) {
        this.code = code;
        this.description = description;
        this.token = token;
    }

    public Response() {

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response response = (Response) o;
        return getCode() == response.getCode();
    }
}
