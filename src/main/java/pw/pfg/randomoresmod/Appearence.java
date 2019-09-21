package pw.pfg.randomoresmod;

import net.minecraft.util.UseAction;

public class Appearence {
	public boolean shiny;
	public UseAction /*?*/useAction;

	public static class Builder {
		private boolean shiny;
		private UseAction /*?*/useAction;

		public Builder() {
			this.shiny = false;
		}

		public Appearence build() {
			return new Appearence(this);
		}

		public Builder setShiny() {
			this.shiny = true;
			return this;
		}

		public Builder setShiny(boolean shiny) {
			this.shiny = shiny;
			return this;
		}

		public Builder setUseAction(UseAction useAction) {
			this.useAction = useAction;
			return this;
		}
	}

	public static Builder builder() {
		return new Builder();
	}

	private Appearence(Builder builder) {
		this.shiny = builder.shiny;
		this.useAction = builder.useAction;
	}
}
