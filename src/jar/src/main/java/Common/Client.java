package Common;

/**
 * Has all the clients that will interact through the system.
 *
 * @author valen
 */
public enum Client {
	/**
	 * Who consume the jar's products.
	 */
	CONSUMER,
	/**
	 * Who produce the jar's products.
	 */
    PRODUCER;
	
	/**
	 * @return Client name.
	 */
	public String getClientName() {
		return switch (this) {
			case CONSUMER -> "Consumidor";
			case PRODUCER -> "Productor";
		};
	}
	
	/**
	 * @param name Client's name.
	 * @return A client.
	 */
	public static Client getClient(String name) {
		return switch (name) {
			case "Consumidor" -> Client.CONSUMER;
			case "Productor" -> Client.PRODUCER;
			default -> null;
		};
	}
	
	/**
	 * @return True if the client is a consumer, false if not.
	 */
	public boolean isConsumer() {
		return this.getClientName().equals(Client.CONSUMER.getClientName());
	}
	
	/**
	 * @return True if the client is a producer, false if not.
	 */
	public boolean isProducer() {
		return this.getClientName().equals(Client.PRODUCER.getClientName());
	}
}
