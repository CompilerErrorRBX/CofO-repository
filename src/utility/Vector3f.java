package utility;

import java.util.logging.Logger;

public class Vector3f  {
    
    private static final Logger logger = Logger.getLogger(Vector3f.class.getName());

    public final static Vector3f ZERO = new Vector3f(0, 0, 0);
    public final static Vector3f NAN = new Vector3f(Float.NaN, Float.NaN, Float.NaN);
    public final static Vector3f UNIT_X = new Vector3f(1, 0, 0);
    public final static Vector3f UNIT_Y = new Vector3f(0, 1, 0);
    public final static Vector3f UNIT_Z = new Vector3f(0, 0, 1);
    public final static Vector3f UNIT_XYZ = new Vector3f(1, 1, 1);
    public final static Vector3f POSITIVE_INFINITY = new Vector3f(
            Float.POSITIVE_INFINITY,
            Float.POSITIVE_INFINITY,
            Float.POSITIVE_INFINITY);
    public final static Vector3f NEGATIVE_INFINITY = new Vector3f(
            Float.NEGATIVE_INFINITY,
            Float.NEGATIVE_INFINITY,
            Float.NEGATIVE_INFINITY);

    public float x;
    public float y;
    public float z;

    public Vector3f() {
        x = y = z = 0;
    }

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3f(Vector3f copy) {
        this.set(copy);
    }

    public Vector3f set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vector3f set(Vector3f vect) {
        this.x = vect.x;
        this.y = vect.y;
        this.z = vect.z;
        return this;
    }
    
    public Vector3f add(Vector3f vec) {
        if (null == vec) {
            return null;
        }
        return new Vector3f(x + vec.x, y + vec.y, z + vec.z);
    }
    
    public Vector3f add(Vector3f vec, Vector3f result) {
        result.x = x + vec.x;
        result.y = y + vec.y;
        result.z = z + vec.z;
        return result;
    }
    
    public Vector3f addLocal(Vector3f vec) {
        if (null == vec) {
            logger.warning("Provided vector is null, null returned.");
            return null;
        }
        x += vec.x;
        y += vec.y;
        z += vec.z;
        return this;
    }

    public Vector3f add(float addX, float addY, float addZ) {
        return new Vector3f(x + addX, y + addY, z + addZ);
    }

    public Vector3f addLocal(float addX, float addY, float addZ) {
        x += addX;
        y += addY;
        z += addZ;
        return this;
    }

    public Vector3f scaleAdd(float scalar, Vector3f add) {
        x = x * scalar + add.x;
        y = y * scalar + add.y;
        z = z * scalar + add.z;
        return this;
    }

    public Vector3f scaleAdd(float scalar, Vector3f mult, Vector3f add) {
        this.x = mult.x * scalar + add.x;
        this.y = mult.y * scalar + add.y;
        this.z = mult.z * scalar + add.z;
        return this;
    }

    public float dot(Vector3f vec) {
        if (null == vec) {
            return 0;
        }
        return x * vec.x + y * vec.y + z * vec.z;
    }
    
    public Vector3f cross(Vector3f v) {
        return cross(v, null);
    }

    public Vector3f cross(Vector3f v, Vector3f result) {
        return cross(v.x, v.y, v.z, result);
    }

    public Vector3f cross(float otherX, float otherY, float otherZ, Vector3f result) {
        if (result == null) result = new Vector3f();
        float resX = ((y * otherZ) - (z * otherY)); 
        float resY = ((z * otherX) - (x * otherZ));
        float resZ = ((x * otherY) - (y * otherX));
        result.set(resX, resY, resZ);
        return result;
    }
    
    public Vector3f crossLocal(Vector3f v) {
        return crossLocal(v.x, v.y, v.z);
    }

    public Vector3f crossLocal(float otherX, float otherY, float otherZ) {
        float tempx = ( y * otherZ ) - ( z * otherY );
        float tempy = ( z * otherX ) - ( x * otherZ );
        z = (x * otherY) - (y * otherX);
        x = tempx;
        y = tempy;
        return this;
    }

