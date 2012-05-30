VRML-Extension
==============

VRML-Extension for NetLogo

This is an extension (with only very basic functionality) for creating VRML files from NetLogo.  

The main application it was designed for was as a means of exporting 3D shapes from NetLogo so that they could then be printed on a 3D printer.

Note: The third-party "CyberX3D for Java" library that this extension is using has a lot of untapped capabilities, so... this extension could be fleshed out to do a lot more, if someone had the time/inclination.

## Using

See the included vrml.html file and/or the example modified NetLogo models.

## Building

Use the NETLOGO environment variable to tell the Makefile which NetLogo.jar to compile against.  For example:

    NETLOGO=/Applications/NetLogo\\\ 5.0 make

If compilation succeeds, `vrml.jar` will be created.

## Credits

This extension was written by Forrest Stonedahl.

## Terms of Use

[![CC0](http://i.creativecommons.org/p/zero/1.0/88x31.png)](http://creativecommons.org/publicdomain/zero/1.0/)

The NetLogo matrix extension is in the public domain.  To the extent possible under law, Forrest Stonedahl has waived all copyright and related or neighboring rights.

The vrml extension makes use of (and is distributed with) the "CyberX3D for Java" library (cx3djava100a.jar), which is distributed under the following license:

Copyright (C) 1996-2003 Satoshi Konno 
All rights reserved. 
 
Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met: 
 
1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.  
 
2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation 
and/or other materials provided with the distribution.   
 
3. The name of the author may not be used to endorse or promote products derived from this software without specific prior written permission.   
 
THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT 
NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
DISCLAIMED. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE 
GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY 
THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR 
OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF 
SUCH DAMAGE.


