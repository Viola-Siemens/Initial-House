package com.hexagram2021.initial_house.server.register;

import com.hexagram2021.initial_house.server.world.structures.InitialHouseStructurePieces;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.hexagram2021.initial_house.InitialHouse.MODID;

public final class IHStructurePieceTypes {
	private static final DeferredRegister<StructurePieceType> REGISTER = DeferredRegister.create(Registries.STRUCTURE_PIECE, MODID);
	public static final RegistryObject<StructurePieceType> INITIAL_HOUSE = REGISTER.register("initial_house", () -> InitialHouseStructurePieces.Piece::new);

	private IHStructurePieceTypes() {
	}

	public static void init(IEventBus bus) {
		REGISTER.register(bus);
	}
}
