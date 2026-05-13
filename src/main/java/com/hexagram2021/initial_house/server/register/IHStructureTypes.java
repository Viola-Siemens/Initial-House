package com.hexagram2021.initial_house.server.register;

import com.hexagram2021.initial_house.server.world.structures.InitialHouseStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.hexagram2021.initial_house.InitialHouse.MODID;

/**
 * 自定义结构类型注册表喵~
 *
 * @author liudongyu
 */
public final class IHStructureTypes {
	private static final DeferredRegister<StructureType<?>> REGISTER = DeferredRegister.create(Registries.STRUCTURE_TYPE, MODID);
	/**
	 * 初始房屋结构类型喵~
	 */
	public static final RegistryObject<StructureType<InitialHouseStructure>> INITIAL_HOUSE = register("initial_house", () -> InitialHouseStructure.CODEC);

	private IHStructureTypes() {
	}

	/**
	 * 注册一个自定义结构类型喵~
	 *
	 * @param name 注册名喵~
	 * @param codec 结构类型喵~
	 * @param <T> 结构类型参数喵~
	 * @return 注册对象喵~
	 */
	@SuppressWarnings("SameParameterValue")
	private static <T extends Structure> RegistryObject<StructureType<T>> register(String name, StructureType<T> codec) {
		return REGISTER.register(name, () -> codec);
	}

	/**
	 * 向 Forge 事件总线注册所有结构类型喵~
	 *
	 * @param bus 模组事件总线喵~
	 */
	public static void init(IEventBus bus) {
		REGISTER.register(bus);
	}
}
