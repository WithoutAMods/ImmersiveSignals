package withoutaname.mods.immersivesignals.modules.signalcontroller;

import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.predicate.PredicateAdapterContainer;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.predicate.PredicateAdapterTile;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.predicate.redstone.RedstoneAdapterBlock;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.predicate.redstone.RedstoneAdapterContainer;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.controller.SignalControllerBlock;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.controller.SignalControllerContainer;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.controller.SignalControllerTile;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.sections.SubSectionMarkerTile;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.sections.SubSectionMarkerBlock;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.predicates.MultiPredicate;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.predicates.RedstonePredicate;
import withoutaname.mods.immersivesignals.setup.ModSetup;

import java.util.function.Supplier;

import static withoutaname.mods.immersivesignals.ImmersiveSignals.MODID;

public class SignalControllerRegistration {

	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
	private static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MODID);
	private static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MODID);

	public static void init() {
		BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
		CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}

	public static final RegistryObject<SignalControllerBlock> SIGNAL_CONTROLLER_BLOCK = BLOCKS.register("signal_controller", SignalControllerBlock::new);
	public static final RegistryObject<BlockItem> SIGNAL_CONTROLLER_ITEM = ITEMS.register("signal_controller", () -> new BlockItem(SIGNAL_CONTROLLER_BLOCK.get(), ModSetup.defaultItemProperties));
	public static final RegistryObject<TileEntityType<SignalControllerTile>> SIGNAL_CONTROLLER_TILE = TILES.register("signal_controller", () -> TileEntityType.Builder.of(SignalControllerTile::new, SIGNAL_CONTROLLER_BLOCK.get()).build(null));
	public static final RegistryObject<ContainerType<SignalControllerContainer>> SIGNAL_CONTROLLER_CONTAINER = CONTAINERS.register("signal_controller", () -> IForgeContainerType.create((windowId, inv, data) -> {
		BlockPos pos = data.readBlockPos();
		World world = inv.player.getCommandSenderWorld();
		return new SignalControllerContainer(windowId, world, pos);
	}));

	public static final RegistryObject<RedstoneAdapterBlock> REDSTONE_ADAPTER_BLOCK = BLOCKS.register("redstone_adapter", RedstoneAdapterBlock::new);
	public static final RegistryObject<BlockItem> REDSTONE_ADAPTER_ITEM = ITEMS.register("redstone_adapter", () -> new BlockItem(REDSTONE_ADAPTER_BLOCK.get(), ModSetup.defaultItemProperties));
	public static final RegistryObject<TileEntityType<PredicateAdapterTile<MultiPredicate<RedstonePredicate>>>> REDSTONE_ADAPTER_TILE = TILES.register("redstone_adapter", () -> TileEntityType.Builder.of(new Supplier<PredicateAdapterTile<MultiPredicate<RedstonePredicate>>>() {
		@Override
		public PredicateAdapterTile<MultiPredicate<RedstonePredicate>> get() {
			return new PredicateAdapterTile<>(REDSTONE_ADAPTER_TILE.get(), new MultiPredicate<>(new RedstonePredicate()));
		}
	}, REDSTONE_ADAPTER_BLOCK.get()).build(null));
	public static final RegistryObject<ContainerType<PredicateAdapterContainer<RedstonePredicate>>> REDSTONE_ADAPTER_CONTAINER = CONTAINERS.register("redstone_adapter", () -> IForgeContainerType.create((windowId, inv, data) -> {
		BlockPos pos = data.readBlockPos();
		World world = inv.player.getCommandSenderWorld();
		return new RedstoneAdapterContainer(windowId, world, pos, inv.player);
	}));

	public static final RegistryObject<SubSectionMarkerBlock> SUB_SECTION_MARKER_BLOCK = BLOCKS.register("sub_section_marker", SubSectionMarkerBlock::new);
	public static final RegistryObject<BlockItem> SUB_SECTION_MARKER_ITEM = ITEMS.register("sub_section_marker", () -> new BlockItem(SUB_SECTION_MARKER_BLOCK.get(), ModSetup.defaultItemProperties));
	public static final RegistryObject<TileEntityType<SubSectionMarkerTile>> SUB_SECTION_MARKER_TILE = TILES.register("sub_section_marker", () -> TileEntityType.Builder.of(SubSectionMarkerTile::new, SUB_SECTION_MARKER_BLOCK.get()).build(null));
}
