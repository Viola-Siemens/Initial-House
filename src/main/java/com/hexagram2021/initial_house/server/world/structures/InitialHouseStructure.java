package com.hexagram2021.initial_house.server.world.structures;

import com.hexagram2021.initial_house.server.register.IHStructureTypes;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

import java.util.Optional;

/**
 * 初始房屋结构定义，负责确定生成阶段与结构片段构建入口喵~
 *
 * @author liudongyu
 */
public class InitialHouseStructure extends Structure {
	/**
	 * 初始房屋结构的编解码器喵~
	 */
	public static final MapCodec<InitialHouseStructure> CODEC = simpleCodec(InitialHouseStructure::new);

	/**
	 * 创建初始房屋结构实例喵~
	 *
	 * @param settings 结构设置喵~
	 */
	public InitialHouseStructure(Structure.StructureSettings settings) {
		super(settings);
	}

	/**
	 * 获取结构所属的世界生成阶段喵~
	 *
	 * @return 世界生成阶段喵~
	 */
	@Override
	public GenerationStep.Decoration step() {
		return GenerationStep.Decoration.SURFACE_STRUCTURES;
	}

	/**
	 * 计算结构的生成起点喵~
	 *
	 * @param context 生成上下文喵~
	 * @return 结构生成桩喵~
	 */
	@Override
	protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
		return onTopOfChunkCenter(context, Heightmap.Types.OCEAN_FLOOR_WG, builder -> generatePieces(builder, context));
	}

	/**
	 * 获取结构注册类型喵~
	 *
	 * @return 结构类型喵~
	 */
	@Override
	public StructureType<?> type() {
		return IHStructureTypes.INITIAL_HOUSE.get();
	}

	private static void generatePieces(StructurePiecesBuilder builder, Structure.GenerationContext context) {
		BlockPos centerOfChunk = new BlockPos(context.chunkPos().getMinBlockX(), 0, context.chunkPos().getMinBlockZ());
		int landHeight = context.chunkGenerator().getBaseHeight(centerOfChunk.getX(), centerOfChunk.getZ(), Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());
		BlockPos blockpos = new BlockPos(centerOfChunk.getX(), landHeight, centerOfChunk.getZ());
		Rotation rotation = Rotation.getRandom(context.random());
		InitialHouseStructurePieces.addPieces(context.structureTemplateManager(), blockpos, rotation, context.random(), builder);
	}
}
