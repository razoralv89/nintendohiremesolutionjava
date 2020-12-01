package com.razor89.hiremechallenge;

import java.nio.charset.StandardCharsets;


public class MainWithLogs {
    static int bitwiseDivision(int dividend, int divisor) {
        int n1 = Integer.SIZE - Integer.numberOfLeadingZeros(dividend);
        int n2 = Integer.SIZE - Integer.numberOfLeadingZeros(divisor);
        int n = n1 >= n2 ? n1 : n2;
        int dividend_Hi = dividend >> n;
        int dividend_Lo = dividend & ((1 << n) - 1);

        for (int i = 0; i < n; i++) {
            dividend <<= 1;
            dividend_Hi = dividend >> n;
            if (dividend_Hi >= divisor) {
                dividend_Hi -= divisor;
                dividend++;
            }
            dividend_Lo = dividend & ((1 << n) - 1);
            dividend = dividend_Hi << n | dividend_Lo;
        }
        return dividend;
    }

    static int bitwiseMultiply(int n1, int n2) {
        /* This value will hold n1 * 2^i for varying values of i.  It will
         * start off holding n1 * 2^0 = n1, and after each iteration will
         * be updated to hold the next term in the sequence.
         */
        int a = n1;

        /* This value will be used to read the individual bits out of n2.
         * We'll use the shifting trick to read the bits and will maintain
         * the invariant that after i iterations, b is equal to n2 >> i.
         */
        int b = n2;

        /* This value will hold the sum of the terms so far. */
        int result = 0;

        /* Continuously loop over more and more bits of n2 until we've
         * consumed the last of them.  Since after i iterations of the
         * loop b = n2 >> i, this only reaches zero once we've used up
         * all the bits of the original value of n2.
         */
        while (b != 0) {
            /* Using the bitwise AND trick, determine whether the ith
             * bit of b is a zero or one.  If it's a zero, then the
             * current term in our sum is zero and we don't do anything.
             * Otherwise, then we should add n1 * 2^i.
             */
            if ((b & 1) != 0) {
                /* Recall that a = n1 * 2^i at this point, so we're adding
                 * in the next term in the sum.
                 */
                result = result + a;
            }

            /* To maintain that a = n1 * 2^i after i iterations, scale it
             * by a factor of two by left shifting one position.
             */
            a <<= 1;

            /* To maintain that b = n2 >> i after i iterations, shift it
             * one spot over.
             */
            b >>>= 1;
        }
        return result;
//        System.out.println(result);
    }

    // function to reduce matrix to reduced
    // row echelon form.
    static int performOperation(int a[][], int n) {
        int i, j, k = 0, c, flag = 0, m = 0;
        float pro = 0;

        // Performing elementary operations
        for (i = 0; i < n; i++) {
            if (a[i][i] == 0) {
                c = 1;
                while ((i + c) < n && a[i + c][i] == 0)
                    c++;
                if ((i + c) == n) {
                    flag = 1;
                    break;
                }
                for (j = i, k = 0; k <= n; k++) {
                    int temp = a[j][k];
                    a[j][k] = a[j + c][k];
                    a[j + c][k] = temp;
                }
            }

            for (j = 0; j < n; j++) {
                // Excluding all i == j
                if (i != j) {

                    // Converting Matrix to reduced row
                    // echelon form(diagonal matrix)
                    int p = bitwiseDivision(a[j][i], a[i][i]);
//                    float p = (a[j][i] / a[i][i]);

                    for (k = 0; k <= n; k++) {
//                        a[j][k] = a[j][k] - (a[i][k]) * p;
                        a[j][k] = a[j][k] ^ bitwiseMultiply((a[i][k]), p);
                    }
                }
            }
        }
        return flag;
    }

    // Function to print the desired result
    // if unique solutions exists, otherwise
    // prints no solution or infinite solutions
    // depending upon the input given.
    static void printResult(int a[][], int n, int flag) {
        System.out.print("Result is : ");

        if (flag == 2)
            System.out.println("Infinite Solutions Exists");
        else if (flag == 3)
            System.out.println("No Solution Exists");


            // Printing the solution by dividing constants by
            // their respective diagonal elements
        else {
            for (int i = 0; i < n; i++)
                System.out.print((a[i][n] / a[i][i]) + " ");
        }
    }

