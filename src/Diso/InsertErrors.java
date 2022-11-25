package Diso;

import java.io.Serializable;

public class InsertErrors extends Exception implements Serializable {
    public InsertErrors(String error)
    {
        super("insert error "+error);
    }
}
