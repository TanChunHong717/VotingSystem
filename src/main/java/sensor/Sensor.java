package sensor;

import com.zkteco.biometric.FingerprintSensorErrorCode;
import com.zkteco.biometric.FingerprintSensorEx;

import java.io.IOException;

public class Sensor {
    private long device;
    private int height;
    private int width;
    private byte[] img;
    private byte[] template = new byte[2048];
    private int[] len;


    public Sensor() {
        FingerprintSensorEx.Init();
        if(FingerprintSensorEx.GetDeviceCount() < 0)
            throw new RuntimeException();
        device = FingerprintSensorEx.OpenDevice(0);
        width = getParam(1);
        height = getParam(2);
        img = new byte[width*height];
        len = new int[1];
        len[0] = 2048;
    }

    public String capture() throws IOException, InterruptedException {
        while(FingerprintSensorEx.AcquireFingerprint(device, img, template, len) != 0)
                ;

        writeBitmap(img, width, height, "fingerprint.bmp");

        return FingerprintSensorEx.BlobToBase64(template, len[0]);
    }

    public void close() {
        FingerprintSensorEx.Terminate();
    }

    private int getParam(int code){
        byte[] paramValue = new byte[4];
        int[] size = new int[1];
        size[0] = 4;
        FingerprintSensorEx.GetParameters(device,code, paramValue, size);

        int result = paramValue[0] & 0xFF;
        result |= ((paramValue[1] << 8) & 0xFF00);
        result |= ((paramValue[2] << 16) & 0xFF0000);
        result |= ((paramValue[3] << 24) & 0xFF000000);
        return result;
    }

