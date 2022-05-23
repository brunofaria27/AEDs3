import java.util.ArrayList;
import java.io.File;
import java.io.RandomAccessFile;

public class OrdenacaoExterna {
  private RandomAccessFile arq;
  private final String nomeArquivoIndex = "dados/index/clubes_index.db";
  private final String arqTemp1 = "dados/ordenacao/arqTemp1";
  private final String arqTemp2 = "dados/ordenacao/arqTemp2";
  private final String arqTemp3 = "dados/ordenacao/arqTemp3";
  private final String arqTemp4 = "dados/ordenacao/arqTemp4";

  public OrdenacaoExterna() {
    try {
      boolean exists_temp1 = (new File(arqTemp1)).exists();
      boolean exists_temp2 = (new File(arqTemp2)).exists();
      boolean exists_temp3 = (new File(arqTemp2)).exists();
      boolean exists_temp4 = (new File(arqTemp3)).exists();

      if (exists_temp1 == true && exists_temp2 == true && exists_temp3 == true && exists_temp4 == true) {
        // Arquivo Existe
      } else {
        try {
          arq = new RandomAccessFile(arqTemp1, "rw"); // Cria o arquivo caso não exista
          arq = new RandomAccessFile(arqTemp2, "rw");
          arq = new RandomAccessFile(arqTemp3, "rw");
          arq = new RandomAccessFile(arqTemp4, "rw");
          arq.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public ArrayList<String> pegarChavesArq() {
    ArrayList<String> arquivoIndexado = new ArrayList<>();

    try {
      arq = new RandomAccessFile(nomeArquivoIndex, "rw");

      long pos;
      byte id;
      long endereco;

      while (arq.getFilePointer() < arq.length()) {
        pos = arq.getFilePointer();
        String arqBuilder = "";
        if (arq.readByte() != -1) {
          arq.seek(pos);
          id = arq.readByte();
          endereco = arq.readLong();
          arqBuilder = id + " " + endereco;
          arquivoIndexado.add(arqBuilder);
        } else {
          arq.readLong();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return arquivoIndexado;
  }

  public void intercalacaoBalanceada(int tamanhoBloco, int caminhos) {
    // System.out.println(pegarChavesArq()); -> debuguer pegar as chaves
    ArrayList<String> chaves = new ArrayList<>(); // Cria o array com todas as chaves
    chaves = pegarChavesArq();

  }

  // Utilizar um metodo sort para ordenar 10 itens em memoria principal e trabalhar em cima disso para
  // ordenar os arquivos em memoria secundaria
}