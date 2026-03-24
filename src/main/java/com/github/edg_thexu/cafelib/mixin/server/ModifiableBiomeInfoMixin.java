package com.github.edg_thexu.cafelib.mixin.server;

import com.github.edg_thexu.cafelib.mixed.IBiomeInfo;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = ModifiableBiomeInfo.class, remap = false)
public class ModifiableBiomeInfoMixin {
    @WrapOperation(method = "applyBiomeModifiers", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/common/world/BiomeModifier;modify(Lnet/minecraft/core/Holder;Lnet/minecraftforge/common/world/BiomeModifier$Phase;Lnet/minecraftforge/common/world/ModifiableBiomeInfo$BiomeInfo$Builder;)V"))
    private void forbidLivingSpawn(BiomeModifier instance, Holder<Biome> biomeHolder, BiomeModifier.Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder, Operation<Void> original) {

        ((IBiomeInfo) builder.getMobSpawnSettings()).cafe_lib$setBiome(biomeHolder);
        original.call(instance, biomeHolder, phase, builder);

    }
}
