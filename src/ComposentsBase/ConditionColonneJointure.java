package ComposentsBase;

import java.util.Arrays;
import java.util.Vector;

public class ConditionColonneJointure {


    String colTab1;
    String colTab2;

    public ConditionColonneJointure(String t1,String t2)
    {
        this.setColTab1(t1);
        this.setColTab2(t2);
    }

    public ConditionColonneJointure(String req)
    {
        Vector<String> liste=new Vector<>(Arrays.asList(req.split("=")));
        this.setColTab1(liste.get(0));
        this.setColTab2(liste.get(1));
    }

    //get and set
    public String getColTab1() {
        return colTab1;
    }

    public void setColTab1(String colTab1) {
        this.colTab1 = colTab1;
    }

    public String getColTab2() {
        return colTab2;
    }

    public void setColTab2(String colTab2) {
        this.colTab2 = colTab2;
    }
}
