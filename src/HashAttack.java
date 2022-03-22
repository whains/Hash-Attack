import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HashAttack {

    public static String SHA1(String in, int size) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        MessageDigest message = MessageDigest.getInstance("SHA-1");
        message.reset();
        message.update(in.getBytes("UTF-8"));
        byte[] sha1 = message.digest();

        StringBuilder result = new StringBuilder();

        int i = 0;
        while (result.length() < size) {
            result.append(Integer.toBinaryString(sha1[i] & 255 | 256).substring(1));
            i++;
        }

        return(result.substring(0,size));
    }

    public static String RandomString(int length) {
        String CHARS = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder str = new StringBuilder();
        Random rnd = new Random();

        while (str.length() < length) { // length of the random string.
            int i = (int) (rnd.nextFloat() * CHARS.length());
            str.append(CHARS.charAt(i));
        }

        return str.toString();
    }

    public static double CollisionAttackAverage(int size) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        double average = 0.0;
        int total = 0;
        int count;
        String randomStr;
        String sha1Str;
        List<String> byteList;

        for (int i = 0; i < 50; i++) {
            count = -1;
            byteList = new ArrayList<String>();

            while (true) {
                randomStr = RandomString(20);
                sha1Str = SHA1(randomStr, size);
                count++;
                if (byteList.contains(sha1Str)) {
                    total = total + count;
                    break;
                }
                else {
                    byteList.add(sha1Str);
                }
            }
        }
        average = total / 50.0;
        return (average);
    }

    public static double PreImageAverage(int size) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        double average = 0.0;
        int total = 0;
        int count;
        String randomStr = SHA1(RandomString(20), size);
        String randomStr2;
        String sha1Str;

        for (int i = 0; i < 50; i++) {
            count = -1;

            while (true) {
                randomStr2 = RandomString(20);
                sha1Str = SHA1(randomStr2, size);
                count++;
                if (randomStr.equals(sha1Str)) {
                    total = total + count;
                    break;
                }
            }
        }
        average = total / 50.0;
        return (average);
    }

    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        System.out.println("Collision Attack with size 8: " + CollisionAttackAverage(8));
        System.out.println("Pre Image Attack with size 8: " + PreImageAverage(8));
        System.out.println();

        System.out.println("Collision Attack with size 10: " + CollisionAttackAverage(10));
        System.out.println("Pre Image Attack with size 10: " + PreImageAverage(10));
        System.out.println();

        System.out.println("Collision Attack with size 13: " + CollisionAttackAverage(13));
        System.out.println("Pre Image Attack with size 13: " + PreImageAverage(13));
        System.out.println();

        System.out.println("Collision Attack with size 16: " + CollisionAttackAverage(16));
        System.out.println("Pre Image Attack with size 16: " + PreImageAverage(16));
        System.out.println();
    }
}
