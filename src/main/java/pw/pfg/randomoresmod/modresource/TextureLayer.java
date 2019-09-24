package pw.pfg.randomoresmod.modresource;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.Identifier;

public class TextureLayer {
	public Identifier texture;
	public boolean tinted;

	// public int tintIndex;
	public TextureLayer(Identifier texture, boolean tinted) {
		this.texture = texture;
		this.tinted = tinted;
	// this.tintIndex = tintIndex;
	}

	public static class SetBuilder {
		List<TextureLayer> set = new ArrayList<>();

		public SetBuilder() {}

		public SetBuilder add(String texture) {
			return this.add(new Identifier(texture));
		}

		public SetBuilder add(Identifier texture) {
			return this.add(texture, false);
		}

		// public SetBuilder add(String texture, int tintIndex) {
		// 	return this.add(new Identifier(texture), tintIndex);
		// }
		// public SetBuilder add(Identifier texture, int tintIndex) {
		// 	set.add(new TextureLayer(texture, true, tintIndex));
		// 	return this;
		// }
		public SetBuilder add(String texture, boolean tinted) {
			return this.add(new Identifier(texture), tinted);
		}

		public SetBuilder add(Identifier texture, boolean tinted) {
			set.add(new TextureLayer(texture, tinted));
			return this;
		}

		public TextureLayer[] build() {
			return set.toArray(new TextureLayer[0]);
		}
	}
}
