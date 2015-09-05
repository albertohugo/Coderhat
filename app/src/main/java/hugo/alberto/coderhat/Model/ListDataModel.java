package hugo.alberto.coderhat.Model;

public class ListDataModel {

    private String userid;
    private String id;
    private String title;
    private String body;

    public ListDataModel() {
    }

    public ListDataModel(String id, String userid, String title, String body) {
        this.id = id;
        this.userid = userid;
        this.title = title;
        this.body = body;
    }


    public String getUserId() {
        return userid;
    }

    public void setUserId(String userid) {
        this.userid = userid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
