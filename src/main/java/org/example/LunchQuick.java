package org.example;

import java.util.Scanner;

public class LunchQuick {
    public static Scanner sc = new Scanner(System.in);

    public static void menu() {
        System.out.println("SELECCIONA UN DIA DE LA SEMANA");
        System.out.println("1.Lunes");
        System.out.println("2.Martes");
        System.out.println("3.Miercoles");
        System.out.println("4.Jueves");
        System.out.println("5.Viernes");
    }

    public static void menu2() {
        System.out.println("SELECCIONA UN MENU");
        System.out.println("1.Menu ejecutivo");
        System.out.println("2.Menu normal");
        System.out.println("3.Menu baes");
    }

    public static void seleccionMenu() {
        int opcion;

        do {
            opcion = sc.nextInt();
            switch (opcion) {
                case 1:


            }}
            while (opcion > 0 && opcion < 5);
            sc.close();



    }
}
