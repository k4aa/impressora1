import com.sun.jna.Library;
import com.sun.jna.Native;
import java.util.Scanner;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.io.FileInputStream;

import java.util.Scanner;
import java.util.Locale;
public class Main {
    static {
        Locale.setDefault(Locale.US);
    }

    private static final Scanner sc = new Scanner(System.in);

    // Interface que representa a DLL, usando JNA
    public interface ImpressoraDLL extends Library {

        // Caminho completo para a DLL
        ImpressoraDLL INSTANCE = (ImpressoraDLL) Native.load(
                "C:\\Users\\gomes_joyce\\Downloads\\Java-Aluno Graduacao\\Java-Aluno Graduacao\\E1_Impressora01.dll",
                ImpressoraDLL.class
        );

        private static String lerArquivoComoString(String path) throws IOException {
            FileInputStream fis = new FileInputStream(path);
            byte[] data = fis.readAllBytes();
            fis.close();
            return new String(data, StandardCharsets.UTF_8);
        }

        int AbreConexaoImpressora(int tipo, String modelo, String conexao, int param);
        int FechaConexaoImpressora();
        int ImpressaoTexto(String dados, int posicao, int estilo, int tamanho);
        int Corte(int avanco);
        int ImpressaoQRCode(String dados, int tamanho, int nivelCorrecao);
        int ImpressaoCodigoBarras(int tipo, String dados, int altura, int largura, int HRI);
        int AvancaPapel(int linhas);
        int StatusImpressora(int param);
        int AbreGavetaElgin();
        int AbreGaveta(int pino, int ti, int tf);
        int SinalSonoro(int qtd, int tempoInicio, int tempoFim);
        int ModoPagina();
        int LimpaBufferModoPagina();
        int ImprimeModoPagina();
        int ModoPadrao();
        int PosicaoImpressaoHorizontal(int posicao);
        int PosicaoImpressaoVertical(int posicao);
        int ImprimeXMLSAT(String dados, int param);
        int ImprimeXMLCancelamentoSAT(String dados, String assQRCode, int param);
    }

    private static boolean conexaoAberta = false;
    private static int tipo;
    private static String modelo;
    private static String conexao;
    private static int parametro;

    private static final Scanner scanner = new Scanner(System.in);

    private static String capturarEntrada(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }

    public static void configurarConexao() {
        if(!conexaoAberta){
            System.out.println("Digite o tipo: ");
            tipo = scanner.nextInt();

            System.out.println("Digite o modelo: ");
            modelo = scanner.nextLine();
            scanner.nextLine();

            System.out.println("Digite a conexao: ");
            conexao = scanner.nextLine();

            System.out.println("Digite o parametro: ");
            parametro = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Dados salvos com sucesso!!");
        }else{
            System.out.println("Precisa abrir conexao");
        }
    }

    public static void abrirConexao() {
        if (!conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.AbreConexaoImpressora(tipo, modelo, conexao, parametro);
            if (retorno == 0) {
                conexaoAberta = true;
                System.out.println("Conexão aberta com sucesso!");
            } else {
                System.out.println("Erro ao abrir conexão. Retorno " + retorno);
            }
        } else {
            System.out.println("Conexão já está aberta");
        }
    }

    public static void fecharConexao() {
        if (!conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.FechaConexaoImpressora();
            if (retorno == 0) {
                conexaoAberta = false;
                System.out.println("Conexão fechada!");
            } else {
                System.out.println("Erro ao fechar conexão. Retorno " + retorno);
            }
        } else {
            System.out.println("Conexão já está fechada");
        }
    }

    public static void ImpressaoTexto() {
        if (conexaoAberta) {
            System.out.println("Digite o texto a imprimir abaixo:");
            String dados = sc.nextLine();
            int resultado = ImpressoraDLL.INSTANCE.ImpressaoTexto(dados, 1, 4, 0);
        } else {
            System.out.println("Precisa abrir conexao primeiro");
        }
    }

    public static void Corte() {
        if (conexaoAberta) {
            int resultado = ImpressoraDLL.INSTANCE.Corte(3);
            if (resultado == 0) {
                System.out.println("Corte realizado com sucesso!");
            } else {
                System.out.println("Erro: " + resultado);
            }
        } else {
            System.out.println("Precisa abrir conexao primeiro");
        }
    }

    public static void ImpressaoQRCode() {
        if (conexaoAberta) {
            int qrcode = ImpressoraDLL.INSTANCE.ImpressaoQRCode("Teste de Impressao", 6, 4);
            if (qrcode == 0) {
                System.out.println("Impressão bem-sucessida");
            } else {
                System.out.println("Erro" + qrcode);
            }
        } else {
            System.out.println("Precisa abrir conexao primeiro");
        }
    }

    public static void ImpressaoCodigoBarras() {
        if (conexaoAberta) {
            int codbarra = ImpressoraDLL.INSTANCE.ImpressaoCodigoBarras(8, "{A012345678912", 100, 2, 3);
            if (codbarra == 0) {
                System.out.println("Impressão bem sucedida");
            } else {
                System.out.println("Erro" + codbarra);
            }
        } else {
            System.out.println("Precisa abrir conexao primeiro");
        }
    }

