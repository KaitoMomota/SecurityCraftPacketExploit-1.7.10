package net.geforcemods.securitycraft.network;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import net.geforcemods.securitycraft.api.CustomizableSCTE;
import net.geforcemods.securitycraft.api.TileEntitySCTE;
import net.geforcemods.securitycraft.blocks.BlockAlarm;
import net.geforcemods.securitycraft.blocks.BlockCageTrap;
import net.geforcemods.securitycraft.blocks.BlockFakeLava;
import net.geforcemods.securitycraft.blocks.BlockFakeLavaBase;
import net.geforcemods.securitycraft.blocks.BlockFakeWater;
import net.geforcemods.securitycraft.blocks.BlockFakeWaterBase;
import net.geforcemods.securitycraft.blocks.BlockFrame;
import net.geforcemods.securitycraft.blocks.BlockInventoryScanner;
import net.geforcemods.securitycraft.blocks.BlockInventoryScannerField;
import net.geforcemods.securitycraft.blocks.BlockIronFence;
import net.geforcemods.securitycraft.blocks.BlockIronTrapDoor;
import net.geforcemods.securitycraft.blocks.BlockKeycardReader;
import net.geforcemods.securitycraft.blocks.BlockKeypad;
import net.geforcemods.securitycraft.blocks.BlockKeypadChest;
import net.geforcemods.securitycraft.blocks.BlockKeypadFurnace;
import net.geforcemods.securitycraft.blocks.BlockLaserBlock;
import net.geforcemods.securitycraft.blocks.BlockLaserField;
import net.geforcemods.securitycraft.blocks.BlockLogger;
import net.geforcemods.securitycraft.blocks.BlockOwnable;
import net.geforcemods.securitycraft.blocks.BlockPanicButton;
import net.geforcemods.securitycraft.blocks.BlockPortableRadar;
import net.geforcemods.securitycraft.blocks.BlockProtecto;
import net.geforcemods.securitycraft.blocks.BlockReinforcedDoor;
import net.geforcemods.securitycraft.blocks.BlockReinforcedFenceGate;
import net.geforcemods.securitycraft.blocks.BlockReinforcedGlass;
import net.geforcemods.securitycraft.blocks.BlockReinforcedGlassPane;
import net.geforcemods.securitycraft.blocks.BlockReinforcedIronBars;
import net.geforcemods.securitycraft.blocks.BlockReinforcedSandstone;
import net.geforcemods.securitycraft.blocks.BlockReinforcedSlabs;
import net.geforcemods.securitycraft.blocks.BlockReinforcedStainedGlass;
import net.geforcemods.securitycraft.blocks.BlockReinforcedStainedGlassPanes;
import net.geforcemods.securitycraft.blocks.BlockReinforcedStainedHardenedClay;
import net.geforcemods.securitycraft.blocks.BlockReinforcedStairs;
import net.geforcemods.securitycraft.blocks.BlockReinforcedStoneBrick;
import net.geforcemods.securitycraft.blocks.BlockReinforcedWood;
import net.geforcemods.securitycraft.blocks.BlockReinforcedWoodSlabs;
import net.geforcemods.securitycraft.blocks.BlockRetinalScanner;
import net.geforcemods.securitycraft.blocks.BlockScannerDoor;
import net.geforcemods.securitycraft.blocks.BlockSecurityCamera;
import net.geforcemods.securitycraft.blocks.mines.BlockBouncingBetty;
import net.geforcemods.securitycraft.blocks.mines.BlockClaymore;
import net.geforcemods.securitycraft.blocks.mines.BlockFullMineBase;
import net.geforcemods.securitycraft.blocks.mines.BlockFurnaceMine;
import net.geforcemods.securitycraft.blocks.mines.BlockIMS;
import net.geforcemods.securitycraft.blocks.mines.BlockMine;
import net.geforcemods.securitycraft.blocks.mines.BlockTrackMine;
import net.geforcemods.securitycraft.entity.EntityBouncingBetty;
import net.geforcemods.securitycraft.entity.EntityIMSBomb;
import net.geforcemods.securitycraft.entity.EntitySecurityCamera;
import net.geforcemods.securitycraft.entity.EntityTaserBullet;
import net.geforcemods.securitycraft.gui.GuiHandler;
import net.geforcemods.securitycraft.items.ItemAdminTool;
import net.geforcemods.securitycraft.items.ItemBlockReinforcedColoredBlock;
import net.geforcemods.securitycraft.items.ItemBlockReinforcedPlanks;
import net.geforcemods.securitycraft.items.ItemBlockReinforcedSandstone;
import net.geforcemods.securitycraft.items.ItemBlockReinforcedSlabs;
import net.geforcemods.securitycraft.items.ItemBlockReinforcedStoneBrick;
import net.geforcemods.securitycraft.items.ItemBriefcase;
import net.geforcemods.securitycraft.items.ItemCameraMonitor;
import net.geforcemods.securitycraft.items.ItemCodebreaker;
import net.geforcemods.securitycraft.items.ItemKeyPanel;
import net.geforcemods.securitycraft.items.ItemKeycardBase;
import net.geforcemods.securitycraft.items.ItemMineRemoteAccessTool;
import net.geforcemods.securitycraft.items.ItemModifiedBucket;
import net.geforcemods.securitycraft.items.ItemModule;
import net.geforcemods.securitycraft.items.ItemReinforcedDoor;
import net.geforcemods.securitycraft.items.ItemSCManual;
import net.geforcemods.securitycraft.items.ItemScannerDoor;
import net.geforcemods.securitycraft.items.ItemTaser;
import net.geforcemods.securitycraft.items.ItemTestItem;
import net.geforcemods.securitycraft.items.ItemUniversalBlockModifier;
import net.geforcemods.securitycraft.items.ItemUniversalBlockReinforcer;
import net.geforcemods.securitycraft.items.ItemUniversalBlockRemover;
import net.geforcemods.securitycraft.items.ItemUniversalKeyChanger;
import net.geforcemods.securitycraft.items.ItemUniversalOwnerChanger;
import net.geforcemods.securitycraft.main.mod_SecurityCraft;
import net.geforcemods.securitycraft.misc.EnumCustomModules;
import net.geforcemods.securitycraft.misc.SCManualPage;
import net.geforcemods.securitycraft.network.packets.PacketCCreateLGView;
import net.geforcemods.securitycraft.network.packets.PacketCPlaySoundAtPos;
import net.geforcemods.securitycraft.network.packets.PacketCRemoveLGView;
import net.geforcemods.securitycraft.network.packets.PacketCSetCameraLocation;
import net.geforcemods.securitycraft.network.packets.PacketCSetPlayerPositionAndRotation;
import net.geforcemods.securitycraft.network.packets.PacketCSpawnLightning;
import net.geforcemods.securitycraft.network.packets.PacketCUpdateNBTTag;
import net.geforcemods.securitycraft.network.packets.PacketGivePotionEffect;
import net.geforcemods.securitycraft.network.packets.PacketSAddModules;
import net.geforcemods.securitycraft.network.packets.PacketSCheckPassword;
import net.geforcemods.securitycraft.network.packets.PacketSMountCamera;
import net.geforcemods.securitycraft.network.packets.PacketSOpenGui;
import net.geforcemods.securitycraft.network.packets.PacketSSetCameraRotation;
import net.geforcemods.securitycraft.network.packets.PacketSSetOwner;
import net.geforcemods.securitycraft.network.packets.PacketSSetPassword;
import net.geforcemods.securitycraft.network.packets.PacketSSyncTENBTTag;
import net.geforcemods.securitycraft.network.packets.PacketSToggleOption;
import net.geforcemods.securitycraft.network.packets.PacketSUpdateNBTTag;
import net.geforcemods.securitycraft.network.packets.PacketSUpdateSliderValue;
import net.geforcemods.securitycraft.network.packets.PacketSetBlock;
import net.geforcemods.securitycraft.network.packets.PacketSetBlockAndMetadata;
import net.geforcemods.securitycraft.network.packets.PacketSetBlockMetadata;
import net.geforcemods.securitycraft.network.packets.PacketSetExplosiveState;
import net.geforcemods.securitycraft.network.packets.PacketSetISType;
import net.geforcemods.securitycraft.network.packets.PacketSetKeycardLevel;
import net.geforcemods.securitycraft.network.packets.PacketUpdateLogger;
import net.geforcemods.securitycraft.tileentity.TileEntityAlarm;
import net.geforcemods.securitycraft.tileentity.TileEntityCageTrap;
import net.geforcemods.securitycraft.tileentity.TileEntityClaymore;
import net.geforcemods.securitycraft.tileentity.TileEntityFrame;
import net.geforcemods.securitycraft.tileentity.TileEntityIMS;
import net.geforcemods.securitycraft.tileentity.TileEntityInventoryScanner;
import net.geforcemods.securitycraft.tileentity.TileEntityKeycardReader;
import net.geforcemods.securitycraft.tileentity.TileEntityKeypad;
import net.geforcemods.securitycraft.tileentity.TileEntityKeypadChest;
import net.geforcemods.securitycraft.tileentity.TileEntityKeypadFurnace;
import net.geforcemods.securitycraft.tileentity.TileEntityLaserBlock;
import net.geforcemods.securitycraft.tileentity.TileEntityLogger;
import net.geforcemods.securitycraft.tileentity.TileEntityOwnable;
import net.geforcemods.securitycraft.tileentity.TileEntityPortableRadar;
import net.geforcemods.securitycraft.tileentity.TileEntityProtecto;
import net.geforcemods.securitycraft.tileentity.TileEntityRetinalScanner;
import net.geforcemods.securitycraft.tileentity.TileEntityScannerDoor;
import net.geforcemods.securitycraft.tileentity.TileEntitySecurityCamera;
import net.geforcemods.securitycraft.util.ItemUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Property;

public class ConfigurationHandler{
	private int[]  harmingPotions = {8268, 8236, 16460, 16428};
	private int[]  healingPotions = {8261, 8229, 16453, 16421};

	//******************configuration options
	public boolean allowCodebreakerItem;
	public boolean allowAdminTool;
	public boolean shouldSpawnFire;
	public boolean ableToBreakMines;
	public boolean ableToCraftKeycard1;
	public boolean ableToCraftKeycard2;
	public boolean ableToCraftKeycard3;
	public boolean ableToCraftKeycard4;
	public boolean ableToCraftKeycard5;
	public boolean ableToCraftLUKeycard;
	public boolean smallerMineExplosion;
	public boolean mineExplodesWhenInCreative;
	public boolean sayThanksMessage;
	public boolean isIrcBotEnabled;
	public boolean disconnectOnWorldClose;
	public boolean fiveMinAutoShutoff;
	public boolean useLookingGlass;
	public boolean useOldKeypadRecipe;
	public boolean checkForUpdates;
	public double portableRadarSearchRadius;
	public int usernameLoggerSearchRadius;	
	public int laserBlockRange;
	public int alarmTickDelay;
	public double alarmSoundVolume;
	public int portableRadarDelay;
	public int claymoreRange;
	public int imsRange;
	public float cameraSpeed;
	//***************************************
	
