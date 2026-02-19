package app;

import servicios.Juego;

public class Principal {
    static void main() {

        Juego juego = new Juego();
        juego.inicializarJuego();
        juego.jugar();

    }

}
