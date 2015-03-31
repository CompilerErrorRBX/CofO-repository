package physics;

import util.Matrix2f;
import util.Polygon;
import util.Vector2f;

public class Collision {

	public static final Collision instance = new Collision();

	public void handleCollision(Manifold m, Body a, Body b) {
		
		Polygon A = (Polygon) a.shape;
		Polygon B = (Polygon) b.shape;
		m.contactCount = 0;

		// Check for a separating axis with A's face planes
		int[] faceA = { 0 };
		float penetrationA = findAxisLeastPenetration(faceA, A, B);
		if (penetrationA >= 0.0f) {
			return;
		}

		// Check for a separating axis with B's face planes
		int[] faceB = { 0 };
		float penetrationB = findAxisLeastPenetration(faceB, B, A);
		if (penetrationB >= 0.0f) {
			return;
		}

		int referenceIndex;
		boolean flip; // Always point from a to b

		Polygon RefPoly; // Reference
		Polygon IncPoly; // Incident

		// Determine which shape contains reference face
		if (Mathp.gt(penetrationA, penetrationB)) {
			RefPoly = A;
			IncPoly = B;
			referenceIndex = faceA[0];
			flip = false;
		} else {
			RefPoly = B;
			IncPoly = A;
			referenceIndex = faceB[0];
			flip = true;
		}

		// World space incident face
		Vector2f[] incidentFace = Vector2f.arrayOf(2);

		findIncidentFace(incidentFace, RefPoly, IncPoly, referenceIndex);

		// Setup reference face vertices
		Vector2f v1 = RefPoly.vertices[referenceIndex];
		referenceIndex = referenceIndex + 1 == RefPoly.vertexCount ? 0 : referenceIndex + 1;
		Vector2f v2 = RefPoly.vertices[referenceIndex];

		// Transform vertices to world space
		v1 = RefPoly.cFrame.mul(v1).add(RefPoly.body.position);
		v2 = RefPoly.cFrame.mul(v2).add(RefPoly.body.position);

		// Calculate reference face side normal in world space
		Vector2f sidePlaneNormal = v2.subtract(v1);
		sidePlaneNormal.normalize();

		// Orthogonalize
		Vector2f refFaceNormal = new Vector2f(sidePlaneNormal.y, -sidePlaneNormal.x);

		float refC = v1.dot(refFaceNormal);
		float negSide = -v1.dot(sidePlaneNormal);
		float posSide = v2.dot(sidePlaneNormal);

		// Clip incident face to reference face side planes
		if (clip(sidePlaneNormal.neg(), negSide, incidentFace) < 2) {
			return; // Due to floating point error, possible to not have required points
		}

		if (clip(sidePlaneNormal, posSide, incidentFace) < 2) {
			return; // Due to floating point error, possible to not have required points
		}

		// Flip
		m.normal.set(refFaceNormal);
		if (flip) {
			m.normal.negi();
		}

		// Keep points behind reference face
		int cp = 0; // clipped points behind reference face
		float separation = incidentFace[0].dot(refFaceNormal) - refC;
		if (separation <= 0.0f) {
			m.contacts[cp].set(incidentFace[0]);
			m.penetration = -separation;
			++cp;
		} else {
			m.penetration = 0;
		}

		separation = incidentFace[1].dot(refFaceNormal) - refC;

		if (separation <= 0.0f) {
			m.contacts[cp].set(incidentFace[1]);

			m.penetration += -separation;
			++cp;

			// Average penetration
			m.penetration /= cp;
		}

		m.contactCount = cp;
	}

	public float findAxisLeastPenetration(int[] faceIndex, Polygon A, Polygon B) {
		float bestDistance = -Float.MAX_VALUE;
		int bestIndex = 0;

		for (int i = 0; i < A.vertexCount; ++i) {
			// Retrieve a face normal from A
			Vector2f nw = A.cFrame.mul(A.normals[i]);
			
			// Transform face normal into B's model space
			Matrix2f buT = B.cFrame.transpose();
			Vector2f n = buT.mul(nw);
			
			// Retrieve support point from B along -n
			Vector2f s = B.getSupport(n.neg());
			
			// Retrieve vertex on face from A, transform into
			Vector2f v = buT.muli(A.cFrame.mul(A.vertices[i]).add(A.body.position).subtract(B.body.position));

			// Compute penetration distance (in B's model space)
			float d = Vector2f.dot(n, s.subtract(v));

			// Store greatest distance
			
			if (d > bestDistance) {
				bestDistance = d;
				bestIndex = i;
			}
		}

		faceIndex[0] = bestIndex;
		return bestDistance;
	}

	public void findIncidentFace(Vector2f[] v, Polygon RefPoly, Polygon IncPoly, int referenceIndex) {
		Vector2f referenceNormal = RefPoly.normals[referenceIndex];

		// Calculate normal in incident's frame of reference
		// incident's model space
		referenceNormal = RefPoly.cFrame.mul(referenceNormal); // To world space
		referenceNormal = IncPoly.cFrame.transpose().mul(referenceNormal); // To incident's model space

		// Find most anti-normal face on incident polygon
		int incidentFace = 0;
		float minDot = Float.MAX_VALUE;
		for (int i = 0; i < IncPoly.vertexCount; ++i) {
			float dot = IncPoly.normals[i].dot(referenceNormal);

			if (dot < minDot) {
				minDot = dot;
				incidentFace = i;
			}
		}

		// Assign face vertices for incidentFace
		v[0] = IncPoly.cFrame.mul(IncPoly.vertices[incidentFace]).add(IncPoly.body.position);
		incidentFace = incidentFace + 1 >= (int) IncPoly.vertexCount ? 0 : incidentFace + 1;
		v[1] = IncPoly.cFrame.mul(IncPoly.vertices[incidentFace]).add(IncPoly.body.position);
		System.out.println(incidentFace);
	}

	public int clip(Vector2f n, float c, Vector2f[] face) {
		int sp = 0;
		Vector2f[] out = {new Vector2f(face[0]), new Vector2f(face[1])};

		// Retrieve distances from each endpoint to the line
		float d1 = face[0].dot(n) - c;
		float d2 = face[1].dot(n) - c;

		// If negative (behind plane) clip
		if (d1 <= 0.0f) out[sp++].set(face[0]);
		if (d2 <= 0.0f) out[sp++].set(face[1]);

		// If the points are on different sides of the plane
		if (d1 * d2 < 0.0f) // less than to ignore -0.0f
		{
			// Push intersection point

			float alpha = d1 / (d1 - d2);

			out[sp++].set(face[1]).subtract(face[0]).multiply(alpha).add(face[0]);
		}

		// Assign our new converted values
		face[0] = out[0];
		face[1] = out[1];
		
		System.out.println(sp);
		return sp;
	}

}
