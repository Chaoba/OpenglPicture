precision highp float;
varying vec2 textureCoordinate;
const float DIM = 1024.0;
uniform float index;

uniform vec4 vColor;
float _sq(float x){
    return x*x;
}
void main() {
        float i = -(textureCoordinate.x-1.0) * 512.0;
        float j = -(textureCoordinate.y-1.0) * 512.0;
        float r,g,b;
        if (index == 0.0){
            float s=3.0/(j+99.0);
            r= (mod((i+DIM)*s+j*s,2.0)+mod((DIM*2.0-i)*s+j*s,2.0))*127.0;
            g= r;
            b= r;
        } else if (index == 1.0){
            float s=3.0/(j+99.0);
            float y=(j+sin((i*i+sqrt(j-700.0)*5.0)/100.0/DIM)*35.0)*s;
            r= (mod((i+DIM)*s+y,2.0)+mod((DIM*2.0-i)*s+y,2.0))*127.0;
            g=r;
            b=r;
        } else if (index == 2.0){
            float P = 6.03;
            float s=3./(j+250.),y=(j+sin((i*i+pow(j-700.0,2.)*5.0)/100./DIM+P)*15.)*s;
            r= (mod((i+DIM)*s+y,2.)+mod((DIM*2.-i)*s+y,2.))*127.;
            g= (mod(5.*((i+DIM)*s+y),2.)+mod(5.*((DIM*2.-i)*s+y),2.))*127.;
            b= (mod(29.*((i+DIM)*s+y),2.)+mod(29.*((DIM*2.-i)*s+y),2.))*127.;
        } else if (index == 3.0){
            r= _sq(sqrt(i*1.0)+sqrt(j*1.0));
            g= _sq(sqrt(512.-i*1.0)+sqrt(512.-j*1.0));
            b= _sq(sqrt(1024.0-i)+sqrt(1024.0-j));
        } else if (index == 4.0){
            r= sqrt(_sq((512.0-i))+_sq((512.0-j)));
            g= 10240./sqrt(_sq((512.0-i)/2.)+_sq((512.0-j)/.2));
            b= sqrt(_sq(r*1.0)+_sq(g));
        } else if(index == 5.0){
            r= _sq((1024.-i)*(1024.-j)/1024.)/1024.*2.;
            g= _sq((512.-i)*(521.-j)/512.)/512.*2.;
            b= _sq(i*j/1024.)/1024.;
        } else if(index == 6.0){
            r= _sq(cos(atan(j-512.0,i-512.0)/2.))*255.;
            g= _sq(cos(atan(j-512.0,i-512.0)/2.0-2.*acos(-1.0)/3.))*255.;
            b= _sq(cos(atan(j-512.0,i-512.0)/2.0+2.*acos(-1.0)/3.))*255.;
        } else if(index == 7.0){
            r= sqrt(_sq(512.0-i)+_sq(512.0-j));
            g= sqrt(_sq(1024.0-i)+_sq(1024.0-j));
            b= sqrt(_sq(i*1.0)+_sq(j*1.0));
        } else if(index == 8.0){
            r= sqrt(_sq((512.0-i)/2.)+_sq((512.0-j)/2.));
            g= sqrt(_sq((1024.0-i)/4.)+_sq((1024.0-j)/4.));
            b= sqrt(_sq(i*1.0/4.)+_sq(j*1.0/4.));
        } else if(index == 9.0){
            float k=256.;
            r= abs(_sq((512.0-i))/k-_sq((512.0-j))/k);
            g= abs(_sq((1024.0-i))/(k*2.)-_sq((1024.0-j))/(k*2.));
            b= abs(_sq(i*1.0)/(k*2.)-_sq(j*1.0)/(k*2.));
        } else if(index == 10.0){
            r= abs(_sq(512.0-i))/abs((512.0-j));
            g= abs(_sq(512.0-j))/abs((512.0-i));
            b= abs(_sq(512.0-i))/abs((1024.-j));
        } else if(index == 11.0){
            r=sin((512.0-i)/(512.0-j))*255.;
            g=sin((512.0-j)/(512.0-i))*255.;
            b=cos((512.0-i)/(512.0-j))*255.;
        } else if(index == 12.0){
            r=cos((512.0-i)/(512.0-j))*255.;
            g=cos((512.0-j)/(512.0-i))*255.;
            b=sin((512.0-i)/(512.0-j))*255.;
        } else if(index == 13.0){
            r=acos((512.0-i)/(512.0-j))*255.;
            g=acos((512.0-j)/(512.0-i))*255.;
            b=asin((512.0-i)/(512.0-j))*255.;
        } else if(index == 14.0){
            r=asin((512.0-i)/(512.0-j))*255.;
            g=asin((512.0-j)/(512.0-i))*255.;
            b=acos((512.0-i)/(512.0-j))*255.;
        } else if(index == 15.0){
            r=1024.* atan(abs(j-512.0),abs(i-512.0));
            g=512.* atan(abs(j-512.0),abs(i-512.0));
            b=256.* atan(abs(j-512.0),abs(i-512.0));
        }

        vec3 rgb = vec3(r,g,b);
        vec3 rgb255 = vec3(255.,255.,255.);
        rgb = mod(rgb, rgb255)/rgb255;
        vec4 rgba = vec4(rgb,1.0);
        gl_FragColor = rgba;
}
