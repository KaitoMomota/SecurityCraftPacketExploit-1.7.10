package net.geforcemods.securitycraft.tileentity;

import net.geforcemods.securitycraft.main.mod_SecurityCraft;
import net.geforcemods.securitycraft.misc.SCSounds;
import net.geforcemods.securitycraft.network.packets.PacketCPlaySoundAtPos;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityAlarm extends TileEntityOwnable {
	
	private int cooldown = 0;
	private boolean isPowered = false;
	
	public void updateEntity(){
		super.updateEntity();
		
		if(this.worldObj.isRemote){
			return;
		}else{		
			if(cooldown > 0){
				cooldown--;
			}
			
			if(isPowered && cooldown == 0){
				mod_SecurityCraft.network.sendToAll(new PacketCPlaySoundAtPos(xCoord, yCoord, zCoord, SCSounds.ALARM.path, mod_SecurityCraft.configHandler.alarmSoundVolume));
				setCooldown((mod_SecurityCraft.configHandler.alarmTickDelay * 20));
			}			
		}
	}

	/**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound){
        super.writeToNBT(par1NBTTagCompound);
    
        par1NBTTagCompound.setInteger("cooldown", this.cooldown);
        par1NBTTagCompound.setBoolean("isPowered", this.isPowered);
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound par1NBTTagCompound){
        super.readFromNBT(par1NBTTagCompound);

        if (par1NBTTagCompound.hasKey("cooldown"))
        {
            this.cooldown = par1NBTTagCompound.getInteger("cooldown");
        }
        
        if (par1NBTTagCompound.hasKey("isPowered"))
        {
            this.isPowered = par1NBTTagCompound.getBoolean("isPowered");
        }
     
    }
    
    public int getCooldown(){
    	return cooldown;
    }
    
    public void setCooldown(int par1){
    	this.cooldown = par1;
    }

	public boolean isPowered() {
		return isPowered;
	}

	public void setPowered(boolean isPowered) {
		this.isPowered = isPowered;
	}

}
