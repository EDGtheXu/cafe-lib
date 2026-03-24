package com.github.edg_thexu.cafelib.mixin.server;

import com.github.edg_thexu.cafelib.data.pack.resources.LivingSpawnForbidden;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.serialization.Decoder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;

@Mixin(RegistryDataLoader.class)
public class RegistryDataLoaderMixin {
    @Inject(method = "load", at = @At("HEAD"))
    private static void preload(ResourceManager pResourceManager, RegistryAccess pRegistryAccess, List<RegistryDataLoader.RegistryData<?>> pRegistryData, CallbackInfoReturnable<RegistryAccess.Frozen> cir) {
        LivingSpawnForbidden.getInstance().preload(pResourceManager, Runnable::run);
    }

    // 由于codec，无法得到群系id，使用静态变量传入
    @WrapOperation(method = "loadRegistryContents", at = @At(value = "INVOKE", target = "Lnet/minecraft/resources/ResourceKey;create(Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/resources/ResourceKey;"))
    private static <T> ResourceKey<T> modifyResourceKey(ResourceKey<? extends Registry<T>> pRegistryKey, ResourceLocation pLocation, Operation<ResourceKey<T>> original) {
        ResourceKey<T> value =  original.call(pRegistryKey, pLocation);
        if(pRegistryKey.location().equals(Registries.BIOME.location()) ) {
            LivingSpawnForbidden.dynamicBiomeId = value.location();
        }
        return value;
    }

    @Inject(method = "loadRegistryContents", at = @At("TAIL"))
    private static <E> void modifyRegistryContents(RegistryOps.RegistryInfoLookup pLookup, ResourceManager pManager, ResourceKey<? extends Registry<E>> pRegistryKey, WritableRegistry<E> pRegistry, Decoder<E> pDecoder, Map<ResourceKey<?>, Exception> pExceptions, CallbackInfo ci) {
        LivingSpawnForbidden.dynamicBiomeId = null;
    }
}
