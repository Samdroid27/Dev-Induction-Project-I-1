package collision;

import org.joml.Vector2f;
import world.Tile;
public class AABB
{
	private Vector2f center, half_extent;
	public static int tp=10,pp=10;
	public AABB(Vector2f center, Vector2f half_extent) 
	{
		this.center = center;
		this.half_extent = half_extent;
	}

	public Collision getCollision(AABB box2) {
		Vector2f distance = box2.center.sub(center, new Vector2f());
		distance.x = (float)Math.abs(distance.x);
		distance.y = (float)Math.abs(distance.y);
		if(Tile.c[0]==0)
		{
			Tile.c[0]++;
			tp=10;pp=10;
		}
			
		if(Tile.c[1]==0)
			{
				Tile.c[1]++;
				tp=10;pp=10;
			}
		if(Tile.c[2]==0)
		{
			Tile.c[2]++;
			tp=10;pp=10;
		}
		distance.sub(half_extent.add(box2.half_extent, new Vector2f()));

		return new Collision(distance, distance.x < 0 && distance.y < 0);
	}

	public void correctPosition(AABB box2, Collision data) {
		Vector2f correctionDistance = box2.center.sub(center, new Vector2f());
		if(data.distance.x > data.distance.y) {
			if(correctionDistance.x > 0) {
				center.add(data.distance.x, 0);
			}else{
				center.add(-data.distance.x, 0);
			}
		}else{
			if(correctionDistance.y > 0) {
				center.add(0, data.distance.y);
			}else{
				center.add(0, -data.distance.y);
			}
		}
	}

	public Vector2f getCenter() { return center; }
	public Vector2f getHalfExtent() { return half_extent; }
}
