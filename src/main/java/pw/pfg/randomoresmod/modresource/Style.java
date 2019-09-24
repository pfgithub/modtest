package pw.pfg.randomoresmod.modresource;

import java.util.ArrayList;
import java.util.List;

public class Style {
    public TextureLayer[] texture;
    public String languageKey;

    public Style(TextureLayer[] texture, String languageKey){
        this.texture = texture;
        this.languageKey = languageKey;
    }
}