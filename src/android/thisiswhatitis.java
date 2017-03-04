package cordova.plugin.thisiswhatitis;




import android.content.Context;
import android.content.Intent;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONException;
import org.json.JSONObject;
import javax.crypto.Cipher;
import android.app.KeyguardManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.util.Base64;
import android.security.KeyPairGeneratorSpec;
import android.os.Build;
import android.content.Context;
import android.util.Log;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.security.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.StringBuffer;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.security.auth.x500.X500Principal;
import android.security.keystore.KeyGenParameterSpec;
import static android.os.Build.VERSION.SDK_INT;
import android.security.keystore.KeyProperties;
import 	javax.crypto.KeyGenerator;
import 	android.util.Log;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.GCMParameterSpec;
/**
 * This class echoes a string called from JavaScript.
 */
public class thisiswhatitis extends CordovaPlugin {
      Cipher cipher = null;
       @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		
		   if (action.equals("set")) {
            String alias = args.getString(0);
            String input = args.getString(1);
			alias = alias + "thisisanpasswordusedforstoringthedatarequired";
            this.set(alias, input, callbackContext);
            return true;
        }
		
		else if (action.equals("get")) {
            String alias = args.getString(0);
			alias = alias + "thisisanpasswordusedforstoringthedatarequired";
            this.get(alias, callbackContext);
            return true;
        }
		
		else if (action.equals("delete")) {
            String alias = args.getString(0);
			alias = alias + "thisisanpasswordusedforstoringthedatarequired";
            this.delete(alias, callbackContext);
            return true;
        }
        return false;
    }

	
	 private void set(String alias, String input, CallbackContext callbackContext) {
			    try {
      KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
      keyStore.load(null);
 Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
      if (!keyStore.containsAlias(alias)) {

         

                if (SDK_INT < 23) {
                        cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
                    Calendar start = Calendar.getInstance();
                    Calendar end = Calendar.getInstance();
                    end.add(Calendar.YEAR, 2);
                    KeyPairGeneratorSpec spec = new KeyPairGeneratorSpec.Builder(getContext())
                            .setAlias(alias)
                            .setSubject(new X500Principal(String.format("CN=%s, O=%s", "Bower", "com.android.keystore.com")))
                            .setSerialNumber(BigInteger.ONE)
                            .setStartDate(start.getTime())
                            .setEndDate(end.getTime())
                            .build();
                   KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore");
                    generator.initialize(spec);
                    KeyPair keyPair =  generator.generateKeyPair();

                    PublicKey publicKey = keyStore.getCertificate(alias).getPublicKey();
                    cipher.init(Cipher.ENCRYPT_MODE, publicKey);
                }
                else if (SDK_INT >= 23) {
                     cipher = Cipher.getInstance("AES/GCM/NoPadding");
                    KeyGenerator keyGenerator = KeyGenerator.getInstance(
                            KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
                    keyGenerator.init(
                            new KeyGenParameterSpec.Builder(alias,
                                    KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                                    .build());
                    keyGenerator.generateKey();
                    cipher.init(Cipher.ENCRYPT_MODE,     keyGenerator.generateKey() );
                    byte[] encryptionIv = cipher.getIV();

                    SharedPreferences.Editor editor = getContext().getSharedPreferences("com.save.e",0).edit();
                    editor.putString("encryptionIv", Base64.encodeToString(encryptionIv, Base64.DEFAULT));
                    editor.apply();
                }
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            CipherOutputStream cipherOutputStream = new CipherOutputStream(
                    outputStream, cipher);
            cipherOutputStream.write(input.getBytes("UTF-8"));
            cipherOutputStream.close();
            byte[] vals = outputStream.toByteArray();
            this.write(vals, callbackContext);
            callbackContext.success("Success");

            }



            

        }
		  catch (Exception e) {
            callbackContext.error(e.getMessage());
        }
	 }
	 
	  public  void write(byte[] vals, CallbackContext callbackContext)  {
        try {
			  SharedPreferences pref = getContext().getSharedPreferences("com.save",0); 
    Editor editor = pref.edit();
	 editor.putString("npcorbower", Base64.encodeToString(vals, 0)); 
	  editor.commit();
            // FileOutputStream fileStream = context.openFileOutput("npcorbower.sdat", context.MODE_PRIVATE);
            // fileStream.write(vals);
            // fileStream.close();
        } catch (Exception e) {
             callbackContext.error(e.getMessage());
        }
    }

	private void get(String alias,  CallbackContext callbackContext) {
		  try {
			    KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
				keyStore.load(null);
            if (SDK_INT < 23) {
                 cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
                PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, null);
                cipher.init(Cipher.DECRYPT_MODE, privateKey);
            }
            else if (SDK_INT >= 23) {
                    cipher = Cipher.getInstance("AES/GCM/NoPadding");
                SharedPreferences pref = getContext().getSharedPreferences("com.save.e",0);
                String base64EncryptionIv = pref.getString("encryptionIv", null);
                byte[] encryptionIv = Base64.decode(base64EncryptionIv, Base64.DEFAULT);

                SecretKey key = (SecretKey) keyStore.getKey(alias, null);
                cipher.init(Cipher.DECRYPT_MODE, key,  new GCMParameterSpec(16*8,encryptionIv) );
           }
				 CipherInputStream cipherInputStream = new CipherInputStream(
                    new ByteArrayInputStream(this.read(callbackContext)), cipher);
			    ArrayList<Byte> values = new ArrayList<Byte>();
            int nextByte;
            while ((nextByte = cipherInputStream.read()) != -1) {
                values.add((byte)nextByte);
            }
            byte[] bytes = new byte[values.size()];
            for(int i = 0; i < bytes.length; i++) {
                bytes[i] = values.get(i).byteValue();
            }

            String value = new String(bytes, 0, bytes.length, "UTF-8");
            callbackContext.success(value);
		  }
		  catch (Exception e){
			   callbackContext.error(e.getMessage());
		  }
	}

	 public  byte[] read(CallbackContext callbackContext) {
        try {
			 SharedPreferences pref = getContext().getSharedPreferences("com.save",0); 
			String value =   pref.getString("npcorbower", null);
			byte[] encryptedText = Base64.decode(value.getBytes(), Base64.DEFAULT);
        //    FileInputStream fileStream = context.openFileInput("npcorbower.sdat");
         //   byte[] buffer = new byte[8192];
         //   int bytesRead;
         //   ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
         //   while ((bytesRead = fileStream.read(buffer)) != -1) {
           //     byteStream.write(buffer, 0, bytesRead);
        //    }
            // byte[] encryptedText = byteStream.toByteArray();
            // fileStream.close();
            return encryptedText;
        } catch (Exception e) {
            callbackContext.error(e.getMessage());;
            return new byte[0];
        }
    }
	
	
	
private void delete(String alias,  CallbackContext callbackContext) {
		  try {
			     KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            keyStore.deleteEntry(alias);
			  this.remove(callbackContext);
            callbackContext.success("Success");
		  }
		  catch (Exception e){
			   callbackContext.error(e.getMessage());
		  }
	}
 public  void remove(CallbackContext callbackContext)  {
        try {
			 SharedPreferences pref = getContext().getSharedPreferences("com.save",0); 
    Editor editor = pref.edit();
	editor.remove("npcorbower"); 
	 editor.commit();
         //   context.deleteFile("npcorbower.sdat");
        } catch (Exception e) {
            callbackContext.error(e.getMessage());
        }

    } 


	private Context getContext() {
       return cordova.getActivity().getApplicationContext();
    }
	 

}
