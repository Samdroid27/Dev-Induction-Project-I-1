package io;
 import static org.lwjgl.glfw.GLFW.*;

import collision.Collision;
 
 
public class Input {
	private long window;
	
	private boolean keys[];
	
	public Input(long window)
	{
		this.window = window;
		this.keys=new boolean [GLFW_KEY_LAST];
		for (int i=32;i< GLFW_KEY_LAST;i++)
		{
			keys[i] =false;
		}
	}
	
	public boolean isKeyDown(int key)
	{
		
			return (glfwGetKey(window,key) == 1);
		
	}
	public boolean isKeyPressed(int key)
	{
		
		return (isKeyDown(key)  && !keys[key]);
	}
	public boolean isKeyReleased(int key)
	{
		return (!isKeyDown(key)  && keys[key]);
	}
	/*   Left mouse button code is 0
	 *   Right mouse button code is 1
	 *   Scroll mouse button code is 2
	 */
	public boolean isMouseButtonDown(int button)                 
	{
		
			return glfwGetMouseButton(window,button) == 1;
		
			
	}
	
	
	public void update()
	{
		for (int i=32;i< GLFW_KEY_LAST;i++)
		{
			keys[i] =isKeyDown(i);
		}
	}
	
}
