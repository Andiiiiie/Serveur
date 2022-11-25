package Diso;

import java.io.Serializable;

public class GrammarError extends Exception implements Serializable {
    public GrammarError(String s)
    {
        super("global error: "+s);
    }
}
