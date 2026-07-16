package com.hexagram2021.initial_house.server.world.structures;

import com.hexagram2021.initial_house.server.config.IHServerConfig;
import com.hexagram2021.initial_house.server.register.IHStructurePieceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
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

/**
 * 初始房屋结构片段工具类，负责模板选择、片段构造与数据标记处理喵~
 *
 * @author liudongyu
 */
public final class InitialHouseStructurePieces {
	private static final String REGISTRY_NAME_MATCHER = "([a-z0-9_.-]+:[a-z0-9_/.-]+)";

	/**
	 * 向结构构建器中加入一个初始房屋片段喵~
	 *
	 * @param structureTemplateManager 结构模板管理器喵~
	 * @param pos 目标原点喵~
	 * @param rotation 结构旋转喵~
	 * @param random 随机源喵~
	 * @param pieces 结构片段访问器喵~
	 */
	public static void addPieces(StructureTemplateManager structureTemplateManager, BlockPos pos, Rotation rotation, RandomSource random, StructurePieceAccessor pieces) {
		List<ResourceLocation> initialHouses = IHServerConfig.INITIAL_HOUSE_STRUCTURES.get().stream().map(ResourceLocation::parse).toList();
		ResourceLocation id = initialHouses.get(random.nextInt(initialHouses.size()));
		pieces.addPiece(new InitialHouseStructurePieces.Piece(structureTemplateManager, id, pos, rotation));
	}

	/**
	 * 初始房屋的模板结构片段实现喵~
	 */
	public static class Piece extends TemplateStructurePiece {
		/**
		 * 使用现成放置设置创建结构片段喵~
		 *
		 * @param structureTemplateManager 结构模板管理器喵~
		 * @param location 模板资源位置喵~
		 * @param pos 放置原点喵~
		 * @param settings 放置设置喵~
		 */
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

		/**
		 * 根据旋转信息创建结构片段喵~
		 *
		 * @param structureTemplateManager 结构模板管理器喵~
		 * @param location 模板资源位置喵~
		 * @param pos 放置原点喵~
		 * @param rotation 结构旋转喵~
		 */
		public Piece(StructureTemplateManager structureTemplateManager, ResourceLocation location, BlockPos pos, Rotation rotation) {
			this(structureTemplateManager, location, pos, makeSettings(rotation));
		}

		/**
		 * 从序列化数据恢复结构片段喵~
		 *
		 * @param context 结构片段序列化上下文喵~
		 * @param tag 结构片段标签喵~
		 */
		public Piece(StructurePieceSerializationContext context, CompoundTag tag) {
			super(
					IHStructurePieceTypes.INITIAL_HOUSE.get(), tag, context.structureTemplateManager(),
					location -> makeSettings(Rotation.valueOf(tag.getString("Rot")))
			);
		}

		private static StructurePlaceSettings makeSettings(Rotation rotation) {
			return new StructurePlaceSettings()
					.setRotation(rotation)
					.setMirror(Mirror.LEFT_RIGHT)
					.addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);
		}

		/**
		 * 保存额外的结构片段序列化数据喵~
		 *
		 * @param context 结构片段序列化上下文喵~
		 * @param tag 输出标签喵~
		 */
		@Override
		protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
			super.addAdditionalSaveData(context, tag);
			tag.putString("Rot", this.placeSettings.getRotation().name());
		}

		/**
		 * 处理结构模板中的数据标记喵~
		 *
		 * @param function 数据标记内容喵~
		 * @param pos 标记方块位置喵~
		 * @param level 服务端世界访问器喵~
		 * @param random 随机源喵~
		 * @param sbb 当前结构包围盒喵~
		 */
		@Override
		protected void handleDataMarker(String function, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox sbb) {
			if(function.matches(REGISTRY_NAME_MATCHER)) {
				ResourceLocation id = ResourceLocation.parse(function);
				level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
				BlockPos chestPos = pos.below();
				BlockEntity blockentity = level.getBlockEntity(chestPos);
				if (blockentity instanceof RandomizableContainerBlockEntity container && sbb.isInside(chestPos)) {
					container.setLootTable(ResourceKey.create(Registries.LOOT_TABLE, id), random.nextLong());
				}
			} else {
				level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
			}
		}
	}

	private InitialHouseStructurePieces() {
	}
}
