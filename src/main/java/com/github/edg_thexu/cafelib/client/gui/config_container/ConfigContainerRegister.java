package com.github.edg_thexu.cafelib.client.gui.config_container;


import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;


// example
//@SuppressWarnings("all")
//public class ConfigContainerRegister {

//    public static ConfigScreenBuilder init(ConfigScreen screen){
//        ConfigScreenBuilder builder;
//        builder = ConfigScreenBuilder.builder(screen);
//
//        builder.addTab(Better_experience.MODID,"Client",45);
//
//
//        builder.addCheckBox(ClientConfig.SHOW_OUTLINES)
//                .comment("Show Staff outlines.");
//
//
//
//        builder.addTab(Better_experience.MODID,"Player",60);
//
//        if(JeiHelper.isLoaded()) {
//            builder.addIntSliderEditBox(CommonConfig.QUICK_JEI_FETCH_DISTANCE, 1, 30)
//                    .comment("Quick JEI Fetch Distance.");
//            builder.addCheckBox(CommonConfig.QUICK_JEI_FETCH)
//                    .comment("Quick JEI Fetch.");
//        }
//
//        builder.addTab(Better_experience.MODID,"Entity",98);
//        if(TEHelper.isLoaded()) {
//            builder.addCheckBox(CommonConfig.SLIME_DIE_NO_LAVA)
//                    .comment("Forbidden slime to generate lava.");
//        }
//
//        builder.build(Better_experience.MODID);
//        return builder;
//    }
//
//    @SuppressWarnings("removal")
//    public static void registerModsPage(final InterModEnqueueEvent event) {
//        event.enqueueWork(() -> {
//            if (FMLEnvironment.dist == Dist.CLIENT) {
//                ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () ->
//                        new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> new ConfigScreen(parent, Component.literal("title")){
//
//                            @Override
//                            protected ForgeConfigSpec getSpec() {
//                                return null;
//                            }
//                        }));
//            }
//        });
//    }


//}
