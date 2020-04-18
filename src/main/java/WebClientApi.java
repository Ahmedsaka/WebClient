import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class WebClientApi {

    private WebClient webClient;

    public WebClientApi() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8080/products")
                .build();
    }

    public Flux<Product> getAllProducts(){
        return webClient
                .get()
                .retrieve()
                .bodyToFlux(Product.class)
                .doOnNext(product -> System.out.println("***************GET " + product));
    }

    public Mono<Product> getProduct(String id){
        return webClient
                .get()
                .uri("/id", id)
                .retrieve()
                .bodyToMono(Product.class)
                .doOnSuccess(o-> System.out.println("***************GET " + o));
    }

    public Mono<ResponseEntity<Product>> postNewProduct(){
        return webClient
                .post()
                .body(Mono.just(new Product(null, "Black Tea", 1.99)), Product.class)
                .exchange()
                .flatMap(clientResponse -> clientResponse.toEntity(Product.class))
                .doOnSuccess(o -> System.out.println("***************POST " + o));
    }

    public Mono<ResponseEntity<Product>> postNewProducts() {
        Flux<Product> productFlux = Flux.just(
                new Product(null, "Flat White", 2.99),
                new Product(null, "Espresso", 3.19),
                new Product(null, "Irish Coffee", 2.89),
                new Product(null, "Macchiato", 3.99)
        );
        return webClient
                .post()
                .body(productFlux, Product.class)
                .exchange()
                .flatMap(clientResponse -> clientResponse.toEntity(Product.class))
                .doOnSuccess(o-> System.out.println("***************POST " + o));
    }

    public Mono<Product> updateProduct(String id, String name, double price){
        return webClient
                .put()
                .uri("/id", id)
                .body(Mono.just(new Product(null, name, price)), Product.class)
                .retrieve()
                .bodyToMono(Product.class)
                .doOnSuccess(o-> System.out.println("***************UPDATE" + o));
    }


    public Mono<Void> deleteProduct(String id){
        return webClient
                .delete()
                .uri("/id", id)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnSuccess(o-> System.out.println("***************DELETE " + o));
    }

    public Flux<ProductEvent> getProductEvent(){
        return webClient
                .get()
                .uri("/events")
                .retrieve()
                .bodyToFlux(ProductEvent.class);
    }
}
