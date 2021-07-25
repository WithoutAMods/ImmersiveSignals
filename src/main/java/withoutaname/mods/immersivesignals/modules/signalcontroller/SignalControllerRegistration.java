package withoutaname.mods.immersivesignals.modules.signalcontroller;

import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.predicate.PredicateAdapterContainer;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.predicate.PredicateAdapterTile;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.predicate.redstone.RedstoneAdapterBlock;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.predicate.redstone.RedstoneAdapterContainer;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.controller.SignalControllerBlock;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.controller.SignalControllerContainer;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.controller.SignalControllerEntity;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.predicates.MultiPredicate;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.predicates.RedstonePredicate;
import withoutaname.mods.immersivesignals.setup.ModSetup;

import javax.annotation.Nonnull;

import static withoutaname.mods.immersivesignals.ImmersiveSignals.MODID;

public class SignalControllerRegistration {
	
	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
	private static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MODID);
	private static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MODID);
	
	public static void init() {
		BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
		CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
	
	public static final RegistryObject<SignalControllerBlock> SIGNAL_CONTROLLER_BLOCK = BLOCKS.register("signal_controller", SignalControllerBlock::new);
	public static final RegistryObject<BlockItem> SIGNAL_CONTROLLER_ITEM = ITEMS.register("signal_controller", () -> new BlockItem(SIGNAL_CONTROLLER_BLOCK.get(), ModSetup.DEFAULT_ITEM_PROPERTIES));
	public static final RegistryObject<BlockEntityType<SignalControllerEntity>> SIGNAL_CONTROLLER_ENTITY = TILES.register("signal_controller", () -> BlockEntityType.Builder.of(SignalControllerEntity::new, SIGNAL_CONTROLLER_BLOCK.get()).build(null));
	public static final RegistryObject<MenuType<SignalControllerContainer>> SIGNAL_CONTROLLER_CONTAINER = CONTAINERS.register("signal_controller", () -> IForgeContainerType.create((windowId, inv, data) -> {
		BlockPos pos = data.readBlockPos();
		Level level = inv.player.level;
		return new SignalControllerContainer(windowId, level, pos);
	}));
	
	public static final RegistryObject<RedstoneAdapterBlock> REDSTONE_ADAPTER_BLOCK = BLOCKS.register("redstone_adapter", RedstoneAdapterBlock::new);
	public static final RegistryObject<BlockItem> REDSTONE_ADAPTER_ITEM = ITEMS.register("redstone_adapter", () -> new BlockItem(REDSTONE_ADAPTER_BLOCK.get(), ModSetup.DEFAULT_ITEM_PROPERTIES));
	public static final RegistryObject<BlockEntityType<PredicateAdapterTile<MultiPredicate<RedstonePredicate>>>> REDSTONE_ADAPTER_ENTITY = TILES.register("redstone_adapter", () -> BlockEntityType.Builder.of(new BlockEntityType.BlockEntitySupplier<PredicateAdapterTile<MultiPredicate<RedstonePredicate>>>() {
		@Nonnull
		@Override
		public PredicateAdapterTile<MultiPredicate<RedstonePredicate>> create(@Nonnull BlockPos pos, @Nonnull BlockState state) {
			return new PredicateAdapterTile<>(REDSTONE_ADAPTER_ENTITY.get(), new MultiPredicate<>(new RedstonePredicate()), pos, state);
		}
	}, REDSTONE_ADAPTER_BLOCK.get()).build(null));
	public static final RegistryObject<MenuType<PredicateAdapterContainer<RedstonePredicate>>> REDSTONE_ADAPTER_CONTAINER = CONTAINERS.register("redstone_adapter", () -> IForgeContainerType.create((windowId, inv, data) -> {
		BlockPos pos = data.readBlockPos();
		Level level = inv.player.level;
		return new RedstoneAdapterContainer(windowId, level, pos, inv.player);
	}));
	
}
