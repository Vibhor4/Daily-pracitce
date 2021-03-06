package interview.leetcode._1xx._19x;

/**
 * Created by zzt on 12/1/17.
 * <p>
 * <h3></h3>
 */
public class ReverseBits {

    public int reverseBits(int t) {
        // have to use unsigned shift
        t = (t >>> 16) | (t << 16);
        t = ((t & 0x00ff00ff) << 8) | ((t & 0xff00ff00) >>> 8);
        t = ((t & 0x0f0f0f0f) << 4) | ((t & 0xf0f0f0f0) >>> 4);
        t = ((t & 0x33333333) << 2) | ((t & 0xcccccccc) >>> 2);
        t = ((t & 0x55555555) << 1) | ((t & 0xaaaaaaaa) >>> 1);
        return t;
    }

    private static void test(int i) {
        System.out.println(Integer.toBinaryString(i));
        ReverseBits r = new ReverseBits();
        System.out.println(Integer.toBinaryString(r.reverseBits(i)));
    }

    public static void main(String[] args) {
        test(65536);
        test(2);
        test(1);
    }
}
