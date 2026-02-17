package servicios;

import entidades.Heroe;
import entidades.Item;
import entidades.TipoHeroe;
import entidades.TipoItem;

import java.util.ArrayList;

public class Juego {

    private ArrayList<Heroe> equipo; // (los 3 héroes del jugador)
    private ArrayList<Sala> salas; // (las 5 salas de la mazmorra)
    private int salaActual; // (1-5)
    private boolean juegoTerminado;

    public Juego() {
        this.equipo = new ArrayList<>();
        this.salas = new ArrayList<>();
        this.salaActual = 1;
        this.juegoTerminado = false;
    }

    /**
     * Crear las 5 salas
     * Dejar que el jugador elija sus 3 héroes (o crear equipo predefinido)
     * Darles algunas pociones iniciales
     */
    public void inicializarJuego() {
        //Salas
        for(int i=1; i<=5; i++) {
            salas.add(new Sala(i));
        }
        //Equipo predefinido con una poción cada uno (luego podemos añadir más)
        Heroe h1 = new Heroe("Gandalf", TipoHeroe.MAGO);
        h1.agregarItem(new Item("Poción de Bombadil", TipoItem.POCION_GRANDE));
        Heroe h2 = new Heroe("Legolas", TipoHeroe.ARQUERO);
        h2.agregarItem(new Item("Poción Arundilo", TipoItem.POCION_PEQUENA));
        Heroe h3 = new Heroe("Aragorn", TipoHeroe.GUERRERO);
        h3.agregarItem(new Item("Elixir de Vida", TipoItem.ELIXIR));
        equipo.add(h1);
        equipo.add(h2);
        equipo.add(h3);
    }

    /**
     * Mientras no esté terminado:
     * 1. Mostrar menú entre salas (si no es la primera)
     * 2. Entrar en la sala actual
     * 3. Iniciar combate
     * 4. Verificar resultado (victoria/derrota)
     * 5. Avanzar a siguiente sala
     */
    public void jugar() {
        int opcion=0;
        Sala sala = null;

        IO.println("--- JUEGO DE HEROES ---");

        while (!juegoTerminado) {

            do {
                menuEntreSalas();
                IO.println("Introduce opción");
                opcion = Integer.parseInt(IO.readln());
                switch (opcion) {
                    case 1 -> {
                        IO.println(equipo);
                    }
                    case 2 -> {
                        for (Heroe h : equipo) {
                            if (h.getInventario().isEmpty()) continue;
                            h.usarItem(h.getInventario().get(0));
                            h.getInventario().remove(0);
                        }
                    }
                    case 3 -> {
                        for (Heroe h : equipo) {
                            h.curar(20);
                        }
                    }
                    case 4 -> {
                        IO.println("A por la siguiente sala...");
                    }
                    default -> IO.println("Opción incorrecta (1-4)");
                }
            } while (opcion != 4);

            //Entrar en sala actual
            sala = buscarSala(salaActual);
            IO.println("--- SALA " + salaActual + " ---");

            //Iniciar combate
            Combate combate = new Combate(this.equipo, sala);
            combate.iniciarCombate();

            //Verificar resultado y si corresponde avanzar a la siguiente sala
            if (this.heroesVivos()) {
                if (salaActual < 5) {
                    IO.println("--- SALA " + salaActual + " TERMINADA ---");
                    salaActual++;
                    sala = buscarSala(salaActual);
                } else {
                    juegoTerminado = true;
                }
            } else {
                juegoTerminado = true;
            }
        }

        mostrarResultadoFinal();
    }


    /**
     * Pintar las opciones entre salas
     */
    private void menuEntreSalas() {
        IO.println("--- OPCIONES ---");
        IO.println("1. Ver equipo");
        IO.println("2. Usar pociones");
        IO.println("3. Descansar");
        IO.println("4. Continuar");
    }

    /**
     * Método que determina si queda algún héroe vivo en el equipo.
     * @return
     */
    private boolean heroesVivos() {
        for(Heroe h : this.equipo) {
            if (h.estaVivo()) {
               return true;
            }
        }
        return false;
    }

    /**
     * Buscar sala por número de sala
     * @param numeroSala
     * @return
     */
    private Sala buscarSala(int numeroSala) {
        for (Sala s : salas) {
            if (s.getNumeroSala() == numeroSala) return s;
        }
        return null;
    }


    /**
     * Una vez terminado el juego, mostrar el resultado final.
     */
    public void mostrarResultadoFinal() {
        IO.println("--- RESULTADO FINAL ---");
        if (this.heroesVivos()) {
            IO.println("--- FIN DEL JUEGO ---");
            IO.println("--- HÉROES GANAN ---");
            IO.println("--- Estado Equipo ---");
            IO.println(equipo);
            IO.println("--- Todas las salas completadas ---");
        } else {
            IO.println("--- FIN DEL JUEGO ---");
            IO.println("--- ENEMIGOS GANAN ---");
            IO.println("--- Estado Equipo ---");
            IO.println(equipo);
            IO.println("--- Sala donde quedaron ---");
            IO.println(salaActual);
        }
    }

}
