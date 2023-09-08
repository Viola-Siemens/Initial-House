package com.hexagram2021.initial_house.server;

import com.google.common.collect.Lists;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.saveddata.SavedData;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class IHSavedData extends SavedData {
	@Nullable
	private static IHSavedData INSTANCE = null;

	private final List<UUID> players;

	public static final String SAVED_DATA_NAME = "Initial-House-SavedData";
	private static final String PLAYERS_KEY = "players";
	private static final String UUID_KEY = "uuid";

	public IHSavedData() {
		super();
		this.players = Lists.newArrayList();
	}

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

	public static boolean containsPlayer(UUID uuid) {
		if(INSTANCE == null) {
			return false;
		}
		return INSTANCE.players.contains(uuid);
	}

	public static void addPlayer(UUID uuid) {
		if(INSTANCE != null) {
			INSTANCE.players.add(uuid);
			INSTANCE.setDirty();
		}
	}

	public static void setInstance(IHSavedData in) {
		INSTANCE = in;
	}
}
