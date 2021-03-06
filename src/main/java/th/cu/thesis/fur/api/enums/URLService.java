package th.cu.thesis.fur.api.enums;

import java.util.HashMap;
import java.util.Map;


public enum URLService {

	APPLICATION("/applications", "APPLICATION"),
	ELIGIBLE("/eligible", "ELIGIBLE"),
	FLOWCONFIG("/flowconfig", "FLOW_CONFIG"),
	TODOLIST("/todolist", "TODOLIST"),
	USERREQUEST("/userrequest", "USERREQUEST"),
	USER("/users", "USER"),
	WATCHLIST("/watchlist", "WATCHLIST");


	
	private URLService(String uri, String page) {
		this.uri = uri;
		this.page = page;
	}

	private static Map<String, URLService> map = new HashMap<String, URLService>();
	static {
		for (URLService e : values()) {
			map.put(e.uri, e);
		}
	}

	public static final URLService getUri(String uri) {
		return map.get(uri);
	}

	public static Map<String, URLService> getMap() {
		return map;
	}

	public static URLService getPageByUri(String uri){
		for (URLService URLService : URLService.values()) {
			if (uri.contains(URLService.getUri()))
				return URLService;
		}
		return null;
	}

	private final String uri;
	private final String page;

	public String getUri() {
		return uri;
	}

	public String getPage() {
		return page;
	}

}
