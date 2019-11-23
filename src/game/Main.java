package game;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.glfw.GLFW;

import org.lwjgl.opengl.GL;
import io.Timer;
import io.Window;
import render.Camera;
import render.Shader;
import world.Tile;
import world.TileRenderer;
import world.World;
import entity.Player;
import collision.AABB;

/**
 *
 * @author Samdroid27 AKA Shivam Agrawal
 */
public class Main {
	public static void main(String[] args) throws InterruptedException {
		
		Window.setCallbacks();
		if (!glfwInit()) // check if glfw gets initialised or not
		{
			throw new IllegalStateException("failed to initialise");
		}
		
		
		Window window = new Window();
		 window.setSize(640,480);
		window.setFullScreen(false);
		window.createWindow("PokeQuest");
		GL.createCapabilities();

		Camera camera = new Camera(window.getWidth(), window.getHeight());
		glEnable(GL_TEXTURE_2D);

		TileRenderer tiles = new TileRenderer();
		for(int i=0;i<10;i++)                //Setting Points tile solid
		{
			Tile.num[i].setSolid();
		}
		
		
		
		Shader shader = new Shader("shader");

		
		World world = new World();
		//World world = new World("test_level");
		Player player = new Player();

		double frame_cap = 1.0 / 60.0; // sec/frame
		double frame_time = 0;
		int frames = 0;
		double time = Timer.getTime();
		double unprocessed = 0;
		
		world.setTile(Tile.test2, 7, 15);
		world.setTile(Tile.test2, 15,8);
		world.setTile(Tile.test2, 15,9);
		world.setTile(Tile.test2, 16,8);
		world.setTile(Tile.test2, 16,9);
		world.setTile(Tile.test2, 24,14);
		world.setTile(Tile.test2, 24,15);
		world.setTile(Tile.test2, 25,14);
		world.setTile(Tile.A, 9, 0);
		world.setTile(Tile.T, 11, 0);
		world.setTile(Tile.O, 12, 0);
		world.setTile(Tile.A, 14, 0);
		world.setTile(Tile.T, 15, 0);
		world.setTile(Tile.T, 16, 0);
		world.setTile(Tile.A, 17, 0);
		world.setTile(Tile.C, 18, 0);
		world.setTile(Tile.K, 19, 0);

		world.setTile(Tile.pok1, 0,2);
		
				
		while (!window.shouldClose()) {
			boolean can_render = false; // saves processing when program is not render anything

			double time_2 = Timer.getTime();
			double passed = time_2 - time;
			unprocessed += passed;
			frame_time += passed;
			time = time_2;

			while (unprocessed >= frame_cap) 
			{

				unprocessed -= frame_cap;
				can_render = true;
				
				
				if (window.getInput().isKeyReleased(GLFW_KEY_ESCAPE))
				{
					glfwSetWindowShouldClose(window.getWindow(), true);
				}
			
				player.update((float)frame_cap, window, camera, world);		
				world.correctCamera(camera, window);
				
				window.update();
				if (frame_time >= 1.0) 
				{
					frame_time = 0;
					frames = 0;
				}
				
			}

			
			if (can_render) {
				glClear(GL_COLOR_BUFFER_BIT);
				double posX = player.transform.pos.x;
				double posY = player.transform.pos.y;
				
				if((posX>=28 && posX<=34  )&& (posY<=-14 && posY>=-20))
				{
					
					if(Tile.c[0]==1)
					{
						
						Tile.c[0]++;
						Tile.c[1]=0;
						Tile.c[2]=0;
					}
				
					if(Tile.c[0]==2)
					{	
						
						if(AABB.tp>=1 && AABB.pp>=1)
						{
							world.setTile(Tile.bulba,(int) 16,10);
							if(window.getInput().isKeyPressed(GLFW.GLFW_KEY_A))
								{
								AABB.pp=AABB.pp-(int)(Math.random()*5);
									
								AABB.tp=AABB.tp-(int)(Math.random()*3);
								 
								}
							
						}
						if(AABB.pp<=0) {
							System.out.println("You own Bulbasaur");
							world.setTile(Tile.test_tile,(int) 14,11);
							world.setTile(Tile.test_tile,(int) 16,11);
							world.setTile(Tile.bulba,(int) 0,4);
							world.setTile(Tile.test_tile,(int) 14,12);
							
						}
						else if(AABB.tp<=0) {
							System.out.println("Bulbasaur ran away");
							world.setTile(Tile.test_tile,(int) 14,11);
							world.setTile(Tile.test_tile,(int)  16,11);
							world.setTile(Tile.test_tile,(int) 16,10);
							world.setTile(Tile.test_tile,(int) 14,12);
						}
							
						 if(AABB.tp>0 && AABB.pp>0)
						{
							world.setTile(Tile.num[AABB.tp-1],(int) 14,11);
							
							world.setTile(Tile.num[AABB.pp-1],(int) 16,11);
							world.setTile(Tile.you,(int) 14,12);
							
						}
						
					}		
						
				}
				if((posX>=12 && posX<=16  )&& (posY<=-28 && posY>=-32))
				{
					world.setTile(Tile.char1,(int) 7,16);
					if(Tile.c[1]==1)
					{
						
						Tile.c[1]++;
						Tile.c[0]=0;
						Tile.c[2]=0;
					}
					if(Tile.c[1]==2)
					{	
						
						if(AABB.tp>=1 && AABB.pp>=1)
						{
							if(window.getInput().isKeyPressed(GLFW.GLFW_KEY_A))
								{
								AABB.pp=AABB.pp-(int)(Math.random()*5);
									
								AABB.tp=AABB.tp-(int)(Math.random()*3);
								
								}
							
						}
						if(AABB.pp<=0) {
							System.out.println("You own charizard");
							world.setTile(Tile.test_tile,(int) 4,17);
							world.setTile(Tile.test_tile,(int) 7,17);
							world.setTile(Tile.test_tile,(int) 4,18);
							world.setTile(Tile.char1,(int) 0,3);
						}
						else if(AABB.tp<=0) {
							System.out.println("charizard burnt you");
							world.setTile(Tile.test_tile,(int) 7,16);
							world.setTile(Tile.test_tile,(int) 4,17);
							world.setTile(Tile.test_tile,(int) 7,17);
							world.setTile(Tile.test_tile,(int) 4,18);
						}
							
						else if(AABB.tp>0 && AABB.pp>0)
						{
							world.setTile(Tile.num[AABB.tp-1],(int) 4,17);
							world.setTile(Tile.num[AABB.pp-1],(int) 7,17);
							world.setTile(Tile.you,(int) 4,18);
						}
					}
						
				}
				if((posX>=46 && posX<=52  )&& (posY<=-26 && posY>=-32))
				{
					world.setTile(Tile.tr,(int) 25,15);
					if(Tile.c[2]==1)
					{
						
						Tile.c[2]++;
						Tile.c[0]=0;
						Tile.c[1]=0;
					}
					if(Tile.c[2]==2)
					{	
						
						if(AABB.tp>=1 && AABB.pp>=1)
						{
							if(window.getInput().isKeyPressed(GLFW.GLFW_KEY_A))
								{
								AABB.pp=AABB.pp-(int)(Math.random()*5);
									
								AABB.tp=AABB.tp-(int)(Math.random()*5);
								
								}
							
						}
						if(AABB.pp<=0) {
							System.out.println("Team Rocket lost once again");
							world.setTile(Tile.test_tile,(int) 25,17);
							world.setTile(Tile.test_tile,(int) 23,17);
							world.setTile(Tile.test_tile,(int) 25,15);
							world.setTile(Tile.test_tile,(int) 23,18);
						}
						else if(AABB.tp<=0) {
							System.out.println("Team rocket stole your pokemon");
							world.setTile(Tile.test_tile,(int) 0,3);
							world.setTile(Tile.test_tile,(int) 0,4);
							world.setTile(Tile.test_tile,(int) 25,17);
							world.setTile(Tile.test_tile,(int) 23,17);
							world.setTile(Tile.test_tile,(int) 23,18);
							
						}
							
						else if(AABB.tp>0 && AABB.pp>0)
						{
							world.setTile(Tile.num[AABB.tp-1],(int) 23,17);
							world.setTile(Tile.num[AABB.pp-1],(int) 25,17);
							world.setTile(Tile.you,(int) 23,18);
						}
					}
				}
				world.render(tiles, shader, camera, window);
				player.render(shader, camera);
				
				window.swapBuffers();
				frames++;
			}

			
		}
		glfwTerminate(); // CLEARS MEMORY
	}
	

}
