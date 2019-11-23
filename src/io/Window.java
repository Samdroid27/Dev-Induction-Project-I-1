package io;
import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;   
public class Window {
	private long window;
	private int width,height;
	private boolean fullscreen;
	private Input input;
	
	/*  Call backs are helpful to get the  exact error that have been crept in the program
	 *  allowing us to go to that set of code and correct it.
	 */
	public static void setCallbacks()
	{
		glfwSetErrorCallback(new GLFWErrorCallback() {

			@Override
			public void invoke(int error, long description) {
				// TODO Auto-generated method stub
				throw new IllegalStateException(GLFWErrorCallback.getDescription(description));
			}});
		
	}
	
	public Window()
	{
		setSize(640,480);
		setFullScreen(false);
	}
	
	public void createWindow(String title)
	{
		
		window=glfwCreateWindow(width,
				height,
				title,
				fullscreen ? glfwGetPrimaryMonitor() : 0,
				0);
		 if (window==0)                                                                          //CHECK IF WINDOW IS ABLE TO BE CREATED
         {
             throw new IllegalStateException("failed to create window");
         }
		 if (!fullscreen)
		 {
			 GLFWVidMode vid= glfwGetVideoMode(glfwGetPrimaryMonitor());
			 glfwSetWindowPos(window,(vid.width()-width)/2,(vid.height()-height)/2 );         //SETTING WINDOW POSITION
			 glfwShowWindow(window);
			 
		 }
		 glfwMakeContextCurrent(window);
		 
		 
		input=new Input (window);
	}
	public boolean shouldClose()
	{
		return glfwWindowShouldClose(window) != false;
	}
	public void swapBuffers()
	{
		glfwSwapBuffers(window);
	}
	public void setSize(int width, int height)
	{
		this.width =width;
		this.height = height;
	}
	public void setFullScreen(boolean fs)
	{
		this.fullscreen=fs;
	}
	
	public void update()
	{
		input.update();
		glfwPollEvents();
	}
	
	public boolean isFullScreen()
	{
		return fullscreen;
	}
	public int getWidth()
	{
		return width;
	}
	public int getHeight()
	{
		return height;
	}
	public long getWindow()
	{
		return window;
	}
	public Input getInput()
	{
		return input;
	}
}
