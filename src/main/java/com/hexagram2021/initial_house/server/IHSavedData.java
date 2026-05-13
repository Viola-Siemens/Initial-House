package com.hexagram2021.initial_house.server;

import com.google.common.collect.Lists;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.saveddata.SavedData;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

/**
 * 模组世界级持久化数据，用于记录已经处理过首次出生逻辑的玩家喵~
 *
 * @author liudongyu
 */
public class IHSavedData extends SavedData {
	@Nullable
	private static IHSavedData INSTANCE = null;

	private final List<UUID> players;

	/**
	 * 持久化数据在世界存档中的名称喵~
	 */
	public static final String SAVED_DATA_NAME = "Initial-House-SavedData";
	private static final String PLAYERS_KEY = "players";
	private static final String UUID_KEY = "uuid";

	/**
	 * 创建一个空的持久化数据实例喵~
	 */
	public IHSavedData() {
		super();
		this.players = Lists.newArrayList();
	}

	/**
	 * 从 NBT 中恢复持久化数据喵~
	 *
	 * @param nbt 持久化标签喵~
	 */
	public IHSavedData(CompoundTag nbt) {
		this();
		if(nbt.contains(PLAYERS_KEY, Tag.TAG_LIST)) {
			ListTag allPlayers = nbt.getList(PLAYERS_KEY, Tag.TAG_COMPOUND);
			for(Tag tag: allPlayers) {
				CompoundTag compound = (CompoundTag)tag;
				this.players.add(compound.getUUID(UUID_KEY));
			}
		}
	}

	/**
	 * 将当前数据写回 NBT 标签喵~
	 *
	 * @param nbt 输出标签喵~
	 * @return 写入后的标签喵~
	 */
	@Override
	public CompoundTag save(CompoundTag nbt) {
		ListTag allPlayers = new ListTag();
		synchronized (this.players) {
			this.players.forEach(uuid -> {
				CompoundTag compound = new CompoundTag();
				compound.putUUID(UUID_KEY, uuid);
				allPlayers.add(compound);
			});
		}
		nbt.put(PLAYERS_KEY, allPlayers);
		return nbt;
	}

	/**
	 * 判断指定玩家是否已经记录过首次处理状态喵~
	 *
	 * @param uuid 玩家 UUID 喵~
	 * @return 若已记录则返回 true，否则返回 false 喵~
	 */
	public static boolean containsPlayer(UUID uuid) {
		if(INSTANCE == null) {
			return false;
		}
		return INSTANCE.players.contains(uuid);
	}

	/**
	 * 记录指定玩家已经完成首次出生处理喵~
	 *
	 * @param uuid 玩家 UUID 喵~
	 */
	public static void addPlayer(UUID uuid) {
		if(INSTANCE != null) {
			INSTANCE.players.add(uuid);
			INSTANCE.setDirty();
		}
	}

	/**
	 * 设置当前激活的持久化数据实例喵~
	 *
	 * @param in 持久化数据实例喵~
	 */
	public static void setInstance(IHSavedData in) {
		INSTANCE = in;
	}
}
