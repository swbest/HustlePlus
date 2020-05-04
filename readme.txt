GP19

Group Leader:
Tan Jia Le Damien
A0183560E
damientan@u.nus.edu
+6582990105

Group Member 1:
Amanda Lai Ting Yu
A0190392A
amanda.lai@u.nus.edu
+6590226659

Group Member 2:
Nurhidayah Rahmat
A0176756M
e0235163@u.nus.edu
+6594568341

Group Member 3:
Wong Jin Fu Shaun
A0189408R
e0235163@u.nus.edu
+6597280022

Instructions:
Step 1: Make sure you have Netbeans with GlassFish, Node, Angular, Ionic and Gradle is properly installed and configured

Step 2: Open ./source/HustlePlus in Netbeans

Step 3: Create MySQL database named HustlePlus and create JDBC resource and persistence unit

Step 4: Deploy HustlePlus onto GlassFish

Step 5: Open http://localhost:8080/HustlePlus-war to access the JSF front-end
Admin Account (Username: admin1 Password: password)
Company Account (Username: company1 password: password)

Step 6: Open ./source/HustlePlusIonic

Step 7: run "npm install" in your terminal

Step 8: run "ionic serve" in your terminal

Step 9: Open http://localhost:8100/ to access Ionic front-end on the browser

Student Accounts:
Username: bryantanyx Password: password
Username: chlolim Password: password
Username: rusellng Password: password

To run the Ionic application on Android, follow the steps below
Step 10: run "npm install -g native-run" in your terminal

Step 11: run "ionic cordova prepare android" in your terminal,
enter "Y" when prompted to intergrate the app with Cordova,
enter "Y" when prompted to install platform android

Step 12: Connect the Android device to the computer and enable USB debugging

Step 13: Open .\source\HustlePlusIonic\src\app\session.service.ts and change the IP address on line 20 to your computer's IP address

Step 14: run "ionic cordova run android -l" in your terminal

Step 15: Open HustlePlus on your Android device

Step 16: To create APK file for Android devices, go to .\source\HustlePlusIonic\ and run "ionic cordova build --release android" in the terminal,
the output file "" will be in .\source\HustlePlusIonic\platforms\android\app\build\outputs\apk\release