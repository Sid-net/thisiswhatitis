<font size="6"><b>thisiswhatitis is a SecureStorage plugin for Apache Cordova</b></font>

<font size="6"><b>Introduction</b></font>

This plugin is for use with Apache Cordova on Android and iOS for securely storing string based information.

<font size="6"><b>Supported platforms</b></font>

Android<br />
iOS


<font size="6"><b>Installation</b></font>

cordova plugin add thisiswhatitis


<font size="6"><b>API</b></font>

Create a namespaced storage.

var secureStorage = new cordova.plugins.thisiswhatitis;

Set a key/value in the storage.

secureStorage.set(
    function (response) { console.log(response); },
    function (error) { console.log(error); },
    'Key', 'Value');

Key and value are both strings.

Retrieving the value

secureStorage.get(
    function (response) { console.log(response); },
    function (error) { console.log(error); },
    'Key');

Remove a key from the storage.

secureStorage.delete(
    function (response) { console.log(response); },
    function (error) { console.log('Error, ' + error); },
    'Key');

Plugin has been updated to work with all API levels for Android and all OS versions for iOS

Windows Coming Soon!

Part of the code has been extracted from https://github.com/pradeep1991singh/cordova-plugin-secure-key-store/blob/master/src/ios/SecureKeyStore.m 

Credits to <font size="8"><b>Karthik Vinnakota</b></font> for helping in getting the iOS code to a working condtion.

<font size="6"><b>MIT License</b></font>

Copyright (c) 2017 thisiswhatitis

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.