    public Vector3f project(Vector3f other){
        float n = this.dot(other); // A . B
        float d = other.lengthSquared(); // |B|^2
        return new Vector3f(other).normalizeLocal().multLocal(n/d);
    }

    public boolean isUnitVector(){
        float len = length();
        return 0.99f < len && len < 1.01f;
    }

    public float length() {
        return (float) Math.sqrt(lengthSquared());
    }

    public float lengthSquared() {
        return x * x + y * y + z * z;
    }

    public float distanceSquared(Vector3f v) {
        double dx = x - v.x;
        double dy = y - v.y;
        double dz = z - v.z;
        return (float) (dx * dx + dy * dy + dz * dz);
    }

    public float distance(Vector3f v) {
        return (float) Math.sqrt(distanceSquared(v));
    }
    
    public Vector3f mult(float scalar) {
        return new Vector3f(x * scalar, y * scalar, z * scalar);
    }

    public Vector3f mult(float scalar, Vector3f product) {
        if (null == product) {
            product = new Vector3f();
        }

        product.x = x * scalar;
        product.y = y * scalar;
        product.z = z * scalar;
        return product;
    }

    public Vector3f multLocal(float scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        return this;
    }

    public Vector3f multLocal(Vector3f vec) {
        if (null == vec) {
            return null;
        }
        x *= vec.x;
        y *= vec.y;
        z *= vec.z;
        return this;
    }
    
    public Vector3f multLocal(float x, float y, float z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }

    public Vector3f mult(Vector3f vec) {
        if (null == vec) {
            return null;
        }
        return mult(vec, null);
    }

    public Vector3f mult(Vector3f vec, Vector3f store) {
        if (null == vec) {
            return null;
        }
        if (store == null) store = new Vector3f();
        return store.set(x * vec.x, y * vec.y, z * vec.z);
    }

    public Vector3f divide(float scalar) {
        scalar = 1f/scalar;
        return new Vector3f(x * scalar, y * scalar, z * scalar);
    }

    public Vector3f divideLocal(float scalar) {
        scalar = 1f/scalar;
        x *= scalar;
        y *= scalar;
        z *= scalar;
        return this;
    }

    public Vector3f divide(Vector3f scalar) {
        return new Vector3f(x / scalar.x, y / scalar.y, z / scalar.z);
    }

    public Vector3f divideLocal(Vector3f scalar) {
        x /= scalar.x;
        y /= scalar.y;
        z /= scalar.z;
        return this;
    }

    public Vector3f negate() {
        return new Vector3f(-x, -y, -z);
    }

    public Vector3f negateLocal() {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }

    public Vector3f subtract(Vector3f vec) {
        return new Vector3f(x - vec.x, y - vec.y, z - vec.z);
    }

    public Vector3f subtractLocal(Vector3f vec) {
        if (null == vec) {
            logger.warning("Provided vector is null, null returned.");
            return null;
        }
        x -= vec.x;
        y -= vec.y;
        z -= vec.z;
        return this;
    }

    public Vector3f subtract(Vector3f vec, Vector3f result) {
        if(result == null) {
            result = new Vector3f();
        }
        result.x = x - vec.x;
        result.y = y - vec.y;
        result.z = z - vec.z;
        return result;
    }

    public Vector3f subtract(float subtractX, float subtractY, float subtractZ) {
        return new Vector3f(x - subtractX, y - subtractY, z - subtractZ);
    }

    public Vector3f subtractLocal(float subtractX, float subtractY, float subtractZ) {
        x -= subtractX;
        y -= subtractY;
        z -= subtractZ;
        return this;
    }

    public Vector3f normalize() {
        float length = x * x + y * y + z * z;
        if (length != 1f && length != 0f){
            length = (float) (1.0f / Math.sqrt(length));
            return new Vector3f(x * length, y * length, z * length);
        }
        return clone();
    }