	public void setupAdditions(){
		this.setupTechnicalBlocks();
		this.setupMines();
		this.setupItems();
	}

	public void setupDebugAdditions() {
		this.setupDebuggingBlocks();
		this.setupDebuggingItems();

		this.registerDebuggingAdditions();
	}


	public void setupTechnicalBlocks(){
		mod_SecurityCraft.laserBlock = new BlockLaserBlock(Material.iron).setBlockUnbreakable().setResistance(1000).setStepSound(Block.soundTypeMetal).setCreativeTab(mod_SecurityCraft.tabSCTechnical).setBlockName("laserBlock").setBlockTextureName("securitycraft:laserBlock");
		mod_SecurityCraft.laser = new BlockLaserField(Material.rock).setBlockUnbreakable().setResistance(1000F).setBlockName("laser");

		mod_SecurityCraft.keypad = new BlockKeypad(Material.iron).setBlockUnbreakable().setResistance(1000).setStepSound(Block.soundTypeStone).setCreativeTab(mod_SecurityCraft.tabSCTechnical).setBlockName("keypad");

		mod_SecurityCraft.retinalScanner = new BlockRetinalScanner(Material.iron).setBlockUnbreakable().setResistance(1000F).setStepSound(Block.soundTypeMetal).setCreativeTab(mod_SecurityCraft.tabSCTechnical).setBlockName("retinalScanner");

		mod_SecurityCraft.reinforcedDoor = new BlockReinforcedDoor(Material.iron).setBlockUnbreakable().setResistance(1000F).setStepSound(Block.soundTypeMetal).setBlockName("ironDoorReinforced");

		mod_SecurityCraft.bogusLava = new BlockFakeLavaBase(Material.lava).setHardness(100.0F).setLightLevel(1.0F).setBlockName("bogusLava").setBlockTextureName("lava_still");
		mod_SecurityCraft.bogusLavaFlowing = new BlockFakeLava(Material.lava).setHardness(0.0F).setLightLevel(1.0F).setBlockName("bogusLavaFlowing").setBlockTextureName("lava_flow");
		mod_SecurityCraft.bogusWater = new BlockFakeWaterBase(Material.water).setHardness(100.0F).setBlockName("bogusWater").setBlockTextureName("water_still");
		mod_SecurityCraft.bogusWaterFlowing = new BlockFakeWater(Material.water).setHardness(0.0F).setBlockName("bogusWaterFlowing").setBlockTextureName("water_flow");

		mod_SecurityCraft.keycardReader = new BlockKeycardReader(Material.iron).setBlockUnbreakable().setResistance(1000F).setStepSound(Block.soundTypeMetal).setCreativeTab(mod_SecurityCraft.tabSCTechnical).setBlockName("keycardReader").setBlockTextureName("securitycraft:keycardReader");

		mod_SecurityCraft.ironTrapdoor = new BlockIronTrapDoor(Material.iron).setHardness(5.0F).setResistance(200F).setStepSound(Block.soundTypeMetal).setCreativeTab(mod_SecurityCraft.tabSCDecoration).setBlockName("ironTrapdoor").setBlockTextureName("securitycraft:ironTrapdoor");

		mod_SecurityCraft.inventoryScanner = new BlockInventoryScanner(Material.rock).setBlockUnbreakable().setResistance(1000F).setCreativeTab(mod_SecurityCraft.tabSCTechnical).setStepSound(Block.soundTypeStone).setBlockName("inventoryScanner");
		mod_SecurityCraft.inventoryScannerField = new BlockInventoryScannerField(Material.glass).setBlockUnbreakable().setResistance(1000F).setBlockName("inventoryScannerField");

		mod_SecurityCraft.cageTrap = new BlockCageTrap(Material.rock, false).setBlockUnbreakable().setResistance(1000F).setCreativeTab(mod_SecurityCraft.tabSCTechnical).setBlockName("cageTrap").setBlockTextureName("securitycraft:reinforcedIronBars");
		mod_SecurityCraft.deactivatedCageTrap = new BlockCageTrap(Material.rock, true).setBlockUnbreakable().setResistance(1000F).setBlockName("deactivatedCageTrap").setBlockTextureName("iron_bars");

		mod_SecurityCraft.portableRadar = new BlockPortableRadar(Material.circuits).setHardness(1F).setResistance(50F).setCreativeTab(mod_SecurityCraft.tabSCTechnical).setBlockName("portableRadar");

		mod_SecurityCraft.unbreakableIronBars = new BlockReinforcedIronBars("securitycraft:reinforcedIronBars", "securitycraft:reinforcedIronBars", Material.iron, true).setCreativeTab(mod_SecurityCraft.tabSCDecoration).setBlockUnbreakable().setResistance(1000F).setBlockName("reinforcedIronBars");

		mod_SecurityCraft.keypadChest = new BlockKeypadChest(0).setBlockUnbreakable().setResistance(1000F).setStepSound(Block.soundTypeWood).setCreativeTab(mod_SecurityCraft.tabSCTechnical).setBlockName("keypadChest");

		mod_SecurityCraft.usernameLogger = new BlockLogger(Material.rock).setHardness(8F).setResistance(1000F).setStepSound(Block.soundTypeStone).setCreativeTab(mod_SecurityCraft.tabSCTechnical).setBlockName("usernameLogger").setBlockTextureName("securitycraft:usernameLogger");

		mod_SecurityCraft.reinforcedGlassPane = new BlockReinforcedGlassPane("securitycraft:glass_reinforced", "glass_pane_top", Material.iron, true).setBlockUnbreakable().setResistance(1000F).setStepSound(Block.soundTypeGlass).setCreativeTab(mod_SecurityCraft.tabSCDecoration).setBlockName("reinforcedGlass");

		mod_SecurityCraft.alarm = new BlockAlarm(Material.iron, false).setBlockUnbreakable().setResistance(1000F).setTickRandomly(true).setCreativeTab(mod_SecurityCraft.tabSCTechnical).setBlockName("alarm").setBlockTextureName("securitycraft:alarmParticleTexture");
		mod_SecurityCraft.alarmLit = new BlockAlarm(Material.iron, true).setBlockUnbreakable().setResistance(1000F).setTickRandomly(true).setBlockName("alarmLit");

		mod_SecurityCraft.reinforcedStone = new BlockOwnable(Blocks.stone, Material.rock).setBlockUnbreakable().setResistance(1000F).setStepSound(Block.soundTypeStone).setCreativeTab(mod_SecurityCraft.tabSCDecoration).setBlockName("reinforcedStone").setBlockTextureName("securitycraft:reinforcedStone");

		mod_SecurityCraft.reinforcedFencegate = new BlockReinforcedFenceGate().setBlockUnbreakable().setResistance(1000F).setStepSound(Block.soundTypeMetal).setCreativeTab(mod_SecurityCraft.tabSCDecoration).setBlockName("reinforcedFenceGate");

		mod_SecurityCraft.reinforcedWoodPlanks = new BlockReinforcedWood(Material.wood).setBlockUnbreakable().setResistance(1000F).setStepSound(Block.soundTypeWood).setCreativeTab(mod_SecurityCraft.tabSCDecoration).setBlockName("reinforcedPlanks").setBlockTextureName("securitycraft:reinforcedPlanks");

		mod_SecurityCraft.panicButton = new BlockPanicButton().setBlockUnbreakable().setResistance(1000F).setCreativeTab(mod_SecurityCraft.tabSCTechnical).setBlockName("panicButton");

		mod_SecurityCraft.frame = new BlockFrame(Material.rock).setBlockUnbreakable().setResistance(1000).setStepSound(Block.soundTypeStone).setCreativeTab(mod_SecurityCraft.tabSCTechnical).setBlockName("keypadFrame").setBlockTextureName("iron_block");

		mod_SecurityCraft.keypadFurnace = new BlockKeypadFurnace(Material.iron).setBlockUnbreakable().setResistance(1000F).setCreativeTab(mod_SecurityCraft.tabSCTechnical).setStepSound(Block.soundTypeMetal).setBlockName("keypadFurnace").setBlockTextureName("securitycraft:keypadUnactive");

		mod_SecurityCraft.securityCamera = new BlockSecurityCamera(Material.iron).setBlockUnbreakable().setResistance(1000F).setCreativeTab(mod_SecurityCraft.tabSCTechnical).setBlockName("securityCamera").setBlockTextureName("securitycraft:securityCameraParticleTexture");

		mod_SecurityCraft.reinforcedStairsOak = new BlockReinforcedStairs(mod_SecurityCraft.reinforcedWoodPlanks, 0).setBlockUnbreakable().setResistance(1000).setStepSound(Block.soundTypeWood).setCreativeTab(mod_SecurityCraft.tabSCDecoration).setBlockName("reinforcedStairsOak");
		mod_SecurityCraft.reinforcedStairsSpruce = new BlockReinforcedStairs(mod_SecurityCraft.reinforcedWoodPlanks, 1).setBlockUnbreakable().setResistance(1000).setStepSound(Block.soundTypeWood).setCreativeTab(mod_SecurityCraft.tabSCDecoration).setBlockName("reinforcedStairsSpruce");
		mod_SecurityCraft.reinforcedStairsBirch = new BlockReinforcedStairs(mod_SecurityCraft.reinforcedWoodPlanks, 2).setBlockUnbreakable().setResistance(1000).setStepSound(Block.soundTypeWood).setCreativeTab(mod_SecurityCraft.tabSCDecoration).setBlockName("reinforcedStairsBirch");
		mod_SecurityCraft.reinforcedStairsJungle = new BlockReinforcedStairs(mod_SecurityCraft.reinforcedWoodPlanks, 3).setBlockUnbreakable().setResistance(1000).setStepSound(Block.soundTypeWood).setCreativeTab(mod_SecurityCraft.tabSCDecoration).setBlockName("reinforcedStairsJungle");
		mod_SecurityCraft.reinforcedStairsAcacia = new BlockReinforcedStairs(mod_SecurityCraft.reinforcedWoodPlanks, 4).setBlockUnbreakable().setResistance(1000).setStepSound(Block.soundTypeWood).setCreativeTab(mod_SecurityCraft.tabSCDecoration).setBlockName("reinforcedStairsAcacia");
		mod_SecurityCraft.reinforcedStairsDarkoak = new BlockReinforcedStairs(mod_SecurityCraft.reinforcedWoodPlanks, 5).setBlockUnbreakable().setResistance(1000).setStepSound(Block.soundTypeWood).setCreativeTab(mod_SecurityCraft.tabSCDecoration).setBlockName("reinforcedStairsDarkoak");
		mod_SecurityCraft.reinforcedStairsStone = new BlockReinforcedStairs(mod_SecurityCraft.reinforcedStone, 0).setBlockUnbreakable().setResistance(1000).setStepSound(Block.soundTypeStone).setCreativeTab(mod_SecurityCraft.tabSCDecoration).setBlockName("reinforcedStairsStone");

		mod_SecurityCraft.ironFence = new BlockIronFence("securitycraft:reinforcedDoorLower", Material.iron).setBlockUnbreakable().setResistance(1000F).setStepSound(Block.soundTypeMetal).setCreativeTab(mod_SecurityCraft.tabSCDecoration).setBlockName("scIronFence");

		mod_SecurityCraft.reinforcedGlass = new BlockReinforcedGlass(Material.glass).setBlockUnbreakable().setResistance(1000F).setStepSound(Block.soundTypeGlass).setCreativeTab(mod_SecurityCraft.tabSCDecoration).setBlockName("reinforcedGlassBlock");
		mod_SecurityCraft.reinforcedStainedGlass = new BlockReinforcedStainedGlass(Material.glass).setBlockUnbreakable().setResistance(1000F).setStepSound(Block.soundTypeGlass).setCreativeTab(mod_SecurityCraft.tabSCDecoration).setBlockName("reinforcedStainedGlass").setBlockTextureName("securitycraft:glass_reinforced");
		mod_SecurityCraft.reinforcedStainedGlassPanes = new BlockReinforcedStainedGlassPanes().setBlockUnbreakable().setResistance(1000F).setStepSound(Block.soundTypeGlass).setCreativeTab(mod_SecurityCraft.tabSCDecoration).setBlockName("reinforcedStainedGlassPanes").setBlockTextureName("securitycraft:glass_reinforced");

		mod_SecurityCraft.reinforcedDirt = new BlockOwnable(Blocks.dirt, Material.ground).setBlockUnbreakable().setResistance(1000F).setStepSound(Block.soundTypeGravel).setCreativeTab(mod_SecurityCraft.tabSCDecoration).setBlockName("reinforcedDirt").setBlockTextureName("securitycraft:reinforcedDirt");

		mod_SecurityCraft.reinforcedCobblestone = new BlockOwnable(Blocks.cobblestone, Material.rock).setBlockUnbreakable().setResistance(1000F).setStepSound(Block.soundTypeStone).setCreativeTab(mod_SecurityCraft.tabSCDecoration).setBlockName("reinforcedCobblestone").setBlockTextureName("securitycraft:reinforcedCobblestone");
		mod_SecurityCraft.reinforcedStairsCobblestone = new BlockReinforcedStairs(mod_SecurityCraft.reinforcedCobblestone, 0).setBlockUnbreakable().setResistance(1000).setStepSound(Block.soundTypeStone).setCreativeTab(mod_SecurityCraft.tabSCDecoration).setBlockName("reinforcedStairsCobblestone");

		mod_SecurityCraft.reinforcedSandstone = new BlockReinforcedSandstone().setBlockUnbreakable().setResistance(1000).setStepSound(Block.soundTypeStone).setCreativeTab(mod_SecurityCraft.tabSCDecoration).setBlockName("reinforcedSandstone").setBlockTextureName("securitycraft:reinforcedSandstone");
		mod_SecurityCraft.reinforcedStairsSandstone = new BlockReinforcedStairs(mod_SecurityCraft.reinforcedSandstone, 0).setBlockUnbreakable().setResistance(1000).setStepSound(Block.soundTypeStone).setCreativeTab(mod_SecurityCraft.tabSCDecoration).setBlockName("reinforcedStairsSandstone");

		mod_SecurityCraft.reinforcedWoodSlabs = new BlockReinforcedWoodSlabs(false).setBlockUnbreakable().setResistance(1000).setStepSound(Block.soundTypeWood).setCreativeTab(mod_SecurityCraft.tabSCDecoration).setBlockName("reinforcedWoodSlabs");
		mod_SecurityCraft.reinforcedDoubleWoodSlabs = new BlockReinforcedWoodSlabs(true).setBlockUnbreakable().setResistance(1000).setStepSound(Block.soundTypeWood).setBlockName("reinforcedDoubleWoodSlabs");
		mod_SecurityCraft.reinforcedStoneSlabs = new BlockReinforcedSlabs(false, Material.rock).setBlockUnbreakable().setResistance(1000).setStepSound(Block.soundTypeStone).setCreativeTab(mod_SecurityCraft.tabSCDecoration).setBlockName("reinforcedStoneSlabs");
		mod_SecurityCraft.reinforcedDoubleStoneSlabs = new BlockReinforcedSlabs(true, Material.rock).setBlockUnbreakable().setResistance(1000).setStepSound(Block.soundTypeStone).setBlockName("reinforcedDoubleStoneSlabs");
		mod_SecurityCraft.reinforcedDirtSlab = new BlockReinforcedSlabs(false, Material.ground).setBlockUnbreakable().setResistance(1000).setStepSound(Block.soundTypeGravel).setCreativeTab(mod_SecurityCraft.tabSCDecoration).setBlockName("reinforcedDirtSlab");
		mod_SecurityCraft.reinforcedDoubleDirtSlab = new BlockReinforcedSlabs(true, Material.ground).setBlockUnbreakable().setResistance(1000).setStepSound(Block.soundTypeGravel).setBlockName("reinforcedDoubleDirtSlab");
	
		mod_SecurityCraft.protecto = new BlockProtecto(Material.iron).setBlockUnbreakable().setResistance(1000F).setStepSound(Block.soundTypeMetal).setLightLevel(0.5F).setCreativeTab(mod_SecurityCraft.tabSCTechnical).setBlockName("protecto").setBlockTextureName("securitycraft:protectoParticleTexture");
		
		mod_SecurityCraft.scannerDoor = new BlockScannerDoor(Material.iron).setBlockUnbreakable().setResistance(1000F).setStepSound(Block.soundTypeMetal).setBlockName("scannerDoor");
		
		mod_SecurityCraft.reinforcedStoneBrick = new BlockReinforcedStoneBrick().setBlockUnbreakable().setResistance(1000).setStepSound(Block.soundTypeStone).setCreativeTab(mod_SecurityCraft.tabSCDecoration).setBlockName("reinforcedStoneBrick").setBlockTextureName("securitycraft:reinforcedStoneBrick");
		mod_SecurityCraft.reinforcedStairsStoneBrick = new BlockReinforcedStairs(mod_SecurityCraft.reinforcedStoneBrick, 0).setBlockUnbreakable().setResistance(1000).setStepSound(Block.soundTypeStone).setCreativeTab(mod_SecurityCraft.tabSCDecoration).setBlockName("reinforcedStairsStoneBrick");
		
		mod_SecurityCraft.reinforcedMossyCobblestone = new BlockOwnable(Blocks.mossy_cobblestone, Material.rock).setBlockUnbreakable().setResistance(1000F).setStepSound(Block.soundTypeStone).setCreativeTab(mod_SecurityCraft.tabSCDecoration).setBlockName("reinforcedMossyCobblestone").setBlockTextureName("securitycraft:reinforcedMossyCobblestone");
		
		mod_SecurityCraft.reinforcedBrick = new BlockOwnable(Blocks.brick_block, Material.rock).setBlockUnbreakable().setResistance(1000).setStepSound(Block.soundTypeStone).setCreativeTab(mod_SecurityCraft.tabSCDecoration).setBlockName("reinforcedBrick").setBlockTextureName("securitycraft:reinforcedBrick");
		mod_SecurityCraft.reinforcedStairsBrick = new BlockReinforcedStairs(mod_SecurityCraft.reinforcedBrick, 0).setBlockUnbreakable().setResistance(1000).setStepSound(Block.soundTypeStone).setCreativeTab(mod_SecurityCraft.tabSCDecoration).setBlockName("reinforcedStairsBrick");
		
		mod_SecurityCraft.reinforcedNetherBrick = new BlockOwnable(Blocks.nether_brick, Material.rock).setBlockUnbreakable().setResistance(1000).setStepSound(Block.soundTypeStone).setCreativeTab(mod_SecurityCraft.tabSCDecoration).setBlockName("reinforcedNetherBrick").setBlockTextureName("securitycraft:reinforcedNetherBrick");
		mod_SecurityCraft.reinforcedStairsNetherBrick = new BlockReinforcedStairs(mod_SecurityCraft.reinforcedNetherBrick, 0).setBlockUnbreakable().setResistance(1000).setStepSound(Block.soundTypeStone).setCreativeTab(mod_SecurityCraft.tabSCDecoration).setBlockName("reinforcedStairsNetherBrick");
		
		mod_SecurityCraft.reinforcedHardenedClay = new BlockOwnable(Blocks.hardened_clay, Material.rock).setBlockUnbreakable().setResistance(1000F).setStepSound(Block.soundTypePiston).setCreativeTab(mod_SecurityCraft.tabSCDecoration).setBlockName("reinforcedHardenedClay").setBlockTextureName("securitycraft:reinforcedHardenedClay");
		mod_SecurityCraft.reinforcedStainedHardenedClay = new BlockReinforcedStainedHardenedClay().setBlockUnbreakable().setResistance(1000F).setStepSound(Block.soundTypePiston).setCreativeTab(mod_SecurityCraft.tabSCDecoration).setBlockName("reinforcedStainedHardenedClay").setBlockTextureName("securitycraft:reinforcedStainedHardenedClay");
	}

