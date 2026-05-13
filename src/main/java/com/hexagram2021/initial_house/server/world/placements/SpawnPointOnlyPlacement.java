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

/**
 * 仅允许结构生成在世界出生点区块上的自定义放置规则喵~
 *
 * @author liudongyu
 */
public class SpawnPointOnlyPlacement extends StructurePlacement {
	private final int xShift;
	private final int zShift;

	@Nullable
	private static ChunkPos cachedSpawnPointChunk = null;

	/**
	 * 该放置规则的编解码器喵~
	 */
	public static final Codec<SpawnPointOnlyPlacement> CODEC = RecordCodecBuilder.create(
			instance -> placementCodec(instance).and(instance.group(
					Codec.intRange(-1024, 1023).fieldOf("xShift").forGetter(SpawnPointOnlyPlacement::xShift),
					Codec.intRange(-1024, 1023).fieldOf("zShift").forGetter(SpawnPointOnlyPlacement::zShift)
			)).apply(instance, SpawnPointOnlyPlacement::new)
	);

	/**
	 * 创建一个仅在出生点区块附近生效的结构放置规则喵~
	 *
	 * @param locateOffset 定位偏移喵~
	 * @param frequencyReductionMethod 频率衰减方式喵~
	 * @param frequency 生成频率喵~
	 * @param salt 随机盐值喵~
	 * @param exclusionZone 排斥区域喵~
	 * @param xShift X 轴区块偏移喵~
	 * @param zShift Z 轴区块偏移喵~
	 */
	@SuppressWarnings({"OptionalUsedAsFieldOrParameterType", "deprecation"})
	public SpawnPointOnlyPlacement(Vec3i locateOffset, StructurePlacement.FrequencyReductionMethod frequencyReductionMethod,
								   float frequency, int salt, Optional<ExclusionZone> exclusionZone, int xShift, int zShift) {
		super(locateOffset, frequencyReductionMethod, frequency, salt, exclusionZone);
		this.xShift = xShift;
		this.zShift = zShift;
	}

	/**
	 * 判断当前区块是否满足出生点放置条件喵~
	 *
	 * @param chunkGenerator 结构状态对象喵~
	 * @param x 当前区块 X 坐标喵~
	 * @param z 当前区块 Z 坐标喵~
	 * @return 若允许放置则返回 true，否则返回 false 喵~
	 */
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

	/**
	 * 获取该放置规则对应的注册类型喵~
	 *
	 * @return 结构放置类型喵~
	 */
	@Override
	public StructurePlacementType<?> type() {
		return IHStructurePlacementTypes.INIT_PLACEMENT.get();
	}

	/**
	 * 清空缓存的出生点区块喵~
	 */
	public static void clearCache() {
		cachedSpawnPointChunk = null;
	}

	/**
	 * 写入缓存的出生点区块喵~
	 *
	 * @param newCache 新的出生点区块喵~
	 */
	public static void setCache(ChunkPos newCache) {
		cachedSpawnPointChunk = newCache;
	}

	/**
	 * 获取当前缓存的出生点区块喵~
	 *
	 * @return 出生点区块喵~
	 * @throws IllegalStateException 当缓存尚未初始化时抛出喵~
	 */
	public static ChunkPos getSpawnPointChunk() throws IllegalStateException {
		if(cachedSpawnPointChunk == null) {
			throw new IllegalStateException("cachedSpawnPointChunk is null!");
		}
		return cachedSpawnPointChunk;
	}

	/**
	 * 获取 X 轴区块偏移喵~
	 *
	 * @return X 轴偏移喵~
	 */
	public int xShift() {
		return this.xShift;
	}

	/**
	 * 获取 Z 轴区块偏移喵~
	 *
	 * @return Z 轴偏移喵~
	 */
	public int zShift() {
		return this.zShift;
	}
}