    public Vector3f normalizeLocal() {
        // NOTE: this implementation is more optimized
        // than the old jme normalize as this method
        // is commonly used.
        float length = x * x + y * y + z * z;
        if (length != 1f && length != 0f){
            length = (float) (1.0f / Math.sqrt(length));
            x *= length;
            y *= length;
            z *= length;
        }
        return this;
    }

    public void maxLocal(Vector3f other){
        x = other.x > x ? other.x : x;
        y = other.y > y ? other.y : y;
        z = other.z > z ? other.z : z;
    }

    public void minLocal(Vector3f other){
        x = other.x < x ? other.x : x;
        y = other.y < y ? other.y : y;
        z = other.z < z ? other.z : z;
    }

    public Vector3f zero() {
        x = y = z = 0;
        return this;
    }

    public float angleBetween(Vector3f otherVector) {
        float dotProduct = dot(otherVector);
        float angle = (float) Math.acos(dotProduct);
        return angle;
    }
    
    public Vector3f interpolate(Vector3f finalVec, float changeAmnt) {
        this.x=(1-changeAmnt)*this.x + changeAmnt*finalVec.x;
        this.y=(1-changeAmnt)*this.y + changeAmnt*finalVec.y;
        this.z=(1-changeAmnt)*this.z + changeAmnt*finalVec.z;
        return this;
    }

    public Vector3f interpolate(Vector3f beginVec,Vector3f finalVec, float changeAmnt) {
        this.x=(1-changeAmnt)*beginVec.x + changeAmnt*finalVec.x;
        this.y=(1-changeAmnt)*beginVec.y + changeAmnt*finalVec.y;
        this.z=(1-changeAmnt)*beginVec.z + changeAmnt*finalVec.z;
        return this;
    }
    
    public static boolean isValidVector(Vector3f vector) {
      if (vector == null) return false;
      if (Float.isNaN(vector.x) ||
          Float.isNaN(vector.y) ||
          Float.isNaN(vector.z)) return false;
      if (Float.isInfinite(vector.x) ||
          Float.isInfinite(vector.y) ||
          Float.isInfinite(vector.z)) return false;
      return true;
    }

    @Override
    public Vector3f clone() {
        try {
            return (Vector3f) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // can not happen
        }
    }

    public float[] toArray(float[] floats) {
        if (floats == null) {
            floats = new float[3];
        }
        floats[0] = x;
        floats[1] = y;
        floats[2] = z;
        return floats;
    }
    
    public boolean equals(Object o) {
        if (!(o instanceof Vector3f)) { return false; }

        if (this == o) { return true; }

        Vector3f comp = (Vector3f) o;
        if (Float.compare(x,comp.x) != 0) return false;
        if (Float.compare(y,comp.y) != 0) return false;
        if (Float.compare(z,comp.z) != 0) return false;
        return true;
    }
    
    public int hashCode() {
        int hash = 37;
        hash += 37 * hash + Float.floatToIntBits(x);
        hash += 37 * hash + Float.floatToIntBits(y);
        hash += 37 * hash + Float.floatToIntBits(z);
        return hash;
    }

    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }

    public float getX() {
        return x;
    }

    public Vector3f setX(float x) {
        this.x = x;
        return this;
    }

    public float getY() {
        return y;
    }

    public Vector3f setY(float y) {
        this.y = y;
        return this;
    }

    public float getZ() {
        return z;
    }

    public Vector3f setZ(float z) {
        this.z = z;
        return this;
    }

    public float get(int index) {
        switch (index) {
            case 0:
                return x;
            case 1:
                return y;
            case 2:
                return z;
        }
        throw new IllegalArgumentException("index must be either 0, 1 or 2");
    }
 
    public void set(int index, float value) {
        switch (index) {
            case 0:
                x = value;
                return;
            case 1:
                y = value;
                return;
            case 2:
                z = value;
                return;
        }
        throw new IllegalArgumentException("index must be either 0, 1 or 2");
    }

}