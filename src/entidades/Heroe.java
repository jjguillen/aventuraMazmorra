package entidades;

import java.util.ArrayList;
import java.util.List;

public class Heroe extends Personaje {

    private TipoHeroe tipoHeroe;
    private Integer nivel;
    private Integer experiencia;
    private ArrayList<Item> inventario;
    // Equipo
    private Armadura casco;
    private Armadura cota;
    private Armadura grebas;
    private Armadura guanteletes;
    private Armadura escudo;

    public Heroe(String nombre, TipoHeroe tipoHeroe) {
        super(nombre, 0,0,0);
        this.nivel = 1;
        this.experiencia = 0;
        this.tipoHeroe = tipoHeroe;
        this.inventario = new ArrayList<>();

        switch (tipoHeroe) {
            case GUERRERO:
                this.setPuntosVidaMax(100);
                this.setPuntosVidaActual(100);
                this.setAtaque(20);
                this.setDefensa(15);
                break;
            case MAGO:
                this.setPuntosVidaMax(60);
                this.setPuntosVidaActual(60);
                this.setAtaque(30);
                this.setDefensa(5);
                break;
            case ARQUERO:
                this.setPuntosVidaMax(80);
                this.setPuntosVidaActual(80);
                this.setAtaque(25);
                this.setDefensa(10);
                break;
        }

    }

    public TipoHeroe getTipoHeroe() {
        return tipoHeroe;
    }

    public Integer getNivel() {
        return nivel;
    }

    public Integer getExperiencia() {
        return experiencia;
    }

    public ArrayList<Item> getInventario() {
        return inventario;
    }

    /**
     *  MÉTODOS -----------------------------------------------
     */

    @Override
    public void usarHabilidadEspecial(Personaje objetivo, List<Personaje> objetivosCercanos) {
        // Lógica para usar la habilidad especial del héroe
        switch (tipoHeroe) {
            case GUERRERO -> {
                // Lógica para la habilidad especial del guerrero
                // "Golpe Poderoso" - hace el doble de daño
                this.atacar(objetivo);
                this.atacar(objetivo);
            }
            case MAGO -> {
                // Lógica para la habilidad especial del mago
                // "Bola de Fuego" - hace daño a todos los enemigos (se le
                //pasa una lista)
                this.atacar(objetivo);
                for (Personaje p : objetivosCercanos) {
                    this.atacar(p);
                }
            }
            case ARQUERO -> {
                // Lógica para la habilidad especial del arquero
                // "Disparo Preciso" - ignora la defensa del enemigo
                objetivo.recibirDanio(this.getAtaque());
            }
        }

    }

    /**
     * Gana experiencia y sube de nivel si es necesario.
     * @param exp
     */
    public void ganarExperiencia(int exp) {
        this.experiencia += exp;
        if (this.experiencia >= nivel * 100) {
            this.subirNivel();
        }
    }

    /**
     * Sube de nivel, aumentando las estadísticas del héroe.
     */
    public void subirNivel() {
        this.nivel++;
        this.experiencia = 0;
        this.setPuntosVidaActual(Math.min(this.getPuntosVidaMax(), 20 + this.getPuntosVidaActual()));
        this.setAtaque(this.getAtaque() + 5);
        this.setDefensa(this.getDefensa() + 3);
    }


    /**
     * Usa un item del inventario.
     * @param item
     */
    public void usarItem(Item item) {
        // Lógica para usar un item del inventario
        item.usar(this);
    }

    /**
     * Agrega un item al inventario.
     * @param item
     */
    public void agregarItem(Item item) {
        this.inventario.add(item);
    }

    /**
     * Equipa una armadura a un héroe
     * Si ya llevaba en esa posición primero le quita los bonus
     * Luego le pone la nueva pieza de armadura y le aplica los bonus nuevos
     * @param armadura
     */
    public void equiparArmadura(Armadura armadura) {

        switch (armadura.getTipo()) {

            case CASCO:
                quitarBonus(this.casco);
                this.casco = armadura;
                break;

            case COTA:
                quitarBonus(this.cota);
                this.cota = armadura;
                break;

            case GREBAS:
                quitarBonus(this.grebas);
                this.grebas = armadura;
                break;

            case GUANTELETES:
                quitarBonus(this.guanteletes);
                this.guanteletes = armadura;
                break;

            case ESCUDO:
                quitarBonus(this.escudo);
                this.escudo = armadura;
                break;
        }

        aplicarBonus(armadura);

        System.out.println(nombre + " ha equipado " + armadura.getNombre());
    }

    private void aplicarBonus(Armadura armadura) {
        if (armadura != null) {
            this.defensa += armadura.getBonusDefensa();
            this.ataque += armadura.getBonusAtaque();
        }
    }

    private void quitarBonus(Armadura armadura) {
        if (armadura != null) {
            this.defensa -= armadura.getBonusDefensa();
            this.ataque -= armadura.getBonusAtaque();
        }
    }



}
