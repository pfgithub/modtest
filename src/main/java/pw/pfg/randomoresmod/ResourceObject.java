package pw.pfg.randomoresmod;

public class ResourceObject {
	public String id;
	public Style style; // max 4 for items, infinite for blocks

	public ResourceObject(String id, Style style) {
		this.id = id;
		this.style = style;
	}
}
