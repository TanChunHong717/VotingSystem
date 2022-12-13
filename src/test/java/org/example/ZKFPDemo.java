package org.example;

import com.zkteco.biometric.FingerprintSensorErrorCode;
import com.zkteco.biometric.FingerprintSensorEx;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

public class ZKFPDemo extends JFrame{
    JButton btnOpen = null;
    JButton btnEnroll = null;
    JButton btnVerify = null;
    JButton btnIdentify = null;
    JButton btnRegImg = null;
    JButton btnIdentImg = null;
    JButton btnClose = null;
    JButton btnImg = null;


    private JTextArea textArea;

    //the width of fingerprint image
    int fpWidth = 0;
    //the height of fingerprint image
    int fpHeight = 0;
    //for verify test
    private byte[] lastRegTemp = new byte[2048];
    //the length of lastRegTemp
    private int cbRegTemp = 0;
    //pre-register template
    private byte[][] regtemparray = new byte[3][2048];
    //Register
    private boolean bRegister = false;
    //Identify
    private boolean bIdentify = true;
    //finger id
    private int iFid = 1;

    private int nFakeFunOn = 1;
    //must be 3
    static final int enroll_cnt = 3;
    //the index of pre-register function
    private int enroll_idx = 0;

    private byte[] imgbuf = null;
    private byte[] template = new byte[2048];
    private int[] templateLen = new int[1];


    private boolean mbStop = true;
    private long mhDevice = 0;
    private long mhDB = 0;
    private WorkThread workThread = null;

