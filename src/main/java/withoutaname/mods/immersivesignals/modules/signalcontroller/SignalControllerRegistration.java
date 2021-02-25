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
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.RedstoneAdapterBlock;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.RedstoneAdapterTile;
import withoutaname.mods.immersivesignals.setup.ModSetup;

public class SignalControllerRegistration {

	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
	private static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MODID);

	public static void init() {
		BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
	}

	public static final RegistryObject<RedstoneAdapterBlock> REDSTONE_SIGNAL_CONTROLLER = BLOCKS
			.register(RedstoneAdapterBlock.REGISTRY_NAME, RedstoneAdapterBlock::new);
	public static final RegistryObject<Item> REDSTONE_SIGNAL_CONTROLLER_ITEM = ITEMS
			.register(RedstoneAdapterBlock.REGISTRY_NAME, () -> new BlockItem(REDSTONE_SIGNAL_CONTROLLER.get(), ModSetup.defaultItemProperties));
	public static final RegistryObject<TileEntityType<RedstoneAdapterTile>> REDSTONE_SIGNAL_CONTROLLER_TILE = TILES
			.register(RedstoneAdapterBlock.REGISTRY_NAME, () -> TileEntityType.Builder.create(() -> new RedstoneAdapterTile(), REDSTONE_SIGNAL_CONTROLLER.get()).build(null));

}
