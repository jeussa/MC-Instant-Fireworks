package fireworks;

import org.bukkit.inventory.ItemStack;

public class jDataWatcherObject<T> implements iNmsObject{
	
	private static final boolean mc194=!ProtocolUtils.version.endsWith("R1");
	
	public static final jDataWatcherObject<Byte> entity_ay=new jDataWatcherObject<>(((jDataWatcherObject.mc194)?("ay"):("ax")), "Entity");
	public static final jDataWatcherObject<Integer> entity_az=new jDataWatcherObject<>(((jDataWatcherObject.mc194)?("az"):("ay")), "Entity");
	public static final jDataWatcherObject<String> entity_aA=new jDataWatcherObject<>(((jDataWatcherObject.mc194)?("aA"):("az")), "Entity");
	public static final jDataWatcherObject<Boolean> entity_aB=new jDataWatcherObject<>(((jDataWatcherObject.mc194)?("aB"):("aA")), "Entity");
	public static final jDataWatcherObject<Boolean> entity_aC=new jDataWatcherObject<>(((jDataWatcherObject.mc194)?("aC"):("aB")), "Entity");
	public static final jDataWatcherObject<ItemStack> entityfireworks_FIREWORK_ITEM=new jDataWatcherObject<>("FIREWORK_ITEM", "EntityFireworks");
	
	
	
	private final Object nms;
	
	private jDataWatcherObject(String fieldname, String classname){
		this.nms=ProtocolUtils.refl_fieldGet0(fieldname, classname);
	}
	
	/**
	 * Build
	 */
	@Override
	public Object build(){
		return this.nms;
	}
}
