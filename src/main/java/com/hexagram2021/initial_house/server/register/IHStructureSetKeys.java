package com.hexagram2021.initial_house.server.register;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.StructureSet;

import static com.hexagram2021.initial_house.InitialHouse.MODID;

public final class IHStructureSetKeys {
	public static final ResourceKey<StructureSet> INITIAL_HOUSE = createKey("initial_house");

	private IHStructureSetKeys() {
	}

	@SuppressWarnings("SameParameterValue")
	private static ResourceKey<StructureSet> createKey(String name) {
		return ResourceKey.create(Registries.STRUCTURE_SET, new ResourceLocation(MODID, name));
	}

	public static void init() {
	}
}
