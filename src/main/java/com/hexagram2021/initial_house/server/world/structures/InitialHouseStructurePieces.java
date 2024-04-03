package com.hexagram2021.initial_house.server.world.structures;

import com.hexagram2021.initial_house.server.config.IHServerConfig;
import com.hexagram2021.initial_house.server.register.IHStructurePieceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.List;

public class InitialHouseStructurePieces {
	public static void addPieces(StructureTemplateManager structureTemplateManager, BlockPos pos, Rotation rotation, RandomSource random, StructurePieceAccessor pieces) {
		List<ResourceLocation> initialHouses = IHServerConfig.INITIAL_HOUSE_STRUCTURES.get().stream().map(ResourceLocation::new).toList();
		ResourceLocation id = initialHouses.get(random.nextInt(initialHouses.size()));
		pieces.addPiece(new InitialHouseStructurePieces.Piece(structureTemplateManager, id, pos, rotation));
	}

	public static class Piece extends TemplateStructurePiece {
		public Piece(StructureTemplateManager structureTemplateManager, ResourceLocation location, BlockPos pos, StructurePlaceSettings settings) {
			super(
					IHStructurePieceTypes.INITIAL_HOUSE.get(), 0, structureTemplateManager,
					location, location.toString(), settings,
					pos.offset(StructureTemplate.calculateRelativePosition(settings, new BlockPos(
							-IHServerConfig.INITIAL_HOUSE_PIVOT_X.get(),
							-IHServerConfig.INITIAL_HOUSE_PIVOT_Y.get(),
							-IHServerConfig.INITIAL_HOUSE_PIVOT_Z.get()
					)))
			);
		}

		public Piece(StructureTemplateManager structureTemplateManager, ResourceLocation location, BlockPos pos, Rotation rotation) {
			this(structureTemplateManager, location, pos, makeSettings(rotation));
		}

		public Piece(StructurePieceSerializationContext context, CompoundTag tag) {
			super(
					IHStructurePieceTypes.INITIAL_HOUSE.get(), tag, context.structureTemplateManager(),
					(location) -> makeSettings(Rotation.valueOf(tag.getString("Rot")))
			);
		}

		private static StructurePlaceSettings makeSettings(Rotation rotation) {
			return new StructurePlaceSettings()
					.setRotation(rotation)
					.setMirror(Mirror.LEFT_RIGHT)
					.addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);
		}


		@Override
		protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
			super.addAdditionalSaveData(context, tag);
			tag.putString("Rot", this.placeSettings.getRotation().name());
		}

		@Override
		protected void handleDataMarker(String function, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox sbb) {
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
