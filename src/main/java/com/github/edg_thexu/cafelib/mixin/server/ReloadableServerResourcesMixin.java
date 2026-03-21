package com.github.edg_thexu.cafelib.mixin.server;

import com.github.edg_thexu.cafelib.data.pack.resources.PreReloader;
import net.minecraft.commands.Commands;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.flag.FeatureFlagSet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin(ReloadableServerResources.class)
public class ReloadableServerResourcesMixin {

    @Inject(method = "loadResources", at = @At(value = "HEAD"))
    private static void preloadResources(ResourceManager pResourceManager, RegistryAccess.Frozen pRegistryAccess, FeatureFlagSet pEnabledFeatures, Commands.CommandSelection pCommandSelection, int pFunctionCompilationLevel, Executor pBackgroundExecutor, Executor pGameExecutor, CallbackInfoReturnable<CompletableFuture<ReloadableServerResources>> cir) {
//        CompletableFuture<Void> preloadFuture = CompletableFuture.supplyAsync(() -> {
            PreReloader.reloadAsync(pResourceManager, pBackgroundExecutor, pGameExecutor);
//            return null;
//        }, pBackgroundExecutor);  // 使用后台线程执行
//        preloadFuture.join();
    }

}
