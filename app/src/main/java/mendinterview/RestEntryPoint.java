package mendinterview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@SpringBootApplication

public class RestEntryPoint {
    private final ResourceLoader resourceLoader;

    public RestEntryPoint(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public static void main(String[] args) {
        SpringApplication.run(RestEntryPoint.class, args);
    }

    @RestController
    public static class ShortestPathController {
        private final ResourceLoader resourceLoader;
        private final RestTemplate restTemplate;

        public ShortestPathController(ResourceLoader resourceLoader, RestTemplate restTemplate) {
            this.resourceLoader = resourceLoader;
            this.restTemplate = restTemplate;
        }

        @GetMapping("/shortestPath/{filePath}/{startNodeId}")
        public ResponseEntity<String> calculateShortestPath(@PathVariable String filePath,
                @PathVariable String startNodeId) {
            try {
                Resource resource = resourceLoader.getResource("file:" + filePath);
                String jsonContent = new String(Files.readAllBytes(Paths.get(resource.getURI())));

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                HttpEntity<String> request = new HttpEntity<>(jsonContent, headers);
                ResponseEntity<Map> response = restTemplate.postForEntity(
                        "http://localhost:8080/api/v1/graphs/shortestPaths?startNodeId={startNodeId}",
                        request,
                        Map.class,
                        startNodeId);

                return ResponseEntity.ok(response.getBody().toString());
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().body("Invalid file path: " + filePath);
            }
        }
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
