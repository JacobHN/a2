/**
 * creates a 3d virtual world using objects created in blender and objects created explicitly with vertexs that
 * can be traversed through using the wasdeq and up down left right arrow keys.
 *
 * @author Jacob Hua
 * @version 1.0
 * @since 2021-03-19
 *
 */
package a2;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;
import org.joml.*;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.Math;
import java.nio.FloatBuffer;

import static com.jogamp.opengl.GL.*;

public class Starter extends JFrame implements GLEventListener, KeyListener {

    private GLCanvas myCanvas;

    private int renderingProgram;
    private int axesRenderingProgram;

    private int vao[] = new int[1];
    private int vbo[] = new int[9];
    private float cameraX, cameraY, cameraZ;

    //world matrices
    private FloatBuffer vals = Buffers.newDirectFloatBuffer(16);
    private Matrix4fStack mvStack = new Matrix4fStack(8);
    private Matrix4f pMat = new Matrix4f();
    private Matrix4f vMat = new Matrix4f();
    private Matrix4f mMat = new Matrix4f();
    private int mvLoc;
    private int projLoc;
    private float aspect;

    //texture
    private int dummyTexture;
    private int gordonFaceTexture;
    private int importedModelTexture;
    private int shuttleTexture;
    private int mushroomTexture;
    private int cubeHeadTexture;

    //model
    private ImportedModel mushroom;
    private ImportedModel shuttle;
    private CubeHead cubeHead;
    private Cube cube;
    private Rectangle rectangle;

    //time variables
    private long elapsedTime;
    private long startTime;
    private long currentTime;
    private float tf;

    //movement variables
    private float movementIncrement;

    private float leftArmMovementAngle = 0;
    private float leftArmRotationDirection = 1;

    private float bowAngle = (float) Math.toRadians(1);
    private float bodyRotationDirection = 1;

    private float lookAtCenterAngle;

    //camera variables
    Camera camera;

    //boolean variables
    boolean axisFlag = true;

    /**
     * constructor
     */
    public Starter() {
        setTitle("assignment 2");
        setSize(600,600);
        myCanvas = new GLCanvas();
        myCanvas.addGLEventListener(this);
        myCanvas.addKeyListener(this);
        this.add(myCanvas);
        this.setVisible(true);
        Animator animator = new Animator(myCanvas);
        animator.start();
    }

    /**
     * initializes program
     * @param glAutoDrawable
     */
    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL4 gl = (GL4) GLContext.getCurrentGL();
        renderingProgram = Utils.createShaderProgram("a2/vertShader.glsl", "a2/fragShader.glsl");
        axesRenderingProgram = Utils.createShaderProgram("a2/axesVertShader.glsl", "a2/axesFragShader.glsl");

        startTime = System.currentTimeMillis();

        cameraX = 0.0f; cameraY = 2.0f; cameraZ = 15.0f;
        setupVertices();

        //camera position and angle variables
        camera = new Camera(cameraX, cameraY, cameraZ);

        //load textures
        dummyTexture = Utils.loadTexture("a2/brick1.jpg");
        gordonFaceTexture = Utils.loadTexture("a2/gordonPic.jpg");
        shuttleTexture = Utils.loadTexture("a2/spstob_1.jpg");
        mushroomTexture = Utils.loadTexture("a2/mushroom_color.png");
        cubeHeadTexture = Utils.loadTexture("a2/cubehead.png");

