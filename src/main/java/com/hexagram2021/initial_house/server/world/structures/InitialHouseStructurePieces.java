package com.hexagram2021.initial_house.server.world.structures;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.hexagram2021.initial_house.server.config.IHServerConfig;
import com.hexagram2021.initial_house.server.register.IHStructurePieceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.*;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class InitialHouseStructurePieces {
	public static void addPieces(StructureManager structureManager, BlockPos pos, Rotation rotation, Random random, StructurePieceAccessor pieces) {
		List<ResourceLocation> initialHouses = IHServerConfig.INITIAL_HOUSE_STRUCTURES.get().stream().map(ResourceLocation::new).toList();
		ResourceLocation id = initialHouses.get(random.nextInt(initialHouses.size()));
		pieces.addPiece(new InitialHouseStructurePieces.Piece(structureManager, id, pos, rotation));
	}

	@SuppressWarnings("CommentedOutCode")
	public static class Piece extends TemplateStructurePiece {
		public Piece(StructureManager structureManager, ResourceLocation location, BlockPos pos, Rotation rotation) {
			super(
					IHStructurePieceTypes.INITIAL_HOUSE, 0, structureManager,
					location, location.toString(), makeSettings(rotation),
					pos.offset(
							-IHServerConfig.INITIAL_HOUSE_PIVOT_X.get(),
							-IHServerConfig.INITIAL_HOUSE_PIVOT_Y.get(),
							-IHServerConfig.INITIAL_HOUSE_PIVOT_Z.get()
					)
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
//				CompoundTag tag = new CompoundTag();
				level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
//				try {
//					JsonObject json = GsonHelper.parse(function);
//					convertJsonToNbt(tag, json);
//				} catch (JsonParseException ignored) {
//				}
//				EntityType.loadEntityRecursive(tag, level, entity -> {
//					entity.moveTo(pos, entity.getYRot(), entity.getXRot());
//					return entity;
//				});
			}
		}

		private static void convertJsonToNbt(CompoundTag dst, JsonObject json) {
			for(Map.Entry<String, JsonElement> entry: json.entrySet()) {
				if(entry.getValue() instanceof JsonObject jsonObject) {
					CompoundTag nbt = new CompoundTag();
					convertJsonToNbt(nbt, jsonObject);
					dst.put(entry.getKey(), nbt);
				} else if(entry.getValue() instanceof JsonArray jsonArray) {
					ListTag nbt = new ListTag();
					convertArrayToListNbt(nbt, jsonArray);
					dst.put(entry.getKey(), nbt);
				} else if(entry.getValue() instanceof JsonPrimitive jsonPrimitive) {
					if(jsonPrimitive.isString()) {
						dst.put(entry.getKey(), StringTag.valueOf(jsonPrimitive.getAsString()));
					} else if(jsonPrimitive.isBoolean()) {
						dst.put(entry.getKey(), ByteTag.valueOf(jsonPrimitive.getAsBoolean()));
					} else if(jsonPrimitive.isNumber()) {
						dst.put(entry.getKey(), DoubleTag.valueOf(jsonPrimitive.getAsDouble()));
					}
				}
			}
		}

		private static void convertArrayToListNbt(ListTag dst, JsonArray json) {
			json.forEach(e -> {
				if(e instanceof JsonObject jsonObject) {
					CompoundTag nbt = new CompoundTag();
					convertJsonToNbt(nbt, jsonObject);
					dst.add(nbt);
				} else if(e instanceof JsonArray jsonArray) {
					ListTag nbt = new ListTag();
					convertArrayToListNbt(nbt, jsonArray);
					dst.add(nbt);
				} else if(e instanceof JsonPrimitive jsonPrimitive) {
					if(jsonPrimitive.isString()) {
						dst.add(StringTag.valueOf(jsonPrimitive.getAsString()));
					} else if(jsonPrimitive.isBoolean()) {
						dst.add(ByteTag.valueOf(jsonPrimitive.getAsBoolean()));
					} else if(jsonPrimitive.isNumber()) {
						dst.add(DoubleTag.valueOf(jsonPrimitive.getAsDouble()));
					}
				}
			});
		}
	}
}
