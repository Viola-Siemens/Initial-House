package com.hexagram2021.initial_house.server.register;

import com.hexagram2021.initial_house.server.world.placements.SpawnPointOnlyPlacement;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.hexagram2021.initial_house.InitialHouse.MODID;

public final class IHStructurePlacementTypes {
	private static final DeferredRegister<StructurePlacementType<?>> REGISTER = DeferredRegister.create(Registries.STRUCTURE_PLACEMENT, MODID);
	public static final RegistryObject<StructurePlacementType<SpawnPointOnlyPlacement>> INIT_PLACEMENT = register("init_placement", SpawnPointOnlyPlacement.CODEC);

	private IHStructurePlacementTypes() {
	}

	@SuppressWarnings("SameParameterValue")
	private static <SP extends StructurePlacement> RegistryObject<StructurePlacementType<SP>> register(String name, Codec<SP> codec) {
		return REGISTER.register(name, () -> () -> codec);
	}

	public static void init(IEventBus bus) {
		REGISTER.register(bus);
	}
}
