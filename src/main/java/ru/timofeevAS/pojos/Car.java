package ru.timofeevAS.pojos;

public class Car {
    private String name;
    private boolean isSold;
    private Person owner;

    public Car(){
        this.name = "";
        this.isSold = false;
        this.owner = new Person();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSold() {
        return isSold;
    }

    public void setSold(boolean sold) {
        isSold = sold;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", isSold=" + isSold +
                ", owner=" + owner +
                '}';
    }
}
