package servicios;

import entidades.Enemigo;
import entidades.TipoEnemigo;

import java.util.ArrayList;

public class Sala {

    private int numeroSala;
    private ArrayList<Enemigo> enemigos;
    private boolean completada;

    public Sala(int numeroSala) {
        this.enemigos = new ArrayList<>();
        this.numeroSala = numeroSala;
        this.generarEnemigos();
    }

    /**
     * En función del número de sala (1-5) va a generar unos enemigos
     * de forma aleatoria.
     */
    private void generarEnemigos() {
        int aleatorio=0;
        switch (numeroSala) {
            case 1,2 -> {
                //2 o 3 Goblins
                aleatorio = (int) (Math.random() * 2);
                if (aleatorio == 1) {
                    Enemigo gob1 = new Enemigo("Goblin 1", TipoEnemigo.GOBLIN);
                    Enemigo gob2 = new Enemigo("Goblin 2", TipoEnemigo.GOBLIN);
                    enemigos.add(gob1);
                    enemigos.add(gob2);
                } else {
                    Enemigo gob1 = new Enemigo("Goblin 1", TipoEnemigo.GOBLIN);
                    Enemigo gob2 = new Enemigo("Goblin 2", TipoEnemigo.GOBLIN);
                    Enemigo gob3 = new Enemigo("Goblin 3", TipoEnemigo.GOBLIN);
                    enemigos.add(gob1);
                    enemigos.add(gob2);
                    enemigos.add(gob3);
                }
            }
            case 3,4 -> {
                //1 o 2 Orcos y un Goblin
                aleatorio = (int) (Math.random() * 2);
                if (aleatorio == 1) {
                    Enemigo orco1 = new Enemigo("Orco 1", TipoEnemigo.ORCO);
                    Enemigo goblin = new Enemigo("Goblin", TipoEnemigo.GOBLIN);
                    enemigos.add(goblin);
                    enemigos.add(orco1);
                } else {
                    Enemigo orco1 = new Enemigo("Orco 1", TipoEnemigo.ORCO);
                    Enemigo orco2 = new Enemigo("Orco 2", TipoEnemigo.ORCO);
                    Enemigo goblin = new Enemigo("Goblin", TipoEnemigo.GOBLIN);
                    enemigos.add(orco1);
                    enemigos.add(goblin);
                    enemigos.add(orco2);
                }
            }
            case 5 -> {
                //1 Dragón y 2 Orcos
                Enemigo dragon = new Enemigo("Dragon", TipoEnemigo.DRAGON);
                Enemigo orco1 = new Enemigo("Orco 1", TipoEnemigo.ORCO);
                Enemigo orco2 = new Enemigo("Orco 2", TipoEnemigo.ORCO);
                enemigos.add(orco1);
                enemigos.add(orco2);
                enemigos.add(dragon);
            }
        }
    }

    /**
     * Si queda algún enemigo vivo en la sala devuelve false, sino true.
     * @return boolean
     */
    public boolean todosEnemigosmuertos() {
        for(Enemigo e : enemigos) {
            if(e.estaVivo()) return false;
        }
        return true;
    }

    public int getNumeroSala() {
        return numeroSala;
    }

    public ArrayList<Enemigo> getEnemigos() {
        return enemigos;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }

    /**
     * Devuelve una lista con los enemigos vivos.
     * @return ArrayList<Enemigo>
     */
    public ArrayList<Enemigo> getEnemigosVivos() {
        ArrayList<Enemigo> enemigosVivos = new ArrayList<>();
        for(Enemigo e : enemigos) {
            if(e.estaVivo()) enemigosVivos.add(e);
        }
        return enemigosVivos;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Sala: " + numeroSala + "\n");
        for(Enemigo e : enemigos) {
            sb.append(e.getNombre())
                    .append(" -> ")
                    .append(e.getPuntosVidaActual())
                    .append(" vida.\n");
        }

        return sb.toString();
    }
}
