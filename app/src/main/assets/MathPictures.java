precision highp float;
varying vec2 textureCoordinate;
const float DIM = 1024.0;
uniform float index;

uniform vec4 vColor;
void main() {
        float i = textureCoordinate.x * 512.0;
        float j = textureCoordinate.y * 512.0;
        float r,g,b;

        if (index == 0.0){
            r=sin((512.0-i)/(512.0-j));
            g=sin((512.0-j)/(512.0-i));
            b=cos((512.0-i)/(512.0-j));
        } else if(index == 1.0){
            r=cos((512.0-i)/(512.0-j));
            g=cos((512.0-j)/(512.0-i));
            b=sin((512.0-i)/(512.0-j));
        }
    vec4 c = vec4(r,g,b,0.0);
    gl_FragColor = c;
}
