import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Register {

    public static void main(String[] args) {

        try {

            String json = """
            {
                "email": "23bq1a4940@vit.net",
                "name": "Divya Rachakonda",
                "mobileNo": "9494616256",
                "githubUsername": "DivyaRachakonda",
                "rollNo": "23BQ1A4940",
                "accessCode": "QQdEYy"
            }
            """;

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://4.224.186.213/evaluation-service/register"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Status Code: " + response.statusCode());
            System.out.println(response.body());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}