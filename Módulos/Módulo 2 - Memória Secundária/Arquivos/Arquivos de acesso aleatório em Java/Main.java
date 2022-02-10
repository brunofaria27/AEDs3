import java.io.RandomAccessFile;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Livro l1 = new Livro(1, "Eu, Robô", "Isaac Asimov", 14.90F);
        Livro l2 = new Livro(2, "Eu Sou a Lenda", "Richard Matheson", 21.99F);

        RandomAccessFile arq;
        byte[] ba;

        try {

            // ESCRITA
            arq = new RandomAccessFile("dados/livros.db", "rw");

            long pos1 = arq.getFilePointer();   // Pegar posicao do primeiro livro
            ba = l1.toByteArray();
            arq.writeInt(ba.length);
            arq.write(ba);

            long pos2 = arq.getFilePointer();   // Pegar posicao do segundo livro
            ba = l2.toByteArray();
            arq.writeInt(ba.length);
            arq.write(ba);

            // LEITURA
            Livro l3 = new Livro();
            Livro l4 = new Livro();
            int tam;

            arq.seek(pos1);         // Colocar o ponteiro no inicio do primeiro livro, para ler ele todo
            tam = arq.readInt();    // Pega o tamanho correto do livro
            ba = new byte[tam];
            arq.read(ba);           // Le tudo até o tamanho correto do livro
            l3.fromByteArray(ba);

            arq.seek(pos2);         // Colocar o ponteiro no inicio do segundo livro, para ler ele todo
            tam = arq.readInt();    // Pega o tamanho correto do livro
            ba = new byte[tam];
            arq.read(ba);           // Le tudo até o tamanho correto do livro
            l4.fromByteArray(ba);

            System.out.println(l3);
            System.out.println(l4);

            arq.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}