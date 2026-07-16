package com.hexagram2021.initial_house.server.register;

import com.hexagram2021.initial_house.server.world.structures.InitialHouseStructurePieces;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.hexagram2021.initial_house.InitialHouse.MODID;

/**
 * 自定义结构片段类型注册表喵~
 *
 * @author liudongyu
 */
public final class IHStructurePieceTypes {
	private static final DeferredRegister<StructurePieceType> REGISTER = DeferredRegister.create(Registries.STRUCTURE_PIECE, MODID);
	/**
	 * 初始房屋结构片段类型喵~
	 */
	public static final DeferredHolder<StructurePieceType, StructurePieceType> INITIAL_HOUSE = REGISTER.register("initial_house", () -> InitialHouseStructurePieces.Piece::new);

	private IHStructurePieceTypes() {
	}

	/**
	 * 向 Forge 事件总线注册所有结构片段类型喵~
	 *
	 * @param bus 模组事件总线喵~
	 */
	public static void init(IEventBus bus) {
		REGISTER.register(bus);
	}
}
