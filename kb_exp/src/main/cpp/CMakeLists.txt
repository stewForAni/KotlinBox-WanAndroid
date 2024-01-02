
# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.22.1)

project(my_malloc)

set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR}/../jniLibs/${CMAKE_ANDROID_ARCH_ABI})

ADD_SUBDIRECTORY(hookmalloc)
ADD_SUBDIRECTORY(malloc)