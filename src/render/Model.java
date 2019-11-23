package render;


import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;

import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;//access to shader commands
/**
 *
 * @author user
 */
public class Model {
    private int drawcount;
    private int vertexObject;
    private int textureCoordObject;
    private int indexObject;
    public Model(float[] vertices,float[] tex_coords,int[] indices)
    {
        drawcount=indices.length;
        
        
        vertexObject=glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER,vertexObject);
        glBufferData(GL_ARRAY_BUFFER,createBuffer(vertices),GL_STATIC_DRAW);
        
        
        textureCoordObject=glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER,textureCoordObject);
        glBufferData(GL_ARRAY_BUFFER,createBuffer(tex_coords),GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER,0);
        
        indexObject=glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,indexObject);
        IntBuffer buffer=BufferUtils.createIntBuffer(indices.length);
        buffer.put(indices);
        buffer.flip();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,buffer,GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);
        glBindBuffer(GL_ARRAY_BUFFER,0);
    }
    
    protected void finalize() throws Throwable {
		glDeleteBuffers(vertexObject);
		glDeleteBuffers(textureCoordObject);
		glDeleteBuffers(indexObject);
		super.finalize();
	}
    
    public void render()
    {
       glEnableVertexAttribArray(0);
       glEnableVertexAttribArray(1);
    //    glEnableClientState(GL_VERTEX_ARRAY);
     //   glEnableClientState(GL_TEXTURE_COORD_ARRAY);
        glBindBuffer(GL_ARRAY_BUFFER,vertexObject);
        glVertexAttribPointer(0,3,GL_FLOAT,false,0,0);
        
        glBindBuffer(GL_ARRAY_BUFFER,textureCoordObject);
        glVertexAttribPointer(1,2,GL_FLOAT,false,0,0);
       
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,indexObject);
        glDrawElements(GL_TRIANGLES,drawcount,GL_UNSIGNED_INT,0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);
        
      //  glDrawArrays(GL_TRIANGLES,0,draw_count);
        glBindBuffer(GL_ARRAY_BUFFER,0);
    //    glDisableClientState(GL_VERTEX_ARRAY);
    //    glDisableClientState(GL_TEXTURE_COORD_ARRAY);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
    }
    private FloatBuffer createBuffer(float[] data)
    {
        FloatBuffer buffer=BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
}