	public void setupMines(){
		mod_SecurityCraft.mine = (BlockMine) new BlockMine(Material.circuits, false).setHardness(!ableToBreakMines ? -1F : 1F).setResistance(1000F).setCreativeTab(mod_SecurityCraft.tabSCMine).setBlockName("mine");
		mod_SecurityCraft.mineCut = (BlockMine) new BlockMine(Material.circuits, true).setHardness(!ableToBreakMines ? -1F : 1F).setResistance(1000F).setBlockName("mineCut");

		mod_SecurityCraft.dirtMine = new BlockFullMineBase(Material.ground, Blocks.dirt).setCreativeTab(mod_SecurityCraft.tabSCMine).setHardness(!ableToBreakMines ? -1F : 1.25F).setStepSound(Block.soundTypeGravel).setBlockName("dirtMine").setBlockTextureName("dirt");
		mod_SecurityCraft.stoneMine = new BlockFullMineBase(Material.rock, Blocks.stone).setCreativeTab(mod_SecurityCraft.tabSCMine).setHardness(!ableToBreakMines ? -1F : 2.5F).setStepSound(Block.soundTypeStone).setBlockName("stoneMine").setBlockTextureName("stone");
		mod_SecurityCraft.cobblestoneMine = new BlockFullMineBase(Material.rock, Blocks.cobblestone).setCreativeTab(mod_SecurityCraft.tabSCMine).setHardness(!ableToBreakMines ? -1F : 2.75F).setStepSound(Block.soundTypeStone).setBlockName("cobblestoneMine").setBlockTextureName("cobblestone");
		mod_SecurityCraft.sandMine = new BlockFullMineBase(Material.sand, Blocks.sand).setCreativeTab(mod_SecurityCraft.tabSCMine).setHardness(!ableToBreakMines ? -1F : 1.25F).setStepSound(Block.soundTypeSand).setBlockName("sandMine").setBlockTextureName("sand");
		mod_SecurityCraft.diamondOreMine = new BlockFullMineBase(Material.rock, Blocks.diamond_ore).setCreativeTab(mod_SecurityCraft.tabSCMine).setHardness(!ableToBreakMines ? -1F : 3.75F).setStepSound(Block.soundTypeStone).setBlockName("diamondMine").setBlockTextureName("diamond_ore");
		mod_SecurityCraft.furnaceMine = new BlockFurnaceMine(Material.rock).setCreativeTab(mod_SecurityCraft.tabSCMine).setHardness(!ableToBreakMines ? -1F : 3.75F).setStepSound(Block.soundTypeStone).setBlockName("furnaceMine");

		mod_SecurityCraft.trackMine = new BlockTrackMine().setHardness(!ableToBreakMines ? -1F : 0.7F).setStepSound(Block.soundTypeMetal).setCreativeTab(mod_SecurityCraft.tabSCMine).setBlockName("trackMine").setBlockTextureName("securitycraft:rail_mine");

		mod_SecurityCraft.bouncingBetty = new BlockBouncingBetty(Material.circuits).setHardness(!ableToBreakMines ? -1F : 1F).setResistance(1000F).setCreativeTab(mod_SecurityCraft.tabSCMine).setBlockName("bouncingBetty");

		mod_SecurityCraft.claymoreActive = new BlockClaymore(Material.circuits, true).setHardness(!ableToBreakMines ? -1F : 1F).setResistance(3F).setCreativeTab(mod_SecurityCraft.tabSCMine).setBlockName("claymoreActive").setBlockTextureName("securitycraft:claymore");
		mod_SecurityCraft.claymoreDefused = new BlockClaymore(Material.circuits, false).setHardness(!ableToBreakMines ? -1F : 1F).setResistance(3F).setBlockName("claymoreDefused").setBlockTextureName("securitycraft:claymore");

		mod_SecurityCraft.ims = new BlockIMS(Material.iron).setBlockUnbreakable().setResistance(1000F).setStepSound(Block.soundTypeMetal).setCreativeTab(mod_SecurityCraft.tabSCMine).setBlockName("ims").setBlockTextureName("securitycraft:ims");
	}

