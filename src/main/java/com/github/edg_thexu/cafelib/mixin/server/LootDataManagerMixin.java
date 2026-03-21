package com.github.edg_thexu.cafelib.mixin.server;

import com.github.edg_thexu.cafelib.data.pack.resources.PreReloader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootDataManager;
import net.minecraft.world.level.storage.loot.LootDataType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(LootDataManager.class)
public class LootDataManagerMixin {
    @Inject(method = "apply", at = @At(value = "HEAD"))
    private void forbidRecipeMixin(Map<LootDataType<?>, Map<ResourceLocation, ?>> pCollectedElements, CallbackInfo ci) {
        pCollectedElements.forEach((type, map)-> map.keySet().removeIf(id -> PreReloader.getInstance().lootTableForbidden.check(id.toString())));
    }
}
