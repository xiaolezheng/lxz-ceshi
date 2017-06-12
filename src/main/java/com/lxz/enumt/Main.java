package com.lxz.enumt;

import lombok.extern.slf4j.Slf4j;
import sun.reflect.ReflectionFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * Created by xiaolezheng on 17/6/12.
 */
@Slf4j
public class Main {
    public static void main(String[] args) throws Exception {
        try {
            buildCustomEnum();
        } catch (Exception e) {
            log.error("", e);
        }

        log.info("---------------------------------------");

        buildCustomEnum2();
    }

    private static void buildCustomEnum() throws NoSuchMethodException, InstantiationException, InvocationTargetException {
        Constructor cstr = HumanState.class.getDeclaredConstructor(
                String.class, int.class
        );
        ReflectionFactory reflection =
                ReflectionFactory.getReflectionFactory();
        HumanState e =
                (HumanState) reflection.newConstructorAccessor(cstr).newInstance(new Object[]{"ANGRY", 3});
        System.out.printf("%s = %d\n", e.toString(), e.ordinal());

        Human human = new Human();
        human.sing(e);
    }

    private static void buildCustomEnum2() {
        EnumBuster<HumanState> buster =
                new EnumBuster(HumanState.class,
                        Human.class);
        HumanState ANGRY = buster.make("ANGRY");
        buster.addByValue(ANGRY);
        System.out.println(Arrays.toString(HumanState.values()));

        Human human = new Human();
        human.sing(ANGRY);
    }
}
