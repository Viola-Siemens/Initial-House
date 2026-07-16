package com.hexagram2021.initial_house.server;

import com.hexagram2021.initial_house.server.register.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.EventBusSubscriber;

import static com.hexagram2021.initial_house.InitialHouse.MODID;

/**
 * 服务端内容注册入口，集中初始化结构生成相关的注册项喵~
 *
 * @author liudongyu
 */
@EventBusSubscriber(modid = MODID)
public final class IHContent {
	/**
	 * 执行模组构造阶段的注册初始化喵~
	 *
	 * @param bus 模组事件总线喵~
	 */
	public static void modConstruct(IEventBus bus) {
		IHStructurePlacementTypes.init(bus);
		IHStructurePieceTypes.init(bus);
		IHStructureTypes.init(bus);
		IHStructureSetKeys.init();
	}

	private IHContent() {
	}
}
