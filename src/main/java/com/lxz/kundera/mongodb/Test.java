package com.lxz.kundera.mongodb;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * Created by xiaolezheng on 16/5/30.
 */

@Slf4j
public class Test {
    public static void main(String[] args){


        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mongo_pu");
        EntityManager em = emf.createEntityManager();

        for(int i=1; i<101;i++) {
            Person person = new Person();
            person.setPersonId(i);
            person.setPersonName("John Smith");
            person.setAge(32);
            person.setCreateTime(System.currentTimeMillis());
            person.setUpdateTime(System.currentTimeMillis());

            //Insert data
            em.persist(person);
        }

        em.clear();   //Clear cache before finding record

        // search for data
        for(int i=1; i<101; i++) {
            Person personFound = em.find(Person.class,i);
            personFound.setUpdateTime(System.currentTimeMillis());
            em.merge(personFound);

            log.info("p: {}",personFound);
        }

        em.close();
        emf.close();
    }
}
