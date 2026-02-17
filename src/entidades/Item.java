package entidades;

public class Item {

    private String nombre;
    private TipoItem tipo;
    private int valorCuracion;

    public Item(String nombre, TipoItem tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
        switch (tipo) {
            case POCION_PEQUENA -> this.valorCuracion = 30;
            case POCION_GRANDE -> this.valorCuracion = 60;
            case ELIXIR -> this.valorCuracion = 1000000;
        }
    }

    public String getNombre() {
        return nombre;
    }

    public TipoItem getTipo() {
        return tipo;
    }

    public int getValorCuracion() {
        return valorCuracion;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Item{");
        sb.append("nombre='").append(nombre).append('\'');
        sb.append(", tipo=").append(tipo);
        sb.append(", valorCuracion=").append(valorCuracion);
        sb.append('}');
        return sb.toString();
    }

    /**
     * Usa el item para curar al héroe. El valor de curación se aplica a los puntos de vida actuales del héroe.
     * @param heroe
     */
    public void usar(Heroe heroe) {
        heroe.curar(this.valorCuracion);
    }
}
