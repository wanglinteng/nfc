package wang.linteng.tool;

import java.util.ArrayList;
import java.util.List;

public class Nfc {

    // 把原始字符串分割成指定长度的16进制列表
    public static List<Integer> getIntList(String inputString, int length) {
        int size = inputString.length() / length;
        if (inputString.length() % length != 0) {
            size += 1;
        }
        return getIntList(inputString, length, size);
    }

    // 把原始字符串分割成指定长度指定个数的16进制列表
    public static List<Integer> getIntList(String inputString, int length,
                                           int size) {
        List<Integer> list = new ArrayList<>();
        for (int index = 0; index < size; index++) {
            String childStr = substring(inputString, index * length,
                    (index + 1) * length);
            int data = Integer.parseInt(childStr, 16);
            list.add(data);
        }
        return list;
    }

    // 分割字符串，如果开始位置大于字符串长度，返回空
    public static String substring(String str, int f, int t) {
        if (f > str.length())
            return null;
        if (t > str.length()) {
            return str.substring(f, str.length());
        } else {
            return str.substring(f, t);
        }
    }

    //字符序列转换为16进制字符串
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        char[] buffer = new char[2];
        for (int i = 0; i < src.length; i++) {
            buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
            buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
            stringBuilder.append(buffer);
        }
        return stringBuilder.toString().toUpperCase();
    }

}
