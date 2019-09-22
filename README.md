![screenshot showing many ores and blocks](https://i.imgur.com/a9sigV0.png)
![screenshot showing many ores](https://i.imgur.com/6E5fHXq.png)

## setup

vscode: `./gradlew genSources vscode`  
eclipse: `./gradlew genSources eclipse`  
idea: `./gradlew genSources idea`

## running

use vscode, eclipse, or idea to run the project

## building

`./gradlew build`

## making textures

base should generally be solid white behind colored things (to allow color brightening)

colored things:

- solid white = resource color
- darker grey = darker than resource color
- white + opacity = lighter than resource color (assuming base is solid white there)

quickly generate this using a gradient map (black .. white .. rgba(255, 255, 255, 0)) (don't use gimp, it is really hard to edit gradients and you have to press a button and undo every time to apply, it's helpful to use a program with gradient map that shows the output in realtime as you update the gradient)

- if you want to use gimp,
- add a gradient in the gradient window
- edit it using the gradient editor by selecting sections in the bottom and right clicking them to edit left/right color
- select that gradient in the gradient window
- colors > map > gradient map

doing it manually

- when doing it manually, you can also add color tints like in the chunk (coal) texture
- you can add background colors other than white like in the infused stone (fire charge) texture

ores:

- make sure to add the darker grey pixels around ore spaces like in jappa's 1.13 textures
- see ore_dull_base, ore_gem_base, ore_shiny_base, ore_chunky_base, ore_vein_base for examples and notice the dark spots above and around color spots
- generally fill things that will get overlayed with white to allow brightening
