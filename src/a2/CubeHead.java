/**
 * Setups vertices and texture coordinates for cube with a face on it
 *
 * @author Jacob Hua
 * @version 1.0
 * @since 2021-03-19
 *
 */
package a2;

public class CubeHead {

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
                    0.0f, 0.5f, 1.0f, 0.0f, 1.0f, 0.5f,// top up fix
                    1.0f, 0.0f, 0.0f, 0.5f, 0.0f, 0.0f,// top down
                    1.0f, 1.0f, 0.0f, 0.5f, 1.0f, 0.5f,// front up fix
                    0.0f, 0.5f, 1.0f, 1.0f, 0.0f, 1.0f,// front down
                    1.0f, 0.5f, 0.0f, 0.0f, 1.0f, 0.0f,// back up
                    0.0f, 0.0f, 1.0f, 0.5f, 0.0f, 0.5f,// back down
                    0.0f, 0.5f, 1.0f, 0.0f, 1.0f, 0.5f,// left up fix
                    1.0f, 0.0f, 0.0f, 0.5f, 0.0f, 0.0f,// left down
                    1.0f, 0.5f, 0.0f, 0.0f, 1.0f, 0.0f,// right up
                    0.0f, 0.0f, 1.0f, 0.5f, 0.0f, 0.5f,// right down
                    1.0f, 0.5f, 0.0f, 0.0f, 1.0f, 0.0f,// bottom up
                    0.0f, 0.0f, 1.0f, 0.5f, 0.0f, 0.5f,// bottom down

            };


    public CubeHead(){

    }


    public float[] getVertices(){
        return cubePositions;
    }
    public float[] getTextureCoordinates(){ return textureCoordinates;}
}