    static byte[] confusion = new byte[]{
            (byte) 0xac, (byte) 0xd1, (byte) 0x25, (byte) 0x94, (byte) 0x1f, (byte) 0xb3, (byte) 0x33, (byte) 0x28, (byte) 0x7c, (byte) 0x2b, (byte) 0x17, (byte) 0xbc, (byte) 0xf6, (byte) 0xb0, (byte) 0x55, (byte) 0x5d,
            (byte) 0x8f, (byte) 0xd2, (byte) 0x48, (byte) 0xd4, (byte) 0xd3, (byte) 0x78, (byte) 0x62, (byte) 0x1a, (byte) 0x02, (byte) 0xf2, (byte) 0x01, (byte) 0xc9, (byte) 0xaa, (byte) 0xf0, (byte) 0x83, (byte) 0x71,
            (byte) 0x72, (byte) 0x4b, (byte) 0x6a, (byte) 0xe8, (byte) 0xe9, (byte) 0x42, (byte) 0xc0, (byte) 0x53, (byte) 0x63, (byte) 0x66, (byte) 0x13, (byte) 0x4a, (byte) 0xc1, (byte) 0x85, (byte) 0xcf, (byte) 0x0c,
            (byte) 0x24, (byte) 0x76, (byte) 0xa5, (byte) 0x6e, (byte) 0xd7, (byte) 0xa1, (byte) 0xec, (byte) 0xc6, (byte) 0x04, (byte) 0xc2, (byte) 0xa2, (byte) 0x5c, (byte) 0x81, (byte) 0x92, (byte) 0x6c, (byte) 0xda,
            (byte) 0xc6, (byte) 0x86, (byte) 0xba, (byte) 0x4d, (byte) 0x39, (byte) 0xa0, (byte) 0x0e, (byte) 0x8c, (byte) 0x8a, (byte) 0xd0, (byte) 0xfe, (byte) 0x59, (byte) 0x96, (byte) 0x49, (byte) 0xe6, (byte) 0xea,
            (byte) 0x69, (byte) 0x30, (byte) 0x52, (byte) 0x1c, (byte) 0xe0, (byte) 0xb2, (byte) 0x05, (byte) 0x9b, (byte) 0x10, (byte) 0x03, (byte) 0xa8, (byte) 0x64, (byte) 0x51, (byte) 0x97, (byte) 0x02, (byte) 0x09,
            (byte) 0x8e, (byte) 0xad, (byte) 0xf7, (byte) 0x36, (byte) 0x47, (byte) 0xab, (byte) 0xce, (byte) 0x7f, (byte) 0x56, (byte) 0xca, (byte) 0x00, (byte) 0xe3, (byte) 0xed, (byte) 0xf1, (byte) 0x38, (byte) 0xd8,
            (byte) 0x26, (byte) 0x1c, (byte) 0xdc, (byte) 0x35, (byte) 0x91, (byte) 0x43, (byte) 0x2c, (byte) 0x74, (byte) 0xb4, (byte) 0x61, (byte) 0x9d, (byte) 0x5e, (byte) 0xe9, (byte) 0x4c, (byte) 0xbf, (byte) 0x77,
            (byte) 0x16, (byte) 0x1e, (byte) 0x21, (byte) 0x1d, (byte) 0x2d, (byte) 0xa9, (byte) 0x95, (byte) 0xb8, (byte) 0xc3, (byte) 0x8d, (byte) 0xf8, (byte) 0xdb, (byte) 0x34, (byte) 0xe1, (byte) 0x84, (byte) 0xd6,
            (byte) 0x0b, (byte) 0x23, (byte) 0x4e, (byte) 0xff, (byte) 0x3c, (byte) 0x54, (byte) 0xa7, (byte) 0x78, (byte) 0xa4, (byte) 0x89, (byte) 0x33, (byte) 0x6d, (byte) 0xfb, (byte) 0x79, (byte) 0x27, (byte) 0xc4,
            (byte) 0xf9, (byte) 0x40, (byte) 0x41, (byte) 0xdf, (byte) 0xc5, (byte) 0x82, (byte) 0x93, (byte) 0xdd, (byte) 0xa6, (byte) 0xef, (byte) 0xcd, (byte) 0x8d, (byte) 0xa3, (byte) 0xae, (byte) 0x7a, (byte) 0xb6,
            (byte) 0x2f, (byte) 0xfd, (byte) 0xbd, (byte) 0xe5, (byte) 0x98, (byte) 0x66, (byte) 0xf3, (byte) 0x4f, (byte) 0x57, (byte) 0x88, (byte) 0x90, (byte) 0x9c, (byte) 0x0a, (byte) 0x50, (byte) 0xe7, (byte) 0x15,
            (byte) 0x7b, (byte) 0x58, (byte) 0xbc, (byte) 0x07, (byte) 0x68, (byte) 0x3a, (byte) 0x5f, (byte) 0xee, (byte) 0x32, (byte) 0x9f, (byte) 0xeb, (byte) 0xcc, (byte) 0x18, (byte) 0x8b, (byte) 0xe2, (byte) 0x57,
            (byte) 0xb7, (byte) 0x49, (byte) 0x37, (byte) 0xde, (byte) 0xf5, (byte) 0x99, (byte) 0x67, (byte) 0x5b, (byte) 0x3b, (byte) 0xbb, (byte) 0x3d, (byte) 0xb5, (byte) 0x2d, (byte) 0x19, (byte) 0x2e, (byte) 0x0d,
            (byte) 0x93, (byte) 0xfc, (byte) 0x7e, (byte) 0x06, (byte) 0x08, (byte) 0xbe, (byte) 0x3f, (byte) 0xd9, (byte) 0x2a, (byte) 0x70, (byte) 0x9a, (byte) 0xc8, (byte) 0x7d, (byte) 0xd8, (byte) 0x46, (byte) 0x65,
            (byte) 0x22, (byte) 0xf4, (byte) 0xb9, (byte) 0xa2, (byte) 0x6f, (byte) 0x12, (byte) 0x1b, (byte) 0x14, (byte) 0x45, (byte) 0xc7, (byte) 0x87, (byte) 0x31, (byte) 0x60, (byte) 0x29, (byte) 0xf7, (byte) 0x73,
            (byte) 0x2c, (byte) 0x97, (byte) 0x72, (byte) 0xcd, (byte) 0x89, (byte) 0xa6, (byte) 0x88, (byte) 0x4c, (byte) 0xe8, (byte) 0x83, (byte) 0xeb, (byte) 0x59, (byte) 0xca, (byte) 0x50, (byte) 0x3f, (byte) 0x27,
            (byte) 0x4e, (byte) 0xae, (byte) 0x43, (byte) 0xd5, (byte) 0x6e, (byte) 0xd0, (byte) 0x99, (byte) 0x7b, (byte) 0x7c, (byte) 0x40, (byte) 0x0c, (byte) 0x52, (byte) 0x86, (byte) 0xc1, (byte) 0x46, (byte) 0x12,
            (byte) 0x5a, (byte) 0x28, (byte) 0xa8, (byte) 0xbb, (byte) 0xcb, (byte) 0xf0, (byte) 0x11, (byte) 0x95, (byte) 0x26, (byte) 0x0d, (byte) 0x34, (byte) 0x66, (byte) 0x22, (byte) 0x18, (byte) 0x6f, (byte) 0x51,
            (byte) 0x9b, (byte) 0x3b, (byte) 0xda, (byte) 0xec, (byte) 0x5e, (byte) 0x00, (byte) 0x2a, (byte) 0xf5, (byte) 0x8f, (byte) 0x61, (byte) 0xba, (byte) 0x96, (byte) 0xb3, (byte) 0xd1, (byte) 0x30, (byte) 0xdc,
            (byte) 0x33, (byte) 0x75, (byte) 0xe9, (byte) 0x6d, (byte) 0xc8, (byte) 0xa1, (byte) 0x3a, (byte) 0x3e, (byte) 0x5f, (byte) 0x9d, (byte) 0xfd, (byte) 0xa9, (byte) 0x31, (byte) 0x9f, (byte) 0xaa, (byte) 0x85,
            (byte) 0x2f, (byte) 0x92, (byte) 0xaf, (byte) 0x67, (byte) 0x78, (byte) 0xa5, (byte) 0xab, (byte) 0x03, (byte) 0x21, (byte) 0x4f, (byte) 0xb9, (byte) 0xad, (byte) 0xfe, (byte) 0xf3, (byte) 0x42, (byte) 0xfc,
            (byte) 0x17, (byte) 0xd7, (byte) 0xee, (byte) 0xa3, (byte) 0xd8, (byte) 0x80, (byte) 0x14, (byte) 0x2e, (byte) 0xa0, (byte) 0x47, (byte) 0x55, (byte) 0xc4, (byte) 0xff, (byte) 0xe5, (byte) 0x13, (byte) 0x3f,
            (byte) 0x81, (byte) 0xb6, (byte) 0x7a, (byte) 0x94, (byte) 0xd0, (byte) 0xb5, (byte) 0x54, (byte) 0xbf, (byte) 0x91, (byte) 0xa7, (byte) 0x37, (byte) 0xf1, (byte) 0x6b, (byte) 0xc9, (byte) 0x1b, (byte) 0xb1,
            (byte) 0x3c, (byte) 0xb6, (byte) 0xd9, (byte) 0x32, (byte) 0x24, (byte) 0x8d, (byte) 0xf2, (byte) 0x82, (byte) 0xb4, (byte) 0xf9, (byte) 0xdb, (byte) 0x7d, (byte) 0x44, (byte) 0xfb, (byte) 0x1e, (byte) 0xd4,
            (byte) 0xea, (byte) 0x5d, (byte) 0x35, (byte) 0x69, (byte) 0x23, (byte) 0x71, (byte) 0x57, (byte) 0x01, (byte) 0x06, (byte) 0xe4, (byte) 0x55, (byte) 0x9a, (byte) 0xa4, (byte) 0x58, (byte) 0x56, (byte) 0xc7,
            (byte) 0x4a, (byte) 0x8c, (byte) 0x8a, (byte) 0xd6, (byte) 0x6a, (byte) 0x49, (byte) 0x70, (byte) 0xc5, (byte) 0x8e, (byte) 0x0a, (byte) 0x62, (byte) 0xdc, (byte) 0x29, (byte) 0x4b, (byte) 0x42, (byte) 0x41,
            (byte) 0xcb, (byte) 0x2b, (byte) 0xb7, (byte) 0xce, (byte) 0x08, (byte) 0xa1, (byte) 0x76, (byte) 0x1d, (byte) 0x1a, (byte) 0xb8, (byte) 0xe3, (byte) 0xcc, (byte) 0x7e, (byte) 0x48, (byte) 0x20, (byte) 0xe6,
            (byte) 0xf8, (byte) 0x45, (byte) 0x93, (byte) 0xde, (byte) 0xc3, (byte) 0x63, (byte) 0x0f, (byte) 0xb0, (byte) 0xac, (byte) 0x5c, (byte) 0xba, (byte) 0xdf, (byte) 0x07, (byte) 0x77, (byte) 0xe7, (byte) 0x4e,
            (byte) 0x1f, (byte) 0x28, (byte) 0x10, (byte) 0x6c, (byte) 0x59, (byte) 0xd3, (byte) 0xdd, (byte) 0x2d, (byte) 0x65, (byte) 0x39, (byte) 0xb2, (byte) 0x74, (byte) 0x84, (byte) 0x3d, (byte) 0xf4, (byte) 0xbd,
            (byte) 0xc7, (byte) 0x79, (byte) 0x60, (byte) 0x0b, (byte) 0x4d, (byte) 0x33, (byte) 0x36, (byte) 0x25, (byte) 0xbc, (byte) 0xe0, (byte) 0x09, (byte) 0xcf, (byte) 0x5b, (byte) 0xe2, (byte) 0x38, (byte) 0x9e,
            (byte) 0xc0, (byte) 0xef, (byte) 0xd2, (byte) 0x16, (byte) 0x05, (byte) 0xbe, (byte) 0x53, (byte) 0xf7, (byte) 0xc2, (byte) 0xc6, (byte) 0xa2, (byte) 0x24, (byte) 0x98, (byte) 0x1c, (byte) 0xad, (byte) 0x04};

