//package examples.boot.simpleboard;
//
//import org.junit.Test;
//
//import javax.crypto.Mac;
//import javax.crypto.spec.SecretKeySpec;
//
//import java.util.Base64;
//
//import static com.vividsolutions.jts.io.WKBWriter.bytesToHex;
//
//public class ImageTest {
//    @Test
//    public void test() throws Exception {
//
//
//        String imageUrl = "http://192.168.0.106:8080/image-files/2";
//        imageUrl = imageUrl.replace("=", "");
//        imageUrl = imageUrl.replace("\\/", "_");
//        imageUrl = imageUrl.replace("\\+", "-");
//
//        byte[] s = Base64.getUrlEncoder().encode(imageUrl.getBytes("UTF-8"));
//        String encodeUrl = new String(s, "UTF-8");
//        System.out.println(encodeUrl);
//
//        //https://github.com/DarthSim/imgproxy
//        // fit, fill , crop , force
//        String path = "/fit/300/300/no/0/" + encodeUrl + ".jpg";
//
//        String key = "010101";
//        Integer i = Integer.decode("0x" + key);
//        String keyStr = i.toString();
////        String keyStr = hexToAscii("010101");
////        String salt = hexToAscii("0202");
//        String salt = "0202";
//        Integer i2 = Integer.decode("0x" + salt);
//        String saltStr = i2.toString();
//
//        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
//        SecretKeySpec secret_key = new SecretKeySpec(keyStr.getBytes(), "HmacSHA256");
//        sha256_HMAC.init(secret_key);
//
//        sha256_HMAC.update(saltStr.getBytes());
//        sha256_HMAC.update(key.getBytes());
//
//        byte[] bytes = sha256_HMAC.doFinal();
//
//        byte[] encode = Base64.getUrlEncoder().encode(bytes);
//        String encodeStr = new String(encode, "UTF-8");
//        encodeStr = encodeStr.replace("=", "");
//        encodeStr = encodeStr.replace("\\/", "_");
//        encodeStr = encodeStr.replace("\\+", "-");
//        System.out.println(encodeStr);
//    }
//
//    /*
//  var shaObj = new jsSHA("SHA-256", "BYTES")
//  shaObj.setHMACKey(opts.key, "HEX")
//  shaObj.update(hex2a(opts.salt))
//  shaObj.update(path)
//  var hmac = shaObj.getHMAC("B64").replace(/=/g, "").replace(/\//g, '_').replace(/\+/g, '-')
//  return opts.proxy_url + "/" + hmac + path
//}
//     */
//
//    /*
//    	messageMAC, err := base64.RawURLEncoding.DecodeString(token)
//	if err != nil {
//		return errors.New("Invalid token encoding")
//	}
//
//	mac := hmac.New(sha256.New, conf.Key)
//	mac.Write(conf.Salt)
//	mac.Write([]byte(path))
//	expectedMAC := mac.Sum(nil)
//     */
//
//
//    private String hexToAscii(String hexStr) {
//        StringBuilder output = new StringBuilder("");
//
//        for (int i = 0; i < hexStr.length(); i += 2) {
//            String str = hexStr.substring(i, i + 2);
//            output.append((char) Integer.parseInt(str, 16));
//        }
//
//        return output.toString();
//    }
//
//    private String asciiToHex(String asciiStr) {
//        char[] chars = asciiStr.toCharArray();
//        StringBuilder hex = new StringBuilder();
//        for (char ch : chars) {
//            hex.append(Integer.toHexString((int) ch));
//        }
//
//        return hex.toString();
//    }
//}
