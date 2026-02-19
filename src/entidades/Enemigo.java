package entidades;

import java.util.List;
import java.util.Objects;

public class Enemigo extends Personaje {

    private TipoEnemigo tipo;
    private int expOtorgada;

    public Enemigo(String nombre, TipoEnemigo tipo) {
        super(nombre, 0, 0, 0);
        this.tipo = tipo;
        switch (tipo) {
            case GOBLIN -> {
                this.setPuntosVidaMax(30);
                this.setPuntosVidaActual(30);
                this.setAtaque(12); //8
                this.setDefensa(5);
                this.expOtorgada = 20;
            }
            case ORCO -> {
                this.setPuntosVidaMax(60);
                this.setPuntosVidaActual(60);
                this.setAtaque(20); //15
                this.setDefensa(10);
                this.expOtorgada = 40;
            }
            case DRAGON -> {
                this.setPuntosVidaMax(150);
                this.setPuntosVidaActual(150);
                this.setAtaque(30); //25
                this.setDefensa(15);
                this.expOtorgada = 100;
            }
        }
    }


    public TipoEnemigo getTipo() {
        return tipo;
    }

    public int getExpOtorgada() {
        return expOtorgada;
    }

    @Override
    public void usarHabilidadEspecial(Personaje objetivo, List<Personaje> objetivosCercanos) {
        switch (this.tipo) {
            case GOBLIN -> {
                // "Golpe Rápido" - ataca dos veces seguidas con daño reducido
                this.atacar(objetivo);
                this.atacar(objetivo);
            }
            case ORCO -> {
                int ataqueOriginal = this.ataque;
                this.ataque += 5;

                // Ataca con el ataque aumentado
                this.atacar(objetivo);

                // Vuelve a su ataque normal
                this.ataque = ataqueOriginal;
            }
            case DRAGON -> {
                this.atacar(objetivo);
                for (Personaje p : objetivosCercanos) {
                    this.atacar(p);
                }
            }
        }

    }

}
