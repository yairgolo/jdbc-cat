package com.company;

import com.company.facade.CatFacade;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        CatFacade catFacade = CatFacade.instance;
        boolean isRunning = true;
        while (isRunning) {
            printMenu();
            int cmd = new Scanner(System.in).nextInt();
            switch (cmd) {
                case 1:
                    catFacade.addCat();
                    break;

                case 2:
                    catFacade.readcat();
                    break;

                case 3:
                    catFacade.deleteCat();
                    break;

                case 4:
                    catFacade.updateCat();
                    break;

                case 5:
                    catFacade.getAllCats();
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
}
