# AES_CBC
#### 網路安全課作業

###### 參考小黑人網站的Code http://dean-android.blogspot.tw/2013/07/androidaes.html


<功能介紹>
APP介面主要為四個EditView與兩個ImageButton
* 按下左邊向下的箭頭會讀取上方明文與金鑰，將金鑰HASH過後就將明文加密並顯示在密文，而金鑰也會顯示在密文下的KEY欄，按下右邊向上的箭頭則反之
* 如果加解密過程出錯，並不會造成APP崩潰，而會toast訊息
* 因為初始向量IV是固定值，所以相同的字串加密後都會得到相同的密文

<img src="https://github.com/whatthefun/AES_CBC/blob/master/pic_demo/initialization.jpg" width="300" height="400">
**初始畫面**


<img src="https://github.com/whatthefun/AES_CBC/blob/master/pic_demo/Encrypt.jpg" width="300" height="400">
<img src="https://github.com/whatthefun/AES_CBC/blob/master/pic_demo/Encrypted.jpg" width="300" height="400">
**加密**


<img src="https://github.com/whatthefun/AES_CBC/blob/master/pic_demo/Decrypt.jpg" width="300" height="400">
<img src="https://github.com/whatthefun/AES_CBC/blob/master/pic_demo/Decrypted.jpg" width="300" height="400">
**解密**


<img src="https://github.com/whatthefun/AES_CBC/blob/master/pic_demo/wrongKey.jpg" width="300" height="400">
<img src="https://github.com/whatthefun/AES_CBC/blob/master/pic_demo/prompt.jpg" width="300" height="400">
**輸入錯誤金鑰會有錯誤提示**


<img src="https://github.com/whatthefun/AES_CBC/blob/master/pic_demo/newText.jpg" width="300" height="400">
<img src="https://github.com/whatthefun/AES_CBC/blob/master/pic_demo/newCipher.jpg" width="300" height="400">
**新的明文會產生新的密文哦~**

