package com.hexagram2021.initial_house.mixin;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.ServerLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
	@Inject(method = "setInitialSpawn", at = @At(value = "RETURN"))
	private static void initial_house$cacheSpawnPoint(ServerLevel serverLevel, ServerLevelData serverLevelData,
													  boolean bonusChest, boolean debug, CallbackInfo ci) {
	}
}
