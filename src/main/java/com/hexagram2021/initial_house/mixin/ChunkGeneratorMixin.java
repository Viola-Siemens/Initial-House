package com.hexagram2021.initial_house.mixin;

import com.hexagram2021.initial_house.server.config.IHServerConfig;
import com.hexagram2021.initial_house.server.register.IHStructureSetKeys;
import com.hexagram2021.initial_house.server.world.placements.SpawnPointOnlyPlacement;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalDoubleRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Map;
import java.util.Set;

/**
 * 区块生成器 Mixin 类，用于处理出生点区块的生成逻辑喵~
 *
 * @author liudongyu
 */
@Mixin(ChunkGenerator.class)
public class ChunkGeneratorMixin {
	@WrapOperation(method = "findNearestMapStructure", at = @At(value = "INVOKE", target = "Ljava/util/Map$Entry;getKey()Ljava/lang/Object;", ordinal = 0))
	private <K> K initial_house$findInitialHouse(Map.Entry<StructurePlacement, Set<Holder<Structure>>> instance, Operation<K> original,
												 @Local(argsOnly = true) BlockPos blockPos,
												 @Local(ordinal = 0) LocalRef<Pair<BlockPos, Holder<Structure>>> pair,
												 @Local(ordinal = 0) LocalDoubleRef distance) {
		K ret = original.call(instance);
		if(ret instanceof SpawnPointOnlyPlacement) {
			ChunkPos chunkPos = SpawnPointOnlyPlacement.getSpawnPointChunk();
			BlockPos newPos = new BlockPos(SectionPos.sectionToBlockCoord(chunkPos.x, 8), 32, SectionPos.sectionToBlockCoord(chunkPos.z, 8));
			double newDistance = blockPos.distSqr(newPos);
			if(newDistance < distance.get()) {
				distance.set(newDistance);
				pair.set(Pair.of(newPos, instance.getValue().iterator().next()));
			}
		}
		return ret;
	}

	@WrapOperation(method = "lambda$createStructures$14", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/structure/placement/StructurePlacement;isStructureChunk(Lnet/minecraft/world/level/chunk/ChunkGeneratorStructureState;II)Z"))
	private static boolean initial_house$isStructureChunk(StructurePlacement instance, ChunkGeneratorStructureState chunkGeneratorStructureState, int x, int z, Operation<Boolean> original,
														  @Local(argsOnly = true) RegistryAccess registryAccess, @Local(argsOnly = true) Holder<StructureSet> structureSetHolder) {
		boolean ret = original.call(instance, chunkGeneratorStructureState, x, z);
		if(ret && !structureSetHolder.is(IHStructureSetKeys.INITIAL_HOUSE)) {
			return chunkGeneratorStructureState.hasStructureChunkInRange(
					registryAccess.lookupOrThrow(Registries.STRUCTURE_SET).getOrThrow(IHStructureSetKeys.INITIAL_HOUSE),
					x, z, IHServerConfig.PREVENT_STRUCTURES_GENERATE_IN.get()
			);
		}
		return ret;
	}
}
