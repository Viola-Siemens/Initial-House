package com.hexagram2021.initial_house.mixin;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.ServerLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 针对 {@link MinecraftServer} 的 Mixin 挂接点定义喵~
 *
 * @author liudongyu
 */
@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
	/**
	 * 在服务端设置初始出生点后触发的注入点喵~
	 *
	 * @param serverLevel 当前服务端维度喵~
	 * @param serverLevelData 维度数据喵~
	 * @param bonusChest 是否启用奖励箱喵~
	 * @param debug 是否为调试世界喵~
	 * @param ci 回调信息喵~
	 */
	@Inject(method = "setInitialSpawn", at = @At(value = "RETURN"))
	private static void initial_house$cacheSpawnPoint(ServerLevel serverLevel, ServerLevelData serverLevelData,
													  boolean bonusChest, boolean debug, CallbackInfo ci) {
	}
}
