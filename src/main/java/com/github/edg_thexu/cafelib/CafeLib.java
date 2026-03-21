package com.github.edg_thexu.cafelib;

import com.github.edg_thexu.cafelib.api.event.AddPreloadResourceEvent;
import com.github.edg_thexu.cafelib.init.CafeDataComponentTypes;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.function.Consumer;

@SuppressWarnings("removal")
@Mod(CafeLib.MODID)
public class CafeLib {

    public static final String MODID = "cafelib";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static ResourceLocation space(String path) {return new ResourceLocation(MODID, path);}
    public static ResourceLocation parse(String path){return ResourceLocation.parse(path);}
    public static ResourceLocation fromSpaceAndPath(String space, String path){return new ResourceLocation(space, path);}
    public static ResourceLocation defaultPath(String path){return new ResourceLocation("minecraft", path);}


    public CafeLib() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        CafeDataComponentTypes.register(modEventBus);


    }

    public static void listenMe(Consumer<? extends Event> listener) {
        MinecraftForge.EVENT_BUS.addListener(listener);
    }


}
