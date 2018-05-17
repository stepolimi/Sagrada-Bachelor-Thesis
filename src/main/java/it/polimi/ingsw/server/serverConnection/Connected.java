package it.polimi.ingsw.server.serverConnection;

;
import java.util.*;

import static it.polimi.ingsw.costants.LoginMessages.startingGameMsg;

public class Connected {
private HashMap <Connection,String> users = new HashMap<Connection,String>();


    public HashMap<Connection,String> getUsers() {
        return users;
    }

    public void forwardMessage(List action)
    {
        Iterator <Connection> it = users.keySet().iterator();
        while(it.hasNext())
        {
            //if(action.get(0).equals(startingGameMsg))
                it.next().sendMessage((String)action.get(1));
        }
    }

    public void sendMessage(List action){
        Iterator <Connection> it = users.keySet().iterator();
        while(it.hasNext())
        {
            Connection conn = it.next();
            if(users.get(conn).equals(action.get(1)))
                conn.sendMessage((String)action.get(0));
        }
    }

    public int nConnection()
    {
        return users.size();
    }

    public boolean checkUsername(String str)
    {
        Iterator <Connection> it = users.keySet().iterator();
        while(it.hasNext())
        {
            Connection conn = it.next();
            if(users.get(conn).equals(str))
            {
                System.out.println("Esiste già username");
                return false;
            }
        }

        return true;
    }

    public String remove(Connection user)
    {
        String name=null;
        Iterator <Connection> it = users.keySet().iterator();
        while(it.hasNext())
        {
            Connection conn = it.next();
            if(user.equals(conn))
            {
                name = users.get(conn);
                it.remove();
            }
        }

        return name;
    }
}
