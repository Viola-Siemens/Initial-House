package com.hexagram2021.initial_house.server.register;

import com.hexagram2021.initial_house.server.world.structures.InitialHouseStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.hexagram2021.initial_house.InitialHouse.MODID;

public final class IHStructureTypes {
	private static final DeferredRegister<StructureType<?>> REGISTER = DeferredRegister.create(Registries.STRUCTURE_TYPE, MODID);
	public static final RegistryObject<StructureType<InitialHouseStructure>> INITIAL_HOUSE = register("initial_house", () -> InitialHouseStructure.CODEC);

	private IHStructureTypes() {
	}

	@SuppressWarnings("SameParameterValue")
	private static <T extends Structure> RegistryObject<StructureType<T>> register(String name, StructureType<T> codec) {
		return REGISTER.register(name, () -> codec);
	}

	public static void init(IEventBus bus) {
		REGISTER.register(bus);
	}
}
