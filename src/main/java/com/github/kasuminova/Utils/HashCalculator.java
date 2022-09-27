package com.github.kasuminova.Utils;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;

/**
 * 公用 Hash 计算类
 */
public class HashCalculator {
    /**
     * 获取文件 SHA1
     * BalloonUpdate 的默认方法
     * @param file 目标文件
     * @return String
     **/
    public static String getSHA1(File file) throws IOException, NoSuchAlgorithmException {
        FileChannel fc = FileChannel.open(Paths.get(file.toURI()), StandardOpenOption.READ);
        ByteBuffer byteBuffer = ByteBuffer.allocate(FileUtil.formatFileSizeInt(file.length()));
        int len;
        MessageDigest md = MessageDigest.getInstance("SHA1");
        while ((len = fc.read(byteBuffer)) > 0) {
            md.update(byteBuffer.array(), 0, len);
            byteBuffer.clear();
        }
        fc.close();
        //转换并返回包含 16 个元素字节数组,返回数值范围为 -128 到 127
        byte[] sha1Bytes = md.digest();
        //1 代表绝对值
        BigInteger bigInt = new BigInteger(1, sha1Bytes);
        //转换为 16 进制
        return bigInt.toString(16);
    }

    public static String getMD5(File file) throws IOException, NoSuchAlgorithmException {
        FileChannel fc = FileChannel.open(Paths.get(file.toURI()), StandardOpenOption.READ);
        ByteBuffer byteBuffer = ByteBuffer.allocate(FileUtil.formatFileSizeInt(file.length()));
        int len;
        MessageDigest md = MessageDigest.getInstance("MD5");
        while ((len = fc.read(byteBuffer)) > 0) {
            md.update(byteBuffer.array(), 0, len);
            byteBuffer.clear();
        }
        fc.close();
        //转换并返回包含 16 个元素字节数组,返回数值范围为 -128 到 127
        byte[] md5Bytes = md.digest();
        //1 代表绝对值
        BigInteger bigInt = new BigInteger(1, md5Bytes);
        //转换为 16 进制
        return bigInt.toString(16);
    }

    public static String getCRC32(File file) throws IOException {
        FileChannel fc = FileChannel.open(Paths.get(file.toURI()), StandardOpenOption.READ);
        ByteBuffer byteBuffer = ByteBuffer.allocate(FileUtil.formatFileSizeInt(file.length()));
        int len;
        CRC32 crc32 = new CRC32();
        while ((len = fc.read(byteBuffer)) > 0) {
            crc32.update(byteBuffer.array(), 0, len);
            byteBuffer.clear();
        }
        fc.close();

        //转换为 16 进制
        return Integer.toHexString((int) crc32.getValue());
    }
}