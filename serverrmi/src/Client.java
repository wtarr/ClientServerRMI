import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: William
 * Date: 02/12/13
 * Time: 15:24
 * To change this template use File | Settings | File Templates.
 */
public class Client {
    private String owner;
    public ArrayList<String> filenameList;
    private Boolean online;

    public Client()
    {
        filenameList = new ArrayList<String>();
    }

    public String getOwner()
    {
        return owner;
    }

    public void setOwner(String owner)
    {
        this.owner = owner;
    }

    public Boolean getOnlineStatus() {
        return online;
    }

    public void setOnlineStatus(Boolean online) {
        this.online = online;
    }
}
