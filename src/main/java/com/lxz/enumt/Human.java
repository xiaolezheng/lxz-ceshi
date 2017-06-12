package com.lxz.enumt;

public class Human
{
    public void sing(HumanState state)
    {
        switch (state)
        {
            case HAPPY:
                singHappySong();
                break;
            case SAD:
                singDirge();
                break;
            default:
                throw new IllegalStateException("Invalid State: " + state);
        }
    }
 
    private void singHappySong()
    {
        System.out.println("When you're happy and you know it ...");
    }
 
    private void singDirge()
    {
        System.out.println("Don't cry for me Argentina, ...");
    }
}