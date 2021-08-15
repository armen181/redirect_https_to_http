package am.armen181.redirecthttpstohttp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@RestController
public class RequestsController {

    private final RestTemplate restTemplate;

    @Value("${remout.server}")
    private String endpoint;

    public RequestsController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/**")
    public ResponseEntity<?> undefineGetRequests(final HttpServletRequest request) {
        final HttpHeaders headers = new HttpHeaders();
        final Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String param = headerNames.nextElement();
            headers.set(param, request.getHeader(param));
        }
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        final boolean b = request.getQueryString() == null || "".equals(request.getQueryString());
        final String url = b ? endpoint.concat(request.getRequestURI()) : endpoint.concat(request.getRequestURI().concat("?").concat(request.getQueryString()));

        return restTemplate.exchange(url, HttpMethod.GET, entity,  byte[].class);
    }

    @PostMapping("/**")
    public ResponseEntity<?> undefinePostRequests(final HttpServletRequest request) {
        return ResponseEntity.notFound().build();
    }
}
