package entity;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import collision.AABB;
import collision.Collision;
import io.Window;
import render.Animation;
import render.Camera;
import render.Model;
import render.Shader;
import render.Texture;
import world.Tile;
import world.World;
import org.joml.Vector2f;

public class Player {
	private Model model;
	private AABB bounding_box;
	//private Texture texture;
	private Animation texture;
	public Transform transform;

	public Player() {
		float[] vertices = new float[] {
				-1f, 1f, 0, //TOP LEFT     0
				1f, 1f, 0,  //TOP RIGHT    1
				1f, -1f, 0, //BOTTOM RIGHT 2
				-1f, -1f, 0,//BOTTOM LEFT  3
		};

		float[] texture = new float[] {
				0,0,
				1,0,
				1,1,
				0,1,
		};

		int[] indices = new int[] {
				0,1,2,
				2,3,0
		};

		model = new Model(vertices, texture, indices);
		this.texture = new Animation(4, 10, "an");

		transform = new Transform();
		transform.scale = new Vector3f(16,16,1);
		
		bounding_box = new AABB(new Vector2f(transform.pos.x, transform.pos.y), new Vector2f(1,1));
	}

	public void update(float delta, Window window, Camera camera, World world) {
		if(window.getInput().isKeyDown(GLFW.GLFW_KEY_LEFT)) 
			transform.pos.add(new Vector3f(-10*delta, 0, 0));

		if(window.getInput().isKeyDown(GLFW.GLFW_KEY_RIGHT)) 
			transform.pos.add(new Vector3f(10*delta, 0, 0));


		if(window.getInput().isKeyDown(GLFW.GLFW_KEY_UP)) 
			transform.pos.add(new Vector3f(0, 10*delta, 0));

		if(window.getInput().isKeyDown(GLFW.GLFW_KEY_DOWN)) 
			transform.pos.add(new Vector3f(0, -10*delta, 0));
		
		//limiting the boundary of player
		
		if(transform.pos.x > (world.width*2)-1.5)
			transform.pos.x=(world.width*2)-1.5f;
		if(transform.pos.x < 0)
			transform.pos.x=0;
		if(transform.pos.y > 0)
			transform.pos.y=0;
		if(transform.pos.y < -(world.height*2)+1.5f)
			transform.pos.y=-(world.height*2)+1.5f;
	
		
		
		bounding_box.getCenter().set(transform.pos.x, transform.pos.y);

		AABB[] boxes = new AABB[9];
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				boxes[i + j * 3] = world.getTileBoundingBox(
							(int)(((transform.pos.x / 2) + 0.3f) - (3/2)) + i,
							(int)(((-transform.pos.y / 2) + 0.3f) - (3/2)) + j
						);
			}
		}

		AABB box = null;
		for(int i = 0; i < boxes.length; i++) 
		{
			if(boxes[i] != null)
			{
				if(box == null) box = boxes[i];

				Vector2f length1 = box.getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());
				Vector2f length2 = boxes[i].getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());

				if(length1.lengthSquared() > length2.lengthSquared()) 
				{
					box = boxes[i];
				}
			}
		}
		if(box != null) {
			Collision data = bounding_box.getCollision(box);
			
			if(data.isIntersecting) {
				bounding_box.correctPosition(box, data);
				transform.pos.set(bounding_box.getCenter(), 0);
			}

			for(int i = 0; i < boxes.length; i++) {
				if(boxes[i] != null) {
					if(box == null) box = boxes[i];

					Vector2f length1 = box.getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());
					Vector2f length2 = boxes[i].getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());

					if(length1.lengthSquared() > length2.lengthSquared()) {
						box = boxes[i];
					}
				}
			}

			data = bounding_box.getCollision(box);
			if(data.isIntersecting) {
				bounding_box.correctPosition(box, data);
				transform.pos.set(bounding_box.getCenter(), 0);
			}
		}

		camera.getPosition().lerp(transform.pos.mul(-world.getScale(), new Vector3f()), 0.05f);
		//camera.setPosition(transform.pos.mul(world.getScale(), new Vector3f()));
	}

	public void render(Shader shader, Camera camera) {
		shader.bind();
		shader.setUniform("sampler", 0);
		shader.setUniform("projection", transform.getProjection(camera.getProjection()));
		texture.bind(0);
		model.render();
	}
}