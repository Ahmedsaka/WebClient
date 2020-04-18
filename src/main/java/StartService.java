public class StartService {

    public static void main(String[] args) {
        WebClientApi api = new WebClientApi();

        api.postNewProduct()
                .thenMany(api.getAllProducts())
                .take(1)
                .flatMap(product -> api.updateProduct(product.getId(), "White Tea", 2.99))
                .flatMap(product -> api.deleteProduct(product.getId()))
                .thenMany(api.getAllProducts())
                .thenMany(api.getProductEvent())
                .subscribe(System.out::println);

        api.postNewProducts()
            .subscribe(System.out::println);
    }

}
