package com.hexagram2021.initial_house.server.register;

import com.hexagram2021.initial_house.server.world.placements.SpawnPointOnlyPlacement;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;

import static com.hexagram2021.initial_house.InitialHouse.MODID;

public final class IHStructurePlacementTypes {
	public static final StructurePlacementType<SpawnPointOnlyPlacement> RANDOM_SPREAD = register("random_spread", SpawnPointOnlyPlacement.CODEC);

	private IHStructurePlacementTypes() {
	}

	@SuppressWarnings("SameParameterValue")
	private static <SP extends StructurePlacement> StructurePlacementType<SP> register(String name, Codec<SP> codec) {
		return Registry.register(Registry.STRUCTURE_PLACEMENT_TYPE, new ResourceLocation(MODID, name), () -> codec);
	}

	public static void init() {
	}
}
