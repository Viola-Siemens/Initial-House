package com.hexagram2021.initial_house;

import com.hexagram2021.initial_house.server.IHContent;
import com.hexagram2021.initial_house.server.IHSavedData;
import com.hexagram2021.initial_house.server.config.IHServerConfig;
import com.hexagram2021.initial_house.server.util.IHLogger;
import com.hexagram2021.initial_house.server.world.placements.SpawnPointOnlyPlacement;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RespawnAnchorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.saveddata.SavedData;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;

import java.util.Objects;

/**
 * 模组主入口，负责配置注册、Forge 事件挂接以及出生点相关运行时逻辑喵~
 *
 * @author liudongyu
 */
@Mod(InitialHouse.MODID)
public class InitialHouse {
	/**
	 * 模组 ID 常量喵~
	 */
	public static final String MODID = "initial_house";

	/**
	 * 初始化模组入口并完成注册绑定喵~
	 *
	 * @param modBus 模组事件总线
	 * @param modContainer 模组容器
	 */
	public InitialHouse(IEventBus modBus, ModContainer modContainer) {
		modContainer.registerConfig(ModConfig.Type.SERVER, IHServerConfig.getConfig());

		IHContent.modConstruct(modBus);

		NeoForge.EVENT_BUS.addListener(this::onPlayerRespawn);
		NeoForge.EVENT_BUS.addListener(this::onEntityJoin);
		NeoForge.EVENT_BUS.addListener(this::onOverworldLoad);
		NeoForge.EVENT_BUS.addListener(this::onServerStarted);
		NeoForge.EVENT_BUS.addListener(this::onServerClose);
	}

	private static void teleportPlayerToSpawnPoint(ServerPlayer serverPlayer) {
		BlockPos sharedSpawnPos = serverPlayer.level().getSharedSpawnPos();
		serverPlayer.teleportTo(
				sharedSpawnPos.getX() + IHServerConfig.SPAWN_POINT_SHIFT_X.get() + 0.5D,
				sharedSpawnPos.getY() + (double) IHServerConfig.SPAWN_POINT_SHIFT_Y.get(),
				sharedSpawnPos.getZ() + IHServerConfig.SPAWN_POINT_SHIFT_Z.get() + 0.5D
		);
	}

	private void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
		Player player = event.getEntity();
		if(!player.level().isClientSide && player instanceof ServerPlayer serverPlayer && IHServerConfig.DISABLE_SPAWN_POINT_RANDOM_SHIFTING.get()) {
			ServerLevel serverLevel = Objects.requireNonNull(serverPlayer.level().getServer()).getLevel(serverPlayer.getRespawnDimension());
			if (serverPlayer.getRespawnPosition() == null || serverLevel == null || !hasRespawnPosition(serverLevel, serverPlayer.getRespawnPosition())) {
				teleportPlayerToSpawnPoint(serverPlayer);
			}
		}
	}

	private void onEntityJoin(EntityJoinLevelEvent e) {
		if(!e.getLevel().isClientSide && e.getEntity() instanceof ServerPlayer serverPlayer &&
				IHServerConfig.DISABLE_SPAWN_POINT_RANDOM_SHIFTING.get() && !IHSavedData.containsPlayer(serverPlayer.getUUID())) {
			IHSavedData.addPlayer(serverPlayer.getUUID());
			teleportPlayerToSpawnPoint(serverPlayer);
		}
	}

	/**
	 * 在主世界加载后缓存出生点所在区块，供自定义结构放置逻辑使用喵~
	 *
	 * @param event 维度加载事件喵~
	 */
	public void onOverworldLoad(LevelEvent.Load event) {
		if(event.getLevel() instanceof ServerLevel world && world.dimension().equals(Level.OVERWORLD)) {
			BlockPos spawnPoint = world.getChunkSource().randomState().sampler().findSpawnPosition();
			IHLogger.debug("Spawn Point is (%d, %d, %d).".formatted(spawnPoint.getX(), spawnPoint.getY(), spawnPoint.getZ()));
			SpawnPointOnlyPlacement.setCache(new ChunkPos(spawnPoint));
		}
	}

	/**
	 * 在服务端启动后装载本模组的世界级持久化数据喵~
	 *
	 * @param event 服务端启动事件喵~
	 */
	public void onServerStarted(ServerStartedEvent event) {
		ServerLevel world = event.getServer().getLevel(Level.OVERWORLD);
		assert world != null;
		if (!world.isClientSide) {
			IHSavedData worldData = world.getDataStorage().computeIfAbsent(
					new SavedData.Factory<>(IHSavedData::new, IHSavedData::new),
					IHSavedData.SAVED_DATA_NAME
			);
			IHSavedData.setInstance(worldData);
		}
	}

	private void onServerClose(ServerStoppedEvent event) {
		SpawnPointOnlyPlacement.clearCache();
	}

	private static boolean hasRespawnPosition(ServerLevel serverLevel, BlockPos blockPos) {
		BlockState blockstate = serverLevel.getBlockState(blockPos);
		Block block = blockstate.getBlock();
		if (block instanceof RespawnAnchorBlock && blockstate.getValue(RespawnAnchorBlock.CHARGE) > 0 && RespawnAnchorBlock.canSetSpawn(serverLevel)) {
			return RespawnAnchorBlock.findStandUpPosition(EntityType.PLAYER, serverLevel, blockPos).isPresent();
		}
		if (block instanceof BedBlock && BedBlock.canSetSpawn(serverLevel)) {
			return BedBlock.findStandUpPosition(EntityType.PLAYER, serverLevel, blockPos, blockstate.getValue(HorizontalDirectionalBlock.FACING), 1.0F).isPresent();
		}
		return blockstate.getRespawnPosition(EntityType.PLAYER, serverLevel, blockPos, 1.0F).isPresent();
	}
}
