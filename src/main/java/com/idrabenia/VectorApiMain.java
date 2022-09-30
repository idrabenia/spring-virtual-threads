package com.idrabenia;

import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorSpecies;

import java.util.Arrays;

public class VectorApiMain {

    static final VectorSpecies<Float> SPECIES = FloatVector.SPECIES_PREFERRED;

    static void vectorComputation(float[] a, float[] b, float[] c) {
        int upperBound = SPECIES.loopBound(a.length);
        int i = 0;

        for (; i < upperBound; i += SPECIES.length()) {
            // FloatVector va, vb, vc;
            var va = FloatVector.fromArray(SPECIES, a, i);
            var vb = FloatVector.fromArray(SPECIES, b, i);
            var vc = va.mul(va)
                    .add(vb.mul(vb))
                    .neg();
            vc.intoArray(c, i);
        }

        for (; i < a.length; i++) {
            c[i] = (a[i] * a[i] + b[i] * b[i]) * -1.0f;
        }
    }

    public static void main(String... args) {
        var src = new float[] {0f, 1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f, 10f, 11f};
        var dst = new float[12];

        vectorComputation(src, src, dst);

        System.out.println("Result: " + Arrays.toString(dst));
    }

}
