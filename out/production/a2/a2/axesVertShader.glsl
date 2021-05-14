#version 430


layout (location=0) in vec3 pos;
layout (location=1) in vec2 texCoord;
out vec2 tc;

uniform mat4 mv_matrix;
uniform mat4 proj_matrix;
out vec4 varyingColor;
layout (binding=0) uniform sampler2D samp;

void main(void){
    const vec4 vertices[6] = vec4[6]
    (vec4(0.0,0.0,0.0, 1.0),
    vec4( 6.0,0.0,0.0, 1.0),
    vec4( 0.0,0.0,0.0, 1.0),
    vec4( 0.0,6.0,0.0, 1.0),
    vec4( 0.0,0.0,0.0, 1.0),
    vec4( 0.0,0.0,6.0, 1.0));
    if(gl_VertexID == 0 || gl_VertexID == 1){
        varyingColor = vec4(1.0f, 0.0f, 0.0f, 1.0f);
    }
    else if(gl_VertexID == 2 || gl_VertexID == 3){
        varyingColor = vec4(0.0f, 1.0f, 0.0f, 1.0f);
    }
    else if(gl_VertexID == 4 || gl_VertexID == 5){
        varyingColor = vec4(0.0f, 0.0f, 1.0f, 1.0f);
    }
    gl_Position = proj_matrix * mv_matrix * vertices[gl_VertexID];

}