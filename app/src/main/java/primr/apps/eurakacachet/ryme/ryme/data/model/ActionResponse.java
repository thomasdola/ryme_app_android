package primr.apps.eurakacachet.ryme.ryme.data.model;


public class ActionResponse {

    public String status;
    public String message;
    public int code;

    @Override
    public String toString() {
        return "ActionResponse{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", code=" + code +
                '}';
    }
}