        //print out system aOpenGL, JOGL and JAVA
        System.out.println("OpenGL version: " +  gl.glGetString(GL_VERSION));
        System.out.println("JOGL version: " + Package.getPackage("com.jogamp.opengl").getImplementationVersion());
        System.out.println("Java version: " + System.getProperty("java.version"));
    }



    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {}

    /**
     * displays objects onto screen at clock speed
     * @param glAutoDrawable
     */
    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL4 gl = (GL4) GLContext.getCurrentGL();
        gl.glClear(GL_DEPTH_BUFFER_BIT);
        gl.glClear(GL_COLOR_BUFFER_BIT);

        gl.glUseProgram(renderingProgram);


        elapsedTime = System.currentTimeMillis() - startTime;
        tf = (float)elapsedTime/1000;

        mvLoc = gl.glGetUniformLocation(renderingProgram, "mv_matrix");
        projLoc = gl.glGetUniformLocation(renderingProgram, "proj_matrix");

        aspect = (float) myCanvas.getWidth() / (float) myCanvas.getHeight();
        pMat.setPerspective((float) Math.toRadians(60.0f), aspect, 0.1f, 1000.0f);
        gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));

        //rotation matrix
        camera.updateCameraRotation();
        //position matrix
        camera.updateCameraPosition();

        vMat = camera.vMatrix();
        mMat.identity();
        mMat.translate(0.0f,0.0f,0.0f);


        mvStack.pushMatrix();//pushes camera onto matrix
        mvStack.identity();
        mvStack.mul(vMat);
        mvStack.mul(mMat);


        //creates a body
        mvStack.pushMatrix();//translates entire body
        mvStack.translate((float)Math.sin(tf)*5.0f, 0.0f,(float)Math.cos(tf)*5.0f);

        mvStack.pushMatrix();
        mvStack.rotate((float)Math.toRadians(180), 0, 1, 0);
        mvStack.rotate(tf, 0, 1, 0);


        //rotates the body
        mvStack.pushMatrix();//bow rotation
        movementIncrement = (float)(elapsedTime - currentTime) / 2000;
        if(bowAngle <= Math.toRadians(0)) {
            bodyRotationDirection = 1;
        }else if(bowAngle >= Math.toRadians(30)){
            bodyRotationDirection = -1;
        }
        bowAngle += (bodyRotationDirection * movementIncrement);

        mvStack.translate(0.0f, -0.5f, 0.0f);
        mvStack.rotate((float)bowAngle, 1.0f, 0.0f, 0.0f);
        mvStack.translate(0.0f, 0.5f, 0.0f);


        gl.glUniformMatrix4fv(mvLoc, 1, false, mvStack.get(vals));

        gl.glBindBuffer(GL_ARRAY_BUFFER,vbo[0]);
        gl.glVertexAttribPointer(0,3,GL_FLOAT,false, 0, 0);
        gl.glEnableVertexAttribArray(0);

        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[1]);
        gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(1);

        gl.glActiveTexture(GL_TEXTURE0);
        gl.glBindTexture(GL_TEXTURE_2D, dummyTexture);

        gl.glEnable(GL_DEPTH_TEST);
        gl.glEnable(GL_LEQUAL);
        gl.glEnable(GL_CULL_FACE);

        gl.glDrawArrays(GL_TRIANGLES, 0, 36);


        //creates left arm

        //calculation for arm rotation
        movementIncrement = (float)(elapsedTime - currentTime) / 1000;
        if(leftArmMovementAngle <= -Math.toRadians(90)) {
            leftArmRotationDirection = 1;
        }else if(leftArmMovementAngle >= Math.toRadians(90)){
            leftArmRotationDirection = -1;
        }
        leftArmMovementAngle += (leftArmRotationDirection * movementIncrement);

        mvStack.pushMatrix();//translate arm
        mvStack.translate(-1.3f, 0.2f, 0.0f);


        mvStack.pushMatrix();//rotate arm
        mvStack.translate(0.0f, 1.1f, 0.0f);
        mvStack.rotate((float)-Math.toRadians(140), 1.0f, 0.0f, 0.0f);
        mvStack.translate(0.0f, -1.1f, 0.0f);


        mvStack.pushMatrix();//scale arm
        mvStack.scale(0.25f, 0.9f, 0.25f);

        gl.glUniformMatrix4fv(mvLoc, 1, false, mvStack.get(vals));

        gl.glBindBuffer(GL_ARRAY_BUFFER,vbo[0]);
        gl.glVertexAttribPointer(0,3,GL_FLOAT,false, 0, 0);
        gl.glEnableVertexAttribArray(0);

        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[1]);
        gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(1);

        gl.glActiveTexture(GL_TEXTURE0);
        gl.glBindTexture(GL_TEXTURE_2D, dummyTexture);

        gl.glEnable(GL_DEPTH_TEST);
        gl.glEnable(GL_LEQUAL);
        gl.glEnable(GL_CULL_FACE);

        gl.glDrawArrays(GL_TRIANGLES, 0, 36);

        mvStack.popMatrix();//arm scale
        mvStack.popMatrix();//arm rotate
        mvStack.popMatrix();//arm translation

        //creates right arm
        mvStack.pushMatrix();//arm translation
        mvStack.translate(1.3f, 0.2f, 0.0f);

        mvStack.pushMatrix();//rotate arm
        mvStack.translate(0.0f, 1.1f, 0.0f);
        mvStack.rotate((float)-Math.toRadians(140), 1.0f, 0.0f, 0.0f);
        mvStack.translate(0.0f, -1.1f, 0.0f);

        mvStack.pushMatrix();//arm scale
        mvStack.scale(0.25f, 0.9f, 0.25f);
        gl.glUniformMatrix4fv(mvLoc, 1, false, mvStack.get(vals));

        gl.glBindBuffer(GL_ARRAY_BUFFER,vbo[0]);
        gl.glVertexAttribPointer(0,3,GL_FLOAT,false, 0, 0);
        gl.glEnableVertexAttribArray(0);

        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[1]);
        gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(1);

        gl.glActiveTexture(GL_TEXTURE0);
        gl.glBindTexture(GL_TEXTURE_2D, dummyTexture);

        gl.glEnable(GL_DEPTH_TEST);
        gl.glEnable(GL_LEQUAL);
        gl.glEnable(GL_CULL_FACE);

        gl.glDrawArrays(GL_TRIANGLES, 0, 36);
        mvStack.popMatrix(); // scale
        mvStack.popMatrix(); // rotate
        mvStack.popMatrix(); // translate


        //creates head
        mvStack.pushMatrix();//head translation
        mvStack.translate(0.0f, 2.55f, 0.0f);
        gl.glUniformMatrix4fv(mvLoc, 1, false, mvStack.get(vals));

        gl.glBindBuffer(GL_ARRAY_BUFFER,vbo[2]);
        gl.glVertexAttribPointer(0,3,GL_FLOAT,false, 0, 0);
        gl.glEnableVertexAttribArray(0);

        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[3]);
        gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(1);

        gl.glActiveTexture(GL_TEXTURE0);
        gl.glBindTexture(GL_TEXTURE_2D, cubeHeadTexture);

        gl.glEnable(GL_DEPTH_TEST);
        gl.glEnable(GL_LEQUAL);
        gl.glEnable(GL_CULL_FACE);

        gl.glDrawArrays(GL_TRIANGLES, 0, 36);
        mvStack.popMatrix();//head translation


        mvStack.popMatrix();//bow movement

        //creates left leg
        mvStack.pushMatrix();//leg translation
        mvStack.translate(-0.5f, -2.0f, 0.0f);
        mvStack.pushMatrix();//leg scale
        mvStack.scale(0.25f, 0.9f, 0.25f);
        gl.glUniformMatrix4fv(mvLoc, 1, false, mvStack.get(vals));

        gl.glBindBuffer(GL_ARRAY_BUFFER,vbo[0]);
        gl.glVertexAttribPointer(0,3,GL_FLOAT,false, 0, 0);
        gl.glEnableVertexAttribArray(0);

        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[1]);
        gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(1);

        gl.glActiveTexture(GL_TEXTURE0);
        gl.glBindTexture(GL_TEXTURE_2D, dummyTexture);

        gl.glEnable(GL_DEPTH_TEST);
        gl.glEnable(GL_LEQUAL);
        gl.glEnable(GL_CULL_FACE);

        gl.glDrawArrays(GL_TRIANGLES, 0, 36);
        mvStack.popMatrix(); //leg scale
        mvStack.popMatrix(); //leg translation

        //right leg
        mvStack.pushMatrix();//leg translation
        mvStack.translate(0.5f, -2.0f, 0.0f);
        mvStack.pushMatrix();//leg scale
        mvStack.scale(0.25f, 0.9f, 0.25f);
        gl.glUniformMatrix4fv(mvLoc, 1, false, mvStack.get(vals));

        gl.glBindBuffer(GL_ARRAY_BUFFER,vbo[0]);
        gl.glVertexAttribPointer(0,3,GL_FLOAT,false, 0, 0);
        gl.glEnableVertexAttribArray(0);

        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[1]);
        gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(1);

        gl.glActiveTexture(GL_TEXTURE0);
        gl.glBindTexture(GL_TEXTURE_2D, dummyTexture);

        gl.glEnable(GL_DEPTH_TEST);
        gl.glEnable(GL_LEQUAL);
        gl.glEnable(GL_CULL_FACE);

        gl.glDrawArrays(GL_TRIANGLES, 0, 36);
        mvStack.popMatrix(); //leg scale
        mvStack.popMatrix(); //leg translation

        //pops the rest of the stack
        mvStack.popMatrix(); //pop rotation
        mvStack.popMatrix(); //pop body


        //creates mushroom
        mvStack.pushMatrix();//mushroom translation
        mvStack.translate(0, -2.0f,0);
        mvStack.pushMatrix();//mushroom rotate
        mvStack.rotate(tf, 0, 1, 0);
        mvStack.pushMatrix();//mushroom scale
        mvStack.scale(0.5f, 0.5f, 0.5f);
        gl.glUniformMatrix4fv(mvLoc, 1, false, mvStack.get(vals));

        gl.glBindBuffer(GL_ARRAY_BUFFER,vbo[4]);
        gl.glVertexAttribPointer(0,3,GL_FLOAT,false, 0, 0);
        gl.glEnableVertexAttribArray(0);

        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[5]);
        gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(1);

        gl.glActiveTexture(GL_TEXTURE0);
        gl.glBindTexture(GL_TEXTURE_2D, mushroomTexture);

        gl.glEnable(GL_DEPTH_TEST);
        gl.glEnable(GL_LEQUAL);
        gl.glEnable(GL_CULL_FACE);

        gl.glDrawArrays(GL_TRIANGLES, 0, mushroom.getNumVertices());

        mvStack.popMatrix(); //mushroom scale
        mvStack.popMatrix(); //mushroom rotate
        mvStack.popMatrix(); // mushroom translation


        //creates shuttle
        mvStack.pushMatrix();//shuttle translation
        mvStack.translate(0, 5,0);
        mvStack.translate((float)Math.sin(tf * 2)*3.0f, 0.0f,(float)Math.cos(tf * 2)*3.0f);
        mvStack.pushMatrix();//shuttle rotate
        mvStack.rotate((float)Math.toRadians(-90), 0, 1, 0);
        mvStack.rotate(tf * 2, 0, 1, 0);
        mvStack.pushMatrix();//shuttle scale
        mvStack.scale(1.5f, 1.5f, 1.5f);
        gl.glUniformMatrix4fv(mvLoc, 1, false, mvStack.get(vals));

        gl.glBindBuffer(GL_ARRAY_BUFFER,vbo[6]);
        gl.glVertexAttribPointer(0,3,GL_FLOAT,false, 0, 0);
        gl.glEnableVertexAttribArray(0);

        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[7]);
        gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(1);

        gl.glActiveTexture(GL_TEXTURE0);
        gl.glBindTexture(GL_TEXTURE_2D, shuttleTexture);

        gl.glEnable(GL_DEPTH_TEST);
        gl.glEnable(GL_LEQUAL);
        gl.glEnable(GL_CULL_FACE);

        gl.glDrawArrays(GL_TRIANGLES, 0, mushroom.getNumVertices());
        mvStack.popMatrix(); //mushroom scale
        mvStack.popMatrix(); //mushroom rotate
        mvStack.popMatrix(); // mushroom translation

        //creates axis if spacebar is pushed
        if(axisFlag) {
            mvStack.pushMatrix();
            gl.glUseProgram(axesRenderingProgram);
            mvLoc = gl.glGetUniformLocation(axesRenderingProgram, "mv_matrix");
            projLoc = gl.glGetUniformLocation(axesRenderingProgram, "proj_matrix");

            gl.glUniformMatrix4fv(projLoc, 1, false, pMat.get(vals));
            gl.glUniformMatrix4fv(mvLoc, 1, false, mvStack.get(vals));


            gl.glDrawArrays(GL_LINES, 0, 6);
            mvStack.popMatrix();
        }

        mvStack.popMatrix();//pop camera
        currentTime = System.currentTimeMillis() - startTime;

    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {}

    /**
     * sets up the vertices and texture coordinates and binds them to vbos
     */
    private void setupVertices(){
        GL4 gl = (GL4) GLContext.getCurrentGL();

        cubeHead = new CubeHead();
        cube = new Cube();
        rectangle = new Rectangle();
        mushroom = new ImportedModel("mushroom.obj");
        shuttle = new ImportedModel("shuttle.obj");



        gl.glGenVertexArrays(vao.length, vao, 0);
        gl.glBindVertexArray(vao[0]);
        gl.glGenBuffers(vbo.length, vbo, 0);

        //binds rectangle model
        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);
        FloatBuffer rectangleVertBuf = Buffers.newDirectFloatBuffer(rectangle.getVertices());
        gl.glBufferData(GL_ARRAY_BUFFER, rectangleVertBuf.limit()*4, rectangleVertBuf, GL_STATIC_DRAW);

        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[1]);
        FloatBuffer rectangleTexBuf = Buffers.newDirectFloatBuffer(rectangle.getTextureCoordinates());
        gl.glBufferData(GL_ARRAY_BUFFER, rectangleTexBuf.limit()*4, rectangleTexBuf, GL_STATIC_DRAW);

        //binds cube model
        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[2]);
        FloatBuffer cubeVertBuf = Buffers.newDirectFloatBuffer(cubeHead.getVertices());
        gl.glBufferData(GL_ARRAY_BUFFER, cubeVertBuf.limit()*4, cubeVertBuf, GL_STATIC_DRAW);

        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[3]);
        FloatBuffer cubeTexBuf = Buffers.newDirectFloatBuffer(cubeHead.getTextureCoordinates());
        gl.glBufferData(GL_ARRAY_BUFFER, cubeTexBuf.limit()*4, cubeTexBuf, GL_STATIC_DRAW);

        //binds mushroom model
        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[4]);
        FloatBuffer mushVertBuf = Buffers.newDirectFloatBuffer(mushroom.getPValues());
        gl.glBufferData(GL_ARRAY_BUFFER, mushVertBuf.limit()*4, mushVertBuf, GL_STATIC_DRAW);

        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[5]);
        FloatBuffer mushTextBuf = Buffers.newDirectFloatBuffer(mushroom.getTValues());
        gl.glBufferData(GL_ARRAY_BUFFER, mushTextBuf.limit()*4, mushTextBuf, GL_STATIC_DRAW);

        //binds spaceship model
        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[6]);
        FloatBuffer vertBuf = Buffers.newDirectFloatBuffer(shuttle.getPValues());
        gl.glBufferData(GL_ARRAY_BUFFER, vertBuf.limit()*4, vertBuf, GL_STATIC_DRAW);

        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[7]);
        FloatBuffer textBuf = Buffers.newDirectFloatBuffer(shuttle.getTValues());
        gl.glBufferData(GL_ARRAY_BUFFER, textBuf.limit()*4, textBuf, GL_STATIC_DRAW);

    }

    /**
     * calls constructor
     * @param args arguements
     */
    public static void main(String[] args){
       new Starter();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {}

    /**
     *  key pushed
     * @param keyEvent key pushed
     */
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch(keyEvent.getKeyCode()){
            //key push a, move camera left
            case KeyEvent.VK_A:
                camera.moveLeft();
                System.out.println("press a");
                break;
            //key push s, move camera back
            case KeyEvent.VK_S:
                camera.moveBack();
                System.out.println("press s");
                break;
            //key push d, move camera right
            case KeyEvent.VK_D:
                camera.moveRight();
                System.out.println("press d");
                break;
            //key push w, move camera forward
            case KeyEvent.VK_W:
                camera.moveForward();
                System.out.println("press w");
                break;
            // key push q, moves up
            case KeyEvent.VK_Q:
                camera.moveUp();
                System.out.println("press w");
                break;
            // key push e moves down
            case KeyEvent.VK_E:
                camera.moveDown();
                System.out.println("press w");
                break;
            // key push left look left
            case KeyEvent.VK_LEFT:
                camera.lookLeft();
                break;
            // key push down look down
            case KeyEvent.VK_DOWN:
                camera.lookDown();
                break;
            // key push right look right
            case KeyEvent.VK_RIGHT:
                camera.lookRight();
                break;
            // key push up, look up
            case KeyEvent.VK_UP:
                camera.lookUp();
                break;
            case KeyEvent.VK_SPACE:
                axisFlag = (axisFlag) ? false : true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {}
}
