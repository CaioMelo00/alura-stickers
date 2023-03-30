import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {

    // padrão: \u001b[m (existe a biblioteca JColor para Java)
    // para dar cor e realce texto no terminal
    // código de escape ANSI

    //private static final String ansiBoldStyle = "\u001b[1m";
    //private static final String ansiBlackText = "\u001b[30m";
    //private static final String ansiBlackBrightText = "\u001b[90m";
    //private static final String ansiYellowBack = "\u001b[43m";
    //private static final String ansiMagentaBack = "\u001b[45m";
    //private static final String ansiCyanBack = "\u001b[46m";
    //private static final String ansiReset = "\u001b[m";

    public static void main(String[] args) throws Exception {

        // fazer uma conexão HTTP e buscar os top 250 filmes
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient(); // criando uma nova variável
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // extrair só os dados que interessam (title, image, imDbRating)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        // exibir os dados
        for (Map<String,String> filme : listaDeFilmes) {
            System.out.println("\u001b[30m\u001b[45mTítulo\u001b[m: " + "\u001b[1m" + filme.get("title") + "\u001b[m"); 
            System.out.println("\u001b[30m\u001b[43mPoster\u001b[m: " + "\u001b[1m" + filme.get("image") + "\u001b[m");
            double classificacao = Double.parseDouble(filme.get("imDbRating")); // variável double recebe valor decimal
            int numeroEstrelinhas = (int) classificacao; // convertendo forçadamente o valor de volta, de double para inteiro

            for (int st = 1; st <= numeroEstrelinhas; st++) {
                System.out.print("\u2B50"); // Sem o ln, para não haver quebra de linha
            }
            System.out.println();
        }
    }
}
 