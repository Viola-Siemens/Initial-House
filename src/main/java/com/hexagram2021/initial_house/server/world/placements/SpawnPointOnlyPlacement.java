package com.hexagram2021.initial_house.server.world.placements;

import com.hexagram2021.initial_house.server.register.IHStructurePlacementTypes;
import com.hexagram2021.initial_house.server.util.IHLogger;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;

import javax.annotation.Nullable;
import java.util.Optional;

public class SpawnPointOnlyPlacement extends StructurePlacement {
	private final int xShift;
	private final int zShift;

	@SuppressWarnings({"OptionalUsedAsFieldOrParameterType", "deprecation"})
	public SpawnPointOnlyPlacement(Vec3i locateOffset, StructurePlacement.FrequencyReductionMethod frequencyReductionMethod,
								   float frequency, int salt, Optional<ExclusionZone> exclusionZone, int xShift, int zShift) {
		super(locateOffset, frequencyReductionMethod, frequency, salt, exclusionZone);
		this.xShift = xShift;
		this.zShift = zShift;
	}

	@Nullable
	private static ChunkPos cachedSpawnPointChunk = null;

	public static final Codec<SpawnPointOnlyPlacement> CODEC = RecordCodecBuilder.create(
			instance -> placementCodec(instance).and(instance.group(
					Codec.intRange(-1024, 1023).fieldOf("xShift").forGetter(SpawnPointOnlyPlacement::xShift),
					Codec.intRange(-1024, 1023).fieldOf("zShift").forGetter(SpawnPointOnlyPlacement::zShift)
			)).apply(instance, SpawnPointOnlyPlacement::new)
	);

	@Override
	protected boolean isPlacementChunk(ChunkGeneratorStructureState chunkGenerator, int x, int z) {
		try {
			ChunkPos chunkPos = getSpawnPointChunk();
			return chunkPos.x + this.xShift == x && chunkPos.z + this.zShift == z;
		} catch (IllegalStateException e) {
			IHLogger.error("Error when checking spawn point:", e);
		}
		return false;
	}

	@Override
	public StructurePlacementType<?> type() {
		return IHStructurePlacementTypes.INIT_PLACEMENT.get();
	}

	public static void clearCache() {
		cachedSpawnPointChunk = null;
	}
	public static void setCache(ChunkPos newCache) {
		cachedSpawnPointChunk = newCache;
	}

	public static ChunkPos getSpawnPointChunk() throws IllegalStateException {
		if(cachedSpawnPointChunk == null) {
			throw new IllegalStateException("cachedSpawnPointChunk is null!");
		}
		return cachedSpawnPointChunk;
	}

	public int xShift() {
		return this.xShift;
	}
	public int zShift() {
		return this.zShift;
	}
}
