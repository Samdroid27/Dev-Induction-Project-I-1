package render;
 

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;
import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterf;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
/**
 *
 * @author user
 */
public class Texture {
    private int textureObject;
    private int width;
    private int height;
    
    public Texture(String filename)
    {
        BufferedImage bufferedImage;
        try
        {
        	bufferedImage=ImageIO.read(new File("./images/"+filename));
            width=bufferedImage.getWidth();
            height=bufferedImage.getHeight();
             
           int[] pixels_raw= new int[width*height*4];
           pixels_raw = bufferedImage.getRGB(0, 0, width, height, null, 0,width);
           
           ByteBuffer pixels = BufferUtils.createByteBuffer(width*height*4);
           
           for(int i=0;i<height;i++)
           {
               for(int j=0;j<width;j++)
               {
                   int pixel= pixels_raw[(i*width + j)];
                   pixels.put((byte)((pixel >> 16)& 0xFF));    //red
                   pixels.put((byte)((pixel >> 8)& 0xFF));     //green
                   pixels.put((byte)(pixel & 0xFF));         //blue
                   pixels.put((byte)((pixel >> 24)& 0xFF));    //alpha
                   
               }
                   
           }
           pixels.flip();
           textureObject=glGenTextures();
           
           glBindTexture(GL_TEXTURE_2D, textureObject );
           glTexParameterf(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_NEAREST);
          glTexParameterf(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_NEAREST);
          glTexImage2D(GL_TEXTURE_2D,0,GL_RGBA,width,height,0,GL_RGBA,GL_UNSIGNED_BYTE,pixels); 
          
        } 
        catch(IOException e)
        {
            e.printStackTrace(); 
        }
         
        
    }
    
    protected void finalize() throws Throwable {
		glDeleteTextures(textureObject);
		super.finalize();
	}
    
    public void bind(int sampler)
        {
            if (sampler>=0 && sampler<= 31)
            {
                glActiveTexture(GL_TEXTURE0 + sampler);
                glBindTexture(GL_TEXTURE_2D,textureObject);
            }
        }
}
