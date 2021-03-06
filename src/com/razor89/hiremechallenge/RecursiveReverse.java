package com.razor89.hiremechallenge;

import java.util.Arrays;

public class RecursiveReverse {

    static final byte[] confusion = Utils.confusion;
    //0xfa is not in the confusion array
    //the rest is excluded because they have only 1 repetition in the confusion array and its indexes are greater than 255.
    //check Utils.printBlacklist()
    static final int[] blackList = new int[]{0xfa, 0xd5, 0x5a, 0xcb, 0x11, 0x75, 0x3e, 0xaf, 0x80, 0x6b, 0xb1, 0x44, 0xe4, 0x20, 0x0f, 0x9e};
    static int[] outputPositions = new int[]{
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
    };
    //static String outputHex = "52657665727365206D65206661737400308E1B81702368F7591832BA2A49DF92";//Reverse me fast
    static String outputHex = "48697265206D65212121212121212100308E1B81702368F7591832BA2A49DF92";//Hire me!!!!!!!!
    static byte[] output = "Hire me!!!!!!!!0".getBytes();

    static long start = System.currentTimeMillis();

    static boolean blacklisted(byte[] conf) {
        for (int j : blackList) {
            if (Utils.indexOf(conf, (byte) j) != -1) {
                return true;
            }
        }
        return false;
    }

    static int[] reverseAll(int[][] possibilities) {
        while (true) {
            System.out.print("Reversing All steps for combination: [");
            for (int i = 0; i < 32; i++) {
                System.out.print(outputPositions[i] + ", ");
            }
            System.out.println("]");
            int[] indexes = Utils.getIndexesFromPositions(possibilities, outputPositions);
            //Solves the equation system to get the last s_box output
            byte[] conf = Utils.equationSystemSolver(indexes);
            //Filters the output from the blacklisted elements
            if (!blacklisted(conf)) {
                System.out.println("Indexes: " + Utils.bytesToHex(indexes));
                //Try to reverse 256 times starting with this combination
                int[] result = reverseConf(indexes, 0);
                if (result[0] != -1) {
                    return result;
                }
            }
            System.out.println("Computing next positions");
            //Move to next combination
            boolean error = next2(possibilities, outputPositions, 31);
            if (error) {
                System.out.println("********************************");
                System.out.println("***** NO SOLUTION FOUND ********");
                System.out.println("********************************");
                return new int[0];
            }
        }
    }

    static boolean next2(int[][] possibilities, int[] positions, int currentIndex) {
        positions[currentIndex]++;
        positions[currentIndex - 1]++;
        if (positions[currentIndex] > possibilities[0].length - 1 || possibilities[currentIndex][positions[currentIndex]] == -1) {
            positions[currentIndex] = 0;
            positions[currentIndex - 1] = 0;
            if (currentIndex <= 1) {
                return true;
            }
            currentIndex -= 2;
            return next2(possibilities, positions, currentIndex);
        }
        return false;
    }

    //Try to reverse the last known s_box result 256 times
    static int[] reverseConf(int[] indexes, int depth) {
        if (depth > 255) {
            return indexes;
        }
        byte[] conf = Utils.equationSystemSolver(indexes);
        int[][] possibilities = new int[32][4];
        for (int i = 0; i < 32; i++) {
            Arrays.fill(possibilities[i], -1);
        }
        //Get all possible combinations to get from s_box to the initial indexes
        for (int i = 0; i < conf.length; i++) {
            boolean found = false;
            //Must be up to 255 because it is the max result of xor operations over bytes
            int limit = 256;
            for (int j = 0; j < limit; j++) {
                if (confusion[j] == conf[i]) {
                    possibilities[i][Utils.indexOf(possibilities[i], -1)] = j;
                    found = true;
                }
            }
            if (!found) {
                return new int[]{-1};
            }
        }
        int[] positions = new int[32];
        int positionCounter = 1;
        //Uncomment for more interactive logging
        //System.out.println("Position 0 for depth " + depth);
        while (true) {
            int[] indexesTemp = Utils.getIndexesFromPositions(possibilities, positions);
            byte[] confTemp = Utils.equationSystemSolver(indexesTemp);
            if (!blacklisted(confTemp)) {
                int[] resp = reverseConf(indexesTemp, depth + 1);
                if (resp[0] != -1) {
                    System.out.println("Conf: " + Utils.bytesToHex(conf));
                    System.out.println("Index: " + Utils.bytesToHex(indexesTemp));
                    System.out.println("Position: " + positionCounter);
                    return resp;
                }
            }
            //Move to next combination
            boolean error = next(possibilities, positions, 31);
            if (error) {
                // No more combinations, this particular case is a dead end
                break;
            }
            //Uncomment for more interactive logging
            // System.out.println("Position " + positionCounter + " for depth " + depth);
            positionCounter++;
        }
        return new int[]{-1};
    }

    static boolean next(int[][] possibilities, int[] positions, int currentIndex) {
        positions[currentIndex]++;
        if (positions[currentIndex] > possibilities[0].length || possibilities[currentIndex][positions[currentIndex]] == -1) {
            positions[currentIndex] = 0;
            if (currentIndex <= 0) {
                return true;
            }
            currentIndex--;
            return next(possibilities, positions, currentIndex);
        }
        return false;
    }

    public static void main(String[] args) {

        int[][] possibilities = new int[32][256];
        for (int i = 0; i < 32; i++) {
            Arrays.fill(possibilities[i], -1);
        }
        //Get all possible combinations to reverse from the desired text (Hire me!!!!!!!0) or any other
        for (int x = 0; x < 16; x++) {
            for (int i = 0; i < 256; i++) {
                for (int j = 256; j < confusion.length; j++) {
                    if ((confusion[i] ^ confusion[j]) == output[x]) {
                        possibilities[x * 2][Utils.indexOf(possibilities[x * 2], -1)] = i;
                        possibilities[x * 2 + 1][Utils.indexOf(possibilities[x * 2 + 1], -1)] = (j - 256);
                    }
                }
            }
        }
        int[] indexes = reverseAll(possibilities);
        System.out.println("Found !!!!!!!!!!!!!!!!!!!: " + Utils.bytesToHex(indexes));
        System.out.println("Elapsed: " + (System.currentTimeMillis() - start));
        //Print the final position in case we want to find the next solution just add 1 to the last position
        System.out.print("Final position: [");
        for (int outputPosition : outputPositions) {
            System.out.print(outputPosition + ", ");
        }
        System.out.println("]");
    }
}
