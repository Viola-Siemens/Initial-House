package com.hexagram2021.initial_house.server.world.structures;

import com.hexagram2021.initial_house.server.register.IHStructureTypes;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

import java.util.Optional;

public class InitialHouseStructure extends Structure {
	public static final Codec<InitialHouseStructure> CODEC = simpleCodec(InitialHouseStructure::new);

	public InitialHouseStructure(Structure.StructureSettings settings) {
		super(settings);
	}

	@Override
	public GenerationStep.Decoration step() {
		return GenerationStep.Decoration.SURFACE_STRUCTURES;
	}

	@Override
	protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
		return onTopOfChunkCenter(context, Heightmap.Types.OCEAN_FLOOR_WG, (builder) -> generatePieces(builder, context));
	}

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
