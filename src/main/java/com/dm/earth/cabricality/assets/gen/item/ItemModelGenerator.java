package com.dm.earth.cabricality.assets.gen.item;

import com.dm.earth.cabricality.Cabricality;
import net.devtech.arrp.json.models.JModel;

public class ItemModelGenerator {
    public static JModel generated(String id) {
        return new JModel().parent("item/generated").addTexture("layer0", Cabricality.id(id).toString());
    }

    public static JModel generated(String... id) {
        return generated(String.join("/", id));
    }
}
