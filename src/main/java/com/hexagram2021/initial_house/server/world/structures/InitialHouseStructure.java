package com.hexagram2021.initial_house.server.world.structures;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

public class InitialHouseStructure extends StructureFeature<NoneFeatureConfiguration> {
	public InitialHouseStructure(Codec<NoneFeatureConfiguration> codec) {
		super(codec, PieceGeneratorSupplier.simple(context -> true, InitialHouseStructure::generatePieces));
	}

	@Override
	public GenerationStep.Decoration step() {
		return GenerationStep.Decoration.SURFACE_STRUCTURES;
	}

	private static void generatePieces(StructurePiecesBuilder builder, PieceGenerator.Context<NoneFeatureConfiguration> context) {
		BlockPos centerOfChunk = new BlockPos(context.chunkPos().getMinBlockX() + 8, 0, context.chunkPos().getMinBlockZ() + 8);
		int landHeight = context.chunkGenerator().getBaseHeight(centerOfChunk.getX(), centerOfChunk.getZ(), Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor());
		BlockPos blockpos = new BlockPos(centerOfChunk.getX(), landHeight, centerOfChunk.getZ());
		Rotation rotation = Rotation.getRandom(context.random());
		InitialHouseStructurePieces.addPieces(context.structureManager(), blockpos, rotation, context.random(), builder);
	}
}
