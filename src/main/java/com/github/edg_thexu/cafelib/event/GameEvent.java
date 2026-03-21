package com.github.edg_thexu.cafelib.event;

import com.github.edg_thexu.cafelib.CafeLib;
import com.github.edg_thexu.cafelib.data.pack.resources.LivingSpawnForbidden;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = CafeLib.MODID)
public class GameEvent {

    @SubscribeEvent
    public static void addReloadListener(AddReloadListenerEvent event) {

        event.addListener(LivingSpawnForbidden.getInstance());
    }

}
