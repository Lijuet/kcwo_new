package practice.example.com.kcwo_new;

import java.io.Serializable;

public class ItemClass implements Serializable{
    private String name;
    private int value;
    private String type;

    public ItemClass(){}

    public ItemClass(String name, int value, String type){
        this.name =  name;
        this.value = value;
        this.type = type;
    }

    public String getName(){ return name;}
    public int getValue(){ return value;}
    public String getType(){ return type;}

    public void setName(String name){ this.name = name; }
    public void setValue(int value){ this.value = value; }
    public void setType(String type){ this.type = type; }
}