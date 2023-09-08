package com.hexagram2021.initial_house.server.world.placements;

import com.hexagram2021.initial_house.server.register.IHStructurePlacementTypes;
import com.hexagram2021.initial_house.server.util.IHLogger;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;

import javax.annotation.Nullable;

public record SpawnPointOnlyPlacement(int xShift, int zShift) implements StructurePlacement {
	@Nullable
	public static ChunkPos cachedSpawnPointChunk = null;

	public static final Codec<SpawnPointOnlyPlacement> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					Codec.intRange(-1024, 1023).fieldOf("xShift").forGetter(SpawnPointOnlyPlacement::xShift),
					Codec.intRange(-1024, 1023).fieldOf("zShift").forGetter(SpawnPointOnlyPlacement::zShift)
			).apply(instance, SpawnPointOnlyPlacement::new)
	);

	@Override
	public boolean isFeatureChunk(ChunkGenerator chunkGenerator, long salt, int x, int z) {
		ChunkPos chunkPos = getSpawnPointChunk(chunkGenerator);
		return chunkPos.x + this.xShift == x && chunkPos.z + this.zShift == z;
	}

	@Override
	public StructurePlacementType<?> type() {
		return IHStructurePlacementTypes.RANDOM_SPREAD;
	}

	private static ChunkPos getSpawnPointChunk(ChunkGenerator chunkGenerator) {
		if(cachedSpawnPointChunk == null) {
			BlockPos spawnPoint = chunkGenerator.climateSampler().findSpawnPosition();
			IHLogger.debug("Spawn Point is (%d, %d, %d).".formatted(spawnPoint.getX(), spawnPoint.getY(), spawnPoint.getZ()));
			cachedSpawnPointChunk = new ChunkPos(spawnPoint);
		}
		return cachedSpawnPointChunk;
	}
}
