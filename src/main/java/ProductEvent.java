public class ProductEvent {
    private Long eventId;
    private String eventType;

    public ProductEvent(Long eventId, String eventType) {
        this.eventId = eventId;
        this.eventType = eventType;
    }
}
