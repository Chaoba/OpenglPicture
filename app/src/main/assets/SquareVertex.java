uniform mat4 uMVPMatrix;
attribute vec4 vPosition;
varying vec2 textureCoordinate;
void main() {
    textureCoordinate = vPosition.xy;
    gl_Position = uMVPMatrix * vPosition;
}