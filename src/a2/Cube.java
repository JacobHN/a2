/**
 * Setups vertices and texture coordinates for cube with a face on it
 *
 * @author Jacob Hua
 * @version 1.0
 * @since 2021-03-19
 *
 */

package a2;

public class Cube {

    private float[] cubePositions =
            {
                    -1.0f, 1.0f, -1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, -1.0f,//top up
                    1.0f, 1.0f, 1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f,//top down
                    1.0f, 1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f,-1.0f, 1.0f,  //front up
                    -1.0f, -1.0f, 1.0f, 1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f,  //front down
                    -1.0f, 1.0f, -1.0f, 1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f,//back up
                    1.0f, -1.0f, -1.0f, -1.0f, 1.0f, -1.0f, 1.0f, 1.0f, -1.0f,//back down
                    -1.0f, 1.0f, 1.0f, -1.0f , -1.0f, -1.0f, -1.0f, -1.0f, 1.0f, //left up
                    -1.0f , -1.0f, -1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, -1.0f, //left down
                    1.0f, 1.0f, -1.0f, 1.0f, -1.0f, 1.0f, 1.0f, -1.0f, -1.0f,//right up
                    1.0f, -1.0f, 1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f, 1.0f,//right down
                    -1.0f, -1.0f, -1.0f, 1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, //Bottom up
                    1.0f, -1.0f, 1.0f, -1.0f, -1.0f, -1.0f, 1.0f, -1.0f, -1.0f //Bottom down
            };

    private float[] textureCoordinates =

            {
                    1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f,
                    0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,
                    1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f,
                    0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,
                    1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f,
                    0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,
                    1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f,
                    0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,
                    1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f,
                    0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,
                    1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f,
                    0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f

            };

    public Cube(){

    }


    public float[] getVertices(){
        return cubePositions;
    }
    public float[] getTextureCoordinates(){ return textureCoordinates;}
}
