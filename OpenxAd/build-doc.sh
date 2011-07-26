#!/bin/sh
javadoc -d ./doc -sourcepath ./src ru.denivip.android.widgets \
    -classpath $HOME/Applications/android-sdk-linux_x86/platforms/android-10/android.jar
