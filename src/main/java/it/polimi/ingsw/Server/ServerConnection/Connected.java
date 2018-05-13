package it.polimi.ingsw.Server.ServerConnection;

;
import java.util.*;

public class Connected {
private HashMap <Connection,String> users = new HashMap<Connection,String>();


    public HashMap<Connection,String> getUsers() {
        return users;
    }

    public void sendMessage(String str)
    {
        Iterator <Connection> it = users.keySet().iterator();
        while(it.hasNext())
        {
            it.next().sendMessage(str);
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
