package pw.pfg.randomoresmod.modbiome.plant;

import java.util.Random;
import pw.pfg.randomoresmod.ObjectDetails;
import pw.pfg.randomoresmod.ResourceObject;

public class TreeDetails extends ObjectDetails {
	public ResourceObject log;
	public ResourceObject leaves;
	public ResourceObject planks;

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
		ResourceObject logStyle = ResourceObjectLog.random(resourceNameId);
		ResourceObject leavesStyle = ResourceObjectLeaves.random(resourceNameId);
		ResourceObject plankStyle = ResourceObjectPlanks.random(resourceNameId);

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
		ResourceObject logStyle,
		ResourceObject leavesStyle,
		ResourceObject plankStyle
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
