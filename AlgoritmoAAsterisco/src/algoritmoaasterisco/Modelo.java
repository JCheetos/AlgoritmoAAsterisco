/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmoaasterisco;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Cheetos
 */
public class Modelo {

    String Heuristica[] = new String[21];
    String FilasM[] = new String[21];
    String MatrizAdy[][] = new String[21][21];
    ArrayList<String> ruta = new ArrayList();
    String ciudades[] = new String[21];
    int cont;

    public void AlmacenarMatrizAd() {
        this.cont = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader("matriz.txt"));

            String bread;
            try {
                while ((bread = br.readLine()) != null) {
                    FilasM[this.cont] = bread;
                    this.cont++;
                }
            } catch (IOException e) {
            }
        } catch (FileNotFoundException e) {
        }

        this.cont = 0;
        int ncont = 0;
        String datos = "";
        for (int i = 0; i < FilasM.length; i++) {
            for (int j = 0; j < FilasM[i].length(); j++) {
                if (FilasM[i].charAt(j) == ' ') {
                    this.cont++;
                } else {
                    datos = datos + FilasM[i].charAt(j);
                }

                MatrizAdy[i][this.cont] = datos;
                if (ncont < this.cont) {
                    datos = "";
                    ncont++;
                }
            }
            datos = "";
            ncont = 0;
            this.cont = 0;
        }
    }

    public void AlmacenarHeuristica() {
        this.cont = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader("heuristica.txt"));
            String bread;
            try {
                while ((bread = br.readLine()) != null) {
                    Heuristica[this.cont] = bread;
                    this.cont++;
                }
            } catch (IOException e) {
            }
        } catch (FileNotFoundException e) {
        }
    }

    public boolean primerciudad(String Inicial) {
        boolean verificar = true;
        for (int i = 0; i < FilasM.length; i++) {
            ciudades[i] = MatrizAdy[i][0];
        }
        this.cont = 0;
        while (Inicial.equals(ciudades[this.cont]) == false) {
            this.cont++;
            if (this.cont >= ciudades.length) {
                verificar = false;
                break;
            }

        }
        return verificar;
    }

    public void CalculoRuta() {
        int c = this.cont;
        if (c == 2) {
            ruta.add(ciudades[c]);
        } else {
            int menor;
            int csel;
            boolean pasado = false;

            ruta.add(ciudades[c]);
            do {
                csel = 0;
                menor = 2000;

                for (int i = 1; i < ciudades.length; i++) {
                    if (Integer.parseInt(MatrizAdy[i][c]) != 0) {
                        if (i == 2) {
                            csel = i;
                            break;
                        } else {
                            int valor = Integer.parseInt(MatrizAdy[i][c]) + Integer.parseInt(Heuristica[i]);
                            if (menor >= valor) {
                                for (int j = 0; j < ruta.size(); j++) {
                                    if (ciudades[i].equals(ruta.get(j))) {
                                        pasado = true;
                                    }
                                }
                                if (pasado == false) {
                                    menor = valor;
                                    csel = i;
                                } else {
                                    pasado = false;
                                }
                            }

                        }
                    }
                }
                c = csel;
                ruta.add(ciudades[csel]);
            } while (csel != 2);
        }
    }
}
