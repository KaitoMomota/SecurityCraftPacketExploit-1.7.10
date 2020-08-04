package net.geforcemods.securitycraft.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.geforcemods.securitycraft.api.CustomizableSCTE;
import net.geforcemods.securitycraft.main.mod_SecurityCraft;
import net.geforcemods.securitycraft.misc.CustomDamageSources;
import net.geforcemods.securitycraft.misc.EnumCustomModules;
import net.geforcemods.securitycraft.util.EntityUtils;
import net.geforcemods.securitycraft.util.ModuleUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLaserField extends Block{
	
	@SideOnly(Side.CLIENT)
	private IIcon transparentIcon;

	public BlockLaserField(Material material) {
		super(material);
		this.setBlockBounds(0.250F, 0.300F, 0.300F, 0.750F, 0.700F, 0.700F);

	}
	
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return null;
    }
	
	public boolean isOpaqueCube(){
		return false;	
	}
	
	/**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    
    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity) {
        if(!par1World.isRemote && par5Entity instanceof EntityLivingBase && !EntityUtils.doesMobHavePotionEffect((EntityLivingBase) par5Entity, Potion.invisibility)){	
			for(int i = 1; i <= mod_SecurityCraft.configHandler.laserBlockRange; i++){
				Block id = par1World.getBlock(par2 + i, par3, par4);
				if(id == mod_SecurityCraft.laserBlock){
					if(par1World.getTileEntity(par2 + i, par3, par4) instanceof CustomizableSCTE && ((CustomizableSCTE) par1World.getTileEntity(par2 + i, par3, par4)).hasModule(EnumCustomModules.WHITELIST) && ModuleUtils.getPlayersFromModule(par1World, par2 + i, par3, par4, EnumCustomModules.WHITELIST).contains(((EntityLivingBase) par5Entity).getCommandSenderName().toLowerCase())){ return; }
					par1World.setBlockMetadataWithNotify(par2 + i, par3, par4, 2, 3);
					par1World.notifyBlocksOfNeighborChange(par2 + i, par3, par4, mod_SecurityCraft.laserBlock);
					par1World.scheduleBlockUpdate(par2 + i, par3, par4, mod_SecurityCraft.laserBlock, 50);
					par1World.notifyBlocksOfNeighborChange(par2 + i, par3, par4, mod_SecurityCraft.laserBlock);
					
					if(par1World.getTileEntity(par2 + i, par3, par4) instanceof CustomizableSCTE && ((CustomizableSCTE) par1World.getTileEntity(par2 + i, par3, par4)).hasModule(EnumCustomModules.HARMING)){
						((EntityLivingBase) par5Entity).attackEntityFrom(CustomDamageSources.laser, 10F);
					}
				}else{
					continue;
				}
			}
			
			for(int i = 0; i <= mod_SecurityCraft.configHandler.laserBlockRange; i++){
				Block id = par1World.getBlock(par2 - i, par3, par4);
				if(id == mod_SecurityCraft.laserBlock){
					if(par1World.getTileEntity(par2 - i, par3, par4) instanceof CustomizableSCTE && ((CustomizableSCTE) par1World.getTileEntity(par2 - i, par3, par4)).hasModule(EnumCustomModules.WHITELIST) && ModuleUtils.getPlayersFromModule(par1World, par2 - i, par3, par4, EnumCustomModules.WHITELIST).contains(((EntityLivingBase) par5Entity).getCommandSenderName().toLowerCase())){ return; }
					par1World.setBlockMetadataWithNotify(par2 - i, par3, par4, 2, 3);
					par1World.notifyBlocksOfNeighborChange(par2 - i, par3, par4, mod_SecurityCraft.laserBlock);
					par1World.scheduleBlockUpdate(par2 - i, par3, par4, mod_SecurityCraft.laserBlock, 50);
					par1World.notifyBlocksOfNeighborChange(par2 - i, par3, par4, mod_SecurityCraft.laserBlock);

					if(par1World.getTileEntity(par2 - i, par3, par4) instanceof CustomizableSCTE && ((CustomizableSCTE) par1World.getTileEntity(par2 - i, par3, par4)).hasModule(EnumCustomModules.HARMING)){
						((EntityLivingBase) par5Entity).attackEntityFrom(CustomDamageSources.laser, 10F);
					}
				}else{
					continue;
				}
			}
			
			for(int i = 0; i <= mod_SecurityCraft.configHandler.laserBlockRange; i++){
				Block id = par1World.getBlock(par2, par3, par4 + i);
				if(id == mod_SecurityCraft.laserBlock){
					if(par1World.getTileEntity(par2, par3, par4 + i) instanceof CustomizableSCTE && ((CustomizableSCTE) par1World.getTileEntity(par2, par3, par4 + i)).hasModule(EnumCustomModules.WHITELIST) && ModuleUtils.getPlayersFromModule(par1World, par2, par3, par4 + i, EnumCustomModules.WHITELIST).contains(((EntityLivingBase) par5Entity).getCommandSenderName().toLowerCase())){ return; }
					par1World.setBlockMetadataWithNotify(par2, par3, par4 + i, 2, 3);
					par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + i, mod_SecurityCraft.laserBlock);
					par1World.scheduleBlockUpdate(par2, par3, par4 + i, mod_SecurityCraft.laserBlock, 50);
					par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + i, mod_SecurityCraft.laserBlock);

					if(par1World.getTileEntity(par2, par3, par4 + i) instanceof CustomizableSCTE && ((CustomizableSCTE) par1World.getTileEntity(par2, par3, par4 + i)).hasModule(EnumCustomModules.HARMING)){
						((EntityLivingBase) par5Entity).attackEntityFrom(CustomDamageSources.laser, 10F);
					}
				}else{
					continue;
				}
			}
			
			for(int i = 0; i <= mod_SecurityCraft.configHandler.laserBlockRange; i++){
				Block id = par1World.getBlock(par2, par3, par4 - i);
				if(id == mod_SecurityCraft.laserBlock){
					if(par1World.getTileEntity(par2, par3, par4 - i) instanceof CustomizableSCTE && ((CustomizableSCTE) par1World.getTileEntity(par2, par3, par4 - i)).hasModule(EnumCustomModules.WHITELIST) && ModuleUtils.getPlayersFromModule(par1World, par2, par3, par4 - i, EnumCustomModules.WHITELIST).contains(((EntityLivingBase) par5Entity).getCommandSenderName().toLowerCase())){ return; }
					par1World.setBlockMetadataWithNotify(par2, par3, par4 - i, 2, 3);
					par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - i, mod_SecurityCraft.laserBlock);
					par1World.scheduleBlockUpdate(par2, par3, par4 - i, mod_SecurityCraft.laserBlock, 50);
					par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - i, mod_SecurityCraft.laserBlock);

					if(par1World.getTileEntity(par2, par3, par4 - i) instanceof CustomizableSCTE && ((CustomizableSCTE) par1World.getTileEntity(par2, par3, par4 - i)).hasModule(EnumCustomModules.HARMING)){
						((EntityLivingBase) par5Entity).attackEntityFrom(CustomDamageSources.laser, 10F);
					}
				}else{
					continue;
				}
			}
			
			for(int i = 0; i <= mod_SecurityCraft.configHandler.laserBlockRange; i++){
				Block id = par1World.getBlock(par2, par3 + i, par4);
				if(id == mod_SecurityCraft.laserBlock){
					if(par1World.getTileEntity(par2, par3 + i, par4 ) instanceof CustomizableSCTE && ((CustomizableSCTE) par1World.getTileEntity(par2, par3 + i, par4)).hasModule(EnumCustomModules.WHITELIST) && ModuleUtils.getPlayersFromModule(par1World, par2, par3 + i, par4, EnumCustomModules.WHITELIST).contains(((EntityLivingBase) par5Entity).getCommandSenderName().toLowerCase())){ return; }
					par1World.setBlockMetadataWithNotify(par2, par3 + i, par4 , 2, 3);
					par1World.notifyBlocksOfNeighborChange(par2, par3 + i, par4, mod_SecurityCraft.laserBlock);
					par1World.scheduleBlockUpdate(par2, par3 + i, par4, mod_SecurityCraft.laserBlock, 50);
					par1World.notifyBlocksOfNeighborChange(par2, par3 + i, par4, mod_SecurityCraft.laserBlock);

					if(par1World.getTileEntity(par2, par3 + i, par4) instanceof CustomizableSCTE && ((CustomizableSCTE) par1World.getTileEntity(par2, par3 + i, par4)).hasModule(EnumCustomModules.HARMING)){
						((EntityLivingBase) par5Entity).attackEntityFrom(CustomDamageSources.laser, 10F);
					}
				}else{
					continue;
				}
			}
			
			for(int i = 0; i <= mod_SecurityCraft.configHandler.laserBlockRange; i++){
				Block id = par1World.getBlock(par2, par3 - i, par4);
				if(id == mod_SecurityCraft.laserBlock){
					if(par1World.getTileEntity(par2, par3 - i, par4 ) instanceof CustomizableSCTE && ((CustomizableSCTE) par1World.getTileEntity(par2, par3 - i, par4)).hasModule(EnumCustomModules.WHITELIST) && ModuleUtils.getPlayersFromModule(par1World, par2, par3 - i, par4, EnumCustomModules.WHITELIST).contains(((EntityLivingBase) par5Entity).getCommandSenderName().toLowerCase())){ return; }
					par1World.setBlockMetadataWithNotify(par2, par3 - i, par4 , 2, 3);
					par1World.notifyBlocksOfNeighborChange(par2, par3 - i, par4, mod_SecurityCraft.laserBlock);
					par1World.scheduleBlockUpdate(par2, par3 - i, par4, mod_SecurityCraft.laserBlock, 50);
					par1World.notifyBlocksOfNeighborChange(par2, par3 - i, par4, mod_SecurityCraft.laserBlock);

					if(par1World.getTileEntity(par2, par3 - i, par4) instanceof CustomizableSCTE && ((CustomizableSCTE) par1World.getTileEntity(par2, par3 - i, par4)).hasModule(EnumCustomModules.HARMING)){
						((EntityLivingBase) par5Entity).attackEntityFrom(CustomDamageSources.laser, 10F);
					}
				}else{
					continue;
				}
			}
        }
    }
    
    
    /**
     * Called right before the block is destroyed by a player.  Args: world, x, y, z, metaData
     */
	public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5)
    {
    	if(!par1World.isRemote){
    		for(int i = 1; i <= mod_SecurityCraft.configHandler.laserBlockRange; i++){
    			Block id = par1World.getBlock(par2 + i, par3, par4);
    			if(id == mod_SecurityCraft.laserBlock){
    				for(int j = 1; j < i; j++){
    					par1World.func_147480_a(par2 + j, par3, par4, false);
    				}
    			}else{
    				continue;
    			}
    		}
    		
    		for(int i = 0; i <= mod_SecurityCraft.configHandler.laserBlockRange; i++){
    			Block id = par1World.getBlock(par2 - i, par3, par4);
    			if(id == mod_SecurityCraft.laserBlock){
    				for(int j = 1; j < i; j++){
    					par1World.func_147480_a(par2 - j, par3, par4, false);
    				}
    			}else{
    				continue;
    			}
    		}
    		
    		for(int i = 0; i <= mod_SecurityCraft.configHandler.laserBlockRange; i++){
    			Block id = par1World.getBlock(par2, par3, par4 + i);
    			if(id == mod_SecurityCraft.laserBlock){
    				for(int j = 1; j < i; j++){
    					par1World.func_147480_a(par2, par3, par4 + j, false);
    				}
    			}else{
    				continue;
    			}
    		}
    		
    		for(int i = 0; i <= mod_SecurityCraft.configHandler.laserBlockRange; i++){
    			Block id = par1World.getBlock(par2 , par3, par4 - i);
    			if(id == mod_SecurityCraft.laserBlock){
    				for(int j = 1; j < i; j++){
    					par1World.func_147480_a(par2, par3, par4 - j, false);
    				}
    			}else{
    				continue;
    			}
    		}
    		
    		for(int i = 0; i <= mod_SecurityCraft.configHandler.laserBlockRange; i++){
    			Block id = par1World.getBlock(par2, par3 + i, par4);
    			if(id == mod_SecurityCraft.laserBlock){
    				for(int j = 1; j < i; j++){
    					par1World.func_147480_a(par2, par3 + j, par4, false);
    				}
    			}else{
    				continue;
    			}
    		}
    		
    		for(int i = 0; i <= mod_SecurityCraft.configHandler.laserBlockRange; i++){
    			Block id = par1World.getBlock(par2, par3 - i, par4);
    			if(id == mod_SecurityCraft.laserBlock){
    				for(int j = 1; j < i; j++){
    					par1World.func_147480_a(par2, par3 - j, par4, false);
    				}
    			}else{
    				continue;
    			}
    		}
    	}
    }
    
    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
    {
        int l = MathHelper.floor_double(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

        if(l == 0)
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 2, 2);
        else if(l == 1)
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 5, 2);

        else if(l == 2)
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 3, 2);
        else if(l == 3)
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 4, 2);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        if(par1IBlockAccess.getBlockMetadata(par2, par3, par4) == 1)
    		this.setBlockBounds(0.250F, 0.000F, 0.300F, 0.750F, 1.000F, 0.700F);
        else if(par1IBlockAccess.getBlockMetadata(par2, par3, par4) == 2)
    		this.setBlockBounds(0.250F, 0.300F, 0.000F, 0.750F, 0.700F, 1.000F);
        else if(par1IBlockAccess.getBlockMetadata(par2, par3, par4) == 3)
    		this.setBlockBounds(0.000F, 0.300F, 0.300F, 1.000F, 0.700F, 0.700F);
        else
    		this.setBlockBounds(0.250F, 0.300F, 0.300F, 0.750F, 0.700F, 0.700F);
    }


    @SideOnly(Side.CLIENT)

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public IIcon getIcon(int side, int meta)
    {
        if(meta == 1 && (side == 1 || side == 0)){
    		return this.transparentIcon;
    	}else{
    		return this.blockIcon;
    	}
        
    	//if(par2 == 7 || par2 == 8 || par2 == 9 || par2 == 10){
    	//	return par1 == 1 ? this.keypadIconTop : (par1 == 0 ? this.keypadIconTop : (par1 != (par2 - 5) ? this.blockIcon : this.keypadIconFrontActive));
    	//}else{
    	//	return par1 == 1 ? this.keypadIconTop : (par1 == 0 ? this.keypadIconTop : (par1 != par2 ? this.blockIcon : this.keypadIconFront));
    	//}
    }
    
    @SideOnly(Side.CLIENT)

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon("securitycraft:aniLaser");
        this.transparentIcon = par1IconRegister.registerIcon("securitycraft:transparent");
    }
    
    @SideOnly(Side.CLIENT)

    /**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     */
    public Item getItem(World par1World, int par2, int par3, int par4)
    {
        return null;
    }

}
