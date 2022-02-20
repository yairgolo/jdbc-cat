package com.company;

import com.company.dal.CatDAL;
import com.company.model.Cat;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        boolean isRunning = true;
        while (isRunning) {
            printMenu();
            int cmd = new Scanner(System.in).nextInt();
            switch (cmd) {
                case 1:
                    addCat();
                    break;

                case 2:
                    readcat();
                    break;

                case 3:
                    deleteCat();
                    break;

                case 4:
                    updateCat();
                    break;

                case 5:
                    getAllCats();
                    break;

                default:
                    isRunning = false;
            }
        }
    }

    public static void printMenu() {
        System.out.println(
                "1. add new cat\n" +
                "2. get a cat\n" +
                "3. delete a cat\n" +
                "4. update a cat\n" +
                "5. read all cats\n" +
                "6. exit");
    }

    public static void addCat() {
        CatDAL catDAL = CatDAL.instance;
        System.out.print("please enter a name: ");
        String name = new Scanner(System.in).next();
        System.out.print("please enter a age: ");
        int age = new Scanner(System.in).nextInt();
        Cat cat = new Cat(name, age);
        catDAL.create(cat);
    }

    public static void readcat() {
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
    public static void deleteCat(){
        CatDAL catDAL = CatDAL.instance;
        System.out.print("please enter a id to delete: ");
        long id = new Scanner(System.in).nextLong();
        catDAL.delete(id);
    }
    public static void getAllCats(){
        CatDAL catDAL = CatDAL.instance;
        for (Cat cat : catDAL.readAll()) {
            System.out.println(cat);
        }
    }
    public static void updateCat(){
        CatDAL catDAL = CatDAL.instance;
        Scanner scanner = new Scanner(System.in);
        System.out.println("what is the id cat you want to update ?");
        long id = scanner.nextLong();
        System.out.println("what is the name to update ?");
        String name = scanner.next();
        System.out.println("what is the name to update ?");
        int age = scanner.nextInt();
        catDAL.update(new Cat(id, name, age));
    }
}
