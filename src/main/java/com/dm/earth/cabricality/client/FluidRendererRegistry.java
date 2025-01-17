package com.dm.earth.cabricality.client;

import com.dm.earth.cabricality.Cabricality;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.block.extensions.api.client.BlockRenderLayerMap;
import org.quiltmc.qsl.resource.loader.api.ResourceLoader;
import org.quiltmc.qsl.resource.loader.api.reloader.SimpleSynchronousResourceReloader;

import java.util.function.Function;

public class FluidRendererRegistry {
    public static void register(String name, String texture, Fluid still, Fluid flowing, boolean flow) {
        int color = FluidColorRegistry.get(name);
        Identifier stillId = Cabricality.id("fluid/" + texture + "/" + texture + "_still");
        Identifier flowingId = Cabricality.id("fluid/" + texture + "/" + texture + "_flowing");

        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register((atlasTexture, registry) -> {
            registry.register(stillId);
            registry.register(flowingId);
        });

        final Sprite[] fluidSprites = {null, null};
        ResourceLoader.get(ResourceType.CLIENT_RESOURCES).registerReloader(new SimpleSynchronousResourceReloader() {
            @Override
            public void reload(ResourceManager manager) {
                final Function<Identifier, Sprite> atlas = MinecraftClient.getInstance().getSpriteAtlas(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE);
                fluidSprites[0] = atlas.apply(stillId);
                fluidSprites[1] = atlas.apply(flowingId);
            }

            @Override
            public @NotNull Identifier getQuiltId() {
                return Cabricality.id(name + "_fluid_renderer_reloader");
            }
        });
        FluidRenderHandler handler = new FluidRenderHandler() {
            @Override
            public Sprite[] getFluidSprites(@Nullable BlockRenderView view, @Nullable BlockPos pos, FluidState state) {
                return fluidSprites;
            }

            @Override
            public int getFluidColor(@Nullable BlockRenderView view, @Nullable BlockPos pos, FluidState state) {
                return color < 0 ? FluidRenderHandler.super.getFluidColor(view, pos, state) : color;
            }
        };

        FluidRenderHandlerRegistry.INSTANCE.register(still, flowing, handler);
        if (flow) BlockRenderLayerMap.put(RenderLayer.getTranslucent(), still, flowing);
        else BlockRenderLayerMap.put(RenderLayer.getTranslucent(), still);
    }

    public static void register(String name, Fluid still, Fluid flowing, boolean flow) {
        register(name, name, still, flowing, flow);
    }
}
