package com.dm.earth.cabricality;

import com.dm.earth.cabricality.content.entries.CabfFluids;
import com.dm.earth.cabricality.content.fluids.IFluid;
import com.dm.earth.cabricality.util.ModChecker;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

import static com.dm.earth.cabricality.Cabricality.ID;

public class CabricalityClient implements ClientModInitializer {

    public static String genTranslationKey(String type, String path) {
        return type + "." + ID + "." + path;
    }

    @Override
    public void onInitializeClient(ModContainer mod) {
        ModChecker.check();
        initFluidRendering();
    }

    private static void initFluidRendering() {
        renderFluids(
                CabfFluids.RESIN,
                CabfFluids.REDSTONE
        );
    }

    private static void renderFluid(Fluid fluid) {
        IFluid iFluid = (IFluid) fluid;
        iFluid.setupRendering();
        if (fluid instanceof FlowableFluid flowable) {
            IFluid flowingIFluid = (IFluid) flowable.getFlowing();
            flowingIFluid.setupRendering();
        }
    }

    private static void renderFluids(Fluid... fluids) {
        for (Fluid fluid : fluids) renderFluid(fluid);
    }
}
