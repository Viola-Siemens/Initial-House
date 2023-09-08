package com.hexagram2021.initial_house.server.world.structures;

import com.hexagram2021.initial_house.server.config.IHServerConfig;
import com.hexagram2021.initial_house.server.register.IHStructurePieceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.List;
import java.util.Random;

public class InitialHouseStructurePieces {
	public static void addPieces(StructureManager structureManager, BlockPos pos, Rotation rotation, Random random, StructurePieceAccessor pieces) {
		List<ResourceLocation> initialHouses = IHServerConfig.INITIAL_HOUSE_STRUCTURES.get().stream().map(ResourceLocation::new).toList();
		ResourceLocation id = initialHouses.get(random.nextInt(initialHouses.size()));
		pieces.addPiece(new InitialHouseStructurePieces.Piece(structureManager, id, pos, rotation));
	}

	public static class Piece extends TemplateStructurePiece {
		public Piece(StructureManager structureManager, ResourceLocation location, BlockPos pos, Rotation rotation) {
			super(
					IHStructurePieceTypes.INITIAL_HOUSE, 0, structureManager,
					location, location.toString(), makeSettings(rotation), pos
			);
		}

		public Piece(StructurePieceSerializationContext context, CompoundTag tag) {
			super(
					IHStructurePieceTypes.INITIAL_HOUSE, tag, context.structureManager(),
					(location) -> makeSettings(Rotation.valueOf(tag.getString("Rot")))
			);
		}

		private static StructurePlaceSettings makeSettings(Rotation rotation) {
			return new StructurePlaceSettings()
					.setRotation(rotation)
					.setMirror(Mirror.LEFT_RIGHT)
					.setRotationPivot(new BlockPos(
							IHServerConfig.INITIAL_HOUSE_PIVOT_X.get(),
							IHServerConfig.INITIAL_HOUSE_PIVOT_Y.get(),
							IHServerConfig.INITIAL_HOUSE_PIVOT_Z.get()
					))
					.addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);
		}


		@Override
		protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
			super.addAdditionalSaveData(context, tag);
			tag.putString("Rot", this.placeSettings.getRotation().name());
		}

		@Override
		public void postProcess(WorldGenLevel level, StructureFeatureManager structureFeatureManager, ChunkGenerator chunkGenerator, Random random, BoundingBox bbox, ChunkPos chunkPos, BlockPos blockPos) {
			StructurePlaceSettings structureplacesettings = makeSettings(this.placeSettings.getRotation());
			BlockPos tempPos = this.templatePosition;
			this.templatePosition = tempPos.offset(StructureTemplate.calculateRelativePosition(structureplacesettings, new BlockPos(
					-IHServerConfig.INITIAL_HOUSE_PIVOT_X.get(),
					-IHServerConfig.INITIAL_HOUSE_PIVOT_Y.get(),
					-IHServerConfig.INITIAL_HOUSE_PIVOT_Z.get()
			)));
			super.postProcess(level, structureFeatureManager, chunkGenerator, random, bbox, chunkPos, blockPos);
			this.templatePosition = tempPos;
		}

		@Override
		protected void handleDataMarker(String function, BlockPos pos, ServerLevelAccessor level, Random random, BoundingBox sbb) {
			if(ResourceLocation.isValidResourceLocation(function)) {
				ResourceLocation id = new ResourceLocation(function);
				level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
				BlockPos chestPos = pos.below();
				BlockEntity blockentity = level.getBlockEntity(chestPos);
				if (blockentity instanceof RandomizableContainerBlockEntity container && sbb.isInside(chestPos)) {
					container.setLootTable(id, random.nextLong());
				}
			} else {
				level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
			}
		}
	}
}
