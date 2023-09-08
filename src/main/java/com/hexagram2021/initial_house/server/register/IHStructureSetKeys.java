package com.hexagram2021.initial_house.server.register;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.StructureSet;

import static com.hexagram2021.initial_house.InitialHouse.MODID;

public class IHStructureSetKeys {
	public static final ResourceKey<StructureSet> INITIAL_HOUSE = createKey("initial_house");

	@SuppressWarnings("SameParameterValue")
	private static ResourceKey<StructureSet> createKey(String name) {
		return ResourceKey.create(Registry.STRUCTURE_SET_REGISTRY, new ResourceLocation(MODID, name));
	}
}
