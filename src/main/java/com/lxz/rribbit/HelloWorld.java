package com.lxz.rribbit;

import org.rribbit.Listener;
import org.rribbit.RequestResponseBus;
import org.rribbit.creation.InstantiatingClassBasedListenerObjectCreator;
import org.rribbit.creation.ObjectBasedListenerObjectCreator;
import org.rribbit.util.RRiBbitUtil;

public class HelloWorld {

    private static RequestResponseBus rrb;
    
    public static final void main(String[] args) throws Exception {

        ObjectBasedListenerObjectCreator creator = new InstantiatingClassBasedListenerObjectCreator(HelloWorld.class);
        rrb = RRiBbitUtil.createRequestResponseBusForLocalUse(creator, false);
        HelloWorld helloWorld = new HelloWorld();
        helloWorld.sayHello();
    }
    
    public void sayHello() {
        rrb.send("say", "Hello World");
    }
    
    @Listener(hint="say")
    public void say(String message) {
        System.out.println(message);
    }
    
    @Listener(hint="say")
    public void say2(String message) {
        System.out.println(message + " : )");
    }
}