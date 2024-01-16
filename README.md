<p align="center">
  <img height="300" width="300" src="https://i.imgur.com/OauunT9.png" />
</p>

<h1 align="center"> Java SatuSehat KYC Library </h1> <br>

<p align="center">
  Library java yang digunakan untuk generate kyc url
</p>

## Tentang library ini

Dokumentasi pembuatan library java SIMRS ICHA untuk integrasi SatuSehat Verifikasi Profil - Know Your Customer (KYC), memiliki beberapa fitur yaitu :

1. Read & Write Text File
2. Base64 Encode & Decode
3. AES Encryption & Decryption
4. RSA Encryption & Decryption
5. KYC SatuSehat Unit Test

```java
private static void sendKyc(String data) throws IOException {

  // ini nanti dirubah ya... sesuai dengan token masing2. karena pasti exired, ini hanya ujicoba saja.
  String access_token = "Adt9ioFlWQCHzSDidWpg4AnUbNki";
  
  String url = "https://api-satusehat.kemkes.go.id/kyc/v1/generate-url";
  
  
  
  URL obj = new URL(url);
  HttpURLConnection con = (HttpURLConnection) obj.openConnection();
  con.setRequestMethod("POST");
  con.setRequestProperty("Content-Type", "text/plain");
  con.setRequestProperty("Authorization", "Bearer "+access_token);

  con.setDoOutput(true);
  OutputStream os = con.getOutputStream();
  os.write(data.getBytes());
  os.flush();
  os.close();
  // For POST only - END

  int responseCode = con.getResponseCode();
  System.out.println("POST Response Code :: " + responseCode);

  if (responseCode == HttpURLConnection.HTTP_OK) { // success
    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    String inputLine;
    StringBuffer response = new StringBuffer();

    while ((inputLine = in.readLine()) != null) {
      response.append(inputLine);
    }
    in.close();

    // print result
    System.out.println(response.toString());
  } else {
    System.out.println("POST request did not work.");
  }
}
```
## Playlist Tutorial

[![Video Tutorial](https://i3.ytimg.com/vi/_sTKG9Vy4d8/hqdefault.jpg)](https://www.youtube.com/playlist?list=PLnyI7vRatb6ozjU82eibeCuqU8tcyRBLq)
