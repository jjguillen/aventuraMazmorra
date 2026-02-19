package servicios;

import entidades.Enemigo;
import entidades.Heroe;

import java.util.ArrayList;

public class Combate {

    private ArrayList<Heroe> heroes;
    private Sala sala;
    private int turno;

    public Combate(ArrayList<Heroe> heroes, Sala sala) {
        this.heroes = heroes;
        this.sala = sala;
        this.turno = 1;
    }

    public void iniciarCombate() {
        while(!combateTerminado()) {
            mostrarEstadoCombate();
            IO.println("--- TURNO " + turno + " ---");
            //Turno héroes
            turnoHeroes();
            //Turno enemigos
            turnoEnemigos();

            turno++;
        }
        IO.println("--- FIN DEL COMBATE ---");
        mostrarEstadoCombate();

        //Repartir experiencia
        IO.println("--- RECOMPENSAS ---");
        distribuirRecompensas();
    }

    /**
     * Indica si el combate ha terminado.
     * Si todos los héroes están muertos O todos los enemigos de la sala
     * @return
     */
    public boolean combateTerminado() {
        boolean salaSinEnemigos = sala.todosEnemigosmuertos();
        boolean heroesMuertos = true;
        for(Heroe h : heroes) {
            if (h.estaVivo()) {
                heroesMuertos = false;
                break;
            }
        }

        return salaSinEnemigos || heroesMuertos;
    }

    /**
     * Muestra el estado actual del combate.
     */
    public void mostrarEstadoCombate() {
        IO.println("--- ENEMIGOS ---");
        ArrayList<Enemigo> enemigosVivos = sala.getEnemigosVivos();
        if (enemigosVivos.isEmpty()) {
            IO.println("No hay enemigos vivos.");
        } else {
            for (Enemigo e : enemigosVivos) {
                IO.println(e.getNombre() + " (atq: " + e.getAtaque() +
            ") -> " + e.getPuntosVidaActual());
            }
        }
        IO.println("--- HÉROES ---");
        for (Heroe h : heroes) {
            IO.println(h.getNombre() + " -> " + h.getPuntosVidaActual());
        }
    }

    /**
     * Cada héroe vivo ataca al primer enemigo vivo.
     */
    public void turnoHeroes() {
        for(Heroe h : heroes) {
            if (h.estaVivo()) {
                if (!this.sala.getEnemigosVivos().isEmpty()) {
                    IO.println("[Combate - turnoHeroes] - " + h.getNombre() +
                            " ha atacado a " + this.sala.getEnemigosVivos().getFirst().getNombre());

                    h.atacar(this.sala.getEnemigosVivos().getFirst());
                }
            }
        }
    }

    /**
     * Cada enemigo vivo ataca a un héroe aleatorio
     */
    public void turnoEnemigos() {
        int aleatorio=0;
        for(Enemigo e : sala.getEnemigosVivos()) {
            IO.println("[Combate - turnoEnemigos] - Buscando héroe vivo para atacar ... ");
            do {
                aleatorio = (int) (Math.random() * heroes.size());
            } while (!heroes.get(aleatorio).estaVivo() && quedanHeroes());

            if (!quedanHeroes()) break;

            e.atacar(heroes.get(aleatorio));
            IO.println("[Combate - turnoEnemigos] - " + e.getNombre() +
                    " ha atacado a " + heroes.get(aleatorio).getNombre());
        }
    }

    /**
     * Si ganan, reparte experiencia entre héroes vivos
     */
    public void distribuirRecompensas() {
          if (sala.todosEnemigosmuertos()) {
              //Calcular la recompensa de los enemigos muertos
              int experiencia = 0;
              for(Enemigo e: sala.getEnemigos()) {
                  experiencia += e.getExpOtorgada();
              }
              //Repartir la experiencia entre los héroes vivos
              //Contar número héroes vivos
              int heroesVivos = 0;
              for(Heroe h : heroes) {
                  if (h.estaVivo())
                      heroesVivos++;
              }
              //Calcular la experiencia a repartir
              int expPorHeroe = experiencia / heroesVivos;
              IO.println("Experiencia otorgada: " + experiencia);

              //Asignamos la experiencia a cada héroe vivo
              for(Heroe h : heroes) {
                  if (h.estaVivo())
                      h.ganarExperiencia(expPorHeroe);
              }
          }
    }

    private boolean quedanHeroes() {
        for(Heroe h : this.heroes) {
            if (h.estaVivo()) {
                return true;
            }
        }
        return false;
    }

}
