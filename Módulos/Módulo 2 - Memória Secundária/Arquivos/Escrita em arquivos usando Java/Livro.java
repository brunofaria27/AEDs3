import java.text.DecimalFormat;

public class Livro {
    protected int idLivro;
    protected String titulo;
    protected String autor;
    protected float preco;

    DecimalFormat df = new DecimalFormat("#,##0.00");

    public Livro(int i, String t, String a, float p) {
        idLivro = i;
        titulo = t;
        autor = a;
        preco = p;
    }

    public String toString() {
        return "\nID: " + idLivro +
                "\nTítulo: " + titulo +
                "\nAutor: " + autor +
                "\nPreço: R$ " + df.format(preco);
    }

}