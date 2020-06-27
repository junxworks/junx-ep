package io.github.junxworks.ep.auth;

public class RamRequestHolder {
	private static final ThreadLocal<RamRequest> requests = new ThreadLocal<>();

	public static void set(RamRequest request) {
		requests.set(request);
	}

	public static RamRequest get() {
		return requests.get();
	}

	public static void remove() {
		requests.remove();
	}
}
