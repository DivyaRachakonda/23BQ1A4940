import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Auth {

    public static void main(String[] args) {

        try {

            String json = """
            {
                "email": "23bq1a4940@vit.net",
                "name": "Divya Rachakonda",
                "rollNo": "23BQ1A4940",
                "accessCode": "QQdEYy",
                "clientID": "1f3052b8-d3ce-4232-9e8b-49ce2e80b139",
                "clientSecret": "UwMakSrSzGWyqYKc"
            }
            """;

            // Print the JSON being sent
            System.out.println("JSON Request:");
            System.out.println(json);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://4.224.186.213/evaluation-service/auth"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("\nStatus Code: " + response.statusCode());
            System.out.println("Response:");
            System.out.println(response.body());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}