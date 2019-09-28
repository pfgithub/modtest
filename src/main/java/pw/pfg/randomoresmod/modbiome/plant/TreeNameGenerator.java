package pw.pfg.randomoresmod.modbiome.plant;

import java.util.Random;
import pw.pfg.randomoresmod.NameGenerator;

// function nameGen(type){
// 	var tp = type;
// 	var element = document.createElement("div");
// 	element.setAttribute("id", "result");
 // 			rnd = Math.random() * nm3.length | 0;
 // 			rnd2 = Math.random() * nm4.length | 0;
 // 			rnd3 = Math.random() * nm5.length | 0;
// 			if(rnd > 9){
// 				while(rnd3 > 9 ){
 // 					rnd3 = Math.random() * nm5.length | 0;
// 				}
// 			}
 // 			rnd4 = Math.random() * nm6.length | 0;
// 			if(tp === 1){
 // 				rnd5 = Math.random() * nm10.length | 0;
// 				names = nm3[rnd] + nm4[rnd2] + nm5[rnd3] + nm6[rnd4] + nm10[rnd5];
// 			}else{
 // 				rnd5 = Math.random() * nm7.length | 0;
// 				names = nm3[rnd] + nm4[rnd2] + nm5[rnd3] + nm6[rnd4] + nm7[rnd5];
// 			}
// 			names = names.charAt(0).toUpperCase() + names.slice(1);
public class TreeNameGenerator extends NameGenerator {
	// based on https://www.fantasynamegenerators.com/magical-tree-names.php
	//prettier-ignore
	static final NameGenerator NM1 = list(
		"abyss",		"aching",		"angel",		"angel's",
		"anxious",		"aquatic",		"arching",		"aromatic",
		"assassin",		"banshee",		"barbed",		"bitter",
		"black",		"bleak",		"blight",		"blister",
		"blood",		"blue",			"bone",			"boomerang",
		"bouncing",		"bright",		"bronze",		"candy",
		"cave",			"chilling",		"cliff",		"cold",
		"corrupt",		"corrupted",	"corrupting",	"coughing",
		"crawling",		"creeping",		"dancing",		"dawn",
		"deadly",		"death's",		"delicious",	"demon",
		"demon's",		"devil's",		"dim",			"dire",
		"dragon",		"drifting",		"drowsy",		"dusk",
		"dwarf",		"eagle",		"fake",			"fanged",
		"fatigue",		"fear",			"fearful",		"fever",
		"fire",			"fjord",		"flying",		"fragrant",
		"frozen",		"funeral",		"funky",		"ghost",
		"giant",		"glacier",		"glowing",		"golden",
		"grand",		"grave",		"gray",			"green",
		"grim",			"grumpy",		"hammer",		"happy",
		"harmless",		"hate",			"hidden",		"hollow",
		"horned",		"hot",			"hovering",		"humble",
		"ice",			"imperial",		"infecting",	"invisible",
		"island",		"itching",		"jealous",		"jester",
		"joyful",		"king's",		"lethal",		"life's",
		"lion",			"love",			"lunar",		"mage's",
		"majestic",		"mammoth",		"marsh",		"mercy's",
		"mimic",		"mock",			"mocking",		"monk's",
		"moon",			"mound",		"mountain",		"nasty",
		"naughty",		"nervous",		"noxious",		"ocean",
		"orange",		"ordinary",		"perfumed",		"pest",
		"phantom",		"pink",			"piranha",		"pixy",
		"plague",		"pleasant",		"poisonous",	"prancing",
		"putrid",		"pygmy",		"queen's",		"quiet",
		"rare",			"rash",			"raven",		"red",
		"regal",		"restoration",	"river",		"rotten",
		"royal",		"sad",			"salty",		"sanguine",
		"savage",		"scented",		"screaming",	"sentient",
		"serpent",		"shadow",		"shield",		"shocking",
		"shrine",		"shy",			"silver",		"skeletal",
		"skulking",		"sleeping",		"sleepy",		"smelly",
		"smooth",		"sneeze",		"sneezing",		"solar",
		"sore",			"sour",			"spicy",		"spiky",
		"spirit",		"spitfire",		"stink",		"stinking",
		"sugar",		"sun",			"sunny",		"swamp",
		"sweet",		"tall",			"tangle",		"tangled",
		"taunting",		"tickle",		"toxic",		"twilight",
		"twisted",		"urban",		"venomous",		"vision",
		"volcano",		"walking",		"warm",			"weeping",
		"whisper",		"white",		"whomping",		"wicked",
		"wild",			"wisdom",		"wolf",			"yellow"
	);

	//prettier-ignore
	static final NameGenerator NM2 = list(
		"acacia",		"alder",		"ash",		"aspen",
		"azalea",		"balsa",		"bamboo",		"baobab",
		"bayonet",		"beech",		"birch",		"box",
		"buckeye",		"buckthorn",	"bunya",		"bush",
		"cassava",		"catalpa",		"cedar",		"conifer",
		"cycad",		"cypress",		"elder",		"elm",
		"eucalyptus",	"fir",			"hawthorn",		"hazel",
		"hemlock",		"hickory",		"holly",		"hornbeam",
		"juniper",		"larch",		"leaf",			"locust",
		"magnolia",		"mahogany",		"mangrove",		"maple",
		"medlar",		"milkbark",		"oak",			"oleander",
		"palm",			"palmetto",		"persimmon",	"pine",
		"poplar",		"privet",		"rhododendron",	"rowan",
		"sequoia",		"spruce",		"strongbark",	"sumac",
		"sycamore",		"tree",			"viburnum",		"willow",
		"wood",			"yew",			"yucca"
	);

