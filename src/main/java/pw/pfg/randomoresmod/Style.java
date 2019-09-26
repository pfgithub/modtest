package pw.pfg.randomoresmod;

import pw.pfg.randomoresmod.modresource.TextureLayer;

public class Style {
	public TextureLayer[] texture;
	public String languageKey;

	public Style(TextureLayer[] texture, String languageKey) {
		this.texture = texture;
		this.languageKey = languageKey;
	}
}
