package entidades;

public class Armadura {

    private String nombre;
    private TipoArmadura tipo;
    private int bonusDefensa;
    private int bonusAtaque;

    public Armadura(String nombre, TipoArmadura tipo, int bonusDefensa, int bonusAtaque) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.bonusDefensa = bonusDefensa;
        this.bonusAtaque = bonusAtaque;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoArmadura getTipo() {
        return tipo;
    }

    public void setTipo(TipoArmadura tipo) {
        this.tipo = tipo;
    }

    public int getBonusDefensa() {
        return bonusDefensa;
    }

    public void setBonusDefensa(int bonusDefensa) {
        this.bonusDefensa = bonusDefensa;
    }

    public int getBonusAtaque() {
        return bonusAtaque;
    }

    public void setBonusAtaque(int bonusAtaque) {
        this.bonusAtaque = bonusAtaque;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Armadura{");
        sb.append(" '").append(nombre).append('\'');
        sb.append(", tipo=").append(tipo);
        sb.append(", def=").append(bonusDefensa);
        sb.append(", atq=").append(bonusAtaque);
        sb.append('}');
        return sb.toString();
    }
}
