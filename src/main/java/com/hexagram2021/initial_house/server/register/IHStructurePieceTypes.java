package com.hexagram2021.initial_house.server.register;

import com.hexagram2021.initial_house.server.world.structures.InitialHouseStructurePieces;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;

import static com.hexagram2021.initial_house.InitialHouse.MODID;

public final class IHStructurePieceTypes {
	public static final StructurePieceType INITIAL_HOUSE = Registry.register(Registry.STRUCTURE_PIECE, new ResourceLocation(MODID, "initial_house"), InitialHouseStructurePieces.Piece::new);

	private IHStructurePieceTypes() {
	}

	public static void init() {
	}
}