    private static void writeBitmap(byte[] imageBuf, int nWidth, int nHeight,
                                   String path) throws IOException {
        java.io.FileOutputStream fos = new java.io.FileOutputStream(path);
        java.io.DataOutputStream dos = new java.io.DataOutputStream(fos);

        int w = (((nWidth+3)/4)*4);
        int bfType = 0x424d; // 浣嶅浘鏂囦欢绫诲瀷锛?0鈥?1瀛楄妭锛?
        int bfSize = 54 + 1024 + w * nHeight;// bmp鏂囦欢鐨勫ぇ灏忥紙2鈥?5瀛楄妭锛?
        int bfReserved1 = 0;// 浣嶅浘鏂囦欢淇濈暀瀛楋紝蹇呴』涓?0锛?6-7瀛楄妭锛?
        int bfReserved2 = 0;// 浣嶅浘鏂囦欢淇濈暀瀛楋紝蹇呴』涓?0锛?8-9瀛楄妭锛?
        int bfOffBits = 54 + 1024;// 鏂囦欢澶村紑濮嬪埌浣嶅浘瀹為檯鏁版嵁涔嬮棿鐨勫瓧鑺傜殑鍋忕Щ閲忥紙10-13瀛楄妭锛?

        dos.writeShort(bfType); // 杈撳叆浣嶅浘鏂囦欢绫诲瀷'BM'
        dos.write(changeByte(bfSize), 0, 4); // 杈撳叆浣嶅浘鏂囦欢澶у皬
        dos.write(changeByte(bfReserved1), 0, 2);// 杈撳叆浣嶅浘鏂囦欢淇濈暀瀛?
        dos.write(changeByte(bfReserved2), 0, 2);// 杈撳叆浣嶅浘鏂囦欢淇濈暀瀛?
        dos.write(changeByte(bfOffBits), 0, 4);// 杈撳叆浣嶅浘鏂囦欢鍋忕Щ閲?

        int biSize = 40;// 淇℃伅澶存墍闇?鐨勫瓧鑺傛暟锛?14-17瀛楄妭锛?
        int biWidth = nWidth;// 浣嶅浘鐨勫锛?18-21瀛楄妭锛?
        int biHeight = nHeight;// 浣嶅浘鐨勯珮锛?22-25瀛楄妭锛?
        int biPlanes = 1; // 鐩爣璁惧鐨勭骇鍒紝蹇呴』鏄?1锛?26-27瀛楄妭锛?
        int biBitcount = 8;// 姣忎釜鍍忕礌鎵?闇?鐨勪綅鏁帮紙28-29瀛楄妭锛夛紝蹇呴』鏄?1浣嶏紙鍙岃壊锛夈??4浣嶏紙16鑹诧級銆?8浣嶏紙256鑹诧級鎴栬??24浣嶏紙鐪熷僵鑹诧級涔嬩竴銆?
        int biCompression = 0;// 浣嶅浘鍘嬬缉绫诲瀷锛屽繀椤绘槸0锛堜笉鍘嬬缉锛夛紙30-33瀛楄妭锛夈??1锛圔I_RLEB鍘嬬缉绫诲瀷锛夋垨2锛圔I_RLE4鍘嬬缉绫诲瀷锛変箣涓?銆?
        int biSizeImage = w * nHeight;// 瀹為檯浣嶅浘鍥惧儚鐨勫ぇ灏忥紝鍗虫暣涓疄闄呯粯鍒剁殑鍥惧儚澶у皬锛?34-37瀛楄妭锛?
        int biXPelsPerMeter = 0;// 浣嶅浘姘村钩鍒嗚鲸鐜囷紝姣忕背鍍忕礌鏁帮紙38-41瀛楄妭锛夎繖涓暟鏄郴缁熼粯璁ゅ??
        int biYPelsPerMeter = 0;// 浣嶅浘鍨傜洿鍒嗚鲸鐜囷紝姣忕背鍍忕礌鏁帮紙42-45瀛楄妭锛夎繖涓暟鏄郴缁熼粯璁ゅ??
        int biClrUsed = 0;// 浣嶅浘瀹為檯浣跨敤鐨勯鑹茶〃涓殑棰滆壊鏁帮紙46-49瀛楄妭锛夛紝濡傛灉涓?0鐨勮瘽锛岃鏄庡叏閮ㄤ娇鐢ㄤ簡
        int biClrImportant = 0;// 浣嶅浘鏄剧ず杩囩▼涓噸瑕佺殑棰滆壊鏁?(50-53瀛楄妭)锛屽鏋滀负0鐨勮瘽锛岃鏄庡叏閮ㄩ噸瑕?

        dos.write(changeByte(biSize), 0, 4);// 杈撳叆淇℃伅澶存暟鎹殑鎬诲瓧鑺傛暟
        dos.write(changeByte(biWidth), 0, 4);// 杈撳叆浣嶅浘鐨勫
        dos.write(changeByte(biHeight), 0, 4);// 杈撳叆浣嶅浘鐨勯珮
        dos.write(changeByte(biPlanes), 0, 2);// 杈撳叆浣嶅浘鐨勭洰鏍囪澶囩骇鍒?
        dos.write(changeByte(biBitcount), 0, 2);// 杈撳叆姣忎釜鍍忕礌鍗犳嵁鐨勫瓧鑺傛暟
        dos.write(changeByte(biCompression), 0, 4);// 杈撳叆浣嶅浘鐨勫帇缂╃被鍨?
        dos.write(changeByte(biSizeImage), 0, 4);// 杈撳叆浣嶅浘鐨勫疄闄呭ぇ灏?
        dos.write(changeByte(biXPelsPerMeter), 0, 4);// 杈撳叆浣嶅浘鐨勬按骞冲垎杈ㄧ巼
        dos.write(changeByte(biYPelsPerMeter), 0, 4);// 杈撳叆浣嶅浘鐨勫瀭鐩村垎杈ㄧ巼
        dos.write(changeByte(biClrUsed), 0, 4);// 杈撳叆浣嶅浘浣跨敤鐨勬?婚鑹叉暟
        dos.write(changeByte(biClrImportant), 0, 4);// 杈撳叆浣嶅浘浣跨敤杩囩▼涓噸瑕佺殑棰滆壊鏁?

        for (int i = 0; i < 256; i++) {
            dos.writeByte(i);
            dos.writeByte(i);
            dos.writeByte(i);
            dos.writeByte(0);
        }

        byte[] filter = null;
        if (w > nWidth)
        {
            filter = new byte[w-nWidth];
        }

        for(int i=0;i<nHeight;i++)
        {
            dos.write(imageBuf, (nHeight-1-i)*nWidth, nWidth);
            if (w > nWidth)
                dos.write(filter, 0, w-nWidth);
        }
        dos.flush();
        dos.close();
        fos.close();
    }

    private static byte[] changeByte(int data) {
        return intToByteArray(data);
    }

    private static byte[] intToByteArray (final int number) {
        byte[] abyte = new byte[4];
        abyte[0] = (byte) (0xff & number);
        abyte[1] = (byte) ((0xff00 & number) >> 8);
        abyte[2] = (byte) ((0xff0000 & number) >> 16);
        abyte[3] = (byte) ((0xff000000 & number) >> 24);
        return abyte;
    }
}
