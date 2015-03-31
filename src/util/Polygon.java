package util;

import physics.Shape;

public class Polygon extends Shape {

	public static final int MAX_VERTEX_COUNT = 256;

	public int vertexCount;
	public Vector2f[] vertices = Vector2f.arrayOf(MAX_VERTEX_COUNT);
	public Vector2f[] normals = Vector2f.arrayOf(MAX_VERTEX_COUNT);

	public Polygon() {
	}

	public Polygon(Vector2f... verts) {
		set(verts);
	}

	public Polygon(float hw, float hh) {
		setBox(hw, hh);
	}

	@Override
	public Shape clone() {
		Polygon p = new Polygon();
		p.cFrame.set(cFrame);
		for (int i = 0; i < vertexCount; i++) {
			p.vertices[i].set(vertices[i]);
			p.normals[i].set(normals[i]);
		}
		p.vertexCount = vertexCount;

		return p;
	}

	@Override
	public void initialize() {
		computeMass(1.0f);
	}

	@Override
	public void computeMass(float density) {
		// Calculate centroid and moment of inertia
		Vector2f c = new Vector2f(0.0f, 0.0f);
		float area = 0.0f;
		float I = 0.0f;
		final float k_inv3 = 1.0f / 3.0f;

		for (int i = 0; i < vertexCount; ++i) {
			// Triangle vertices, third vertex implied as (0, 0)
			Vector2f p1 = vertices[i];
			Vector2f p2 = vertices[(i + 1) % vertexCount];

			float D = p2.cross(p1);
			float triangleArea = 0.5f * D;

			area += triangleArea;

			// Use area to weight the centroid average, not just vertex position
			float weight = triangleArea * k_inv3;
			c.addsi(p1, weight);
			c.addsi(p2, weight);

			I += (0.25f * k_inv3 * D) * ((p1.x * p1.x + p2.x * p1.x + p2.x * p2.x) + (p1.y * p1.y + p2.y * p1.y + p2.y * p2.y));
		}

		c.multiply(1.0f / area);
		
		for (int i = 0; i < vertexCount; ++i) {
			vertices[i].subtract(c);
		}

		body.centerOfMass = c;
		body.mass = density * area;
		body.invMass = (body.mass != 0.0f) ? 1.0f / body.mass : 0.0f;
		body.inertia = I * density;
		body.invInertia = (body.inertia != 0.0f) ? 1.0f / body.inertia : 0.0f;
	}

	@Override
	public void setOrient(float radians) {
		cFrame.set(radians);
	}

	@Override
	public Type getType() {
		return Type.Poly;
	}

	public void setBox(float hw, float hh) {
		vertexCount = 4;
		vertices[0].set(-hw, -hh);
		vertices[1].set(hw, -hh);
		vertices[2].set(hw, hh);
		vertices[3].set(-hw, hh);
		normals[0].set(0.0f, -1.0f);
		normals[1].set(1.0f, 0.0f);
		normals[2].set(0.0f, 1.0f);
		normals[3].set(-1.0f, 0.0f);
	}

	public void set(Vector2f... verts) {
		// Find the right most point on the hull
		int rightMost = 0;
		float highestXCoord = verts[0].x;
		for (int i = 1; i < verts.length; ++i) {
			float x = verts[i].x;

			if (x > highestXCoord) {
				highestXCoord = x;
				rightMost = i;
			}
			// If matching x then take farthest negative y
			else if (x == highestXCoord) {
				if (verts[i].y < verts[rightMost].y) {
					rightMost = i;
				}
			}
		}

		int[] hull = new int[MAX_VERTEX_COUNT];
		int outCount = 0;
		int indexHull = rightMost;

		for (;;) {
			hull[outCount] = indexHull;

			// Search for next index that wraps around the hull
			// by computing cross products to find the most counter-clockwise
			// vertex in the set, given the previous hull index
			int nextHullIndex = 0;
			for (int i = 1; i < verts.length; ++i) {
				// Skip if same coordinate as we need three unique
				// points in the set to perform a cross product
				if (nextHullIndex == indexHull) {
					nextHullIndex = i;
					continue;
				}
				
				Vector2f e1 = verts[nextHullIndex].subtract(verts[hull[outCount]]);
				Vector2f e2 = verts[i].subtract(verts[hull[outCount]]);
				float c = e2.cross(e1);
				if (c < 0.0f) {
					nextHullIndex = i;
				}
				if (c == 0.0f && e2.magnitudeSq() > e1.magnitudeSq()) {
					nextHullIndex = i;
				}
			}

			++outCount;
			indexHull = nextHullIndex;

			// Conclude algorithm upon wrap-around
			if (nextHullIndex == rightMost) {
				vertexCount = outCount;
				break;
			}
		}

		// Copy vertices into shape's vertices
		for (int i = 0; i < vertexCount; ++i) {
			vertices[i].set(verts[hull[i]]);
		}

		// Compute face normals
		for (int i = 0; i < vertexCount; ++i) {
			Vector2f face = vertices[(i + 1) % vertexCount].subtract(vertices[i]);

			// Calculate normal with 2D cross product between vector and scalar
			normals[i].set(face.y, -face.x);
			normals[i].normalize();
		}
	}

	public Vector2f getSupport(Vector2f dir) {
		float bestProjection = -Float.MAX_VALUE;
		Vector2f bestVertex = null;

		for (int i = 0; i < vertexCount; ++i) {
			Vector2f v = vertices[i];
			float projection = dir.dot(v);

			if (projection > bestProjection) {
				bestVertex = v;
				bestProjection = projection;
			}
		}

		return bestVertex;
	}

}
