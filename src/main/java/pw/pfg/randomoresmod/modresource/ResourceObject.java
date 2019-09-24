package pw.pfg.randomoresmod.modresource;

public abstract class ResourceObject {
	public String id;
	public Style style; // max 4 for items, infinite for blocks

	public ResourceObject(String id, Style style) {
		this.id = id;
		this.style = style;
	}
}