	public void setupItems(){
		mod_SecurityCraft.codebreaker = new ItemCodebreaker().setCreativeTab(mod_SecurityCraft.tabSCTechnical).setUnlocalizedName("codebreaker").setTextureName("securitycraft:CodeBreaker1");

		mod_SecurityCraft.keycards = new ItemKeycardBase().setUnlocalizedName("keycards");

		mod_SecurityCraft.reinforcedDoorItem = new ItemReinforcedDoor().setUnlocalizedName("doorIndestructibleIronItem").setCreativeTab(mod_SecurityCraft.tabSCDecoration).setTextureName("securitycraft:doorReinforcedIron");

		mod_SecurityCraft.universalBlockRemover = new ItemUniversalBlockRemover().setMaxStackSize(1).setMaxDamage(476).setCreativeTab(mod_SecurityCraft.tabSCTechnical).setUnlocalizedName("universalBlockRemover").setTextureName("securitycraft:universalBlockRemover");

		mod_SecurityCraft.remoteAccessMine = new ItemMineRemoteAccessTool().setCreativeTab(mod_SecurityCraft.tabSCTechnical).setUnlocalizedName("remoteAccessMine").setTextureName("securitycraft:remoteAccessDoor").setMaxStackSize(1);

		mod_SecurityCraft.fWaterBucket = new ItemModifiedBucket(mod_SecurityCraft.bogusWaterFlowing).setCreativeTab(mod_SecurityCraft.tabSCTechnical).setUnlocalizedName("bucketFWater").setTextureName("securitycraft:bucketFWater");
		mod_SecurityCraft.fLavaBucket = new ItemModifiedBucket(mod_SecurityCraft.bogusLavaFlowing).setCreativeTab(mod_SecurityCraft.tabSCTechnical).setUnlocalizedName("bucketFLava").setTextureName("securitycraft:bucketFLava");

		mod_SecurityCraft.universalBlockModifier = new ItemUniversalBlockModifier().setMaxStackSize(1).setCreativeTab(mod_SecurityCraft.tabSCTechnical).setUnlocalizedName("universalBlockModifier").setTextureName("securitycraft:universalBlockModifier");

		mod_SecurityCraft.redstoneModule = (ItemModule) new ItemModule(EnumCustomModules.REDSTONE, false).setUnlocalizedName("redstoneModule").setTextureName("securitycraft:redstoneModule");
		mod_SecurityCraft.whitelistModule = (ItemModule) new ItemModule(EnumCustomModules.WHITELIST, true).setUnlocalizedName("whitelistModule").setTextureName("securitycraft:whitelistModule");
		mod_SecurityCraft.blacklistModule = (ItemModule) new ItemModule(EnumCustomModules.BLACKLIST, true).setUnlocalizedName("blacklistModule").setTextureName("securitycraft:blacklistModule");
		mod_SecurityCraft.harmingModule = (ItemModule) new ItemModule(EnumCustomModules.HARMING, false).setUnlocalizedName("harmingModule").setTextureName("securitycraft:harmingModule");
		mod_SecurityCraft.smartModule = (ItemModule) new ItemModule(EnumCustomModules.SMART, false).setUnlocalizedName("smartModule").setTextureName("securitycraft:smartModule");
		mod_SecurityCraft.storageModule = (ItemModule) new ItemModule(EnumCustomModules.STORAGE, false).setUnlocalizedName("storageModule").setTextureName("securitycraft:storageModule");
		mod_SecurityCraft.disguiseModule = (ItemModule) new ItemModule(EnumCustomModules.DISGUISE, false, true, GuiHandler.DISGUISE_MODULE, 0, 1).setUnlocalizedName("disguiseModule").setTextureName("securitycraft:disguiseModule");

		mod_SecurityCraft.wireCutters = new Item().setMaxStackSize(1).setMaxDamage(476).setCreativeTab(mod_SecurityCraft.tabSCTechnical).setUnlocalizedName("wireCutters").setTextureName("securitycraft:wireCutter");

		mod_SecurityCraft.keyPanel = new ItemKeyPanel().setCreativeTab(mod_SecurityCraft.tabSCTechnical).setUnlocalizedName("keypadItem").setTextureName("securitycraft:keypadItem");

		mod_SecurityCraft.adminTool = new ItemAdminTool().setMaxStackSize(1).setUnlocalizedName("adminTool").setTextureName("securitycraft:adminTool");

		mod_SecurityCraft.cameraMonitor = new ItemCameraMonitor().setMaxStackSize(1).setCreativeTab(mod_SecurityCraft.tabSCTechnical).setUnlocalizedName("cameraMonitor").setTextureName("securitycraft:cameraMonitor");

		mod_SecurityCraft.scManual = new ItemSCManual().setMaxStackSize(1).setCreativeTab(mod_SecurityCraft.tabSCTechnical).setUnlocalizedName("scManual").setTextureName("securitycraft:scManual");

		mod_SecurityCraft.taser = new ItemTaser().setMaxStackSize(1).setCreativeTab(mod_SecurityCraft.tabSCTechnical).setUnlocalizedName("taser");

		mod_SecurityCraft.universalOwnerChanger = new ItemUniversalOwnerChanger().setMaxStackSize(1).setMaxDamage(48).setCreativeTab(mod_SecurityCraft.tabSCTechnical).setUnlocalizedName("universalOwnerChanger").setTextureName("securitycraft:universalOwnerChanger");

		mod_SecurityCraft.universalBlockReinforcerLvL1 = new ItemUniversalBlockReinforcer(300).setMaxStackSize(1).setCreativeTab(mod_SecurityCraft.tabSCTechnical).setUnlocalizedName("universalBlockReinforcerLvL1").setTextureName("securitycraft:universalBlockReinforcerLvL1");
		mod_SecurityCraft.universalBlockReinforcerLvL2 = new ItemUniversalBlockReinforcer(2700).setMaxStackSize(1).setCreativeTab(mod_SecurityCraft.tabSCTechnical).setUnlocalizedName("universalBlockReinforcerLvL2").setTextureName("securitycraft:universalBlockReinforcerLvL2");
		mod_SecurityCraft.universalBlockReinforcerLvL3 = new ItemUniversalBlockReinforcer(0).setMaxStackSize(1).setCreativeTab(mod_SecurityCraft.tabSCTechnical).setUnlocalizedName("universalBlockReinforcerLvL3").setTextureName("securitycraft:universalBlockReinforcerLvL3");
	
	    mod_SecurityCraft.briefcase = new ItemBriefcase().setMaxStackSize(1).setCreativeTab(mod_SecurityCraft.tabSCTechnical).setUnlocalizedName("briefcase");
	
	    mod_SecurityCraft.universalKeyChanger = new ItemUniversalKeyChanger().setMaxStackSize(1).setCreativeTab(mod_SecurityCraft.tabSCTechnical).setUnlocalizedName("universalKeyChanger").setTextureName("securitycraft:universalKeyChanger");
	
	    mod_SecurityCraft.scannerDoorItem = new ItemScannerDoor().setUnlocalizedName("scannerDoorItem").setCreativeTab(mod_SecurityCraft.tabSCDecoration).setTextureName("securitycraft:scannerDoor");
	}

	public void setupDebuggingBlocks() {}

	public void setupDebuggingItems(){
		mod_SecurityCraft.testItem = new ItemTestItem().setCreativeTab(mod_SecurityCraft.tabSCTechnical).setUnlocalizedName("Test");
		//mod_SecurityCraft.testChestplate = new ItemModifiedArmor(3213, armorBase, 0, 1).setUnlocalizedName("testChestplate").setCreativeTab(mod_SecurityCraft.tabSCTechnical);
	}

