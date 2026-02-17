package entidades;

import java.util.List;

public abstract class Personaje {

    protected String nombre;
    protected int puntosVidaMax;
    protected int puntosVidaActual;
    protected int ataque;
    protected int defensa;
    protected boolean vivo;

    public Personaje(String nombre, int puntosVidaMax, int ataque, int defensa) {
        this.nombre = nombre;
        this.puntosVidaMax = puntosVidaMax;
        this.puntosVidaActual = puntosVidaMax;
        this.ataque = ataque;
        this.defensa = defensa;
        this.vivo = false;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntosVidaMax() {
        return puntosVidaMax;
    }

    public void setPuntosVidaMax(int puntosVidaMax) {
        this.puntosVidaMax = puntosVidaMax;
    }

    public int getPuntosVidaActual() {
        return puntosVidaActual;
    }

    public void setPuntosVidaActual(int puntosVidaActual) {
        this.puntosVidaActual = puntosVidaActual;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Personaje{");
        sb.append("nombre='").append(nombre).append('\'');
        sb.append(", puntosVidaMax=").append(puntosVidaMax);
        sb.append(", puntosVidaActual=").append(puntosVidaActual);
        sb.append(", ataque=").append(ataque);
        sb.append(", defensa=").append(defensa);
        sb.append(", vivo=").append(vivo);
        sb.append('}');
        return sb.toString();
    }

    /**
     * MÉTODOS ---------------------------------------------------
     */

    /**
     * Metodo abstracto para usar la habilidad especial del personaje.
     * @param objetivo
     */
    public abstract void usarHabilidadEspecial(Personaje objetivo, List<Personaje> objetivosCercanos);

    /**
     * Metodo para atacar a otro personaje, calculando el daño basado en el ataque del atacante y la defensa del objetivo.
     * @param objetivo
     */
    public void atacar(Personaje objetivo) {
       int damage = this.ataque - objetivo.getDefensa();
       if (damage > 1) {
           objetivo.recibirDanio(damage);
       }
    }

    /**
     * Metodo para recibir daño, reduciendo los puntos de vida actuales y verificando si el personaje sigue vivo.
     * @param danio
     */
    public void recibirDanio(int danio) {
        this.setPuntosVidaActual(this.getPuntosVidaActual() - danio);
        if (this.getPuntosVidaActual() <= 0) {
            this.setVivo(false);
        }
    }

    /**
     * Metodo para curar al personaje, aumentando los puntos de vida actuales sin exceder el máximo.
     * @param puntos
     */
    public void curar(int puntos) {
        this.setPuntosVidaActual(Math.min(this.getPuntosVidaMax(), puntos + this.getPuntosVidaActual()));
    }


    /**
     * Metodo para verificar si el personaje está vivo, basado en sus puntos de vida actuales.
     * @return
     */
    public boolean estaVivo() {
      return this.vivo;
    }

}
