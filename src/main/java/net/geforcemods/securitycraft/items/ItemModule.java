package net.geforcemods.securitycraft.items;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.geforcemods.securitycraft.main.mod_SecurityCraft;
import net.geforcemods.securitycraft.misc.EnumCustomModules;
import net.geforcemods.securitycraft.util.ClientUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class ItemModule extends Item{
	
	private final EnumCustomModules module;
	private final boolean nbtCanBeModified;
	private boolean canBeCustomized;
	private int guiToOpen;
	private int numberOfItemAddons;
	private int numberOfBlockAddons;

	public ItemModule(EnumCustomModules module, boolean nbtCanBeModified){
		this(module, nbtCanBeModified, false, -1, 0, 0);
	}
	
	public ItemModule(EnumCustomModules module, boolean nbtCanBeModified, boolean canBeCustomized, int guiToOpen){
		this(module, nbtCanBeModified, canBeCustomized, guiToOpen, 0, 0);
	}
	
	public ItemModule(EnumCustomModules module, boolean nbtCanBeModified, boolean canBeCustomized, int guiToOpen, int itemAddons, int blockAddons){
		this.module = module;
		this.nbtCanBeModified = nbtCanBeModified;
		this.canBeCustomized = canBeCustomized;
		this.guiToOpen = guiToOpen;
		numberOfItemAddons = itemAddons;
		numberOfBlockAddons = blockAddons;

		this.setMaxStackSize(1);
		this.setCreativeTab(mod_SecurityCraft.tabSCTechnical);
	}
	
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) { 
    	if(!par2World.isRemote) {
	    	if(!par1ItemStack.hasTagCompound()) {
	    		par1ItemStack.stackTagCompound = new NBTTagCompound();
	    	    ClientUtils.syncItemNBT(par1ItemStack);
	    	}
	    	
	    	if(canBeCustomized()) {
	    	    par3EntityPlayer.openGui(mod_SecurityCraft.instance, guiToOpen, par2World, (int) par3EntityPlayer.posX, (int) par3EntityPlayer.posY, (int) par3EntityPlayer.posZ); 
	    	}
    	}
    	
    	return par1ItemStack;
    }
	
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		if(nbtCanBeModified || canBeCustomized()) {
			par3List.add(StatCollector.translateToLocal("tooltip.module.modifiable"));
		}
		else {
			par3List.add(StatCollector.translateToLocal("tooltip.module.notModifiable"));
		}
		
		if(nbtCanBeModified) {
			par3List.add(StatCollector.translateToLocal("tooltip.module.playerCustomization.usage"));
			
			par3List.add(" ");
			par3List.add(StatCollector.translateToLocal("tooltip.module.playerCustomization.players") + ":");
			
			if(par1ItemStack.stackTagCompound != null){
				for(int i = 1; i <= 10; i++){
					if(!par1ItemStack.stackTagCompound.getString("Player" + i).isEmpty()){
						par3List.add(par1ItemStack.stackTagCompound.getString("Player" + i));
					}
				}
			}
		}
		
		if(canBeCustomized()) {
			if(numberOfItemAddons > 0 && numberOfBlockAddons > 0) {
				par3List.add(StatCollector.translateToLocal("tooltip.module.itemAddons.usage.blocksAndItems").replace("#blocks", numberOfBlockAddons + "").replace("#items", numberOfItemAddons + ""));
			}
			
			if(numberOfItemAddons > 0 && numberOfBlockAddons == 0) {
				par3List.add(StatCollector.translateToLocal("tooltip.module.itemAddons.usage.items").replace("#", numberOfItemAddons + ""));
			}
			
			if(numberOfItemAddons == 0 && numberOfBlockAddons > 0) {
				par3List.add(StatCollector.translateToLocal("tooltip.module.itemAddons.usage.blocks").replace("#", numberOfBlockAddons + ""));
			}
			
			if(getNumberOfAddons() > 0) {
				par3List.add(" ");

				par3List.add(StatCollector.translateToLocal("tooltip.module.itemAddons.added") + ":");
				for(Item item : getItemAddons(par1ItemStack.stackTagCompound)) {
					par3List.add("- " + StatCollector.translateToLocal(item.getUnlocalizedName() + ".name"));
				}
				
				for(Block block : getBlockAddons(par1ItemStack.stackTagCompound)) {
					par3List.add("- " + StatCollector.translateToLocal(block.getLocalizedName()));
				}
			}
		}
	}

	public EnumCustomModules getModule() {
		return module;
	}
	
	public boolean canNBTBeModified(){
		return this.nbtCanBeModified;
	}
	
	public int getNumberOfAddons(){
		return numberOfItemAddons + numberOfBlockAddons;
	}
	
	public int getNumberOfItemAddons(){
		return numberOfItemAddons;
	}
	
	public int getNumberOfBlockAddons(){
		return numberOfBlockAddons;
	}
	
	public ArrayList<Item> getItemAddons(NBTTagCompound tag){
		ArrayList<Item> list = new ArrayList<Item>();
		
		if(tag == null) return list;

		NBTTagList items = tag.getTagList("ItemInventory", Constants.NBT.TAG_COMPOUND);

		for(int i = 0; i < items.tagCount(); i++) {
			NBTTagCompound item = items.getCompoundTagAt(i);
			int slot = item.getInteger("Slot");

			if(slot < numberOfItemAddons) {
				if(ItemStack.loadItemStackFromNBT(item).getUnlocalizedName().startsWith("item.")) {
				    list.add(ItemStack.loadItemStackFromNBT(item).getItem());
				}
			}
		}
		
		return list;
	}
	
	public ArrayList<Block> getBlockAddons(NBTTagCompound tag){
		ArrayList<Block> list = new ArrayList<Block>();
		
		if(tag == null) return list;
		
		NBTTagList items = tag.getTagList("ItemInventory", Constants.NBT.TAG_COMPOUND);

		for(int i = 0; i < items.tagCount(); i++) {
			NBTTagCompound item = items.getCompoundTagAt(i);
			int slot = item.getInteger("Slot");

			if(slot < numberOfBlockAddons) {
				if(ItemStack.loadItemStackFromNBT(item).getUnlocalizedName().startsWith("tile.")) {
				    list.add(Block.getBlockFromItem(ItemStack.loadItemStackFromNBT(item).getItem()));
				}
			}
		}
		
		return list;
	}
	
	public ArrayList<ItemStack> getAddons(NBTTagCompound tag){
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		
		if(tag == null) return list;
		
		NBTTagList items = tag.getTagList("ItemInventory", Constants.NBT.TAG_COMPOUND);

		for(int i = 0; i < items.tagCount(); i++) {
			NBTTagCompound item = items.getCompoundTagAt(i);
			int slot = item.getInteger("Slot");

			if(slot < numberOfBlockAddons) {
		        list.add(ItemStack.loadItemStackFromNBT(item));				
			}
		}
		
		return list;
	}

	public boolean canBeCustomized(){
		return canBeCustomized;
	}

}