	public void setupConfiguration() {
		mod_SecurityCraft.configFile.load();
		
		Property dummyProp;

		dummyProp = mod_SecurityCraft.configFile.get("options", "Is codebreaker allowed?", true);
		dummyProp.setLanguageKey("config.isCodebreakerAllowed");
		allowCodebreakerItem = dummyProp.getBoolean(true);
		
		dummyProp = mod_SecurityCraft.configFile.get("options", "Is admin tool allowed?", false);
		dummyProp.setLanguageKey("config.allowAdminTool");
		allowAdminTool = dummyProp.getBoolean(false);
		
		dummyProp = mod_SecurityCraft.configFile.get("options", "Mine(s) spawn fire when detonated?", true);
		dummyProp.setLanguageKey("config.shouldSpawnFire");
		shouldSpawnFire = dummyProp.getBoolean(true);
		
		dummyProp = mod_SecurityCraft.configFile.get("options", "Are mines unbreakable?", true);
		dummyProp.setLanguageKey("config.ableToBreakMines");
		ableToBreakMines = dummyProp.getBoolean(true);
		
		dummyProp = mod_SecurityCraft.configFile.get("options", "Craftable level 1 keycard?", true);
		dummyProp.setLanguageKey("config.ableToCraftKeycard1");
		ableToCraftKeycard1 = dummyProp.getBoolean(true);
		
		dummyProp = mod_SecurityCraft.configFile.get("options", "Craftable level 2 keycard?", true);
		dummyProp.setLanguageKey("config.ableToCraftKeycard2");
		ableToCraftKeycard2 = dummyProp.getBoolean(true);
		
		dummyProp = mod_SecurityCraft.configFile.get("options", "Craftable level 3 keycard?", true);
		dummyProp.setLanguageKey("config.ableToCraftKeycard3");
		ableToCraftKeycard3 = dummyProp.getBoolean(true);
		
		dummyProp = mod_SecurityCraft.configFile.get("options", "Craftable level 4 keycard?", true);
		dummyProp.setLanguageKey("config.ableToCraftKeycard4");
		ableToCraftKeycard4 = dummyProp.getBoolean(true);
		
		dummyProp = mod_SecurityCraft.configFile.get("options", "Craftable level 5 keycard?", true);
		dummyProp.setLanguageKey("config.ableToCraftKeycard5");
		ableToCraftKeycard5 = dummyProp.getBoolean(true);
		
		dummyProp = mod_SecurityCraft.configFile.get("options", "Craftable Limited Use keycard?", true);
		dummyProp.setLanguageKey("config.ableToCraftLUKeycard");
		ableToCraftLUKeycard = dummyProp.getBoolean(true);
		
		dummyProp = mod_SecurityCraft.configFile.get("options", "Mines use a smaller explosion?", false);
		dummyProp.setLanguageKey("config.smallerMineExplosion");
		smallerMineExplosion = dummyProp.getBoolean(false);
		
		dummyProp = mod_SecurityCraft.configFile.get("options", "Mines explode when broken in Creative?", true);
		dummyProp.setLanguageKey("config.mineExplodesWhenInCreative");
		mineExplodesWhenInCreative = dummyProp.getBoolean(true);
		
		dummyProp = mod_SecurityCraft.configFile.get("options", "Monitors shutoff after 5 minutes?", true);
		dummyProp.setLanguageKey("config.fiveMinAutoShutoff");
		fiveMinAutoShutoff = dummyProp.getBoolean(true);
		
		if(!Loader.isModLoaded("LookingGlass")){
			useLookingGlass = false;
		}else{
			dummyProp = mod_SecurityCraft.configFile.get("options", "Use LookingGlass for viewing cameras?", true);
			dummyProp.setLanguageKey("config.useLookingGlass");
			useLookingGlass = dummyProp.getBoolean(true);		
		}
		
		dummyProp = mod_SecurityCraft.configFile.get("options", "Portable radar search radius:", 25);
		dummyProp.setLanguageKey("config.portableRadarSearchRadius");
		portableRadarSearchRadius = dummyProp.getDouble(25);
		
		dummyProp = mod_SecurityCraft.configFile.get("options", "Username logger search radius:", 3);
		dummyProp.setLanguageKey("config.usernameLoggerSearchRadius");
		usernameLoggerSearchRadius = dummyProp.getInt(3);
		
		dummyProp = mod_SecurityCraft.configFile.get("options", "Laser range:", 5);
		dummyProp.setLanguageKey("config.laserBlockRange");
		laserBlockRange = dummyProp.getInt(5);
		
		dummyProp = mod_SecurityCraft.configFile.get("options", "Delay between alarm sounds (seconds):", 2);
		dummyProp.setLanguageKey("config.alarmTickDelay");
		alarmTickDelay = dummyProp.getInt(2);
		
		dummyProp = mod_SecurityCraft.configFile.get("options", "Alarm sound volume:", 0.8D);
		dummyProp.setLanguageKey("config.alarmSoundVolume");
		alarmSoundVolume = dummyProp.getDouble(0.8D);
		
		dummyProp = mod_SecurityCraft.configFile.get("options", "Portable radar delay (seconds):", 4);
		dummyProp.setLanguageKey("config.portableRadarDelay");
		portableRadarDelay = dummyProp.getInt(4);
		
		dummyProp = mod_SecurityCraft.configFile.get("options", "Claymore range:", 5);
		dummyProp.setLanguageKey("config.claymoreRange");
		claymoreRange = dummyProp.getInt(5);
		
		dummyProp = mod_SecurityCraft.configFile.get("options", "IMS range:", 12);
		dummyProp.setLanguageKey("config.imsRange");
		imsRange = dummyProp.getInt(12);
		
		dummyProp = mod_SecurityCraft.configFile.get("options", "Display a 'tip' message at spawn?", true);
		dummyProp.setLanguageKey("config.sayThanksMessage");
		sayThanksMessage = dummyProp.getBoolean(true);
		
		dummyProp = mod_SecurityCraft.configFile.get("options", "Is debug mode? (not recommended!)", false);
		dummyProp.setLanguageKey("config.debuggingMode");
		mod_SecurityCraft.debuggingMode = dummyProp.getBoolean(false);
		
		dummyProp = mod_SecurityCraft.configFile.get("options", "Is IRC bot enabled?", true);
		dummyProp.setLanguageKey("config.isIrcBotEnabled");
		isIrcBotEnabled = dummyProp.getBoolean(true);
		
		dummyProp = mod_SecurityCraft.configFile.get("options", "Disconnect IRC bot on world exited?", true);
		dummyProp.setLanguageKey("config.disconnectOnWorldClose");
		disconnectOnWorldClose = dummyProp.getBoolean(true);
		
		dummyProp = mod_SecurityCraft.configFile.get("options", "Use old keypad recipe (9 buttons)?", false);
		dummyProp.setLanguageKey("config.useOldKeypadRecipe");
		useOldKeypadRecipe = dummyProp.getBoolean(false);
		
		dummyProp = mod_SecurityCraft.configFile.get("options", "Camera Speed when not using LookingGlass:", 2);
		dummyProp.setLanguageKey("config.cameraSpeed");
		cameraSpeed = dummyProp.getInt(2);
		
		dummyProp = mod_SecurityCraft.configFile.get("options", "Should check for updates on Github?", true);
		dummyProp.setLanguageKey("config.checkForUpdates");
		checkForUpdates = dummyProp.getBoolean(true);
				
		if(mod_SecurityCraft.configFile.hasChanged()){
			mod_SecurityCraft.configFile.save();
		}
	}

