package com.amidos.trainstask;

public class Town {

    private String name;

    private boolean stopped;

    public Town(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setName()
    {
        this.name = name;
    }


    @Override
    public int hashCode() {
        return (name != null) ? super.hashCode() : 0;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }

        Town town = (Town) obj;

        return (name != null) ? this.name.equals(town.name) : town == null;
    }

    public boolean hasStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }
}
