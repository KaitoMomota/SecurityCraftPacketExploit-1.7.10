package net.geforcemods.securitycraft.gui;

import org.lwjgl.opengl.GL11;

import net.geforcemods.securitycraft.containers.ContainerGeneric;
import net.geforcemods.securitycraft.main.mod_SecurityCraft;
import net.geforcemods.securitycraft.network.packets.PacketSOpenGui;
import net.geforcemods.securitycraft.util.PlayerUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiBriefcase extends GuiContainer {
	
    public static final String UP_ARROW  = "\u2b06";
    public static final String DOWN_ARROW  = "\u2b07";
    public static final String RIGHT_ARROW  = "\u27a1";

	private static final ResourceLocation field_110410_t = new ResourceLocation("securitycraft:textures/gui/container/blank.png");

	private GuiButton[] keycodeTopButtons = new GuiButton[4];
	private GuiButton[] keycodeBottomButtons = new GuiButton[4];
	private GuiTextField[] keycodeTextboxes = new GuiTextField[4];
	private GuiButton continueButton;
		
	public GuiBriefcase(InventoryPlayer inventoryPlayer, TileEntity tileEntity) {
		super(new ContainerGeneric(inventoryPlayer, tileEntity));
	}
	
	public void initGui() {
		super.initGui();

		for(int i = 0; i < keycodeTopButtons.length; i++) {
			keycodeTopButtons[i] = new GuiButton(i, this.width / 2 - 40 + (i * 20), this.height / 2 - 52, 20, 20, UP_ARROW);
			this.buttonList.add(keycodeTopButtons[i]);
		}
		
		for(int i = 0; i < keycodeBottomButtons.length; i++) {
			keycodeBottomButtons[i] = new GuiButton(4 + i, this.width / 2 - 40 + (i * 20), this.height / 2, 20, 20, DOWN_ARROW);
			this.buttonList.add(keycodeBottomButtons[i]);
		}
		
		continueButton = new GuiButton(8, (this.width / 2 + 42), this.height / 2 - 26, 20, 20, RIGHT_ARROW);
		this.buttonList.add(continueButton);
		
		for(int i = 0; i < keycodeTextboxes.length; i++) {
			keycodeTextboxes[i] = new GuiTextField(this.fontRendererObj, (this.width / 2 - 37) + (i * 20), this.height / 2 - 22, 14, 12);

			keycodeTextboxes[i].setTextColor(-1);
			keycodeTextboxes[i].setDisabledTextColour(-1);
			keycodeTextboxes[i].setEnableBackgroundDrawing(true);
			keycodeTextboxes[i].setMaxStringLength(1);
			keycodeTextboxes[i].setText("0");
		}
	}
	
    public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
		GL11.glDisable(GL11.GL_LIGHTING);
		
		for(GuiTextField textfield : keycodeTextboxes) {
			textfield.drawTextBox();
		}	
    }
    
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRendererObj.drawString(StatCollector.translateToLocal("gui.briefcase.enterPasscode"), this.xSize / 2 - this.fontRendererObj.getStringWidth(StatCollector.translateToLocal("gui.briefcase.enterPasscode")) / 2, 6, 4210752);
    }

	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(field_110410_t);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	}

	protected void actionPerformed(GuiButton guibutton) {
		int[] keys = new int[]{Integer.parseInt(keycodeTextboxes[0].getText()), Integer.parseInt(keycodeTextboxes[1].getText()), Integer.parseInt(keycodeTextboxes[2].getText()), Integer.parseInt(keycodeTextboxes[3].getText())};
		
		switch(guibutton.id) {
			case 0:
				if(keys[0] == 9) { keys[0] = 0; } else { keys[0]++; }
			    break;
			case 1:
				if(keys[1] == 9) { keys[1] = 0; } else { keys[1]++; }
				break;
			case 2:
				if(keys[2] == 9) { keys[2] = 0; } else { keys[2]++; }
				break;
			case 3:
				if(keys[3] == 9) { keys[3] = 0; } else { keys[3]++; }
				break;
			case 4:
				if(keys[0] == 0) { keys[0] = 9; } else { keys[0]--; }
				break;
			case 5:
				if(keys[1] == 0) { keys[1] = 9; } else { keys[1]--; }
				break;
			case 6:
				if(keys[2] == 0) { keys[2] = 9; } else { keys[2]--; }
				break;
			case 7:
				if(keys[3] == 0) { keys[3] = 9; } else { keys[3]--; }
				break;
			case 8:
				if(PlayerUtils.isHoldingItem(Minecraft.getMinecraft().thePlayer, mod_SecurityCraft.briefcase)) {
					NBTTagCompound nbt = Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem().stackTagCompound;
					String code = keys[0] + "" + keys[1] + "" +  keys[2] + "" + keys[3];
	
					if(nbt.getString("passcode").matches(code)) {
						mod_SecurityCraft.network.sendToServer(new PacketSOpenGui(GuiHandler.BRIEFCASE_GUI_ID, (int) Minecraft.getMinecraft().thePlayer.posX, (int) Minecraft.getMinecraft().thePlayer.posY, (int) Minecraft.getMinecraft().thePlayer.posZ));
					}
				}
				
				break;
			}
		
		keycodeTextboxes[0].setText(String.valueOf(keys[0]));
		keycodeTextboxes[1].setText(String.valueOf(keys[1]));
		keycodeTextboxes[2].setText(String.valueOf(keys[2]));
		keycodeTextboxes[3].setText(String.valueOf(keys[3]));
	}
}