	public void setupGameRegistry(){
		registerBlock(mod_SecurityCraft.laserBlock);
		GameRegistry.registerBlock(mod_SecurityCraft.laser, mod_SecurityCraft.laser.getUnlocalizedName().substring(5));
		registerBlock(mod_SecurityCraft.keypad);
		registerBlock(mod_SecurityCraft.mine);
		GameRegistry.registerBlock(mod_SecurityCraft.mineCut,mod_SecurityCraft.mineCut.getUnlocalizedName().substring(5));
		registerBlock(mod_SecurityCraft.dirtMine);
		GameRegistry.registerBlock(mod_SecurityCraft.stoneMine, mod_SecurityCraft.stoneMine.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(mod_SecurityCraft.cobblestoneMine, mod_SecurityCraft.cobblestoneMine.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(mod_SecurityCraft.diamondOreMine, mod_SecurityCraft.diamondOreMine.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(mod_SecurityCraft.sandMine, mod_SecurityCraft.sandMine.getUnlocalizedName().substring(5));
		registerBlock(mod_SecurityCraft.furnaceMine);
		registerBlock(mod_SecurityCraft.retinalScanner);
		GameRegistry.registerBlock(mod_SecurityCraft.reinforcedDoor, mod_SecurityCraft.reinforcedDoor.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(mod_SecurityCraft.bogusLava, mod_SecurityCraft.bogusLava.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(mod_SecurityCraft.bogusLavaFlowing, mod_SecurityCraft.bogusLavaFlowing.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(mod_SecurityCraft.bogusWater, mod_SecurityCraft.bogusWater.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(mod_SecurityCraft.bogusWaterFlowing, mod_SecurityCraft.bogusWaterFlowing.getUnlocalizedName().substring(5));
		registerBlock(mod_SecurityCraft.keycardReader);
		registerBlock(mod_SecurityCraft.ironTrapdoor);
		registerBlock(mod_SecurityCraft.bouncingBetty);
		registerBlock(mod_SecurityCraft.inventoryScanner);
		GameRegistry.registerBlock(mod_SecurityCraft.inventoryScannerField, mod_SecurityCraft.inventoryScannerField.getUnlocalizedName().substring(5));
		registerBlock(mod_SecurityCraft.trackMine);
		registerBlock(mod_SecurityCraft.cageTrap);
		registerBlock(mod_SecurityCraft.portableRadar);
		GameRegistry.registerBlock(mod_SecurityCraft.deactivatedCageTrap, mod_SecurityCraft.deactivatedCageTrap.getUnlocalizedName().substring(5));
		registerBlock(mod_SecurityCraft.unbreakableIronBars);
		registerBlockWithCustomRecipe(mod_SecurityCraft.keypadChest, new ItemStack[]{ null, ItemUtils.toItemStack(mod_SecurityCraft.keyPanel), null, null, ItemUtils.toItemStack(Items.redstone), null, null, ItemUtils.toItemStack(Item.getItemFromBlock(Blocks.chest)), null});
		registerBlock(mod_SecurityCraft.usernameLogger);
		registerBlock(mod_SecurityCraft.reinforcedGlassPane);
		registerBlock(mod_SecurityCraft.alarm);
		GameRegistry.registerBlock(mod_SecurityCraft.alarmLit, mod_SecurityCraft.alarmLit.getUnlocalizedName().substring(5));
		registerBlock(mod_SecurityCraft.reinforcedStone);
		registerBlock(mod_SecurityCraft.reinforcedSandstone, ItemBlockReinforcedSandstone.class);
		registerBlock(mod_SecurityCraft.reinforcedDirt);
		registerBlock(mod_SecurityCraft.reinforcedCobblestone);
		registerBlock(mod_SecurityCraft.reinforcedFencegate);
		registerBlock(mod_SecurityCraft.reinforcedWoodPlanks, ItemBlockReinforcedPlanks.class);
		registerBlock(mod_SecurityCraft.panicButton);
		registerBlock(mod_SecurityCraft.frame);
		registerBlock(mod_SecurityCraft.claymoreActive);
		GameRegistry.registerBlock(mod_SecurityCraft.claymoreDefused, mod_SecurityCraft.claymoreDefused.getUnlocalizedName().substring(5));
		registerBlock(mod_SecurityCraft.keypadFurnace);
		registerBlock(mod_SecurityCraft.securityCamera);
		GameRegistry.registerBlock(mod_SecurityCraft.reinforcedStairsOak, mod_SecurityCraft.reinforcedStairsOak.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(mod_SecurityCraft.reinforcedStairsSpruce, mod_SecurityCraft.reinforcedStairsSpruce.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(mod_SecurityCraft.reinforcedStairsBirch, mod_SecurityCraft.reinforcedStairsBirch.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(mod_SecurityCraft.reinforcedStairsJungle, mod_SecurityCraft.reinforcedStairsJungle.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(mod_SecurityCraft.reinforcedStairsAcacia, mod_SecurityCraft.reinforcedStairsAcacia.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(mod_SecurityCraft.reinforcedStairsDarkoak, mod_SecurityCraft.reinforcedStairsDarkoak.getUnlocalizedName().substring(5));
		registerBlock(mod_SecurityCraft.reinforcedStairsStone);
		registerBlock(mod_SecurityCraft.reinforcedStairsCobblestone);
		registerBlock(mod_SecurityCraft.reinforcedStairsSandstone);
		registerBlock(mod_SecurityCraft.ironFence);
		registerBlock(mod_SecurityCraft.ims);
		registerBlock(mod_SecurityCraft.reinforcedGlass);
		registerBlock(mod_SecurityCraft.reinforcedStainedGlass, ItemBlockReinforcedColoredBlock.class);
		registerBlock(mod_SecurityCraft.reinforcedStainedGlassPanes, ItemBlockReinforcedColoredBlock.class);
		registerBlock(mod_SecurityCraft.reinforcedWoodSlabs, ItemBlockReinforcedSlabs.class, mod_SecurityCraft.reinforcedWoodSlabs, false, ItemBlockReinforcedSlabs.ReinforcedSlabType.WOOD);
		registerBlock(mod_SecurityCraft.reinforcedStoneSlabs, ItemBlockReinforcedSlabs.class, mod_SecurityCraft.reinforcedStoneSlabs, false, ItemBlockReinforcedSlabs.ReinforcedSlabType.OTHER);
		GameRegistry.registerBlock(mod_SecurityCraft.reinforcedDirtSlab, ItemBlockReinforcedSlabs.class, mod_SecurityCraft.reinforcedDirtSlab.getUnlocalizedName().substring(5), mod_SecurityCraft.reinforcedDirtSlab, false, ItemBlockReinforcedSlabs.ReinforcedSlabType.OTHER);
		GameRegistry.registerBlock(mod_SecurityCraft.reinforcedDoubleWoodSlabs, mod_SecurityCraft.reinforcedDoubleWoodSlabs.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(mod_SecurityCraft.reinforcedDoubleStoneSlabs, mod_SecurityCraft.reinforcedDoubleStoneSlabs.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(mod_SecurityCraft.reinforcedDoubleDirtSlab, mod_SecurityCraft.reinforcedDoubleDirtSlab.getUnlocalizedName().substring(5));
		registerBlock(mod_SecurityCraft.protecto);
		GameRegistry.registerBlock(mod_SecurityCraft.scannerDoor, mod_SecurityCraft.scannerDoor.getUnlocalizedName().substring(5));
		registerBlock(mod_SecurityCraft.reinforcedStoneBrick, ItemBlockReinforcedStoneBrick.class);
		registerBlock(mod_SecurityCraft.reinforcedStairsStoneBrick);
		registerBlock(mod_SecurityCraft.reinforcedMossyCobblestone);
		registerBlock(mod_SecurityCraft.reinforcedBrick);
		registerBlock(mod_SecurityCraft.reinforcedStairsBrick);
		registerBlock(mod_SecurityCraft.reinforcedNetherBrick);
		registerBlock(mod_SecurityCraft.reinforcedStairsNetherBrick);
		registerBlock(mod_SecurityCraft.reinforcedHardenedClay);
		registerBlock(mod_SecurityCraft.reinforcedStainedHardenedClay, ItemBlockReinforcedColoredBlock.class);

		registerItem(mod_SecurityCraft.codebreaker);
		registerItem(mod_SecurityCraft.reinforcedDoorItem, mod_SecurityCraft.reinforcedDoorItem.getUnlocalizedName().substring(5));
		registerItem(mod_SecurityCraft.scannerDoorItem, mod_SecurityCraft.scannerDoorItem.getUnlocalizedName().substring(5));
		registerItem(mod_SecurityCraft.universalBlockRemover);
		registerItem(mod_SecurityCraft.keycards);
		registerItem(mod_SecurityCraft.remoteAccessMine);
		registerItem(mod_SecurityCraft.fWaterBucket);
		registerItem(mod_SecurityCraft.fLavaBucket);
		registerItem(mod_SecurityCraft.universalBlockModifier);
		registerItem(mod_SecurityCraft.redstoneModule);
		registerItem(mod_SecurityCraft.whitelistModule);
		registerItem(mod_SecurityCraft.blacklistModule);
		registerItem(mod_SecurityCraft.harmingModule);
		registerItem(mod_SecurityCraft.smartModule);
		registerItem(mod_SecurityCraft.storageModule);
		registerItem(mod_SecurityCraft.disguiseModule);
		registerItem(mod_SecurityCraft.wireCutters);
		registerItem(mod_SecurityCraft.adminTool);
		registerItem(mod_SecurityCraft.keyPanel);
		registerItem(mod_SecurityCraft.cameraMonitor);
		registerItem(mod_SecurityCraft.taser);
		registerItem(mod_SecurityCraft.scManual);
		registerItem(mod_SecurityCraft.universalOwnerChanger);
		registerItem(mod_SecurityCraft.universalBlockReinforcerLvL1);
		registerItem(mod_SecurityCraft.universalBlockReinforcerLvL2);
		registerItem(mod_SecurityCraft.universalBlockReinforcerLvL3);
		registerItem(mod_SecurityCraft.briefcase);
		registerItem(mod_SecurityCraft.universalKeyChanger);

		GameRegistry.registerTileEntity(TileEntityOwnable.class, "abstractOwnable");
		GameRegistry.registerTileEntity(TileEntitySCTE.class, "abstractSC");
		GameRegistry.registerTileEntity(TileEntityKeypad.class, "keypad");
		GameRegistry.registerTileEntity(TileEntityLaserBlock.class, "laserBlock");
		GameRegistry.registerTileEntity(TileEntityCageTrap.class, "cageTrap");
		GameRegistry.registerTileEntity(TileEntityKeycardReader.class, "keycardReader");
		GameRegistry.registerTileEntity(TileEntityInventoryScanner.class, "inventoryScanner");
		GameRegistry.registerTileEntity(TileEntityPortableRadar.class, "portableRadar");
		GameRegistry.registerTileEntity(TileEntitySecurityCamera.class, "securityCamera");
		GameRegistry.registerTileEntity(TileEntityLogger.class, "usernameLogger");
		GameRegistry.registerTileEntity(TileEntityRetinalScanner.class, "retinalScanner");
		GameRegistry.registerTileEntity(TileEntityKeypadChest.class, "keypadChest");
		GameRegistry.registerTileEntity(TileEntityAlarm.class, "alarm");
		GameRegistry.registerTileEntity(TileEntityFrame.class, "keypadFrame");
		GameRegistry.registerTileEntity(TileEntityClaymore.class, "claymore");
		GameRegistry.registerTileEntity(TileEntityKeypadFurnace.class, "keypadFurnace");
		GameRegistry.registerTileEntity(TileEntityIMS.class, "ims");
		GameRegistry.registerTileEntity(TileEntityProtecto.class, "protecto");
		GameRegistry.registerTileEntity(CustomizableSCTE.class, "customizableSCTE");
		GameRegistry.registerTileEntity(TileEntityScannerDoor.class, "scannerDoor");

		if(useOldKeypadRecipe){
			GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.keypad, 1), new Object[]{
					"III", "III", "III", 'I', Blocks.stone_button
			});
		}else{
			GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.keyPanel, 1), new Object[]{
					"III", "IBI", "III", 'I', Blocks.stone_button, 'B', Blocks.heavy_weighted_pressure_plate
			});

			GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.frame, 1), new Object[]{
					"III", "IBI", "I I", 'I', Items.iron_ingot, 'B', Items.redstone
			});
		}

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.laserBlock, 1), new Object[]{
				"III", "IBI", "IPI", 'I', Blocks.stone, 'B', Blocks.redstone_block, 'P', Blocks.glass_pane
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.mine, 3), new Object[]{
				" I ", "IBI", 'I', Items.iron_ingot, 'B', Items.gunpowder
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.reinforcedDoorItem, 1), new Object[]{
				"III", "IDI", "III", 'I', Items.iron_ingot, 'D', Items.iron_door
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.universalBlockRemover, 1), new Object[]{
				"SII",'I', Items.iron_ingot, 'S', Items.shears
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.ironTrapdoor, 1), new Object[]{
				"###", "#P#", "###", '#', Items.iron_ingot, 'P', Blocks.trapdoor
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.keycardReader, 1), new Object[]{
				"SSS", "SHS", "SSS", 'S', Blocks.stone, 'H', Blocks.hopper
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.bouncingBetty, 1), new Object[]{
				" P ", "IBI", 'I', Items.iron_ingot, 'B', Items.gunpowder, 'P', Blocks.heavy_weighted_pressure_plate
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.codebreaker, 1), new Object[]{
				"DTD", "GSG", "RER", 'D', Items.diamond, 'T', Blocks.redstone_torch, 'G', Items.gold_ingot, 'S', Items.nether_star, 'R', Items.redstone, 'E', Items.emerald
		});

		if(ableToCraftKeycard1){
			GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.keycards, 1, 0), new Object[]{
					"III", "YYY", 'I', Items.iron_ingot, 'Y', Items.gold_ingot 
			});
		}

		if(ableToCraftKeycard2){
			GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.keycards, 1, 1), new Object[]{
					"III", "YYY", 'I', Items.iron_ingot, 'Y', Items.brick
			});
		}

		if(ableToCraftKeycard3){ 
			GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.keycards, 1, 2), new Object[]{
					"III", "YYY", 'I', Items.iron_ingot, 'Y', Items.netherbrick
			});
		}

		if(ableToCraftKeycard4){
			GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.keycards, 1, 4), new Object[]{
					"III", "DDD", 'I', Items.iron_ingot, 'D', new ItemStack(Items.dye, 1, 13)
			});
		}

		if(ableToCraftKeycard5){
			GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.keycards, 1, 5), new Object[]{
					"III", "DDD", 'I', Items.iron_ingot, 'D', new ItemStack(Items.dye, 1, 5)
			});
		}

		if(ableToCraftLUKeycard){
			GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.keycards, 1, 3), new Object[]{
					"III", "LLL", 'I', Items.iron_ingot, 'L', new ItemStack(Items.dye, 1, 4)
			});
		}

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.trackMine, 4), new Object[]{
				"X X", "X#X", "XGX", 'X', Items.iron_ingot, '#', Items.stick, 'G', Items.gunpowder
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.portableRadar, 1), new Object[]{
				"III", "ITI", "IRI", 'I', Items.iron_ingot, 'T', Blocks.redstone_torch, 'R', Items.redstone
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.keypadChest, 1), new Object[]{
				"K", "R", "C", 'K', mod_SecurityCraft.keyPanel, 'R', Items.redstone, 'C', Blocks.chest
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.remoteAccessMine, 1), new Object[]{
				" R ", " DG", "S  ", 'R', Blocks.redstone_torch, 'D', Items.diamond, 'G', Items.gold_ingot, 'S', Items.stick
		});

		for(int i = 0; i < 4; i++){
			GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.fWaterBucket, 1), new Object[]{
					"P", "B", 'P', new ItemStack(Items.potionitem, 1, harmingPotions[i]), 'B', Items.water_bucket
			});
		}

		for(int i = 0; i < 4; i++){
			GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.fLavaBucket, 1), new Object[]{
					"P", "B", 'P', new ItemStack(Items.potionitem, 1, healingPotions[i]), 'B', Items.lava_bucket
			});
		}

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.retinalScanner, 1), new Object[]{
				"SSS", "SES", "SSS", 'S', Blocks.stone, 'E', Items.ender_eye
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.inventoryScanner, 1), new Object[]{
				"SSS", "SLS", "SCS", 'S', Blocks.stone, 'L', mod_SecurityCraft.laserBlock, 'C', Blocks.ender_chest
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.cageTrap, 1), new Object[]{
				"BBB", "GRG", "III", 'B', mod_SecurityCraft.unbreakableIronBars, 'G', Items.gold_ingot, 'R', Items.redstone, 'I', Blocks.iron_block
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.alarm, 1), new Object[]{
				"GGG", "GNG", "GRG", 'G', Blocks.glass, 'R', Items.redstone, 'N', Blocks.noteblock
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.reinforcedFencegate, 1), new Object[]{
				" I ", "IFI", " I ", 'I', Items.iron_ingot, 'F', Blocks.fence_gate
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.wireCutters, 1), new Object[]{
				"SI ", "I I", " I ", 'I', Items.iron_ingot, 'S', Items.shears
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.panicButton, 1), new Object[]{
				" I ", "IBI", " R ", 'I', Items.iron_ingot, 'B', Blocks.stone_button, 'R', Items.redstone
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.whitelistModule, 1), new Object[]{
				"III", "IPI", "IPI", 'I', Items.iron_ingot, 'P', Items.paper
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.blacklistModule, 1), new Object[]{
				"III", "IPI", "IDI", 'I', Items.iron_ingot, 'P', Items.paper, 'D', new ItemStack(Items.dye, 1, 0)
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.redstoneModule, 1), new Object[]{
				"III", "IPI", "IRI", 'I', Items.iron_ingot, 'P', Items.paper, 'R', Items.redstone
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.harmingModule, 1), new Object[]{
				"III", "IPI", "IAI", 'I', Items.iron_ingot, 'P', Items.paper, 'A', Items.arrow
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.smartModule, 1), new Object[]{
				"III", "IPI", "IEI", 'I', Items.iron_ingot, 'P', Items.paper, 'E', Items.ender_pearl
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.storageModule, 1), new Object[]{
				"III", "IPI", "ICI", 'I', Items.iron_ingot, 'P', Items.paper, 'C', mod_SecurityCraft.keypadChest
		});
		
		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.disguiseModule, 1), new Object[]{
				"III", "IPI", "IAI", 'I', Items.iron_ingot, 'P', Items.paper, 'A', Items.painting
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.universalBlockModifier, 1), new Object[]{
				"ER ", "RI ", "  I", 'E', Items.emerald, 'R', Items.redstone, 'I', Items.iron_ingot
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.universalBlockModifier, 1), new Object[]{
				" RE", " IR", "I  ", 'E', Items.emerald, 'R', Items.redstone, 'I', Items.iron_ingot
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.usernameLogger, 1), new Object[]{
				"SPS", "SRS", "SSS", 'S', Blocks.stone, 'P', mod_SecurityCraft.portableRadar, 'R', Items.redstone
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.keypadFurnace, 1), new Object[]{
				"K", "F", "P", 'K', mod_SecurityCraft.frame, 'F', Blocks.furnace, 'P', mod_SecurityCraft.keyPanel
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.claymoreActive, 1), new Object[]{
				"HSH", "SBS", "RGR", 'H', Blocks.tripwire_hook, 'S', Items.string, 'B', mod_SecurityCraft.bouncingBetty, 'R', Items.redstone, 'G', Items.gunpowder
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.ironFence, 1), new Object[]{
				" I ", "IFI", " I ", 'I', Items.iron_ingot, 'F', Blocks.fence
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.reinforcedStairsStone, 4), new Object[]{
				"S  ", "SS ", "SSS", 'S', mod_SecurityCraft.reinforcedStone
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.reinforcedStairsCobblestone, 4), new Object[]{
				"S  ", "SS ", "SSS", 'S', mod_SecurityCraft.reinforcedCobblestone
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.reinforcedStairsSandstone, 4), new Object[]{
				"S  ", "SS ", "SSS", 'S', new ItemStack(mod_SecurityCraft.reinforcedSandstone, 1, 0)
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.reinforcedStairsOak, 4), new Object[]{
				"W  ", "WW ", "WWW", 'W', new ItemStack(mod_SecurityCraft.reinforcedWoodPlanks, 1, 0)
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.reinforcedStairsSpruce, 4), new Object[]{
				"W  ", "WW ", "WWW", 'W', new ItemStack(mod_SecurityCraft.reinforcedWoodPlanks, 1, 1)
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.reinforcedStairsBirch, 4), new Object[]{
				"W  ", "WW ", "WWW", 'W', new ItemStack(mod_SecurityCraft.reinforcedWoodPlanks, 1, 2)
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.reinforcedStairsJungle, 4), new Object[]{
				"W  ", "WW ", "WWW", 'W', new ItemStack(mod_SecurityCraft.reinforcedWoodPlanks, 1, 3)
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.reinforcedStairsAcacia, 4), new Object[]{
				"W  ", "WW ", "WWW", 'W', new ItemStack(mod_SecurityCraft.reinforcedWoodPlanks, 1, 4)
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.reinforcedStairsDarkoak, 4), new Object[]{
				"W  ", "WW ", "WWW", 'W', new ItemStack(mod_SecurityCraft.reinforcedWoodPlanks, 1, 5)
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.reinforcedStairsStoneBrick, 4), new Object[]{
				"S  ", "SS ", "SSS", 'S', mod_SecurityCraft.reinforcedStoneBrick
		});
		
		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.reinforcedStairsBrick, 4), new Object[]{
				"S  ", "SS ", "SSS", 'S', mod_SecurityCraft.reinforcedBrick
		});
		
		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.reinforcedStairsNetherBrick, 4), new Object[]{
				"S  ", "SS ", "SSS", 'S', mod_SecurityCraft.reinforcedNetherBrick
		});
		
		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.ims, 1), new Object[]{
				"BPB", " I ", "B B", 'B', mod_SecurityCraft.bouncingBetty, 'P', mod_SecurityCraft.portableRadar, 'I', Blocks.iron_block
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.cameraMonitor, 1), new Object[]{
				"III", "IGI", "III", 'I', Items.iron_ingot, 'G', Blocks.glass_pane
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.taser, 1), new Object[]{
				"BGI", "RSG", "  S", 'B', Items.bow, 'G', Items.gold_ingot, 'I', Items.iron_ingot, 'R', Items.redstone, 'S', Items.stick
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.securityCamera, 1), new Object[]{
				"III", "GRI", "IIS", 'I', Items.iron_ingot, 'G', mod_SecurityCraft.reinforcedGlassPane, 'R', Blocks.redstone_block, 'S', Items.stick
		});

		for(int i = 0; i < 16; i++){
			GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.reinforcedStainedGlass, 8, BlockColored.func_150031_c(i)), new Object[]{
					"###", "#X#", "###", '#', new ItemStack(mod_SecurityCraft.reinforcedGlass), 'X', new ItemStack(Items.dye, 1, i)
			});

			GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.reinforcedStainedGlassPanes, 16, i), new Object[]{
					"###", "###", '#', new ItemStack(mod_SecurityCraft.reinforcedStainedGlass, 1, i)
			});
			
			GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.reinforcedStainedHardenedClay, 8, BlockColored.func_150031_c(i)), new Object[]{
					"###", "#X#", "###", '#', new ItemStack(mod_SecurityCraft.reinforcedHardenedClay), 'X', new ItemStack(Items.dye, 1, i)
			});
		}

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.universalBlockReinforcerLvL1, 1), new Object[]{
				" DG", "RLD", "SR ", 'G', Blocks .glass, 'D', Items.diamond, 'L', mod_SecurityCraft.laserBlock, 'R', Items.redstone, 'S', Items.stick
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.universalBlockReinforcerLvL2, 1), new Object[]{
				" DG", "RLD", "SR ", 'G', new ItemStack(mod_SecurityCraft.reinforcedStainedGlass, 1, 15), 'D', Blocks.diamond_block, 'L', mod_SecurityCraft.laserBlock, 'R', Items.redstone, 'S', Items.stick
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.universalBlockReinforcerLvL3, 1), new Object[]{
				" EG", "RNE", "SR ", 'G', new ItemStack(mod_SecurityCraft.reinforcedStainedGlass, 1, 6), 'E', Blocks.emerald_block, 'N', Items.nether_star, 'R', Blocks.redstone_block, 'S', Items.stick
		});

		for(int i = 0; i < 6; i++)
		{
			GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.reinforcedWoodSlabs, 6, i), new Object[]{
					"MMM", 'M', new ItemStack(mod_SecurityCraft.reinforcedWoodPlanks, 1, i)
			});
		}

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.reinforcedStoneSlabs, 6, 0), new Object[]{
				"MMM", 'M', mod_SecurityCraft.reinforcedStone
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.reinforcedStoneSlabs, 6, 1), new Object[]{
				"MMM", 'M', mod_SecurityCraft.reinforcedCobblestone
		});
		
		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.reinforcedStoneSlabs, 6, 2), new Object[]{
				"MMM", 'M', mod_SecurityCraft.reinforcedSandstone
		});
		
		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.reinforcedDirtSlab, 6, 3), new Object[]{
				"MMM", 'M', mod_SecurityCraft.reinforcedDirt
		});
		
		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.reinforcedStoneSlabs, 6, 4), new Object[]{
				"MMM", 'M', mod_SecurityCraft.reinforcedStoneBrick
		});
		
		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.reinforcedStoneSlabs, 6, 5), new Object[]{
				"MMM", 'M', mod_SecurityCraft.reinforcedBrick
		});

		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.reinforcedStoneSlabs, 6, 6), new Object[]{
				"MMM", 'M', mod_SecurityCraft.reinforcedNetherBrick
		});
		
		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.protecto, 1), new Object[]{
				"ODO", "OEO", "OOO", 'O', Blocks.obsidian, 'D', Blocks.daylight_detector, 'E', Items.ender_eye
		});
		
		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.briefcase, 1), new Object[]{
				"SSS", "ICI", "III", 'S', Items.stick, 'I', Items.iron_ingot, 'C', Blocks.chest
		});
		
		GameRegistry.addRecipe(new ItemStack(mod_SecurityCraft.universalKeyChanger, 1), new Object[]{
				" RL", " IR", "I  ", 'R', Items.redstone, 'L', mod_SecurityCraft.laserBlock, 'I', Items.iron_ingot
		});
		
		GameRegistry.addShapelessRecipe(new ItemStack(mod_SecurityCraft.dirtMine, 1), new Object[] {Blocks.dirt, mod_SecurityCraft.mine});
		GameRegistry.addShapelessRecipe(new ItemStack(mod_SecurityCraft.stoneMine, 1), new Object[] {Blocks.stone, mod_SecurityCraft.mine});
		GameRegistry.addShapelessRecipe(new ItemStack(mod_SecurityCraft.cobblestoneMine, 1), new Object[] {Blocks.cobblestone, mod_SecurityCraft.mine});
		GameRegistry.addShapelessRecipe(new ItemStack(mod_SecurityCraft.diamondOreMine, 1), new Object[] {Blocks.diamond_ore, mod_SecurityCraft.mine});
		GameRegistry.addShapelessRecipe(new ItemStack(mod_SecurityCraft.sandMine, 1), new Object[] {Blocks.sand, mod_SecurityCraft.mine});
		GameRegistry.addShapelessRecipe(new ItemStack(mod_SecurityCraft.furnaceMine, 1), new Object[] {Blocks.furnace, mod_SecurityCraft.mine});
		GameRegistry.addShapelessRecipe(new ItemStack(mod_SecurityCraft.universalOwnerChanger, 1), new Object[] {mod_SecurityCraft.universalBlockModifier, Items.name_tag});
		GameRegistry.addShapelessRecipe(new ItemStack(mod_SecurityCraft.scannerDoorItem), new Object[]{mod_SecurityCraft.reinforcedDoorItem, mod_SecurityCraft.retinalScanner});
	}

	/**
	 * Registers the given block with GameRegistry.registerBlock(), and adds the help info for the block to the SecurityCraft manual item.
	 */
	private void registerBlock(Block block){
		GameRegistry.registerBlock(block, block.getUnlocalizedName().substring(5));

		mod_SecurityCraft.instance.manualPages.add(new SCManualPage(Item.getItemFromBlock(block), "help." + block.getUnlocalizedName().substring(5) + ".info"));
	}

	private void registerBlock(Block block, Class<? extends ItemBlock> itemClass){
		GameRegistry.registerBlock(block, itemClass, block.getUnlocalizedName().substring(5));

		mod_SecurityCraft.instance.manualPages.add(new SCManualPage(Item.getItemFromBlock(block), "help." + block.getUnlocalizedName().substring(5) + ".info"));
	}

	private void registerBlock(Block block, Class<? extends ItemBlock> itemClass, Object... constructorParams){ 
		GameRegistry.registerBlock(block, itemClass, block.getUnlocalizedName().substring(5), constructorParams);

		mod_SecurityCraft.instance.manualPages.add(new SCManualPage(Item.getItemFromBlock(block), "help." + block.getUnlocalizedName().substring(5) + ".info"));
	}
	
	/**
	 * Registers the given block with GameRegistry.registerBlock(), and adds the help info for the block to the SecurityCraft manual item.
	 * Also overrides the default recipe that would've been drawn in the manual with a new recipe.
	 * 
	 */
	private void registerBlockWithCustomRecipe(Block block, ItemStack... customRecipe){ 
		GameRegistry.registerBlock(block, block.getUnlocalizedName().substring(5));

		mod_SecurityCraft.instance.manualPages.add(new SCManualPage(Item.getItemFromBlock(block), "help." + block.getUnlocalizedName().substring(5) + ".info", customRecipe));
	}

	/**
	 * Registers the given item with GameRegistry.registerItem(), and adds the help info for the item to the SecurityCraft manual item.
	 */
	private void registerItem(Item item){
		registerItem(item, item.getUnlocalizedName().substring(5));
	}

	/**
	 * Registers the given item with GameRegistry.registerItem(), and adds the help info for the item to the SecurityCraft manual item.
	 */
	private void registerItem(Item item, String customName){
		GameRegistry.registerItem(item, customName);

		mod_SecurityCraft.instance.manualPages.add(new SCManualPage(item, "help." + item.getUnlocalizedName().substring(5) + ".info"));
	}

	public void registerDebuggingAdditions(){		
		//GameRegistry.registerItem(mod_SecurityCraft.testItem, mod_SecurityCraft.testItem.getUnlocalizedName().substring(5));
	}

	public void setupOtherRegistries(){
		EnumCustomModules.refresh();
	}

	public void setupEntityRegistry() {
		EntityRegistry.registerModEntity(EntityBouncingBetty.class, "BBetty", 0, mod_SecurityCraft.instance, 128, 1, true);
		EntityRegistry.registerModEntity(EntityTaserBullet.class, "TazerBullet", 2, mod_SecurityCraft.instance, 256, 1, true);
		EntityRegistry.registerModEntity(EntityIMSBomb.class, "IMSBomb", 3, mod_SecurityCraft.instance, 256, 1, true);
		EntityRegistry.registerModEntity(EntitySecurityCamera.class, "SecurityCamera", 4, mod_SecurityCraft.instance, 256, 20, false);
	}

	public void setupHandlers(FMLPreInitializationEvent event) {
		FMLCommonHandler.instance().bus().register(mod_SecurityCraft.eventHandler);
	} 

	public void setupPackets(SimpleNetworkWrapper network) {
		network.registerMessage(PacketSetBlock.Handler.class, PacketSetBlock.class, 1, Side.SERVER);
		network.registerMessage(PacketSetBlockMetadata.Handler.class, PacketSetBlockMetadata.class, 2, Side.SERVER);
		network.registerMessage(PacketSetISType.Handler.class, PacketSetISType.class, 3, Side.SERVER);
		network.registerMessage(PacketSetKeycardLevel.Handler.class, PacketSetKeycardLevel.class, 4, Side.SERVER);
		network.registerMessage(PacketUpdateLogger.Handler.class, PacketUpdateLogger.class, 5, Side.CLIENT);
		network.registerMessage(PacketCUpdateNBTTag.Handler.class, PacketCUpdateNBTTag.class, 6, Side.CLIENT);
		network.registerMessage(PacketSUpdateNBTTag.Handler.class, PacketSUpdateNBTTag.class, 7, Side.SERVER);
		network.registerMessage(PacketCPlaySoundAtPos.Handler.class, PacketCPlaySoundAtPos.class, 8, Side.CLIENT);
		network.registerMessage(PacketSetExplosiveState.Handler.class, PacketSetExplosiveState.class, 9, Side.SERVER);
		network.registerMessage(PacketGivePotionEffect.Handler.class, PacketGivePotionEffect.class, 10, Side.SERVER);
		network.registerMessage(PacketSetBlockAndMetadata.Handler.class, PacketSetBlockAndMetadata.class, 11, Side.SERVER);
		network.registerMessage(PacketSSetOwner.Handler.class, PacketSSetOwner.class, 12, Side.SERVER);
		network.registerMessage(PacketSAddModules.Handler.class, PacketSAddModules.class, 13, Side.SERVER);
		network.registerMessage(PacketCSetCameraLocation.Handler.class, PacketCSetCameraLocation.class, 14, Side.CLIENT);
		network.registerMessage(PacketCRemoveLGView.Handler.class, PacketCRemoveLGView.class, 15, Side.CLIENT);
		network.registerMessage(PacketCCreateLGView.Handler.class, PacketCCreateLGView.class, 16, Side.CLIENT);
		network.registerMessage(PacketSSetPassword.Handler.class, PacketSSetPassword.class, 17, Side.SERVER);
		network.registerMessage(PacketSCheckPassword.Handler.class, PacketSCheckPassword.class, 18, Side.SERVER);
		network.registerMessage(PacketSSyncTENBTTag.Handler.class, PacketSSyncTENBTTag.class, 19, Side.SERVER);
		network.registerMessage(PacketSMountCamera.Handler.class, PacketSMountCamera.class, 20, Side.SERVER);
		network.registerMessage(PacketSSetCameraRotation.Handler.class, PacketSSetCameraRotation.class, 21, Side.SERVER);
		network.registerMessage(PacketCSetPlayerPositionAndRotation.Handler.class, PacketCSetPlayerPositionAndRotation.class, 22, Side.CLIENT);
		network.registerMessage(PacketCSpawnLightning.Handler.class, PacketCSpawnLightning.class, 23, Side.CLIENT);
		network.registerMessage(PacketSOpenGui.Handler.class, PacketSOpenGui.class, 24, Side.SERVER);
		network.registerMessage(PacketSToggleOption.Handler.class, PacketSToggleOption.class, 25, Side.SERVER);
		network.registerMessage(PacketSUpdateSliderValue.Handler.class, PacketSUpdateSliderValue.class, 26, Side.SERVER);
	}
	
}
