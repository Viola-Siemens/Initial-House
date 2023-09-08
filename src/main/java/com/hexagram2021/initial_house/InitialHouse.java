package com.hexagram2021.initial_house;

import com.hexagram2021.initial_house.server.IHContent;
import com.hexagram2021.initial_house.server.IHSavedData;
import com.hexagram2021.initial_house.server.config.IHServerConfig;
import com.hexagram2021.initial_house.server.util.IHLogger;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RespawnAnchorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;

@Mod(InitialHouse.MODID)
public class InitialHouse {
	public static final String MODID = "initial_house";

	public InitialHouse() {
		IHLogger.logger = LogManager.getLogger(MODID);
		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, IHServerConfig.getConfig());

		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		IHContent.modConstruct(bus);

		MinecraftForge.EVENT_BUS.addListener(this::onPlayerRespawn);
		MinecraftForge.EVENT_BUS.addListener(this::onEntityJoin);
		MinecraftForge.EVENT_BUS.register(this);
	}

	private static void teleportPlayerToSpawnPoint(ServerPlayer serverPlayer) {
		ServerLevel serverLevel = serverPlayer.getLevel().getServer().getLevel(serverPlayer.getRespawnDimension());
		if(serverPlayer.getRespawnPosition() == null || serverLevel == null || !hasRespawnPosition(serverLevel, serverPlayer.getRespawnPosition())) {
			BlockPos sharedSpawnPos = serverPlayer.getLevel().getSharedSpawnPos();
			serverPlayer.teleportTo(sharedSpawnPos.getX() + 0.5D, sharedSpawnPos.getY(), sharedSpawnPos.getZ() + 0.5D);
		}
	}

	private void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		if(!player.level.isClientSide && player instanceof ServerPlayer serverPlayer && IHServerConfig.DISABLE_SPAWN_POINT_RANDOM_SHIFTING.get()) {
			teleportPlayerToSpawnPoint(serverPlayer);
		}
	}

	private void onEntityJoin(EntityJoinWorldEvent e) {
		if(!e.getWorld().isClientSide && e.getEntity() instanceof ServerPlayer serverPlayer &&
				IHServerConfig.DISABLE_SPAWN_POINT_RANDOM_SHIFTING.get() && !IHSavedData.containsPlayer(serverPlayer.getUUID())) {
			IHSavedData.addPlayer(serverPlayer.getUUID());
			teleportPlayerToSpawnPoint(serverPlayer);
		}
	}

	private static boolean hasRespawnPosition(ServerLevel serverLevel, BlockPos blockPos) {
		BlockState blockstate = serverLevel.getBlockState(blockPos);
		Block block = blockstate.getBlock();
		if (block instanceof RespawnAnchorBlock && blockstate.getValue(RespawnAnchorBlock.CHARGE) > 0 && RespawnAnchorBlock.canSetSpawn(serverLevel)) {
			return RespawnAnchorBlock.findStandUpPosition(EntityType.PLAYER, serverLevel, blockPos).isPresent();
		}
		if (block instanceof BedBlock && BedBlock.canSetSpawn(serverLevel)) {
			return BedBlock.findStandUpPosition(EntityType.PLAYER, serverLevel, blockPos, 1.0F).isPresent();
		}
		return blockstate.getRespawnPosition(EntityType.PLAYER, serverLevel, blockPos, 1.0F, null).isPresent();
	}
}
