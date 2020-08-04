package net.geforcemods.securitycraft.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.geforcemods.securitycraft.api.CustomizableSCTE;
import net.geforcemods.securitycraft.main.mod_SecurityCraft;
import net.geforcemods.securitycraft.tileentity.TileEntityLaserBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockLaserBlock extends BlockContainer {
	    
	public BlockLaserBlock(Material par2Material) {
		super(par2Material);
	}
	
    public boolean isNormalCube(IBlockAccess world, int x, int y, int z){
		return true;
	}
    
    @Override
    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side)
    {
    	return true;
    }
    
    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World par1World, int par2, int par3, int par4){
        super.onBlockAdded(par1World, par2, par3, par4);
    }
    
    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack){
        if(!par1World.isRemote){
        	this.setLaser(par1World, par2, par3, par4);
        }
    }

    public void setLaser(World par1World, int par2, int par3, int par4) {
		for(int i = 1; i <= mod_SecurityCraft.configHandler.laserBlockRange; i++){
			Block id = par1World.getBlock(par2 + i, par3, par4);
			if(id != Blocks.air && id != mod_SecurityCraft.laserBlock){ break; }
			if(id == mod_SecurityCraft.laserBlock){
				CustomizableSCTE.link((CustomizableSCTE) par1World.getTileEntity(par2, par3, par4), (CustomizableSCTE) par1World.getTileEntity(par2 + i, par3, par4));
				
				for(int j = 1; j < i; j++){
					if(par1World.getBlock(par2 + j, par3, par4) == Blocks.air){
						par1World.setBlock(par2 + j, par3, par4, mod_SecurityCraft.laser, 3, 3);
					}
				}
			}else{
				continue;
			}
		}
		
		for(int i = 1; i <= mod_SecurityCraft.configHandler.laserBlockRange; i++){
			Block id = par1World.getBlock(par2 - i, par3, par4);
			if(id != Blocks.air && id != mod_SecurityCraft.laserBlock){ break; }
			if(id == mod_SecurityCraft.laserBlock){
				CustomizableSCTE.link((CustomizableSCTE) par1World.getTileEntity(par2, par3, par4), (CustomizableSCTE) par1World.getTileEntity(par2 - i, par3, par4));
				for(int j = 1; j < i; j++){
					if(par1World.getBlock(par2 - j, par3, par4) == Blocks.air){
						par1World.setBlock(par2 - j, par3, par4, mod_SecurityCraft.laser, 3, 3);
					}
				}
			}else{
				continue;
			}
		}
		
		for(int i = 1; i <= mod_SecurityCraft.configHandler.laserBlockRange; i++){
			Block id = par1World.getBlock(par2, par3, par4 + i);
			if(id != Blocks.air && id != mod_SecurityCraft.laserBlock){ break; }
			if(id == mod_SecurityCraft.laserBlock){
				CustomizableSCTE.link((CustomizableSCTE) par1World.getTileEntity(par2, par3, par4), (CustomizableSCTE) par1World.getTileEntity(par2, par3, par4 + i));
				for(int j = 1; j < i; j++){
					if(par1World.getBlock(par2, par3, par4 + j) == Blocks.air){
						par1World.setBlock(par2, par3, par4 + j, mod_SecurityCraft.laser, 2, 3);
					}
				}
			}else{
				continue;
			}
		}
		
		for(int i = 1; i <= mod_SecurityCraft.configHandler.laserBlockRange; i++){
			Block id = par1World.getBlock(par2 , par3, par4 - i);
			if(id != Blocks.air && id != mod_SecurityCraft.laserBlock){ break; }
			if(id == mod_SecurityCraft.laserBlock){
				CustomizableSCTE.link((CustomizableSCTE) par1World.getTileEntity(par2, par3, par4), (CustomizableSCTE) par1World.getTileEntity(par2, par3, par4 - i));
				for(int j = 1; j < i; j++){
					if(par1World.getBlock(par2, par3, par4 - j) == Blocks.air){
						par1World.setBlock(par2, par3, par4 - j, mod_SecurityCraft.laser, 2, 3);
					}
				}
			}else{
				continue;
			}
		}
		
		for(int i = 1; i <= mod_SecurityCraft.configHandler.laserBlockRange; i++){
			Block id = par1World.getBlock(par2, par3 + i, par4);
			if(id != Blocks.air && id != mod_SecurityCraft.laserBlock){ break; }
			if(id == mod_SecurityCraft.laserBlock){
				CustomizableSCTE.link((CustomizableSCTE) par1World.getTileEntity(par2, par3, par4), (CustomizableSCTE) par1World.getTileEntity(par2, par3 + i, par4));
				for(int j = 1; j < i; j++){
					if(par1World.getBlock(par2, par3 + j, par4) == Blocks.air){
						par1World.setBlock(par2, par3 + j, par4, mod_SecurityCraft.laser, 1, 3);
					}
				}
			}else{
				continue;
			}
		}
		
		for(int i = 1; i <= mod_SecurityCraft.configHandler.laserBlockRange; i++){
			Block id = par1World.getBlock(par2, par3 - i, par4);
			if(id != Blocks.air && id != mod_SecurityCraft.laserBlock){ break; }
			if(id == mod_SecurityCraft.laserBlock){
				CustomizableSCTE.link((CustomizableSCTE) par1World.getTileEntity(par2, par3, par4), (CustomizableSCTE) par1World.getTileEntity(par2, par3 - i, par4));
				for(int j = 1; j < i; j++){
					if(par1World.getBlock(par2, par3 - j, par4) == Blocks.air){
						par1World.setBlock(par2, par3 - j, par4, mod_SecurityCraft.laser, 1, 3);
					}
				}
			}else{
				continue;
			}
		}
	}
    
    /**
     * Called right before the block is destroyed by a player.  Args: world, x, y, z, metaData
     */
    public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5) {
    	if(!par1World.isRemote){
    		destroyAdjacentLasers(par1World, par2, par3, par4);
    	}
    }
    
    public static void destroyAdjacentLasers(World par1World, int par2, int par3, int par4){
    	for(int i = 1; i <= mod_SecurityCraft.configHandler.laserBlockRange; i++){
			Block id = par1World.getBlock(par2 + i, par3, par4);
			if(id == mod_SecurityCraft.laserBlock){				
				for(int j = 1; j < i; j++){
					if(par1World.getBlock(par2 + j, par3, par4) == mod_SecurityCraft.laser){
						par1World.func_147480_a(par2 + j, par3, par4, false);
					}
				}
			}else{
				continue;
			}
		}
		
		for(int i = 1; i <= mod_SecurityCraft.configHandler.laserBlockRange; i++){
			Block id = par1World.getBlock(par2 - i, par3, par4);
			if(id == mod_SecurityCraft.laserBlock){				
				for(int j = 1; j < i; j++){
					if(par1World.getBlock(par2 - j, par3, par4) == mod_SecurityCraft.laser){
						par1World.func_147480_a(par2 - j, par3, par4, false);
					}
				}
			}else{
				continue;
			}
		}
		
		for(int i = 1; i <= mod_SecurityCraft.configHandler.laserBlockRange; i++){
			Block id = par1World.getBlock(par2, par3, par4 + i);
			if(id == mod_SecurityCraft.laserBlock){
				for(int j = 1; j < i; j++){
					if(par1World.getBlock(par2, par3, par4 + j) == mod_SecurityCraft.laser){
						par1World.func_147480_a(par2, par3, par4 + j, false);
					}
				}
			}else{
				continue;
			}
		}
		
		for(int i = 1; i <= mod_SecurityCraft.configHandler.laserBlockRange; i++){
			Block id = par1World.getBlock(par2, par3, par4 - i);
			if(id == mod_SecurityCraft.laserBlock){
				for(int j = 1; j < i; j++){
					if(par1World.getBlock(par2, par3, par4 - j) == mod_SecurityCraft.laser){
						par1World.func_147480_a(par2, par3, par4 - j, false);
					}
				}
			}else{
				continue;
			}
		}
		
		for(int i = 1; i <= mod_SecurityCraft.configHandler.laserBlockRange; i++){
			Block id = par1World.getBlock(par2, par3 + i, par4);
			if(id == mod_SecurityCraft.laserBlock){
				for(int j = 1; j < i; j++){
					if(par1World.getBlock(par2, par3 + j, par4) == mod_SecurityCraft.laser){
						par1World.func_147480_a(par2, par3 + j, par4, false);
					}
				}
			}else{
				continue;
			}
		}
		
		for(int i = 1; i <= mod_SecurityCraft.configHandler.laserBlockRange; i++){
			Block id = par1World.getBlock(par2, par3 - i, par4);
			if(id == mod_SecurityCraft.laserBlock){
				for(int j = 1; j < i; j++){
					if(par1World.getBlock(par2, par3 - j, par4) == mod_SecurityCraft.laser){
						par1World.func_147480_a(par2, par3 - j, par4, false);
					}
				}
			}else{
				continue;
			}
		}
    }
    
    public boolean canProvidePower(){
		return true;
	}
    
    /**
     * Returns true if the block is emitting indirect/weak redstone power on the specified side. If isBlockNormalCube
     * returns true, standard redstone propagation rules will apply instead and this will not be called. Args: World, X,
     * Y, Z, side. Note that the side is reversed - eg it is 1 (up) when checking the bottom of the block.
     */
    public int isProvidingWeakPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5){
    	if(par1IBlockAccess.getBlockMetadata(par2, par3, par4) == 2){
    		return 15;
    	}else{
    		return 0;
    	}
    }
    
    /**
     * Returns true if the block is emitting direct/strong redstone power on the specified side. Args: World, X, Y, Z,
     * side. Note that the side is reversed - eg it is 1 (up) when checking the bottom of the block.
     */
    public int isProvidingStrongPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5){
    	if(par1IBlockAccess.getBlockMetadata(par2, par3, par4) == 2){
    		return 15;
    	}else{
    		return 0;
    	}
    }
    
    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random){
        if(!par1World.isRemote && par1World.getBlockMetadata(par2, par3, par4) == 2){
        	par1World.setBlockMetadataWithNotify(par2, par3, par4, 1, 3);
        }                      
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random){
    	if(par1World.getBlockMetadata(par2, par3, par4) == 2){
    		double d0 = par2 + 0.5F + (par5Random.nextFloat() - 0.5F) * 0.2D;
    		double d1 = par3 + 0.7F + (par5Random.nextFloat() - 0.5F) * 0.2D;
    		double d2 = par4 + 0.5F + (par5Random.nextFloat() - 0.5F) * 0.2D;
    		double d3 = 0.2199999988079071D;
    		double d4 = 0.27000001072883606D;

    		par1World.spawnParticle("reddust", d0 - d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
    		par1World.spawnParticle("reddust", d0 + d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D); 
    		par1World.spawnParticle("reddust", d0, d1 + d3, d2 - d4, 0.0D, 0.0D, 0.0D);
    		par1World.spawnParticle("reddust", d0, d1 + d3, d2 + d4, 0.0D, 0.0D, 0.0D);
    		par1World.spawnParticle("reddust", d0, d1, d2, 0.0D, 0.0D, 0.0D);
    	}
    }

	public TileEntity createNewTileEntity(World par1World, int par2) {
		return new TileEntityLaserBlock().linkable();
	}

}