    public static void impressaoXMLSAT() {
        if (conexaoAberta) {
            String dados = "path=C:\\Users\\gomes_joyce\\Downloads\\Java-Aluno Graduacao\\Java-Aluno Graduacao\\XMLSAT.xml";
            int impre = ImpressoraDLL.INSTANCE.ImprimeXMLSAT(dados, 0);
            if (impre == 0) {
                System.out.println("Impressao realizada!");
            } else {
                System.out.println("Erro imprimir xml sat. Retorno " + impre);
            }
        } else {
            System.out.println("Precisa abrir conexao primeiro");
        }
    }

    public static void ImprimeXMLCancelamentoSAT() {
        if (conexaoAberta) {
            String dados = "path=C:\\Users\\gomes_joyce\\Downloads\\Java-Aluno Graduacao\\Java-Aluno Graduacao\\CANC_SAT.xml";
            int cancel = ImpressoraDLL.INSTANCE.ImprimeXMLCancelamentoSAT(dados, "Q5DLkpdRijIRGY6YSSNsTWK1TztHL1vD0V1Jc4spo/CEUqICEb9SFy82ym8EhBRZjbh3btsZhF+sjHqEMR159i4agru9x6KsepK/q0E2e5xlU5cv3m1woYfgHyOkWDNcSdMsS6bBh2Bpq6s89yJ9Q6qh/J8YHi306ce9Tqb/drKvN2XdE5noRSS32TAWuaQEVd7u+TrvXlOQsE3fHR1D5f1saUwQLPSdIv01NF6Ny7jZwjCwv1uNDgGZONJdlTJ6p0ccqnZvuE70aHOI09elpjEO6Cd+orI7XHHrFCwhFhAcbalc+ZfO5b/+vkyAHS6CYVFCDtYR9Hi5qgdk31v23w==", 0);
            if (cancel == 0) {
                System.out.println("Impressao realizada!");
            } else {
                System.out.println("Erro " + cancel);
            }
        } else {
            System.out.println("Precisa abrir conexao primeiro");
        }
    }

    public static void AbreGavetaElgin() {
        if (conexaoAberta) {
            String dados = "path=C:\\Users\\gomes_joyce\\Downloads\\Java-Aluno Graduacao\\Java-Aluno Graduacao\\XMLSAT.xml";
            int abre = ImpressoraDLL.INSTANCE.AbreGavetaElgin();
            if (abre == 0) {
                System.out.println("Impressao realizada!");
            } else {
                System.out.println("Erro ao abrir gaveta  " + abre);
            }
        } else {
            System.out.println("Precisa abrir conexao primeiro");
        }
    }

    public static void AbreGaveta() {
        if (conexaoAberta) {
            int gav = ImpressoraDLL.INSTANCE.AbreGaveta(1, 5,10);
            if (gav == 0) {
                System.out.println("Gaveta aberta com sucesso!");
            } else {
                System.out.println("Erro ao abrir a gaveta. Retorno " + gav);
            }
        }
    }
    public static void AvancaPapel() {
        if (conexaoAberta) {
            int papel = ImpressoraDLL.INSTANCE.AvancaPapel(5);
        }
    }

    public static void SinalSonoro(){
        if(conexaoAberta){
            int som = ImpressoraDLL.INSTANCE.SinalSonoro(4,5,5);
        }else{
            System.out.println("Precisa abrir conexao");
        }
    }


        public static void main (String[]args){
        while (true) {
            System.out.println("\n*************************************************");
            System.out.println("**************** MENU IMPRESSORA *******************");
            System.out.println("*************************************************\n");

            System.out.println("1  - Configurar Conexao");
            System.out.println("2  - Abrir Conexao");
            System.out.println("3  - Impressao Texto");
            System.out.println("4  - Impressao QRCode");
            System.out.println("5  - Impressao Cod Barras");
            System.out.println("6  - Impressao XML SAT");
            System.out.println("7  - Impressao XML Canc SAT");
            System.out.println("8  - Abrir Gaveta Elgin");
            System.out.println("9  - Abrir Gaveta");
            System.out.println("10 - Sinal Sonoro");
            System.out.println("0  - Fechar Conexao e Sair");
            System.out.println("--------------------------------------");

            String escolha = capturarEntrada("\nDigite a opção desejada: ");

            if (escolha.equals("0")) {
                fecharConexao();
                System.out.println("Fechando conexão");
                break;
            }

            switch (escolha) {
                case "1":
                    configurarConexao();
                    break;
                case "2":
                    abrirConexao();
                    break;
                case "3":
                    ImpressaoTexto();
                    ImpressoraDLL.INSTANCE.Corte(5);
                    break;
                case "4":
                    ImpressaoQRCode();
                    ImpressoraDLL.INSTANCE.Corte(5);
                    break;
                case "5":
                    ImpressaoCodigoBarras();
                    ImpressoraDLL.INSTANCE.Corte(5);
                    break;
                case "6":
                    impressaoXMLSAT();
                    ImpressoraDLL.INSTANCE.Corte(5);
                    break;
                case "7":
                    ImprimeXMLCancelamentoSAT();
                    ImpressoraDLL.INSTANCE.Corte(5);
                    break;
                case "8":
                    AbreGavetaElgin();
                    break;
                case "9":
                    AbreGaveta();
                    break;
                case "10":
                    SinalSonoro();
                    break;
                default:
                    System.out.println("Opção invalida");
            }
        }
    }
}
