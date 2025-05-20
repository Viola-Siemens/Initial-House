package com.hexagram2021.initial_house.mixin;

import com.hexagram2021.initial_house.server.world.placements.SpawnPointOnlyPlacement;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalDoubleRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Map;
import java.util.Set;

@Mixin(ChunkGenerator.class)
public class ChunkGeneratorMixin {
	@WrapOperation(method = "findNearestMapFeature", at = @At(value = "INVOKE", target = "Ljava/util/Map$Entry;getKey()Ljava/lang/Object;", ordinal = 0))
	private <K> K initial_house$findInitialHouse(Map.Entry<K, Set<Holder<ConfiguredStructureFeature<?, ?>>>> instance, Operation<K> original,
												 @Local(argsOnly = true) BlockPos blockPos,
												 @Local(ordinal = 0) LocalRef<Pair<BlockPos, Holder<ConfiguredStructureFeature<?, ?>>>> pair,
												 @Local(ordinal = 0) LocalDoubleRef distance) {
		K ret = original.call(instance);
		if(ret instanceof SpawnPointOnlyPlacement) {
			ChunkPos chunkPos = SpawnPointOnlyPlacement.getSpawnPointChunk((ChunkGenerator)(Object)this);
			BlockPos newPos = new BlockPos(SectionPos.sectionToBlockCoord(chunkPos.x, 8), 32, SectionPos.sectionToBlockCoord(chunkPos.z, 8));
			double newDistance = blockPos.distSqr(newPos);
			if(newDistance < distance.get()) {
				distance.set(newDistance);
				pair.set(Pair.of(newPos, instance.getValue().iterator().next()));
			}
		}
		return ret;
	}
}