    public void launchFrame(){
        this.setLayout (null);
        btnOpen = new JButton("Open");
        this.add(btnOpen);
        int nRsize = 20;
        btnOpen.setBounds(30, 10 + nRsize, 100, 30);

        btnEnroll = new JButton("Enroll");
        this.add(btnEnroll);
        btnEnroll.setBounds(30, 60 + nRsize, 100, 30);

        btnVerify = new JButton("Verify");
        this.add(btnVerify);
        btnVerify.setBounds(30, 110 + nRsize, 100, 30);

        btnIdentify = new JButton("Identify");
        this.add(btnIdentify);
        btnIdentify.setBounds(30, 160 + nRsize, 100, 30);

        btnRegImg = new JButton("Register By Image");
        this.add(btnRegImg);
        btnRegImg.setBounds(15, 210 + nRsize, 120, 30);

        btnIdentImg = new JButton("Verify By Image");
        this.add(btnIdentImg);
        btnIdentImg.setBounds(15, 260 + nRsize, 120, 30);


        btnClose = new JButton("Close");
        this.add(btnClose);
        btnClose.setBounds(30, 310 + nRsize, 100, 30);


        btnImg = new JButton();
        btnImg.setBounds(150, 5, 256, 300);
        btnImg.setDefaultCapable(false);
        this.add(btnImg);

        textArea = new JTextArea();
        this.add(textArea);
        textArea.setBounds(10, 440, 480, 100);


        this.setSize(520, 580);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("ZKFinger Demo");
        this.setResizable(false);

        btnOpen.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if (0 != mhDevice)
                {
                    //already inited
                    textArea.setText("Please close device first!");
                    return;
                }
                int ret = FingerprintSensorErrorCode.ZKFP_ERR_OK;
                //Initialize
                cbRegTemp = 0;
                bRegister = false;
                bIdentify = false;
                iFid = 1;
                enroll_idx = 0;
                if (FingerprintSensorErrorCode.ZKFP_ERR_OK != FingerprintSensorEx.Init())
                {
                    textArea.setText("Init failed!");
                    return;
                }
                ret = FingerprintSensorEx.GetDeviceCount();
                if (ret < 0)
                {
                    textArea.setText("No devices connected!");
                    FreeSensor();
                    return;
                }
                if (0 == (mhDevice = FingerprintSensorEx.OpenDevice(0)))
                {
                    textArea.setText("Open device fail, ret = " + ret + "!");
                    FreeSensor();
                    return;
                }
                if (0 == (mhDB = FingerprintSensorEx.DBInit()))
                {
                    textArea.setText("Init DB fail, ret = " + ret + "!");
                    FreeSensor();
                    return;
                }


                //set fakefun off

                //FingerprintSensorEx.SetParameter(mhDevice, 2002, changeByte(nFakeFunOn), 4);

                byte[] paramValue = new byte[4];
                int[] size = new int[1];
                //GetFakeOn
                //size[0] = 4;
                //FingerprintSensorEx.GetParameters(mhDevice, 2002, paramValue, size);
                //nFakeFunOn = byteArrayToInt(paramValue);

                size[0] = 4;
                FingerprintSensorEx.GetParameters(mhDevice, 1, paramValue, size);
                fpWidth = byteArrayToInt(paramValue);
                size[0] = 4;
                FingerprintSensorEx.GetParameters(mhDevice, 2, paramValue, size);
                fpHeight = byteArrayToInt(paramValue);
                //width = fingerprintSensor.getImageWidth();
                //height = fingerprintSensor.getImageHeight();
                imgbuf = new byte[fpWidth*fpHeight];
                btnImg.resize(fpWidth, fpHeight);
                mbStop = false;
                workThread = new WorkThread();
                workThread.start();// 线程启动
                textArea.setText("Open succ!");
            }
        });



        btnClose.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                FreeSensor();

                textArea.setText("Close succ!");
            }
        });

        btnEnroll.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(0 == mhDevice)
                {
                    textArea.setText("Please Open device first!");
                    return;
                }
                if(!bRegister)
                {
                    enroll_idx = 0;
                    bRegister = true;
                    textArea.setText("Please your finger 3 times!");
                }
            }
        });

        btnVerify.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(0 == mhDevice)
                {
                    textArea.setText("Please Open device first!");
                    return;
                }
                if(bRegister)
                {
                    enroll_idx = 0;
                    bRegister = false;
                }
                if(bIdentify)
                {
                    bIdentify = false;
                }
            }
        });

        btnIdentify.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(0 == mhDevice)
                {
                    textArea.setText("Please Open device first!");
                    return;
                }
                if(bRegister)
                {
                    enroll_idx = 0;
                    bRegister = false;
                }
                if(!bIdentify)
                {
                    bIdentify = true;
                }
            }
        });


        btnRegImg.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(0 == mhDB)
                {
                    textArea.setText("Please open device first!");
                }
                String path = "d:\\test\\fingerprint.bmp";
                byte[] fpTemplate = new byte[2048];
                int[] sizeFPTemp = new int[1];
                sizeFPTemp[0] = 2048;
                int ret = FingerprintSensorEx.ExtractFromImage( mhDB, path, 500, fpTemplate, sizeFPTemp);
                if (0 == ret)
                {
                    ret = FingerprintSensorEx.DBAdd( mhDB, iFid, fpTemplate);
                    if (0 == ret)
                    {
                        //String base64 = fingerprintSensor.BlobToBase64(fpTemplate, sizeFPTemp[0]);		
                        iFid++;
                        cbRegTemp = sizeFPTemp[0];
                        System.arraycopy(fpTemplate, 0, lastRegTemp, 0, cbRegTemp);
                        //Base64 Template
                        //String strBase64 = Base64.encodeToString(regTemp, 0, ret, Base64.NO_WRAP);
                        textArea.setText("enroll succ");
                    }
                    else
                    {
                        textArea.setText("DBAdd fail, ret=" + ret);
                    }
                }
                else
                {
                    textArea.setText("ExtractFromImage fail, ret=" + ret);
                }
            }
        });


        btnIdentImg.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(0 ==  mhDB)
                {
                    textArea.setText("Please open device first!");
                }
                String path = "d:\\test\\fingerprint.bmp";
                byte[] fpTemplate = new byte[2048];
                int[] sizeFPTemp = new int[1];
                sizeFPTemp[0] = 2048;
                int ret = FingerprintSensorEx.ExtractFromImage(mhDB, path, 500, fpTemplate, sizeFPTemp);
                if (0 == ret)
                {
                    if (bIdentify)
                    {
                        int[] fid = new int[1];
                        int[] score = new int [1];
                        ret = FingerprintSensorEx.DBIdentify(mhDB, fpTemplate, fid, score);
                        if (ret == 0)
                        {
                            textArea.setText("Identify succ, fid=" + fid[0] + ",score=" + score[0]);
                        }
                        else
                        {
                            textArea.setText("Identify fail, errcode=" + ret);
                        }

                    }
                    else
                    {
                        if(cbRegTemp <= 0)
                        {
                            textArea.setText("Please register first!");
                        }
                        else
                        {
                            ret = FingerprintSensorEx.DBMatch(mhDB, lastRegTemp, fpTemplate);
                            if(ret > 0)
                            {
                                textArea.setText("Verify succ, score=" + ret);
                            }
                            else
                            {
                                textArea.setText("Verify fail, ret=" + ret);
                            }
                        }
                    }
                }
                else
                {
                    textArea.setText("ExtractFromImage fail, ret=" + ret);
                }
            }
        });


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter(){

            @Override
            public void windowClosing(WindowEvent e) {
                // TODO Auto-generated method stub
                FreeSensor();
            }
        });
    }

    private void FreeSensor()
    {
        mbStop = true;
        try {		//wait for thread stopping
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (0 != mhDB)
        {
            FingerprintSensorEx.DBFree(mhDB);
            mhDB = 0;
        }
        if (0 != mhDevice)
        {
            FingerprintSensorEx.CloseDevice(mhDevice);
            mhDevice = 0;
        }
        FingerprintSensorEx.Terminate();
    }

    public static void writeBitmap(byte[] imageBuf, int nWidth, int nHeight,
                                   String path) throws IOException {
        java.io.FileOutputStream fos = new java.io.FileOutputStream(path);
        java.io.DataOutputStream dos = new java.io.DataOutputStream(fos);

        int w = (((nWidth+3)/4)*4);
        int bfType = 0x424d; // 位图文件类型�?0�?1字节�?
        int bfSize = 54 + 1024 + w * nHeight;// bmp文件的大小（2�?5字节�?
        int bfReserved1 = 0;// 位图文件保留字，必须�?0�?6-7字节�?
        int bfReserved2 = 0;// 位图文件保留字，必须�?0�?8-9字节�?
        int bfOffBits = 54 + 1024;// 文件头开始到位图实际数据之间的字节的偏移量（10-13字节�?

        dos.writeShort(bfType); // 输入位图文件类型'BM'
        dos.write(changeByte(bfSize), 0, 4); // 输入位图文件大小
        dos.write(changeByte(bfReserved1), 0, 2);// 输入位图文件保留�?
        dos.write(changeByte(bfReserved2), 0, 2);// 输入位图文件保留�?
        dos.write(changeByte(bfOffBits), 0, 4);// 输入位图文件偏移�?

        int biSize = 40;// 信息头所�?的字节数�?14-17字节�?
        int biWidth = nWidth;// 位图的宽�?18-21字节�?
        int biHeight = nHeight;// 位图的高�?22-25字节�?
        int biPlanes = 1; // 目标设备的级别，必须�?1�?26-27字节�?
        int biBitcount = 8;// 每个像素�?�?的位数（28-29字节），必须�?1位（双色）�??4位（16色）�?8位（256色）或�??24位（真彩色）之一�?
        int biCompression = 0;// 位图压缩类型，必须是0（不压缩）（30-33字节）�??1（BI_RLEB压缩类型）或2（BI_RLE4压缩类型）之�?�?
        int biSizeImage = w * nHeight;// 实际位图图像的大小，即整个实际绘制的图像大小�?34-37字节�?
        int biXPelsPerMeter = 0;// 位图水平分辨率，每米像素数（38-41字节）这个数是系统默认�??
        int biYPelsPerMeter = 0;// 位图垂直分辨率，每米像素数（42-45字节）这个数是系统默认�??
        int biClrUsed = 0;// 位图实际使用的颜色表中的颜色数（46-49字节），如果�?0的话，说明全部使用了
        int biClrImportant = 0;// 位图显示过程中重要的颜色�?(50-53字节)，如果为0的话，说明全部重�?

        dos.write(changeByte(biSize), 0, 4);// 输入信息头数据的总字节数
        dos.write(changeByte(biWidth), 0, 4);// 输入位图的宽
        dos.write(changeByte(biHeight), 0, 4);// 输入位图的高
        dos.write(changeByte(biPlanes), 0, 2);// 输入位图的目标设备级�?
        dos.write(changeByte(biBitcount), 0, 2);// 输入每个像素占据的字节数
        dos.write(changeByte(biCompression), 0, 4);// 输入位图的压缩类�?
        dos.write(changeByte(biSizeImage), 0, 4);// 输入位图的实际大�?
        dos.write(changeByte(biXPelsPerMeter), 0, 4);// 输入位图的水平分辨率
        dos.write(changeByte(biYPelsPerMeter), 0, 4);// 输入位图的垂直分辨率
        dos.write(changeByte(biClrUsed), 0, 4);// 输入位图使用的�?�颜色数
        dos.write(changeByte(biClrImportant), 0, 4);// 输入位图使用过程中重要的颜色�?

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

    public static byte[] changeByte(int data) {
        return intToByteArray(data);
    }

    public static byte[] intToByteArray (final int number) {
        byte[] abyte = new byte[4];
        // "&" 与（AND），对两个整型操作数中对应位执行布尔代数，两个位都为1时输�?1，否�?0�?  
        abyte[0] = (byte) (0xff & number);
        // ">>"右移位，若为正数则高位补0，若为负数则高位�?1  
        abyte[1] = (byte) ((0xff00 & number) >> 8);
        abyte[2] = (byte) ((0xff0000 & number) >> 16);
        abyte[3] = (byte) ((0xff000000 & number) >> 24);
        return abyte;
    }

    public static int byteArrayToInt(byte[] bytes) {
        int number = bytes[0] & 0xFF;
        // "|="按位或赋值�??  
        number |= ((bytes[1] << 8) & 0xFF00);
        number |= ((bytes[2] << 16) & 0xFF0000);
        number |= ((bytes[3] << 24) & 0xFF000000);
        return number;
    }

    private class WorkThread extends Thread {
        @Override
        public void run() {
            super.run();
            int ret = 0;
            while (!mbStop) {
                templateLen[0] = 2048;
                if (0 == (ret = FingerprintSensorEx.AcquireFingerprint(mhDevice, imgbuf, template, templateLen)))
                {
                    if (nFakeFunOn == 1)
                    {
                        byte[] paramValue = new byte[4];
                        int[] size = new int[1];
                        size[0] = 4;
                        int nFakeStatus = 0;
                        //GetFakeStatus
                        ret = FingerprintSensorEx.GetParameters(mhDevice, 2004, paramValue, size);
                        nFakeStatus = byteArrayToInt(paramValue);
                        System.out.println("ret = "+ ret +",nFakeStatus=" + nFakeStatus);
                        if (0 == ret && (byte)(nFakeStatus & 31) != 31)
                        {
                            textArea.setText("Is a fake-finer?");
                            return;
                        }
                    }
                    OnCatpureOK(imgbuf);
                    OnExtractOK(template, templateLen[0]);
                    String strBase64 = FingerprintSensorEx.BlobToBase64(template, templateLen[0]);
                    System.out.println("strBase64=" + strBase64);
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

        private void runOnUiThread(Runnable runnable) {
            // TODO Auto-generated method stub

        }
    }

    private void OnCatpureOK(byte[] imgBuf)
    {
        try {
            writeBitmap(imgBuf, fpWidth, fpHeight, "fingerprint.bmp");
            btnImg.setIcon(new ImageIcon(ImageIO.read(new File("fingerprint.bmp"))));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void OnExtractOK(byte[] template, int len)
    {
        if(bRegister)
        {
            int[] fid = new int[1];
            int[] score = new int [1];
            int ret = FingerprintSensorEx.DBIdentify(mhDB, template, fid, score);
            if (ret == 0)
            {
                textArea.setText("the finger already enroll by " + fid[0] + ",cancel enroll");
                bRegister = false;
                enroll_idx = 0;
                return;
            }
            if (enroll_idx > 0 && FingerprintSensorEx.DBMatch(mhDB, regtemparray[enroll_idx-1], template) <= 0)
            {
                textArea.setText("please press the same finger 3 times for the enrollment");
                return;
            }
            System.arraycopy(template, 0, regtemparray[enroll_idx], 0, 2048);
            enroll_idx++;
            if (enroll_idx == 3) {
                int[] _retLen = new int[1];
                _retLen[0] = 2048;
                byte[] regTemp = new byte[_retLen[0]];

                if (0 == (ret = FingerprintSensorEx.DBMerge(mhDB, regtemparray[0], regtemparray[1], regtemparray[2], regTemp, _retLen)) &&
                        0 == (ret = FingerprintSensorEx.DBAdd(mhDB, iFid, regTemp))) {
                    iFid++;
                    cbRegTemp = _retLen[0];
                    System.arraycopy(regTemp, 0, lastRegTemp, 0, cbRegTemp);
                    String strBase64 = FingerprintSensorEx.BlobToBase64(regTemp, cbRegTemp);
                    //Base64 Template
                    textArea.setText("enroll succ");
                } else {
                    textArea.setText("enroll fail, error code=" + ret);
                }
                bRegister = false;
            } else {
                textArea.setText("You need to press the " + (3 - enroll_idx) + " times fingerprint");
            }
        }
        else
        {
            if (bIdentify)
            {
                int[] fid = new int[1];
                int[] score = new int [1];
                int ret = FingerprintSensorEx.DBIdentify(mhDB, template, fid, score);
                if (ret == 0)
                {
                    textArea.setText("Identify succ, fid=" + fid[0] + ",score=" + score[0]);
                }
                else
                {
                    textArea.setText("Identify fail, errcode=" + ret);
                }

            }
            else
            {
                if(cbRegTemp <= 0)
                {
                    textArea.setText("Please register first!");
                }
                else
                {
                    int ret = FingerprintSensorEx.DBMatch(mhDB, lastRegTemp, template);
                    if(ret > 0)
                    {
                        textArea.setText("Verify succ, score=" + ret);
                    }
                    else
                    {
                        textArea.setText("Verify fail, ret=" + ret);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        new ZKFPDemo().launchFrame();
    }
}
