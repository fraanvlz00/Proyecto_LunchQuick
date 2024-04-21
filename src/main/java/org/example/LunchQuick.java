package org.example;

import java.util.Scanner;

public class LunchQuick {

    public static String [][] almuerzos = {{"Pollo con pure, ensalada, bebestibles, postre, pan, sopa","pollo con papas,bebestible, postre", "salteado de verduras con papas, bebestible, ensalada, postre"},
            {"carne con arroz, bebestible, ensalada, postre, pan, sopa", "carne con arroz, bebestible, ensalada, postre, pan, sopa", "zapallo con arroz, bebestible, ensalada, postre, pan, sopa"},
            {"cazuela, bebestible, ensalada, postre, pan, sopa", "cazuela, bebestible, postre", "cazuela de verduras, bebestible, ensalada, postre, pan, sopa"},
            {"pollo con papas fritas, bebestible, ensalada, postre, pan, sopa", "pollo con papas fritas, bebestible, postre", "hamburguesa de verduras con papas fritas, bebestible, ensalada, postre, pan sopa"},
            {"porotos, bebestible, ensalada, postre, pan, sopa", "porotos, bebestibles, postre", "porotos, bebestible, ensalada, postre, pan, sopa"}};


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
        System.out.println("1.Menu ejecutivo: ");
        System.out.println("2.Menu normal: ");
        System.out.println("3.Menu baes: ");
        System.out.println("4.Menu vegetariano");
    }

    public static void seleccionMenu() {
        do {
            menu();
            int opcion = sc.nextInt();
            switch (opcion) {
                case 1:
                    menu2();
                    break;
                case 2:
                    menu2();
                    break;
                case 3:
                    menu2();
                    break;
                case 4:
                    menu2();
                    break;
                case 5:
                    menu2();
                    break;
            }while (6 > opcion && opcion> 0);
        }}
    public static void elegirMenu2(){
        do {
            int opcionMenu2 = sc.nextInt();
            switch (opcionMenu2){
                case 1:

                case 2:

                case 3:

                case 4:


            }while (0 < opcionMenu2 && opcionMenu2 < 4 );
        }}
    public static void menuSeleccionado(){

    }


    }
