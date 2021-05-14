/**
 * Creates and stores camera matrices within 3d world
 * @author Jacob Hua
 * @version 1.0
 * @since 2021-03-19
 *
 */
package a2;

import org.joml.Matrix4f;
import org.joml.Vector3f;


public class Camera {
    //camera variables
    Vector3f cameraPositionVector;
    Vector3f uVector;
    Vector3f vVector;
    Vector3f nVector;

    Matrix4f cameraPosition;
    Matrix4f cameraRotation;

    /**
     * constructor
     * @param cameraX camera x axis
     * @param cameraY camera y axis
     * @param cameraZ camera z axis
     */
    public Camera(float cameraX, float cameraY, float cameraZ){

        uVector = new Vector3f(1,0,0);
        vVector = new Vector3f(0,1,0);
        nVector = new Vector3f(0,0,1);

        cameraPositionVector = new Vector3f(cameraX, cameraY, cameraZ);

        cameraRotation = new Matrix4f(
                uVector.get(0), uVector.get(1), uVector.get(2), 0,
                vVector.get(0), vVector.get(1), vVector.get(2), 0,
                nVector.get(0), nVector.get(1), nVector.get(2), 0,
                0,0,0,1
        );
        cameraRotation.transpose();

        cameraPosition = new Matrix4f(
                1.0f, 0.0f, 0.0f, -cameraPositionVector.get(0),
                0.0f, 1.0f, 0.0f, -cameraPositionVector.get(1),
                0.0f, 0.0f, 1.0f, -cameraPositionVector.get(2),
                0.0f, 0.0f, 0.0f, 1.0f
        );
        cameraPosition.transpose();
    }

    /**
     * updates camera rotation matrix
     */
    public void updateCameraRotation(){
        cameraRotation = new Matrix4f(
                uVector.get(0), uVector.get(1), uVector.get(2), 0,
                vVector.get(0), vVector.get(1), vVector.get(2), 0,
                nVector.get(0), nVector.get(1), nVector.get(2), 0,
                0,0,0,1
        );
        cameraRotation.transpose();
    }

    /**
     * updates camera position matrix
     */
    public void updateCameraPosition(){
        cameraPosition = new Matrix4f(
                1.0f, 0.0f, 0.0f, -cameraPositionVector.get(0),
                0.0f, 1.0f, 0.0f, -cameraPositionVector.get(1),
                0.0f, 0.0f, 1.0f, -cameraPositionVector.get(2),
                0.0f, 0.0f, 0.0f, 1.0f
        );
        cameraPosition.transpose();
    }

    /**
     * creates the view matrix from position and rotation
     * @return the view matrix
     */
    public Matrix4f vMatrix(){
        return cameraRotation.mul(cameraPosition);
    }

    /**
     * moves the camera left
     */
    public void moveLeft() {
        cameraPositionVector = cameraPositionVector.sub(uVector);
    }

    /**
     * moves the camera back
     */
    public void moveBack(){
        cameraPositionVector = cameraPositionVector.add(nVector);
    }

    /**
     * moves the camera right
     */
    public void moveRight() {
        cameraPositionVector = cameraPositionVector.add(uVector);
    }

    /**
     * moves the camera forward
     */
    public void moveForward(){
        cameraPositionVector = cameraPositionVector.sub(nVector);
    }

    /**
     * moves the camera up
     */
    public void moveUp() {
        cameraPositionVector = cameraPositionVector.add(vVector);
    }

    /**
     * moves the camera down
     */
    public void moveDown() {
        cameraPositionVector = cameraPositionVector.sub(vVector);
    }

    /**
     * makes camera look to left
     */
    public void lookLeft() {
    nVector = nVector.rotateAxis((float) Math.toRadians(5.0), vVector.get(0), vVector.get(1), vVector.get(2));
    uVector = uVector.rotateAxis((float) Math.toRadians(5.0), vVector.get(0), vVector.get(1), vVector.get(2));
    }

    /**
     * makes camera look down
     */
    public void lookDown() {
        nVector = nVector.rotateAxis((float) Math.toRadians(-5.0), uVector.get(0), uVector.get(1), uVector.get(2));
        vVector = vVector.rotateAxis((float) Math.toRadians(-5.0), uVector.get(0), uVector.get(1), uVector.get(2));
    }

    /**
     *  makes camera look to right
     */
    public void lookRight() {
        nVector = nVector.rotateAxis((float) Math.toRadians(-5.0), vVector.get(0), vVector.get(1), vVector.get(2));
        uVector = uVector.rotateAxis((float) Math.toRadians(-5.0), vVector.get(0), vVector.get(1), vVector.get(2));
    }

    /**
     * makes camerea look up
     */
    public void lookUp() {
        nVector = nVector.rotateAxis((float) Math.toRadians(5.0), uVector.get(0), uVector.get(1), uVector.get(2));
        vVector = vVector.rotateAxis((float) Math.toRadians(5.0), uVector.get(0), uVector.get(1), uVector.get(2));
    }

    public Vector3f getnVector() { return nVector; }
    public Vector3f getuVector() { return uVector; }
    public Vector3f getvVector() { return vVector; }
    public Matrix4f getCameraPosition() { return cameraPosition; }
    public Matrix4f getCameraRotation() { return cameraRotation; }
    public Vector3f getCameraPositionVector() {return cameraPositionVector; }

    public void setnVector(Vector3f nVector) { this.nVector = nVector; }
    public void setuVector(Vector3f uVector) { this.uVector = uVector; }
    public void setvVector(Vector3f vVector) { this.vVector = vVector; }
    public void setCameraPosition(Matrix4f cameraPosition) { this.cameraPosition = cameraPosition; }
    public void setCameraRotation(Matrix4f cameraRotation) { this.cameraRotation = cameraRotation; }
    public void setCameraPositionVector(Vector3f cameraPositionVector) { this.cameraPositionVector = cameraPositionVector; }
}
