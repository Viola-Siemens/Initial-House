package com.hexagram2021.initial_house.mixin;

import com.hexagram2021.initial_house.server.register.IHConfiguredStructures;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.level.storage.ServerLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("CommentedOutCode")
@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
//	@Inject(method = "setInitialSpawn", at = @At(value = "TAIL"))
//	private static void addInitialHouse(ServerLevel serverLevel, ServerLevelData serverLevelData, boolean bonus, boolean debug, CallbackInfo ci) {
//		if(!debug) {
//			List<ResourceLocation> initialHouses = IHServerConfig.INITIAL_HOUSE_STRUCTURES.get().stream().map(ResourceLocation::new).toList();
//			ResourceLocation id = initialHouses.get(serverLevel.getRandom().nextInt(initialHouses.size()));
//			StructureTemplate template = serverLevel.getStructureManager().getOrCreate(id);
//			Vec3i size = template.getSize();
//			LevelData levelData = serverLevel.getLevelData();
//			BlockPos spawnPoint = new BlockPos(levelData.getXSpawn(), levelData.getYSpawn(), levelData.getZSpawn());
//			BlockPos blockPos = spawnPoint.offset(
//					-size.getX() / 2 + IHServerConfig.INITIAL_HOUSE_PIVOT_X.get(),
//					IHServerConfig.INITIAL_HOUSE_PIVOT_Y.get(),
//					-size.getZ() / 2 + IHServerConfig.INITIAL_HOUSE_PIVOT_Z.get()
//			);
//			StructurePlaceSettings settings = new StructurePlaceSettings();
//			template.placeInWorld(serverLevel, blockPos, blockPos, settings, serverLevel.getRandom(), Block.UPDATE_CLIENTS);
//		}
//	}

	@Inject(method = "setInitialSpawn", at = @At(value = "TAIL"))
	private static void addInitialHouse(ServerLevel serverLevel, ServerLevelData serverLevelData, boolean bonus, boolean debug, CallbackInfo ci) {
		if(!debug) {
			LevelData levelData = serverLevel.getLevelData();
			BlockPos spawnPoint = new BlockPos(levelData.getXSpawn(), levelData.getYSpawn(), levelData.getZSpawn());
			ConfiguredStructureFeature<?, ?> configuredStructureFeature = IHConfiguredStructures.INITIAL_HOUSE.value();
			StructureStart start = configuredStructureFeature.generate(
					serverLevel.registryAccess(),
					serverLevel.getChunkSource().getGenerator(),
					serverLevel.getChunkSource().getGenerator().getBiomeSource(),
					serverLevel.getStructureManager(),
					serverLevel.getSeed(),
					new ChunkPos(spawnPoint),
					0,
					serverLevel.getChunk(spawnPoint),
					biome -> true
			);
			serverLevel.getChunkAt(spawnPoint).setStartForFeature(configuredStructureFeature, start);
		}
	}
}
