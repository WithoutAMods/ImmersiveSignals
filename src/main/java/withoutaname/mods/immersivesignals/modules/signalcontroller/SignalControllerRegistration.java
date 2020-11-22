package withoutaname.mods.immersivesignals.modules.signalcontroller;

import static withoutaname.mods.immersivesignals.ImmersiveSignals.MODID;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.RedstoneSignalControllerBlock;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.RedstoneSignalControllerTile;
import withoutaname.mods.immersivesignals.setup.ModSetup;

public class SignalControllerRegistration {

	private static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, MODID);
	private static final DeferredRegister<Item> ITEMS = new DeferredRegister<Item>(ForgeRegistries.ITEMS, MODID);
	private static final DeferredRegister<TileEntityType<?>> TILES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, MODID);

	public static void init() {
		BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
	}

	public static final RegistryObject<RedstoneSignalControllerBlock> REDSTONE_SIGNAL_CONTROLLER = BLOCKS
			.register(RedstoneSignalControllerBlock.REGISTRY_NAME, RedstoneSignalControllerBlock::new);
	public static final RegistryObject<Item> REDSTONE_SIGNAL_CONTROLLER_ITEM = ITEMS
			.register(RedstoneSignalControllerBlock.REGISTRY_NAME, () -> new BlockItem(REDSTONE_SIGNAL_CONTROLLER.get(), ModSetup.getStandardItemProperties()));
	public static final RegistryObject<TileEntityType<RedstoneSignalControllerTile>> REDSTONE_SIGNAL_CONTROLLER_TILE = TILES
			.register(RedstoneSignalControllerBlock.REGISTRY_NAME, () -> TileEntityType.Builder.create(RedstoneSignalControllerTile::new, REDSTONE_SIGNAL_CONTROLLER.get()).build(null));

}
