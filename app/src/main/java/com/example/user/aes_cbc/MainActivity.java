package com.example.user.aes_cbc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {
    private final static String IV = "AAAAAAAAAAAAAAAA";
    private EditText edtEncrypt, edtDecrypt, edtEnKey, edtDeKey;
    private ImageButton imgBtnEn, imgBtnDe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEncrypt = (EditText) findViewById(R.id.edtEncrypt);
        edtDecrypt = (EditText) findViewById(R.id.edtDecrypt);
        edtEnKey = (EditText) findViewById(R.id.edtEnKey);
        edtDeKey = (EditText) findViewById(R.id.edtDeKey);
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
                  TextByte = EncryptAES(IV.getBytes("UTF-8"), Hash_MD5(edtEnKey.getText().toString()), edtEncrypt.getText().toString().getBytes("UTF-8"));
                encryptText = Base64.encodeToString(TextByte, Base64.DEFAULT);
                edtDecrypt.setText(encryptText);
                edtDeKey.setText(edtEnKey.getText().toString());
            } catch (Exception e) {
                edtDecrypt.setText(null);
                edtDeKey.setText(null);
                Toast.makeText(MainActivity.this,"加密失敗!",Toast.LENGTH_SHORT).show();
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
                TextByte = DecryptAES(IV.getBytes("UTF-8"), Hash_MD5(edtDeKey.getText().toString()), Base64.decode(edtDecrypt.getText().toString().getBytes("UTF-8"), Base64.DEFAULT));
                decryptText = new String(TextByte, "UTF-8");
                edtEncrypt.setText(decryptText);
                edtEnKey.setText(edtDeKey.getText().toString());
            } catch (Exception e) {
                edtEncrypt.setText(null);
                edtEnKey.setText(null);
                Toast.makeText(MainActivity.this,"解密失敗!",Toast.LENGTH_SHORT).show();
                Log.e("ImgBtnDe", e.toString());
            }
        }
    };
    //AES加密，帶入byte[]型態的16位英數組合文字、32位英數組合Key、需加密文字
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
    //Hash字串並回傳byte[]
    public byte[] Hash_MD5 (String input){
        byte[] bytesInput = input.getBytes();
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(bytesInput);
            byte[] hashInput = messageDigest.digest();
            return hashInput;
        }catch (Exception e){
            Log.e("HASH",e.toString());
        }
        return null;
    }
}
