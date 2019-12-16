package pw.pfg.randomoresmod;

public class TextureInfo {
	public String id;
	public Style style; // max 4 for items, infinite for blocks

	public TextureInfo(String id, Style style) {
		this.id = id;
		this.style = style;
	}
}
