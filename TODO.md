## textures

gem

- update all gems to use base+overlay textures so things like ingot look right
- update some older textures like diamond to better match the original
- add some clear variants of textures (like shard now but with both filled and clear)

ingot

- make some more unique textures
- make existing unique textures not look terrible

storage block

- make streaky (coal block themed) not look terrible
- lighten borders of lined (iron) or make it not look as bad
- add some of the pre-1.14 textures and some of the new resource pack public beta textures

ores

- add some custom ore textures (for example, smaller dots and thinner, longer streaks)
- add some nether ores (and do the transparency correctly like netherrack has)
- maybe?: andesite/granite/diorite ores?
- maybe?: sand/gravel/dirt ores?

## ores

ores

- rarity
- should impact all other stats, more rare = better usually
- should negatively impact some stats (smelting time)
- should not effect some stats (blast resistsnce)
- should have a chance of being a different color than the item

resource

- shiny (based on rarity maybe)
- rarity (vanilla thing, affects name color) (based on rarity)
- change colors lightly (using getcolor for items and animations for ores)

tools [pick, shovel, axe, hoe, sword, bow?, arrow?, armor]

- stats: sharpness (for axe, arrow)
- stats: mining speed (for pick, shovel, axe, hoe)
- stats: protection (for armor) + also use blast resistance (so strong block = strong armor)

related items

- nugget of x (ingots only)
- block of x (for storage, 3x3)
- decorative block (for decoration, 2x2, not reverseable)

decorative blocks

- stairs,slabs
- walls
- smooth (+stairs,slabs,walls if exist)
- cut (+stairs,slabs,walls if exist)
- chiseled
- pillar

special items (any of these)

- torch (must be fuel)
- spike
- anvil
- hopper
- redstone dust (maybe not...)
- tripwire string/hook
- cauldrons
- chest
- rails (powered?) (possibly increase max speed)
- doors/trapdoors
- weighted pressure plates
- bars
- ladders
- specialized furnaces
- fluids
- pistons

effects (items can have multiple, effects impact tool use, edibility, ...)

- status effect
- teleportation effect
- sound effect
- others...

right click

- eat
- drink
- throw
- use
- instant mine (silk?)
- do nothing

edible

- saturation
- effects

mining level

- impacted by rarity / hardness

dungeon chests and rarity (see infinite minecraft)

- dungeon, temples, stronghold, mineshaft, village, mansion, end city, end ship

far future

- add textures using artifice instead of using color overlays
- this would allow better mod support and possibly a different color filter method
  and fix the transparent mining bug

## biomes

trees:

- height (min,max)
- straightness (chance of moving horizontally)
- weight (chance of bending)

plants

- plants that attach to the side of blocks (mushrooms)

mushrooms

- optional tall version (stem + top)
- optional small placeable version
- optional block side version (maybe allow multiplein the same block)
