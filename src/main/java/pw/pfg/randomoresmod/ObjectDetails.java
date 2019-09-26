package pw.pfg.randomoresmod;

public class ObjectDetails {
	public int color;

	public String baseID;
	public String translationKey;
	public String englishName;

	public ObjectDetails(
		int color,
		String baseID,
		String translationKey,
		String englishName
	) {
		this.color = color;
		this.baseID = baseID;
		this.translationKey = translationKey;
		this.englishName = englishName;
	}

	//prettier-ignore
	@Override
	public String toString() {
		return "{" +
			" \n super=" + super.toString() +
			",\n color='" + String.format("#%06X", (0xFFFFFF & this.color)) + "'" +
			",\n baseID='" + this.baseID + "'" +
			",\n translationKey='" + this.translationKey + "'" +
			",\n englishName='" + this.englishName + "'" +
			" \n}";
	}
}
