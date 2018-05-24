package practice.example.com.kcwo_new;

import java.io.Serializable;
import java.util.Calendar;

public class ItemClass implements Serializable{
    private String name;
    private int value;
    private String type;
    Calendar date ;


    public ItemClass(){}

    public ItemClass(String name, int value, String type, Calendar date){
        this.name =  name;
        this.value = value;
        this.type = type;
        this.date = date;
    }

    public String getName(){ return name;}
    public int getValue(){ return value;}
    public String getType(){ return type;}
    public Calendar getDate(){ return date;}

    public void setName(String name){ this.name = name;}
    public void setValue(int value){ this.value = value;}
    public void setType(String type){ this.type = type;}
    public void setDate(Calendar date){ this.date = date;}
}