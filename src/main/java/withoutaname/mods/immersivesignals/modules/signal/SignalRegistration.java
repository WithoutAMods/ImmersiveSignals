package withoutaname.mods.immersivesignals.modules.signal;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import withoutaname.mods.immersivesignals.modules.signal.blocks.*;
import withoutaname.mods.immersivesignals.modules.signal.items.SignalItem;

import static withoutaname.mods.immersivesignals.ImmersiveSignals.MODID;

public class SignalRegistration {

	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

	public static void init() {
		BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}

	public static final RegistryObject<SignalFoundationBlock> SIGNAL_FOUNDATION = BLOCKS.register(SignalFoundationBlock.REGISTRY_NAME, SignalFoundationBlock::new);
	public static final RegistryObject<SignalMainBlock> SIGNAL_MAIN = BLOCKS.register(SignalMainBlock.REGISTRY_NAME, SignalMainBlock::new);
	public static final RegistryObject<SignalPostBlock> SIGNAL_POST = BLOCKS.register(SignalPostBlock.REGISTRY_NAME, SignalPostBlock::new);
	public static final RegistryObject<SignalZs3Block> SIGNAL_ZS3 = BLOCKS.register(SignalZs3Block.REGISTRY_NAME, SignalZs3Block::new);
	public static final RegistryObject<SignalZs3vBlock> SIGNAL_ZS3V = BLOCKS.register(SignalZs3vBlock.REGISTRY_NAME, SignalZs3vBlock::new);

	public static final RegistryObject<SignalItem> SIGNAL_ITEM = ITEMS.register(SignalItem.REGISTRY_NAME, SignalItem::new);
}
