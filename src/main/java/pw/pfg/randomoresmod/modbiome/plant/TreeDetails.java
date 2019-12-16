package pw.pfg.randomoresmod.modbiome.plant;

import java.util.Random;
import pw.pfg.randomoresmod.ObjectDetails;
import pw.pfg.randomoresmod.TextureInfo;

public class TreeDetails extends ObjectDetails {
	public TextureInfo log;
	public TextureInfo leaves;
	public TextureInfo planks;

	static double halfLife(
		double current,
		double initial,
		double halfLife,
		double change
	) { // half life 3 formula
		return initial * Math.pow(change, current / halfLife);
	}

	public static TreeDetails random(String resourceNameId, String englishName) {
		// note that new features must be inserted at the bottom of a section
		Random setupRandom = new Random(resourceNameId.hashCode());
		Random cosmeticRandom = new Random(setupRandom.nextInt());

		int color = cosmeticRandom.nextInt(0xFFFFFF);

		// computed
		TextureInfo logStyle = ResourceObjectLog.random(resourceNameId);
		TextureInfo leavesStyle = ResourceObjectLeaves.random(resourceNameId);
		TextureInfo plankStyle = ResourceObjectPlanks.random(resourceNameId);

		String baseId = resourceNameId;
		String translationKey = "name.randomoresmod.tree." + baseId;

		return new TreeDetails(
			color,
			baseId,
			translationKey,
			englishName,
			logStyle,
			leavesStyle,
			plankStyle
		);
	}

	private TreeDetails(
		int color,
		String baseId,
		String translationKey,
		String englishName,
		TextureInfo logStyle,
		TextureInfo leavesStyle,
		TextureInfo plankStyle
	) {
		super(color, baseId, translationKey, englishName);
		this.log = logStyle;
		this.leaves = leavesStyle;
		this.planks = plankStyle;
	}

	//prettier-ignore
	@Override
	public String toString() {
		return "{" +
            " \n super=" + super.toString() +
            " \n log=" + log +
            " \n leaves=" + leaves +
            " \n plank=" + planks +
			" \n}";
	}
}
