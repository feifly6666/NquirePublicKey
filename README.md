# NquirePublicKey
Starting with the NQ1000-II-USER-V0.016 version, Nquire1000 will contain a pair of default public and private keys for SSH connections. <br>
Your computer can connect to Nquire1000 via SSH by using the private key.
<br>
# How to connect Nquire1000 by using SSH(Windows10 OS example):
①	Copy the private key file to the .ssh folder of your PC.The default path of .ssh folder will be “C:\Users\dell\.ssh”<br>
※If there is still no .ssh folder in your PC,you may use “ssh-keygen -t rsa”command to create a default one and also it will create a pair of default keys what you should not use.<br>

 
②	Open cmd.exe and type in “ssh root@ip_address” command.Then you will connect to Nquire1000.<br>
※The ip_address should be the Nquire1000’s IP address.<br>
 

 

 
# How to change Nquire1000’s public key<br>
If you are an android developer you can send the following broadcast to change Nquire1000’s public key.<br>
Intent intent = new Intent();  <br>
intent.putExtra("secretkey", public key String);<br>
intent.setAction("com.secret.key"); <br>
sendBroadcast(intent);<br>
	We also have a apk program “PublicKey.apk” for you to run on Nquire1000 to change Nquire1000’s public key.<br>

 

 
