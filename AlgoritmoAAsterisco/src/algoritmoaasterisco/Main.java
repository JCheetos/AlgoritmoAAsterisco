/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmoaasterisco;

import java.util.Scanner;

/**
 *
 * @author Cheetos-Squeak y Juan Ostos
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Modelo m = new Modelo();
        m.AlmacenarMatrizAd();
        m.AlmacenarHeuristica();
        System.out.println("Escriba ciudad de origen: ");
        String ciudad = s.nextLine();
        ciudad.toLowerCase();
        if (m.primerciudad(ciudad) == false) {
            System.out.println("Ciudad invalida");
        } else {
            m.CalculoRuta();
            System.out.print(m.ruta);
        }
    }
}
