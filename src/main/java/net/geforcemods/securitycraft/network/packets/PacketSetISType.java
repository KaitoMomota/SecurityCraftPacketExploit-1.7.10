package net.geforcemods.securitycraft.network.packets;

import io.netty.buffer.ByteBuf;
import net.geforcemods.securitycraft.main.mod_SecurityCraft;
import net.geforcemods.securitycraft.tileentity.TileEntityInventoryScanner;
import net.geforcemods.securitycraft.util.Utils;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketSetISType implements IMessage{
	
	private int x, y, z;
	private String type;
	
	public PacketSetISType(){
		
	}
	
	public PacketSetISType(int x, int y, int z, String type){
		this.x = x;
		this.y = y;
		this.z = z;
		this.type = type;
	}

	public void fromBytes(ByteBuf par1ByteBuf) {
		this.x = par1ByteBuf.readInt();
		this.y = par1ByteBuf.readInt();
		this.z = par1ByteBuf.readInt();
		this.type = ByteBufUtils.readUTF8String(par1ByteBuf);

	}
	
	public void toBytes(ByteBuf par1ByteBuf) {
		par1ByteBuf.writeInt(x);
		par1ByteBuf.writeInt(y);
		par1ByteBuf.writeInt(z);
		ByteBufUtils.writeUTF8String(par1ByteBuf, type);

	}
	
public static class Handler extends PacketHelper implements IMessageHandler<PacketSetISType, IMessage> {
	
	public IMessage onMessage(PacketSetISType packet, MessageContext context) {
		int x = packet.x;
		int y = packet.y;
		int z = packet.z;
		
		((TileEntityInventoryScanner) getWorld(context.getServerHandler().playerEntity).getTileEntity(x, y, z)).setType(packet.type);
		
		
		
		mod_SecurityCraft.log("Setting type to " + packet.type);
		getWorld(context.getServerHandler().playerEntity).scheduleBlockUpdate(x, y, z, getWorld(context.getServerHandler().playerEntity).getBlock(x, y, z), 1);
		
		Utils.setISinTEAppropriately(getWorld(context.getServerHandler().playerEntity), x, y, z, ((TileEntityInventoryScanner) getWorld(context.getServerHandler().playerEntity).getTileEntity(x, y, z)).getContents(), ((TileEntityInventoryScanner) getWorld(context.getServerHandler().playerEntity).getTileEntity(x, y, z)).getType());			
		
		return null;
	}

}

}