    static int[] diffusion = new int[]{
            0xf26cb481, 0x16a5dc92, 0x3c5ba924, 0x79b65248, 0x2fc64b18, 0x615acd29, 0xc3b59a42, 0x976b2584,
            0x6cf281b4, 0xa51692dc, 0x5b3c24a9, 0xb6794852, 0xc62f184b, 0x5a6129cd, 0xb5c3429a, 0x6b978425,
            0xb481f26c, 0xdc9216a5, 0xa9243c5b, 0x524879b6, 0x4b182fc6, 0xcd29615a, 0x9a42c3b5, 0x2584976b,
            0x81b46cf2, 0x92dca516, 0x24a95b3c, 0x4852b679, 0x184bc62f, 0x29cd5a61, 0x429ab5c3, 0x84256b97};

//    static byte[] input = new byte[]{
////            (byte) 0x66, (byte) 0xd5, (byte) 0x4e, (byte) 0x28, (byte) 0x5f, (byte) 0xff, (byte) 0x6b, (byte) 0x53, (byte) 0xac, (byte) 0x3b, (byte) 0x34, (byte) 0x14, (byte) 0xb5, (byte) 0x3c, (byte) 0xb2, (byte) 0xc6,
////            (byte) 0xa4, (byte) 0x85, (byte) 0x1e, (byte) 0x0d, (byte) 0x86, (byte) 0xc7, (byte) 0x4f, (byte) 0xba, (byte) 0x75, (byte) 0x5e, (byte) 0xcb, (byte) 0xc3, (byte) 0x6e, (byte) 0x48, (byte) 0x79, (byte) 0x8f
//    };




