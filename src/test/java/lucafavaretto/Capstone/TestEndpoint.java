package lucafavaretto.Capstone;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestEndpoint {
    @Test
    public void userDoesNotExistTest() throws ClientProtocolException, IOException {
        String id = "bd880c3c-c049-4946-ac4e-76c267ca9274";
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MDkyODU3OTIsImV4cCI6MTcwOTM3MjE5Miwic3ViIjoiYmQ4ODBjM2MtYzA0OS00OTQ2LWFjNGUtNzZjMjY3Y2E5Mjc0In0.l1lXotVnnJhtO-Jyv4TdZ9-greWV-4PQIcBnZASce7E";
        HttpUriRequest request = new HttpGet("http://localhost:4201/users/" + id);
        request.setHeader("Authorization", "Bearer " + token);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
    }
}
