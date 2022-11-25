package Diso;

import java.io.Serializable;

public class SelectErrors extends Exception implements Serializable {
    public SelectErrors(String error)
    {
        super("select error: "+error);
    }
}
