package pw.pfg.randomoresmod.modresource;

import java.util.ArrayList;
import java.util.List;

interface Processor<T>{
    void call(T builder);
}

public class StyleSet {
    public List<Style> styleSet = new ArrayList<>();
    public String addBase = "";
    public String addBaseLanguageKey = "";

    public static StyleSet Builder(){
        return new StyleSet();
    }
    
    public StyleSet(){

    }

    public StyleSet add(String languageKey, Processor<TextureLayer.SetBuilder> processor){
        TextureLayer.SetBuilder builder = new TextureLayer.SetBuilder();
        processor.call(builder);
        this.styleSet.add(new Style(builder.build(), this.addBaseLanguageKey + languageKey));
        return this;
    }

    public StyleSet addWithLanguageKey(String languageKey, String... items){
        return this.add(languageKey, t -> {
            int index = 0;
            for(String item : items){
                t.add(addBase + item, index == items.length - 1);
                index++;
            }
        });
    }

    public StyleSet add(String... items){
        this.addWithLanguageKey(items[0], items);
        return this;
    }

    public StyleSet baseOverlay(String main){
        return this.addWithLanguageKey(main, main + "_base", main + "_overlay");
    }

    public StyleSet resourceBase(String base){
        this.addBase = base;
        return this;
    }

    public StyleSet languageKeyBase(String base){
        this.addBaseLanguageKey = base;
        return this;
    }

    public Style[] build(){
        return this.styleSet.toArray(new Style[0]);
    }
}