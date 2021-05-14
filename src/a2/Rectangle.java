/**
 * Sets up the vertices and texture coordinates of a rectangle
 *
 * @author Jacob Hua
 * @version 1.0
 * @since 2021-03-19
 *
 */
package a2;

public class Rectangle {
    private float[] rectanglePositions =
            {
                    -1.0f, 1.5f, -1.0f, 1.0f, 1.5f, 1.0f, 1.0f, 1.5f, -1.0f,//top up
                    1.0f, 1.5f, 1.0f, -1.0f, 1.5f, -1.0f, -1.0f, 1.5f, 1.0f,//top down
                    1.0f, 1.5f, 1.0f, -1.0f, -1.5f, 1.0f, 1.0f,-1.5f, 1.0f,  //front up
                    -1.0f, -1.5f, 1.0f, 1.0f, 1.5f, 1.0f, -1.0f, 1.5f, 1.0f,  //front down
                    -1.0f, 1.5f, -1.0f, 1.0f, -1.5f, -1.0f, -1.0f, -1.5f, -1.0f,//back up
                    1.0f, -1.5f, -1.0f, -1.0f, 1.5f, -1.0f, 1.0f, 1.5f, -1.0f,//back down
                    -1.0f, 1.5f, 1.0f, -1.0f , -1.5f, -1.0f, -1.0f, -1.5f, 1.0f, //left up
                    -1.0f , -1.5f, -1.0f, -1.0f, 1.5f, 1.0f, -1.0f, 1.5f, -1.0f, //left down
                    1.0f, 1.5f, -1.0f, 1.0f, -1.5f, 1.0f, 1.0f, -1.5f, -1.0f,//right up
                    1.0f, -1.5f, 1.0f, 1.0f, 1.5f, -1.0f, 1.0f, 1.5f, 1.0f,//right down
                    -1.0f, -1.5f, -1.0f, 1.0f, -1.5f, 1.0f, -1.0f, -1.5f, 1.0f, //Bottom up
                    1.0f, -1.5f, 1.0f, -1.0f, -1.5f, -1.0f, 1.0f, -1.5f, -1.0f //Bottom down
            };

    private float[] textureCoordinates =
            {
                    1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f,// top up fix
                    0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,// top down
                    1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f,// front up fix
                    0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,// front down
                    1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f,// back up
                    0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,// back down
                    1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f,// left up fix
                    0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,// left down
                    1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f,// right up
                    0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,// right down
                    1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f,// bottom up
                    0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f// bottom down

            };

    public Rectangle(){

    }


    public float[] getVertices(){
        return rectanglePositions;
    }
    public float[] getTextureCoordinates(){ return textureCoordinates;}

}
