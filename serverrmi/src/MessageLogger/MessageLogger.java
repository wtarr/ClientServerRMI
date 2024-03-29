package MessageLogger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: William
 * Date: 15/12/13
 * Time: 19:11
 * To change this template use File | Settings | File Templates.
 */
public class MessageLogger implements Subject {
    private ArrayList<Observer> observers;
    private String message;

    public MessageLogger()
    {
        observers = new ArrayList<Observer>();
    }


    @Override
    public void registerObserver(Observer ob) {
        observers.add(ob);
    }

    @Override
    public void removeObserver(Observer ob) {
        int i = observers.indexOf(ob);
        if (i >= 0)
        {
            observers.remove(i);
        }
    }

    @Override
    public void notifyObservers() {
        for ( int i = 0 ; i < observers.size(); i++)
        {
             Observer observer = observers.get(i);
             observer.update(message);
        }
    }

    public void messageChanged()
    {
        notifyObservers();
    }

    public void setMessage(String msg)
    {
        Date dt = new Date();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        message = df.format(dt)+ " -> " + msg;
        messageChanged();
    }


}
