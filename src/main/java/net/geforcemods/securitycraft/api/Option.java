package net.geforcemods.securitycraft.api;

import net.geforcemods.securitycraft.gui.GuiCustomizeBlock;
import net.geforcemods.securitycraft.gui.GuiSlider;
import net.geforcemods.securitycraft.gui.GuiSlider.ISlider;
import net.geforcemods.securitycraft.main.mod_SecurityCraft;
import net.geforcemods.securitycraft.network.packets.PacketSUpdateSliderValue;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

/**
 * A class that allows blocks that have
 * {@link CustomizableSCTE}s to have custom, "per-block"
 * options that are separate from the main SecurityCraft
 * configuration options.
 * 
 * @author Geforce
 *
 * @param <T> The Class of the type of value this option should use
 */
public class Option<T> {
	
	private String name;
	
	protected T value;
	private T defaultValue;
	private T increment;
	private T minimum;
	private T maximum;
		
	public Option(String optionName, T value) {
		this.name = optionName;
		this.value = value;
		this.defaultValue = value;
	}
	
	public Option(String optionName, T value, T min, T max, T increment) {
		this.name = optionName;
		this.value = value;
		this.defaultValue = value;
		this.increment = increment;
		this.minimum = min;
		this.maximum = max;
	}
	
	/**
	 * Called when this option's button in {@link GuiCustomizeBlock} is pressed.
	 * Update the option's value here. <p>
	 * 
	 * NOTE: This gets called on the server side, not on the client!
	 * Use TileEntitySCTE.sync() to update values on the client-side.
	 */
	public void toggle() {}
	
	@SuppressWarnings("unchecked")
	public void copy(Option<?> option) {
		value = (T) option.getValue();
	}
	
	/**
	 * @return This option, casted to a boolean.
	 */
	public boolean asBoolean() {
		return (Boolean) value;
	}
	
	/**
	 * @return This option, casted to a integer.
	 */
	public int asInteger() {
		return (Integer) value;
	}
	
	/**
	 * @return This option, casted to a double.
	 */
	public double asDouble() {
		return (Double) value;
	}
	
	/**
	 * @return This option, casted to a float.
	 */
	public float asFloat() {
		return (Float) value;
	}
	
	@SuppressWarnings("unchecked")
	public void readFromNBT(NBTTagCompound compound) {
		if(value instanceof Boolean) {
			value = (T) ((Boolean) compound.getBoolean(name));
		}else if(value instanceof Integer) {
			value = (T) ((Integer) compound.getInteger(name));
		}else if(value instanceof Double) {
			value = (T) ((Double) compound.getDouble(name));
		}else if(value instanceof Float) {
			value = (T) ((Float) compound.getFloat(name));
		}
	}
	
	public void writeToNBT(NBTTagCompound compound) {
		if(value instanceof Boolean) {
			compound.setBoolean(name, asBoolean());
		}else if(value instanceof Integer) {
			compound.setInteger(name, asInteger());
		}else if(value instanceof Double) {
			compound.setDouble(name, asDouble());
		}else if(value instanceof Float) {
			compound.setFloat(name, asFloat());
		}
	}
	
	/**
	 * @return This option's name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return This option's value.
	 */
	public T getValue() {
		return value;
	}
	
	/**
	 * Set this option's new value here.
	 * 
	 * @param value The new value
	 */
	public void setValue(T value) {
		this.value = value;
	}
	
	/**
	 * @return This option's default value.
	 */
	public T getDefaultValue() {
		return defaultValue;
	}
	
	/**
	 * @return If this option is some kind of number (integer, float, etc.),
	 *         return the amount the number should increase/decrease every time
	 *         the option is toggled in {@link GuiCustomizeBlock}.
	 */
	public T getIncrement() {
		return increment;
	}
	
	/**
	 * @return The lowest value this option can be set to.
	 */
	public T getMin() {
		return minimum;
	}
	
	/**
	 * @return The highest value this option can be set to.
	 */
	public T getMax() {
		return maximum;
	}
	
/**
 * A subclass of {@link Option}, already setup to handle booleans.
 */
public static class OptionBoolean extends Option<Boolean>{

	public OptionBoolean(String optionName, Boolean value) {
		super(optionName, value);
	}
	
	public void toggle() {
		setValue(!getValue());
	}
	
	public Boolean getValue() {
		return (boolean) value;
	}
	
	public String toString() {
		return value + "";
	}
}

/**
 * A subclass of {@link Option}, already setup to handle integers.
 */
public static class OptionInt extends Option<Integer>{

	public OptionInt(String optionName, Integer value) {
		super(optionName, value);
	}
	
	public OptionInt(String optionName, Integer value, Integer min, Integer max, Integer increment) {
		super(optionName, value, min, max, increment);
	}
	
	public void toggle() {
		if(getValue() >= getMax()) {
			setValue(getMin());
			return;
		}
		
		if((getValue() + getIncrement()) >= getMax()) {
			setValue(getMax());
			return;
		}
		
		setValue(getValue() + getIncrement());
	}
	
	public Integer getValue() {
		return (int) value;
	}
	
	public String toString() {
		return (value) + "";
	}
}

/**
 * A subclass of {@link Option}, already setup to handle doubles.
 */
public static class OptionDouble extends Option<Double> implements ISlider{
	private boolean slider;
	private CustomizableSCTE tileEntity;
	
	public OptionDouble(String optionName, Double value) {
		super(optionName, value);
		slider = false;
	}
	
	public OptionDouble(String optionName, Double value, Double min, Double max, Double increment) {
		super(optionName, value, min, max, increment);
		slider = false;
	}
	
	public OptionDouble(CustomizableSCTE te, String optionName, Double value, Double min, Double max, Double increment, boolean s) {
		super(optionName, value, min, max, increment);
		slider = s;
		tileEntity = te;
	}
	
	public void toggle() {
		if(isSlider())
			return;
		
		if(getValue() >= getMax()) {
			setValue(getMin());
			return;
		}
		
		if((getValue() + getIncrement()) >= getMax()) {
			setValue(getMax());
			return;
		}
		
		setValue(getValue() + getIncrement());
	}
	
	public Double getValue() {
		return (double) value;
	}
	
	public String toString() {
		return Double.toString(value).length() > 5 ? Double.toString(value).substring(0, 5) : Double.toString(value);
	}
	
	public boolean isSlider()
	{
		return slider;
	}
	
	@Override
	public void onChangeSliderValue(GuiSlider slider, String blockName, int id)
	{
		if(!isSlider())
			return;
		
		setValue(slider.getValue());
        slider.displayString = (StatCollector.translateToLocal("option." + blockName + "." + getName()) + " ").replace("#", toString());
		mod_SecurityCraft.network.sendToServer(new PacketSUpdateSliderValue(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, id, getValue()));
	}
}

/**
 * A subclass of {@link Option}, already setup to handle floats.
 */
public static class OptionFloat extends Option<Float>{
	public OptionFloat(String optionName, Float value) {
		super(optionName, value);
	}
	
	public OptionFloat(String optionName, Float value, Float min, Float max, Float increment) {
		super(optionName, value, min, max, increment);
	}
	
	public void toggle() {
		if(getValue() >= getMax()) {
			setValue(getMin());
			return;
		}
		
		if((getValue() + getIncrement()) >= getMax()) {
			setValue(getMax());
			return;
		}
		
		setValue(getValue() + getIncrement());
	}
	
	public Float getValue() {
		return value;
	}
	
	public String toString() {
		return Float.toString(value).length() > 5 ? Float.toString(value).substring(0, 5) : Float.toString(value);
	}
}
	
}
