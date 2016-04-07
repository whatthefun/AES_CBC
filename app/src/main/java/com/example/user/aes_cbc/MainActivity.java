package com.example.user.aes_cbc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {
    private final static String IV = "AAAAAAAAAAAAAAAA";
    private final static String KEY = "0123456789abcdef";
    private EditText edtEncrypt, edtDecrypt;
    private ImageButton imgBtnEn, imgBtnDe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEncrypt = (EditText) findViewById(R.id.edtEncrypt);
        edtDecrypt = (EditText) findViewById(R.id.edtDecrypt);
        imgBtnEn = (ImageButton) findViewById(R.id.imgBtnEn);
        imgBtnDe = (ImageButton) findViewById(R.id.imgBtnDe);

        imgBtnEn.setOnClickListener(imgBtnEnListener);
        imgBtnDe.setOnClickListener(imgBtnDeListener);
    }

    public ImageButton.OnClickListener imgBtnEnListener = new ImageButton.OnClickListener() {
        @Override
        public void onClick(View v) {
            byte[] TextByte = new byte[0];
            String encryptText = "";
            try {
                TextByte = EncryptAES(IV.getBytes("UTF-8"), KEY.getBytes("UTF-8"), edtEncrypt.getText().toString().getBytes("UTF-8"));
                encryptText = Base64.encodeToString(TextByte, Base64.DEFAULT);
                edtDecrypt.setText(encryptText);
            } catch (Exception e) {
                edtDecrypt.setText("");
                Log.e("imgBtnEn", e.toString());
            }
        }
    };

    public ImageButton.OnClickListener imgBtnDeListener = new ImageButton.OnClickListener() {
        @Override
        public void onClick(View v) {
            byte[] TextByte = new byte[0];
            String decryptText = "";
            try {
                TextByte = DecryptAES(IV.getBytes("UTF-8"), KEY.getBytes("UTF-8"), Base64.decode(edtDecrypt.getText().toString().getBytes("UTF-8"), Base64.DEFAULT));
                decryptText = new String(TextByte, "UTF-8");
                edtEncrypt.setText(decryptText);
            } catch (Exception e) {
                edtEncrypt.setText("");
                Log.e("ImgBtnDe", e.toString());
            }
        }
    };

    private static byte[] EncryptAES(byte[] iv, byte[] key, byte[] text) {
        try {
            AlgorithmParameterSpec mAlgorithmParameterSpec = new IvParameterSpec(iv);
            SecretKeySpec mSecretKeySpec = new SecretKeySpec(key, "AES");
            Cipher mCipher = null;
            mCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            mCipher.init(Cipher.ENCRYPT_MODE, mSecretKeySpec, mAlgorithmParameterSpec);

            return mCipher.doFinal(text);
        } catch (Exception ex) {
            return null;
        }
    }

    //AES解密，帶入byte[]型態的16位英數組合文字、32位英數組合Key、需解密文字
    private static byte[] DecryptAES(byte[] iv, byte[] key, byte[] text) {
        try {
            AlgorithmParameterSpec mAlgorithmParameterSpec = new IvParameterSpec(iv);
            SecretKeySpec mSecretKeySpec = new SecretKeySpec(key, "AES");
            Cipher mCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            mCipher.init(Cipher.DECRYPT_MODE,
                    mSecretKeySpec,
                    mAlgorithmParameterSpec);

            return mCipher.doFinal(text);
        } catch (Exception ex) {
            return null;
        }
    }


//    public static byte[] encrypt(String plainText, String encryptionKey) throws Exception {
//        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
//        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
//        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
//        return cipher.doFinal(plainText.getBytes("UTF-8"));
//    }
//
//    public static String decrypt(byte[] cipherText, String encryptionKey) throws Exception {
//        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
//        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
//        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
//        return new String(cipher.doFinal(cipherText), "UTF-8");
//    }


//    public String encrypt(String key, String initVector, String value) {
//        try {
//
//            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
//            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
//
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
//            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
//
//            byte[] encrypted = cipher.doFinal(value.getBytes());
//            return Base64.encodeToString(encrypted,Base64.DEFAULT);
//        } catch (Exception e) {
//            Log.e("Encrypt",e.toString());
//        }
//
//        return null;
//    }
//
//    public String decrypt(String key, String initVector, String encrypted) {
//        try {
//            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
//            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
//
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
//            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
//
//            byte[] original = cipher.doFinal(Base64.decode(encrypted, Base64.DEFAULT));
//
//            return new String(original);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        return null;
//    }

//    public byte[] Hash_MD5 (String input){
//        byte[] bytesInput = input.getBytes();
//        try{
//            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
//            messageDigest.update(bytesInput);
//            byte[] hashInput = messageDigest.digest();
//            return hashInput;
//        }catch (Exception e){
//            Log.e("HASH",e.toString());
//        }
//        return null;
//    }
}
