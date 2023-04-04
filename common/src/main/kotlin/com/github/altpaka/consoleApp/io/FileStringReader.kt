package com.github.altpaka.consoleApp.io

import java.io.File
import java.io.FileInputStream

class FileStringReader(
    filepath: String
): ScannerStringReader(FileInputStream(File(filepath)))