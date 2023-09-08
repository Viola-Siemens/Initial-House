package com.hexagram2021.initial_house.server.register;

import com.hexagram2021.initial_house.server.world.placements.SpawnPointOnlyPlacement;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;

public final class IHStructureSets {
	public static final Holder<StructureSet> INITIAL_HOUSE = register(
			IHStructureSetKeys.INITIAL_HOUSE,
			IHConfiguredStructures.INITIAL_HOUSE,
			new SpawnPointOnlyPlacement(0, 0)
	);

	private IHStructureSets() {
	}

	static Holder<StructureSet> register(ResourceKey<StructureSet> key, StructureSet set) {
		return BuiltinRegistries.register(BuiltinRegistries.STRUCTURE_SETS, key, set);
	}
	@SuppressWarnings("SameParameterValue")
	static Holder<StructureSet> register(ResourceKey<StructureSet> key, Holder<ConfiguredStructureFeature<?, ?>> structure, StructurePlacement placement) {
		return register(key, new StructureSet(structure, placement));
	}

	public static void init() {
	}
}
