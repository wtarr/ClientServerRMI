package MessageLogger;

/**
 * Created with IntelliJ IDEA.
 * User: William
 * Date: 15/12/13
 * Time: 19:08
 * To change this template use File | Settings | File Templates.
 */
public interface Subject {
    public void registerObserver(Observer ob);
    public void removeObserver(Observer ob);
    public void notifyObservers();
    public void setMessage(String message);

}
