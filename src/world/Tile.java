package world;

public class Tile {
	public static Tile tiles[] = new Tile[32];
	
	public static byte not = 0;

	public static final Tile test_tile = new Tile("grass");
	public static final Tile test2 = new Tile("test").setSolid();
	public static final Tile pok1 = new Tile("pikachu").setSolid();
	public static final Tile char1 = new Tile("char").setSolid();
	public static final Tile bulba = new Tile("bulba").setSolid();
	public static final Tile tr = new Tile("tr").setSolid();
	public static final Tile A = new Tile("A").setSolid();
	public static final Tile T = new Tile("T").setSolid();
	public static final Tile O = new Tile("O").setSolid();
	public static final Tile C = new Tile("C").setSolid();
	public static final Tile K = new Tile("K").setSolid();
	public static final Tile you = new Tile("you");
	
	public static final Tile num[] = {new Tile("num1"),new Tile("num2"),new Tile("num3"),new Tile("num4"),new Tile("num5"),new Tile("num6"),new Tile("num7"),new Tile("num8"),new Tile("num9"),new Tile("num10")};
	
	
	public byte id;
	public static int c[]= {0,0,0,0};
	private String texture;
	private static boolean solid;

	public byte getId() {
		return id;
	}

	public String getTexture() {
		return texture;
	}

	public Tile(String texture) {
		this.id = not;
		not++;
		
		this.texture=texture;
		this.solid = false;
		if (tiles[id] != null)
		{
			throw new IllegalStateException("Tiles  at ["+ id + "] is already being used");
			
		}
		tiles[id]=this;
	}
	public Tile setSolid() { 
		this.solid = true;
	    
	return this; 
	}
	
	public  boolean isSolid()
	{ 
		
			return solid;
	}

	
}