	//prettier-ignore
	static final NameGenerator NM3 = list(
		"a",		"e",		"i",		"o",		"u",		"a",		"e",		"i",
		"o",		"u",		"ea",		"ei",		"eo",		"ae",		"ai",		"ia",
		"io",		"ua",		"aa",		"ee",		"oo",		"ou",		"",		"",
		"",			"",			"",			"",			"",			"",			"",		"",
		"",			"",			"",			"",			"",			"",			""
	);

	//prettier-ignore
	static final NameGenerator NM4 = list(
		"b",		"c",		"d",		"f",		"g",		"h",		"k",		"l",
		"m",		"n",		"p",		"r",		"s",		"t",		"v",		"w",
		"x",		"y",		"z",		"bl",		"br",		"ch",		"chr",		"cl",
		"cr",		"dl",		"dr",		"fl",		"fr",		"fy",		"gl",		"gr",
		"kl",		"kn",		"kr",		"ph",		"phr",		"pl",		"pr",		"sc",
		"sh",		"shr",		"sl",		"sm",		"sn",		"sp",		"sr",		"str",
		"th",		"thr",		"tr",		"vl"
	);

	//prettier-ignore
	static final NameGenerator NM5 = list(
		"a",		"e",		"i",		"o",		"u",		"a",		"e",		"i",
		"o",		"u",		"ea",		"ei",		"eo",		"ae",		"ai",		"ia",
		"io",		"ua",		"aa",		"ee",		"oo",		"ou"
	);

	//prettier-ignore
	static final NameGenerator NM6 = list(
		"b",		"c",		"d",		"f",		"g",		"h",		"j",		"k",
		"l",		"m",		"n",		"p",		"q",		"r",		"s",		"t",
		"v",		"w",		"x",		"y",		"z",		"b",		"c",		"d",
		"f",		"g",		"h",		"j",		"k",		"l",		"m",		"n",
		"p",		"q",		"r",		"s",		"t",		"v",		"w",		"x",
		"y",		"z",		"bb",		"bd",		"bg",		"bl",		"br",		"bs",
		"cc",		"ch",		"chr",		"cl",		"cr",		"dd",		"df",		"dg",
		"dl",		"dr",		"ds",		"dt",		"fd",		"ff",		"fg",		"fl",
		"fm",		"fn",		"fp",		"fr",		"fy",		"gd",		"gg",		"ght",
		"gl",		"gr",		"gth",		"hh",		"hl",		"hm",		"hn",		"hs",
		"ht",		"kd",		"kk",		"kl",		"km",		"kn",		"kr",		"lb",
		"ld",		"lf",		"lg",		"lk",		"ll",		"lm",		"ln",		"lp",
		"ls",		"lt",		"ly",		"mb",		"md",		"mf",		"mk",		"ml",
		"mm",		"mn",		"mp",		"ms",		"my",		"nc",		"nd",		"nf",
		"ng",		"nk",		"nl",		"nm",		"nn",		"np",		"ns",		"nt",
		"ny",		"ph",		"phr",		"pl",		"pp",		"pr",		"ql",		"qr",
		"qs",		"rc",		"rd",		"rf",		"rg",		"rh",		"rk",		"rl",
		"rm",		"rn",		"rp",		"rr",		"rs",		"rsh",		"rt",		"rth",
		"rw",		"sb",		"sc",		"sd",		"sf",		"sg",		"sh",		"shr",
		"sk",		"sl",		"sm",		"sn",		"sp",		"sr",		"ss",		"st",
		"str",		"sw",		"sy",		"th",		"thr",		"tr",		"tt",		"vl",
		"zh",		"zl",		"zr",		"zz"
	);

	//prettier-ignore
	static final NameGenerator NM7 = list(
		"ab",		"ac",		"acca",		"acia",		"alea",		"an",		"ander",	"ant",
		"any",		"ar",		"arch",		"ark",		"ava",		"eaf",		"eam",		"eech",
		"en",		"er",		"ess",		"et",		"etto",		"ew",		"eye",		"ifer",
		"immon",	"ine",		"iper",		"irch",		"ock",		"olia",		"on",		"onet",
		"ood",		"ore",		"orn",		"ory",		"ove",		"ow",		"uce",		"um",
		"us"
	);

	static final NameGenerator MAIN = list(
		order(NM1, "_", NM2),
		order(NM3, NM4, NM7),
		order(NM3, NM4, NM5, NM6, NM7)
	);

	public static final TreeNameGenerator INSTANCE = new TreeNameGenerator();

	@Override
	public String generate(Random random) {
		return MAIN.generate(random);
	}
}
