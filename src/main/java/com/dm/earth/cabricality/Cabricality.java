package com.dm.earth.cabricality;

import com.dm.earth.cabricality.content.entries.CabfBlockEntityTypes;
import com.dm.earth.cabricality.content.entries.CabfBlocks;
import com.dm.earth.cabricality.content.entries.CabfFluids;
import com.dm.earth.cabricality.content.entries.CabfItems;
import com.dm.earth.cabricality.util.DataFixerListener;
import com.dm.earth.cabricality.util.UseEntityListener;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.item.group.api.QuiltItemGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cabricality implements ModInitializer {

    public static final String ID = "cabricality";
    public static final Logger LOGGER = LoggerFactory.getLogger(ID);
    public static final RuntimeResourcePack CLIENT_RESOURCES = RuntimeResourcePack.create(id("client_resources"));
    public static ItemGroup MAIN_GROUP = QuiltItemGroup.createWithIcon(id("main_group"), () -> Registry.ITEM.get(id("andesite_machine")).getDefaultStack());

    public static Identifier id(String id) {
        return new Identifier(ID, id);
    }

    @Override
    public void onInitialize(ModContainer mod) {
        LOGGER.info("Cabricality is initializing!");

        CabfItems.register();
        CabfBlocks.register();
        CabfFluids.register();
        CabfBlockEntityTypes.register();

        DataFixerListener.load();
        UseEntityListener.load();

        RRPCallback.AFTER_VANILLA.register(list -> list.add(CLIENT_RESOURCES));
    }
}
