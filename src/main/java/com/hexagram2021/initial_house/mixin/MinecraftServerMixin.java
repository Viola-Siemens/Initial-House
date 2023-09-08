package com.hexagram2021.initial_house.mixin;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;

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
//
//	@Inject(method = "setInitialSpawn", at = @At(value = "TAIL"))
//	private static void addInitialHouse(ServerLevel serverLevel, ServerLevelData serverLevelData, boolean bonus, boolean debug, CallbackInfo ci) {
//		if(!debug) {
//			LevelData levelData = serverLevel.getLevelData();
//			BlockPos spawnPoint = new BlockPos(levelData.getXSpawn(), levelData.getYSpawn(), levelData.getZSpawn());
//			ConfiguredStructureFeature<?, ?> configuredStructureFeature = IHConfiguredStructures.INITIAL_HOUSE.value();
//			StructureStart reference = serverLevel.structureFeatureManager().getStartForFeature(null, configuredStructureFeature, serverLevel.getChunk(spawnPoint));
//			int numReference = reference == null ? 0 : reference.getReferences();
//			StructureStart start = configuredStructureFeature.generate(
//					serverLevel.registryAccess(),
//					serverLevel.getChunkSource().getGenerator(),
//					serverLevel.getChunkSource().getGenerator().getBiomeSource(),
//					serverLevel.getStructureManager(),
//					serverLevel.getSeed(),
//					new ChunkPos(spawnPoint),
//					numReference,
//					serverLevel.getChunk(spawnPoint),
//					biome -> true
//			);
//			if (start.isValid()) {
//				serverLevel.getChunkAt(spawnPoint).setStartForFeature(configuredStructureFeature, start);
//			}
//		}
//	}
}