    static byte[] input = Utils.hexStringToByteArray("8BE5F006896938BCC60750880B8C3F9562566298A4A531FAE8CEC7837FBBEF60");

    static void forward(byte[] indexes, int[] output, byte[] conf, int[] diff) {
        for (int i = 0; i < 256; i++) {
            System.out.println("Indexes: " + Utils.bytesToHex(indexes));
            for (byte j = 0; j < 32; j++) {
                output[j] = conf[positive(indexes[j])];
                indexes[j] = 0;
            }
            System.out.println("Conf: " + Utils.bytesToHex(output));
            for (byte j = 0; j < 32; j++) {
                //el nuevo indexes[j] será un scramble iterativo de conf[indices[j]] dependiendo de diff[j]
                for (byte k = 0; k < 32; k++) {
                    indexes[j] = (byte) (indexes[j] ^ ((byte) output[k]) * ((diff[j] >> k) & 1));
                }
            }
        }
        System.out.println("Indexes: " + Utils.bytesToHex(indexes));
        for (byte i = 0; i < 16; i++) {
            output[i] = conf[positive(indexes[i * 2])] ^ conf[positive(indexes[i * 2 + 1]) + 256];
        }
    }

    static int positive(byte val) {
        int resp = val;
        if (val < 0) {
            resp = 256 + val;
        }
        return resp;
    }

    static void equations() {
        String[] varialbes = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F",};
        for (int i = 0; i < diffusion.length; i++) {
            String bin = Integer.toBinaryString(diffusion[i]);
            String padded = "00000000000000000000000000000000".substring(bin.length()) + bin;
            //System.out.println(padded);
            StringBuilder line = new StringBuilder();
            StringBuilder line2 = new StringBuilder();
            for (int j = padded.length() - 1; j >= 0; j--) {
                if (padded.charAt(j) == '1') {
                    line.append(" + ").append(varialbes[j]);
                }
                line2.append(", ").append(padded.charAt(j));
            }
//            System.out.println(line.substring(3, line.length()));
            System.out.println(line2.substring(2, line2.length()));
        }
    }

    public static void main(String[] args) {

        byte[] target = "Hire me!!!!!!!!".getBytes();
        int[] output = new int[32];
        forward(input, output, confusion, diffusion);
        equations();
        System.out.println(Utils.bytesToHex(output));

        byte[] out = new byte[32];
        for (int i = 0; i < output.length; i++) {
            out[i] = (byte) output[i];
        }
        System.out.println(new String(out, StandardCharsets.UTF_8));
    }
}
