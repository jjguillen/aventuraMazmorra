package servicios;

import entidades.Enemigo;
import entidades.Heroe;
import entidades.Personaje;

import java.util.ArrayList;
import java.util.List;

public class Combate {

    private List<Personaje> heroes;
    private Sala sala;
    private int turno;

    public Combate(List<Personaje> heroes, Sala sala) {
        this.heroes = heroes;
        this.sala = sala;
        this.turno = 1;
    }

    public void iniciarCombate() {
        while(!combateTerminado()) {
            mostrarEstadoCombate();
            IO.println("--- TURNO " + turno + " ---");
            //Turno héroes
            turnoHeroes(turno);
            //Turno enemigos
            turnoEnemigos(turno);

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
        for(Personaje h : heroes) {
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
        List<Personaje> enemigosVivos = sala.getEnemigosVivos();
        if (enemigosVivos.isEmpty()) {
            IO.println("No hay enemigos vivos.");
        } else {
            for (Personaje e : enemigosVivos) {
                IO.println(e.getNombre() + " (atq: " + e.getAtaque() +
            ") -> " + e.getPuntosVidaActual());
            }
        }
        IO.println("--- HÉROES ---");
        for (Personaje h : heroes) {
            IO.println(h.getNombre() + " -> " + h.getPuntosVidaActual());
        }
    }

    /**
     * Cada héroe vivo ataca al primer enemigo vivo.
     * GUERRERO usa "Golpe Poderoso" si:
     *      El enemigo tiene más del 50% de vida
     *      O es un DRAGON
     * MAGO usa "Bola de Fuego" si:
     *      Si es el tercer turno del combate
     * ARQUERO usa "Disparo Preciso" si:
     *      El enemigo tiene mucha defensa (por ejemplo ORCO o DRAGON)
     */
    public void turnoHeroes(int turno) {
        for(Personaje h : heroes) {
            if (h.estaVivo()) {
                if (!this.sala.getEnemigosVivos().isEmpty()) {
                    Enemigo enemigo = (Enemigo) this.sala.getEnemigosVivos().getFirst();

                    boolean especial = false;
                    switch (((Heroe) h).getTipoHeroe()) {
                        case GUERRERO -> {
                            if (enemigo.getPuntosVidaActual() > enemigo.getPuntosVidaMax() / 2 ||
                                    enemigo.getTipo() == entidades.TipoEnemigo.DRAGON) {
                                h.usarHabilidadEspecial(enemigo, sala.getEnemigosVivos());
                                especial = true;
                                IO.println("[Combate - turnoHeroes] - " + h.getNombre() +
                                        " ha usado su habilidad especial Golpe Poderoso contra " +
                                        enemigo.getNombre());
                            }
                        }
                        case MAGO -> {
                            if (turno == 3) {
                                h.usarHabilidadEspecial(enemigo, sala.getEnemigosVivos());
                                especial = true;
                                IO.println("[Combate - turnoHeroes] - " + h.getNombre() +
                                        " ha usado su habilidad especial Bola de Fuego contra " +
                                        enemigo.getNombre());
                            }
                        }
                        case ARQUERO -> {
                            if (enemigo.getDefensa() > 10) { //Ejemplo de defensa alta
                                h.usarHabilidadEspecial(enemigo, sala.getEnemigosVivos());
                                especial = true;
                                IO.println("[Combate - turnoHeroes] - " + h.getNombre() +
                                        " ha usado su habilidad especial Disparo Preciso contra " +
                                        enemigo.getNombre());
                            }
                        }
                    }

                    if (!especial) {
                        h.atacar(enemigo);
                        IO.println("[Combate - turnoHeroes] - " + h.getNombre() +
                                " ha atacado a " + enemigo.getNombre());
                    }

                }
            }
        }
    }

    /**
     * Cada enemigo vivo ataca a un héroe aleatorio
     * GOBLIN usa "Golpe Rápido" si:
     *      Su vida está por debajo del 50%
     * ORCO usa "Grito de Guerra" al inicio del combate
     * DRAGON usa "Aliento de Fuego" si:
     *      Está por debajo del 30% de vida (modo furia)
     */
    public void turnoEnemigos(int turno) {
        int aleatorio=0;
        for(Personaje p : sala.getEnemigosVivos()) {
            Enemigo e = (Enemigo) p;
            IO.println("[Combate - turnoEnemigos] - Buscando héroe vivo para atacar ... ");
            do {
                aleatorio = (int) (Math.random() * heroes.size());
            } while (!heroes.get(aleatorio).estaVivo() && quedanHeroes());

            if (!quedanHeroes()) break;

            boolean especial = false;
            switch(e.getTipo()) {
                case GOBLIN -> {
                    if (e.getPuntosVidaActual() < e.getPuntosVidaMax() / 2) {
                        e.usarHabilidadEspecial(heroes.get(aleatorio), heroes);
                        especial = true;
                        IO.println("[Combate - turnoEnemigos] - " + e.getNombre() +
                                " ha usado su habilidad especial Golpe Rápido contra " +
                                heroes.get(aleatorio).getNombre());
                    }
                }
                case ORCO -> {
                    if (turno == 1) {
                        e.usarHabilidadEspecial(heroes.get(aleatorio), heroes);
                        especial = true;
                        IO.println("[Combate - turnoEnemigos] - " + e.getNombre() +
                                " ha usado su habilidad especial Grito de Guerra contra " +
                                heroes.get(aleatorio).getNombre());
                    }
                }
                case DRAGON -> {
                    if (e.getPuntosVidaActual() < e.getPuntosVidaMax() * 0.3) {
                        e.usarHabilidadEspecial(heroes.get(aleatorio), heroes);
                        especial = true;
                        IO.println("[Combate - turnoEnemigos] - " + e.getNombre() +
                                " ha usado su habilidad especial Aliento de Fuego contra " +
                                heroes.get(aleatorio).getNombre());
                    }
                }
            }

            //Si no has usado habilidad especial, ataque normal
            if (!especial) {
                e.atacar(heroes.get(aleatorio));
                IO.println("[Combate - turnoEnemigos] - " + e.getNombre() +
                        " ha atacado a " + heroes.get(aleatorio).getNombre());
            }
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
              for(Personaje h : heroes) {
                  if (h.estaVivo())
                      heroesVivos++;
              }
              //Calcular la experiencia a repartir
              int expPorHeroe = experiencia / heroesVivos;
              IO.println("Experiencia otorgada: " + experiencia);

              //Asignamos la experiencia a cada héroe vivo
              for(Personaje h : heroes) {
                  if (h.estaVivo()) {
                      ((Heroe) h).ganarExperiencia(expPorHeroe);
                  }
              }
          }
    }

    private boolean quedanHeroes() {
        for(Personaje h : this.heroes) {
            if (h.estaVivo()) {
                return true;
            }
        }
        return false;
    }

}
