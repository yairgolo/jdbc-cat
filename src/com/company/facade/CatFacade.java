package com.company.facade;

import com.company.dal.CatDAL;
import com.company.model.Cat;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Scanner;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CatFacade {

    public static final CatFacade instance = new CatFacade();

    public void addCat() {
        CatDAL catDAL = CatDAL.instance;
        System.out.print("please enter a name: ");
        String name = new Scanner(System.in).next();
        System.out.print("please enter a age: ");
        int age = new Scanner(System.in).nextInt();
        Cat cat = new Cat(name, age);
        System.out.println("id: " + catDAL.create(cat));
    }

    public void readcat() {
        CatDAL catDAL = CatDAL.instance;
        System.out.print("please enter a id: ");
        long id = new Scanner(System.in).nextLong();
        Cat cat = catDAL.read(id);
        if (cat == null) {
            System.out.println("Failed to get a cat");
        } else {
            System.out.println(cat);
        }
    }
    public void deleteCat(){
        CatDAL catDAL = CatDAL.instance;
        System.out.print("please enter a id to delete: ");
        long id = new Scanner(System.in).nextLong();
        catDAL.delete(id);
    }
    public void getAllCats(){
        CatDAL catDAL = CatDAL.instance;
        for (Cat cat : catDAL.readAll()) {
            System.out.println(cat);
        }
    }
    public void updateCat(){
        CatDAL catDAL = CatDAL.instance;
        Scanner scanner = new Scanner(System.in);
        System.out.println("what is the id cat you want to update ?");
        long id = scanner.nextLong();
        System.out.println("what is the name to update ?");
        String name = scanner.next();
        System.out.println("what is the age to update ?");
        int age = scanner.nextInt();
        catDAL.update(new Cat(id, name, age));
    }
}
